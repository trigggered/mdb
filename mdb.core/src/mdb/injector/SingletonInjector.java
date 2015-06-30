/**
 * 
 */
package mdb.injector;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

/**
 * @author azhuk
 * Creation date: May 31, 2013
 *
 */
public class SingletonInjector {	
	
	 private static Injector _injector;
	 
	    public static Injector getInjector()
	    {
	        synchronized (SingletonInjector.class)
	        {
	            if (_injector == null)
	                _injector = createInjector();
	            return _injector;
	        }
	    }
	 
	    private static Injector createInjector()
	    {
	        return Guice.createInjector(Stage.DEVELOPMENT, new DefaultInjectConfiguration());
	    }	    	    
	    
}
