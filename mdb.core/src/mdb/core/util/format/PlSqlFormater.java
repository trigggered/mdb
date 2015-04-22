/*
 * Created on 21.01.2005
 * 
 */
package mdb.core.util.format;

/**
 * @author Zhuk
 * 
 */
public class PlSqlFormater {
	
	private static final String _TokenCR = "\n";	
	private static final String CONCAT = "||";
	private static final String QUOTE = "'";
	private static final String CHR = "chr(10)";

	
	public static String format (String aValue) {
		if (aValue != null && aValue.length() > 0) {
			StringBuffer sb = new StringBuffer(aValue);		
			sb = PLSQLFormatQuote( sb);		
			return QUOTE+PLSQLFormatCR(sb).toString()+QUOTE;
		} else return null;
	}

	/**
	 * @param aValue
	 * @param sb
	 */
	private static StringBuffer PLSQLFormatQuote (StringBuffer aValue) {
		int fromIndex = aValue.indexOf(QUOTE);
		while ( fromIndex > 0 ) {
			aValue.insert(fromIndex, QUOTE);		
			fromIndex = aValue.indexOf(QUOTE, fromIndex+1+QUOTE.length());
		}
		return aValue;
	}

	/**
	 * @param sb
	 * @return
	 */
	private static  StringBuffer PLSQLFormatCR(StringBuffer aValue) {
		int fromIndex = aValue.indexOf(_TokenCR);
		while ( fromIndex > 0 ) {
			aValue.insert(fromIndex, QUOTE+CONCAT+CHR+CONCAT);
			aValue.insert(fromIndex+ QUOTE.length()+CONCAT.length()*2+CHR.length()+1, 
					 QUOTE);
			fromIndex = aValue.indexOf(_TokenCR
									   ,fromIndex+CHR.length()+QUOTE.length()*3+1
									   +CONCAT.length());
		}		 
		return aValue;
	}

	
}
