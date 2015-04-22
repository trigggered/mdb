package mdb.core.shared.gw;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import mdb.core.shared.configuration.PropertyLoader;



public abstract class WebServiceClient<T>   {
	private static final Logger _logger = Logger
			.getLogger(WebServiceClient.class.getName());
	
	private static final boolean USE_HTTPS =false;
	
	static {
	    //for localhost https testing only
		if (USE_HTTPS) {
		    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
		    new javax.net.ssl.HostnameVerifier(){
	 
		        public boolean verify(String hostname,
		                javax.net.ssl.SSLSession sslSession) {
		        	return true;
		        	/*
		            if (hostname.equals("localhost")) {
		                return true;
		            }
		            return false;
		            */
		        }
		    });
		}
	}	
	
	/**
	 * 
	 */
	private static final String PORT_KEY = "port";

	/**
	 * 
	 */
	private static final String HOST_KEY = "host";

	/**
	 * 
	 */
	private static final String PROTOCOL_KEY = "protocol";
	
	/**
	 * 
	 */	
	
	private static final String PROPERTIES_FILE = "ws.properties";
	private static final String PROPERTIES_FILE_SEC = "secws.properties";
	
	/**
	 * 
	 */	
	private static final String  WSDL_LOCATION_KEY = "WSDL_LOCATION";		
	
	
	
	private String _serviceClassName;
	private Properties _properties;		
	private T _webGateway;
	
	

	public WebServiceClient(Class<T> serviceClass,  String webSrvName) {		
		loadProperties();
		setServiceClassName(webSrvName);
		Service service = Service.create(getUrl(), getQName());				 
		
		setWebService(service.getPort( serviceClass  ) );	
	}
	
	
	protected  T getWebClientProxy() {
		return _webGateway;
	}
	

	protected  void setWebService(T webInterface) {
		this._webGateway = webInterface;
	}
	
	
	public String getServiceClassName() {
		return _serviceClassName;	
	}
	
	public void setServiceClassName(String value) {
		_serviceClassName = value;
	}
	
	protected void loadProperties() {
		if (USE_HTTPS) {
			_properties = PropertyLoader.loadProperties(PROPERTIES_FILE_SEC, this.getClass());
		} else {
			_properties = PropertyLoader.loadProperties(PROPERTIES_FILE, this.getClass());
		}
	}
	
	private QName getQName() {
		return   new QName(getNamespace(), getLocalPart() );
	}
	
	
	protected abstract String getNamespace();
	
	
	protected String getLocalPart() {
		return getServiceClassName()+"Service";
	}
	
	private String  getWsdlLocation() {		
		String toReturn = String.format(getWsdlUrl(), getProtocol() , getHost(), getPort(), getServiceClassName()  );		
		_logger.info("WSDL location = "+toReturn);
		
		return toReturn;
	}	
	
	private String getWsdlUrl() {
		return _properties.getProperty(WSDL_LOCATION_KEY);
	}
	
	private String getProtocol() {
		return _properties.getProperty(PROTOCOL_KEY);
	}
	
	private String getHost()  {
		return _properties.getProperty(HOST_KEY);		
	}
	
	private String getPort() {
		return _properties.getProperty(PORT_KEY);	
	}
	
	
	private URL getUrl() {
		URL url = null;
		try {
			url = new URL(getWsdlLocation());
		} catch (MalformedURLException e) {
			_logger.severe(e.getMessage());			
		}
		 return url;
	}

}
