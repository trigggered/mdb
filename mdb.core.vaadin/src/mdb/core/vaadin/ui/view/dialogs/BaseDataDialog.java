/**
 * 
 */
package mdb.core.vaadin.ui.view.dialogs;

import java.util.HashMap;

import javafx.scene.layout.Pane;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.dialogs.BaseDialogs;
import mdb.core.ui.client.view.dialogs.message.Dialogs;
import mdb.core.vaadin.ui.data.IDataSource;
import mdb.core.vaadin.ui.view.IVaadinComponnet;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


/**
 * @author azhuk Creation date: Oct 2, 2012
 * 
 */
public abstract class BaseDataDialog extends BaseDialogs  implements IVaadinComponnet  {

	protected  IDataSource _dataSource; 
	private boolean _isRemouteSave = true;

	
	HashMap<EButtons, Button> _buttons = null;
	protected  Window _wnd;	
	protected  Layout  _mainLayout;

	protected  VerticalLayout  headerLayout ;
	protected  VerticalLayout  footerLayout ;
	/**
	 * @return the _isRemouteSave
	 */
	public boolean isRemouteSave() {
		return _isRemouteSave;
	}

	@Override
	protected void createComponents () {
		_wnd = new Window();
		_wnd.setModal(true);
		_wnd.setResizable(true);
		_wnd.center();
		_wnd.setImmediate(true);
		
		_wnd.setCaption(getCaption());
		
		super.createComponents();
	}
	
/*	@Override
	protected void createMainLayout() {		
		
		setMainLayout(new VerticalLayout());

		Panel p = new Panel();
		p.setSizeFull();
		p.setContent(getMainLayout());
		//_wnd.setContent(getMainLayout());		
		 getMainLayout().setSizeFull();
		_wnd.setContent(p);
		 //_wnd.setContent(getMainLayout());
	}*/
	
	
	@Override
	protected void createMainLayout() {		
		
		headerLayout = new VerticalLayout();
		
		footerLayout = new VerticalLayout();
		footerLayout.setSpacing(true);
		footerLayout.setMargin(true);

		VerticalLayout contentLayout = new VerticalLayout();

        Panel contentPanel = new Panel(contentLayout);
        contentPanel.setSizeFull();
        setMainLayout(new VerticalLayout());
        contentPanel.setContent(getMainLayout());

        // XXX: add the panel instead of the layout
        VerticalLayout mainLayout = new VerticalLayout(headerLayout, contentPanel, footerLayout);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(contentPanel, 1);
        
        _wnd.setContent(mainLayout);
	}
	
	
	@Override
	public void setMainLayout (Layout  layout) {
		_mainLayout = layout;	
	}
	
	@Override
	public Layout getMainLayout () {
		return _mainLayout;
	}

	protected void hideWnd() {
		_wnd.close();
		UI.getCurrent().removeWindow(_wnd);
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
		
	/*	if (value) {
			_btnlayout.show();
		}
		else {			
			_btnlayout.hide();			
		}*/
	}
		

	@Override
	protected  void initSize() {		    
	      setWindowsSize();
	      /*	      setShowButtons(true);
	      getViewPanel().setTitle(getCaption());  
	      getViewPanel().setShowMinimizeButton(false);	      
	      getViewPanel().setIsModal(true);  
	      getViewPanel().setShowModalMask(true);  
	      getViewPanel().setCanDragResize(true);
	      getViewPanel().centerInPage();*/
	}
	
	protected Window getViewPanel() {
		return _wnd;
	}
	
	public Button getButton(EButtons btn) {
		return _buttons.containsKey(btn)?_buttons.get(btn):null; 
	}
	
	
	
	
	protected void setWindowsSize() {		
		getViewPanel().setWidth("35%");
		getViewPanel().setHeight("80%");
		getViewPanel().center();
	}
	
	
	protected Button addButton(EButtons button)  {
			Button toReturn = new Button(button.toString());
		
	      _buttons.put(button, toReturn);
	    
	      return toReturn;
	}
	
	protected void createBottomLayout() {
		HorizontalLayout btnlayout = new HorizontalLayout();
		//btnlayout.setSizeFull();
		btnlayout.addStyleName("wrapping");
		btnlayout.setSpacing(true);
		//btnlayout.setMargin(true);
		btnlayout.setSizeUndefined();
		btnlayout.setWidth("100%");
		
		btnlayout.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
		
		Label spacer = new Label("");
		btnlayout.addComponent(spacer);
	    spacer.setWidth("100%");
	    btnlayout.setExpandRatio(spacer, 1f);
	        
		  
		 _buttons = new HashMap<EButtons, Button>();	     	      
	      
		  Button btnOk = addButton(EButtons.OK);
		  ;
		  btnOk.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				okBtnClickEvent();				
			}
		});
		  
	      
	      Button btnCancel = addButton(EButtons.Cancel);
	      btnCancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				closeBtnClickEvent();				
			}
		});
	      
	      for (Button btn : _buttons.values()) {
	    	  btn.setWidth("150");
	    	  btnlayout.addComponents(btn); 	    	  
	    	  //btnlayout.setComponentAlignment(btn, Alignment.BOTTOM_RIGHT);
	      }
	      btnOk.focus();
	      footerLayout.addComponent(btnlayout);
	      //((AbstractOrderedLayout)getMainLayout()).setExpandRatio(btnlayout, 0.1f);		
	      //footerLayout.setExpandRatio(btnlayout, 0.1f);
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
	
	

	public void show() {
		UI.getCurrent().addWindow(_wnd);
	}
	

}