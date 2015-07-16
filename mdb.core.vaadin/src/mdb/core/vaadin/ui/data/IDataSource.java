/**
 * 
 */
package mdb.core.vaadin.ui.data;

import mdb.core.ui.client.data.IBaseDataSource;

import com.vaadin.data.Container.Indexed;

/**
 * @author azhuk
 * Creation date: Oct 12, 2012
 *
 */
public interface IDataSource extends IBaseDataSource {
	
	
	Indexed getDataSource();	
			
	
}
