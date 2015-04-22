/**
 * 
 */
package mdb.core.db.query.paging.impl;

import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.shared.data.Params;

/**
 * @author azhuk
 * Creation date: Nov 1, 2012
 *
 */
public abstract class QueryPaging implements IQueryPaging {
	private int _pageNum = 1;
	private int _rowCount = 25;
	private Boolean _pagingUsage = false;
	
	protected abstract  String getStartStatement();

	
	protected abstract String getEndStatement();
	
	@Override
	public Boolean getPagingUsage() {
		return _pagingUsage;
	}
	
	@Override
	public void setPagingUsage(Boolean value) {
		_pagingUsage = value;
	}	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.query.paging.IQueryPaging#setPage(int)
	 */
	@Override
	public void setPage( int value) {
		_pageNum = value;

	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.paging.IQueryPaging#getPage()
	 */
	@Override
	public int getPage() {
		return _pageNum;
	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.paging.IQueryPaging#setRowCount(int)
	 */
	@Override
	public void setRowCount(int value) {
		_rowCount =  value;

	}

	/* (non-Javadoc)
	 * @see mdb.core.db.query.paging.IQueryPaging#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return _rowCount;
	}
	
	private Boolean isPageStmtPrepared(String statement) {
		return false;
	}
	
	@Override
	public String  getPrepareStatement (String statement) {
		String toReturn = "%s\n%s\n%s";
		if ( isPageStmtPrepared (statement) ) {
			return statement;
		}
		else {
			return String.format(toReturn, getStartStatement(), statement, getEndStatement());
		}
	}
	
	@Override
	public Params getPagingParams() {		
		return null;
	}
	
	
	
}
