/**
 * 
 */
package mdb.core.db.query.paging.impl;

import mdb.core.shared.data.Params;


/**
 * @author azhuk
 * Creation date: Nov 1, 2012
 *
 */
public class OraQueryPaging extends QueryPaging {
	

	/*
	 *  rec_count -- step (record count) 
	 *  page_num -- page number
	 * */
	
	private final String _startStatement  = "select * from  (select a.*,rownum rw from (";
	private final String _endStatement	    = ") a where rownum <:page_num * :rec_count + :rec_count ) where rw>=:page_num * :rec_count";
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.query.IQueryPagingGenerator#getStartStatement()
	 */
	@Override
	public String getStartStatement() {

		return _startStatement;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.paging.impl.QueryPaging#getEndStatement()
	 */
	@Override
	protected String getEndStatement() {
		return _endStatement;
	}	
	
	@Override
	public Params getPagingParams() {
		Params toReturn = new Params();
		
		toReturn.add("page_num", Integer.toString(getPage()) );
		toReturn.add("rec_count",Integer.toString(getRowCount()));
		
		return toReturn;
	}
	
}
