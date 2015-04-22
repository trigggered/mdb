/**
 * 
 */
package mdb.core.shared.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author azhuk
 * Creation date: Oct 3, 2014
 *
 */
public class PropertyLoader {
	private static final Logger _logger = Logger.getLogger(PropertyLoader.class
			.getName());
	
	public static Properties loadProperties(String resourceName, Class cl) {
	    Properties properties = new Properties();
	    ClassLoader loader = cl.getClassLoader();
	    
	    //ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    
	    try {
	        InputStream in = loader.getResourceAsStream(resourceName);
	        if (in != null) {
	            properties.load(in);
	        } else {
	         _logger.info("Input stream is null");	
	        }

	    } catch (IOException e) {
	    	_logger.severe("Property file not loaded");	        
	    }
	    return properties;
	}
	
}
