/**
 * 
 */
package mdb.gateway.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;


/**
 * @author azhuk
 * Creation date: Jan 30, 2015
 *
 */
//The Java class will be hosted at the URI path "/MDBRestGateway"

@Path("/MDBRestGateway")
public class RestGateway {
	private static final Logger _logger = Logger.getLogger(RestGateway.class
			.getCanonicalName());
	
	
	// The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces(MediaType.TEXT_PLAIN )
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }
    
}
