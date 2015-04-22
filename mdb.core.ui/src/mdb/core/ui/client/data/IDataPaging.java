/**
 * 
 */
package mdb.core.ui.client.data;

import mdb.core.ui.client.view.components.menu.IMenuButtons;


/**
 * @author azhuk
 * Creation date: Feb 21, 2013
 *
 */
public interface IDataPaging  extends IMenuButtons {
	
	

	public abstract void firstPage();

	public abstract void previousPage();
	
	public abstract void goToPageNumber(int pageNumber);

	public abstract void nextPage();

	public abstract void lastPage();

}