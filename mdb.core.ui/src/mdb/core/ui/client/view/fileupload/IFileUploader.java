/**
 * 
 */
package mdb.core.ui.client.view.fileupload;

import mdb.core.ui.client.events.ICallbackEvent;

/**
 * @author azhuk
 * Creation date: May 6, 2014
 *
 */
public interface IFileUploader {
	public void addFileForUpload ();
	public int	 getCountFileForUpload();
	public void uploadAllFiles(ICallbackEvent<Boolean> callbackEvent);
	
	public void clearFilesForUpload();	
	public void addCompleatUploadItem (ICallbackEvent<String> handler);
}
