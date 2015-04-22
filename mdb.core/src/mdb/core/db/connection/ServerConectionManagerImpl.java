/*
 * Created on  14.10.2005 by Andrey L. Zhuk (andr.zhuk@ua.fm)
 * Modify  on  04.01.2011 by Andrey Zhuk (andr.zhuk@gmail.com)
 */
package mdb.core.db.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import mdb.core.config.IAppConfig;

import com.google.inject.Inject;


public class ServerConectionManagerImpl extends ConnectionManager  {

	private static final Logger _logger = Logger.getLogger(ServerConectionManagerImpl.class.getCanonicalName());
	
	private Connection _conn;
		
	@Inject
	public ServerConectionManagerImpl(IAppConfig aAppConfig) {
		super(aAppConfig);
	}	
	
	
//	private Context getContext() {	s
//		
//		if (_Context == null) {
//			Hashtable<String,String> ht = new Hashtable<String,String>();
//			ht.put(Context.INITIAL_CONTEXT_FACTORY, _appConfig.GetJndiContext() );
//			  
//			try {
//				_Context = new javax.naming.InitialContext(ht);
//			} catch (javax.naming.NamingException ex) {
//				MessageListner.exception(ex);
//			}
//		}
//		return _Context;
//	}
	
	private Context getEnvContext() {
		Context initContext;
		Context envContext = null;
		try {
			initContext = new InitialContext();
			//envContext  = (Context)initContext.lookup("java:/comp/env");
			envContext  = (Context)initContext.lookup(_appConfig.getJndiContext());
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return envContext;
	}



	public String getDefDataSourceName() {
		// return getBeanEnviroment("DATA_SOURCE");
		return _appConfig.getJdbcDataSourceName();
	}


	private javax.sql.DataSource getDataSource(String aDSName) {
		Object ref = null;
		try {			
			//ref = getContext().lookup(_appConfig.GetJndiJdbcContextName() + aDSName);
			//ref= (DataSource)getEnvContext().lookup("jdbc/MySqlDS");
			ref= (DataSource)getEnvContext().lookup(_appConfig.getJdbcDataSourceName());
			
			
		} catch (NamingException ex) {
			_logger.severe(ex.getMessage());			
			return null;
		}
		return (javax.sql.DataSource) ref;
	}

	public Connection getConnection() {		
		_logger.log(Level.INFO, "Get Connection");
		return getConnectionByName(_appConfig.getJdbcDataSourceName());
	}

	
	
	
	
	public Connection getConnectionByName(String aDSname) {

		/*
		try {
			return getDataSource(aDSname).getConnection();
		} catch (SQLException e) {
			_logger.severe(e.getMessage());
			return null;
		}

		
		
		*/
		if (isConnectionClosed(_conn) ) {
			try {						
				DataSource ds = null;
				ds = getDataSource(aDSname);
		
				if (ds != null) {					
//						_conn = ds.getConnection(CHashFunctions.StrToHash30(m_UserName),
//												CHashFunctions.StrToHash30(m_UserPwd));
					//_conn = ds.getConnection(m_UserName,m_UserPwd);
					_conn = ds.getConnection();
				} 
				else _conn = null;
			 }
			 catch (SQLException ex) {
				 _logger.log(Level.SEVERE, ex.getMessage());
				 			
			}		
		}
		return _conn;
		
		
	}	
	
	
	public IConnectionManager getIConnection() {
		return (IConnectionManager) this;
	}



	

}
