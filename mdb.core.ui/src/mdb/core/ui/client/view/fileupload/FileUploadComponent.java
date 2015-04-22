package mdb.core.ui.client.view.fileupload;

import java.util.Map;
import java.util.logging.Logger;

import mdb.core.ui.client.data.impl.ViewDataConverter;
import mdb.core.ui.client.events.ICallbackEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;
 /**
  * GWT entry point class.
  * 
  * @author hturksoy
  */
 public class FileUploadComponent extends VLayout {
     
	 private static final Logger _logger = Logger.getLogger(FileUploadComponent.class.getName());
	 
	 private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";	 
        
    
    private final FileUpload _uploadComponent;
    
    private ICallbackEvent<Map<String,String>> _endUploadEvent;
    
    private ICallbackEvent<Boolean> _endUploadStatusEvent; 
    
    final FormPanel _form = new FormPanel();
        
	private boolean _isUploded;

	private VerticalPanel _formItemsPanel = new VerticalPanel();

	
	
	/**
     * This creates file upload widget.
     * @param key, this is a directory key which identifies file upload directory. This should have mapping in
     * BDSetupPaths.properties file which exists under project root directory.
     */
    public FileUploadComponent() {
    	this.setHeight(20);
    	
    	// Create a FileUpload widget.
    	_uploadComponent = new FileUpload();
    	_uploadComponent.setName("uploadFormElement");
    	_uploadComponent.setVisible(true);
    	         //panel.add(upload);
    	
 		fillPanel();
	}
    
    
    
    
	protected void fillPanel() {
		
		_form.setAction(UPLOAD_ACTION_URL);
        // Because we're going to add a FileUpload widget, we'll need to set the
        // form to use the POST method, and multipart MIME encoding.
		//_form.setEncoding("multipart/form-data; charset=UTF-8"); 
        _form.setEncoding(FormPanel.ENCODING_MULTIPART);        
        _form.setMethod(FormPanel.METHOD_POST);            
        if (SC.isIE()) {
        	FormElement.as(_form.getElement()).setAcceptCharset("WINDOWS-1251");
		} else {        	
        	FormElement.as(_form.getElement()).setAcceptCharset("UTF-8");
		}
        
        
        // Create a panel to hold all of the form widgets.
      //  VerticalPanel panel = new VerticalPanel();
      //  panel.setHeight("10px");
        //VLayout panel=new VLayout();        
                

        // Create a FileUpload widget.
        //_uploadComponent = new MyUpload(true);        
        
        _formItemsPanel.add(_uploadComponent);
        
        /*
        Button browse = new Button("Browse");
        browse.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				clickOnInputFile(_uploadComponent.getElement());
						
			}
		});
        panel.add(browse);
        
        Button submit = new Button("Submit");
        submit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				_form.submit();		
			}
		});
        panel.add(submit);
        
        */
        
        
        
        _form.setWidget(_formItemsPanel);
        //_form.setWidget(_uploadComponent);
        _form.setHeight("20px");

        
         
        
        //_uploadComponent.setUploadComponent(this);
        
        Element ee = _uploadComponent.getElement();
        DOM.setElementPropertyInt((com.google.gwt.user.client.Element) ee, "size", 85);

        //panel.add(_uploadComponent);
        _form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				_isUploded = true;				
				
				_logger.info("Successfully uploaded file:"+_uploadComponent.getFilename());
				
				if (_endUploadEvent!= null) {
					String result = event.getResults();
					Map<String,String> responseData = ViewDataConverter.jsonToMap(result);																		
					_endUploadEvent.doWork(responseData);
				}
				
				if (_endUploadStatusEvent!=null) {
					_endUploadStatusEvent.doWork(_isUploded);
				}
			}
		});                

        addMember(_form);
	}
		 
	
	public void upload() {
		if ( !isUploded()  ) {
			_logger.info("Try upload file:"+_uploadComponent.getFilename());			
			_form.submit();
		}		
	}		
	

	public boolean isUploded() {
		return _isUploded;
	}	
	
	public boolean isFileSelected() {
		//return _uploadComponent.isFileSelected();
		return true;
	}
	
	public void setEndUploadHendler (ICallbackEvent<Map<String,String>> value) {
		_endUploadEvent = value;
	}

	public void setEndUploadStatusHendler (ICallbackEvent<Boolean> value) {
		_endUploadStatusEvent= value;
	}
	
	public MyUpload getUploadComponent() {
		//return _uploadComponent;
		return null;
	}
	
	/**
	 * @return the _form
	 */
	public FormPanel getForm() {
		return _form;
	}
	
	/**
	 * @return the _formItemsPanel
	 */	
	public VerticalPanel getFormItemsPanel() {
		return _formItemsPanel;
	}	
	
	
 }