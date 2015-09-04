/**
 * 
 */
package mdb.core.ui.smartgwt.client.view.data;


import com.smartgwt.client.data.Record;



/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public interface ISGWTDataView {
	
		Record[] getSelectedRecords();
		Record getSelectedRecord();
}
