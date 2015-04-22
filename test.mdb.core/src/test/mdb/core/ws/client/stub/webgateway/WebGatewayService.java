
package test.mdb.core.ws.client.stub.webgateway;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "WebGatewayService", targetNamespace = "http://impl.gateway.mdb/", wsdlLocation = "http://localhost:8080/mdb.core/WebGateway?wsdl")
public class WebGatewayService
    extends Service
{

    private final static URL WEBGATEWAYSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(test.mdb.core.ws.client.stub.webgateway.WebGatewayService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = test.mdb.core.ws.client.stub.webgateway.WebGatewayService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/mdb.core/WebGateway?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/mdb.core/WebGateway?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        WEBGATEWAYSERVICE_WSDL_LOCATION = url;
    }

    public WebGatewayService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WebGatewayService() {
        super(WEBGATEWAYSERVICE_WSDL_LOCATION, new QName("http://impl.gateway.mdb/", "WebGatewayService"));
    }

    /**
     * 
     * @return
     *     returns WebGateway
     */
    @WebEndpoint(name = "WebGatewayPort")
    public WebGateway getWebGatewayPort() {
        return super.getPort(new QName("http://impl.gateway.mdb/", "WebGatewayPort"), WebGateway.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebGateway
     */
    @WebEndpoint(name = "WebGatewayPort")
    public WebGateway getWebGatewayPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.gateway.mdb/", "WebGatewayPort"), WebGateway.class, features);
    }

}
