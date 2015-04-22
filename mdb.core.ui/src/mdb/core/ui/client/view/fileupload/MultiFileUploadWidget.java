/**
 * 
 */
package mdb.core.ui.client.view.fileupload;

import java.util.ArrayList;
import java.util.List;

import mdb.core.ui.client.events.ICallbackEvent;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author azhuk
 * Creation date: Aug 7, 2013
 *
 */
public class MultiFileUploadWidget extends VLayout /*implements IFileUploader */ {


	private Layout _vLayoutForUploadedFiles = new VLayout();
	private List<FileUploadComponent>  _lstFiles = new ArrayList<FileUploadComponent>();	
	
	
	public MultiFileUploadWidget() {
		setMembersMargin(5);
		createComponents();
	}
	
	
	
	protected void createComponents () {			
		
		Button btnClear = new Button("Очистить");
		btnClear.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				clearFilesForUpload();				
			}
		});
		
		
        Layout btnLayout = new HLayout(1);
        
        btnLayout.setAlign(Alignment.LEFT);
        btnLayout.setMargin(10);
        btnLayout.setMembersMargin(10);		 

        btnLayout.addMembers(btnClear);
        
        btnLayout.setHeight(40);

        
        this.addMember(btnLayout);
		
		addMember(_vLayoutForUploadedFiles);
	}
	
	
	
	public FileUploadComponent addFileForUpload () {
		FileUploadComponent  uploadComponent = createUploadComponent ();				
		setUploadComponent(uploadComponent);		
		return uploadComponent;				
	}
	
	public void setUploadComponent(FileUploadComponent value) {
		_vLayoutForUploadedFiles.addMember(value);
		_lstFiles.add(value);
		_vLayoutForUploadedFiles.redraw();
	}
	
	
	public FileUploadComponent createUploadComponent () {
		FileUploadComponent toReturn = new  FileUploadComponent();		
		 return toReturn;
	}
	
	public void uploadAllFiles(ICallbackEvent<Boolean> endAllUploadEvent) {
		
		UploaderExecuter executer = new UploaderExecuter (_lstFiles, endAllUploadEvent); 
		executer.go();		
		
	}
	
	
	
	public void clearFilesForUpload() {
		_lstFiles.clear();
		///_vLayoutForUploadedFiles.clear();
		for (Canvas canvas : _vLayoutForUploadedFiles.getMembers()) {
			_vLayoutForUploadedFiles.removeMember(canvas);
		}
	}


	/* (non-Javadoc)
	 * @see mdb.core.ui.client.view.fileupload.IFileUploader#getCountFileForUpload()
	 */
	
	public int getCountFileForUpload() {
			return _lstFiles.size();		
	}	

}
