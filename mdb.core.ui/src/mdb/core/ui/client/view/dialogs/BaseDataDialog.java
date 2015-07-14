/**
 * 
 */
package mdb.core.ui.client.view.dialogs;

import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.view.dialogs.message.Dialogs;

import mdb.core.ui.client.util.BooleanCallback;


/**
 * @author azhuk Creation date: Oct 2, 2012
 * 
 */
public abstract class BaseDataDialog extends BaseDialogs {

	protected  IDataSource _dataSource; 
	private boolean _isRemouteSave = true;
	
	
	/**
	 * @return the _isRemouteSave
	 */
	public boolean isRemouteSave() {
		return _isRemouteSave;
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

}