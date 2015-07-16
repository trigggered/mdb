/**
 * 
 */
package mdb.core.vaadin.ui.data.fields;

import mdb.core.shared.data.EMdbFieldType;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.data.fields.LookUpFld;
import mdb.core.ui.client.data.fields.editors.ICustomItemEditor;
import mdb.core.vaadin.ui.data.fields.ColumnGenerators.BaseColumnGenerator;

import com.vaadin.ui.Table.ColumnGenerator;

/**
 * @author azhuk
 * Creation date: Feb 25, 2014
 *
 */
public class MdbField implements IMdbField {	
	
		private String _name;	 
		private String _title;
		private IColumnGenerator  _dsField;
		private ICustomItemEditor _itemEditor;
		private EMdbFieldType _mdbFieldType;
	 
	 
		LookUpFld _lookUpFld;
		private boolean _fVisivble;
		private boolean _fReadOnly;
	 
		private Class<?> _propertyType;
		private boolean _isPrimaryKey;
		private int _numOrder;
		
	/**
		 * @return the _isPk
		 */
		public boolean isPrimaryKey() {
			return _isPrimaryKey;
		}

		/**
		 * @param _isPk the _isPk to set
		 */
		public void setPrimaryKey(boolean value) {
			this._isPrimaryKey = value;
		}

	public MdbField (String name) {			
		setName(name);						
	}

	public MdbField (BaseColumnGenerator  columnGeberator) {
		setDataSourceField(columnGeberator);
		setName(columnGeberator.getContainerPropertyName());
		setPropertyType(columnGeberator.getContainerPropertyType());		
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#getDataSourceField()
	 */
	
	public IColumnGenerator getDataSourceField() {
		return _dsField;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.smartgwt.data.fields.IMdbField#setDataSourceField(com.smartgwt.client.data.DataSourceField)
	 */
	
	public void setDataSourceField(IColumnGenerator _dsField) {
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

	/**
	 * @return the _propertyType
	 */
	public Class<?> getPropertyType() {
		return _propertyType;
	}

	/**
	 * @param _propertyType the _propertyType to set
	 */
	public void setPropertyType(Class<?> _propertyType) {
		this._propertyType = _propertyType;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.fields.IMdbField#getTitle()
	 */
	@Override
	public String getTitle() {
		return _title;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.fields.IMdbField#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String value) {
		_title = value;		
	}

	
	@Override
	public void setPosition (int value) {
		_numOrder = value;		
	}
	
	@Override
	public int getPosition() {
		return _numOrder;		
	}
}
