/**
 * 
 */
package mdb.core.ui.client.view.components.menu.data;

import java.util.HashMap;

import mdb.core.ui.client.command.ICommand;
import mdb.core.ui.client.data.IDataNavigator;
import mdb.core.ui.client.data.IDataPaging;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.view.components.Component;
import mdb.core.ui.client.view.components.ISimpleVisualComponent;
import mdb.core.ui.client.view.components.menu.IMenuButtons.Buttons;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.Menu;
import mdb.core.ui.client.view.data.IDataView;


/**
 * @author azhuk
 * Creation date: Dec 12, 2012
 *
 */
public abstract class AMenuData extends  Menu   {

	private HashMap<IDataNavigator.Buttons, IMenuItem>  _buttons = new HashMap<IDataNavigator.Buttons, IMenuItem>();	
	private static HashMap<IDataNavigator.Buttons, ISimpleVisualComponent>  _buttonInitialiser;
	private IDataSource _dataSource;
	private IDataView _view;

	
	static {
	    _buttonInitialiser= new HashMap<IDataNavigator.Buttons, ISimpleVisualComponent>();
	  
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataInsert, new Component(Captions.ADD, "dataNavigator/add.png"));	  
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataEdit, new Component(Captions.EDIT, "dataNavigator/edit2.png"));
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataDelete,new Component(Captions.DELETE, "dataNavigator/delete.png"));		
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataSave, new Component(Captions.SAVE, "dataNavigator/save.png"));
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataCancel,new Component(Captions.CANCEL, "dataNavigator/cancel.png"));
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataRefresh,new Component(Captions.REFRESH, "dataNavigator/refresh.png"));				
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataPrint,new Component(Captions.PRINT, "dataNavigator/printer.png"));
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataFilter,new Component(Captions.FILTER, "dataNavigator/find.png"));
		  
		  getButtonInitialiser().put(IDataPaging.Buttons.dataFirstPage,new Component(Captions.FIRST_PAGE, "dataNavigator/resultsetfirst.png"));
		  getButtonInitialiser().put(IDataPaging.Buttons.dataPreviousPage,new Component(Captions.PREVIOUS_PAGE, "dataNavigator/resultsetprevious.png"));	  
		  getButtonInitialiser().put(IDataPaging.Buttons.dataInputPageNumber,new Component(Captions.PAGE, ""));	  
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataNextPage,new Component(Captions.NEXT_PAGE, "dataNavigator/resultsetnext.png"));				
		  getButtonInitialiser().put(IDataNavigator.Buttons.dataLastPage,new Component(Captions.LAST_PAGE, "dataNavigator/resultsetlast.png"));
		  
		  getButtonInitialiser().put(IDataNavigator.Buttons.treeCollapse,new Component(Captions.COLLAPSE, "dataNavigator/GreenUp.png"));				
		  getButtonInitialiser().put(IDataNavigator.Buttons.treeExpand,new Component(Captions.EXPAND, "dataNavigator/GreenDown.png"));		  	 
		  getButtonInitialiser().put(IDataNavigator.Buttons.download,new Component(Captions.DOWNLOAD , "icons/16/download.png"));
		 
	}
	
	public AMenuData(String name, IDataSource dataSource, IDataView view) {
		super(name);
		
		_view = view;
		if (dataSource!=null) {			
			setDataSource(dataSource);	
		}
	}
	
	protected IDataView getView() {
		return _view;
	}
	
	public void setDataSource (IDataSource dataSource) {		
		_dataSource = dataSource;
		if (_dataSource != null) {
			createDefaultButtons();
		}		
	}
	
	public IDataSource getDataSource() {
		return _dataSource;
	}	

	
	protected abstract void createDefaultButtons();
	public abstract ICommand<IMenuItem> getButtonCommand(Buttons button);
	
			
	protected void createButton (IDataNavigator.Buttons button) {
		
		IMenuItem item = this.addItem(IMenuItem.ItemType.ToolButton);
		initPropertyMenuItem(item, button);
		
		item.setShowCaption(isShowCaption());
		item.setShowHint(isShowHint());		
		item.setCommand(getButtonCommand(button));
		_buttons.put(button, item) ;
	}
	
	
	public static void initPropertyMenuItem (IMenuItem item, IDataNavigator.Buttons button) {				
		item.setCaption(getButtonInitialiser().get(button).getCaption());
		item.setHint(getButtonInitialiser().get(button).getCaption());
		item.setImg(getButtonInitialiser().get(button).getImg());		
	}		
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.data.IDataNavigator#getButton(mdb.core.ui.data.IDataNavigator.Buttons)
	 */
	
	public IMenuItem getButton(Buttons button) {
		return _buttons.get(button);
	}

	public static HashMap<IDataNavigator.Buttons, ISimpleVisualComponent> getButtonInitialiser() {
		return _buttonInitialiser;
	}
	
	protected HashMap<IDataNavigator.Buttons, IMenuItem>  getButtons() {
		return _buttons;
	}
	
}
