/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.data.grid;

import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.view.components.menu.IMenuFactory;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.ui.smartgwt.client.view.data.AListView;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;



/**
 * @author azhuk
 * Creation date: Sep 20, 2012
 *
 */

public  class GridView extends AListView  {

	private static final Logger _logger = Logger
			.getLogger(GridView.class.getName());
			
	 private static final String DESCRIPTION = "Grid  View";


	 public GridView () {		 
		 super();
		 setCaption(DESCRIPTION);
	 }
		 
	 public GridView (int entityId) {
		 super();
		 setMainEntityId(entityId);		 
	 }
	 
	protected ListGrid createGrid() {
		 return new ListGrid();
	} 


	@Override
	public void prepareRequestData() {
		super.prepareRequestData();
		_logger.info("prepareRequestData");
		
		if (getMainEntityId() <= 0) {
			_logger.info("entityId  < 0 ");
			return; 	
		}
		
		IRequestData re = null;		
		
		if ( getDataBinder().getDataProvider().getRequest().existEntity(String.valueOf(getMainEntityId() )) ) {
			re = getDataBinder().getDataProvider().getRequest().get(String.valueOf(getMainEntityId() ));
						
		} else {
			re = getDataBinder().getDataProvider().getRequest().add(new RequestEntity(getMainEntityId()));			
		}
		
		re.getParams().putAll(getParams());
		re.setExecuteType(ExecuteType.GetData);
	}

	public void setSortingEnable(boolean value) {
		getListGrid().setCanSort(value);
	}
	
	public void setIsMultiSelect(boolean value) {	

		if (value) {
			getListGrid().setSelectionType(SelectionStyle.SIMPLE);
			getListGrid().setSelectionAppearance(SelectionAppearance.CHECKBOX);
		} else {			
			getListGrid().setSelectionType(SelectionStyle.SINGLE);
		}
	}
	
	@Override
	public void print() {
		Canvas.showPrintPreview(getListGrid());
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#clearEvents(mdb.core.ui.client.view.data.IDataView)
	 */
	@Override
	protected void clearEvents(IDataView<Record> view) {
		// TODO Auto-generated method stub
		
	}

	

}
