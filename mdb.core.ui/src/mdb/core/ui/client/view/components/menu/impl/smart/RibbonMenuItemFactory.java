/**
 * 
 */
package mdb.core.ui.client.view.components.menu.impl.smart;

import java.util.List;

import mdb.core.ui.client.events.IChangeEvent;
import mdb.core.ui.client.events.IChangeHandler;
import mdb.core.ui.client.view.components.menu.IMenuItem;
import mdb.core.ui.client.view.components.menu.IMenuItemFactory;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IconButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.menu.IconMenuButton;

import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

/**
 * @author azhuk
 * Creation date: Dec 25, 2012
 *
 */
public class RibbonMenuItemFactory implements IMenuItemFactory {

	/* (non-Javadoc)
	 * @see mdb.ui.client.common.view.components.menu.IMenuItemFactory#Create(mdb.ui.client.common.view.components.menu.IMenuItem)
	 */
	@Override
	public Canvas create(final IMenuItem item) {
		Canvas toReturn = null;
		//Button button;
		
		switch (item.getItemType() ) {
									
		case Menu:{
			IconMenuButton button =new IconMenuButton();
			//  
			button.setTitle(item.getCaption());
			button.setLargeIcon(item.getImg());
			button.setOrientation("vertical"); 
			toReturn = button;
			break;					
		}
		case ToolButton:
		case MenuItem: {
			IconButton  button =  new IconButton();
			 if (item.isShowCaption() ) {
				 //button.setShowTitle(item.isShowCaption() );
				 button.setTitle(item.getCaption());
			 }
			 if ( item.isShowHint() ) {
				 button.setPrompt(item.getHint());
			 }
			 button.setIcon(item.getImg());
			 //button.setShowTitle(item.isShowCaption());			 
			 button.addClickHandler((ClickHandler) item);
			 button.setOrientation("vertical");
			 toReturn = button;
			 
			break;
		}
			
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

			selItem.setShowTitle(item.isShowCaption());			
			selItem.setTitle(item.getCaption());	
			selItem.setShowHint(item.isShowHint());
			selItem.setWidth(item.getWidth());
			
			if (item.getValueMap()!= null) {			
				selItem.setValueMap(item.getValueMap());
				selItem.setValue(item.getValue());
			}			
			
			selItem.addChangeHandler(new ChangeHandler() {				
				@Override
				public void onChange(ChangeEvent event) {
					if (item.getComand() != null) 
					{
						item.setValue(selItem.getValueAsString());
						item.getComand().execute(item);	
					}	
				}
			});
			
			DynamicForm dynamicForm = new DynamicForm();
		    dynamicForm.setCellPadding(3);
		    dynamicForm.setWidth(item.getWidth());
		    dynamicForm.setNumCols(1);
		    dynamicForm.setFields(selItem);
			toReturn = dynamicForm;		
			
			break;
		}
		case NumberItem:	{			
			final IntegerItem  formItem =  new IntegerItem();
			
			formItem.setShowTitle(item.isShowCaption());			
			formItem.setTitle(item.getCaption());	
			formItem.setShowHint(item.isShowHint());
			formItem.setHint(item.getHint());						
			formItem.setWidth(item.getWidth());			
			formItem.setValue(item.getValue());
			
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
						if (item.getComand() != null) 
						{
							item.setValue(formItem.getValueAsString());
							item.getComand().execute(item);	
						}	
					}						
				}
			});
			
			DynamicForm dynamicForm = new DynamicForm();
		    dynamicForm.setCellPadding(3);
		    dynamicForm.setWidth(item.getWidth());
		    dynamicForm.setNumCols(1);
		    dynamicForm.setFields(formItem);
			toReturn = dynamicForm;			
			break;
		}		
			
		default:
			return null;				
	}									
		
		return toReturn;			
	}
	
	public Canvas createAndRefSubMenu(IMenuItem item) {
		
		if ( item.getChildMenu()!= null ) {			
			
			IconMenuButton menu = (IconMenuButton)create(item);			
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
				 //createAndRefSubMenu (item);
				 
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
					if (item.getComand() != null) { 
						item.getComand().execute(item);	
					}					
				}			
			});			
			 
			 toReturn.addItem(menuItem);			 
			 }
		 }
		
		return toReturn;
	}
	
	
}

