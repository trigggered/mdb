/**
 * 
 */
package mdb.core.vaadin.ui.view.dialogs;

import java.util.List;
import java.util.logging.Logger;

import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.vaadin.ui.view.data.GridView;

import com.vaadin.data.Item;

/**
 * @author azhuk
 * Creation date: Aug 13, 2015
 *
 */
public class SelectDialog extends BaseDataDialog {
	private static final Logger _logger = Logger.getLogger(SelectDialog.class
			.getName());
	private GridView _dataForm;
	
	private ICallbackEvent<List<Item>> _callbackEvent;
	
	
	public SelectDialog(ICallbackEvent<List<Item>> callbackEvent ) {
		_callbackEvent = callbackEvent;
	}
	
	@Override
	protected void createContextLayout() {
		_dataForm = new GridView();
		_dataForm.getMainLayout().setSizeFull();
		getMainLayout().addComponent(_dataForm.getMainLayout());
		//((AbstractOrderedLayout)getMainLayout()).setExpandRatio(_dataForm.getMainLayout(), 1f);
	}

	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.view.dialogs.BaseDataDialog#validate()
	 */
	@Override
	protected boolean validate() {
		return true;
	}

	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.view.dialogs.BaseDataDialog#save()
	 */
	@Override
	protected void save() {
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.view.dialogs.BaseDataDialog#isHaseChanges()
	 */
	@Override
	public boolean isHaseChanges() {
		return false;
	}
	
	
	@Override
	public void okBtnClickEvent() {
		
		if (_callbackEvent != null)  {
			_callbackEvent.doWork(_dataForm.getSelectedRecords());
		}
		super.okBtnClickEvent();		
	}
	
	public void callRequestData() {
		_dataForm.callRequestData();
	}
	
}
