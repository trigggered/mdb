/**
 * 
 */
package mdb.core.ui.client.data.impl.fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DataSourceField;

/**
 * @author azhuk
 * Creation date: Feb 14, 2014
 *
 */
public class DataSourceFields {
	
	private int _entityId;	
	
	public DataSourceFields (int entityId) {
		_entityId = entityId;
	}
	
	private Map<String, MdbField> _mdbFieldMap = new HashMap<String, MdbField>();	
	private ArrayList<DataSourceField> _dataSourceFieldList = new ArrayList<DataSourceField>();
	
	
	public ArrayList<DataSourceField> getDataSourceFields () {				
    	return _dataSourceFieldList;    	
    }

		
	public void addField(MdbField value) {		
		_mdbFieldMap.put(value.getName(), value);
		_dataSourceFieldList.add(value.getDataSourceField());		
	}

	public int getEntityId() {
		return _entityId;
	}

	
	public Map<String, MdbField>  getMdbFieldMap() {
		return _mdbFieldMap;
	}
	
	public int count() {
		return _dataSourceFieldList.size();
	}
	
}
