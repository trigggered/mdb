/**
 * 
 */
package mdb.core.ui.client.data.fields.editors;

import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.events.IChangeHandler;


/**
 * @author azhuk
 * Creation date: Feb 14, 2014
 *
 */
public interface ICustomItemEditor {
	void addOnValueChangeEvent(IChangeHandler value  );
	
	IChangeHandler getOnValueChangeEvent();
	
	IMdbField getMdbField();
	void setMdbField( IMdbField value);	
}
