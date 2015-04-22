/**
 * 
 */
package mdb.injector;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;
/**
 * @author azhuk
 * Creation date: May 31, 2013
 *
 */
public class InjectingInterceptor  implements Serializable {
	
	 /**
	 * mdb.injector.InjectingInterceptor
	 */
	private static final long serialVersionUID = 1L;

	@PostConstruct
	    public void postConstruct(InvocationContext invocation)
	    {
	        try
	        {
	           invocation.proceed();
	        }
	        catch (RuntimeException e)
	        {
	           throw e;
	        }
	        catch (Exception e)
	        {
	            RuntimeException runtimeException = new RuntimeException("Exception in EJB lifecycle callback", e);
	            throw runtimeException;
	        }        
	        
	        SingletonInjector.getInjector().injectMembers(invocation.getTarget());
	    }
	 
}
