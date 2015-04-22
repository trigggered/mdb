package test.mongo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * @author azhuk
 * Creation date: Feb 20, 2015
 *
 */
public class TestMongoDB {
	private static final Logger _logger = Logger.getLogger(TestMongoDB.class
			.getCanonicalName());
	
	private MongoClient getDBClient() {
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( "localhost" , 27017 );
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		return mongoClient;
	}
	
	
	
		@Test
		public void testMongoClient() {
			// To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
			// if it's a member of a replica set:
			
						
						/*
						// or
						MongoClient mongoClient = new MongoClient( "localhost" );
						// or
						
						// or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
						MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
						                                      new ServerAddress("localhost", 27018),
						                                      new ServerAddress("localhost", 27019)));
				*/
	
			MongoClient client =   getDBClient();
			assertNotNull(client);
			
			DB db = client.getDB("db");
			
			DBCollection coll = db.getCollection("testCollection");
			
			
			BasicDBObject doc = new BasicDBObject("name", "MongoDB")
	        .append("type", "database")
	        .append("count", 1)
	        .append("info", new BasicDBObject("x", 203).append("y", 102));
			coll.insert(doc);				
			
			assertTrue(coll.count() > 0);
			
			System.out.println("Count " + coll.count());
			
			DBObject myDoc = coll.findOne();
			
			
			 DBCursor cursor = coll.find();
			 for (DBObject obj : cursor) {
				 System.out.println(obj);
				  
			 }
			 cursor.close();
			 
		}
			
}
