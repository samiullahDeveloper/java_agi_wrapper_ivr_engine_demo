package uk.co.planetbeyond.service.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stopper
{
	private static final Logger log = LoggerFactory.getLogger(Stopper.class);

	public static void main(String[] args)
	{
		stop();
	}

	public static void stop()
	{
		try
		{
			// EmailServer.getInstance().stopEmailSendingThread();
			Main.defaultAgiServer.shutdown();
		}
		catch (Exception e)
		{
			log.error(e.toString(), e);
		}
	}
}
