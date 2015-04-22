package mdb.core.ui.client.view.utils;

import java.util.Calendar;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class DateTimeHelper {

	final static String DEFAULT_FORMAT = "dd.MM.yyyy";
			
	static DateTimeFormat _defaultDateFormater;
	
	
	private static DateTimeFormat getDateFormater() {
		if (_defaultDateFormater==null) {
			_defaultDateFormater = DateTimeFormat.getFormat(DEFAULT_FORMAT);
		}
		return _defaultDateFormater;
	}
	
	public static String format(Date date) {
		return getDateFormater().format(date, TimeZone.createTimeZone(0));		
	}
	
	
	public  static Date getSysdate() {
		return new Date();
	}

	public static String getSysdateAsString() {
		return format(getSysdate());
	}
	
	public static Date getFirstDayOfMonth(Date value) {
		CalendarUtil.setToFirstDayOfMonth(value);		
		return value;
	}
	
	
	public static Date addMonths(Date value, int months) {
		CalendarUtil.addMonthsToDate(value, months);
		return value;
	}
	
	public static Date getLastDayOfMonth(Date value) {
		CalendarUtil.addMonthsToDate(value, 1);
		CalendarUtil.setToFirstDayOfMonth(value);
		CalendarUtil.addDaysToDate(value, -1);
		return value;
	}
	
	public static Date getFirstDayOfYear() {
		DateTimeFormat fmt = DateTimeFormat.getFormat(PredefinedFormat.YEAR);	
		String year = fmt.format(new Date());

		return getDateFormater().parse("01.01."+year);
	}

	public static Date getLasttDayOfYear() {
		DateTimeFormat fmt = DateTimeFormat.getFormat(PredefinedFormat.YEAR);	
		String year = fmt.format(new Date());

		return getDateFormater().parse("31.12."+year);
	}

	
}
