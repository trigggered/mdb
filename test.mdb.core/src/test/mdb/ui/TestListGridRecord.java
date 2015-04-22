/**
 * 
 */
package test.mdb.ui;

import java.util.logging.Logger;

import junit.framework.TestCase;

import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * @author azhuk
 * Creation date: Oct 10, 2012
 *
 */
public class TestListGridRecord extends TestCase //GWTTestCase 
{
	private static final Logger _logger = Logger
			.getLogger(TestListGridRecord.class.getName());

	public String getModuleName() {
		return "";
	}
	
	
	public void testGridRecord() {
		assertEquals(true, true);
		
		ListGridRecord record = new ListGridRecord();
		//JavaScriptObject jsObj = new JavaScriptObject();
		
		record.setAttribute("ID", 100);
		record.setAttribute("NAME", "Petya");
		
		//record.
		JSOHelper helper = null;
		
		
		
	}
}
