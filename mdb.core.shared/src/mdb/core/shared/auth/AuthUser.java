/**
 * 
 */
package mdb.core.shared.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author azhuk
 * Creation date: Dec 23, 2013
 *
 */
public  class AuthUser implements IUserInfo, java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger _logger = Logger.getLogger(AuthUser.class
			.getName());
	
	private String _name;
	private String _mail;
	private String _loginName;
	private List<String> _roles = new ArrayList<String>();
	private int _id;
	private String _sessionId;
	private Date _authTime;
	private String _devisionName;
	private int _devisionId;

	public static AuthUser copyFrom (IUserInfo info) {
		AuthUser toReturn = new AuthUser();
		
		toReturn.setId(info.getId());
		toReturn.setName(info.getName());
		toReturn.setSessionID(info.getSessionID());
		toReturn._authTime = info.getAuthTime();
		toReturn._devisionId = info.getDevisionId();
		toReturn._devisionName = info.getDevisionName();
		
		toReturn._roles = info.getRoles();		
		return toReturn;
	}
	
	public AuthUser () {
		
	}
	
	public AuthUser(String name) {
	  setName(name);
	  
	}
	
	@Override
	public String getName() {
		return _name;
	}

	public void setName(String value) {
		_name = value;
	}


	
	
	/* (non-Javadoc)
	 * @see mdb.core.shared.auth.IUserInfo#getAuthTime()
	 */
	@Override
	public Date getAuthTime() {		
		return _authTime;
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.auth.IUserInfo#getId()
	 */
	@Override
	public int getId() {
		return _id;		
	}
	
	public int setId(int value) {
		return _id = value;
	}
	
	public String toString() {
		return "User info: ID="+getId()+" Name="+getName()+ " SessionId="+getSessionID() ;
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.auth.IUserInfo#getSessionID()
	 */
	@Override
	public String getSessionID() {
		return _sessionId;
	}
	
	public void setSessionID(String value) {
		_sessionId = value;
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.auth.IUserInfo#getDevisionName()
	 */
	@Override
	public String getDevisionName() {
		return _devisionName;
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.auth.IUserInfo#getDevisionId()
	 */
	@Override
	public int getDevisionId() {		
		return _devisionId;
	}

	/**
	 * @param attribute
	 */
	public void setMail(String value) {
		_mail = value;		
	}
	
	public String getMail() {
		return _mail;
	}

	/**
	 * @param name
	 */
	public void setLoginName(String name) {
		_loginName = name;		
	}
	
	public String getLoginName() {
		return _loginName ;
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.auth.IUserInfo#setRoles(java.util.List)
	 */
	@Override
	public void setRoles(List<String> value) {
		_roles = value;		
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.shared.auth.IUserInfo#getRole()
	 */
	@Override
	public List<String> getRoles() {
		return _roles;
	}
	
	
	
}
