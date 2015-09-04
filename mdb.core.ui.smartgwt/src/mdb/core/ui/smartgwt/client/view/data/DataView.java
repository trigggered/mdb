/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.data;

import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.data.ADataView;
import mdb.core.ui.smartgwt.client.components.menu.ToolStripMenuItemFactory;
import mdb.core.ui.smartgwt.client.data.bind.MdbViewDataBinder;
import mdb.core.ui.smartgwt.client.view.ISGWTComponent;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;


/**
 * @author azhuk
 * Creation date: Oct 16, 2012
 *
 */
public abstract class DataView extends ADataView<Record> implements ISGWTComponent {

	
	private Layout _mainLayout;
	
	   
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#createDataBinder()
	 */
	@Override
	protected IDataBinder createDataBinder() {
		return new MdbViewDataBinder(this);
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#createViewPanel()
	 */
	@Override
	protected void createMainLayout() {
		setMainLayout(new VLayout());		
	}
	
	@Override
	protected void createMenuContainer() {
		setMenuContainer(getMenuFactory().createMenuContainer());
		getMainLayout().addMember(((ISGWTComponent)getMenuContainer()).getMainLayout());
		
	}
	
	@Override
	public Layout getMainLayout() {
		   return _mainLayout;
	}
	
	@Override
	 public void 	setMainLayout(Layout value) {
		   _mainLayout = value;
	 }
		
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#getMenuFactory()
	 */
	@Override
	protected IMenuFactory getMenuFactory() {
		return (IMenuFactory) ToolStripMenuItemFactory.instance();
	}

		
}