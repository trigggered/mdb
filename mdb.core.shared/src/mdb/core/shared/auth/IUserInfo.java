/**
 * 
 */
package mdb.core.shared.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author azhuk
 * Creation date: Dec 23, 2013
 *
 */
public interface IUserInfo extends Serializable{
	
	public int getId();
	public String getName();
	
	public int getDevisionId();
	public String getDevisionName();	
	
	public List<String> getRoles();
	public void setRoles(List<String> value);
	
	public Date getAuthTime();	
	
	public String getSessionID();	
	
	public void setMail(String value);	
	
	public String getMail();
	
	public void setLoginName(String name);
	
	public String getLoginName();
	/**
	 * @return
	 */
	int getChooseApplicationID();	
	/**
	 * @param _chooseApplicationID
	 */
	void setChooseApplicationID(int _chooseApplicationID);
	/**
	 * @param value
	 */
	void setId(int value);	
	
}
