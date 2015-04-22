/**
 * 
 */
package mdb.core.shared.transport;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author azhuk
 * Creation date: Feb 7, 2013
 *
 */
public class ChangedData implements IsSerializable {

	
	private static final long serialVersionUID = 1L;
	private String _jsonOriginValue;
	private String _jsonChangedValue;
	
	public ChangedData() {
		
	}
	
	public ChangedData(String originValue, String changedValue) {
		setOriginValue(originValue);
		setChangedValue(changedValue);
	}
	
	public String getChangedValue() {
		return _jsonChangedValue;
	}
	
	public void setChangedValue(String jsonChangedValue) {
		_jsonChangedValue = jsonChangedValue;
	}

	public String getOriginValue() {
		return _jsonOriginValue;
	}

	public void setOriginValue(String value) {
		_jsonOriginValue = value;
	}		
	
}
