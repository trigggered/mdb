/**
 * 
 */
package mdb.core.ui.client.view.dialogs;

import java.util.HashMap;

import mdb.core.ui.client.view.IMainView;
import mdb.core.ui.client.view.IOwnerWnd;
import mdb.core.ui.client.view.IView;

import com.smartgwt.client.types.Alignment;

import mdb.core.ui.client.util.BooleanCallback;

import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * @author azhuk Creation date: Oct 2, 2012
 * 
 */
public abstract class BaseDialogs extends Window implements IView {

	public enum EButtons {
		OK,
		Cancel,
		Apply,
		Clear, 
		Save,
		Search,
		Close
	};
	
	HashMap<EButtons, Button> _buttons = new HashMap<EButtons, Button>(); 
	

	private boolean _isSingleInstance = false;
	
	protected Layout _btnlayout;

	private String _caption;
	private String _imgCaption;
	private IMainView _mainView;	
	private HashMap<String,Object> _property = new HashMap<String, Object>();


	private boolean _isCanEdit;


	private String _containerID;
	
	public  BaseDialogs() {		
		createComponents();
	}
	
	protected void createComponents () {
		  createTopLayout();
		  createContextLayout();
		  createBottomLayout();		
		  setWindowPropety();
	}
	
	public Button getButton(EButtons btn) {
		return _buttons.containsKey(btn)?_buttons.get(btn):null; 
	}
	/*
	 * 
	 * Abstract Methods 
	 * 
	 */
	
	public abstract  void closeBtnClickEvent();	
	
	public abstract  void okBtnClickEvent();

	
	@Override
	public void close() {
		IsCanClose( new BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				if (value) {
					hide();
				}				
			}
		});	
	}
	
	@Override
	public String getCaption() {
		return _caption;
	}
	
	@Override
	public   void  setCaption(String value) {
		_caption=value;
	}
	
	
	@Override
	public  String getImgCaption() {
		return _imgCaption;
	}
	@Override
	public  void setImgCaption(String value) {
		_imgCaption = value;			
	}
		
	@Override
	public Canvas getCanvas() {
		return this;
	}
	
	@Override
	public void initialize() {
		
	}
	
	public void setMainView (IMainView mainView) {
		_mainView=mainView;
	}
	
	public IMainView getMainView () {
		return _mainView;
	}
	
	public void addCustomProperty(String name, Object value) {
		_property.put(name, value);
	}
	
	public Object getCustomProperty(String name) {
		return _property.containsKey(name)?_property.get(name):null;
	}
	
	
	public void setShowButtons(boolean value) {
		
		if (value) {
			_btnlayout.show();
		}
		else {			
			_btnlayout.hide();			
		}
	}
		

	private void setWindowPropety() {		    
	      setWindowsSize();
	      setShowButtons(true);
	      this.setTitle(getCaption());  
	      this.setShowMinimizeButton(false);	      
	      this.setIsModal(true);  
	      this.setShowModalMask(true);  
	      this.setCanDragResize(true);
	      centerInPage();
	}
	
	
	protected void setWindowsSize() {
		//setWidth(360);  
	    //setHeight(500);
	   setWidth("35%");
	   setHeight("80%");
	}
	
	
	protected void createTopLayout() {
		
	}

	protected void createContextLayout() {
		
	}
	
	protected Button addButton(EButtons button)  {
		Button toReturn = new Button();
		toReturn.setTitle(button.toString());
	      _buttons.put(button, toReturn);
	      _btnlayout.addMember(toReturn);
	      return toReturn;
	      
	}
	
	protected void createBottomLayout() {
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
	      
	      addItem(_btnlayout);
	}


	public Boolean view() {
		show();
		return true;
	}
	
	@Override
	public void setOwnerWindow(IOwnerWnd ownerWnd) {
		
	}
	
	@Override
	public IOwnerWnd getOwnerWindow() {
		return null;
	}
	
	@Override
	public void setSingleInstance (boolean value) {
		_isSingleInstance = value;
	}
	
	@Override
	public boolean  isSingleInstance()  {
		return _isSingleInstance;
	}
	
	@Override
	public void IsCanClose(BooleanCallback callback) {
		if (callback != null) {
			callback.execute(true);
		}		
	}
	
	public void setCanEdit(boolean value) {
		_isCanEdit = value;
	}
	
	@Override
	public boolean isCanEdit() {		
		return _isCanEdit;
	}
	
	public void setViewContainerID(String id) {
		 _containerID = id;
	}
	
	public String getViewContainerID( ) {
		return _containerID;
	}

	@Override
	public void print() {
		Canvas.showPrintPreview(getCanvas());
	}
}