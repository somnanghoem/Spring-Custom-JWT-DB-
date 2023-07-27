package com.security.springsecurity.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	public final static String YEAR 					= "yyyy";
	public final static String MONTH 					= "MM";
	public final static String DAY 						= "dd";
	public final static String DATE 					= "yyyyMMdd";
	public final static String DATETIME 				= "yyyyMMddHHmmss";
	public final static String FULL_DATETIME 			= "yyyyMMddHHmmssSSS";
	public final static String TIME 					= "HHmmss";
	public final static String TIMESTAMP 				= "HHmmssSSS";
	public final static String HOUR 					= "HH";
	public final static String MINUTE 					= "mm";
	public final static String SECOND 					= "ss";
	public final static String MILLISECOND 				= "SSS";
	public final static String FILE_LOG_TYPE1 			= "yyyyMMddaa";
	public final static String FILE_LOG_TYPE2 			= "yyyyMMddHH";
	public final static String TIMESTAMP_TYPE1 			= "yyyyMMddHHmm";
	// Format
	public final static String FORMAT_DATE 				= "yyyy-MM-dd";
	public final static String FORMAT_DATETIME 			= "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_FULL_DATETIME 	= "yyyy-MM-dd HH:mm:ssSSS";
	public final static String FORMAT_TIME	 			= "HH:mm:ss";
	public final static String FORMAT_TIMESTAMP 		= "HH:mm:ssSSS";
	
	
	/**
	 * @param format
	 * @return
	 */
	public static String getCurrentFormatDate( String format ) {
		SimpleDateFormat sdf = new SimpleDateFormat( format, new Locale( "en", "US" ) );
		return sdf.format( Calendar.getInstance().getTime() );
	}
	
	/**
	 * @param date
	 * @param beforeFormatString
	 * @param afterFormatString
	 * @return
	 * @throws Exception
	 */
	public static String changeDateFormat( String date, String beforeFormatString, String afterFormatString ) throws Exception {
		try {
			SimpleDateFormat beforeFormat = new SimpleDateFormat( beforeFormatString );
			SimpleDateFormat afterFormat = new SimpleDateFormat( afterFormatString, new Locale( "en", "US" ) );
			return changeDateFormat( date, beforeFormat, afterFormat );
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * @param date
	 * @param beforeFormat
	 * @param afterFormat
	 * @return
	 * @throws Exception
	 */
	public static String changeDateFormat( String date, SimpleDateFormat beforeFormat, SimpleDateFormat afterFormat ) throws Exception {
		Date tempDate;
		String changedDate = date;
		try {
			tempDate = beforeFormat.parse( date );
			changedDate = afterFormat.format( tempDate );
		} catch ( ParseException e ) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return changedDate;
	}

}
