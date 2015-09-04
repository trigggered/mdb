/**
 * 
 */
package mdb.ui.client.view.editor;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.Menu;
import mdb.core.ui.smartgwt.client.view.data.grid.GridView;
import mdb.core.shared.data.Params;

/**
 * @author azhuk
 * Creation date: Jan 28, 2013
 *
 */
public class EntityData extends GridView {
	

	Params _params;
	
	private class Menu1 extends Menu {
		
		public Menu1 () {
			super("Menu1");
			createButtons();
		}
		
		private void createButtons () {
			
			IMenuItem item = super.addItem(IMenuItem.ItemType.ToolButton);
			item.setCaption("Create default definition ");		
			item.setShowCaption(isShowCaption());
			item.setShowHint(isShowHint());		
			item.setCommand(new ICommand<IMenuItem>() {
				
				@Override
				public void execute(IMenuItem sender) {
					_entityFieldsEditor.addNewFieldsDefine (getMainDataSource().getDataSource().getFields());	
				}
			});						
		}
	}		
	
	
	private FieldsEditor _entityFieldsEditor;
	
	
	
	@Override
	public void prepareRequestData() {
		IRequestData re = getDataBinder().getDataProvider().getRequest().add(new RequestEntity(getMainEntityId()));
		re.setRequestFieldsDescription(false);
		re.setParams(_params);
	}
	
	
	public EntityData (FieldsEditor entityFieldsEditor) {
		_entityFieldsEditor =entityFieldsEditor;
	}
	
	
	@Override
	protected void createComponents() {
		super.createComponents();
		setImgCaption( "icons/16/table16.bmp");
	}
	
	
	
	@Override
	public String getCaption() {
		return "Entity Data";
	}
	
	@Override
	protected void createMenu () {
		IMenuContainer container =  getMenuContainer();	
		container.bind(new Menu1());
		super.createMenu();
	}	
	
	public void setRequestParams(Params params) {
		_params = params;		
	}
	
	
}
