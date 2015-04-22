/**
 * 
 */
package mdb.ui.client.view.editor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Params;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.IDataSpecification;
import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.IMainView;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.data.DataView;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.dialogs.input.InputVariablesDialog;
import mdb.ui.client.communication.rpc.regexp.RegExpService;
import mdb.ui.client.communication.rpc.regexp.RegExpServiceAsync;
import at.wizzart.gwt.widgets.client.CodeMirror;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;

/**
 * @author azhuk
 * Creation date: Sep 20, 2012
 *
 */
public class EntityEditor extends DataView   {
	
	private static final Logger _logger = Logger.getLogger(EntityEditor.class.getName());
	
	class MenuNewItemsSpec implements IDataSpecification {
		
		@Override
		public String getId() {
			return "id_dentity_action";
		}

		@Override
		public String getParentId() {
			return null;
		}

		@Override
		public String getTitle() {
			return "name_action";
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.data.IDataSpecification#getImg()
		 */
		@Override
		public String getImg() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.data.IDataSpecification#getAction()
		 */
		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataSpecification#getPosition()
		 */
		@Override
		public String getPosition() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.client.data.IDataSpecification#getShortcut()
		 */
		@Override
		public String getShortcut() {
			// TODO Auto-generated method stub
			return null;
		}		
	}
	
	class ExecuteMehodHandler implements com.smartgwt.client.widgets.events.ClickHandler 
	{		
		
		private void	executeMethod(Params params) {
			int entityId = Integer.valueOf(getEditEntityId());
			_viewEntityData.setMainEntityId(entityId);						
			_viewEntityData.setRequestParams(params);
			_viewEntityData.callRequestData();   	
		}		
		
		
		private void viewSetVariableDlg(String[]  result) {			

			InputVariablesDialog.viewSetVariableDlg(result, new ICallbackEvent<Record>() {
				
				@Override
				public void doWork(Record data) {
					if (data != null) {						
						executeMethod(ViewDataConverter.recordToParams(data));
					}					
				}
			});							
		}
		
		
		/* (non-Javadoc)
		 * @see com.smartgwt.client.widgets.form.fields.events.ClickHandler#onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent)
		 */
		@Override
		public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {			
			
			
			CodeMirror form =  _editorsForms.get( _tabSetSql.getSelectedTab().getTitle() );		
			String sqlText =  form.getValue();						
		
				_regExpService.Parse(sqlText, SQL_PARAMS_MATCHES, new AsyncCallback<String[]>() {				
					@Override
					public void onSuccess(String[] result) {
					  
						if (result != null && result.length > 0 ) {						
							viewSetVariableDlg(result);												 
						} 
						else {
						   executeMethod(null);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						_logger.severe("Failure process SQL Parse from RPC");				
					}				
				});
		}			
	}
	
	class SqlTabSelectedHandler implements com.smartgwt.client.widgets.tab.events.TabSelectedHandler {

		/* (non-Javadoc)
		 * @see com.smartgwt.client.widgets.tab.events.TabSelectedHandler#onTabSelected(com.smartgwt.client.widgets.tab.events.TabSelectedEvent)
		 */
		@Override
		public void onTabSelected(TabSelectedEvent event) {			
			Record rec = event.getTab().getAttributeAsRecord("record");			
			changeActiveMethod(rec);
		}
	}
	
	class SaveEditMehodHandler implements com.smartgwt.client.widgets.events.ClickHandler {

		/* (non-Javadoc)
		 * @see com.smartgwt.client.widgets.form.fields.events.ClickHandler#onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent)
		 */
		@Override
		public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {			
			CodeMirror codeForm =  _editorsForms.get( _tabSetSql.getSelectedTab().getTitle() );			
			final String sqlText =  codeForm.getValue();
			final IDataSource dsEntityEdit = getDataSource(Integer.valueOf(ID_DS_METHODS));					
			
			if (dsEntityEdit != null && dsEntityEdit.getData()!= null) {
				
				DynamicForm frm =  (DynamicForm)_tabSetSql.getSelectedTab().getAttributeAsObject("frmEntityMethod");
				Record recFromFrm = frm.getValuesAsRecord();
				recFromFrm.setAttribute("METHOD_BODY", sqlText );

				if (_tabSetSql.getSelectedTab().getAttributeAsString("edit_state").equals("insert") ) {
						dsEntityEdit.getDataSource().addData(recFromFrm);
				}
				else{				
					dsEntityEdit.getDataSource().updateData(recFromFrm);
				}
				//dsEntityEdit.getDataSource().addData(recFromFrm);
				_tabSetSql.getSelectedTab().setAttribute("record", recFromFrm);
				 
				 dsEntityEdit.save();
			}			
		}				
	}
	
	class CancelEditMehodHandler implements  com.smartgwt.client.widgets.events.ClickHandler {

		/* (non-Javadoc)
		 * @see com.smartgwt.client.widgets.form.fields.events.ClickHandler#onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent)
		 */
		@Override
		public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
			DynamicForm frm =  (DynamicForm)_tabSetSql.getSelectedTab().getAttributeAsObject("frmEntityMethod");
			frm.cancel();
			Record rec = _tabSetSql.getSelectedTab().getAttributeAsRecord("record");
			CodeMirror form =  _editorsForms.get( _tabSetSql.getSelectedTab().getTitle() );
			if (rec != null && form != null) {							
				form.setValue(rec.getAttribute("METHOD_BODY"));
			}
		}				
	}
	
	 
 	 private EntityGetData _viewEntityData;
	 private IDataView _viewFieldsEditor;
	 private EntityDefinition _frmEntityDefinition;
	
	 private static final String DESCRIPTION = "Entity  editor";
	 private final String SQL_PARAMS_MATCHES = "(?<=:)[^=]\\w*"; 
	 	 
	 private final int ID_DS_METHODS = 1091;	 	 	 


	 private TabSet _tabSetSql;
	 private HashMap<String, CodeMirror> _editorsForms = new HashMap<String, CodeMirror>();	 
	 private RegExpServiceAsync _regExpService = GWT.create(RegExpService.class);	
	 
	 
	 private String _editEntityId;
	 
	 public void setEditedntityId (String value) {
		 _editEntityId = value;
	 }
	 
	 public String getEditEntityId () {
		 return _editEntityId;
	 }	 
	 
	
		
	 public EntityEditor(String entityId) {				
		 setEditedntityId( entityId);		 
		 _frmEntityDefinition.addCustomProperty("ID_DENTITY", getEditEntityId ());		 
	 }	 
	
	@Override
	protected Layout createViewPanel() {
		Layout layout = super.createViewPanel();
		SectionStack sectionStackMain = new SectionStack();
		sectionStackMain.setVisibilityMode(VisibilityMode.MULTIPLE);
        
		SectionStackSection sqlSection = new SectionStackSection("SQL's");
        sqlSection.setExpanded(true);
        
        _tabSetSql = new TabSet();				
        sqlSection.addItem(_tabSetSql);
        
        
        SectionStackSection entityPropertySection = new SectionStackSection("Entity Propertys");
        entityPropertySection.setExpanded(true);
        entityPropertySection.addItem(createEntityPropertyPanel());
        
        sectionStackMain.setSections(sqlSection, entityPropertySection);
        layout.addMember(sectionStackMain);
        return sectionStackMain;
     }

	
	@Override
	public void setMainView(IMainView value) {
		super.setMainView(value);
		_frmEntityDefinition.setMainView(value);
		_viewFieldsEditor.setMainView(value);
		_viewEntityData.setMainView(value);
	}
	
	private TabSet createEntityPropertyPanel () {
		_frmEntityDefinition  = new EntityDefinition();		
		_viewFieldsEditor = new EntityFieldsEditor();		
		_viewEntityData = new EntityGetData((EntityFieldsEditor) _viewFieldsEditor);		
		
		TabSet tabSetProperty = new TabSet();
        tabSetProperty.setHeight("50%");
        
        /*Tab Description*/
        Tab tabDescription = new Tab();
        tabDescription.setTitle("Description");                        
        tabDescription.setPane(_frmEntityDefinition.getCanvas());
        tabDescription.setIcon(_frmEntityDefinition.getImgCaption());
        tabSetProperty.addTab(tabDescription);        
        
        /*Tab "Data Result"*/
        Tab tabDataResult = new Tab();
        tabDataResult.setTitle("Data Result");        
        tabDataResult.setPane(_viewEntityData.getCanvas());
        tabDataResult.setIcon(_viewEntityData.getImgCaption());
        tabSetProperty.addTab(tabDataResult);  
        
        /*Tab "Fields Defenition"*/
        Tab tabCollumns = new Tab();
        tabCollumns.setTitle("Fields Defenition");        
        
        //ListGrid gridColumns = new ListGrid();        
        tabCollumns.setPane(_viewFieldsEditor.getCanvas());        
        tabCollumns.setIcon(_viewFieldsEditor.getImgCaption() );
        tabSetProperty.addTab(tabCollumns);
        
        return  tabSetProperty;
	}
	
		
	
	private boolean  isCanCreateMethod(String nameAction ) {	
		return !_editorsForms.containsKey(nameAction);		
	}
	
	private Tab createSqlEditor(EMdbEntityActionType  mdbEntityActionType, Record record, String nameAction, final String methodBody ) {

        Tab tabSql = new Tab(nameAction);
                
        tabSql.setAttribute("EntityActionType", mdbEntityActionType.getValue());        
                
        tabSql.setIcon("demoApp/icon_edit.png", 16);
        
        //final String method = methodBody;                 	      

        /*Layout description */
        Layout methodDescriotionLayout = new HLayout();
        methodDescriotionLayout.setMargin(10);
        methodDescriotionLayout.setMembersMargin(10);
        methodDescriotionLayout.setHeight("10%");
          
        DynamicForm entityMethodsForm = new DynamicForm();
        entityMethodsForm.setGroupTitle("Edit method note");
        entityMethodsForm.setIsGroup(true);
        //entityMethods.setWidth100();
        entityMethodsForm.setWidth(300);  
        DataSource ds = getDataSource(Integer.valueOf(ID_DS_METHODS)).getDataSource();
	 	entityMethodsForm.setDataSource(ds);
	 	entityMethodsForm.fetchData();
	 	
	 	if ( record==null) {	 		
	 		record = new Record();
	 		record.setAttribute("ID_DENTITY", getEditEntityId());					
	 		record.setAttribute("ID_DENTITY_ACTION",mdbEntityActionType.getValue());
	 		//record.setAttribute("NAME_ACTION",mdbEntityActionType.getValue());
	 		
	 		tabSql.setAttribute("edit_state", "insert");
	 		//ds.addData(record);
	 	}
	 	else {
	 		tabSql.setTitle(nameAction+"-"+ record.getAttributeAsString("ID_EXT_METHOD"));
	 		tabSql.setAttribute("edit_state", "update");
	 	}
	 	
	 	entityMethodsForm.editRecord(record);	 	
	 	tabSql.setAttribute("record", record);
	 	tabSql.setAttribute("frmEntityMethod", entityMethodsForm);
	 	
	 	methodDescriotionLayout.addMember(entityMethodsForm);
	 	/*Layout description */	 	
        
	 	/*SQL editor*/
	 	final CodeMirror editor = new CodeMirror();    
        editor.setHeight("90%");
        //editor.setWidth("100%");
        editor.addInitializeHandler(new InitializeHandler() {			
			@Override
			public void onInitialize(InitializeEvent event) {
				editor.setParser(CodeMirror.PARSER_SQL);
				editor.setTextWrapping(true);
				editor.setLineNumbers(true);				
				editor.setContent(methodBody);
			}
		});        
        _editorsForms.put(tabSql.getTitle(), editor);
        /*SQL editor*/
        
        
        /*Buttons and layout for buttons*/
        Button btnExecute = new Button();
        btnExecute.setTitle("Execute");
        btnExecute.addClickHandler(  new ExecuteMehodHandler());
        btnExecute.setIcon("icons/16/sql_execute16.bmp");
        
        Button btnSave = new Button();
        btnSave.setTitle("Save");
        btnSave.addClickHandler( new SaveEditMehodHandler() );
        btnSave.setIcon("icons/16/sql_scriptsave16.bmp");
        
        
        Button btnCancel = new Button();
        btnCancel.setTitle("Cancel");
        btnCancel.addClickHandler( new CancelEditMehodHandler() );
        btnCancel.setIcon("icons/16/rollback16.bmp");
        
        
        
        Layout btnsLayout = new HLayout();
        btnsLayout.setMargin(10);
        btnsLayout.setMembersMargin(10);
        btnsLayout.setHeight("10%");
        btnsLayout.addMember(btnExecute);
        btnsLayout.addMember(btnSave);
        btnsLayout.addMember(btnCancel);     
        /*Buttons and layout for buttons*/
        
        /*Main Layout*/        
        final Layout mainLayout = new HStack();        
        mainLayout.setWidth100();
        final Layout leftLayout = new VStack();        
        leftLayout.setVisible(true);
        leftLayout.setWidth("20%");
        leftLayout.setMembersMargin(10);
        leftLayout.setShowEdges(true);
        leftLayout.addMember(btnsLayout );	 		 	                
        leftLayout.addMember(methodDescriotionLayout);
	 	
        editor.setWidth("75%");
        mainLayout.addMember(editor);
        mainLayout.addMember(leftLayout);		 		 		 	
	 	
        tabSql.setPane(mainLayout);        
        return tabSql;        
	}	
	

	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.DataView#view()
	 */
	@Override
	public void bindDataComponents() {		
		final String calNameAction = "NAME_ACTION";
		final String colMethodBody = "METHOD_BODY";
		final String colEntityActionId = "ID_DENTITY_ACTION";				
				
		IDataSource dsEntityEdit = getDataSource(Integer.valueOf(ID_DS_METHODS));		
		
		if (dsEntityEdit != null && dsEntityEdit.getData()!= null) {
			
			for (Record record  : dsEntityEdit.getData()) {								
				
				_tabSetSql.addTab( createSqlEditor(EMdbEntityActionType.fromInt( Integer.parseInt(record.getAttribute (colEntityActionId)) ),
													record,
													record.getAttribute(calNameAction), 
													record.getAttribute(colMethodBody) ) );
			}
		}
		
		_tabSetSql.addTabSelectedHandler(new SqlTabSelectedHandler ());
		if (_tabSetSql.getTabs().length >0) {
			Record rec = _tabSetSql.getSelectedTab().getAttributeAsRecord("record");		
			changeActiveMethod(rec);
		}        
	}


	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.DataView#requestData()
	 */
	@Override
	public void prepareRequestData() {		
		
    	IRequestData entity = getDataBinder().getDataProvider().getRequest().add(new RequestEntity (ID_DS_METHODS));
    	entity.getParams().add("ID_DENTITY", _editEntityId);
    	entity.getParams().add("ID_DENTITY_ACTION", "");
    	entity.setExecuteType(ExecuteType.GetData);
    	
    	_frmEntityDefinition.prepareRequestData();
    	_frmEntityDefinition.putRequestToQueue();
	}

	
	@Override
	public  String getCaption() {		
		String imgHTML = Canvas.imgHTML(getIconName(), 16, 16);
		return "<span>" + imgHTML + "&nbsp;" + DESCRIPTION +" "+_editEntityId + "</span>";
	}
	
	protected String getIconName() {
		return "silk/plugin.png";
	}	
	
	
	@Override
	 protected void createMenu () {
		
	  new MenuCreateNewEntityMethod(getMenuContainer(), new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					if ( isCanCreateMethod(sender.getCaption()) ) {
						
						Tab tab = createSqlEditor(EMdbEntityActionType.fromInt(Integer.parseInt( sender.getAttribute("ID")) ), 
												null, sender.getCaption(), null);						
						_tabSetSql.addTab(tab );
					}					
				}
			});		 
	   }

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.data.IDataView#getSelectedRecord()
	 */
	@Override
	public Record getSelectedRecord() {		
		return null;
	}
	
	@Override
	public Record[] getSelectedRecords() {		
		return null;
	}
	
	@Override
	public boolean isSelectedRecord() {		
		return getSelectedRecord() != null;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#showFilterView()
	 */
	@Override
	public void showFilterView() {				
	}
	
	private void changeActiveMethod(Record activeRec) {
		if (activeRec != null) {		
			
			EMdbEntityActionType mdbEntityActionType =EMdbEntityActionType.fromInt( Integer.parseInt(activeRec.getAttribute ("ID_DENTITY_ACTION")));			
			
			if ( Arrays.asList (EMdbEntityActionType.SELECT, EMdbEntityActionType.STORED_PROC, EMdbEntityActionType.PREPERE_FILTER).contains (mdbEntityActionType) ) {				
						
				_viewFieldsEditor.addCustomProperty("ID_DENTITY", getEditEntityId());		
				_viewFieldsEditor.addCustomProperty("ID_EXT_METHOD", activeRec.getAttribute("ID_EXT_METHOD"));
				  
		    	_viewFieldsEditor.callRequestData();	
			}				
		}
	}
	



}
