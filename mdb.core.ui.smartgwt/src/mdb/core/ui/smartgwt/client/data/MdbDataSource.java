/**
 * 
 */
package mdb.core.ui.smartgwt.client.data;

import java.util.ArrayList;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.shared.utils.ISequenceGenerator;
import mdb.core.ui.client.app.AppController;
import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.data.IDataComponent;
import mdb.core.ui.client.data.IDataSourceEditor;
import mdb.core.ui.client.data.SequenceGenerator;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.data.fields.IDataSourceFields;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.data.impl.editors.EditRelationFields;
import mdb.core.ui.smartgwt.client.data.fields.DataSourceFields;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Random;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * @author azhuk
 * Creation date: Oct 8, 2012
 *
 */
public class MdbDataSource  extends GwtRpcDataSource implements IDataSource {	

	private static final Logger _logger = Logger.getLogger(MdbDataSource.class.getName());
	
    private Record[] _dataList; 
    private IDataBinder _viewDataBinder;
    private IDataComponent _dataComponent;
    private int _entityId;
    
    private ISequenceGenerator _seqGenerator = new SequenceGenerator();
    private DataSourceFields _dataSourceFields;
    private String[] _keyFields;	
	private boolean _localKeyGen = true;
    
    public MdbDataSource (int entityId, IDataBinder viewDataBinder) {
    	_entityId = entityId;
    	setID("IdIs_"+Integer.toString(Math.abs(Random.nextInt()))+"_"+Integer.toString(entityId))  ;   	    	   	    	
    	
    	setViewDataBinder(viewDataBinder);
    	IDataProvider provider = viewDataBinder.getDataProvider();
    	
    	setDataSourceFields(provider.getDataSourceFieldsMap().get(entityId));
    	
    	
    	setData(
    	ViewDataConverter.jSon2RecordArray(
    			viewDataBinder.getDataProvider().getDataMap().get(entityId)));
    	
    	
    	setKeyFilds(getViewDataBinder().getDataProvider().getDataSourceKeysMap().get(entityId));
    	//setKeyFilds( getRequestEntity().getKeys());    	
    }
         
    
    public MdbDataSource () {
    	setID("IdIs_"+Integer.toString(Math.abs(Random.nextInt())))  ;
    }

    
        
    @Override
    protected void executeFetch (final String requestId, final DSRequest request, final DSResponse response) {    	
        response.setData (_dataList);
        processResponse (requestId, response);
    }  
    
        
    @Override
    protected void executeAdd (final String requestId, final DSRequest request, final DSResponse response) {
    	
    	JavaScriptObject data = request.getData();
    	String keyValue = null;
    	
    	if ( isViewDataBinderExist() && data != null ) { 		    					
    		
    		if (isLocalKeyGen())
    		{
    			data =localKeyGen(data);
    			
    			if ( _keyFields != null && _keyFields.length > 0) {
	    			if (JSOHelper.isArray(data) ) {
	    				
	    			} else {
	    				
	    				keyValue = JSOHelper.getAttribute(data, _keyFields[0]);
	    			}
    			}
    		} 		
    		
    		
    		String strToServer = ViewDataConverter.JavaScriptObject2Json(data,true);
	    	RequestEntity entity =	getRequestEntity();	    	
	    	
	    	
	    	entity.addForInsert(keyValue, strToServer);
	    	
	    	entity.setExecuteType(ExecuteType.ChangeData);
	    	
	    	response.setData(ListGridRecord.convertToRecordArray(request.getData ()));
	    	processResponse (requestId, response); 	    		    	
    	}
    }

    
    /**
     * @return
	 */
    @Override
	public  boolean isLocalKeyGen() {
		return _localKeyGen;
	}
	
	
    @Override
	public void setLocalKeyGen(boolean value) {
		_localKeyGen = value;
	}


	private JavaScriptObject localKeyGen(JavaScriptObject data) {    
		if (_keyFields == null)
			 return data;
		
		for (String key :  _keyFields) {
			
			if (JSOHelper.isArray(data) ) {
				
                JavaScriptObject[] componentsj = JSOHelper.toArray(data);
                
                for (JavaScriptObject componentJS : componentsj) {    	                	
                    Record rec = (Record) RefDataClass.getRef(componentJS);
                    if (rec != null) {
                    	rec.setAttribute(key, _seqGenerator.nextVal());
                    }
                }    	                    	                
    		}
			else {    					
					JSOHelper.setAttribute(data, key, _seqGenerator.nextVal());					
			}
			
		}
	

    	return data;    	
	}
    
    
	
    
    @Override
    protected void executeUpdate (final String requestId, final DSRequest request, final DSResponse response) {   	        
    	
    	if ( isViewDataBinderExist() ) {
    		
    	 // Retrieve record which should be updated.
            JavaScriptObject data = request.getData ();            
            ListGridRecord rec = new ListGridRecord (data);
            
            String pkName  = getDataSource().getPrimaryKeyFieldName();
            int index = 0;
            if ( pkName != null && pkName.length() > 0) {
	            String pkVal = rec.getAttributeAsString(pkName);
	            index = Integer.parseInt(pkVal);
            }
            
            
            // Find grid
            // При редактировании не через карточку редактирования, из грида надо взять
            // запись целиком, так как в таком случае на вход приходит только DSRequest request  изменненные поля 
            /*Canvas canvas  = Canvas.getById (request.getComponentId ());
             if (canvas !=null ) {            	
            	DataBoundComponent dataBoundComponent = (DataBoundComponent) canvas;
	            if (dataBoundComponent!=null) {
		            // Get record with old and new values combined            	
	            	index = dataBoundComponent.getRecordIndex (rec);	            	
		            rec = (ListGridRecord) dataBoundComponent.getEditedRecord (index);
	            }
            }*/
            
            String strToServer = ViewDataConverter.record2Json(rec);
            _logger.info(strToServer);
	    	RequestEntity entity =	getRequestEntity();
	    	entity.addForUpdate(index, strToServer);
	    	entity.setExecuteType(ExecuteType.ChangeData);
	    	
	    	
	    	/*
	    	 * 
	    	 */
	    	//response.setData(ListGridRecord.convertToRecordArray(request.getData ()));
	    	processResponse (requestId, response);
	    	    	
    	}        
    }

    @Override
    protected void executeRemove (final String requestId, final DSRequest request, final DSResponse response) {
    	if  (isViewDataBinderExist()) {
    		
    		Record[]  recsForDel = ListGridRecord.convertToRecordArray(request.getData());    		    		
    		
	    	String strToServer = ViewDataConverter.JavaScriptObject2Json(request.getData(),true);
	    	_logger.info("Remove record is:"+strToServer);
	    	RequestEntity entity =	getRequestEntity();	    	
	    	entity.addForDelete(strToServer);
	    	entity.setExecuteType(ExecuteType.ChangeData);
	    	removeNewAddRecors(entity, recsForDel);
	    	
	    	response.setData(recsForDel);
	    	processResponse (requestId, response); 	    	
	    	
    	}    	
    	else {
    		_logger.info("ViewDataBinder not found");
    	}
    }    
 
    private void removeNewAddRecors(RequestEntity entity , Record[]  recsForDel) {
	    
		if (_keyFields!= null && _keyFields.length > 0 ) {			
			for ( Record rec  :recsForDel) {			
				entity.getForInsert().remove(rec.getAttribute(_keyFields[0]));
    		}		
		}	    	
	}
	

    private boolean isViewDataBinderExist() {
    	return getViewDataBinder() != null; 
    }
    
    /*
     * IDataSource implemenation
     * */

	/* (non-Javadoc)
	 * @see mdb.ui.client.data.sources.IDataSource#setData(com.smartgwt.client.data.Record[])
	 */
    public void setData(Record[] data) {
    	_dataList = data;
    }
    
	@Override
	public void setData(String jsonString) {
		setData(ViewDataConverter.jSon2RecordArray(jsonString));				
	}
	
	@Override
	public Record[] getData() {
		return _dataList;		
	}  
	

	
	
	@Override
	public void setFields(ArrayList<DataSourceField> fields) {
		
		if (fields == null )
			return; 
			
			for (DataSourceField fld : fields) {				
				this.addField(fld);
				
				if ( _dataSourceFields != null && _dataSourceFields.getMdbFieldMap().containsKey( fld.getName()) ) {
					
					IMdbField mdbField = _dataSourceFields.getMdbFieldMap().get(fld.getName());
					 if (mdbField.getMdbFieldType() != null)   {
					  switch (  mdbField.getMdbFieldType() ) {
					  		case FORM: {
					  				IDataSourceEditor dsEditor = new EditRelationFields();
					  				dsEditor.edit(this, mdbField);					  								  								  			
					  			}
					  			break;					  		
							default:
								break;
					   }
					}
				}		
			}		
		
	}

	@Override
	public void initFieldToDefValue() {
		
		for  ( DataSourceField fld : _dataSourceFields.getDataSourceFields() ) {
			
			IMdbField mdbField = _dataSourceFields.getMdbFieldMap().get(fld.getName());
			switch (mdbField.getMdbFieldType()) {
			case CONTEXT_VALUE: 
				
				this.getDataComponent().setFieldValue(mdbField.getName(), 
	  					AppController.getInstance().getAppContext().getValue(mdbField.getLookUpFld().getLookUpKeyName())
	  					//"708609"
	  					);
				break;
				
			default:
				break;								
			}						
		}
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.data.sources.IDataSource#setViewDataBinder(mdb.ui.client.data.sources.IViewDataBinder)
	 */
	@Override
	public void setViewDataBinder(IDataBinder viewDataBinder) {
		_viewDataBinder = viewDataBinder;		
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.common.data.IDataSource#getViewDataBinder()
	 */
	@Override
	public IDataBinder getViewDataBinder() {
		return _viewDataBinder;
	}
	

	/* (non-Javadoc)
	 * @see mdb.ui.client.data.sources.IDataSource#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return this;
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.data.sources.IDataSource#getEntityId()
	 */
	@Override
	public int getEntityId() {
		return _entityId;
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.data.sources.IDataSource#getRecords()
	 */
	@Override
	public Record[] getRecords() {
		ListGrid grid = new ListGrid();
		grid.setDataSource(this);
		grid.fetchData();
		return grid.getRecords();		
	}	


	@Override
	public void save() {
		if (isHasChanges()) {
			callRemoteCommunication() ;	
		}
	}


	public void callRemoteCommunication () {		
		
		 GatewayQueue.instance().put(getViewDataBinder().getDataProvider());
		 GatewayQueue.instance().getProcessor().run();
	}
	

	public RequestEntity getRequestEntity() {		
		return (RequestEntity) getViewDataBinder().getDataProvider().getRequest().get(Integer.toString(_entityId));
	}
	
	public RequestEntity getResponseEntity() {		
		return (RequestEntity) getViewDataBinder().getDataProvider().getResponse().get(Integer.toString(_entityId));
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.common.data.IDataSource#isAutoSave()
	 */
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataSource#isPagingUsage()
	 */
	@Override
	public boolean isPagingUsage() {
		 RequestEntity re =  getResponseEntity();		 
		 return  ( re != null)?re.isPagingUsage():false;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataSource#refresh()
	 */
	@Override
	public void refresh() {
		RequestEntity entity = getRequestEntity();
		if (entity != null) {
			entity.setExecuteType(ExecuteType.GetData);
			entity.setRequestFieldsDescription(false);
			callRemoteCommunication();
		}
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataSource#isHasChanges()
	 */
	@Override
	public boolean isHasChanges() {
		return getRequestEntity().isHasChanges();
	}

	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataSource#editRecord(com.smartgwt.client.data.Record)
	 */
	@Override
	public void editRecord(Record record) {
		updateData(record);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataSource#setDataComponent(mdb.core.ui.client.data.EditDialog)
	 */
	@Override
	public void setDataComponent(IDataComponent dataComponent) {
		_dataComponent = dataComponent;
	}	


	public IDataComponent getDataComponent() {
		return _dataComponent;		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataSource#bindToDataComponent(com.smartgwt.client.widgets.form.DynamicForm)
	 */		
	
	/**
	 * @return the _dataSourceFields
	 */
	@Override
	public DataSourceFields getDataSourceFields() {
		return _dataSourceFields;
	}


	/**
	 * @param _dataSourceFields the _dataSourceFields to set
	 */
	public void setDataSourceFields(IDataSourceFields value) {
		this._dataSourceFields = (DataSourceFields) value;
		
		if ( _dataSourceFields!= null ) {    		   	
    		setFields(_dataSourceFields.getDataSourceFields());
    	}		
	}


	/**
	 * 
	 */
	public void createEmptyRecord() {
		final Record[] dataList = new Record[1];
		final Record rec = new Record();
		dataList[0] = rec;		
		
		for (DataSourceField fld : getFields()) {
			rec.setAttribute(fld.getName() , "");
		}
		setData(dataList);		
	}

	public void setKeyFilds(String[] value) {
		_keyFields = value;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IBaseDataSource#updateData(java.lang.String)
	 */
	@Override
	public void updateData(String jsonString) {		
		Record[] records = ViewDataConverter.jSon2RecordArray(jsonString);
				if (records!= null && records.length >0) {
					updateData(records[1]);
				}
	}





	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IBaseDataSource#isHasData()
	 */
	@Override
	public boolean isHasData() {
		return getData()!=null && getData().length >0;
		
	}
}