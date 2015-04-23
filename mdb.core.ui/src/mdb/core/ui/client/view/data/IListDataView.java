/**
 * 
 */
package mdb.core.ui.client.view.data;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.smartgwt.client.widgets.grid.ListGrid;

/**
 * @author azhuk
 * Creation date: Apr 23, 2015
 *
 */
public interface IListDataView extends IDataView {
	void setFilteringEnable(boolean value);
	void setSortingEnable(boolean value);	
	ListGrid getListGrid() ;
}
