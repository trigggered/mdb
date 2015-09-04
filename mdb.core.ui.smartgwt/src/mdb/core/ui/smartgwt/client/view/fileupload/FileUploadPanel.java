package mdb.core.ui.smartgwt.client.view.fileupload;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Progressbar;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
 /**
  * GWT entry point class.
  * 
  * @author hturksoy
  */
@Deprecated
 public class FileUploadPanel extends VLayout {
    private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";
    
    
    final Progressbar _progressBar = new Progressbar();
    
    /**
     * This creates file upload widget.
     * @param key, this is a directory key which identifies file upload directory. This should have mapping in
     * BDSetupPaths.properties file which exists under project root directory.
     */
    public FileUploadPanel() {
 		fillPanel();
	}
    
    
	protected void fillPanel() {

        // Create a FormPanel and point it at a service.
       
		final FormPanel form = new FormPanel();
        form.setAction(UPLOAD_ACTION_URL);

        // Because we're going to add a FileUpload widget, we'll need to set the
        // form to use the POST method, and multipart MIME encoding.
        form.setEncoding(FormPanel.ENCODING_MULTIPART);        
        form.setMethod(FormPanel.METHOD_POST);
        
        

        // Create a panel to hold all of the form widgets.
        VerticalPanel panel = new VerticalPanel();
        //VLayout panel=new VLayout();
        
        
        form.setWidget(panel);

        final MyUpload uploadComponent = new MyUpload(false);
        // Create a FileUpload widget.
        /*
        final MyUpload uploadComponent = new MyUpload(false, new ICallbackEvent<String>() {
			
			@Override
			public void doWork(String data) {
				_progressBar.setPercentDone(0);
				_progressBar.setTitle(data);
			}
		});  
        */
        //final FileUpload uploadComponent = new FileUpload();        
        //uploadComponent.setName("uploadFormElement");
        
        Element ee = uploadComponent.getElement();
        DOM.setElementPropertyInt((com.google.gwt.user.client.Element) ee, "size", 85);

        panel.add(uploadComponent);
        
        form.addSubmitCompleteHandler(new SubmitCompleteHandler() {			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				_progressBar.setPercentDone(100);
				SC.say("result: "+event.getResults());
				//Here you can do whatever you want after file upload.				
			}
		});

        Button btnAddFile = new Button("Add File", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				uploadComponent.selectFileDialog();
			}
		}); 
        	
        
        Button btnLoad=new Button("Upload", new ClickHandler() {
            public void onClick(ClickEvent event) {
            	
            	if(uploadComponent.getFilename()==null || uploadComponent.getFilename().trim().length()==0){            		
            		SC.say("Please select a file to load.");
            	}else
            	{
            		form.submit();
            	}
            }
        });
        
        panel.setStyleName("fileUpload");
        
        
        Layout horizontalBars = new HLayout();
        //horizontalBars.setMembersMargin(5);
        horizontalBars.setWidth(500);  
        
        final Label hBar1Label = new Label("Current File Progress");  
        hBar1Label.setHeight(16);         
        
          
        _progressBar.setHeight(24);  
        _progressBar.setVertical(false);
        _progressBar.setShowTitle(true);
        //progressBar.setTitle(title)        
        
        horizontalBars.addMember(btnAddFile);
        horizontalBars.addMember(btnLoad);
        horizontalBars.addMember(hBar1Label);
        horizontalBars.addMember(_progressBar); 
        
        panel.add(horizontalBars);
        //panel.add(btnLoad);       

        addMember(form);		
	}

 }