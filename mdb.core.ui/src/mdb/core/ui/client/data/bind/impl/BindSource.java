/**
 * 
 */
package mdb.core.ui.client.data.bind.impl;

import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.data.bind.IBindSource;

/**
 * @author azhuk
 * Creation date: Aug 15, 2013
 *
 */
public abstract class BindSource implements IBindSource {
	private IDataProvider _daDataProvider;
	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IBindSource#bindDataComponents()
	 */
	@Override
	public abstract void bindDataComponents();

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IBindSource#bindDataComponentsAfterChange()
	 * Bind data to visual components after changet data
	 */
	@Override
	public void bindDataComponentsAfterChange() {				
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IBindSource#setDataProvider(mdb.core.ui.communications.IDataProvider)
	 */
	@Override
	public void setDataProvider(IDataProvider dataProvider) {
		_daDataProvider =dataProvider;
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.data.bind.IBindSource#getDataProvider()
	 */
	@Override
	public IDataProvider getDataProvider() {	
		return _daDataProvider;
	}
	
		
}
