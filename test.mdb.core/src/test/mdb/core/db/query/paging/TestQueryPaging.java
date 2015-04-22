/**
 * 
 */
package test.mdb.core.db.query.paging;

import junit.framework.TestCase;
import mdb.core.db.query.paging.IQueryPaging;
import mdb.core.db.query.paging.impl.MySqlQueryPaging;
import mdb.core.db.query.paging.impl.OraQueryPaging;

/**
 * @author azhuk
 * Creation date: Nov 1, 2012
 *
 */
public class TestQueryPaging extends TestCase {
	
	
	public void testGenerateMySqlPaging() {
		String testStatement = "select * from dual";
		String expectedStatement = "\nselect * from dual\nlimit 1, 50";
		
		IQueryPaging paging = new MySqlQueryPaging();
		String actualStatement = paging.getPrepareStatement(testStatement);
		
		assertEquals(expectedStatement, actualStatement);		
	}
	
	
	public void testGenerateOraPaging() {
		final String startStatement  = "select * from  (select a.*,rownum rw from (";
		final String endStatement	    = ") a where rownum <:page_num * :rec_count + :rec_count ) where rw>=:page_num * :rec_count";
		
		String testStatement = "select * from dual";
		String expectedStatement = startStatement+"\n"+"select * from dual"+"\n"+endStatement;
		
		IQueryPaging paging = new OraQueryPaging();
		String actualStatement = paging.getPrepareStatement(testStatement);
		
		assertEquals(expectedStatement, actualStatement);		
	}
}
