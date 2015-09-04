/**
 * 
 */
package mdb.core.ui.smartgwt.client.components.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mdb.core.ui.client.events.IButtonClickEvent;
import mdb.core.ui.client.events.IChangeEvent;
import mdb.core.ui.client.events.IChangeHandler;
import mdb.core.ui.client.view.components.menu.IMenuContainer;
import mdb.core.ui.client.view.components.menu.IMenuContainer.EContaynerOriented;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.smartgwt.client.data.fields.editors.ButtonItemEditor;

import com.smartgwt.client.types.DateItemSelectorFormat;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.StretchImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public class ToolStripMenuItemFactory implements IMenuItemFactory, IMenuFactory {

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.menu.IMenuItemFactory#Create(mdb.ui.client.common.view.components.menu.IMenuItem)
	 */
	
	static IMenuItemFactory _factory; 
	
	public static IMenuItemFactory instance() {
		if (_factory == null) {
			_factory = new ToolStripMenuItemFactory();
		}
		return _factory;
	}
	
	@Override
	public Canvas create(final IMenuItem item) {
		Canvas toReturn = null;
		StretchImgButton button;
		
		switch (item.getItemType() ) {
									
		case Menu: /*Parent Menu */
			button = new ToolStripMenuButton();
			button.setTitle(item.getCaption());
		    button.setIcon(item.getImg());
			toReturn = button;
			break;					
			
		case ToolButton:
		case MenuItem:
			 button = new ToolStripButton();
			 if (item.isShowCaption() ) {
				 button.setShowTitle(item.isShowCaption() );
				 button.setTitle(item.getCaption());				 
			 }
			 if ( item.isShowHint() ) {
				 button.setPrompt(item.getHint());
			 }
			 button.setIcon(item.getImg());

			 button.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {
					if (item.getCommand() != null) {
						item.getCommand().execute(item);
					}					
				}
			});			 
			 toReturn = button;
			 
			break;
			
		case Separator:
			toReturn = new ToolStripSeparator();			
			break;				
		case Label:
			toReturn = new Label();
			toReturn.setContents(item.getCaption());
			toReturn.setStyleName(item.getStyleName());
			toReturn.setWidth(item.getWidth());
			break;
		case Spacer:
			toReturn = new Canvas();
			toReturn.setWidth(1);
			toReturn.setHeight(1);
			toReturn.setBorder("none");			
			String extraSpace  = item.getAttribute("ExtraSpace");
			if(extraSpace!= null) {				
				toReturn.setExtraSpace(Integer.parseInt(extraSpace));
			}
			break;
			
		case Fill:
			toReturn = new LayoutSpacer();
			break;
			
		case Combobox: {
			final SelectItem  selItem = new SelectItem();
			toReturn = initializeFormItem(item,selItem, null, true);			
			break;
		}
		
		case DateItem:{
			DateItem dateItem = new DateItem();
			toReturn = initializeFormItem(item, dateItem, null, true);
			dateItem.setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);
			break;
		}
		case DateRange: {
			ChangedHandler changeHandler = new ChangedHandler()  {
				@Override
				public void onChanged(ChangedEvent event) {
					if (item.getCommand() != null) 
					{		
						item.setAttribute("dateRangeType", 
							event.getItem().getAttribute("dateRangeType"));
						item.setValue(event.getItem().getValue());
						item.getCommand().execute(item);	
					}						
				}
			};
			DateItem dateFromItem = new DateItem();
			dateFromItem.setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);
			dateFromItem.setAttribute("dateRangeType", "dateFrom");
			dateFromItem.addChangedHandler((ChangedHandler) changeHandler);
			toReturn = initializeFormItem(item, dateFromItem, null, true);
			if ( item.getValueMap() != null) {
				dateFromItem.setValue(item.getValueMap().get("dateFrom"));
			}
			dateFromItem.setTitle("С");
			
			DateItem dateToItem = new DateItem();
			dateToItem.setAttribute("dateRangeType", "dateTo");
			dateToItem.setSelectorFormat(DateItemSelectorFormat.DAY_MONTH_YEAR);
			dateToItem.addChangedHandler((ChangedHandler) changeHandler);
			toReturn = initializeFormItem(item, dateToItem, (DynamicForm)toReturn, true);
			if ( item.getValueMap() != null) {
				dateToItem.setValue(item.getValueMap().get("dateTo"));
			}
			dateToItem.setTitle("По");							
			
			break;
		}
		case NumberItem:	{			
			final IntegerItem  formItem =  new IntegerItem();
			toReturn = initializeFormItem( item, formItem, null, true );
			
			
			item.addChangeHandler(new IChangeHandler() {				
				@Override
				public void onChange(IChangeEvent event) {
					formItem.setValue(event.getValue());					
				}
			});
			
			
			formItem.addKeyPressHandler(new KeyPressHandler() {									
				@Override
				public void onKeyPress(KeyPressEvent event) {
					
					if (  (event.getKeyName().equalsIgnoreCase("Enter") ) /*&&  (event.getForm().validate())*/  )  {
						if (item.getCommand() != null) 
						{
							item.setValue(formItem.getValueAsString());
							item.getCommand().execute(item);	
						}	
					}						
				}
			});			
						
			break;
			
		}		
		case ButtonDataItem:
			final ButtonItemEditor buttonItemEditor = new ButtonItemEditor();
			toReturn = initializeFormItem( item, buttonItemEditor, null, false );
			buttonItemEditor.addButtonClickEvent(new IButtonClickEvent() {				
				@Override
				public void onClickEvent() {
					if (item.getCommand() != null) 
					{
						item.setValue(buttonItemEditor.getValueAsString());
						item.getCommand().execute(item);	
					}	
					
				}
			});
			
			buttonItemEditor.addKeyPressHandler(new KeyPressHandler() {									
				@Override
				public void onKeyPress(KeyPressEvent event) {
					
					if (  (event.getKeyName().equalsIgnoreCase("Enter") ) /*&&  (event.getForm().validate())*/  )  {
						if (item.getCommand() != null) 
						{
							item.setValue(buttonItemEditor.getValueAsString());
							item.getCommand().execute(item);	
						}	
					}						
				}
			});	
			
			
			break;
			
		default:
			return null;				
	}									
		
		return toReturn;			
	}
	
	private Canvas initializeFormItem(final IMenuItem item, final FormItem formItem, DynamicForm form, boolean addDefChangeEvent) {

		formItem.setShowTitle(item.isShowCaption());			
		formItem.setTitle(item.getCaption());	
		formItem.setShowHint(item.isShowHint());
		formItem.setWidth(item.getWidth());
		
		if (item.getValueMap()!= null) {			
			formItem.setValueMap(item.getValueMap());			
		}			
		formItem.setValue(item.getValue());		
		
		if (addDefChangeEvent && formItem.getHandlerCount(com.smartgwt.client.widgets.form.fields.events.ChangeEvent.getType()) ==0) {
			formItem.addChangedHandler(new ChangedHandler() {				
				@Override
				public void onChanged(ChangedEvent event) {
					if (item.getCommand() != null) 
					{												
						item.setValue(event.getItem().getValue());
						item.getCommand().execute(item);	
					}					
				}
			});
		}
		
		if (form == null) {
			form = new DynamicForm();
		}
		form.setAutoWidth();
		form.setAutoHeight();
		form.setNumCols(8);
		if (form.getFields() != null && form.getFields().length>0) {
			ArrayList<FormItem> fieldList = new ArrayList<FormItem>(Arrays.asList(form.getFields()));
			fieldList.add(formItem);
			
			FormItem[] items = new FormItem[fieldList.size()];  
			items = fieldList.toArray(items);
			
			
			form.setFields(items);
		}
		else  form.setFields(formItem, new SpacerItem());
		return form;		
	}
	
	
	
	public Canvas createAndRefSubMenu(IMenuItem item) {
		
		if ( item.getChildMenu()!= null ) {
			
			ToolStripMenuButton menu = (ToolStripMenuButton)create(item);			
			menu.setMenu(
					createSubMenu(item.getChildMenu().getItems())
					);
			
			return menu;			
		} 
		else  {
			return create(item);
		}
	}
	

	private com.smartgwt.client.widgets.menu.Menu createSubMenu ( List<IMenuItem> list)  {		
		
		com.smartgwt.client.widgets.menu.Menu toReturn = new com.smartgwt.client.widgets.menu.Menu();		
		 for (final IMenuItem item : list) {
			 
			 if (item.getChildMenu() !=null) {
		
				 
				 MenuItem menuItem = new MenuItem(item.getCaption() );
				 menuItem.setAttribute("ID", item.getAttribute("ID"));
				 
				 menuItem.setSubmenu(
				  createSubMenu (item.getChildMenu().getItems()));
				 toReturn.addItem(menuItem);
				 
			 }
			 else {
				 item.setItemType(IMenuItem.ItemType.MenuItem);
				 
				 MenuItem menuItem = new MenuItem(item.getCaption());			 
				 menuItem.setAttribute("ID", item.getAttribute("ID"));
				 menuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
				
				@Override
				public void onClick(MenuItemClickEvent event) {
					if (item.getCommand() != null) { 
						item.getCommand().execute(item);	
					}					
				}			
			});			
			 
			 toReturn.addItem(menuItem);			 
			 }
		 }
		
		return toReturn;
	}
	
	
	@Override
	public IMenuContainer createMenuContainer() {
		return new ToolStripMenuContainer();
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.components.menu.IMenuFactory#createMenuContainer(mdb.core.ui.client.view.components.menu.IMenuContainer.EContaynerOriented)
	 */
	@Override
	public IMenuContainer createMenuContainer(
			EContaynerOriented contaynerOriented) {		
		return createMenuContainer();
	}
	
	
}
