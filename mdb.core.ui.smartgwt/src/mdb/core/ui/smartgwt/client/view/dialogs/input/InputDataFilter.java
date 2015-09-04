/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.dialogs.input;

import java.util.logging.Logger;

import mdb.core.ui.client.events.ICallbackEvent;
import mdb.core.ui.smartgwt.client.data.IDataSource;
import mdb.core.ui.smartgwt.client.view.dialogs.BaseDataDialog;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.types.TopOperatorAppearance;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * @author azhuk
 * Creation date: Feb 15, 2013
 *
 */
public class InputDataFilter extends BaseDataDialog {
	
	private static final Logger _logger = Logger
			.getLogger(InputDataFilter.class.getName());
	
	
	private ICallbackEvent<AdvancedCriteria> _callbackEvent;
	
	public InputDataFilter (AdvancedCriteria fileredCriteria, 
			IDataSource dataSource, ICallbackEvent<AdvancedCriteria> callbackEvent) {
		setDataSource( dataSource );
		_callbackEvent = callbackEvent;		
		if (fileredCriteria != null) {
			_logger.info("Filter ctiteria: "+ fileredCriteria.toString());
			_filterBuilder.setCriteria(fileredCriteria);
		}
	}
	
	private FilterBuilder _filterBuilder;
	private VStack _vStack;
	
	@Override
	public String getCaption() {		
		return "Data filtering...";
	}


	
	@Override
	protected void setWindowsSize() {
		 super.setWindowsSize();
		 getViewPanel().setWidth("50%");  
	}
	
	public void setDataSource(IDataSource value ) {
		_dataSource=value;
		_filterBuilder.setDataSource(_dataSource.getDataSource());
		_vStack.addMember(_filterBuilder);
	}
	
	
	@Override
	protected void createContextLayout() {		
		
		 _vStack = new VStack(10);
		 _vStack.setHeight("90%");  
		 _vStack.setWidth100();  
		 _vStack.setPadding(5);
		 _vStack.setLayoutAlign(VerticalAlignment.BOTTOM);
	        
	     _vStack.draw();
	        
        _filterBuilder = new FilterBuilder();                 
        _filterBuilder.setTopOperatorAppearance(TopOperatorAppearance.RADIO);      
        

        //_vStack.addMember(_filterBuilder);
        getViewPanel().addItem(_vStack);

	}
	
	@Override
	public void okBtnClickEvent() {
		_callbackEvent.doWork(_filterBuilder.getCriteria());		
		hideWnd();
	}
	
	/* (non-Javadoc)
	 * @see mdb.ui.client.view.dialogs.BaseDialogs#closeBtnClickEvent()
	 */
	@Override
	public void closeBtnClickEvent() {		
		hideWnd();
	}
	
	@Override
	protected void createBottomLayout() {
		super.createBottomLayout();
		Button btnApply = addButton(EButtons.Apply);		
		btnApply.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				_logger.info("Filter ctiteria: "+ _filterBuilder.getCriteria().toString());
				_callbackEvent.doWork(_filterBuilder.getCriteria());				
			}
		});		
		addButton(EButtons.Clear);
		getButton(EButtons.Cancel).setTitle("Close");
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.BaseDataDialog#validate()
	 */
	@Override
	protected boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.dialogs.BaseDataDialog#save()
	 */
	@Override
	protected void save() {		
	}

	@Override
	public boolean isHaseChanges() {
		return true;
	}
	
}
