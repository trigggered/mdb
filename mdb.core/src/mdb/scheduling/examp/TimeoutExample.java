/**
 * 
 */
package mdb.scheduling.examp;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.interceptor.InvocationContext;

/**
 * @author azhuk
 * Creation date: Oct 21, 2014
 *
 */

//@Singleton
//@Startup
public class TimeoutExample {
	private static final Logger _logger = Logger.getLogger(TimeoutExample.class
			.getCanonicalName());
	
	

    @Resource
    private TimerService timerService;
    
    @Timeout
    public void scheduler(Timer timer) {
    	
        System.out.println("TimeoutExample: EJB Timer: Info=" + timer.getInfo());
    }
    
    @PostConstruct
    public void initialize( InvocationContext ctx ) {
        ScheduleExpression se = new ScheduleExpression();
        // Set schedule to every 3 seconds (starting at second 0 of every minute).
        se.hour("*").minute("*").second("0/10");
        timerService.createCalendarTimer( se, new TimerConfig("Hi from TimeoutExample!", false) );
    }
    
    @PreDestroy
    public void stop() {    
        System.out.println("Stop all existing timers");
        for (Timer timer : timerService.getTimers()) {
            System.out.println("Stopping timer: " + timer.getInfo());
            timer.cancel();
        }
    }
    
}
