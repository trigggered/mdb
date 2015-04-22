/**
 * 
 */
package mdb.core.ui.client.view;


/**
 * @author azhuk
 * Creation date: May 13, 2014
 *
 */
public interface IManagingView {
	public void openViewInTab (IView view);
	public void closeCurrentTab();
	public void closeAllTab();
	public void closeAllTabButCurrent();
}
