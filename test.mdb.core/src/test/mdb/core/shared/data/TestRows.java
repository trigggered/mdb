/**
 * 
 */
package test.mdb.core.shared.data;

import java.util.ArrayList;

import junit.framework.TestCase;
import mdb.core.shared.data.Rows;

/**
 * @author azhuk
 * Creation date: Sep 18, 2012
 *
 */
public class TestRows extends TestCase {
	
	
	public void test () {
		
		Rows rows = new Rows();
		
		for (int i = 0; i <5; i++) {
			Rows.Row row = rows.addRow();		
			row.add("Test row "+ i+ "Col1");
			row.add("Test row "+ i +"Col2");
		}
		
		assertTrue( rows.getRows().size() == 5);
		
		ArrayList<Rows.Row>  rowss = rows.getRows();	
		
		
		for (int i=0; i<rowss.size(); i++) {
		 Rows.Row row	= rowss.get(i);
		 System.out.println(row.get(0) + " " + row.get(1));		 
		 
		}
		
		//for (Rows.Row row : rowss) {			
		for (Rows.Row row : rows.getRows()) {
			 System.out.println(row.get(0) + " " + row.get(1));
		}
	}
}
