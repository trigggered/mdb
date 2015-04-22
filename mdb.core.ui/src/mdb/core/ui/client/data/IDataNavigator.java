/**
 * 
 */
package mdb.core.ui.client.data;

import mdb.core.ui.client.view.components.menu.IMenuButtons;


/**
 * @author azhuk
 * Creation date: Dec 12, 2012
 *
 */
public interface IDataNavigator  extends IMenuButtons {		
		
	public void insert();
	public void edit();
	public void delete();
	public void save();
	public void cancel();
	public void refresh();
	public void filter();

}
