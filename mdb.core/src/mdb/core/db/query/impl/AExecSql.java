/*
 * Created on  29.12.2005 by Andrey L. Zhuk (andr.zhuk@ua.fm)
 */
package mdb.core.db.query.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import mdb.core.db.connection.IConnectionManager;
import mdb.core.db.query.IQuery;
import mdb.core.shared.data.Params;
import mdb.core.util.format.SqlFormater;
import mdb.core.shared.data.EMdbEntityActionType;


public abstract class AExecSql {
	
	private static final Logger _logger = Logger
			.getLogger(AExecSql.class.getCanonicalName());
	
	private IQuery _q;
	private SqlFormater			_SqlFormat = new SqlFormater();;
	private String 				_methodText;	
	private String 				_preparedSql;
	
	
	private ArrayList<Params>  _arrDataForChange = null;
	private boolean 			_IsArhive;	
		
//	private int					_nMethodID;
	private EMdbEntityActionType		_queryMethodType;
		
	private IConnectionManager 		_connectionManager;
	private Connection			_connection;	
	private String 				_DsName;
		
	protected PreparedStatement 	_pstm;
	//protected  PLSQLStringFormat 	_StringHelper;
	protected Params			_outVariables = new Params(); ;
	
	
	public AExecSql (EMdbEntityActionType  queryMethodType) {
		_queryMethodType = queryMethodType;		
	}

	public Params getOutVariables() {
		return _outVariables;
	}

	protected Connection getConnection() {
		_connection = _connectionManager.getConnectionByName(_DsName);		
		return _connection;
		//return _connectionManager.getConnectionByName(_DsName);
	}
	
	public void setConnectionManager (IConnectionManager dataSource) {
		_connectionManager = dataSource;		
		generateStatement();
	}	

	protected abstract boolean 	generateStatement();
	protected abstract void 	setPreparedStatement(PreparedStatement aValue);		

	/**
	 * @return
	 */
	public String getMethodText() {
		return _methodText;
	}
	
	/**
	 * @param aSQL
	 */
	public boolean setMethodText(String aSQL, boolean fGenerateStatement) {
		setMethodText (aSQL);
		return 	(fGenerateStatement && _methodText!=null  && aSQL.length() != 0)?generateStatement():false;
	}
	
	public void setMethodText (String aSQL) {
		_methodText =_SqlFormat.format(aSQL);
	}
	
	public void setIsArhive (boolean aValue) {
		_IsArhive = aValue;
	}
	
	public boolean getIsArhive() {
		return _IsArhive;		
	}
	
	protected String getDsName () {
		return _DsName;
	}
	
	/**
	 * @return
	 */
	public ArrayList<Params> getDataForChange() {
		return _arrDataForChange;
	}

	
	/**
	 * @param aarChagetData
	 */
	public void setDataForChange(ArrayList<Params> data) {
		_arrDataForChange = data;
	}
	

	public String getPreparedSql() {
		return _preparedSql;
	}	
	
	public void setPreparedSql(String aValue) {
		 _preparedSql = aValue;
	}
	
	public boolean isReady () {
		return (getMethodText()!=null && getMethodText().length() > 0);
	}

	
	private boolean connectionExist() throws SQLException {
		return  (_connection != null && !_connection.isClosed());	
	}
	
	private void closeConnection() {
		try {
			
			if ( connectionExist() ) {
				
				_connection.close();
				_connection = null;
				_logger.info("Success closed active connection");
			}
		} catch (SQLException e) {
			_logger.severe(e.getMessage());
		}
	}
	                         

	public void close() {
		if (_pstm != null)	{		
				try {
					_pstm.close();
					closeConnection();
				} catch (SQLException ex) {
					_logger.severe(ex.getMessage());
				}
				setPreparedStatement(null);
		}
	}	
	
	public EMdbEntityActionType getQueryMethodType() {
		return _queryMethodType;
	}
	
	public void setQuery (IQuery value) {
		_q = value;
	}
	
	public IQuery  getQuery () {
		return _q; 
	}
}

