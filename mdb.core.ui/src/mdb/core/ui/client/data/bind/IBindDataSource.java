/**
 * 
 */
package mdb.core.ui.client.data.bind;

import java.util.HashMap;

import mdb.core.ui.client.data.IDataSource;

/**
 * @author azhuk
 * Creation date: Aug 13, 2013
 *
 */
public interface IBindDataSource extends IBindSource {
	
	public void setDataSources(HashMap<Integer, IDataSource> value);	
	public HashMap<Integer, IDataSource> getDataSources();

	
}
