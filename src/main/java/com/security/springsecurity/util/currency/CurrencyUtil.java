package com.security.springsecurity.util.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

public class CurrencyUtil {

	private static final String ZERO_STR = "Zero ";
	private static final String[] SMALL_DIGITS = { "", "One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ",
			"Eight ", "Nine ", "Ten ", "Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ",
			"Seventeen ", "Eighteen ", "Nineteen " };
	private static final String[] TENTH_DIGITS = { "", "Ten ", "Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ",
			"Seventy ", "Eighty ", "Ninety " };
	private static final String HUNDRED_STR = "Hundred ";
	private static final String[] LARGE_DIGITS = { "", "Thousand ", "Million ", "Billion ", "Trillion ", "Quadrillion ",
			"Quintillion ", "Sextillion ", "Septillion " };
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param number
	 * @param scale
	 * @param roundingMode
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static BigDecimal changeScaleRounding( BigDecimal number, int scale, int roundingMode ) {
		return number.setScale(scale, roundingMode);
	}

	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param amount
	 * @param currencyCode
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static String convertAmountFormat( BigDecimal amount, String currencyCode ) {
		String toBeFormat = StringUtils.EMPTY;
		DecimalFormat df = new DecimalFormat();
		if ( "KHR".equals( currencyCode ) ) {
			amount = amount.setScale( 0, RoundingMode.DOWN );
			df = new DecimalFormat("#,##0");
		} else {
			amount = amount.setScale( 2, RoundingMode.DOWN );
			df = new DecimalFormat("#,##0.00");
		}
		toBeFormat = df.format( amount );
		return toBeFormat;
	}
	
	/**
	 * <pre>
	 *  number to English 
	 * </pre>
	 * 
	 * @param amount
	 * @param currency
	 * @return
	 */
	public static String convertNumberToWord( BigDecimal amount, String currency ) {
		String toEng = "";
		
		String integerStr = "";
		String decimalStr = "";
		String currencyStr = "";
		String smallCurrencyStr = "";
		int scale = 0;
		String formatString = "";
		
		switch ( currency ) {
			case "USD" :
				scale = 2;
				formatString = "#,##0.00";
				currencyStr = "US Dollar";
				smallCurrencyStr = "Cent";
				break;
			case "KHR" :
				scale = 0;
				formatString = "#,##0";
				currencyStr = "KH Riel";
				break;
			case "EUR" :
				scale = 2;
				formatString = "#,##0.00";
				currencyStr = "Euro";
				smallCurrencyStr = "Cent";
			case "KRW" :
				scale = 0;
				formatString = "#,##0";
				currencyStr = "KR Won";
			case "THB" :
				scale = 2;
				formatString = "#,##0.00";
				currencyStr = "Thai Baht";
				smallCurrencyStr = "Satang";
			case "CNY" :
				scale = 0;
				formatString = "#,##0";
				currencyStr = "CN Yuan";
				//smallCurrencyStr = "Jiao";		1/10
				//smallCurrencyStr = "Fen";			1/100
				break;
			case "JPY" :
				scale = 0;
				formatString = "#,##0";
				currencyStr = "JP Yen";
				break;
			case "SGD" :
				scale = 2;
				formatString = "#,##0.00";
				currencyStr = "Singapore Dollar";
				smallCurrencyStr = "Cent";
				break;
			case "VND" :
				scale = 0;
				formatString = "#,##0";
				currencyStr = "Vietnam Dong";
				//smallCurrencyStr = "Hao";		1/10
				break;
			default :
				scale = 2;
				formatString = "#,##0.00";
				currencyStr = "US Dollar";
		}
		
		if ( amount.compareTo( BigDecimal.ZERO ) == 0 ) {
			toEng = ZERO_STR;
		} else {
			amount = amount.setScale( scale, BigDecimal.ROUND_FLOOR );
			
			DecimalFormat df = new DecimalFormat( formatString );
			String amountNumber = df.format( amount );

			String ammountSplit[] = amountNumber.split( "\\." );
			if ( ammountSplit.length == 2 ) {
				integerStr = ammountSplit[0];
				decimalStr = ammountSplit[1];
			} else {
				integerStr = amountNumber;
			}
		}
		
		if ( StringUtils.isNotEmpty( integerStr ) ) {
			String tempSplit[] = integerStr.split( "," );
			for ( int i = tempSplit.length; i > 0 ; i-- ) {
				int largeNum = Integer.valueOf( tempSplit[i-1] );
				if ( largeNum > 0 ) {
					toEng = convertThreeDigitsToEng( largeNum ) + LARGE_DIGITS[tempSplit.length-i] + toEng;
				}
			}
		}
		toEng = toEng + currencyStr + ( amount.compareTo( BigDecimal.ONE ) > 0 ? "s" : "" );
		
		if ( StringUtils.isNotEmpty( decimalStr ) ) {
			int smallNum = Integer.valueOf( decimalStr );
			if ( smallNum > 0 && smallNum < 1000 ) {
				toEng = toEng + " And " + convertThreeDigitsToEng( smallNum ) + smallCurrencyStr + ( smallNum > 1 ? "s" : "" );
			}
		}
		
		return toEng;
	}
	
	private static String convertThreeDigitsToEng( int number ) {
		String replaceStr;
		if ( number % 100 < 20 ) {
			replaceStr = SMALL_DIGITS[number % 100];
			number /= 100;
		} else {
			replaceStr = SMALL_DIGITS[number % 10];
			number /= 10;
			replaceStr = TENTH_DIGITS[number % 10] + replaceStr;
			number /= 10;
		}
		if ( number > 0 ) {
			replaceStr = SMALL_DIGITS[number] + HUNDRED_STR + replaceStr;
		}
		return replaceStr;
	}
}
