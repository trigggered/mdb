/**
 * 
 */
package mdb.core.shared.data;

import java.util.LinkedHashMap;

/**
 * @author azhuk
 * Creation date: Jan 30, 2014
 *
 */
public enum EMdbBooleanType {
	True (1),
	False(0);
	
	private int _value;
	
	public int getValue() {
	    return _value;
   }
	
	private EMdbBooleanType (int value) {
		_value = value;
	}
	
	public EMdbBooleanType fromInt(int value) {
		
		switch (value ) {
			case 1: return True;		
			case 0: return False;
			default:
				return  False;
		}
	}
	
	public String toString() {
	  switch (_value) {
		case 1: return "Да";					
		default:
			return "Нет";
		}		 		 
	}
	
	public static LinkedHashMap<String, String>  getMap() {
		return new LinkedHashMap<String, String>() {			
			private static final long serialVersionUID = 1L;

			{
	            put(String.valueOf(True.getValue()), True.toString());
	            put(String.valueOf(False.getValue()), False.toString());
	        };
		};
	}
}
