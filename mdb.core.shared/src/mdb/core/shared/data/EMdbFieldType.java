/**
 * 
 */
package mdb.core.shared.data;



/**
 * @author azhuk
 * Creation date: Jan 30, 2014
 *
 */
public enum EMdbFieldType {	
	UNKNOWN(0),	
	NUMBER(1),   			    			
	FLOAT(2),		 
	VARCHAR(3),			
	DATE(4),		
	BOOLEAN(5),
	CURRENCY(6),    			
	LOOK_UP(7),
	FORM(8),    			
	EDIT_BUTTON(9),    			
	CHECK_COMBO_BOX(10),    			
	INN (11),    			
	FLOAT_E8(12),    							 
	FLOAT_FORMAT(13),
	MEMO(14),
	CONTEXT_VALUE(15);
	
	
	private int value;    
	
	private EMdbFieldType (int value) {
	    this.value=value;
   }

   public int getValue() {
	    return value;
   }	
   
   public static EMdbFieldType fromInt(int value) {
	   switch (value) {
	   case 1 : return NUMBER;
	   case 2 : return FLOAT;
	   case 3 : return VARCHAR;
	   case 4 : return DATE;
	   case 5 : return BOOLEAN;
	   case 6 : return CURRENCY;
	   case 7 : return LOOK_UP;
	   case 8 : return FORM;
	   case 9 : return EDIT_BUTTON;
	   case 10: return CHECK_COMBO_BOX;
	   case 11: return INN;
	   case 12: return FLOAT_E8;
	   case 13: return FLOAT_FORMAT;
	   case 14: return MEMO; 
	   case 15: return CONTEXT_VALUE;
		   
	   default:
		   return UNKNOWN;
	   }
   }
   
  public static EMdbFieldType fromSqlsqlTypee (int sqlType) {
	   
	     
	  if ( sqlType == java.sql.Types.TINYINT 
			   	|| sqlType == java.sql.Types.SMALLINT		   
		   		|| sqlType == java.sql.Types.INTEGER
		   		|| sqlType == java.sql.Types.BIGINT) {
		   
		   return NUMBER;
	   }
	   
	   else if (sqlType == java.sql.Types.FLOAT 
			   	||	sqlType == java.sql.Types.REAL 
			   	||	sqlType == java.sql.Types.DOUBLE
			   	||  sqlType == java.sql.Types.NUMERIC 
			   	||  sqlType == java.sql.Types.DECIMAL ) {
		   return FLOAT;
	   }	   
	   
	   else if (sqlType == java.sql.Types.CHAR
			   || sqlType == java.sql.Types.VARCHAR 
			   ||sqlType == java.sql.Types.LONGVARCHAR) {
		   return VARCHAR;
	   }
	   
	   else if (sqlType == java.sql.Types.DATE
			   || sqlType == java.sql.Types.TIME 
			   || sqlType == java.sql.Types.TIMESTAMP ) {
		   return DATE;
	   }
	   
	   else if (sqlType == java.sql.Types.CLOB ) {
		   return MEMO;   
	   }/*
			   
			|| sqlType == java.sql.Types.BINARY || sqlType == java.sql.Types.VARBINARY
			|| sqlType == java.sql.Types.LONGVARBINARY || sqlType == java.sql.Types.NULL
			|| sqlType == java.sql.Types.OTHER || sqlType == java.sql.Types.JAVA_OBJECT
			|| sqlType == java.sql.Types.DISTINCT || sqlType == java.sql.Types.STRUCT
			|| sqlType == java.sql.Types.ARRAY || sqlType == java.sql.Types.BLOB
			|| sqlType == java.sql.Types.REF
			|| sqlType == java.sql.Types.DATALINK
			|| sqlType == java.sql.Types.BOOLEAN
			|| sqlType == java.sql.Types.ROWID || sqlType == java.sql.Types.NCHAR
			|| sqlType == java.sql.Types.NVARCHAR || sqlType == java.sql.Types.LONGNVARCHAR
			|| sqlType == java.sql.Types.NCLOB || sqlType == java.sql.Types.SQLXML) {
		   
	   */
	   else 
	   return null;
   }
}
