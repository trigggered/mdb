
package test.mdb.core.transformation;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;
import mdb.core.db.IEntityDataAccess;
import mdb.core.shared.data.Params;
import mdb.core.shared.transformation.json.JSONTransformation;
import mdb.core.shared.transformation.sql.IResultSetSerializer;
import mdb.core.shared.transformation.sql.ResultSetJacksonSerializerImpl;
import mdb.core.shared.transformation.sql.ResultSetManualSerializerImpl;
import mdb.injector.SingletonInjector;

import org.junit.Test;

import test.mdb.core.db.TestEntityDataAccess;
import test.mdb.environment.TestInjectConfiguration;

import com.google.inject.Guice;
import com.google.inject.Injector;



/**
 * @author azhuk
 * Creation date: Aug 1, 2012
 *
 */
public class TestDataTransformation extends TestCase {
	private static final Logger _logger = Logger.getLogger(TestEntityDataAccess.class.getCanonicalName());
	
	private IEntityDataAccess _entityDataAccesst;
	private final int ID_DS_METHODS = 1091;
	
	
	public TestDataTransformation() {
		
		Injector injector = Guice.createInjector(new TestInjectConfiguration());
		SingletonInjector.setInjector(injector);
		_entityDataAccesst = injector.getInstance(IEntityDataAccess.class);
    
	}
	
	public ResultSet getResultSet () {		
		_logger.log(Level.INFO, "Start call");
		int entityId = 1428;					
		Params params  = new Params();
		params.add("TaskId", "1");
		ResultSet rs = _entityDataAccesst.getResultSet(entityId, params);		
		_logger.log(Level.INFO, "End call");		
		return rs;		 
	}	
	
	public ResultSet getResultSet1275 () {		
		_logger.log(Level.INFO, "Start call");
		int entityId = 1275;					
		Params params  = new Params();
		params.add("ID_DENTITY", "1275");
		ResultSet rs = _entityDataAccesst.getResultSet(entityId, params);		
		_logger.log(Level.INFO, "End call");		
		return rs;		 
	}
	
	public ResultSet getEnitiesMethods (int entityId) {		
		_logger.log(Level.INFO, "Start call");
							
		Params params  = new Params();
		
		params.add("ID_DENTITY", String.valueOf(entityId));
		
		ResultSet rs = _entityDataAccesst.getResultSet(ID_DS_METHODS, params);		
		_logger.log(Level.INFO, "End call");		
		return rs;		 
	}
	
	public ResultSet getResultSet (int entityId) {				
		ResultSet rs = _entityDataAccesst.getResultSet(entityId);
		return rs;		 
	}
	
	
	@Test
	public void testData() {
		ResultSet rs =  getResultSet1275();
		assertNotNull(rs);
	}
	
	@Test
	public void testResultSet2Json() {
		//final int ENTITY_ID = 5028;
		final int ENTITY_ID = 5309;
		final int EXPECTED_ROW_COUNT = 1;
		
		ResultSet rs = getResultSet(ENTITY_ID);
		IResultSetSerializer resultSetSerializer = new  ResultSetManualSerializerImpl();
		String jsonStr1 = resultSetSerializer.make(rs);		
		
		assertNotNull(jsonStr1);
		_logger.info("######################################################");
		_logger.info("######################################################");
		_logger.info(jsonStr1);		
		
		List<Map<String, String>> arrMap1=  JSONTransformation.json2ListMap(jsonStr1);
		assertNotNull(arrMap1);
		assertEquals(EXPECTED_ROW_COUNT, arrMap1.size());		
		
		_logger.info("######################################################");
		_logger.info("######################################################");
		
		_entityDataAccesst.getQueryPool().closeAll();
		rs = getResultSet(ENTITY_ID);
		resultSetSerializer = new  ResultSetJacksonSerializerImpl();
		String jsonStr2 = resultSetSerializer.make(rs);				
		
		assertNotNull(jsonStr2);
		_logger.info(jsonStr2);		
		
		List<Map<String, String>> arrMap2=  JSONTransformation.json2ListMap(jsonStr2);
		assertNotNull(arrMap2);
		assertEquals(EXPECTED_ROW_COUNT, arrMap2.size());		
		_entityDataAccesst.getQueryPool().closeAll();
	}
	
	/*
	public void testWebRowsetonversion() {
		IDataTransformation dataConversion = new XMLTransformation();
		ResultSet data = getResultSet();
		assertNotNull(data);
		String convertData = dataConversion.make(data);
		assertNotNull(convertData);		
	}
	
	public void testJSONConversion() {
		IDataTransformation dataTransformation = new JSONTransformation();
		ResultSet data = getResultSet();
		assertNotNull(data);
		String convertData = dataTransformation.make(data);
		
		assertNotNull(convertData);
		_logger.log(Level.INFO,convertData);
		
		Rows rowsAfter = dataTransformation.make(convertData);
		
		assertTrue( rowsAfter.getFields().size()>0);
		assertTrue( rowsAfter.getRows().size()>0);	
		
	}
	*/
	
	
	public void testLobStorInfo() {
	/*	Map<String,String> infoToJson = new HashMap<String,String>();
		infoToJson.put(ELobStoringResultProp.AttributeId.toString(), String.valueOf(100) );
		infoToJson.put(ELobStoringResultProp.LobName.toString(), "Qwerty");
		infoToJson.put(ELobStoringResultProp.StoringStatus.toString(), "Ok");
		
		
		
		Gson gson = new Gson();		
		String jsonStr = gson.toJson(infoToJson);
		
		
		System.out.println(jsonStr);
		Map<String,String> infoFromJson = gson.fromJson(jsonStr, Map.class); 
		
		assertEquals(infoToJson.get(ELobStoringResultProp.AttributeId), infoFromJson.get(ELobStoringResultProp.AttributeId));
		
		*/		
	}
	
}
