package uk.co.planetbeyond.service.outbound;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;

import uk.co.planetbeyond.service.constants.Constants;

public class HelloManager
{
	private ManagerConnection managerConnection;

	public HelloManager() throws IOException
	{
		ManagerConnectionFactory factory = new ManagerConnectionFactory(Constants.ASTERISK_HOST_IP_ADDRESS, Constants.ASTERISK_MANAGER_NAME, Constants.ASTERISK_MANAGER_PASSWORD);

		this.managerConnection = factory.createManagerConnection();
	}

	public void run() throws IOException, AuthenticationFailedException, TimeoutException, AgiException
	{
		OriginateAction originateAction;
		ManagerResponse response;

		originateAction = new OriginateAction();
		originateAction.setChannel(Constants.SIP_CHANNEL_FOR_OUTSIDE);
		// originateAction.setContext(Constants.INCOMING_CONTEXT);
		originateAction.setContext(Constants.OUTGOING_CONTEXT);
		originateAction.setExten("923458590000");
		originateAction.setPriority(Constants.EXTENSION_TOP_PRIORITY);
		originateAction.setTimeout(Constants.ASTERISK_RESPONSE_WAIT_TIME_IN_MILLIES);

		// connect to Asterisk and log in
		managerConnection.login();

		// send the originate action and wait for a maximum of 30 seconds for Asterisk
		// to send a reply
		response = managerConnection.sendAction(originateAction, 30000);

		// print out whether the originate succeeded or not
		System.out.println("Response: " + response.getResponse());

		// and finally log off and disconnect
		managerConnection.logoff();
	}

	public static void main(String[] args) throws Exception
	{
		// org.asteriskjava.Cli

		// Cli cli = new Cli();
		// Cli.main(null);

		// UserActivityHandler handler;
		//
		// handler = new UserActivityHandler();
		// handler.run();

		HelloManager helloManager;

		helloManager = new HelloManager();
		helloManager.run();
	}
}
