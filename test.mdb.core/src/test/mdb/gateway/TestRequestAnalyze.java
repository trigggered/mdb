/**
 * 
 */
package test.mdb.gateway;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;
import mdb.core.shared.transformation.mdbrequest.IRequestSerialiser;
import mdb.core.shared.transport.IRequestData;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;
import mdb.core.shared.utils.zip.GZipHelper;
import mdb.gateway.IRequestAnalyzer;
import test.mdb.environment.TestInjectConfiguration;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author azhuk
 * Creation date: Aug 3, 2012
 *
 */
public class TestRequestAnalyze extends TestCase {
	
	private static final Logger _logger = Logger.getLogger(TestRequestAnalyze.class
			.getCanonicalName());	
	
	private IRequestSerialiser _requestSerialiser;
	private IRequestAnalyzer 	_requestAnalyzer;
	
	public TestRequestAnalyze () {
		Injector injector = Guice.createInjector(new TestInjectConfiguration());
		_requestSerialiser = injector.getInstance(IRequestSerialiser.class);
		_requestAnalyzer = injector.getInstance(IRequestAnalyzer.class);
	}
	
	private Request getRequest() {
		Request  toReturn = new Request();
		IRequestData requestEntity =  new RequestEntity(21);
		requestEntity.getParams().add("PID_DENTITY", "1428");				
		
		toReturn.add(requestEntity);    	
		return toReturn;
	}
	
	public void testRequest() {
		
		Request  requestTo = getRequest();
		
		String strReq = _requestSerialiser.serialize(requestTo);
		assertNotNull(strReq);
		
		Request  requestFrom =  _requestSerialiser.deserialize(strReq);
		
		//assertEquals( requestFrom.getRequesEntitys().size() ,requestTo.getRequesEntitys().size() );		
	}
	
	public void testRequestAnalyzer () {
		Request  requestTo = getRequest();
		String strReq = _requestSerialiser.serialize(requestTo);
		
		Request response =  _requestAnalyzer.make(strReq);
		
		
		
		assertNotNull(response);
		
		
		for ( IRequestData requestEntity  : response.getArrayListValues()  ) {
			//assertNotNull(requestEntity.getResult());
			assertNotNull(requestEntity.getData());
			_logger.log(Level.INFO, requestEntity.getData());
		}		
		
		_logger.log(Level.INFO, "serialize response ");
		String strRes = _requestSerialiser.serialize(response);
		assertNotNull(strRes);
		_logger.log(Level.FINE, strRes);
	}
	
	
	public void TestRequestAnalyzer2 () {
		Request  requestTo = getRequest();
		String strReq = _requestSerialiser.serialize(requestTo);
		byte[] byteReq = null;
		
		try {
			byteReq =  GZipHelper.compress(strReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Request response =  _requestAnalyzer.make(byteReq);		
		
		assertNotNull(response);
		
		for ( IRequestData requestEntity  : response.getArrayListValues() ) {
			//assertNotNull(requestEntity.getResult());
			assertNotNull(requestEntity.getData());
			_logger.log(Level.INFO, requestEntity.getData());
		}		
		
		_logger.log(Level.INFO, "serialize response ");
		String strRes = _requestSerialiser.serialize(response);
		assertNotNull(strRes);
		_logger.log(Level.FINE, strRes);
		
		byte[] byteResponse = null;
		try {
			byteResponse =  GZipHelper.compress(strRes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(byteResponse.length > 0 );		
	}
	
	
}
