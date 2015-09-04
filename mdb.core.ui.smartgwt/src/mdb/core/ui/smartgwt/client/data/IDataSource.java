/**
 * 
 */
package mdb.core.ui.smartgwt.client.data;

import java.util.ArrayList;

import mdb.core.ui.client.data.IBaseDataSource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Oct 12, 2012
 *
 */
public interface IDataSource extends IBaseDataSource {
	
	void setFields (ArrayList<DataSourceField> fields);
	
	
	Record[] getData();
	
	DataSource getDataSource();	
	Record[] getRecords();	
	void editRecord (Record record);		
	
}
