/**
 * 
 */
package mdb.core.ui.client.view.data;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.mdb.MenuActions;
import mdb.core.ui.client.view.components.menu.mdb.MenuPrepareFilters;
import mdb.core.ui.client.view.dialogs.edit.EditDialog;
import mdb.core.ui.client.view.dialogs.input.InputDataFilter;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;



/**
 * @author azhuk
 * Creation date: Sep 20, 2012
 *
 */

public abstract class AListView extends DataView  {

	
	 private static final Logger _logger = Logger
			.getLogger(AListView.class.getName());


	 protected ListGrid _grid;	
	 
	
	 protected  MenuActions  		_menuAction;
	 
	 protected  MenuPrepareFilters _menuPrepareFilters;		
	 
	 protected AdvancedCriteria _fileredCriteria;	 
	 
	 protected abstract ListGrid createGrid();
	 
	 public  ListGrid getListGrid() {
		 return _grid;	 
	 }
	 
	 @Override
	 protected void  createComponents() {
		 super.createComponents();
		 _grid = createGrid();
		 _grid.setWidth100();
	     _grid.setHeight100();
	     _grid.setCanEdit(false);
	     _grid.setShowHover(true);;
	     _grid.setCanHover(true);
	     
	     _grid.setAlternateRecordStyles(true);
	     //_grid.setAutoFitFieldWidths(true);
	     _grid.setAutoFitData(Autofit.VERTICAL);
	     _grid.setAutoFitWidthApproach(AutoFitWidthApproach.VALUE);
	     
	     
	     //ListGrid.getEditorType
	     //((ListGrid)_grid).setEditorCustomizer();
	     //_grid.setDataPageSize(dataPageSize))
	     //_grid.setEditEvent(ListGridEditEvent.CLICK);
	     
	     _grid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {	    	 
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {				
				callViewEvent();
			}			
		});
	     
	     getViewPanel().addMember(_grid);
	 }
	 
	 
		
	 @Override
	 protected Layout createViewPanel() {	 
		 return new VLayout(15);
	 }	 
	 

	 @Override
	 public   void bindDataComponents() throws DataBindException  {		
		 	
		 try {
		 	if (isDataSourceExists() ) {			
		 		IDataSource mds =  getDataSource(getMainEntityId());
		 		
		 		if (mds!=null) {
		 			mds.bindToDataComponent(_grid);	 					 				      
		 		}			 			
		 	}

		 	bindMenuDataNavigator();
		 	_grid.redraw();
		 }
		 catch (Exception e) {
			 throw new DataBindException(e);
		 }	        
	 }

	
	@Override
	protected void createMenu () {
		IMenuContainer container =  getMenuContainer();
		_menuPrepareFilters  = new MenuPrepareFilters("MenuPrepareFilters", container);
		_menuPrepareFilters.setPosition(2);
		
		_menuAction =new MenuActions("MenuActions", container);
		_menuAction.setPosition(3);		 				 					
	}
	
	@Override
	public void prepareRequestData() {
		if (_menuPrepareFilters!= null) {
			_menuPrepareFilters.setActionForEntityId( getEntityIdForFilters());
			_menuPrepareFilters.prepareRequestData();
		}		
		
		if (_menuAction != null) {
			_menuAction.setActionForEntityId( getEntityIdForActions() );
			_menuAction.prepareRequestData();
		}				
	}
	
	protected int getEntityIdForFilters() {
		return getMainEntityId();
	}
	
	protected int getEntityIdForActions() {
		return getMainEntityId();
	}
	
	
	@Override
	public void putRequestToQueue() {
		super.putRequestToQueue();
		if (_menuAction!=null) {
			_menuAction.putRequestToQueue();
		}
		if (_menuPrepareFilters != null) {
			_menuPrepareFilters.putRequestToQueue();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#removeData()
	 */
	@Override
	public void removeData() {
		if (isSelectedRecord()) {			
			//_grid.removeData(getSelectedRecord());
			
			_grid.removeSelectedData();
			_grid.redraw();
		}		
	}
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#getSelectedRecord()
	 */
	@Override
	public Record getSelectedRecord() {		
		return _grid.getSelectedRecord();
	}
	
	@Override
	public Record[] getSelectedRecords() {
		return _grid.getSelectedRecords();
		
	}
	
	public boolean isSelectedRecord() {
		return getSelectedRecord() != null;
	}

	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.data.IDataView#showEditForm()
	 */
	@Override
	public void callEditEvent() {
		
		if (!isCanEdit()) {
			return;
		}
		
		if (_editHandler!=null) {
			_editHandler.onEdit(getSelectedRecord());
		}
		else {						
			EditDialog.viewForEdit(getMainDataSource(), _grid);
		}
	}
	
	@Override
	public void callDeleteEvent() {			
		
		if (!isCanEdit()) {
			return;
		}
		if (_deleteHandler!=null) {
			_deleteHandler.onEdit(getSelectedRecord());
		}
		else {
			//getListGrid().getDataSource().re
			super.callDeleteEvent();
		}
	}
	
	@Override
	protected void removeRecord(Record rec) {
		if ( rec == null) {
			_logger.info("selected record is null");
		}else {
			//getListGrid().getDataSource().removeData(rec);
			getListGrid().removeData(rec);		
		}
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.data.IDataView#showEditForm()
	 */
	@Override
	public void callInsertEvent() {		
		
		if (!isCanEdit()) {
			return;
		}
		
		if ( _insertHandler!= null) {
			_insertHandler.onEdit(null);
		}
		else {
			EditDialog.viewForNewRecord(getMainDataSource(), null,
					new ICallbackEvent<Record>() {			
				@Override
				public void doWork(Record data) {								
				}
			});		
		}
	}
	
	@Override
	public void callViewEvent() {
		
		if (_viewHandler!=null) {
			_viewHandler.onEdit(getSelectedRecord());
		}
		else {					
			callEditEvent();	
		}
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#showFilterView()
	 */
	@Override
	public void showFilterView() {
		
		InputDataFilter dlg  = new InputDataFilter(_fileredCriteria, 
				getDataSource(getMainEntityId()), new ICallbackEvent<AdvancedCriteria>() {
					public void doWork(AdvancedCriteria data) {
						_fileredCriteria = data;
						_grid.filterData(_fileredCriteria);
					};
				});
		dlg.show();		
	}
	
	
	protected IRequestData getRequestData() {
		IRequestData toReturn = new RequestEntity(getMainEntityId());
		Request request = getDataBinder().getDataProvider().getRequest();		
		request.clear();
		request.add(toReturn);
		
		return toReturn;
	}
	
	
	public void prepareRequestData(Map<String,String> parametersMap) {
		
		IRequestData re = getRequestData();
		re.setExecuteType(mdb.core.shared.transport.IRequestData.ExecuteType.GetData);						
		
		for (  Entry<String, String>  entry: parametersMap.entrySet() ) {											
			getParams().add(entry.getKey(), entry.getValue());
		}
		re.setParams(getParams());			
		putRequestToQueue();
	}
	
	public void setFilteringEnable(boolean value) {
		 getListGrid().setShowFilterEditor(value);
		 getListGrid().setFilterOnKeypress(value);
		 
	}
	
	@Override
	public void setCanEdit(boolean value) {
		super.setCanEdit(value);
		//getListGrid().setCanEdit(value);		
	}
	
	
}
