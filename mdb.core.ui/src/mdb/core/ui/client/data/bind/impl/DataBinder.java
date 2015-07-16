package mdb.core.ui.client.data.bind.impl;

import java.util.logging.Logger;

import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.bind.IBindSource;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.events.ICallbackEvent;

public class DataBinder implements IDataBinder, ICallbackEvent<IDataBinder.State> {

	private static final Logger _logger = Logger.getLogger(DataBinder.class.getName());
	private IBindSource _bindSource;	
	private IDataProvider _dataProvider;
	
		
	public DataBinder ( IBindSource bindSource, IDataProvider dataProvider) {        
    	_bindSource =  bindSource;  	
    	registerDataProvider (dataProvider);
    }          

	public void registerDataProvider (IDataProvider dataProvider){
		_dataProvider = dataProvider;
    	_dataProvider.setAfterInvokeEvent(this);
    	_bindSource.setDataProvider(dataProvider);
	}
	
	@Override
	public IBindSource getBindSource() {
		return _bindSource;
	}

	@Override
	public void setBindSource(IBindSource value) {
		_bindSource = value;		
	}

	@Override
	public IDataProvider getDataProvider() {
		return _dataProvider;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.events.ICallbackEvent#doWork(java.lang.Object)
	 */
	@Override
	public void doWork(IDataBinder.State state) {
		
		bind(state);
		
	}

	
	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IViewDataBinder#bind(mdb.core.ui.data.bind.IViewDataBinder.State)
	 */
	@Override
	public void bind(IDataBinder.State  value) {
		
			switch(value) {
			case notBind:
				break;
			case bindAllData:
				try {
					getBindSource().bindDataComponents();
				} catch (DataBindException e) {
					_logger.severe(e.getMessage());
				}
				break;
			case bindChangeData:
				getBindSource().bindDataComponentsAfterChange();
				break;
			}		
		
	}

}
