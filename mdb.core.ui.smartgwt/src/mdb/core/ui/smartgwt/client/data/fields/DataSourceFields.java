/**
 * 
 */
package mdb.core.ui.smartgwt.client.data.fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.data.fields.IMdbField;

import com.smartgwt.client.data.DataSourceField;

/**
 * @author azhuk
 * Creation date: Feb 14, 2014
 *
 */
public class DataSourceFields  implements IDataSourceFields {
	
	private int _entityId;	
	
	public DataSourceFields (int entityId) {
		_entityId = entityId;
	}
	
	private Map<String, IMdbField> _mdbFieldMap = new HashMap<String, IMdbField>();	
	private ArrayList<DataSourceField> _dataSourceFieldList = new ArrayList<DataSourceField>();
	
	
	public ArrayList<DataSourceField> getDataSourceFields () {				
    	return _dataSourceFieldList;    	
    }

		
	public void addField(IMdbField value) {		
		_mdbFieldMap.put(value.getName(), value);
		_dataSourceFieldList.add( ((MdbField)value).getDataSourceField());		
	}

	public int getEntityId() {
		return _entityId;
	}

	
	public Map<String, IMdbField>  getMdbFieldMap() {
		return _mdbFieldMap;
	}
	
	public int count() {		
		return _mdbFieldMap.size();
	}
	
}
