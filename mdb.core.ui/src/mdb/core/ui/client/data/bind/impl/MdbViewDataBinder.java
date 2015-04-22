/**
 * 
 */
package mdb.core.ui.client.data.bind.impl;

import java.util.Map.Entry;
import java.util.logging.Logger;

import mdb.core.ui.client.communication.impl.MdbEntityDataProvider;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.data.bind.IBindDataSource;
import mdb.core.ui.client.data.impl.MdbDataSource;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Oct 8, 2012
 *
 */
public class MdbViewDataBinder extends ViewDataBinder {		

	private static final Logger _logger = Logger.getLogger(MdbViewDataBinder.class.getName());
	
    public MdbViewDataBinder ( IBindDataSource dataSourceBinder) {        
    	super (dataSourceBinder, new MdbEntityDataProvider());    	
    }         	    
	
	
    @Override
	public  void  bind(State value) {		
    	
    	try {
    	
				if (getBindSource() != null && getDataProvider() != null) {				
					   
					_logger.info("Start  bind data to the view:" +getBindSource().getClass().getName());					
						
							if (getBindSource() != null ) {
								
								IBindDataSource source =(IBindDataSource) getBindSource();					
								
								if  ( value == State.bindAllData) {
									for (Entry<Integer,Record[]>  entry :getDataProvider().getDataMap().entrySet() ) {	
										
										IDataSource ds = null;
										
										if ( source.getDataSources().containsKey(entry.getKey()) ) {
											ds = source.getDataSources().get(entry.getKey());											
											ds.setData(entry.getValue());
										}
										else {								
											ds = new MdbDataSource(entry.getKey(),this);		
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