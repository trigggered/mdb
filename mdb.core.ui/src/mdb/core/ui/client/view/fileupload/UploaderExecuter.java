/**
 * 
 */
package mdb.core.ui.client.view.fileupload;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import mdb.core.ui.client.events.ICallbackEvent;

/**
 * @author azhuk
 * Creation date: May 12, 2014
 *
 */
public class UploaderExecuter {

	private static final Logger _logger = Logger.getLogger(UploaderExecuter.class.getName());
	
	ICallbackEvent<Boolean> _endAllUploadEvent;
	Iterator<FileUploadComponent> _iter;
	private int _uploadedSize; 
	
	public UploaderExecuter (List<FileUploadComponent>  lstFiles, ICallbackEvent<Boolean> endAllUploadEvent  ) {
		_uploadedSize = lstFiles.size();
		_endAllUploadEvent =endAllUploadEvent;		
		_iter = lstFiles.iterator();
	}
	
	public void go() {		
		_logger.info("Start uplod files. Size list="+_uploadedSize);
		next();
	}
	
	private void next() {
		ICallbackEvent<Boolean> endEvent =  new ICallbackEvent<Boolean>() {			
			@Override
			public void doWork(Boolean data) {
				next();				
			}
		};
		
		if (_iter.hasNext()) {
			FileUploadComponent uploader = _iter.next();
			if ( !uploader.isUploded() ) {
				uploader.setEndUploadStatusHendler(endEvent);
				uploader.upload();
			} else  {
				_logger.info("File is already uplod:"+ uploader.getUploadComponent().getFilename());
				next();
			}
		}
		else {
			end();
		}
	}
	
	private void end() {
		_logger.info("Call end uppload events");
		if (_endAllUploadEvent != null) {
			_endAllUploadEvent.doWork(true);
		}
	}
	
}
