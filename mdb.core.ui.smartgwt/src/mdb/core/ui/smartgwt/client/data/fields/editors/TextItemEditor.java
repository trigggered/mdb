/**
 * 
 */
package mdb.core.ui.smartgwt.client.data.fields.editors;


import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.data.fields.editors.ICustomItemEditor;
import mdb.core.ui.client.events.IChangeHandler;

import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * @author azhuk
 * Creation date: Jun 26, 2014
 *
 */
public class TextItemEditor extends TextItem implements ICustomItemEditor {
	IMdbField _mdbField;
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.impl.fields.editors.ICustomItemEditor#addOnValueChangeEvent(mdb.core.ui.client.events.IChangeHandler)
	 */
	@Override
	public void addOnValueChangeEvent(IChangeHandler value) {
		// TODO Auto-generated method stub
	
		
	}
	
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.impl.fields.editors.ICustomItemEditor#getMdbField()
	 */
	@Override
	public IMdbField getMdbField() {		
		return _mdbField;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.impl.fields.editors.ICustomItemEditor#setMdbField(mdb.core.ui.client.data.impl.fields.MdbField)
	 */
	@Override
	public void setMdbField(IMdbField value) {
		_mdbField =value;
		
	}



	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.impl.fields.editors.ICustomItemEditor#getOnValueChangeEvent()
	 */
	@Override
	public IChangeHandler getOnValueChangeEvent() {
		// TODO Auto-generated method stub
		return null;
	}
}
