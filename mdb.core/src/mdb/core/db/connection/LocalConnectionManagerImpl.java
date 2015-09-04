package mdb.core.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.google.inject.Inject;

import mdb.core.config.IAppConfig;

public class LocalConnectionManagerImpl extends ConnectionManager {

	private static final Logger _logger = Logger.getLogger(LocalConnectionManagerImpl.class.getCanonicalName());
	
	@Inject
	public LocalConnectionManagerImpl(IAppConfig aAppConfig) {
		super(aAppConfig);
	}


	@Override
	public Connection getConnectionByName(String name) {
		return getConnection();
	}
	
	

	@Override
	public Connection getConnection() {
		Connection toReturn = null;
		
		String host = "10.91.64.73";
		String port = "1527";
		String sid = "wfw1ua10";
		
		 String url = String.format("jdbc:oracle:thin:%s/%s@%s:%s:%s",
				getAppConfig().GetUserName(),
				getAppConfig().GetUserPwd(),
				host, port, sid);
		
		   
		 _logger.info("Connection url="+url);
		   
		   try {
			Class.forName (getAppConfig().getJdbcDataSourceName()).newInstance ();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		   try {
			toReturn = DriverManager.getConnection (url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}



}
