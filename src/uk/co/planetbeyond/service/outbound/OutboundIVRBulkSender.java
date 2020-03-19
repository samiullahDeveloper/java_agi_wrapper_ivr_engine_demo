package uk.co.planetbeyond.service.outbound;

import java.io.IOException;
import java.util.List;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.planetbeyond.service.constants.Constants;
import uk.co.planetbeyond.service.generated.UserBean;
import uk.co.planetbeyond.service.generated.exception.DAOException;
import uk.co.planetbeyond.service.manager.UserManagerImpl;

public class OutboundIVRBulkSender
{
	private static Logger log = LoggerFactory.getLogger(OutboundIVRBulkSender.class);

	private ManagerConnection managerConnection;

	public OutboundIVRBulkSender() throws IOException
	{
		ManagerConnectionFactory factory = new ManagerConnectionFactory(Constants.ASTERISK_HOST_IP_ADDRESS, Constants.ASTERISK_MANAGER_NAME, Constants.ASTERISK_MANAGER_PASSWORD);
		this.managerConnection = factory.createManagerConnection();
	}

	public void run() throws IOException, AuthenticationFailedException, TimeoutException, DAOException
	{
		OriginateAction action;
		ManagerResponse response;

		List<UserBean> listOfUsers = UserManagerImpl.getInstance().loadByPreparedStatementAsList("ORDER BY msisdn", new Object[] {}, null);

		if (listOfUsers == null)
		{
			log.info("No user found in DB.");
			return;
		}

		action = new OriginateAction();
		action.setChannel(Constants.SIP_CHANNEL_FOR_OUTSIDE);
		action.setContext(Constants.OUTGOING_CONTEXT);
		action.setPriority(Constants.EXTENSION_TOP_PRIORITY);
		action.setTimeout(Constants.ASTERISK_RESPONSE_WAIT_TIME_IN_MILLIES);

		action.setCallingPres(5);
		// action.setAsync(true);

		// connect to Asterisk and log in
		managerConnection.login();

		for (UserBean user : listOfUsers)
		{
			System.out.println("MSISDN: " + user.getMsisdn());

			// action.setCallerId("1111");
			action.setExten(user.getMsisdn());

			// send the originate action and wait for a maximum of 30 seconds for Asterisk
			// to send a reply
			response = managerConnection.sendAction(action, Constants.ASTERISK_RESPONSE_WAIT_TIME_IN_MILLIES);

			// print out whether the originate succeeded or not
			System.out.println("Response: " + response.getResponse());
		}

		// and finally log off and disconnect
		managerConnection.logoff();
	}

	public static void main(String[] args) throws Exception
	{
		OutboundIVRBulkSender helloManager;

		helloManager = new OutboundIVRBulkSender();
		helloManager.run();
	}
}
