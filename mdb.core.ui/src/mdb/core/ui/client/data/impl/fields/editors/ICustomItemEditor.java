/**
 * 
 */
package mdb.core.ui.client.data.impl.fields.editors;

import mdb.core.ui.client.data.impl.fields.MdbField;
import mdb.core.ui.client.events.IChangeHandler;


/**
 * @author azhuk
 * Creation date: Feb 14, 2014
 *
 */
public interface ICustomItemEditor {
	void addOnValueChangeEvent(IChangeHandler value  );
	MdbField getMdbField();
	void setMdbField( MdbField value);	
}
