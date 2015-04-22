/**
 * 
 */
package mdb.core.ui.client.data;

import java.util.ArrayList;

import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.data.bind.IViewDataBinder;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;

/**
 * @author azhuk
 * Creation date: Oct 12, 2012
 *
 */
public interface IDataSource {
	
	void refresh();
	void save();
	
	void setViewDataBinder (IViewDataBinder viewDataBinder);	
	IViewDataBinder getViewDataBinder();
	void setData(Record[] dataList);
	Record[] getData();
	
	void setFields (ArrayList<DataSourceField> fields);
	DataSource getDataSource();
	int getEntityId();
	Record[] getRecords();			
	RequestEntity getRequestEntity();
	
	boolean isPagingUsage();
	boolean isHasChanges(); 
	void callRemoteCommunication();
	void editRecord (Record record);
	void setDataComponent(IDataComponent dataComponent);
	IDataComponent getDataComponent();
	
	/**
	 * @param dataForm
	 */
	void bindToDataComponent(DynamicForm dataForm);
	/**
	 * @param dataForm
	 * @param isCanEdit
	 */
	void bindToDataComponent(DynamicForm dataForm, boolean isCanEdit);
	
	/**
	 * @param grid
	 */
	void bindToDataComponent(ListGrid grid);
	/**
	 * @param value
	 */
	void setLocalKeyGen(boolean value);
	/**
	 * @return
	 */
	boolean isLocalKeyGen();
	
	
}
