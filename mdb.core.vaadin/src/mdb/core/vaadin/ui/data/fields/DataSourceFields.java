/**
 * 
 */
package mdb.core.vaadin.ui.data.fields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.vaadin.ui.Table.ColumnGenerator;

import mdb.core.shared.utils.comparator.ComparatorByPosition;
import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.data.fields.IMdbField;


/**
 * @author azhuk
 * Creation date: Feb 14, 2014
 *
 */
public class DataSourceFields  implements IDataSourceFields {
	
	private int _entityId;	
	
	private Map<String, IMdbField> _mdbFieldMap = new TreeMap<String, IMdbField>();	
	private ArrayList<ColumnGenerator> _dataSourceFieldList = new ArrayList<ColumnGenerator>();
	
	
	public DataSourceFields (int entityId) {
		_entityId = entityId;		
	}
	
	
	
	/*public ArrayList<DataSourceField> getDataSourceFields () {				
    	return _dataSourceFieldList;    	
    }
*/
		
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
