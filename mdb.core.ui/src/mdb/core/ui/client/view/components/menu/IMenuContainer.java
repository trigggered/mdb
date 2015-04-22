/**
 * 
 */
package mdb.core.ui.client.view.components.menu;


import mdb.core.ui.client.view.data.IDataView;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */
public interface IMenuContainer {
	public void bind(IMenu menu);
	public Layout getLayout ();
	public IDataView  getView();
	public void setView(IDataView value);
	public boolean isMenuExist(IMenu menu);
	public void addMemberToContainer(Canvas canvas);
	
}
