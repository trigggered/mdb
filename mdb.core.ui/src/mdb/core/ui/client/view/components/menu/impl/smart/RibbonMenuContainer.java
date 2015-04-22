/**
 * 
 */
package mdb.core.ui.client.view.components.menu.impl.smart;

import mdb.core.ui.client.view.components.menu.IMenuItemFactory;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.toolbar.RibbonBar;
import com.smartgwt.client.widgets.toolbar.RibbonGroup;

/**
 * @author azhuk
 * Creation date: Dec 20, 2012
 *
 */

public class RibbonMenuContainer extends ToolStripMenuContainer {	

	/**
	 * @param menuItemFactory
	 */
	public RibbonMenuContainer(IMenuItemFactory menuItemFactory) {
		super(menuItemFactory);
	}

	@Override
	protected  void createContainer() {
		RibbonBar bar =  new RibbonBar();
		bar.setLeft(0);  
		bar.setTop(75);  
		bar.setWidth100();
		bar.setMembersMargin(2);  
	    bar.setLayoutMargin(2);		
		
		RibbonGroup group = new RibbonGroup();
		group.setTitle("Main Menu");
		group.setWidth100();
		group.setNumRows(3);  
		group.setRowHeight(26);				
	        
		bar.addMember(group);			
		setContainer(group);								
	}	
	
	
	@Override
	public void addMemberToContainer(Canvas canvas) {
		RibbonGroup group = (RibbonGroup)(getContainer());
		group.addControl(canvas);		
	}
	
}
