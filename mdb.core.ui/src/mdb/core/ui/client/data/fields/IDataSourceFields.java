/**
 * 
 */
package mdb.core.ui.client.data.fields;

import java.util.Map;



/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public interface IDataSourceFields {
		
	public void addField(IMdbField value);		

	public int getEntityId();
	
	public Map<String, IMdbField>  getMdbFieldMap();
	
	public int count();
}
