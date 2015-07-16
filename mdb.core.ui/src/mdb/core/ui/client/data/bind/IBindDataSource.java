/**
 * 
 */
package mdb.core.ui.client.data.bind;

import java.util.HashMap;

import mdb.core.ui.client.data.IBaseDataSource;

/**
 * @author azhuk
 * Creation date: Aug 13, 2013
 *
 */
public interface IBindDataSource extends IBindSource {
	
	public void setDataSources(HashMap<Integer, IBaseDataSource> value);	
	public HashMap<Integer, IBaseDataSource> getDataSources();

	
}
