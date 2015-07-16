/**
 * 
 */
package mdb.core.ui.client.view.components.menu;


import mdb.core.ui.client.view.IView;
import mdb.core.ui.client.view.data.IDataView;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public interface IMenuContainer {
	
	enum EContaynerOriented {
		Vertical,
		Horizontal
	}
	
	public void bind(IMenu menu);
	public IDataView  getView();
	public void setView(IDataView value);
	public boolean isMenuExist(IMenu menu);
	
	/**
	 * @param aBaseView
	 */
	public void addToView(IView view);
	
}
