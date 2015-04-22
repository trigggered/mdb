package mdb.core.db.sql;

public class SQLTemplates {
  public final static String QUERY_METHOD =
                 "SELECT "+
                  "ID_METHODS_DENTITY "+
                  ", ID_DENTITY "+
                  ", NAME_ACTION "+
                  ", METHOD_BODY "+
				  ", ID_DENTITY_ACTION"+
				  ", IS_ARCHIVE "+
				  ", ID_EXT_METHOD "+
        "FROM MDB.VIEW_METHODS_FOR_DENTITY "+
        "WHERE ID_DENTITY =:ID_DENTITY";

  public final static String m_AllDEntityMethod =
      "SELECT ID_DENTITY ,"+
       "NAME_DENTITY,"+
       "NAME_LANG, "+
       "NAME_ACTION, "+
       "METHOD_BODY "+
       "FROM MDB.VIEW_METHODS_FOR_DENTITY";

  /*
   public final static String  QUERY_KEYS =
       "SELECT SEQ_NAME, KEY_FIELD  "+
       "FROM   MDB.VIEW_KEYS_DENTITY "+
       "WHERE ID_DENTITY =:ID_DENTITY";
   */

   public final static String QUERY_USER_RIGHT =
       "SELECT SELECT_DATA, UPDATE_DATA "+
       "FROM MDB.VIEW_USERS_DENTITY_RIGHTS "+
       "WHERE USRID=:USRID "+
       "AND ID_DENTITY=:ID_DENTITY";



   public static String getSeqSql (String aSeqName) {
     return "SELECT "+aSeqName+".NEXTVAL FROM DUAL";
   }
   
   public static String m_LogArhive = "INSERT INTO MDB.MDB_HISTORY" +
   		"(ID_HISTORY, ID_DENTITY, ID_DENTITY_ACTION, SCRIPT, USRID)" +
   		//"VALUES (:ID_HISTORY, :ID_DENTITY, :ID_DENTITY_ACTION, :DTA, :SCRIPT,:USRID, :STATUS)";
		"VALUES (MDB.ID_HISTORY_SEQ.NEXTVAL, ?, ?, ?, ? )";
   
   
   public static String getExtMethodSql () {
   	 return "SELECT  ID_EXT_METHOD, METHOD_BODY, ID_EXT_METHODS_LANG,  NOTE " +
   	 		"FROM MDB.MDB_EXT_METHODS " +
   	 		"WHERE ID_EXT_METHOD=?";
   }
            
   
   public static String getSelectTmp () {
	   return "SELECT * FROM ";
   }

}
