/**
 * 
 */
package mdb.core.ui.smartgwt.client.data.bind;

import java.util.logging.Logger;

import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.bind.IBindDataSource;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.data.bind.impl.AViewDataBinder;
import mdb.core.ui.smartgwt.client.communication.MdbEntityDataProvider;
import mdb.core.ui.smartgwt.client.data.MdbDataSource;

/**
 * @author azhuk
 * Creation date: Oct 8, 2012
 *
 */
public class MdbViewDataBinder extends AViewDataBinder {		

	private static final Logger _logger = Logger.getLogger(MdbViewDataBinder.class.getName());
	
    public MdbViewDataBinder ( IBindDataSource dataSourceBinder) {        
    	super (dataSourceBinder, new MdbEntityDataProvider());    	
    }        	    
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.bind.impl.AViewDataBinder#createDataSource(int, mdb.core.ui.client.data.bind.IDataBinder)
	 */
	@Override
	protected IBaseDataSource createDataSource(int entityId,
			IDataBinder viewDataBinder) {

		return new MdbDataSource(entityId, viewDataBinder);
	}
	
}