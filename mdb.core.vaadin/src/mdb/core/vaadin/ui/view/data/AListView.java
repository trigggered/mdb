package mdb.core.vaadin.ui.view.data;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.bind.DataBindException;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.mdb.MenuActions;
import mdb.core.ui.client.view.components.menu.mdb.MenuPrepareFilters;
import mdb.core.ui.client.view.dialogs.input.IInputDialogs;
import mdb.core.ui.client.view.dialogs.message.Dialogs;
import mdb.core.vaadin.ui.data.IDataSource;
import mdb.core.vaadin.ui.data.MdbDataSource;
import mdb.core.vaadin.ui.data.VaadinDataWraper;
import mdb.core.vaadin.ui.data.fields.DataSourceFieldsBuilder;
import mdb.core.vaadin.ui.view.dialogs.FormDialog;
import mdb.core.vaadin.ui.view.dialogs.input.InputVariablesDialog;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;



/**o
 * @author azhuk
 * Creation date: Sep 20, 2012
 *
 */

public abstract class AListView extends DataView {

	
	 private static final Logger _logger = Logger
			.getLogger(AListView.class.getName());
	 

	
	 protected  MenuActions  		_menuAction;
	 
	 protected  MenuPrepareFilters _menuPrepareFilters;		
	 
	 
	 protected abstract void createGrid();
	 

	 ItemClickListener _listener;
	 protected Item _currentItemDoubleClick;	
	 
	 @Override
	 protected void  createComponents() {
		 super.createComponents();
		 _listener = new ItemClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void itemClick(ItemClickEvent event) {
				//event.getItem() 
				if  (event.isDoubleClick() ) {
					_currentItemDoubleClick = event.getItem();
					callViewEvent();
				}else {
					
				}
			}
			 
		 };
		 createGrid();		 
	     
	 }
	 
	 
	 
	 @Override
	 public IBaseDataSource getDataSource(Integer key) {		
			if  (_lstDataSources.containsKey(key) ) {
					return _lstDataSources.get(key);
			}
			
			return null;
	}
	 


	@Override
	public IBaseDataSource getMainDataSource() {		
			return getDataSource(getMainEntityId());					
	}
		
	 @Override
	 public   void bindDataComponents() throws DataBindException  {		
		 	
		 try {
		 	if (isDataSourceExists() ) {		
		 		
		 		IDataSource mds =  (IDataSource) getMainDataSource();;
		 		
		 		if (mds!=null) {		 					 			
		 										 
		 			/*	for (  DataSourceField field : mds.getDataSource().getFields() ) {			 
		 			
		 					
		 					 if ( mds.getDataSourceFields().getMdbFieldMap().containsKey(field.getName()) ) {					 
		 						   
		 						 field.setHidden(!mds.getDataSourceFields().getMdbFieldMap().get(field.getName()).isVisivble());
		 						 field.setCanEdit(!mds.getDataSourceFields().getMdbFieldMap().get(field.getName()).isReadOnly());					 
		 					 }
		 				}	 				*/
		 				
		 			
		 				setDataSourceToDataComponent(mds);
		 				
		 		}			 			
		 	}

		 	bindMenuDataNavigator();
		 }
		 catch (Exception e) {
			 throw new DataBindException(e);
		 }	        
	 }

	
	/**
	 * @param dataSource
	 */
	protected abstract void setDataSourceToDataComponent(IDataSource ds);
	
	

	@Override
	protected void createMenu () {
		IInputDialogs dialog = new InputVariablesDialog(null);
		
		IMenuContainer container =  getMenuContainer();
		_menuPrepareFilters  = new MenuPrepareFilters("MenuPrepareFilters", container,
					new VaadinDataWraper(), DataSourceFieldsBuilder.instance(), dialog);
		
		_menuPrepareFilters.setPosition(2);
		
		_menuAction =new MenuActions("MenuActions", container, 
				new VaadinDataWraper(), DataSourceFieldsBuilder.instance(), dialog);
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
			
		}		
	}
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#getSelectedRecord()
	 */
	
	
	public abstract  boolean isSelectedRecord();


	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.data.IDataView#showEditForm()
	 */
	@Override
	public void callEditEvent() {
		
		if (!isCanEdit()) {
			return;
		}
		
		if (_editHandler!=null) {
			//editHandler.onEdit(getSelectedRecord());
		}
		else {							
			FormDialog.viewForEdit((IDataSource)getMainDataSource(), getSelectedRecord());
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
	/*		EditDialog.viewForNewRecord(getMainDataSource(), null,
					new ICallbackEvent<Record>() {			
				@Override
				public void doWork(Record data) {								
				}
			});		*/
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
		/*
		InputDataFilter dlg  = new InputDataFilter(_fileredCriteria, 
				getDataSource(getMainEntityId()), new ICallbackEvent<AdvancedCriteria>() {
					public void doWork(AdvancedCriteria data) {
						_fileredCriteria = data;
						_grid.filterData(_fileredCriteria);
					};
				});
		dlg.show();		*/
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
		 /*getListGrid().setShowFilterEditor(value);
		 getListGrid().setFilterOnKeypress(value);
		 */
	}
	
	@Override
	public void setCanEdit(boolean value) {
		super.setCanEdit(value);
		//getListGrid().setCanEdit(value);		
	}

	@Override
	public void callDeleteEvent() {
		
		if (!isCanEdit()) {
			return;
		}
		if (_deleteHandler!=null) {
		//	_deleteHandler.onEdit(getSelectedRecord());
		}
		else {
			
		
		
		if (isSelectedRecord()) {
			
			Dialogs.AskDialog(Captions.Q_DELETE_REC, new BooleanCallback() {
				
				@Override
				public void execute(Boolean value) {
					if (value) {									 						 						
						/*for  (Record rec : getSelectedRecords()) {
							removeRecord(rec);
							callAfterDeleteEvent();
						}*/								
					}
					
				}
			});
			
							
		}	
		}
	
	}
	
	public abstract Item getSelectedRecord();
	
	public abstract List<Item>  getSelectedRecords();
		

	public String getSelectedRecordJSON() {
		return null;		
		 //return ViewDataConverter.record2Json(getSelectedRecord());
	}
	

	

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#initSize()
	 */
	@Override
	protected void initSize() {
		// TODO Auto-generated method stub
		
	}	
}
