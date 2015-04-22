/**
 * 
 */
package mdb.core.ui.client.view.fileupload;


import java.util.logging.Logger;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

/**
 * @author azhuk
 * Creation date: Oct 2, 2013
 *
 */
public class MyUpload extends Composite {	
	
	
	private static final Logger _logger = Logger.getLogger(MyUpload.class.getName());
	

    private static native void clickOnInputFile(Element elem) /*-{
        elem.click();        
    }-*/;

    //final FileUploadWithMouseEvents _upload;
    final FileUpload _upload;
    final Button _btnBrowse;
    
    private FileUploadComponent _uploadComponent;
    final Progressbar _progressBar = new Progressbar();
    
    
    private String _btnBrowseCaption = "Browse file";
    
    public MyUpload(boolean isBrowseBtnShow) {                      
    	
        _upload= new FileUpload();
        _upload.setName("uploadFormElement");
        _upload.setVisible(true);                      
        
        String mimeList ="image/png, image/gif,image/jpeg,"
        		+ "application/zip,"
        		+ "application/pdf, text/rtf,"
        		+ "application/vnd.ms-excel,"
        		+ "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,"
        		+ "application/vnd.ms-excel.sheet.macroEnabled.12,"
        		+ "application/vnd.ms-powerpoint,"
        		+ "application/vnd.openxmlformats-officedocument.presentationml.presentation,"
        		+ "application/vnd.ms-powerpoint.presentation.macroEnabled.12,"
        		+ "application/msword,"
        		+ "application/vnd.openxmlformats-officedocument.wordprocessingml.document,"
        		+ "application/vnd.ms-word.document.macroEnabled.12";
        
        
        _upload.getElement().setAttribute("accept", mimeList);                
        
        _upload.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				SC.say("FileUpload change. File name is "+_upload.getFilename());
				_logger.info("FileUpload change. File name is "+_upload.getFilename());
				//callbackEvent.doWork(_upload.getFilename());
				
				_progressBar.setPercentDone(0);
				_progressBar.setTitle(_upload.getFilename());
			}
		});
		
                
        AbsolutePanel container = new AbsolutePanel();
        container.add(_upload);
        
        _btnBrowse = new Button(_btnBrowseCaption);
        
        _btnBrowse.setVisible(isBrowseBtnShow);
        //Button btn = new Button(_btnBrowseCaption);

        
        _btnBrowse.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {				
				selectFileDialog();
			}
		});
        
        Button btnGetFileName = new Button("GetFileName");
        btnGetFileName.setVisible(isBrowseBtnShow);
        btnGetFileName.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				SC.say("File Name is:"+_upload.getFilename());
					
			}
		});
        
        Button btnUpload = new Button("Upload");
        btnUpload.setVisible(isBrowseBtnShow);
        btnUpload.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			 if (_uploadComponent!=null) {
				_uploadComponent.upload();
			 }
			}
		});
        
        _progressBar.setHeight(24);  
        _progressBar.setVertical(false);
        _progressBar.setShowTitle(true);         
        
        Layout horizontalBars= new HLayout();
        horizontalBars.setWidth(500);
        horizontalBars.setStyleName("fileUpload");        
        horizontalBars.setMargin(5);
        horizontalBars.addMembers(_btnBrowse, btnUpload, _progressBar);
        
        container.add(horizontalBars);
        
        initWidget(container);
        
    }
    
   
    
    public String getFilename() {
    	return _upload.getFilename();
    }
    
    public void click() {
    	clickOnInputFile(_btnBrowse.getElement());
    }
    
    
    public void selectFileDialog() { 
    	clickOnInputFile(_upload.getElement());
    }
    
    public boolean isFileSelected() {
    	return (getFilename() == null || getFilename().trim().length()==0);
    }



	public FileUploadComponent getUploadComponent() {
		return _uploadComponent;
	}



	public void setUploadComponent(FileUploadComponent value) {
		_uploadComponent = value;
	}



	/**
	 * 
	 */
	public void uploadComplete() {
		// TODO Auto-generated method stub
		_progressBar.setPercentDone(100);
	}
    
}