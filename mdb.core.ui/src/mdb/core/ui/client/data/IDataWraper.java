/**
 * 
 */
package mdb.core.ui.client.data;

import java.util.Iterator;
import java.util.Map;

/**
 * @author azhuk
 * Creation date: Jul 21, 2015
 *
 */
public interface IDataWraper extends Iterator<Map<String,String>>{
	
	
	void setData(String jsonString);		 
	/**
	 * @return
	 */
	boolean isDataExists();
	
	
}
