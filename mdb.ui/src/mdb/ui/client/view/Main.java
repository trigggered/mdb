package mdb.ui.client.view;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.data.DataConverter;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.data.bind.impl.BindSource;
import mdb.core.ui.client.data.bind.impl.DataBinder;
import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.view.components.menu.IMenu;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.data.TreeMenu;
import mdb.core.ui.client.view.components.menu.mdb.IMdbMainMenuAction;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.data.tree.ITreeView;
import mdb.core.ui.client.view.debug.DebugMenu;
import mdb.core.ui.client.view.dialogs.message.Dialogs;
import mdb.core.ui.smartgwt.client.data.IDataSource;
import mdb.core.ui.smartgwt.client.view.AMainView;
import mdb.core.ui.smartgwt.client.view.dialogs.input.InputValueDialog;
import mdb.core.ui.smartgwt.client.view.dialogs.message.MessageDialogs;
import mdb.ui.client.communication.rpc.mdbgw.MdbGWQueueProcessor;
import mdb.ui.client.view.editor.EntityEditor;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;


public class Main extends AMainView  implements ITreeView {
	

    private static final Logger _logger = Logger.getLogger(Main.class.getName());

	private final int  TREE_ENTITY_ID = 41;
	private final int  ENTITY_EDIT_ID = 21;
    
    
    private TreeGrid _sideNavTree;    	

    private final int APP_ID =0;
    
    /* (non-Javadoc)
	 * @see mdb.core.ui.view.IMainView#getAppId()
	 */
	@Override
	public int getAppId() {
		return APP_ID;
	}

	
    @Override
    protected void registerGatewayQueue() {		
		GatewayQueue.instance().setProcessor(new MdbGWQueueProcessor());
		Dialogs.regiserMessageDialogs(new MessageDialogs());
	}  
    
	
	
    @Override
	protected void createComponents () {   	
	    	createMainLayout();	        
	        getMainLayout().setMembersMargin(10);
	        if (isCreateMenuContainer()) {        	
		        createMenuContainer();        	        		        	
		        createMenu ();	                
	        }       
	                
	        initSize();
	        
	        Layout lay = new HLayout();
	        lay.addMembers( createTree(),  createWindowManagerComponent());
	        
	        getMainLayout().addMember(lay);      	        	       
	}  
	   
    
	@Override
	public void onModuleLoad() {
		super.onModuleLoad();
		callRequestData();
	}
	
    private DynamicForm createSpeedOpenSqlEditorForm() {
		final DynamicForm form = new DynamicForm();		
		form.setLayoutAlign(VerticalAlignment.BOTTOM);
		form.setNumCols(2);
		form.setIsGroup(true);
		form.setGroupTitle ("Find");
		
		final IntegerItem intItemEntityId = new IntegerItem();
		intItemEntityId.setWidth("100%");		
		intItemEntityId.setTitle("Entity ID");
		intItemEntityId.setRequired(true);
		intItemEntityId.setEndRow(false);
		//intItemEntityId.setValidators(validators)
		
		
		intItemEntityId.addKeyPressHandler( new KeyPressHandler() {			
			@Override
			public void onKeyPress(KeyPressEvent event) {									
				if (  (event.getKeyName().equalsIgnoreCase("Enter") ) &&  (form.validate())  )  {
					openEntityEditor(intItemEntityId.getValueAsString());
				}				
			}
		});	
		
		ButtonItem btnItemGo = new ButtonItem();
		btnItemGo.setTitle("Go");
		btnItemGo.setWidth(80);
		btnItemGo.setColSpan(2);
		btnItemGo.setAlign(Alignment.RIGHT);
		btnItemGo.setStartRow(false);
		btnItemGo.setEndRow(false);
		
		
		btnItemGo.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler () {
			@Override
			public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {				
				if ( form.validate() ) {						
					openEntityEditor(intItemEntityId.getValueAsString());
				}
			}
      });		
	  form.setFields(intItemEntityId, btnItemGo);
	  return form;
	}	

    
    private void openEntityEditor(String entityId) {
    	if (Integer.valueOf(entityId).intValue() <9000000 ) { 	
    		openViewInTab(new EntityEditor(entityId));    	    
    	}
    }   
    
    
	
    @Override
    public  void prepareRequestData() {    	        	
    	IRequestData entity = getDataBinder().getDataProvider().getRequest().add(new RequestEntity(TREE_ENTITY_ID));
    	entity.setExecuteType(ExecuteType.GetData);    	  	    	
    }
    
    
    
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.common.IDataView#view()
	 */
	@Override
	public void bindDataComponents() {
		_logger.info("Start bind data to visual component");
		 bindTree() ;
		 _logger.info("End bind data");
	}
	
	
	private Menu getTreeContextMenu() {
		final Menu contextMenu = new Menu();		
		contextMenu.addItem(new MenuItem ("Collapse All") );
        contextMenu.addItem(new MenuItem("Expand node"));
        contextMenu.addItem(new MenuItem("Open Entity Editor"));
        contextMenu.addItem(new MenuItem ("New Folder") );
        contextMenu.addItem(new MenuItem ("New Entity") );
        contextMenu.addItem(new MenuItem ("Add Methods to Entity") );
        contextMenu.addCellClickHandler(new CellClickHandler() {
            public void onCellClick(CellClickEvent event) {
            	
            	switch (event.getRowNum()) {
            	
            	case 0: 
            		// Collapse All
            		_sideNavTree.getData().closeAll();            		
            		break;
            		//Expand node
            	case 1: //open the selected node
                    Tree tree = _sideNavTree.getData();
                    ListGridRecord record = _sideNavTree.getSelectedRecord();
                    TreeNode node = (TreeNode) record;
                    //TreeNode[] children = tree.getChildren(node);
                    tree.openFolder(node);                    
                    break;
                    
                    //Open Entity Editor
            	case 2:
            	case 5:            		
            		String id =  _sideNavTree.getSelectedRecord().getAttribute("ID");	                
                    openEntityEditor(id);
            		break;
            	
            		// New Folder
            	case 3:    
            		newFolder(null);
            		break;
            		
            		//New Entity
            	case 4:
            		newEntity();            		
            		break;
            		
            	 	
            	}                                   
            }
        });
        return contextMenu;
	}
	
	private Layout createTree() {
		_sideNavTree = new TreeGrid();		
		_sideNavTree.draw();    	
		_sideNavTree.addCellDoubleClickHandler(new CellDoubleClickHandler() {			
			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
					String id =  event.getRecord().getAttribute("ID");	                
	                openEntityEditor(id);
			}
		});
		
		   	_sideNavTree.setContextMenu(getTreeContextMenu());	       
			_sideNavTree.addCellContextClickHandler(new CellContextClickHandler() {			
				@Override
				public void onCellContextClick(CellContextClickEvent event) {
					if ( event.isRightButtonDown() ) {
						event.cancel();
						_sideNavTree.getContextMenu().showContextMenu();										
					}
					else {
						
					}
					
				}
			});       

			
			Layout sideNavLayout = new VLayout();
	        sideNavLayout.setHeight100();
	        sideNavLayout.setWidth(185);
	        sideNavLayout.setShowResizeBar(true);
	                
			sideNavLayout.addMember(createSpeedOpenSqlEditorForm() );
	    	sideNavLayout.addMember(_sideNavTree);		
	    	//getMainLayout().addMember(sideNavLayout,0);
	    	return sideNavLayout;
	}
	
	
	private void bindTree() {									
		
		DataSource ds =((IDataSource)getMainDataSource()).getDataSource();
		if (!ds.isCreated() ) {
			ds.setTitleField("TITLE");
		}
    	ds.getField("ID_PARENT").setForeignKey("ID");
    	ds.getField("ID_PARENT").setRootValue("");	    
        _sideNavTree.setDataSource(ds);
        _sideNavTree.fetchData();
	}
	

	
	private void newFolder(final ICallbackEvent<TreeNode> callbackEvent) {
				
		TreeNode selectedNode =  _sideNavTree.getSelectedRecord();
		final boolean isSelectedNode = selectedNode!=null;		
		final String currentNodeId = isSelectedNode?selectedNode.getAttribute("ID"):null;
		
		
		final TreeNode node = new TreeNode();
		node.setAttribute("ID", "-1");
		
		if ( !isSelectedNode || Integer.valueOf(currentNodeId) < 9000000) {						
		
		InputValueDialog dlg = new InputValueDialog(new ICallbackEvent<String>() {			
			@Override
			public void doWork(final String data) {				
				final Tree tree = _sideNavTree.getData();		
				final TreeNode parentNode  = tree.findById(currentNodeId);
					
				
				final IDataBinder binder = new DataBinder(new BindSource() {					
					@Override
					public void bindDataComponentsAfterChange()  {							
						
					IRequestData entity = getDataProvider().getResponse().get(String.valueOf(ENTITY_EDIT_ID) );
					Map<String,String> map= DataConverter.jsonToMap(entity.getData());								
						node.setAttribute("ID", map.get("ID_DENTITY"));
						node.setAttribute("NAME", data);
						node.setAttribute("TITLE", map.get("ID_DENTITY")+"-"+data);
						
						tree.add(node, parentNode);
						_sideNavTree.redraw();	
						if (callbackEvent != null) {
							callbackEvent.doWork(node);
						}
					}

					@Override
					public void bindDataComponents() {						
					}
				}, getDataProvider());					
			
				
		    	
				RequestEntity entity = (RequestEntity) binder.getDataProvider().getRequest().add(new RequestEntity(ENTITY_EDIT_ID));
		    	entity.setExecuteType(ExecuteType.ChangeData);		    	
		    	GatewayQueue.instance().put(binder.getDataProvider());
		    	
		    	@SuppressWarnings("serial")
				String jsonData  = DataConverter.map2Json(new HashMap<String,String>() {{
																		put("ID_DENTITY", "-1");																		
																		put("NAME_DENTITY", data);
																		put("NOTE_DENTITY", data);
																		put("ID_PARENT_DENTITY", isSelectedNode?currentNodeId:"");
																	}});		    	
		    	entity.addForInsert(null, jsonData);		    
		    	GatewayQueue.instance().getProcessor().run();
		    		    	  			    	
			}
		});
		dlg.show();				
		} else {
			Dialogs.ShowWarnMessage("Please select  EntityNode");			
		}
	}
	
	private void newEntity() {
		newFolder(new ICallbackEvent<TreeNode>() {
			
			@Override
			public void doWork(TreeNode data) {
				openEntityEditor(data.getAttribute("ID"));
			}
		});				
	}



	

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.BaseView#createMenu()
	 */
	@Override
	protected void createMenu() {
		
        class MainMenu extends  mdb.core.ui.client.view.components.menu.Menu {
				public MainMenu() {
							super("MainMenu");
									addSpacer(6);
									addItem("", "pieces/24/cube_green.png", IMenuItem.ItemType.ToolButton, 0);
									addSpacer(6);
									IMenuItem lblItem = addItem("MDB developer console","", IMenuItem.ItemType.Label,0);
									lblItem.setCaption("MDB developer console");					
									lblItem.setStyleName("sgwtTitle");
									lblItem.setWidth(250);
									addSeparator();	
				}
        };
        
        
        class MenuFile extends mdb.core.ui.client.view.components.menu.Menu {
    		
    		public MenuFile() {
    			super("MenuFile");
    			
    			IMenuItem item = addItem("File", "", IMenuItem.ItemType.Menu,0);
    			IMenu childMenu = new mdb.core.ui.client.view.components.menu.Menu("Child menu for 'File'");
				item.setChildMenu(childMenu);
			
			
				item = childMenu.addItem("Open data by Entity ID", "", IMenuItem.ItemType.ToolButton,0);
				item.setCommand(new ICommand<IMenuItem>() {						
					@Override
					public void execute(IMenuItem sender) {
						
						InputValueDialog dlg = new InputValueDialog(new ICallbackEvent<String>() {			
							@Override
							public void doWork(final String data) {
								
								IDataView view =new DataViewByEntity(Integer.valueOf(data).intValue());
								//view.setProperty("ENTITY_ID", data);
								openViewInTab(view);
								
							}
						});				
						
						dlg.show("Entering Entity ID", "Please enering entityID for request data");							
					}
				});
				
				item = childMenu.addItem("File upload", "", IMenuItem.ItemType.ToolButton,0);
				item.setCommand(new ICommand<IMenuItem>() {						
					@Override
					public void execute(IMenuItem sender) {
						//openViewInTab(new FileUploadView());
						
					}
				});																		
    		}
    	};        	
        
        
        getMenuContainer().bind(new MainMenu());
        getMenuContainer().bind(new TreeMenu(this));
        getMenuContainer().bind(new MenuFile());        
        //getMenuContainer().bind(new AuthoriseMenu());
        getMenuContainer().bind(new DebugMenu());
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.BaseMainView#getMdbMainMenuActionImpl()
	 */
	@Override
	protected IMdbMainMenuAction getMdbMainMenuActionImpl() {
		return null;
	}

	
	@Override
	public int getMainEntityId() {
		return TREE_ENTITY_ID;		
	}

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.data.IDataView#getSelectedRecord()
	 */
	
	public Record getSelectedRecord() {		
		return _sideNavTree.getSelectedRecord();
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.view.AMainView#loadTopBarLogo()
	 */
	@Override
	protected void loadTopBarLogo() {		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.tree.ITreeView#expandTree()
	 */
	@Override
	public void expandTree() {
		_sideNavTree.getData().openAll();		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.tree.ITreeView#collapseTree()
	 */
	@Override
	public void collapseTree() {
		_sideNavTree.getData().closeAll();		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.tree.ITreeView#findNodeById(java.lang.String)
	 */
	@Override
	public void findNodeById(String id) {
				
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.tree.ITreeView#refresh()
	 */
	@Override
	public void refreshTree() {
		super.callRequestData();
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#isSelectedRecord()
	 */
	@Override
	public boolean isSelectedRecord() {
		// TODO Auto-generated method stub
		return false;
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#callDeleteEvent()
	 */
	@Override
	public void callDeleteEvent() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.IDataView#getSelectedRecordJSON()
	 */
	@Override
	public String getSelectedRecordJSON() {
		// TODO Auto-generated method stub
		return null;
	}




	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#clearEvents(mdb.core.ui.client.view.data.IDataView)
	 */
	@Override
	protected void clearEvents(IDataView<Record> view) {
		// TODO Auto-generated method stub
		
	}




}