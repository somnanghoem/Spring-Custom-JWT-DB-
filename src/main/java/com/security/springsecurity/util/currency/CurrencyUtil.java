package com.security.springsecurity.util.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

public class CurrencyUtil {

	
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
}
