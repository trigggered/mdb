/**
 * 
 */
package mdb.authorization.impl;

import java.util.logging.Logger;

import javax.jws.WebService;

import mdb.core.session.User;

/**
 * @author azhuk
 * Creation date: Apr 5, 2013
 *
 */
@WebService
public class AutorizationWS  {
	
	private static final Logger _logger = Logger.getLogger(AutorizationWS.class
			.getCanonicalName());

	
	public User login(String userName, String userPwd) {
		_logger.info("Try Autorizatio user"+userName);
		return null;
	}
	
}
