/**
 * 
 */
package mdb.core.shared.logger;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;



/**
 * @author azhuk
 * Creation date: Apr 19, 2013
 *
 */
public class MdbLogger {	
	
	private static final Logger _logger = Logger.getLogger(MdbLogger.class.getCanonicalName());
	
	 
	 public Object addLog(InvocationContext context) throws Exception{	        
		 
		  String method = context.getClass().getName();
		 _logger.log(Level.INFO, "Start call method: " +method);
		 
		 
		 	long start = System.currentTimeMillis();
		    try
	        {
		    	return context.proceed();
	        }
	        catch(Exception e)
	        {
	        	_logger.severe(e.getMessage());
	            throw e;
	        }
	        finally
	        {
	            long time = System.currentTimeMillis() - start;	            	            
	            _logger.log(Level.INFO, "*** TracingInterceptor invocation of " + method + " took " + time + "ms");		
	        }	    
	}
	 
	 @AroundInvoke
		public Object log(InvocationContext ctx) throws Exception {
	        Logger logger = Logger.getLogger(ctx.getTarget().getClass().getName());
	        logger.info("**i** Before call to " + ctx.getMethod() + " with args " + Arrays.toString(ctx.getParameters()));
	        long time = System.currentTimeMillis();
	        Object o = ctx.proceed();
	        logger.info("**i** After call to " + ctx.getMethod() + " [" + (System.currentTimeMillis() - time) + " ms.] returned " + o);
	        return o;
	    }
	 
	 
}
