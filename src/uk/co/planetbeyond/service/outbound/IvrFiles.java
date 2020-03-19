package uk.co.planetbeyond.service.outbound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class IvrFiles extends BaseAgiScript
{
	@Override
	public void service(AgiRequest request, AgiChannel channel) throws AgiException
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/asterisk_pbx", "root", "root");

			Statement statement = con.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM ivr ORDER BY file_name ASC");

			// Answer the channel...
			answer();

			while (rs.next())
			{
				String file = rs.getString("file_name");

				System.out.println("File Name | " + file);

				// playing file using the file name
				streamFile(file);
			}

			// wati for 5 sec
			waitForDigit(5);

			// ...and hangup
			hangup();

		}
		catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	// public static void main(String[] args) throws AgiException
	// {
	// IvrFiles files = new IvrFiles();
	//
	// files.service(null, null);
	// }
}
