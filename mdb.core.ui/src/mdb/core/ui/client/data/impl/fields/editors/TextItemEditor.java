/**
 * 
 */
package mdb.core.ui.client.data.impl.fields.editors;

import java.util.logging.Logger;

import mdb.core.ui.client.data.impl.fields.MdbField;
import mdb.core.ui.client.events.IChangeHandler;

import com.smartgwt.client.widgets.form.fields.TextItem;

/**
 * @author azhuk
 * Creation date: Jun 26, 2014
 *
 */
public class TextItemEditor extends TextItem implements ICustomItemEditor {
	private static final Logger _logger = Logger.getLogger(TextItemEditor.class
			.getName());

	MdbField _mdbField;
	
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
	public MdbField getMdbField() {		
		return _mdbField;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.impl.fields.editors.ICustomItemEditor#setMdbField(mdb.core.ui.client.data.impl.fields.MdbField)
	 */
	@Override
	public void setMdbField(MdbField value) {
		_mdbField =value;
		
	}
}
