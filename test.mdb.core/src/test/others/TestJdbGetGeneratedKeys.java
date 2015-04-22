/**
 * 
 */
package test.others;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import junit.framework.TestCase;

/**
 * @author azhuk
 * Creation date: May 14, 2013
 *
 */
public class TestJdbGetGeneratedKeys extends TestCase {
	private static final Logger _logger = Logger
			.getLogger(TestJdbGetGeneratedKeys.class.getCanonicalName());
	
	private String oraDriverName = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
	private String insertCmd = "INSERT INTO IDIC.TYPECL_COMBI (ID, TYPECL, COLUMN_NAME, NAME, COL_ORDER)   VALUES   (IDIC.SEQ_TYPECL_COMBI.NEXTVAL, 1, 1, 1, 1)";
	
	private String insertCmd2  = "insert into IDIC.cred_dic_comis_par(comid, parid, value)   values (400, 8, 1)";
	
	public void testReturnRowId() {
		
		
		Connection conn = null;
		try {
			Class.forName (oraDriverName);
			conn = DriverManager.getConnection (url, "andr", "andr");
			 Statement stmt = conn.createStatement();
			 
			 stmt.executeUpdate(insertCmd, Statement.RETURN_GENERATED_KEYS);
			 ResultSet rs = stmt.getGeneratedKeys();
			 if(rs.next()){
				 String id = rs.getString(1);
				 _logger.info("Successful insert. Key is: "+id );
			    
			    assertNotNull(id);
			 }
			 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void testReturnCurrentKey() {
		
		
		Connection conn = null;
		try {
			Class.forName (oraDriverName);
			conn = DriverManager.getConnection (url, "andr", "andr");			 
			 
			 String generatedColumns[] = {"ID"};
			 PreparedStatement pstmt = conn.prepareStatement(insertCmd, generatedColumns);			 
			 pstmt.executeUpdate();
			 
			 ResultSet rs = pstmt.getGeneratedKeys();
			 if(rs.next()){
				 String id = rs.getString(1);
				 _logger.info("Successful insert. Key is: "+id );
			    
			    assertNotNull(id);
			 }
			 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testReturnCurrentKeyFromTriggerGenerate() {		
		
		Connection conn = null;
		try {
			Class.forName (oraDriverName);
			conn = DriverManager.getConnection (url, "andr", "andr");
			 
			 
			 String generatedColumns[] = {"ID"};
			 PreparedStatement pstmt = conn.prepareStatement(insertCmd2, generatedColumns);			 
			 pstmt.executeUpdate();
			 
			 ResultSet rs = pstmt.getGeneratedKeys();
			 if(rs.next()){
				 String id = rs.getString(1);
				 _logger.info("Successful insert. Key is: "+id );
			    
			    assertNotNull(id);
			 }
			 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
