/**
 * 
 */
package mdb.scheduling.examp;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

/**
 * @author azhuk
 * Creation date: Oct 21, 2014
 *
 */
//@Singleton
public class ScheduleExample {
	private static final Logger _logger = Logger
			.getLogger(ScheduleExample.class.getCanonicalName());
	
	// @Schedule(second="*/2", minute="*",hour="*", persistent=false)
	    public void doWork(){
	     //   System.out.println( "Hi from the EJB timer example!" );
	    }
	 
}

