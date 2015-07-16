/**
 * 
 */
package mdb.core.ui.client.data.fields;



public class LookUpFld {


	/**
	 * 
	 */
	private final IMdbField _lookUpFld;
	private String _lookUpFldsName;
	private String _masterFldName;		
	
	private String _lookUpKeyValue;
	private String _lookUpFldsValue;
	
	public LookUpFld (IMdbField mdbField, String lookUpSrc,String lookUpKey,String lookUpFlds, String masterFldName ) {
		
		_lookUpFld = mdbField;
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
		setLookUpKeyValue("-1");
		setLookUpFldsValue("");
	}	
}