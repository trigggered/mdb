/**
 * 
 */
package mdb.ui.client.communication.rpc.regexp;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author azhuk
 * Creation date: Oct 5, 2012
 *
 */
public interface RegExpServiceAsync {	

	void Parse(String value, String paternStr, AsyncCallback<String[]> callback);

}
