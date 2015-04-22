/**
 * 
 */
package mdb.core.shared.transformation;

import java.util.List;

import mdb.core.shared.transport.Request;

/**
 * @author azhuk
 * Creation date: Aug 3, 2012
 *
 */
public interface IRequestSerialiser {
	
	public String serialize (Request value);			
	public Request deserialize (String value);
	
	public String listSerialize(List<Request> list);
	public List<Request>  listDeserialize (String value);
}
