/**
 * 
 */
package test;

import junit.framework.Test;
import junit.framework.TestSuite;
import test.mdb.core.db.TestEntityDataAccess;
import test.mdb.core.transformation.TestDataTransformation;
import test.mdb.core.util.json.TestJsonGoogleImpl;
import test.mdb.core.util.zip.TestZipHelper;
import test.mdb.gateway.TestRequestAnalyze;

/**
 * @author azhuk
 * Creation date: Aug 9, 2012
 *
 */
public class AllTests {
	
	

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		//suite.addTestSuite(TestWebGateway.class);
		suite.addTestSuite(TestJsonGoogleImpl.class);
		suite.addTestSuite(TestDataTransformation.class);		
		suite.addTestSuite(TestEntityDataAccess.class);
		suite.addTestSuite(TestJsonGoogleImpl.class);
		suite.addTestSuite(TestRequestAnalyze.class);
		suite.addTestSuite(TestZipHelper.class);
		//$JUnit-END$
		return suite;
	}
}
