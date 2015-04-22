/**
 * 
 */
package mdb.core.ui.client.data;



/**
 * @author azhuk
 * Creation date: Mar 5, 2014
 *
 */
public interface IDataComponent {
	
	void setDataSource(IDataSource value );
	IDataSource getDataSource();	
	/**
	 * @param value
	 * @param isCanEdit
	 */
	void setDataSource(IDataSource value, boolean isCanEdit);	
	/**
	 * @param masterFldName
	 * @param lookUpKeyValue
	 */
	public void setFieldValue(String fieldName, String value);
	
}
