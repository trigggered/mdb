/**
 * 
 */
package mdb.authorization;

import mdb.core.session.User;


/**
 * @author azhuk
 * Creation date: Apr 5, 2013
 *
 */
public interface IAuthorization {

	public User login(String userName, String userPwd);
	public boolean checkAuthorization (String userKey);
	
}
