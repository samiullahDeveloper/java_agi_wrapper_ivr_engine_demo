package uk.co.planetbeyond.service.main;

import java.io.IOException;

import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{
	private static Logger log = LoggerFactory.getLogger(Main.class);

	static DefaultAgiServer defaultAgiServer = null;

	public static void main(String[] args) throws IOException, AuthenticationFailedException, TimeoutException, AgiException
	{
		// DefaultAgiServer server = null;

		try
		{

			// start email sending server
			// EmailServer.getInstance().startEmailSendingThread();

			// UserActivityHandler handler = new UserActivityHandler();
			// handler.run();

			// OutboundIVRBulkSender bulk = new OutboundIVRBulkSender();
			// bulk.run();

			// HelloManager helloManager = new HelloManager();
			// helloManager.run();

			defaultAgiServer = new DefaultAgiServer();
			defaultAgiServer.startup();
		}
		catch (Throwable e)
		{
			log.error(e.getMessage(), e);
		}
		// finally
		// {
		// server.shutdown();
		// }
	}
}
