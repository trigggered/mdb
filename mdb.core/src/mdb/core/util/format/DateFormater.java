/*
 * Created on 07.02.2005 
 */
package mdb.core.util.format;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhuk
 *
 */
public class DateFormater {
	private String _datFrmDataPacket 	= "yyyyMMdd";
	private String _datFrmOracle 			= "dd.MM.yyyy";
	private String _datFrmWebRowSet 		= "yyyy-MM-dd";
	
	private Format _formatter;

	private String Format (String aFormat, Object aValue) {
		_formatter = new SimpleDateFormat(aFormat);
		return _formatter.format(aValue);
		
	}
	
	public  String UtcDate2DatapacketDate (String  sUTCDate) {
	  	 
	    if (sUTCDate == null || sUTCDate.length() ==0)
	       return "";
	    else {	    		
	     return Format(_datFrmWebRowSet, new Date(new Long(sUTCDate).longValue()));
	    }
	 }

	  
	public String datOracle2BorlanDataPacketFormat (String aDat) throws ParseException {
	  	if (aDat == null || aDat.length() == 0 ) return aDat;
		SimpleDateFormat frm = new SimpleDateFormat(_datFrmOracle);			
		
		return  Format (_datFrmDataPacket , frm.parse(aDat));		
	}
	  
	public String datDataPacket2OracleFormat (String aDat) throws ParseException {
	  	if (aDat == null || aDat.length() == 0 ) return aDat;
		SimpleDateFormat frm = new SimpleDateFormat(_datFrmDataPacket);			
		
		return  Format (_datFrmOracle, frm.parse(aDat));		
	}
}
