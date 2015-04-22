/**
 * 
 */
package mdb.core.ui.client.communication;

import java.util.HashMap;

import mdb.core.shared.transport.Request;
import mdb.core.ui.client.data.bind.IViewDataBinder;
import mdb.core.ui.client.data.impl.fields.DataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 *
 */
public interface IDataProvider extends ICommunication<Request>  {

	public HashMap<Integer, Record[]> getDataMap();
	
	public HashMap<Integer, DataSourceFields>  getDataSourceFieldsMap();		

	public HashMap<Integer, String[]> getDataSourceKeysMap();
	
	public void setAfterInvokeEvent(ICallbackEvent<IViewDataBinder.State> callbackEvent);
	
	/**
	 * @return
	 */
		

}
