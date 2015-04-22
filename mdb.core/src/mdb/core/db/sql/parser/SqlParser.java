package mdb.core.db.sql.parser;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mdb.core.shared.data.Param;

public class SqlParser {
	
	private static final Logger _logger = Logger.getLogger(SqlParser.class.getCanonicalName());

	private String _SQL;
	private final String c_ParamMatches = "(?<=:)[^=]\\w*";
	private final String c_FilterMatches = "/\\*FILTER\\*/";
	private final String c_IsCallSql = "\\{(?=[^\\{]*\\}).*?\\}";	
	private final String c_IsPlSql = "BEGIN";
	private final String COMMENTS_CODE =  "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)";
	
	//private Params _params;
	private HashMap <Integer, Param> _params = new HashMap<Integer, Param>();
	
	private boolean _isCallSQL = false;
	
	public SqlParser() {
	}

	
	public SqlParser(String aSQL) {		
		parser(aSQL);
	}
	
	
	public HashMap <Integer, Param> getParams () {
		return _params;
	}
	
	//    private String c_IsCallSql = "{";

//	private ArrayList<MatchItem> parserParameters() {
//
//		ArrayList<MatchItem> arlParams = null;
//		Pattern p = Pattern.compile(c_ParamMatches);
//		Matcher m = p.matcher(_SQL);
//		arlParams = new ArrayList<MatchItem>();
//
//		int nIndex = 0;
//		while (m.find()) {
//			nIndex++;
//			//arlParams.add(new MatchItem(m.group(), nIndex));
//			arlParams.add(new MatchItem(m.group(), nIndex));
//			
//		
//		}
//		_SQL = m.replaceAll("?");
//
//		p = Pattern.compile(":(?!=)");
//		m = p.matcher(_SQL);
//		_SQL = m.replaceAll("");
//
//		return arlParams;
//	}
	
	private void  parserParameters() {		
		_params.clear();
		
		Pattern p = Pattern.compile(c_ParamMatches);
		Matcher m = p.matcher(_SQL);
		

		int index = 0;
		while (m.find()) {
			index++;
			Param param = new Param();
			param.setName(m.group());
			param.setSqlIndex(index);
			_params.put( Integer.valueOf(index),  param);			
		
		}
		_SQL = m.replaceAll("?");

		p = Pattern.compile(":(?!=)");
		m = p.matcher(_SQL);
		_SQL = m.replaceAll("");		
	}
	
	 


	public String parser(String aSQL) {
		//_logger.log(Level.INFO, "SQL to parsed:" + aSQL);
		setSQL( aSQL);
		IsCallSQL(_SQL);
		parserParameters();		
		return _SQL;
		
	}

	public void setSQL(String value) {
		_SQL =value.replaceAll(COMMENTS_CODE, "");
		//_SQL = value;
	}

	public String getSQL() {
		return _SQL;
	}

//	public ArrayList<MatchItem> SQLFilter(String aSQL, String aFilter) {
//		Pattern p = Pattern.compile(c_FilterMatches);
//		Matcher m = p.matcher(aSQL);
//
//		if (m.find()) {
//			aSQL = m.replaceAll("AND " + aFilter);
//		} else
//			aSQL += " AND " + aFilter;
//
//		return ParserParametersForJdbc(aSQL);
//	}
//	
	

	public String  setStrByTag(String aSQL, String aStr, String aTag) {
		String Tag = "/\\*"+aTag+"\\*/";
		
		Pattern p = Pattern.compile(Tag);
		Matcher m = p.matcher(aSQL);

		if (m.find()) 
			aSQL = m.replaceAll( aStr);
		return aSQL;
	}
	

	/**
	 * @param aSQL
	 * @param aFilter
	 * @return
	 */
	public String getSQLFiltered(String aSQL, String aFilter) {
		Pattern p = Pattern.compile(c_FilterMatches);
		Matcher m = p.matcher(aSQL);

		if (m.find()) {
			aSQL = m.replaceAll("AND " + aFilter);
		} else
			aSQL += " AND " + aFilter;
		return aSQL;
	}

	
	public void IsCallSQL(String aSQL) {
		Pattern p = Pattern.compile(c_IsCallSql);
		Pattern p2 = Pattern.compile(c_IsPlSql);		
		
		Matcher m = p.matcher(aSQL);
		Matcher m2 = p2.matcher(aSQL.toUpperCase());
		 
		_isCallSQL = (m.find() || m2.find());
	}		
	
	public String ReplaceParamSQL2Val (String aSQL, String aParamName,  String aValue) {
		String Result = null;
		try {
			String Value = "";	
			if (aValue!= null && aValue.length() > 0 ) 
				Value = aValue;		
			else 
				Value = "null"; 
					
				Result = aSQL.replaceAll(":"+aParamName+"(?=\\W)", Value);
		} catch (java.lang.IndexOutOfBoundsException e){
			_logger.severe(e.getMessage() +"\n"+aParamName+"="+aValue );			
		}
		return Result;
	}
	
	public boolean getIsCallSQL () {
		return _isCallSQL;	
	}
}