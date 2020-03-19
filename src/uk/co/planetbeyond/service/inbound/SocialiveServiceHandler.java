package uk.co.planetbeyond.service.inbound;

import java.util.List;
import java.util.stream.Collectors;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.planetbeyond.service.constants.Constants;
import uk.co.planetbeyond.service.generated.ContentBean;
import uk.co.planetbeyond.service.generated.UserBean;
import uk.co.planetbeyond.service.generated.exception.DAOException;
import uk.co.planetbeyond.service.manager.ContentManagerImpl;
import uk.co.planetbeyond.service.manager.UserManagerImpl;
import uk.co.planetbeyond.util.StringUtil;

public class SocialiveServiceHandler extends BaseAgiScript
{
	private static Logger log = LoggerFactory.getLogger(SocialiveServiceHandler.class);

	@Override
	public void service(AgiRequest request, AgiChannel channel) throws AgiException
	{
		try
		{
			answer();

			String callerId = "923458590004"; // request.getCallerId();

			if (isSubscriber(callerId))
			{
				// play welcome prompt
				streamFile(Constants.PROMPT_FOR_WELCOME);

				playMainMenuAndHandleResponse(callerId);
				return;
			}

			String subscriptionResponse = playPromptAndGetDTMF(Constants.PROMPT_FOR_SUBSCRIPTION);

			if (StringUtil.isEmpty(subscriptionResponse))
			{
				log.info("No digit pressed for prompt:{}", Constants.PROMPT_FOR_SUBSCRIPTION);

				hangup();
				return;
			}

			if (!Constants.SUBSCRIPTION_OPTIN_DTMF.equals(subscriptionResponse))
			{
				log.info("Invalid digit pressed for prompt:{}", Constants.PROMPT_FOR_SUBSCRIPTION);

				hangup();
				return;
			}

			// sending charging request
			if (playChargingPromptAndSendChargingRequest(callerId))
			{
				subscribeUser(callerId);

				// play welcome prompt
				streamFile(Constants.PROMPT_FOR_WELCOME);

				playMainMenuAndHandleResponse(callerId);
			}
		}
		catch (DAOException e)
		{
			e.printStackTrace();
		}

		// hangup
		hangup();
	}

	/**
	 * 
	 * @param promptName
	 * @return
	 * @throws AgiException
	 */
	public String playPromptAndGetDTMF(String promptName) throws AgiException
	{
		// 2nd param means timeout for 6 seconds by default, 3rd param means max digits to press
		String response = getData(promptName, Constants.DEFAULT_TIMEOUT_FOR_DTMF, Constants.MAXIMUM_DIGITS_LIMIT_FOR_DTMF);

		if (!StringUtil.isEmpty(response))
		{
			return response;
		}

		log.info("No DTMF entered for Prompt:{}", promptName);

		// play the prompt again for 2nd time
		response = getData(promptName, Constants.DEFAULT_TIMEOUT_FOR_DTMF, Constants.MAXIMUM_DIGITS_LIMIT_FOR_DTMF);

		// if (StringUtil.isEmpty(response))
		// {
		// log.info("No digit pressed for prompt:{}", Constants.PROMPT_FOR_SUBSCRIPTION);
		//
		// hangup();
		// return "";
		// }
		//
		// if (!Constants.SUBSCRIPTION_OPTIN_DTMF.equals(response))
		// {
		// log.info("Invalid digit pressed for prompt:{}", Constants.PROMPT_FOR_SUBSCRIPTION);
		//
		// hangup();
		// return "";
		// }

		return response;
	}

	/**
	 * adds a new subscriber
	 * 
	 * @param callerId
	 * @throws DAOException
	 */
	private void subscribeUser(String callerId) throws DAOException
	{
		UserBean user = new UserBean();

		user.setMsisdn(callerId);
		user.setSubscriptionStatus(Constants.SUBSCRIPTION_STATUS_SUBSCRIBED);
		user.save();
	}

	private void sendChargingRequest(String callerId)
	{

	}

	/**
	 * checks for valid subscriber
	 * 
	 * @param string
	 * 
	 * @return
	 * @throws DAOException
	 */
	public boolean isSubscriber(String callerId) throws DAOException
	{
		UserBean user = UserManagerImpl.getInstance().loadByPrimaryKey(callerId);

		if (user != null)
		{
			return true;
		}

		return false;
	}

	private boolean playChargingPromptAndSendChargingRequest(String callerId) throws AgiException
	{
		sendChargingRequest(callerId);

		streamFile("Charging-Attempt");

		// TODO charging pass/fail scenario will be handled here, but for now consider successful charging...
		return true;
	}

	/**
	 * @throws AgiException
	 * @throws DAOException
	 */
	public void playMainMenuAndHandleResponse(String callerId) throws AgiException, DAOException
	{
		String mainMenuResponse = playPromptAndGetDTMF(Constants.PROMPT_FOR_MAIN_MENU);

		if (StringUtil.isEmpty(mainMenuResponse))
		{
			log.info("No DTMF entered for Prompt:{}", Constants.PROMPT_FOR_MAIN_MENU);
			return;
		}

		List<ContentBean> listOfIvrContent = ContentManagerImpl.getInstance().loadByPreparedStatementAsList("ORDER BY creation_timestamp DESC", new Object[] {}, null);

		if (listOfIvrContent == null || listOfIvrContent.size() < 1)
		{
			log.info("No IVR content available.");
			return;
		}

		if (Constants.MAIN_MENU_DTMF_FOR_ENTERTAINMENT.equals(mainMenuResponse))
		{
			// play first sub menu
			streamFile(mainMenuResponse);

			handleResponse(listOfIvrContent, Constants.CONTENT_CATEGORY_ENTERTAINMENT, callerId);
		}
		else if (Constants.MAIN_MENU_DTMF_FOR_SPORTS.equals(mainMenuResponse))
		{
			streamFile(mainMenuResponse);

			handleResponse(listOfIvrContent, Constants.CONTENT_CATEGORY_SPORTS, callerId);
		}
		else if (Constants.MAIN_MENU_DTMF_FOR_HEALTH_AND_FITNESS.equals(mainMenuResponse))
		{
			streamFile(mainMenuResponse);

			handleResponse(listOfIvrContent, Constants.CONTENT_CATEGORY_HEALTH_AND_FITNESS, callerId);
		}
		else if (Constants.MAIN_MENU_DTMF_FOR_LITERATURE.equals(mainMenuResponse))
		{
			streamFile(mainMenuResponse);

			handleResponse(listOfIvrContent, Constants.CONTENT_CATEGORY_LITERATURE, callerId);
		}
		else if (Constants.MAIN_MENU_DTMF_FOR_SCIENCE_AND_TECHNOLOGY.equals(mainMenuResponse))
		{
			streamFile(mainMenuResponse);

			handleResponse(listOfIvrContent, Constants.CONTENT_CATEGORY_SCIENCE_AND_TECHNOLOGY, callerId);
		}
		else if (Constants.MAIN_MENU_DTMF_FOR_UNSUBSCRIPTION.equals(mainMenuResponse))
		{
			handleUnsubscriptionFlow(callerId);
		}
	}

	/**
	 * @throws AgiException
	 * @throws DAOException
	 */
	public void handleUnsubscriptionFlow(String callerId) throws AgiException, DAOException
	{
		String response = playPromptAndGetDTMF(Constants.PROMPT_FOR_UNSUBSCRIPTION);

		// main menu flow
		if (Constants.DTMF_FOR_MAIN_MENU.equals(response))
		{
			playMainMenuAndHandleResponse(callerId);
			return;
		}
		// unsubscription flow
		else if (Constants.DTMF_FOR_UNSUBSCRIPTION_CONFIRMATION.equals(response))
		{
			playUnsubscriptionConfirmationPromptAndUnsubscribeUser(callerId);

			// hangup the call
			hangup();
		}
	}

	private void playUnsubscriptionConfirmationPromptAndUnsubscribeUser(String callerId) throws AgiException, DAOException
	{
		// play the successful unsub prompt
		streamFile(Constants.PROMPT_FOR_SUCCESSFUL_UNSUBSCRIPTION);

		UserBean user = UserManagerImpl.getInstance().loadByPrimaryKey(callerId);

		user.setSubscriptionStatus(Constants.SUBSCRIPTION_STATUS_UNSUBSCRIBED);

		user.update();
	}

	/**
	 * @param listOfIvrContent
	 * @param contentCategoryType
	 * @param callerId
	 * @throws AgiException
	 * @throws DAOException
	 */
	public void handleResponse(List<ContentBean> listOfIvrContent, Integer contentCategoryType, String callerId) throws AgiException, DAOException
	{
		List<ContentBean> contentList = getCategoryContent(listOfIvrContent, contentCategoryType);

		if (contentList == null || contentList.size() < 1)
		{
			log.info("Content not found for Category:{}", contentCategoryType);
			return;
		}

		handleResponseAndPlayContent(contentList, callerId);
	}

	/**
	 * @param listOfIvrContent
	 * @param contentCategoryEntertainment
	 * @return
	 */
	public List<ContentBean> getCategoryContent(List<ContentBean> listOfIvrContent, Integer contentCategoryType)
	{
		return listOfIvrContent.stream().filter(line -> contentCategoryType.equals(line.getContentCategory())).collect(Collectors.toList());
	}

	/**
	 * @param listOfContent
	 * @throws AgiException
	 * @throws DAOException
	 */
	public void handleResponseAndPlayContent(List<ContentBean> listOfContent, String callerId) throws AgiException, DAOException
	{
		int playingArticle = 0;

		streamFile(Constants.PROMPT_FOR_REWIND);
		streamFile(Constants.PROMPT_FOR_FORWARD);
		streamFile(Constants.PROMPT_FOR_REPLAY);
		streamFile(Constants.PROMPT_FOR_TO_GO_TO_MAIN_MENU);

		String selectedOptionForArticle = getData(listOfContent.get(playingArticle).getFileName(), Constants.DEFAULT_TIMEOUT_FOR_DTMF, Constants.MAXIMUM_DIGITS_LIMIT_FOR_DTMF);

		if (StringUtil.isEmpty(selectedOptionForArticle))
		{
			log.info("No DTMF entered after playing Artical: {}", listOfContent.get(playingArticle).getFileName());
			return;
		}

		// play the content 2 times in case of no input
		while (true)
		{
			if (selectedOptionForArticle != null && Constants.TRAVERSAL_PROMPT_DTMF_FOR_REWIND.equals(selectedOptionForArticle))
			{
				if (!isPreviousArticleExist(playingArticle))
				{
					selectedOptionForArticle = getData(listOfContent.get(playingArticle).getFileName(), Constants.DEFAULT_TIMEOUT_FOR_DTMF, Constants.MAXIMUM_DIGITS_LIMIT_FOR_DTMF);
					continue;
				}

				streamFile(Constants.PROMPT_FOR_REWIND_AND_PLAY_ARTICLE);

				selectedOptionForArticle = getData(listOfContent.get(--playingArticle).getFileName());
				continue;
			}

			if (selectedOptionForArticle != null && Constants.TRAVERSAL_PROMPT_DTMF_FOR_FORWARD.equals(selectedOptionForArticle))
			{
				if (isLastArticle(listOfContent, playingArticle))
				{
					streamFile(Constants.PROMPT_FOR_LAST_ARTICLE);
					selectedOptionForArticle = getData(listOfContent.get(playingArticle).getFileName());
					continue;
				}

				streamFile(Constants.PROMPT_FOR_FORWARD_AND_PLAY_ARTICLE);

				selectedOptionForArticle = getData(listOfContent.get(++playingArticle).getFileName());
				continue;
			}

			if (selectedOptionForArticle != null && Constants.TRAVERSAL_PROMPT_DTMF_FOR_REPLAY.equals(selectedOptionForArticle))
			{
				selectedOptionForArticle = getData(listOfContent.get(playingArticle).getFileName());
				continue;
			}

			if (selectedOptionForArticle != null && Constants.TRAVERSAL_PROMPT_DTMF_FOR_MAIN_MENU.equals(selectedOptionForArticle))
			{
				playMainMenuAndHandleResponse(callerId);
				break;
			}

			break;
		}
	}

	private boolean isPreviousArticleExist(Integer playingArticle)
	{
		if ((playingArticle == 0))
		{
			return false;
		}

		return true;
	}

	private boolean isLastArticle(List<ContentBean> listOfEntertainmentContent, Integer playingArticle)
	{
		if (playingArticle == (listOfEntertainmentContent.size() - 1))
		{
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws DAOException
	{
		List<ContentBean> list = ContentManagerImpl.getInstance().loadByPreparedStatementAsList("WHERE creation_timestamp > NOW()", new Object[] {}, null);

		if (list == null)
		{
			System.out.println("size: " + list.size());
		}
	}
}