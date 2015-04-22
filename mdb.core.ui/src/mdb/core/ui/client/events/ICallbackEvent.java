/**
 * 
 */
package mdb.core.ui.client.events;


/**
 * @author azhuk
 * Creation date: Sep 8, 2012
 *
 */
public interface ICallbackEvent<E> {
	
	public void doWork (E data);	
	
}
