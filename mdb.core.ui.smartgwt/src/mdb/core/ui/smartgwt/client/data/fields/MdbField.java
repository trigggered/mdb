/**
 * 
 */
package mdb.core.ui.smartgwt.client.data.fields;

import mdb.core.shared.data.EMdbFieldType;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.data.fields.LookUpFld;
import mdb.core.ui.client.data.fields.editors.ICustomItemEditor;

import com.smartgwt.client.data.DataSourceField;

/**
 * @author azhuk
 * Creation date: Feb 25, 2014
 *
 */
public class MdbField implements IMdbField {	
	
	private String _name;	 
	 private DataSourceField  _dsField;
	 private ICustomItemEditor _itemEditor;
	 private EMdbFieldType _mdbFieldType;
	 
	 LookUpFld _lookUpFld;
	 private boolean _fVisivble;
	 private boolean _fReadOnly;
	 private boolean _isPrimatyKey;
	private String _title;
	private int _numOder; 
	 
	public MdbField (String name) {			
		setName(name);						
	}

	public MdbField (DataSourceField  dsField) {			
		setName(dsField.getName());
		_dsField = dsField;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#getDataSourceField()
	 */
	
	public DataSourceField getDataSourceField() {
		return _dsField;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setDataSourceField(com.smartgwt.client.data.DataSourceField)
	 */
	
	public void setDataSourceField(DataSourceField _dsField) {
		this._dsField = _dsField;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#getName()
	 */
	@Override
	public String getName() {
		return _name;
	}
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setName(java.lang.String)
	 */
	@Override
	public void setName(String _name) {
		this._name = _name;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#getMdbFieldType()
	 */
	@Override
	public EMdbFieldType getMdbFieldType() {
		return _mdbFieldType;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setMdbFieldType(mdb.core.shared.data.EMdbFieldType)
	 */
	@Override
	public void setMdbFieldType(EMdbFieldType _mdbFieldType) {
		this._mdbFieldType = _mdbFieldType;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#getLookUpFld()
	 */
	@Override
	public LookUpFld getLookUpFld() {
		return _lookUpFld;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setLookUpFld(mdb.core.ui.smartgwt.data.fields.MdbField.LookUpFld)
	 */
	@Override
	public void setLookUpFld(LookUpFld _lookUpFld) {
		this._lookUpFld = _lookUpFld;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#getItemEditor()
	 */
	@Override
	public ICustomItemEditor getItemEditor() {
		return _itemEditor;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setItemEditor(mdb.core.ui.smartgwt.data.fields.editors.ICustomItemEditor)
	 */
	@Override
	public void setItemEditor(ICustomItemEditor value) {
		this._itemEditor = value;
		if (_itemEditor != null) {
			_itemEditor.setMdbField(this);
		}
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#isVisivble()
	 */
	@Override
	public boolean isVisivble() {
		return _fVisivble;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setVisivble(boolean)
	 */
	@Override
	public void setVisivble(boolean _fVisivble) {
		this._fVisivble = _fVisivble;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return _fReadOnly;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean _fReadOnly) {
		this._fReadOnly = _fReadOnly;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.fields.IMdbField#isPrimaryKey()
	 */
	@Override
	public boolean isPrimaryKey() {
		return _isPrimatyKey ;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.fields.IMdbField#setPrimaryKey(boolean)
	 */
	@Override
	public void setPrimaryKey(boolean value) {
		_isPrimatyKey = value;		
	}
	

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public void setTitle(String value) {
		_title = value;		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.fields.IMdbField#setNumOrder(int)
	 */
	@Override
	public void setPosition(int value) {
		_numOder = value;		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.fields.IMdbField#getNumOrder()
	 */
	@Override
	public int getPosition() {		
		return _numOder;
	}
	
}
