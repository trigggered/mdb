/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.dialogs;

import java.util.HashMap;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.dialogs.BaseDialogs;
import mdb.core.ui.client.view.dialogs.message.Dialogs;
import mdb.core.ui.smartgwt.client.data.IDataSource;


/**
 * @author azhuk Creation date: Oct 2, 2012
 * 
 */
public abstract class BaseDataDialog extends BaseDialogs {

	protected  IDataSource _dataSource; 
	private boolean _isRemouteSave = true;
	
	private  Window _wnd;
	private  Layout _btnlayout;
	
	HashMap<EButtons, Button> _buttons = null;	
	
	/**
	 * @return the _isRemouteSave
	 */
	public boolean isRemouteSave() {
		return _isRemouteSave;
	}

	@Override
	protected void createMainLayout() {
		_wnd = new Window();
	}
	

	protected void hideWnd() {
		_wnd.hide();
	}
	
	
	
	/**
	 * @param _isRemouteSave the _isRemouteSave to set
	 */
	public void setIsRemouteSave(boolean value) {
		this._isRemouteSave = value;
	}

	public void setDataSource(IDataSource value ) {
		_dataSource=value;
	}
	
	public IDataSource getDataSource() {
		return _dataSource;		
	}		
	
	public void setShowButtons(boolean value) {
		
		if (value) {
			_btnlayout.show();
		}
		else {			
			_btnlayout.hide();			
		}
	}
		

	@Override
	protected  void initSize() {		    
	      setWindowsSize();
	      setShowButtons(true);
	      getViewPanel().setTitle(getCaption());  
	      getViewPanel().setShowMinimizeButton(false);	      
	      getViewPanel().setIsModal(true);  
	      getViewPanel().setShowModalMask(true);  
	      getViewPanel().setCanDragResize(true);
	      getViewPanel().centerInPage();
	}
	
	protected Window getViewPanel() {
		return _wnd;
	}
	
	public Button getButton(EButtons btn) {
		return _buttons.containsKey(btn)?_buttons.get(btn):null; 
	}
	
	
	public  void show() {
		getViewPanel().show();
	}
	
	
	
	protected void setWindowsSize() {		
		getViewPanel().setWidth("35%");
		getViewPanel().setHeight("80%");
	}
	
	
	protected Button addButton(EButtons button)  {
		Button toReturn = new Button();
		toReturn.setTitle(button.toString());
	      _buttons.put(button, toReturn);
	      _btnlayout.addMember(toReturn);
	      return toReturn;
	      
	}
	
	protected void createBottomLayout() {
		 _buttons = new HashMap<EButtons, Button>();
		 _btnlayout = new HLayout();
	      
		 _btnlayout.setAlign(Alignment.RIGHT);
		 _btnlayout.setMargin(10);
		 _btnlayout.setMembersMargin(10);	      
	      
		  Button btnOk = addButton(EButtons.OK);
	      btnOk.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				okBtnClickEvent();
			}
		});	      
	      
	      
	      Button btnCancel = addButton(EButtons.Cancel);
	      btnCancel.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {		
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {			
				closeBtnClickEvent();
			}
		});     
	      getViewPanel().addItem(_btnlayout);
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.dialogs.BaseDialogs#closeBtnClickEvent()
	 */
	@Override
	public void closeBtnClickEvent() {							
			close();		
	}

	
	@Override
	public void okBtnClickEvent() {
		
		if (validate()) {
			
			save();
	
			close();
		}		
	}
	
	
	protected abstract boolean validate();
	
	protected abstract void save();
	
	public abstract  boolean isHaseChanges();
	
	protected void saveChangesToServer() {		
		
		if (isRemouteSave() && getDataSource() != null && getDataSource().isHasChanges() ) {
				
			Dialogs.AskDialog(Captions.Q_SAVE_CHANGES, new BooleanCallback() {				
				@Override
				public void execute(Boolean value) {
					getDataSource().save();				
					
				}
			});			
		}
	}
		
	@Override
	public String getCaption() {		
		return "Base Data Dialog";
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#getMenuFactory()
	 */
	@Override
	protected IMenuFactory getMenuFactory() {
		// TODO Auto-generated method stub
		return null;
	}	
	
	

}