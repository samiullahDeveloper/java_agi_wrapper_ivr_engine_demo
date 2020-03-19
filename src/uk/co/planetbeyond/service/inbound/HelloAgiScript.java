package uk.co.planetbeyond.service.inbound;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class HelloAgiScript extends BaseAgiScript
{
	@Override
	public void service(AgiRequest request, AgiChannel channel) throws AgiException
	{
		// Answer the channel...
		answer();

		// ...say hello...
		streamFile("hello-world");

		waitForDigit(5);

		// ...and hangup.
		hangup();
	}
}