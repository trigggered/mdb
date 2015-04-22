/**
 * 
 */
package mdb.core.shared.transport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Logger;

import mdb.core.shared.auth.AuthUser;
import mdb.core.shared.auth.IUserInfo;
import mdb.core.shared.utils.comparator.ComparatorByPosition;
import mdb.core.shared.utils.comparator.IPosition;
import com.google.gwt.user.client.rpc.IsSerializable;
/**
 * @author azhuk
 * Creation date: Aug 10, 2012
 *Container
 */
public class Request implements IPosition, IsSerializable {
	
	private static final Logger _logger = Logger.getLogger(Request.class.getName());
	
	private AuthUser _user;
	

	public AuthUser getUser() {
		if (_user == null) {
			_user = new AuthUser();
			_user.setName("Empty");
		}
		return _user;
	}


	public void setUser(IUserInfo  value) {
		
		_user = AuthUser.copyFrom(value);
	}

	private HashMap<String, IRequestData>  _requestContainer = new HashMap<String, IRequestData>();	
		
	private int _requestId;
	
	private int _positionNo = 0;	
	
	public Request () {
		generateNewRequestId();		
	}
	
	
	public HashMap<String, IRequestData> getMap() {
		return _requestContainer;
	}
	
	public int size() {
		return _requestContainer.size();
	}
	
	public IRequestData add (IRequestData value) {		
		_logger.info("To requestID="+getRequestId()+" add new Request Data with name: "+value.getName()+" and entityId = "+value.getEntityId());		
		_requestContainer.put(value.getName(), value);		
		return value;
	}		
	
	
	
	public Request add (Request value) {		
		_requestContainer.putAll(value.getMap());		
		return this;
	}	
	
	
	public IRequestData get (String entityId) {
		
		return _requestContainer.get(entityId);
	}
	
	public ArrayList<IRequestData> getArrayListValues() {
	
		ArrayList<IRequestData> toReturn = new ArrayList<IRequestData>(_requestContainer.values());
		Collections.sort(toReturn, new ComparatorByPosition());		
		
		return toReturn;
	}
	
	public String[] getArrayKeys() {
		String[] toReturn = _requestContainer.keySet().toArray(new String[ _requestContainer.keySet().size()]);
		return toReturn;
	}
	
	public Boolean existEntity(String entityId) {		
		return _requestContainer.containsKey(entityId);
	}
	
	public void removeEntity(String entityId) {
		if (existEntity(entityId)) {
			_requestContainer.remove(entityId);
		}		
	}	

	private int generateNewRequestId() {		
		_requestId = new java.util.Random().nextInt();		
		return _requestId;
	}
	
	public int getRequestId() {
		return _requestId;
	}
	
	public void clear() {
		_requestContainer.clear();
	}


	public int getPosition() {
		return _positionNo;
	}


	public void setPosition(int value) {
		this._positionNo = value;
	}
	
	public HashMap<String, IRequestData>  getRequestContainer() {
		return _requestContainer;
	}

}
