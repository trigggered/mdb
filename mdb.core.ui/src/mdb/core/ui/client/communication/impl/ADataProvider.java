/**
 * 
 */
package mdb.core.ui.client.communication.impl;

import java.util.HashMap;
import java.util.logging.Logger;

import mdb.core.shared.transport.Request;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.data.impl.fields.DataSourceFields;

/**
 * @author azhuk
 * Creation date: Jan 30, 2013
 *
 */
public abstract class  ADataProvider implements IDataProvider  {
	
	 private static final Logger _logger = Logger.getLogger(ADataProvider.class.getName());	
	 
	 public HashMap<Integer, DataSourceFields>  	_dataSourceFieldsMap 	= new HashMap<Integer, DataSourceFields>();
	 public HashMap<Integer, String[]>  			_dataSourceKeysMap 		= new HashMap<Integer, String[]>();  	 
	 
	 private Request _request = new Request();
	 private Request _response; 		
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.ICommunication#getRequest()
	 */
	@Override
	public Request getRequest() {
		return _request;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.communications.ICommunication#getResponse()
	 */
	@Override
	public Request getResponse() {
		return _response;
	}


	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	@Override
	public void onFailure(Throwable caught) {
		_logger.severe("Failure RPC response");		
	}


	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
	 */
	@Override
	public void onSuccess(Request result) {
		_logger.info("Success RPC response");		
		_response = result;
		afterInvoke();		
	}
	
	protected void afterInvoke() {
		analizeResponse();
	}
	
	protected abstract void analizeResponse();


	@Override
	public HashMap<Integer, DataSourceFields>  getDataSourceFieldsMap() {
		return _dataSourceFieldsMap;
	}
	
	@Override
	public HashMap<Integer, String[]> getDataSourceKeysMap() {
		return _dataSourceKeysMap;
	}
	
}	