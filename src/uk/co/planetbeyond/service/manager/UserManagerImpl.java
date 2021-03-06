




// ______________________________________________________
// Generated by sql2java - http://sql2java.sourceforge.net/
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
//
// Author: Javed Kansi
// ______________________________________________________

package uk.co.planetbeyond.service.manager;

import uk.co.planetbeyond.service.generated.UserManager;
import uk.co.planetbeyond.service.generated.UserBean;
import uk.co.planetbeyond.service.managedbean.UserManagedBean;

/**
* If any change needs to be made to the UserManager, it MUST not be made directly
* Instead the UserManagerImpl class should be used to make the changes
*/
public class UserManagerImpl extends UserManager 
{
	/**
	* Singleton instance of this class
	*/
	private static UserManagerImpl singleton = new UserManagerImpl();
	
	/**
	* Make the constructor private to make the class singleton
	*/
	private UserManagerImpl()
	{
		super();
		
		// set the singleton instance of UserManagerImpl class in the UserManager so that
		// UserManager.getInstance() also points to the singleton instance of UserManagerImpl 
		setInstance(this);
	}
	
	/**
	* To make the singleton instance available to external classes
	*/
	public static UserManagerImpl getInstance()
	{
		return singleton;
	}

	/**
	* Override this method so that it returns an instance of UserManagedBean instead of UserBean
	*/
	@Override
	public UserBean createUserBean()
	{
		return new UserManagedBean();
	}   
}
