/**
 * 
 */
package mdb.core.ui.client.communication;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mdb.core.shared.transport.Request;



/**
 * @author azhuk
 * Creation date: Feb 1, 2013
 *
 */
public interface IQueue<T, E> {

	public enum EQueueState {
		Waiting,
		Processing
	}
	
	
	public void put(T value);	
	public void clear ();
	public HashMap<Integer, T> getContainer();
	
	public void setProcessor ( IQueueProcessing<T,E> value);
	public IQueueProcessing<T, E> getProcessor();  
	
	public Entry<Integer, T>  next();
	/**
	 * @return
	 */
	List<Request> toValueList();
	public void beginProcess();
	public void endProcess();
	public  void printQueue();
	public    String   getQueueData (List<Request> values);
	public EQueueState getState();
}
