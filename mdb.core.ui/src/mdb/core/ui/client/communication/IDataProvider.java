/**
 * 
 */
package mdb.core.ui.client.communication;

import java.util.HashMap;

import mdb.core.shared.transport.Request;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.events.ICallbackEvent;


/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 *
 */
public interface IDataProvider extends ICommunication<Request>  {	
	
	public HashMap<Integer, IDataSourceFields>  getDataSourceFieldsMap();		

	public HashMap<Integer, String[]> getDataSourceKeysMap();
	
	public void setAfterInvokeEvent(ICallbackEvent<IDataBinder.State> callbackEvent);
	
	public HashMap<Integer, String> getDataMap();

}
