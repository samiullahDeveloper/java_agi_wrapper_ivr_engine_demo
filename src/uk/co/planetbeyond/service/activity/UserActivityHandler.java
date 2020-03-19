package uk.co.planetbeyond.service.activity;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.planetbeyond.service.generated.UserBean;
import uk.co.planetbeyond.service.generated.exception.DAOException;
import uk.co.planetbeyond.service.manager.UserManagerImpl;
import uk.co.planetbeyond.service.outbound.OutboundIVRBulkSender;

public class UserActivityHandler extends BaseAgiScript
{
	private static Logger log = LoggerFactory.getLogger(OutboundIVRBulkSender.class);

	@Override
	public void service(AgiRequest request, AgiChannel channel) throws AgiException
	{
		String cellno = ((channel.getVariable("DB_CLI") == null) ? "" : channel.getVariable("DB_CLI"));

		String duration = channel.getVariable("DIFF");

		try
		{
			UserBean user = UserManagerImpl.getInstance().loadByPrimaryKey(cellno);

			user.setCallDuration(Integer.parseInt(duration));

			user.update();

		}
		catch (DAOException e)
		{
			e.printStackTrace();
		}
	}
}