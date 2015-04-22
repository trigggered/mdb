package mdb.ui.server.communication.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mdb.ui.server.fileupload.MemoryFileItemFactory;
import mdb.ui.server.fileupload.UploadFileWriter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * servlet to handle file upload requests
 * 
 * @author hturksoy
 * 
 */
public class FileUploadServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private static final Logger _logger = Logger.getLogger(FileUploadServlet.class.getName());
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
    
        // process only multipart requests
        if (ServletFileUpload.isMultipartContent(req)) {

            // Create a factory for disk-based file items
            FileItemFactory factory = new MemoryFileItemFactory();
        	

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
           // upload.setHeaderEncoding("UTF-8");

            // Parse the request
            try {
                List<FileItem> items = upload.parseRequest(req);
                for (FileItem item : items) {
                    // process only file upload - discard other form item types
                    if (item.isFormField()) continue;                    
                    
                    _logger.info("Try write file name = "+item.getName());                    
                   
                    if (UploadFileWriter.write(item) ) {
                    	
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        
                        resp.getWriter().print(
                        		String.format("The file %s size is %s was created successfully.", item.getName(), item.getSize()));
                        resp.flushBuffer();
                    } else
                        throw new IOException("The file already exists in repository.");
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "An error occurred while creating the file : " + e.getMessage());
            }

        } else {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                            "Request contents type is not supported by the servlet.");
        }
    }    
    
}