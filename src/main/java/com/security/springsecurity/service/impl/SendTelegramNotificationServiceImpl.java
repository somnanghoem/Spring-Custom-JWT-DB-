package com.security.springsecurity.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.security.springsecurity.service.SendTelegramNotificationService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.date.DateUtil;

@Service
public class SendTelegramNotificationServiceImpl implements SendTelegramNotificationService {

	private final static Logger logger = LoggerFactory.getLogger( SendTelegramNotificationServiceImpl.class );
	
	/**
	 * -- Send Telegram Message --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return outputData
	 * 		String		invoiceNo
	 * 		String		casherName
	 * 		BigDecimal	transactionAmount
	 * 		String		transactionCurrencyCode
	 * 		BigDecimal	cashAmount
	 * 		BigDecimal	returnAmount
	 * 		String		groupName
	 * 		String		token
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@Override
	public DataUtil sendTelegramMessage(DataUtil param ) throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			
			logger.debug(">>>>>>>>>> send Telegram notification start >>>>>>>>>>");
			String invoiceNo 		= param.getString("invoiceNo");
			String transactionDate  = DateUtil.getCurrentFormatDate( DateUtil.FORMAT_DATE );
			String time				= DateUtil.getCurrentFormatDate( DateUtil.TIME );
			String cashierName		= param.getString("casherName");
			
			BigDecimal transactionAmount = param.getBigDecimal( "transactionAmount" ) ;
			String transactionAmountFormat = "USD".equals( param.getString( "transactionCurrencyCode" ) )
					? new DecimalFormat( "###,##0.00" ).format( transactionAmount )
					: new DecimalFormat( "###,##0" ).format( transactionAmount );
					
			String cashAmount = "USD".equals( param.getString( "transactionCurrencyCode" ) )
					? new DecimalFormat( "###,##0.00" ).format( param.getBigDecimal("cashAmount") )
					: new DecimalFormat( "###,##0" ).format( param.getBigDecimal("cashAmount") );
			
			String returnAmount = "USD".equals( param.getString( "transactionCurrencyCode" ) )
					? new DecimalFormat( "###,##0.00" ).format( param.getBigDecimal("returnAmount") )
					: new DecimalFormat( "###,##0" ).format( param.getBigDecimal("returnAmount") );
					
			String message = String.format(   "Order Completed\nInvoice No : %s\n"
											+ "Amount: %s%s\n"
											+ "Cash Amount: %s "
											+ "Return Amount: %s\n"
											+ "Transaction Date: %s  "
											+ "time: %s\n"
											+ "Order by: %s"
											, invoiceNo
											, transactionAmountFormat
											, param.getString( "transactionCurrencyCode" )
											, cashAmount
											, returnAmount
											, transactionDate 
											, time
											, cashierName );
			
			StringBuilder URL = new StringBuilder();
			URL.append( "https://api.telegram.org/bot" );
			URL.append( param.getString("token") );
			URL.append( "/sendMessage" );
			String telegramURL = URL.toString();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			DataUtil body = new DataUtil();
			body.setString("chat_id", param.getString("groupName") );
			body.setString("text", 	message );
			
			/*=================================
			 * Request send message to telegram
			 *=================================*/
			HttpEntity<DataUtil> entity = new HttpEntity<DataUtil>(body,headers);
			restTemplate.exchange( telegramURL , HttpMethod.POST, entity, String.class).getBody();
			logger.debug(">>>>>>>>>> send Telegram notification end >>>>>>>>>>");
			
			return null;
			
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>> send telegram notification error >>>>>>>>> " + param );
			logger.error(">>>>>>>>>> send telegram notification exception >>>>>>>>> " + ExceptionUtils.getStackTrace(e));
			throw e;
		}
		
	}

}
