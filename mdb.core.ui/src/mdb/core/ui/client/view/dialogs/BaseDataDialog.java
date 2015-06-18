/**
 * 
 */
package mdb.core.ui.client.view.dialogs;

import mdb.core.ui.client.data.IDataSource;

import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

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
				
				SC.ask( "Save changes to server", new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
							getDataSource().save();				
					}
				});		
		}
	}
		
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.dialogs.BaseDialogs#okBtnClickEvent()
	 */
	/*@Override
	protected void okBtnClickEvent() {
		if (_dataForm.validate()) {			
			_dataForm.saveData();			
			Record rec = _dataForm.getValuesAsRecord();
			_callbackEvent.doWork(recordToParams(rec));
			hide();
		}		
	}
	*/
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.dialogs.BaseDialogs#getCaption()
	 */
	@Override
	public String getCaption() {		
		return "Base Data Dialog";
	}

}