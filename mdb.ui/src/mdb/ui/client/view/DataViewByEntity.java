/**
 * 
 */
package mdb.ui.client.view;


import mdb.core.ui.smartgwt.client.view.data.grid.GridView;

/**
 * @author azhuk
 * Creation date: Aug 16, 2013
 *
 */
public class DataViewByEntity extends GridView {	
	
	public DataViewByEntity(int entityId) {
		super(entityId);		
	}
	
	
	@Override
	public String getCaption() {
		return "Data from entity :" + getMainEntityId();
	}
	
}
