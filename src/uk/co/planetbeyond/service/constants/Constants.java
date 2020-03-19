package uk.co.planetbeyond.service.constants;

public class Constants
{
	// ASTERISK manager's credentials
	public static final String ASTERISK_HOST_IP_ADDRESS = "127.0.0.1";
	public static final String ASTERISK_MANAGER_NAME = "mathias";
	public static final String ASTERISK_MANAGER_PASSWORD = "12345678";

	public static final String SIP_CHANNEL_FOR_OUTSIDE = "SIP/outside";
	public static final String OUTGOING_CONTEXT = "outgoing";
	public static final String INCOMING_CONTEXT = "phones";

	public static final Integer EXTENSION_TOP_PRIORITY = 1;
	public static final Integer ASTERISK_RESPONSE_WAIT_TIME_IN_MILLIES = 30000;

	// costants for content categories
	public static final Integer CONTENT_CATEGORY_ENTERTAINMENT = 1;
	public static final Integer CONTENT_CATEGORY_SPORTS = 2;
	public static final Integer CONTENT_CATEGORY_HEALTH_AND_FITNESS = 3;
	public static final Integer CONTENT_CATEGORY_LITERATURE = 4;
	public static final Integer CONTENT_CATEGORY_SCIENCE_AND_TECHNOLOGY = 5;

	public static final Integer DEFAULT_TIMEOUT_FOR_DTMF = 0;
	public static final Integer MAXIMUM_DIGITS_LIMIT_FOR_DTMF = 1;

	public static final String SUBSCRIPTION_OPTIN_DTMF = "1";

	public static final String MAIN_MENU_DTMF_FOR_ENTERTAINMENT = "1";
	public static final String MAIN_MENU_DTMF_FOR_SPORTS = "2";
	public static final String MAIN_MENU_DTMF_FOR_HEALTH_AND_FITNESS = "3";
	public static final String MAIN_MENU_DTMF_FOR_LITERATURE = "4";
	public static final String MAIN_MENU_DTMF_FOR_SCIENCE_AND_TECHNOLOGY = "5";
	public static final String MAIN_MENU_DTMF_FOR_UNSUBSCRIPTION = "6";

	public static final String DTMF_FOR_MAIN_MENU = "0";
	public static final String DTMF_FOR_UNSUBSCRIPTION_CONFIRMATION = "1";

	public static final String TRAVERSAL_PROMPT_DTMF_FOR_MAIN_MENU = "0";
	public static final String TRAVERSAL_PROMPT_DTMF_FOR_REWIND = "1";
	public static final String TRAVERSAL_PROMPT_DTMF_FOR_FORWARD = "2";
	public static final String TRAVERSAL_PROMPT_DTMF_FOR_REPLAY = "3";

	public static final String PROMPT_FOR_WELCOME = "ivr-welcome-prompt";
	public static final String PROMPT_FOR_SUBSCRIPTION = "Subscription-prompt";
	public static final String PROMPT_FOR_UNSUBSCRIPTION = "Unsub-Confirm";
	public static final String PROMPT_FOR_MAIN_MENU = "Main-Menu";
	public static final String PROMPT_FOR_SUCCESSFUL_UNSUBSCRIPTION = "Unsubscribed";
	public static final String PROMPT_FOR_REWIND = "Rewind-prompt";
	public static final String PROMPT_FOR_FORWARD = "Forward-prompt";
	public static final String PROMPT_FOR_REPLAY = "Replay-article-from-start";
	public static final String PROMPT_FOR_TO_GO_TO_MAIN_MENU = "To-go-main-menu";
	public static final String PROMPT_FOR_REWIND_AND_PLAY_ARTICLE = "Rewind-prompt-and-play-the-article";
	public static final String PROMPT_FOR_LAST_ARTICLE = "This-is-the-last-article";
	public static final String PROMPT_FOR_FORWARD_AND_PLAY_ARTICLE = "Forward-prompt-and-play-previous-article";

	public static final Integer SUBSCRIPTION_STATUS_UNSUBSCRIBED = 0;
	public static final Integer SUBSCRIPTION_STATUS_SUBSCRIBED = 1;

}