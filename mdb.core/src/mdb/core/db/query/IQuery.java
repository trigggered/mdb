/**
 * 
 */
package mdb.core.db.query;

import java.sql.ResultSet;
import java.util.ArrayList;

import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.session.User;
import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Params;

/**
 * @author azhuk
 * Creation date: Nov 1, 2012
 *
 */
public interface IQuery {
	public IConnectionManager getConnectionManager ();
	public void setConnectionManager (IConnectionManager value);
	
	public int getQueryID();
	public void setQueryID(int value);
	
	public User getExecuteUser();
	public IQueryPaging getQueryPaging();		
	
	public void setEntityKey(String keyName, String seqName);	
	public String[] getEntityKeys();
	
	public boolean setSelect(String value);
	public boolean setInsert(String value);
	public boolean setUpdate(String value);
	public boolean setDelete(String value);
	public boolean setRefresh(String value);
	public boolean setAction(EMdbEntityActionType actionType, int methodId, String value);
	
	public String getSelect();
	/*
	public String getInsert();
	public String getUpdate();
	public String getDelete();
	public String getRefresh();
	public String getAction(int methodId);
	*/
	
	
	public void update(ArrayList<Params>  aInsData, ArrayList<Params>  aUpdData,  ArrayList<Params>  aDelData);
	public boolean execute(int actionId, Params params);
	public ResultSet getResultSet(Params aParamsData, String aSQLFilter);
	public ResultSet getResultSet(int intValue, Params params);
	
	
	public void close();
	public void rollback();
	public void commit();
	
	public void setDataCaching(boolean value);
	
	public boolean isDataCaching();	
	public String getData();
	public void setData(String _data);		
}
