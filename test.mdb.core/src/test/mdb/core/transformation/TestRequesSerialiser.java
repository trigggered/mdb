/**
 * 
 */
package test.mdb.core.transformation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import mdb.core.shared.transformation.mdbrequest.JSONRequestSerialiser;
import mdb.core.shared.transport.Request;
import mdb.core.shared.transport.RequestEntity;

import org.junit.Test;

/**
 * @author azhuk
 * Creation date: Jul 23, 2013
 *
 */
public class TestRequesSerialiser {
	

	@Test
	public void testRequestFromJson() {
		String jsonStr  = 
				"{\"_requestContainer\":{\"41\":{\"isRequestFieldsDescription\":true,\"_entityId\":41,\"_methodId\":0,\"_executeType\":\"GetData\",\"_params\":{},\"_name\":\"41\",\"_pagingUsage\":false,\"_page\":0,\"_rowCountInPage\":50}},\"_requestId\":844201507}";
			
		JSONRequestSerialiser ser = new JSONRequestSerialiser();
		Request  req = ser.deserialize(jsonStr);
		
		assertNotNull(req);
			 
	}
	
	@Test
	public void testListOfRequestSerialize() {
		
		List<Request> list = new ArrayList<Request>();
		Request req = new Request();
		int entityId = 100;
		req.add(new RequestEntity(entityId));
		
		list.add(req);
		JSONRequestSerialiser ser = new JSONRequestSerialiser();
		String jsonStr = ser.listSerialize(list);
		assertNotNull(jsonStr);
		
		List<Request>  desList = ser.listDeserialize(jsonStr);
		assertEquals(list.size(),  desList.size() );
		
		assertTrue(desList.get(0).existEntity( String.valueOf(entityId) ) );	
		
	}
}
