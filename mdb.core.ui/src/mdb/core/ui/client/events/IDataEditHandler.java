/**
 * 
 */
package mdb.core.ui.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Feb 5, 2014
 *
 */
public interface IDataEditHandler extends EventHandler {
	void onEdit(Record record);
}
