/**
 * 
 */
package mdb.core.ui.client.data;

import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.data.fields.IDataSourceFields;

/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public interface IBaseDataSource {

	void refresh();
	void save();
	
	void setData(String jsonString);
	
	void setViewDataBinder (IDataBinder viewDataBinder);	
	IDataBinder getViewDataBinder();		
	
	int getEntityId();
	
	
	
	RequestEntity getRequestEntity();
	
	boolean isPagingUsage();
	boolean isHasChanges(); 
	void callRemoteCommunication();
	
	void setDataComponent(IDataComponent dataComponent);
	
	IDataComponent getDataComponent();
	
	
	void setLocalKeyGen(boolean value);
	
	boolean isLocalKeyGen();
	

	/**
	 * 
	 */
	void initFieldToDefValue();

	/**
	 * @return
	 */	
	
	void updateData(String jsonString);
	
	void setDataSourceFields(IDataSourceFields fields);
	
	IDataSourceFields getDataSourceFields();
	
	boolean isHasData();
}