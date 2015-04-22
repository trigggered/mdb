package mdb.core.db.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import mdb.core.config.IAppConfig;

public abstract  class ConnectionManager implements IConnectionManager  {

	private static final Logger _logger = Logger.getLogger(ConnectionManager.class.getCanonicalName());
	
	protected IAppConfig _appConfig;

	protected Connection _conn;

	public ConnectionManager(IAppConfig aAppConfig) {
		_appConfig = aAppConfig;
	}
	

	public IAppConfig getAppConfig () {
		return _appConfig;
	}
	
	
	public boolean  isConnectionClosed(Connection  conn) {
		boolean toReturn = true;
		if (conn != null ) {
			try  {
				conn.getAutoCommit();
				toReturn = false;
			}
			catch (SQLException e)
			{
				toReturn =  true;
			}
		}
		return toReturn;
	}
	
	
	/* (non-Javadoc)
	 * @see mdb.core.db.connection.IConnectionManager#close()
	 */
	@Override
	public void close() {
		try {
			if (_conn != null && !isConnectionClosed(_conn)) {
			_conn.close();
			}
		} catch (SQLException e) {
			_logger.severe(e.getMessage());
		}
		
	}	
	
}