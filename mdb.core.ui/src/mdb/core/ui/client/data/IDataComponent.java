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
	
	void setDataSource(IBaseDataSource value );
	
	void setDataSource(IBaseDataSource value, boolean isCanEdit);
	
	IBaseDataSource getDataSource();	
	/**
	 * @param value
	 * @param isCanEdit
	 */
		
	/**
	 * @param masterFldName
	 * @param lookUpKeyValue
	 */
	public void setFieldValue(String fieldName, String value);
	
}
