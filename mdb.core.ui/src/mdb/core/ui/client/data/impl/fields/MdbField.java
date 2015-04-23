/**
 * 
 */
package mdb.core.ui.client.data.impl.fields;

import mdb.core.shared.data.EMdbFieldType;
import mdb.core.ui.client.data.impl.fields.editors.ICustomItemEditor;

import com.smartgwt.client.data.DataSourceField;

/**
 * @author azhuk
 * Creation date: Feb 25, 2014
 *
 */
public class MdbField {	
	
	public class LookUpFld {
	
	
		private String _lookUpFldsName;
		private String _masterFldName;		
		
		private String _lookUpKeyValue;
		private String _lookUpFldsValue;
		
		public LookUpFld (String lookUpSrc,String lookUpKey,String lookUpFlds, String masterFldName ) {
			
			_lookUpSrc = lookUpSrc;
			_lookUpKeyName = lookUpKey;
			_lookUpFldsName = lookUpFlds;
			_masterFldName = masterFldName;			
		}
		
		String _lookUpSrc;
		
		String _lookUpKeyName;
		
		
		/**
		 * @return the _lookUpSrc
		 */
		public String get_lookUpSrc() {
			return _lookUpSrc;
		}
		/**
		 * @return the _lookUpKeyName
		 */
		public String getLookUpKeyName() {
			return _lookUpKeyName;
		}
		/**
		 * @return the _lookUpFldsName
		 */
		public String getlookUpFldsName() {
			return _lookUpFldsName;
		}
		/**
		 * @return the _masterFldName
		 */
		public String getMasterFldName() {
			return _masterFldName;
		}
		/**
		 * @param _lookUpSrc the _lookUpSrc to set
		 */
		public void set_lookUpSrc(String _lookUpSrc) {
			this._lookUpSrc = _lookUpSrc;
		}
		/**
		 * @param _lookUpKeyName the _lookUpKeyName to set
		 */
		public void set_lookUpKeyName(String _lookUpKeyName) {
			this._lookUpKeyName = _lookUpKeyName;
		}
		/**
		 * @param _lookUpFldsName the _lookUpFldsName to set
		 */
		public void set_lookUpFldsName(String _lookUpFldsName) {
			this._lookUpFldsName = _lookUpFldsName;
		}
		/**
		 * @param _masterFldName the _masterFldName to set
		 */
		public void set_masterFldName(String _masterFldName) {
			this._masterFldName = _masterFldName;
		}		
		
		/**
		 * @return the _lookUpFldsValue
		 */
		public String getLookUpFldsValue() {
			return _lookUpFldsValue;
		}
		/**
		 * @param _lookUpFldsValue the _lookUpFldsValue to set
		 */
		public void setLookUpFldsValue(String value) {
			this._lookUpFldsValue = value;
		}
		/**
		 * @return the _lookUpKeyValue
		 */
		public String getLookUpKeyValue() {
			return _lookUpKeyValue;
		}
		/**
		 * @param _lookUpKeyValue the _lookUpKeyValue to set
		 */
		public void setLookUpKeyValue(String value) {
			this._lookUpKeyValue = value;
		}
		/**
		 * 
		 */
		public void clear() {
			_lookUpFld.setLookUpKeyValue("-1");
			_lookUpFld.setLookUpFldsValue("");
		}	
	}
	
	 private String _name;	 
	 private DataSourceField  _dsField;
	 private ICustomItemEditor _itemEditor;
	 private EMdbFieldType _mdbFieldType;
	 
	 private LookUpFld _lookUpFld;
	 private boolean _fVisivble;
	 private boolean _fReadOnly;
	 
	public MdbField (String name) {			
		setName(name);						
	}

	public MdbField (DataSourceField  dsField) {			
		setName(dsField.getName());
		_dsField = dsField;
	}
	
	
	public DataSourceField getDataSourceField() {
		return _dsField;
	}

	public void setDataSourceField(DataSourceField _dsField) {
		this._dsField = _dsField;
	}

	public String getName() {
		return _name;
	}
	

	public void setName(String _name) {
		this._name = _name;
	}

	public EMdbFieldType getMdbFieldType() {
		return _mdbFieldType;
	}

	public void setMdbFieldType(EMdbFieldType _mdbFieldType) {
		this._mdbFieldType = _mdbFieldType;
	}

	public LookUpFld getLookUpFld() {
		return _lookUpFld;
	}

	public void setLookUpFld(LookUpFld _lookUpFld) {
		this._lookUpFld = _lookUpFld;
	}

	public ICustomItemEditor getItemEditor() {
		return _itemEditor;
	}

	public void setItemEditor(ICustomItemEditor value) {
		this._itemEditor = value;
		if (_itemEditor != null) {
			_itemEditor.setMdbField(this);
		}
	}

	public boolean isVisivble() {
		return _fVisivble;
	}

	public void setVisivble(boolean _fVisivble) {
		this._fVisivble = _fVisivble;
	}

	public boolean isReadOnly() {
		return _fReadOnly;
	}

	public void setReadOnly(boolean _fReadOnly) {
		this._fReadOnly = _fReadOnly;
	}
	
}
