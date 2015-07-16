/**
 * 
 */
package mdb.core.ui.client.view.dialogs.input;

import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;

/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public interface IInputDialogs {

	/**
	 * @param value
	 * @param selectedRecordJSON
	 * @param iCallbackEvent
	 */
	void show(IDataSourceFields value, String selectedRecordJSON,
			ICallbackEvent<String> iCallbackEvent);

}
