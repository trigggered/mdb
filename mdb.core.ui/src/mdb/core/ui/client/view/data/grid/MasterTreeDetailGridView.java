package mdb.core.ui.client.view.data.grid;

import java.util.logging.Logger;

import mdb.core.ui.client.view.data.DataView;
import mdb.core.ui.client.view.data.tree.TreeView;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;


public abstract class MasterTreeDetailGridView extends DataView {

	private static final Logger _logger = Logger.getLogger(MasterTreeDetailGridView.class.getName());
	
	protected class MasterTree extends TreeView {
		/*
		@Override
		protected void bindTree() throws Exception {
			super.bindTree();
			expandTree();
		}*/
		
		@Override
		protected void nodeSelected(SelectionEvent event) {			
			requestDetailData(event.getRecord());
		}
		
		@Override
		protected void nodeDeselected(SelectionEvent event) {
		
		}
	}
	
	public class DetailGrid extends GridView {
	  	
	}
	
	
	
	private TreeView _masterTree;	
	
	private DetailGrid _detailGrid;
	
	
	public MasterTreeDetailGridView () {
	
	}
	
	
	public TreeView getMaster() {
		return _masterTree;
	}
	
	public void setMaster(TreeView value) {
		this._masterTree = value;
	}
	
	public DetailGrid getDetail() {
		return _detailGrid;
	}	

	public void setDetail(DetailGrid value) {
		this._detailGrid = value;
	}

	
	
	@Override
	public Record getSelectedRecord() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Record[] getSelectedRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSelectedRecord() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void createComponents() {
		super.createComponents();
		_masterTree = createMasterComponent();
		
		_detailGrid = createDetailComponent();
		
		_masterTree.setShowResizeBar(true);
		getViewPanel().addMembers(_masterTree, _detailGrid);
	}

	
	protected TreeView createMasterComponent() {
		TreeView toReturn = new MasterTree();
		toReturn.setWidth("30%");
		return toReturn;
	}
	

	
	protected DetailGrid createDetailComponent() {
		DetailGrid toReturn = new DetailGrid();
		toReturn.setWidth("70%");
		return toReturn;
	}
	
	
	@Override
	public void bindDataComponents() {		
	}	

	protected void requestDetailData(ListGridRecord value) {		
		_logger.info("requestDetailData ID "+value.getAttribute("ID"));
	}	
	
	
}
