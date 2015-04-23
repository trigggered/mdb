/**
 * 
 */
package mdb.core.ui.client.data.export;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * @author azhuk
 * Creation date: Apr 16, 2015
 *
 */
public class ExportHelper {
	
	
	public static  StringBuilder exportCSV(ListGrid listGrid) {
		StringBuilder stringBuilder = new StringBuilder(); // csv data in here
		
		// column names
		ListGridField[] fields = listGrid.getFields();
		for (int i = 0; i < fields.length; i++) {
			ListGridField listGridField = fields[i];
			stringBuilder.append("\"");
			stringBuilder.append(listGridField.getTitle());
			stringBuilder.append("\",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1); // remove last ","
		stringBuilder.append("\n");
		
		// column data
		ListGridRecord[] records = listGrid.getRecords();
		for (int i = 0; i < records.length; i++) {
			ListGridRecord listGridRecord = records[i];
			ListGridField[] listGridFields = listGrid.getFields();
			for (int j = 0; j < listGridFields.length; j++) {
				ListGridField listGridField = listGridFields[j];
				
				stringBuilder.append("\"");
				stringBuilder.append(listGridRecord.getAttribute(listGridField.getName()));
				stringBuilder.append("\",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1); // remove last ","
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}
	
}
