/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.data.tree;


import java.util.logging.Logger;

import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.view.components.menu.data.TreeMenu;
import mdb.core.ui.client.view.data.tree.ITreeView;
import mdb.core.ui.smartgwt.client.view.data.grid.GridView;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.tree.TreeGrid;

/**
 * @author azhuk
 * Creation date: Oct 16, 2012
 *
 */
public class TreeView extends GridView implements ITreeView {

	private static final Logger _logger = Logger
			.getLogger(TreeView.class.getName());
		
	
	public TreeView () {
		super();
	}
		 
	 public TreeView (int entityId) {
		 super();
		 setMainEntityId(entityId);
		 
	 }
	 
	 protected ListGrid createGrid(){
		 TreeGrid toReturn =new TreeGrid();
		 setTreeNodeSelectionChanged(toReturn);		 
		 return toReturn;
	 }
	 
	 
	 @Override
	 public void bindDataComponents() throws DataBindException  {
			_logger.info("Start bind data to visual component");
			 try {
				bindTree() ;
			}
			 catch (NullPointerException e2) {				 
				 throw new DataBindException("Failed bind data: NullPointerException in TreeView binding");
			} 
			 catch (Exception e) {
				throw new DataBindException(e);				
			}
			 _logger.info("End bind data");
	 }
	 
	 protected void bindTree() throws Exception{									
			
			DataSource ds =getMainDataSource().getDataSource();     
			if (!ds.isCreated()) {
				ds.setTitleField("TITLE");
			}
	    	ds.getField("ID_PARENT").setForeignKey("ID");
	    	ds.getField("ID_PARENT").setRootValue("");
	    	
	    	_grid.setDataSource(ds);
	    	_grid.fetchData();
	    
	        
	 }
	 
	 private void setTreeNodeSelectionChanged(TreeGrid tree) {			
			tree.addSelectionChangedHandler(new SelectionChangedHandler() {
				
				@Override
				public void onSelectionChanged(SelectionEvent event) {				
					if (event.getState() ) {
						nodeSelected(event);
					}
					else {
						nodeDeselected(event);
					}
				}
			});
		}
	 
	 @Override
	 protected void createMenu() {
		 if (getMenuContainer() != null) {
				getMenuContainer().bind(new TreeMenu(this));
			}
	 }
		
		protected void nodeSelected(SelectionEvent event) {
			String id =  event.getRecord().getAttribute("ID");
			String title =  event.getRecord().getAttribute("TITLE");
			_logger.info("Selected new record id="+id+" title="+title);
		}
		
		protected void nodeDeselected(SelectionEvent event) {
			String id =  event.getRecord().getAttribute("ID");
			String title =  event.getRecord().getAttribute("TITLE");
			_logger.info("Deselected record id="+id+" title="+title);
		}
		
		private TreeGrid getTree() {
			return (TreeGrid)getListGrid();
			
		}
		
		@Override
		public void expandTree() {
			getTree().getData().openAll();			
		}
		
		@Override
		public void collapseTree() {
			getTree().getData().closeAll();			
			
		}
		
		@Override
		public void findNodeById(String id) {
			getTree().getData().findById(id);
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.view.data.tree.ITreeView#refreshTree()
		 */
		@Override
		public void refreshTree() {
			super.callRequestData();
			
		}
}
