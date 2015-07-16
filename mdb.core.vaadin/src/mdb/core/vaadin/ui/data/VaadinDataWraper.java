/**
 * 
 */
package mdb.core.vaadin.ui.data;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import mdb.core.shared.transformation.json.JSONTransformation;
import mdb.core.ui.client.data.IDataWraper;
/**
 * @author azhuk
 * Creation date: Jul 21, 2015
 *
 */
public class VaadinDataWraper implements IDataWraper{
	private static final Logger _logger = Logger
			.getLogger(VaadinDataWraper.class.getName());

	 
	List<Map<String, String>> _data;


	public VaadinDataWraper() {
		
	}
	
	public VaadinDataWraper(String jsonString) {
		setData(jsonString);
	}
	
	private int _cursor =0;
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		 return this._cursor < _data.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Map<String, String> next() {				
		Map<String, String> toReturn = _data.get(_cursor);	
		_cursor ++;
		return toReturn;
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataWraper#setData(java.lang.String)
	 */
	@Override
	public void setData(String jsonString) {
		_cursor  = 0;
		_data = JSONTransformation.json2ListMap(jsonString);		
	}

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.data.IDataWraper#isDataExists()
	 */
	@Override
	public boolean isDataExists() {
		return _data!=null && _data.size() >0;
	}
}
