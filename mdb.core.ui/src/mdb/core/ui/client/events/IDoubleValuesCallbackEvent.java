/**
 * 
 */
package mdb.core.ui.client.events;


/**
 * @author azhuk
 * Creation date: Sep 8, 2012
 *
 */
public interface IDoubleValuesCallbackEvent<E,V> {
	
	public void execute (E value1, V value2);	
	
}
