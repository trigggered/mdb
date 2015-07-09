/**
 * 
 */
package mdb.core.ui.client.data.checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.smartgwt.client.util.BooleanCallback;

/**
 * @author azhuk
 * Creation date: Jul 10, 2015
 *
 */
/*TODO: NOT Yet implemented*/
public class CheckersExecuter {
	private static final Logger _logger = Logger
			.getLogger(CheckersExecuter.class.getName());
	
	
	
	public static boolean check (IChecker checker) {
		final List<Boolean>  chekerResult = new  ArrayList<Boolean>();
		
		checker.check(new BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				chekerResult.add(value);								
				
			}
		});
		
		return true;
	}
	
	public static boolean check (List<IChecker> checkers) {
		final List<Boolean>  chekerResult = new  ArrayList<Boolean>();
		
		for (IChecker checker : checkers) {							
		
				checker.check(new BooleanCallback() {
					
					@Override
					public void execute(Boolean value) {
						chekerResult.add(value);								
						
					}
				});
		}
		return true;
	}
}
