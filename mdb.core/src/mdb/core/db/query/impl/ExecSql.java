package mdb.core.db.query.impl;


import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import mdb.core.db.query.IQuery;
import mdb.core.db.sql.SQLTemplates;
import mdb.core.db.sql.parser.SqlParser;
import mdb.core.shared.data.EMdbEntityActionType;
import mdb.core.shared.data.Param;
import mdb.core.shared.data.Params;


/**
 * @author Zhuk
 *
 */
public  class   ExecSql extends AExecSql{	
	
	private static final Logger _logger = Logger.getLogger(ExecSql.class.getCanonicalName());
	
	private IQuery _query;
	private Params _params;
	
	private PreparedStatement 	_pstForLogArhive;
	private SqlParser 			_sqlParser = new SqlParser();
	private boolean 			_fAlwaysGenerateStatement;
	
	
	public ExecSql(IQuery query, EMdbEntityActionType queryMethodType, String aSQL) {
		super (queryMethodType);
		_query = query;
		_fAlwaysGenerateStatement = true;

		setConnectionManager(_query.getConnectionManager());							
		setMethodText(aSQL, false);							
	}


	private String preparePaging() {
		String toReturn = getMethodText();		
		if (_query.getQueryPaging()!=null 
				//&&  ( getQueryMethodType() == MdbEntityActionType.SELECT || getQueryMethodType() == MdbEntityActionType.PREPERE_FILTER) 
				&&  getQueryMethodType() == EMdbEntityActionType.SELECT 
				&& _query.getQueryPaging().getPagingUsage())
		{
			toReturn =_query.getQueryPaging().getPrepareStatement(getMethodText());
			Params pageParams = _query.getQueryPaging().getPagingParams();
			if (pageParams != null) {
				
				if (_params == null) {
					_params = pageParams;
				}
				else {
					_params.copyFrom(pageParams);
				}
			}
		}		
		return toReturn;		
	}
	
	protected boolean generateStatement() {		
		boolean toReturn = false;
		//PreparedStatement statement = null;
		String methodWithPaging = preparePaging();
		if (getMethodText() != null && getMethodText().length() != 0){	

			//setPreparedSql( _SqlParser.parser(getMethodText()));
			setPreparedSql( _sqlParser.parser(methodWithPaging));
			
			 
			try {	
					if (_sqlParser.getIsCallSQL())
					{
						_pstm = getConnection().prepareCall(getPreparedSql());
					} 
					else  						
					{		
						switch ( getQueryMethodType() )  {
						
							case INSERT : 	
							case UPDATE:
							case DELETE:
								 String[] keys = getQuery().getEntityKeys(); 
								if ( keys != null && keys.length >0) { 
									_pstm = getConnection().prepareStatement(getPreparedSql(), getQuery().getEntityKeys() );
								}
								else
								{
									_pstm = getConnection().prepareStatement(getPreparedSql());
								}
								break;
						
						default:
							_pstm = getConnection().prepareStatement(getPreparedSql(), ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);
							break;
						}						
					}						
					//initializeCommandParams2Null(_sqlPreparedStatement);					
					toReturn = true;
			} catch (SQLException ex) {
					toReturn = false;				
					_logger.severe(ex.getMessage() );				
			}			
		}		
		return toReturn;
	}



	private void initializeCommandParams2Null(PreparedStatement aPrepaSatement) {
		try {
			aPrepaSatement.clearParameters();
			
			ParameterMetaData pmd = aPrepaSatement.getParameterMetaData();
			for (int i=1; i <= pmd.getParameterCount(); i++) {
				aPrepaSatement.setNull(i, java.sql.Types.VARCHAR);
			}
		} catch (SQLException ex) {
			_logger.severe(ex.getMessage());
		}
	}
	

	private void retrieveParams(Params aInParamsData) {	
		
		try {			
			StringBuffer logBuf = new StringBuffer();			
			
			//initializeCommandParams2Null(_sqlPreparedStatement);
			ParameterMetaData  pmd = _pstm.getParameterMetaData();						
			if (getPreparedSql()!= null && pmd.getParameterCount() > 0 && aInParamsData!= null && aInParamsData.count() > 0) {
							
					_outVariables.clear();					
					/*
					for (int i = 0; i < pmd.getParameterCount(); i++) {
						
						switch  (pmd.getParameterMode(i) ) {
							case ParameterMetaData.parameterModeInOut:
							case ParameterMetaData.parameterModeIn:
								
							
							break;
						case ParameterMetaData.parameterModeOut:
							break;
						} 
					}				
					*/					 					
					for (Map.Entry<Integer,Param> entry : _sqlParser.getParams().entrySet()) {
						
						Param statementParam = entry.getValue();
						Param inParam = aInParamsData.paramByName(statementParam.getName());
						
						if (inParam !=null) {
							
							inParam.setSqlIndex(statementParam.getSqlIndex());
	
								if (_sqlParser.getIsCallSQL() && inParam.getInOut()==1)
								{ 
									((CallableStatement)_pstm).registerOutParameter (inParam.getSqlIndex()
											, (inParam.getType()!=Types.CLOB&&inParam.getType()!=Types.BLOB)?Types.VARCHAR:inParam.getType());
									_outVariables.add(inParam);
								}
														
								setParamVal2dStatement (statementParam.getSqlIndex(), inParam.getName(),  inParam.getType(),  inParam.getValue());								
								logBuf.append(String.format("SqlIndex=%s, Name=%s, Type=%s,Value=%s \n",statementParam.getSqlIndex(), inParam.getName(),  inParam.getType(),  inParam.getValue()));
								/*
								if (inParam.getSqlIndex() <= pmd.getParameterCount() ) 
								{
									setParamVal2dStatement (inParam.getSqlIndex(), inParam.getName(),  inParam.getType(),  inParam.getValue());
								}
								*/
						}	else {							
							_pstm.setNull(statementParam.getSqlIndex(), java.sql.Types.NULL );
						}
			
					}
					_logger.info(String.format("Set params values to statements: \n  %s", logBuf.toString()));
			}
			else if (pmd.getParameterCount() > 0 ) {
				initializeCommandParams2Null(_pstm);
			}
		}
		catch (Exception ex) {
			_logger.severe(ex.getMessage());		
		}	
	}

		
	private boolean setParamVal2dStatement (int paramIndex, String paramName, int paramType,  String paramValue) throws SQLException {			
		
		if (paramValue == null || paramValue.length() == 0) 
		{	
			_pstm.setNull(paramIndex, paramType);
		}
		else {
			
			switch (paramType) {					
			case Types.CLOB:
					StringReader r = new StringReader(paramValue);
					_pstm.setCharacterStream(paramIndex, r, paramValue.length());
					break;
			case Types.BLOB:
//					byte[] b = Base64.decode(param.getValue());									
//					if (b!=null) {										
//						ByteArrayInputStream in = new ByteArrayInputStream(b);									
//						_SQLPreparedStatement.setBinaryStream(paramItem.getIndex(), in, b.length);
//					}
					break;
					
			case	Types.FLOAT:
			case	Types.REAL:
						_pstm.setFloat (paramIndex,Float.valueOf(paramValue).floatValue() );
					break;
					
			case	Types.DECIMAL:					
			case	Types.NUMERIC:		
			case	Types.DOUBLE:
						_pstm.setDouble(paramIndex,Double.valueOf(paramValue).doubleValue() );
					break;
			
				
			case	Types.INTEGER:
						_pstm.setInt(paramIndex,Integer.valueOf(paramValue).intValue() );
					break;
			case	Types.BIGINT:							
						_pstm.setLong(paramIndex,Long.valueOf(paramValue).longValue()) ;									
					break;							
			
			// date 93
			/*
			case Types.TIMESTAMP:
			case Types.DATE:
				if (param.getValue() != null) {								
					java.text.SimpleDateFormat frm = new java.text.SimpleDateFormat("yyyyMMdd");
					java.util.Date d = frm.parse(param.getValue());
					DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
					String sDate = df.format(d);
					if (this._IsArhive) { SQLScript = m_SQLParser.ReplaceParamSQL2Val(SQLScript, sParamName, sDate);}						
					m_SQLPreparedStatement.setString(paramItem.getIndex(),sDate);
					break;
				} else _SQLPreparedStatement.setNull(paramItem.getIndex(), java.sql.Types.VARCHAR); */
			// STRING
			default:
				_pstm.setString(paramIndex,paramValue);	
				break;														
			}
		}
		
		
//		if (paramValue != null &&  getIsArhive()) {			
//			String val = (paramType != Types.BLOB /*&& param.getType() != Types.CLOB*/ )?paramValue:null;			
//			//SQLScript = _SQLParser.ReplaceParamSQL2Val(SQLScript, paramName, PLSQLStringFormat.StrFormat2PLSQL(val));							
//		
//		} else {		
//			_SQLPreparedStatement.setNull(paramIndex, java.sql.Types.VARCHAR);		
//		}
		
		return true;	
	}
	
	/**
	 * 
	 */
	public void сhangeData() {
		if (getDataForChange() != null && getDataForChange().size() >0) {
			//if (_SqlParser.getIsCallSQL())				
				for (Params data :  getDataForChange()) {														
					execute(data);
					_fAlwaysGenerateStatement = false;
				}						
				_fAlwaysGenerateStatement = true;			
				//executeBatch(getChangetData());
		}			
	}

	/**
	 * @param aSql
	 * @param aSQLFilterStr
	 * @return
	 */
	public String getBuildFilteredSQL(String aSql, String aSQLFilterStr) {
		return _sqlParser.getSQLFiltered(aSql, aSQLFilterStr);
	}



	/**
	 * @param aParamsData
	 * @param aSQLFilter
	 * @return
	 */
	public ResultSet getResultSet(Params aParamsData, String aSQLFilter) {
		ResultSet Result = null;
		if (aSQLFilter != null) {
			String sSQL = this.getMethodText();
			String sFilteredSQL = _sqlParser.getSQLFiltered(getMethodText(), aSQLFilter);
			setMethodText(sFilteredSQL,false);

			Result = getResultSet(aParamsData);
			setMethodText(sSQL,false);

		} else
			Result = this.getResultSet(aParamsData);
		return Result;
	}

	/**
	 * @param inParams
	 * @return
	 */
	public ResultSet getResultSet(Params inParams) {		
			_params = inParams;
			if ( generateStatement() ) {
				retrieveParams(_params);		
				return getResultSet();
			}
			else {
				_logger.severe("Error in generateStatement() block"); 
				return null;
			}
	}

	/**
	 * @return
	 */
	private ResultSet getResultSet() {
		
		ResultSet Result = null;
		try {			
			Result =  _pstm.executeQuery();			
			
		} catch (SQLException ex) {			
			StringBuffer sb = new  StringBuffer();
			sb.append("SQLException  "+ex.getMessage()+"\n");			
			sb.append("Data Set №:"+ Integer.toString(_query.getQueryID())+"\n");			
			sb.append(getMethodText());
			
			_logger.severe(sb.toString());
		}
		return Result;
	}
	
	protected  void setPreparedStatement(PreparedStatement aValue) {
		_pstm = aValue;
	}
	
	
	public boolean executeBatch (ArrayList<Params> arrChagetData) {
		try 
		{
			if (getDsName() == null && _fAlwaysGenerateStatement) {	
				if (_pstm != null) {
					_pstm.close();					
				}
				generateStatement();
			}	
			
			for (Params paramsData : arrChagetData ) {

				retrieveParams(paramsData);
				logArhive();
				retrieveParams(paramsData);
				_pstm.addBatch();
			}					 					
			_pstm.executeBatch();			
		
			return true;
		} catch (SQLException ex) {
			_logger.severe(ex.getMessage());								
			return false;
		}
	}
	
	/**
	 * @param aParamsData
	 * @return
	 */	
	public boolean execute(Params aParamsData) {
		try 
		{			
			boolean fStatementGenerated = true;
			_fAlwaysGenerateStatement= true;
			if (getDsName() == null && _fAlwaysGenerateStatement) {	
				if (_pstm != null) {
					_pstm.close();					
				}
				fStatementGenerated = generateStatement();
			}	
			
			if (fStatementGenerated) {
				initializeCommandParams2Null(_pstm);
				retrieveParams(aParamsData);
				logArhive();		
				_pstm.executeUpdate();					
				
				getGeneratedKeys();						
	
				if (_sqlParser.getIsCallSQL())
					
					for (Param param : _outVariables.values()) {
						Object oValue =((CallableStatement)_pstm).getString(param.getSqlIndex());
						param.setValue((String) oValue);
					}				
					
				_pstm.close();
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException ex) {
			
			_logger.severe(ex.getMessage());
			
			return false;
		}
	}
	
	
	private void getGeneratedKeys() throws SQLException {
		
		if ( getQueryMethodType() == EMdbEntityActionType.INSERT ) {
		/*
		 ResultSet toReturn = _pstm.getGeneratedKeys();
		 if (toReturn  != null && toReturn.next()) {
			//long id = toReturn.getLong(1);			
		 }
		 */
		}		
	}


	/**
	 * @throws SQLException
	 */
	private void logArhive() throws SQLException {
		if  ( getIsArhive() ) {
			_pstForLogArhive = getConnection().prepareStatement(SQLTemplates.m_LogArhive);			
			_pstForLogArhive.setInt(1,_query.getQueryID());
			//_pstForLogArhive.setInt(2,queryAction());
			
			StringReader r = new StringReader(getPreparedSql());
			_pstForLogArhive.setCharacterStream(3, r, getPreparedSql().length());			
			_pstForLogArhive.setInt(4,_query.getExecuteUser().getUSRID().intValue());									
			_pstForLogArhive.execute();
			_pstForLogArhive.close();
		}				
	}

}