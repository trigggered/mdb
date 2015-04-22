/**
 * 
 */
package mdb.ui.client.view.editor;

import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.Menu;
import mdb.core.ui.client.view.data.DataView;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;

/**
 * @author azhuk
 * Creation date: Mar 1, 2013
 *
 */
public class EntityDefinition extends DataView {
	private static final Logger _logger = Logger
			.getLogger(EntityDefinition.class.getName());

	private final int ENTITY_DEF_ID  = 21;
	private final int ENTITY_KEYS_ID = 22;
		
	//private String _entityId;
	
 	private DynamicForm _frmEntity ;
 	private DynamicForm _frmEntitySeq;
 	


private class Menu1 extends Menu {	
		public Menu1 () {
			super("Menu1");
			createButtons();
		}
		
		private void createButtons () {
			
			IMenuItem item = super.addItem(IMenuItem.ItemType.ToolButton);
			item.setCaption("Save All ");		
			item.setShowCaption(isShowCaption());
			item.setShowHint(isShowHint());		
			item.setCommand(new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					save();
					
				}
			});
			
			item= super.addItem(IMenuItem.ItemType.ToolButton);
			item.setCaption("Save Entity info ");		
			item.setShowCaption(isShowCaption());
			item.setShowHint(isShowHint());		
			item.setCommand(new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					//save();
					saveEntityInfo();
				}
			});						
			
			item= super.addItem(IMenuItem.ItemType.ToolButton);
			item.setCaption("Save Sequence into  ");		
			item.setShowCaption(isShowCaption());
			item.setShowHint(isShowHint());		
			item.setCommand(new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					saveSeqPkInfo();
				}
			});
			
		}
	}

	

    @Override
    protected void  createComponents() {
    	super.createComponents();
    	setImgCaption("icons/16/view16.bmp");
     	_frmEntity = new DynamicForm();
	 	_frmEntity.setWidth100(); 	        
	 	_frmEntity.setHeight("90%");
        
	 	_frmEntitySeq = new DynamicForm();
	 	_frmEntitySeq.setWidth100(); 	        
	 	_frmEntitySeq.setHeight("90%");
	 	
	 	getViewPanel().addMember(_frmEntity);
	 	getViewPanel().addMember(_frmEntitySeq);
        
	 	
	}
    	 
	 
	 @Override
		public void prepareRequestData() {	

		 	String entityId = String.valueOf(getCustomProperty("ID_DENTITY"));
		 	_logger.info("Try request entity definition for:"+entityId);
		 
			IRequestData re = getDataBinder().getDataProvider().getRequest().add(new RequestEntity(ENTITY_DEF_ID));
			re.setExecuteType(ExecuteType.GetData);			
			re.getParams().add("pID_DENTITY",  entityId);
			
			
			IRequestData reKeys = getDataBinder().getDataProvider().getRequest().add(new RequestEntity(ENTITY_KEYS_ID));
			reKeys.setExecuteType(ExecuteType.GetData);			
			reKeys.getParams().add("pID_DENTITY",  entityId );						
		}

		/* (non-Javadoc)
		 * @see mdb.core.ui.view.data.DataView#bindDataComponents()
		 */
		@Override
		public void bindDataComponents() {		 			
			
			_frmEntity.setDataSource(getDataSources().get(ENTITY_DEF_ID).getDataSource());
			_frmEntity.fetchData();
			
			_frmEntitySeq.setDataSource(getDataSources().get(ENTITY_KEYS_ID).getDataSource());
			_frmEntitySeq.fetchData();
			
		}

		
	 
	@Override
	public int getMainEntityId() {
			return ENTITY_DEF_ID;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#showFilterView()
	 */
	@Override
	public void showFilterView() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#getSelectedRecord()
	 */
	@Override
	public Record getSelectedRecord() {	
		return null;
	}

	@Override
	public Record[] getSelectedRecords() {	
		return null;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#isSelectedRecord()
	 */
	@Override
	public boolean isSelectedRecord() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.view.data.IDataView#removeData()
	 */
	@Override
	public void removeData() {
		// TODO Auto-generated method stub

	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.view.BaseView#createMenu()
	 */
	@Override
	protected void createMenu() {
		IMenuContainer container =  getMenuContainer();	
		container.bind(new Menu1());
	}
	
	private void saveEntityInfo() {
		
		_frmEntity.saveData();		
		getDataSource(ENTITY_DEF_ID).save();
	}
	
	private void saveSeqPkInfo() {
		
		if (_frmEntitySeq.isNewRecord()) {
			
			_frmEntitySeq.setValue("ID_DENTITY",String.valueOf( getCustomProperty("ID_DENTITY")));
			Record  rec = _frmEntity.getValuesAsRecord();
			String k = rec.getAttribute("ID_DENTITY");
			_logger.info("ID_DENTITY ="+k);
		}
		_frmEntitySeq.saveData();
		getDataSource(ENTITY_KEYS_ID).save();
	}
	
	@Override
	public void save() {	
		saveEntityInfo();
		saveSeqPkInfo();
	}	
}
