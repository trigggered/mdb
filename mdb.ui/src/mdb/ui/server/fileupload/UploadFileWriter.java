/**
 * 
 */
package mdb.ui.server.fileupload;

import java.util.logging.Logger;

import mdb.core.shared.gw.LobManagerClientBuilder;
import mdb.core.shared.gw.RemoteServiceClientType;
import mdb.core.shared.gw.lob.ILobManagerRemote;

import org.apache.commons.fileupload.FileItem;

/**
 * @author azhuk
 * Creation date: Aug 8, 2013
 *
 */
public class UploadFileWriter {
	private static final Logger _logger = Logger
			.getLogger(UploadFileWriter.class.getName());
	
	public static  boolean write (FileItem item) {
		
		_logger.info("Begin Write file:" +item.getName() + " size:"+item.getSize());
		ILobManagerRemote client =  LobManagerClientBuilder.create(RemoteServiceClientType.WebSrvInvoke);
		client.putLob(item.getName(), null, 1,item.get());
		_logger.info("Succes Write file:" +item.getName() + " size:"+item.getSize());
		return true;
	}
}
