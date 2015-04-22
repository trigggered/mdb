/**
 * 
 */
package mdb.core.ui.client.communication;


/**
 * @author azhuk
 * Creation date: Mar 14, 2014
 *
 */
public interface IRemoteDataRequest {

	public void prepareRequestData();

	public void putRequestToQueue();

	public void callRequestData();

}