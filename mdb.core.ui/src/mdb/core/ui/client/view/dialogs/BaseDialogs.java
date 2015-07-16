/**
 * 
 */
package mdb.core.ui.client.view.dialogs;

import mdb.core.ui.client.util.BooleanCallback;
import mdb.core.ui.client.view.ABaseView;
import mdb.core.ui.client.view.IOwnerWnd;

/**
 * @author azhuk Creation date: Oct 2, 2012
 * 
 */
public abstract class BaseDialogs extends ABaseView {

	public enum EButtons {
		OK,
		Cancel,
		Apply,
		Clear, 
		Save,
		Search,
		Close
	};	
	
	private boolean _isCanEdit;

	public EViewShowType getViewShowType () {
		return EViewShowType.Dialog;
	}
	
	public  BaseDialogs() {		
		createComponents();
	}
	
	protected void createComponents () {
		  createMainLayout();
		  
		  createTopLayout();			
		  createContextLayout();
		  
		  
		  createBottomLayout();	
		  
		  initSize();		  
	}
	
	/*
	 * Abstract Methods
	 */
	
	public abstract  void closeBtnClickEvent();	
	
	public abstract  void okBtnClickEvent();

	protected abstract void hideWnd();	

	public  abstract void show();		
		
	public abstract  void setShowButtons(boolean value);

	
	protected abstract void setWindowsSize();
	
	@Override
	protected  void initSize() {		    
	      setWindowsSize();
	      setShowButtons(true);	      
	}
	
	protected abstract void createBottomLayout();
	
	
	protected void createTopLayout() {
		
	}

	protected void createContextLayout() {
		
	}	


	
	@Override
	public void close() {
		IsCanClose( new BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				if (value) {
					hideWnd();
				}				
			}
		});	
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
	

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.ABaseView#createMenuContainer()
	 */
	@Override
	protected void createMenuContainer() {
	}


}