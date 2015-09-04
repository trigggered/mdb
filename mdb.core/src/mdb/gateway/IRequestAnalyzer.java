/**
 * 
 */
package mdb.gateway;


import javax.ejb.Local;

import mdb.core.shared.transformation.mdbrequest.IRequestSerialiser;
import mdb.core.shared.transport.Request;


/**
 * @author azhuk
 * Creation date: Aug 2, 2012
 *
 */
@Local
public interface IRequestAnalyzer {

	public Request make (byte[] request);
	
	public Request make (String request);
	
	public Request make (Request request);
	
	
	/*
	public void make (byte[] request);
	
	public void make (String request);
	
	public void make (Request request);	
	
	public Request getRequest ();	
	
	public Request getResponse ();
	*/
	public IRequestSerialiser  getSerialiser();
	
}
