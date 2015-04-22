/**
 * 
 */
package mdb.core.db.query.paging.impl;


/**
 * @author azhuk
 * Creation date: Nov 1, 2012
 *
 */
public class MySqlQueryPaging  extends QueryPaging {
	
	private final String _startStatement  = "";
	private final String _endStatement	    = "limit %s, %s";
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.query.paging.IQueryPaging#getStartStatement()
	 */
	@Override
	public String getStartStatement() {
		return _startStatement;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.paging.IQueryPaging#getEndStatemrnt()
	 */
	@Override
	public String getEndStatement() {
		int startRoew = getPage() == 1? 1:  getPage() *  getRowCount();
		return String.format(_endStatement, startRoew, getRowCount());		
	}
}
