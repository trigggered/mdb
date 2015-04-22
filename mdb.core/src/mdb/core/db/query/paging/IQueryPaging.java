/**
 * 
 */
package mdb.core.db.query.paging;

import mdb.core.shared.data.Params;

/**
 * @author azhuk
 * Creation date: Nov 1, 2012
 *
 */
public interface IQueryPaging {
	
	public Boolean getPagingUsage();
	public void setPagingUsage(Boolean value);
	public void setPage(int value);
	public int getPage();
	
	public void setRowCount(int value);
	public int getRowCount();
	
	public String  getPrepareStatement (String statement);
	public Params getPagingParams();
	
}
