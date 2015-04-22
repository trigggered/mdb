/**
 * 
 */
package mdb.core.ui.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author azhuk
 * Creation date: Oct 5, 2012
 *
 */
public class RemoteServiceImpl extends RemoteServiceServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static 	String SESSION_KEY = "SESSION_KEY"; 

	/**
	  * Returns the current session
	  * @return  The current Session
	  */
	  public HttpSession getSession() {
	        // Get the current request and then return its session
	        return this.getThreadLocalRequest().getSession();
	  }
  
	   
}
