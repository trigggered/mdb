/**
 * 
 */
package mdb.core.ui.client.view.data;

import mdb.core.shared.data.Params;
import mdb.core.ui.client.communication.IRemoteDataRequest;
import mdb.core.ui.client.data.IDataSource;
import mdb.core.ui.client.data.bind.IBindDataSource;
import mdb.core.ui.client.data.bind.IViewDataBinder;
import mdb.core.ui.client.events.IDataEditHandler;
import mdb.core.ui.client.view.IView;

import com.smartgwt.client.data.Record;


/**
 * @author azhuk
 * Creation date: Oct 10, 2012
 *
 */
public interface IDataView extends IBindDataSource, IView, IRemoteDataRequest{	
	
	public void setDataBinder( IViewDataBinder value);
	public IViewDataBinder getDataBinder();
	
	public IDataSource getMainDataSource();
	
	public void showFilterView();	
	
	public Record getSelectedRecord();
	public Record[] getSelectedRecords();
	
	public boolean isSelectedRecord();
	
	public boolean isHaseChanges();
	public void loseChange();
	public void removeData();	
	public void save();
	
	public int getMainEntityId();	
	public void  setMainEntityId(int entityId);
	public Params getParams();	
	
	/*Events*/
	public void addEditEvent (IDataEditHandler handler);
	public void addInsertEvent (IDataEditHandler handler);
	public void addDeleteEvent (IDataEditHandler handler);
	public void addViewEvent (IDataEditHandler handler);
	/*Call events*/
	public void callEditEvent();
	public void callInsertEvent();
	public void callDeleteEvent();
	public void callViewEvent();
	
	public boolean isValidate();
	
}
