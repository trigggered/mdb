package test.mdb.core.ws.client;


import test.mdb.core.ws.client.stub.webgateway.WebGateway;
import test.mdb.core.ws.client.stub.webgateway.WebGatewayService;

public class WebGatewayInvoke {
	
	private static WebGatewayInvoke _webEntryPointInvoke; 
	private WebGateway _webEntryPoint;
	
	public WebGatewayInvoke() {		
		
		setWebEntryPoint(new WebGatewayService().getWebGatewayPort());
	}
	
	public static WebGatewayInvoke instance() {
		if (_webEntryPointInvoke == null) {
			_webEntryPointInvoke = new WebGatewayInvoke();
		}
		return _webEntryPointInvoke;
	}

	public WebGateway getWebEntryPoint() {
		return _webEntryPoint;
	}

	private  void setWebEntryPoint(WebGateway webGateway) {
		this._webEntryPoint = webGateway;
	}

	
}
