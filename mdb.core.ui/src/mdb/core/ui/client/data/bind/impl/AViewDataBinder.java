/**
 * 
 */
package mdb.core.ui.client.data.bind.impl;

import java.util.Map.Entry;
import java.util.logging.Logger;

import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.bind.IBindDataSource;
import mdb.core.ui.client.data.bind.IBindSource;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.data.bind.impl.DataBinder;


/**
 * @author azhuk
 * Creation date: Oct 8, 2012
 *
 */
public abstract class AViewDataBinder extends DataBinder {		

	/**
	 * @param bindSource
	 * @param dataProvider
	 */
	public AViewDataBinder(IBindSource bindSource, IDataProvider dataProvider) {
		super(bindSource, dataProvider);
	}

	private static final Logger _logger = Logger.getLogger(AViewDataBinder.class.getName());
	
	protected  abstract IBaseDataSource createDataSource(int entityId, IDataBinder viewDataBinder);
	
    @Override
	public  void  bind(State value) {		
    	
    	try {
    	
				if (getBindSource() != null && getDataProvider() != null) {				
					   
					_logger.info("Start  bind data to the view:" +getBindSource().getClass().getName());					
						
							if (getBindSource() != null ) {
								
								
								IBindDataSource source =(IBindDataSource) getBindSource();					
								
								
								if  ( value == State.bindAllData) {
									for (Entry<Integer,String>  entry :getDataProvider().getDataMap().entrySet() ) {	
										
										IBaseDataSource ds = null;
										
										if ( source.getDataSources().containsKey(entry.getKey()) ) {
											ds = source.getDataSources().get(entry.getKey());
											
											ds.setData(entry.getValue());
										}
										else {								
											ds =createDataSource (entry.getKey(),this);											
										}
										
										source.getDataSources().put(entry.getKey(), ds);
									}
								}
								
								switch ( value ) {
								case bindAllData:
									try {
										source.bindDataComponents();
									} catch (DataBindException e) {
										_logger.severe(e.getMessage());
										
									}
									break;
									
								case bindChangeData:
									source.bindDataComponentsAfterChange();							
									break;
								/*case  notBind:
									break;
								*/	
								default:
									try {
										source.bindDataComponents();
									} catch (DataBindException e) {
										_logger.severe(e.getMessage());
									}
									break;
								}
							
						}
						_logger.info("Success bind data to the view:" +getBindSource().getClass().getName());
				}else {
					_logger.info("Not exist BindSource or DataProvider!! ");
				}
		
    	} 
    	catch (Exception e) {
    		_logger.severe(e.getMessage());
    	}
		
		
	 }
	
}