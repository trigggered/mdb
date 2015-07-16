/**
 * 
 */
package mdb.core.ui.client.view.data;

import mdb.core.shared.data.Params;
import mdb.core.ui.client.communication.IRemoteDataRequest;
import mdb.core.ui.client.data.IBaseDataSource;
import mdb.core.ui.client.data.bind.IBindDataSource;
import mdb.core.ui.client.data.bind.IDataBinder;
import mdb.core.ui.client.events.IDataEditHandler;
import mdb.core.ui.client.view.IView;




/**
 * @author azhuk
 * Creation date: Oct 10, 2012
 *
 */
public interface IDataView<E> extends IBindDataSource, IView, IRemoteDataRequest{	
	
	public void setDataBinder( IDataBinder value);
	public IDataBinder getDataBinder();
	
	public IBaseDataSource getMainDataSource();
	
	public void showFilterView();	
	
	public boolean isSelectedRecord();
	
	public boolean isHaseChanges();
	public void loseChange();
	public void removeData();	
	public void save();
	
	public int getMainEntityId();	
	public void  setMainEntityId(int entityId);
	public Params getParams();	
	
	/*Events*/
	public void addEditEvent (IDataEditHandler<E> handler);
	public void addInsertEvent (IDataEditHandler<E> handler);
	public void addDeleteEvent (IDataEditHandler<E> handler);
	public void addViewEvent (IDataEditHandler<E> handler);
	/*Call events*/
	public void callEditEvent();
	public void callInsertEvent();
	public void callDeleteEvent();
	public void callViewEvent();
	
	public boolean isValidate();
	
	public void setCreateMenuNavigator(boolean value);
	public void setCreateMenuPaging(boolean value);
	/**
	 * @return
	 */
	boolean isAutoSave();
	/**
	 * @param value
	 */
	void setAutoSave(boolean value);
	
	String getSelectedRecordJSON();
	
	
}
