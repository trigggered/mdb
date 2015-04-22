/**
 * 
 */
package mdb.core.ui.client.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * @author azhuk
 * Creation date: Feb 21, 2013
 *
 */
public interface IHasChangeHandlers extends HasHandlers  {
	
	HandlerRegistration addChangeHandler(IChangeHandler handler);

}
