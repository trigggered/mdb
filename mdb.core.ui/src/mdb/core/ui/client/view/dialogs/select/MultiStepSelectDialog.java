/**
 * 
 */
package mdb.core.ui.client.view.dialogs.select;

import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.client.resources.locales.Captions;
import mdb.core.ui.client.view.dialogs.SelectDialog;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Apr 16, 2015
 *
 */
public class MultiStepSelectDialog extends SelectDialog {

	/**
	 * @param entityId
	 * @param isCanMultiSelect
	 * @param callbackEvent
	 */
	public MultiStepSelectDialog(int entityId, boolean isCanMultiSelect,
			ICallbackEvent<Record[]> callbackEvent) {
		super(entityId, isCanMultiSelect, callbackEvent);
	
	}
	
	public static void view (int entityId, boolean isCanMultiSelect, ICallbackEvent<Record[]>  callbackEvent) {
		
		SelectDialog dlg = new MultiStepSelectDialog(entityId, isCanMultiSelect, callbackEvent);
		dlg.callRequestData();
		
		dlg.show();		
	}
	
	@Override
	public void okBtnClickEvent() {
		if (getCallbackEvent() != null)  {
			Record[] records = getGrid().getListGrid().getSelectedRecords();
			getCallbackEvent().doWork(records );
			getGrid().getListGrid().selectRecords(records, false);
		}		
			
	}
	
	@Override
	protected void createBottomLayout() {
		super.createBottomLayout();
		
		getButton(EButtons.OK).setTitle(Captions.ADD);
		getButton(EButtons.Cancel).setTitle(Captions.CLOSE);
	}
	
}
