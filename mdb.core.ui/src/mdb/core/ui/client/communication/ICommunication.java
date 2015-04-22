/**
 * 
 */
package mdb.core.ui.client.communication;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 * @param <T>
 * RPC Possibility 
 */
public interface ICommunication<T>  extends AsyncCallback<T>  {
	
	public T 		  getRequest();
	public T 		  getResponse();
}
