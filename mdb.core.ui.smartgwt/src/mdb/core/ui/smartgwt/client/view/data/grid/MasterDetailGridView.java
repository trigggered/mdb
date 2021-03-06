package mdb.core.ui.smartgwt.client.view.data.grid;

import java.util.HashMap;

import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.smartgwt.client.view.data.DataView;
import mdb.core.ui.smartgwt.client.view.data.ISGWTDataView;
import mdb.core.ui.smartgwt.client.view.data.grid.ListOfGrids.EmbeddedGrid;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public abstract class MasterDetailGridView extends DataView  implements ISGWTDataView {	

	
	protected ListOfGrids _listOfGrids;
	
	/**
	 * @return the _listOfGrids
	 */
	public ListOfGrids getListOfGrids() {
		return _listOfGrids;
	}

	
	private Layout _mainLayout;
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#createViewPanel()
	 */
	@Override
	protected void createMainLayout() {
		_mainLayout = new HLayout();		
	}
	
	@Override
	public Layout getMainLayout() {
		return _mainLayout;
	}
	
	public abstract GridView getMaster();
	
	public abstract GridView getDetail();
	
	@Override
	public Record getSelectedRecord() {
		return getMaster().getSelectedRecord();		
	}

	@Override
	public Record[] getSelectedRecords() {
		return getMaster().getSelectedRecords();		
	}
	
	
	@Override
	public boolean isSelectedRecord() {
		return getMaster().getSelectedRecord() != null;		
	}
			
	protected abstract void createGrids();
	
	@Override
	protected void createComponents() {
		super.createComponents();		
		
		createGrids();
		EmbeddedGrid[]  grids = _listOfGrids.toArray();		
				
		for (int i=0; i<=grids.length-1; i++) {
			grids[i].getMainLayout().setShowResizeBar(i<grids.length-1);
			
			getMainLayout().addMembers(grids[i].getMainLayout());
		}					
		
		getMaster().getListGrid().addRecordClickHandler(new RecordClickHandler() {
			
		@Override
		public void onRecordClick(RecordClickEvent event) {
				requestDetailData(event.getRecord());				
			}
		});
	}

	
	
	@Override
	public void bindDataComponents() {
		// TODO Auto-generated method stub
	}
	
	

	protected void refreshData(HashMap<String,String> params) {		
		_listOfGrids.prepareRequestData(params);
	}
	
	@Override
	public void prepareRequestData() {		
		_listOfGrids.prepareRequestData(getParams().toMap());	
	}
	
	@Override
	public void putRequestToQueue() {	
		_listOfGrids.putRequestToQueue();
	}
	
	protected abstract String  getRelationField();
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.grid.MasterDetailGridView#getDetaileReqParams(com.smartgwt.client.widgets.grid.ListGridRecord)
	 */
	
	protected HashMap<String, String> getDetaileReqParams(ListGridRecord masterSelectedData) {
		HashMap<String,String> toReturn = null;
		if (masterSelectedData !=null) {	
			String masterId = masterSelectedData.getAttribute(getRelationField());				
		 
			toReturn = new HashMap<String,String>();
			toReturn.put(getRelationField(),masterId);
		}
		return toReturn;
	}
	
	
	
	protected void requestDetailData(ListGridRecord value) {
		HashMap<String,String> params = getDetaileReqParams(value);		
		if(params!=null) {		
			getDetail().prepareRequestData(params);
			GatewayQueue.instance().getProcessor().run();
		}
	}
	
	@Override
	public boolean isHaseChanges() {
		for (DataView  view : getListOfGrids().getList() ) {
			boolean isHasChanges = view.isHaseChanges();
			if (isHasChanges)  {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void save() {
		for (DataView  view : getListOfGrids().getList() ) {
			
			view.getMainDataSource().save();
		}
	}

	public void setFilteringEnable(boolean value) {	
		 for (EmbeddedGrid dataView :   getListOfGrids().getList() ) {
			 dataView.setFilteringEnable(value);
		 }
	}
	
	public void setSortingEnable(boolean value) {
		for (EmbeddedGrid dataView :   getListOfGrids().getList() ) {
			 dataView.setSortingEnable(value);
		 }
	}		
		
}
