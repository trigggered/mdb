/**
 * 
 */
package mdb.core.shared.data;

/**
 * @author azhuk
 * Creation date: Feb 13, 2013
 *
 */

public enum EMdbEntityActionType {
	UNKNOWN (-1),
	SELECT (1),		
	INSERT(2),
	UPDATE (3),
	DELETE (4),
	REFRESH (5),
	CALL_SQL(6),
	STORED_PROC (7),
	PREPERE_FILTER (8),
	FILTER_CONDITIONS(9);	

    private int value;    

   private EMdbEntityActionType (int value) {
	    this.value = value;
   }

   public int getValue() {
	    return value;
   }	
   
   public static EMdbEntityActionType fromInt(int value) {
	   switch (value) {
	   case 1 : return SELECT;
	   case 2 : return INSERT;
	   case 3 : return UPDATE;
	   case 4 : return DELETE;
	   case 5 : return REFRESH;
	   case 6 : return CALL_SQL;
	   case 7 : return STORED_PROC;
	   case 8 : return PREPERE_FILTER;
	   case 9 : return FILTER_CONDITIONS;
	   
	   default:
		   return UNKNOWN;
	   }
   }
}
