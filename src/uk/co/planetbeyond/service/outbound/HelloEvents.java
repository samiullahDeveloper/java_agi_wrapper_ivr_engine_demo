package uk.co.planetbeyond.service.outbound;

import java.io.IOException;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.StatusAction;
import org.asteriskjava.manager.event.ManagerEvent;

public class HelloEvents implements ManagerEventListener
{
	private ManagerConnection managerconnection;

	public HelloEvents() throws IOException
	{
		ManagerConnectionFactory factory = new ManagerConnectionFactory("localhost", "mathias", "12345678");

		this.managerconnection = factory.createManagerConnection();
	}

	public void run() throws IOException, AuthenticationFailedException, TimeoutException, InterruptedException
	{
		// register for events
		managerconnection.addEventListener(this);

		// connect to Asterisk and log in
		managerconnection.login();

		// request channel state
		managerconnection.sendAction(new StatusAction());

		// wait 10 seconds for events to come in
		Thread.sleep(10000);

		// and finally log off and disconnect
		managerconnection.logoff();
	}

	@Override
	public void onManagerEvent(ManagerEvent event)
	{
		// just print received events
		System.out.println(event);
	}

	public static void main(String[] args) throws Exception
	{
		HelloEvents helloEvents;

		helloEvents = new HelloEvents();
		helloEvents.run();
	}

}
