package test.mdb.gateway;


import java.io.IOException;

import junit.framework.TestCase;
import mdb.core.shared.data.Rows;
import mdb.core.shared.transformation.IDataTransformation;
import mdb.core.shared.transformation.IRequestSerialiser;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.shared.utils.zip.GZipHelper;
import test.mdb.core.ws.client.WebGatewayInvoke;
import test.mdb.core.ws.client.stub.webgateway.WebGateway;
import test.mdb.environment.TestInjectConfiguration;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class TestWebGateway extends TestCase {

	private IRequestSerialiser _requestSerialiser;
	private IDataTransformation _dataTransformation;
	
	public TestWebGateway () {
		Injector injector = Guice.createInjector(new TestInjectConfiguration());
		_requestSerialiser = injector.getInstance(IRequestSerialiser.class);
		_dataTransformation = injector.getInstance(IDataTransformation.class);
	}
	
	public void testOpen() {
		int entityId = 201;
		
		WebGateway gateway =  WebGatewayInvoke.instance().getWebEntryPoint();		
		assertNotNull(gateway);		
				
		
		String response = gateway.open(entityId);		

		assertNotNull(response);
		System.out.print(response);
	}
	
	private  byte[] getRequestedBytes() {
		
		byte[] compressedRequest = null;
		
		Request request = new Request();
		RequestEntity requestEntity = new RequestEntity(201);
		request.add(requestEntity);
				
		
		
		try {
			compressedRequest =  GZipHelper.compress(_requestSerialiser.serialize(request));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return compressedRequest;
		
	}
	
	public void testMain() {		
	
		byte[] compressedRequest = getRequestedBytes();
		assertTrue(compressedRequest.length >0);
		
		
		WebGateway gateway =  WebGatewayInvoke.instance().getWebEntryPoint();		
		assertNotNull(gateway);
		byte[] compressedResponse =  gateway.main(compressedRequest);
		assertTrue( compressedResponse.length>0);
		
		String strResponse = new String(GZipHelper.uncompress(compressedResponse));
		assertNotNull(strResponse);
		
		Request  response = _requestSerialiser.deserialize( strResponse);
		assertNotNull(strResponse);
		
		for ( IRequestData entity  : response.getArrayListValues()) {
			assertNotNull(entity.getData());
			Rows rows = _dataTransformation.make(entity.getData());
			
			assertTrue(rows.getRows().size() >0);
		}		
		
	}
	
	
	
}
