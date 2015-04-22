/**
 * 
 */
package mdb.core.shared.data;


/**
 * @author azhuk
 * Creation date: Aug 9, 2012
 *
 */
public class Field implements  java.io.Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EMdbFieldType _mdbFieldType;
	private String _name;	
	private String _caption;
	
	private int _precision;
	private int _scale;	
	private int _displaySize;
	
	private int _sqlType;
	private String _sqlTypeName; 
	
	
	
	/**
	 * @return the _columnName
	 */
	public String getColumnName() {
		return _name;
	}
	/**
	 * @param _name the _columnName to set
	 */
	public void setColumnName(String columnName) {
		this._name = columnName;
	}
	/**
	 * @return the _columnLabel
	 */
	public String getCaption() {
		return _caption;
	}
	/**
	 * @param _caption the _columnLabel to set
	 */
	public void setCaption(String columnLabel) {
		this._caption = columnLabel;
	}
	/**
	 * @return the _precision
	 */
	public int getPrecision() {
		return _precision;
	}
	/**
	 * @param _precision the _precision to set
	 */
	public void setPrecision(int precision) {
		this._precision = precision;
	}
	/**
	 * @return the _scale
	 */
	public int getScale() {
		return _scale;
	}
	/**
	 * @param _scale the _scale to set
	 */
	public void setScale(int scale) {
		this._scale = scale;
	}
	/**
	 * @return the _columnDisplaySize
	 */
	public int getDisplaySize() {
		return _displaySize;
	}
	/**
	 * @param _displaySize the _columnDisplaySize to set
	 */
	public void setDisplaySize(int columnDisplaySize) {
		this._displaySize = columnDisplaySize;
	}
	/**
	 * @return the _sqlType
	 */
	public int getSqlType() {
		return _sqlType;
	}
	/**
	 * @param _sqlType the _columnType to set
	 */
	public void setSqlType(int columnType) {
		this._sqlType = columnType;
	}
	/**
	 * @return the _columnTypeName
	 */
	public String getSqlTypeName() {
		return _sqlTypeName;
	}
	/**
	 * @param _sqlTypeName the _columnTypeName to set
	 */
	public void setSQlTypeName(String columnTypeName) {
		this._sqlTypeName = columnTypeName;
	}
	
	public EMdbFieldType getMdbFieldType() {
		return _mdbFieldType;
	}
	
	public void setMdbFieldType(EMdbFieldType  value) {
		_mdbFieldType = value;
	}
	
	
}
