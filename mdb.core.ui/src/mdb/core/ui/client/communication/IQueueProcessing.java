/**
 * 
 */
package mdb.core.ui.client.communication;



import com.google.gwt.user.client.rpc.AsyncCallback;




/**
 * @author azhuk
 * Creation date: Feb 1, 2013
 *
 */
public interface IQueueProcessing<T, E> extends IProcessing {
	public void setQueue (IQueue<T, E> value);
	public void call (E value, AsyncCallback<E> asyncCallback);
}
