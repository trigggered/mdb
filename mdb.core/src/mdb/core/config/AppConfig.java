/**
 * 
 */
package mdb.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author azhuk
 * Creation date: Jul 2, 2013
 *
 */
public class AppConfig  implements IAppConfig {
	
	private static final Logger _logger = Logger.getLogger(AppConfig.class
			.getName());
	
	private static final String PROPERTIES_FILE = "mdb/core/config/app.properties";
	private Properties _properties;  
	
	
	public AppConfig() {
			_properties = loadProperties(getPropertyFileName(), this.getClass());		 
	}

	public static Properties loadProperties(String resourceName, Class cl) {
	    Properties properties = new Properties();
	    ClassLoader loader = cl.getClassLoader();
	    try {
	        InputStream in = loader.getResourceAsStream(resourceName);
	        if (in != null) {
	            properties.load(in);
	        }

	    } catch (IOException e) {
	    	_logger.severe("Prperty file not loaded");	        
	    }
	    return properties;
	}
	
	protected String getPropertyFileName() {
		return PROPERTIES_FILE;
	}
	
	/* (non-Javadoc)
	 * @see mdb.core.config.IAppConfig#GetJndiContext()
	 */
	@Override
	public String getJndiContext() {
		return _properties.getProperty("JndiContext");
	}

	/* (non-Javadoc)
	 * @see mdb.core.config.IAppConfig#GetJndiJdbcContextName()
	 */
	@Override
	public String getJndiJdbcContextName() {
		return _properties.getProperty("JndiJdbcContextName");
	}

	/* (non-Javadoc)
	 * @see mdb.core.config.IAppConfig#getJdbcDataSourceName()
	 */
	@Override
	public String getJdbcDataSourceName() {
		return _properties.getProperty("JdbcDataSourceName");
	}

	/* (non-Javadoc)
	 * @see mdb.core.config.IAppConfig#GetUserName()
	 */
	@Override
	public String GetUserName() {
		return _properties.getProperty("UserName");		
	}

	/* (non-Javadoc)
	 * @see mdb.core.config.IAppConfig#GetUserPwd()
	 */
	@Override
	public String GetUserPwd() {
		return _properties.getProperty("UserPwd");
	}

	/* (non-Javadoc)
	 * @see mdb.core.config.IAppConfig#IsUsingQueryPool()
	 */
	@Override
	public boolean IsUsingQueryPool() {		
		return Boolean.getBoolean(_properties.getProperty("IsUsingQueryPool"));		
	}
}
