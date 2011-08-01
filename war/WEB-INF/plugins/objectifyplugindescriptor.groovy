import com.googlecode.objectify.ObjectifyService
import com.googlecode.objectify.helper.DAOBase

/*
 * File Name: objectifyplugindescriptor.groovy
 *
 * Created By: Jeff Schwartz
 * 
 * Date Created: 20100715
 * 
 * Change log:
 * 
 * Remarks:
 *  
 * This script should be placed in the war/WEB-INF/plugins folder.
 * 
 * This script adds the variable 'dao' to gaelyk's binding for templates and groovlets.
 * 
 * The 'dao' variable is a reference to an instance of 'DAO' (data access object) which
 * is derived from com.googlecode.objectify.helper.DAOBase and supports
 * the registering of the POJOs you will use with the objectify service as well
 * as providing easy access to the objectify service itself through 'dao.ofy()'.
 * 
 * Therefore, when your appengine application is loaded your code in your templates and
 * groovlets will have full access to objectify just by referencing 'dao.ofy()'.
 * 
 */

/*
 * DAO - a simple data access object modeled after 
 * the one in objectify's documentation.
 * 
 * Usage: Register your pojos and name your binding variable which 
 * I have defaulted to dao. Feel free to rename this to anything you
 * want. Just change 'dao' below to your preferred variable name.
 * 
 */
public class DAO extends DAOBase
{
	// Register your POJOs here
    static {
		ObjectifyService.register(entity.Development.class);
		ObjectifyService.register(entity.UserInfo.class);
		ObjectifyService.register(entity.Relationship.class);
		ObjectifyService.register(entity.Activity.class);
		ObjectifyService.register(entity.Collaboration.class);
    }
}

/*
 * Add variable dao in the binding.
 * Feel free to rename this variable to anything you want.
 * 
 */
binding {
    // an instance of class DAO from objectify library
    dao = new DAO()
}
