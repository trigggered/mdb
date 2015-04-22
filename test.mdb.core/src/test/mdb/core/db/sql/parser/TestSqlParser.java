/**
 * 
 */
package test.mdb.core.db.sql.parser;

import java.util.logging.Logger;

import junit.framework.TestCase;

/**
 * @author azhuk
 * Creation date: Oct 23, 2012
 *
 */
public class TestSqlParser extends TestCase {
	private static final Logger _logger = Logger.getLogger(TestSqlParser.class
			.getCanonicalName());
	
	private final String COMMENTS_CODE =  "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)";
	
	private final String SOURCE_CODE = " SELECT * FROM DUAL /*WHERE NAME =:NAME*/ AND SDDSD";
	
	public void testReplaceComments () {
		
		System.out.println(SOURCE_CODE.replaceAll(COMMENTS_CODE,""));
	}
	
}
