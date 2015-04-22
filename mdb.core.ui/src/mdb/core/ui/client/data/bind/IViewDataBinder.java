/**
 * 
 */
package mdb.core.ui.client.data.bind;

import mdb.core.ui.client.communication.IDataProvider;

/**
 * @author azhuk
 * Creation date: Oct 12, 2012
 *
 */
public interface    IViewDataBinder {
	
	public enum State {		
		notBind,
		bindAllData,
		bindChangeData		
	}

		
	public IBindSource getBindSource();	
	public void setBindSource(IBindSource  value);

	public IDataProvider getDataProvider();
	public void bind(IViewDataBinder.State value);

}
