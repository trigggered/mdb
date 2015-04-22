/**
 * 
 */
package mdb.core.shared.utils;


/**
 * @author azhuk
 * Creation date: Jan 11, 2013
 *
 */
public class DataConverter {
	
	public DataConverter() {
		
	}

	
	public static boolean isInteger (String value){
		try {
			Integer.parseInt(value);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
}
