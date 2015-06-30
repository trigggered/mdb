/*
 * Created on  11.10.2005 by Andrey L. Zhuk (andr.zhuk@ua.fm)
 */
package mdb.core.db.connection;

import java.sql.Connection;

import mdb.core.config.IAppConfig;

public interface IConnectionManager {
	
	public Connection getConnectionByName(String name);
	
	public Connection getConnection();

	public IAppConfig getAppConfig();
	
	public void close();

	/**
	 * @param value
	 */
	void setAppConfig(IAppConfig value);
	
}
