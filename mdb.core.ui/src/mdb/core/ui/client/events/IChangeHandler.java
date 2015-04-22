/**
 * 
 */
package mdb.core.ui.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author azhuk
 * Creation date: Feb 21, 2013
 *
 */
public interface IChangeHandler extends EventHandler {
	
	void onChange(IChangeEvent event);

}
