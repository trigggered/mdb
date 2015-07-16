/**
 * 
 */
package mdb.core.vaadin.ui.view.data;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.IRequestData.ExecuteType;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.ui.client.data.fields.IMdbField;
import mdb.core.ui.client.view.data.IDataView;
import mdb.core.vaadin.ui.data.IDataSource;
import mdb.core.vaadin.ui.data.fields.MdbField;

import com.vaadin.data.Item;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;

/**
 * @author azhuk
 * Creation date: Aug 5, 2015
 *
 */
public class TableView extends AListView {
	private static final Logger _logger = Logger.getLogger(TableView.class
			.getName());

	 //protected Grid _grid;
	
	protected Table _grid;



	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.view.data.AListView#createGrid()
	 */
	@Override
	protected void createGrid() {
		//_grid = new Table();
		_grid =  new Table();
		_grid.setSizeFull();
		_grid.addItemClickListener(_listener);
		_grid.setSelectable(true);
		_grid.setMultiSelect(true);
		_grid.setSortEnabled(true);
		_grid.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT);
		_grid.setColumnCollapsingAllowed(true);
		
		_grid.setColumnReorderingAllowed(true);
		
		AbstractOrderedLayout layout = 	(AbstractOrderedLayout) getMainLayout();
		getMainLayout().addComponent(_grid);
		//layout.setExpandRatio(_grid, 0.9f);
		layout.setExpandRatio(_grid, 0.99f);
		layout.setMargin(false);
		
		
	}

	
	@Override
	public Item getSelectedRecord() {
		
		Collection<Integer>  selectedValues = (Collection<Integer>) _grid.getValue();

		//_grid.isMultiSelect();
		
		Item  selectedItem = null;
		if (selectedValues != null) {						
			
			for ( Integer id  : selectedValues )  {
				selectedItem =_grid.getContainerDataSource().getItem(id);
				break;
			}
			
		}		
		return selectedItem;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.data.ADataView#clearEvents(mdb.core.ui.client.view.data.IDataView)
	 */
	@Override
	protected void clearEvents(IDataView<Object> view) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.view.data.AListView#setDataSourceToDataComponent(com.vaadin.data.Container)
	 */
	@Override
	protected void setDataSourceToDataComponent(IDataSource ds) {		
		
		_grid.clear();
		_grid.removeAllItems();
		_grid.setContainerDataSource(ds.getDataSource());
		
		for (IMdbField   field :   ds.getDataSourceFields().getMdbFieldMap().values() ) {
			
			 MdbField mdbFld = (MdbField)field;						 

			_grid.setColumnHeader(mdbFld.getName(), field.getTitle());
			// you can set individual columns non-collapsible
			_grid.setColumnCollapsible(mdbFld.getName(), !mdbFld.isVisivble());
			//_grid.setColumnCollapsed(mdbFld.getName(), mdbFld.isVisivble());
			
			if ( !mdbFld.isVisivble() ) {
			_grid.setColumnCollapsed(mdbFld.getName(), false);
			}															
			
			//_grid.addGeneratedColumn(mdbFld.getName(), mdbFld.getDataSourceField() );
		}
		
		
	}

	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.view.data.AListView#isSelectedRecord()
	 */
	@Override
	public boolean isSelectedRecord() {
		Object rowId = _grid.getValue();
		return rowId != null;
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


	/* (non-Javadoc)
	 * @see mdb.core.vaadin.ui.view.data.AListView#getSelectedRecords()
	 */
	@Override
	public List<Item> getSelectedRecords() {
		// TODO Auto-generated method stub
		return null;
	}

}
