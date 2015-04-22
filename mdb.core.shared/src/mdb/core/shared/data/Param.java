package mdb.core.shared.data;

public class Param   implements  java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String  _Name;
	private String  _Value;
	private int 	_Type;
	private int 	_InOut;
	private int 	_SqlIndex;	
	
	public Param (String aName, String aValue, int aType, int anInOut) {
		_Name  = aName;
		_Value = aValue;
		_Type  = aType;	
		_InOut  = anInOut;
	}
	
	public Param (){
		
	}
	
	
	/**
	 * @return Returns the _Name.
	 */
	public String getName() {
		return _Name;
	}
	/**
	 * @param name The _Name to set.
	 */
	public void setName(String name) {
		_Name = name;
	}
	/**
	 * @return Returns the _Type.
	 */
	public int getType() {
		return _Type;
	}
	/**
	 * @param type The _Type to set.
	 */
	public void setType(int type) {
		_Type = type;
	}
	/**
	 * @return Returns the _Value.
	 */
	public String getValue() {
		return _Value;
	}
	/**
	 * @param value The _Value to set.
	 */
	public void setValue(String value) {
		_Value = value;
	}



	public int getInOut() {
		return _InOut;
	}



	public void setInOut(int inOut) {
		_InOut = inOut;
	}


	public int getSqlIndex() {
		return _SqlIndex;
	}


	public void setSqlIndex(int sqlIndex) {
		_SqlIndex = sqlIndex;
	}
	
	
}