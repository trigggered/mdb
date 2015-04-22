package mdb.core.ui.client.view.data.grid;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import mdb.core.shared.data.Params;
import mdb.core.ui.client.communication.impl.GatewayQueue;
import mdb.core.ui.client.view.components.menu.IMenuContainer;

import com.smartgwt.client.widgets.layout.Layout;


public class ListOfGrids {

	public class EmbeddedGrid extends GridView{		
	
		int _arrayPosition;
		
		public EmbeddedGrid(int entityId) {
			super(entityId);
			setCreateMenuNavigator(false);
			setCreateMenuPaging(false);
		}
		
		public EmbeddedGrid(int entityId, boolean isCreateMenu) {
			super(entityId);
			setCreateMenuNavigator(isCreateMenu);
			setCreateMenuPaging(isCreateMenu);
		}
		
		@Override
		protected void createMenu () {
			
			if ( isCreateMenuNavigator() || isCreateMenuPaging()) {
				super.createMenu();
			}
		}		
		
		@Override
		protected IMenuContainer createMenuContainer () {
			if ( isCreateMenuNavigator() || isCreateMenuPaging()) {
				return super.createMenuContainer();
			}
			else return null;
		}
		
	}
	
		
	private Map<Integer,EmbeddedGrid> _gridMap;
	

	private int[] _entityIds;
	
	
	/**
	 * @return the _gridMap
	 */
	public Map<Integer, EmbeddedGrid> getGridMap() {
		return _gridMap;
	}
	
	/**
	 * @return the _gridList
	 */
	public Collection<EmbeddedGrid> getList() {
		return _gridMap.values();
	}
	
	public ListOfGrids(int ... entityIds) {
		this();		
		_entityIds= entityIds;
		createDataComponents();		
	}
	
	public ListOfGrids() {
		_gridMap = new HashMap<Integer,  ListOfGrids.EmbeddedGrid>();
	}
	
	public EmbeddedGrid  addGrid (int entityId, Params params, boolean isCreateMenuBar) {
		
		EmbeddedGrid toRetutn = new EmbeddedGrid(entityId,isCreateMenuBar);
		return addGrid(toRetutn, params);
	}
	
	public EmbeddedGrid  addGrid (int entityId, Params params) {
		EmbeddedGrid toRetutn = new EmbeddedGrid(entityId);
		return addGrid(toRetutn, params);		
	}
	
	private EmbeddedGrid  addGrid (EmbeddedGrid   grid, Params params) {
		if (params != null) { 
			grid.getParams().merge(params);
		}		

		_gridMap.put(Integer.valueOf(grid.getMainEntityId()), grid);
		grid._arrayPosition = _gridMap.size() - 1;
		
		return grid;
	}
	
	protected void createDataComponents() {		
		for ( int i = 0;  i< _entityIds.length; i++) {			
			addGrid (_entityIds[i], null);				
		}		
	}

	
	public void fillViewPanel(int entityId,  Layout layout) {				
		layout.addMembers(getDataComponent(entityId));		
	}
	 
	 public void prepareRequestData(Map<String,String> parametersMap) {		 
		 for ( EmbeddedGrid grid: _gridMap.values()){					 			 
			 grid.prepareRequestData(parametersMap);			 
		 }		 		 
	 }
	 
	 public void putRequestToQueue() {		 
		 for ( EmbeddedGrid grid: _gridMap.values()){					 			 
			 grid.putRequestToQueue();			 
		 }		 		 
	 } 	 	 
	 
	 public void callRequestData() {
		 GatewayQueue.instance().getProcessor().run();
	 }
	 
	 public EmbeddedGrid getDataComponent(int entityId) {
		 for (EmbeddedGrid grid : _gridMap.values()) {
			 if (grid.getMainEntityId() == entityId) {
				 return grid;
			 }			 
		 }
		  return null;
	 }
	 
	 public EmbeddedGrid[] toArray() {		 
		 
		 Collection<EmbeddedGrid> list = _gridMap.values();
		 EmbeddedGrid[] toReturn = new EmbeddedGrid[_gridMap.size()];
		 
		 toReturn =  list.toArray(toReturn);
		return toReturn;
	 }	 
	 
 
	
}
