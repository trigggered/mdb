/**
 * 
 */
package mdb.ui.client.view.editor;

import java.util.logging.Logger;

import mdb.core.shared.data.EMdbFieldType;
import mdb.core.shared.data.Params;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.impl.fields.DataSourceFieldsBuilder;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItem.ItemType;
import mdb.core.ui.client.view.components.menu.Menu;
import mdb.core.ui.client.view.components.menu.mdb.MenuFieldViews;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.client.view.data.grid.GridView;
import mdb.core.ui.client.view.dialogs.edit.EditDialog;
import mdb.ui.client.view.DataViewByEntity;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;


/**
 * @author azhuk
 * Creation date: Jan 24, 2013
 *
 */
public class EntityFieldsEditor extends GridView {
	
	private static final Logger _logger = Logger.getLogger(EntityFieldsEditor.class.getName());
	
	private final int ID_ENTITY_FIELDS = 1275;
	//private MenuFieldViews _menuFieldViews = new MenuFieldViews(getMenuContainer());
	private MenuFieldViews _menuFieldViews;
	private String _enttityId;
	
	@Override
	protected void createComponents() {		
		super.createComponents();
		addCustomProperty("VIEW_NN", "1");
		setImgCaption("icons/16/properties16_h.bmp");
	}
	

	@Override
	public int getMainEntityId() {
		return ID_ENTITY_FIELDS;
	}
		
	@Override
	public void prepareRequestData() {		
		IRequestData re = getDataBinder().getDataProvider().getRequest().add(new  RequestEntity(getMainEntityId()));
		re.setExecuteType(ExecuteType.GetData);
		_enttityId = String.valueOf( getCustomProperty("ID_DENTITY") );
		
		if (_enttityId != null) {
			re.getParams().add("ID_DENTITY", _enttityId);
		}
		
		String extMethodId = String.valueOf( getCustomProperty("ID_EXT_METHOD"));
		
		if(extMethodId != null ) {
			re.getParams().add("ID_EXT_METHOD", extMethodId);			
			re.getParams().add("VIEW_NN", (String)getCustomProperty("VIEW_NN") );
		}
		_logger.info("Request field definitions for EntityId ="+_enttityId+" end methodId="+extMethodId);
		refreshMenuViewsNN ();				
	}


	private void refreshMenuViewsNN ()  {
		Params params = new Params();						
		params.add("ID_EXT_METHOD",String.valueOf( getCustomProperty("ID_EXT_METHOD")));
		
		_menuFieldViews.build( params);	
	}
	
	@Override
	public String getCaption() {
		return "Fields editor";
	}
	
	
	@Override
	public  void callInsertEvent() {		
		EditDialog.viewForNewRecord(getMainDataSource(), getNewFieldDefinitionRecord(),null);			
	}	
	
	
	 private Record  getNewFieldDefinitionRecord () {
			Record rec = new Record();
			rec.setAttribute("ID_EXT_METHOD", getCustomProperty("ID_EXT_METHOD"));
			rec.setAttribute("ID_DENTITY", getCustomProperty("ID_DENTITY"));
			rec.setAttribute("VIEW_NN", (String)getCustomProperty("VIEW_NN") );
			
			return rec;
	 }
	
	public void addNewFieldDefine (String name, EMdbFieldType mdbFieldType) {
	
		Record rec = getNewFieldDefinitionRecord();
		rec.setAttribute("NAME", name);
		rec.setAttribute("CAPTION", name);
		rec.setAttribute("VISIBLE", 1);
		rec.setAttribute("ID_FLD_TYPE", mdbFieldType.getValue());
		getMainDataSource().getDataSource().addData(rec);   	    	    	
		
	}
	
	public void addNewFieldsDefine (DataSourceField[] fields) {
		
		for (DataSourceField fld : fields ) {			
			EMdbFieldType fldType = DataSourceFieldsBuilder.convertFieldTypeJs2Mdb(fld.getType());
			
			addNewFieldDefine(fld.getName(), fldType);			
		}			
   	    getMainDataSource().save();
	}		
	
	
	@Override
	protected void createMenu() {	 	  
		super.createMenu();		
		
		IMenuContainer container =  getMenuContainer();		
		
		if (container!=null) {			
		
			_menuFieldViews = new MenuFieldViews(container);
			_menuFieldViews.addCommand(new ICommand<IMenuItem>() {				
				@Override
				public void execute(IMenuItem sender) {
					addCustomProperty("VIEW_NN", sender.getValue());
					callRequestData();				
				}
			});
			
			 Menu menu = new Menu("Test view");
			 IMenuItem menuItem = menu.addItem("Test View","icons/16/table16.bmp", ItemType.MenuItem, 0);
			 menuItem.setCommand( new ICommand<IMenuItem>() {
				
				@Override
				public void execute(IMenuItem sender) {					
					IDataView view =new DataViewByEntity(Integer.valueOf(_enttityId).intValue());					 					
					getMainView().openViewInTab(view);									
				}
			});			 		 
			 container.bind(menu);			 
		}
	}
	
}
