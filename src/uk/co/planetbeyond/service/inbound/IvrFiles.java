package uk.co.planetbeyond.service.inbound;

import java.util.List;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.planetbeyond.service.generated.IvrMenuBean;
import uk.co.planetbeyond.service.manager.IvrMenuManagerImpl;

public class IvrFiles extends BaseAgiScript
{
	private static Logger log = LoggerFactory.getLogger(IvrFiles.class);

	@Override
	public void service(AgiRequest request, AgiChannel channel) throws AgiException
	{
		List<IvrMenuBean> listOfIvrmenus;
		try
		{
			listOfIvrmenus = IvrMenuManagerImpl.getInstance().loadByPreparedStatementAsList("ORDER BY file_name ASC", new Object[] {}, null);

			if (listOfIvrmenus == null)
			{
				log.info("No IVR menu file found.");
				return;
			}

			// Answer the channel...
			answer();

			for (IvrMenuBean menu : listOfIvrmenus)
			{
				String file = menu.getFileName();

				System.out.println("File Name | " + file);

				// playing file using the file name
				streamFile(file);
			}

			// wati for 5 sec
			waitForDigit(5);

			// ...and hangup
			hangup();

			// Class.forName("com.mysql.jdbc.Driver");
			// Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/asterisk_pbx", "root", "root");
			//
			// Statement statement = con.createStatement();
			//
			// ResultSet rs = statement.executeQuery("SELECT * FROM ivr ORDER BY file_name ASC");
			//
			// // Answer the channel...
			// answer();
			//
			// while (rs.next())
			// {
			// String file = rs.getString("file_name");
			//
			// System.out.println("File Name | " + file);
			//
			// // playing file using the file name
			// streamFile(file);
			// }
			//
			// // wati for 5 sec
			// waitForDigit(5);
			//
			// // ...and hangup
			// hangup();
		}
		catch (Throwable e)
		{
			log.error("Error : " + e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
