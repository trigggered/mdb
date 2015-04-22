/**
 * 
 */
package mdb.core.ui.client.data.bind;

import mdb.core.ui.client.communication.IDataProvider;


/**
 * @author azhuk
 * Creation date: Aug 15, 2013
 *
 */
public interface IBindSource {
	public void bindDataComponents() throws DataBindException;
	public void bindDataComponentsAfterChange();
	
	public void setDataProvider(IDataProvider dataProvider);
	public IDataProvider getDataProvider();
	
}

