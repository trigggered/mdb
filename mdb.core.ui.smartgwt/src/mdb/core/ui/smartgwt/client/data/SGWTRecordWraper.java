/**
 * 
 */
package mdb.core.ui.smartgwt.client.data;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import mdb.core.ui.client.data.IDataWraper;

import com.smartgwt.client.data.Record;

/**
 * @author azhuk
 * Creation date: Jul 21, 2015
 *
 */
public class SGWTRecordWraper implements IDataWraper{
	private static final Logger _logger = Logger
			.getLogger(SGWTRecordWraper.class.getName());

	private Record[] _data;
	private int _cursor;
	
	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataWraper#setData(java.lang.String)
	 */
	@Override
	public void setData(String jsonString) {
		_cursor = 0;
		
		_data =  ViewDataConverter.jSon2RecordArray(jsonString);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataWraper#isDataExists()
	 */
	@Override
	public boolean isDataExists() {
		return _data != null && _data.length>0;
	}
	
	public Record[] getRecords() {
		return _data;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this._cursor < _data.length;		
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Map<String, String> next() {
		Record rec = _data[_cursor];
		Map<String, String> toReturn = new HashMap<String,String>();
		for (String attrName : rec.getAttributes()) {
			toReturn.put(attrName, rec.getAttribute(attrName));
		}
		_cursor ++;
		return toReturn;
	}
	
	public void remove() {
		
	}
}
