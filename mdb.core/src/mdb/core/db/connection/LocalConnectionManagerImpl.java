package mdb.core.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.Inject;

import mdb.core.config.IAppConfig;

public class LocalConnectionManagerImpl extends ConnectionManager {

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
 

		
			
		   String url = "jdbc:mysql://localhost/test?user="
		                   + getAppConfig().GetUserName()
		                   + "&password="
		                   + getAppConfig().GetUserPwd();
		   
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
