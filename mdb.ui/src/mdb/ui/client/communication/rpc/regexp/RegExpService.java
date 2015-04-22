/**
 * 
 */
package mdb.ui.client.communication.rpc.regexp;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author azhuk
 * Creation date: Oct 5, 2012
 *
 */
@RemoteServiceRelativePath("regexpservice")
public interface RegExpService  extends RemoteService {
	
	String[] Parse (String value, String paternStr);

}
