/**
 * 
 */
package mdb.core.ui.client.view.components.menu.data;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IDataNavigator;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.Menu;
import mdb.core.ui.client.view.data.tree.ITreeView;

/**
 * @author azhuk
 * Creation date: Dec 20, 2013
 *
 */
public class TreeMenu extends Menu {
	
	private ITreeView _treeView;
	
		public TreeMenu(ITreeView tree) {
			super("TreeMenu");
			_treeView = tree;
			create();
			setShowCaption(false);
			
		}
		
		private void create() {			
			
			IMenuItem item = addItem(IMenuItem.ItemType.ToolButton);
			AMenuData.initPropertyMenuItem(item, IDataNavigator.Buttons.treeExpand);
			item.setCommand(new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					if (_treeView != null)
						_treeView.expandTree();
					
				}
			});		 
			
			item = addItem(IMenuItem.ItemType.ToolButton);
			AMenuData.initPropertyMenuItem(item, IDataNavigator.Buttons.treeCollapse);
			item.setCommand(new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					if (_treeView != null)
					_treeView.collapseTree();					
				}
			});
			
			item = addItem(IMenuItem.ItemType.ToolButton);
			AMenuData.initPropertyMenuItem(item, IDataNavigator.Buttons.dataRefresh);
			item.setCommand(new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					if (_treeView != null)
						_treeView.refreshTree();
				}
			});	
		}		
	}
