/*-----------------------------------------------------------------------------------------
 * NAME : SendTelegramNotificationScheduler.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-28   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.schedule;

import java.math.BigDecimal;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.security.springsecurity.service.SendTelegramNotificationService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.type.CurrencyCodeType;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName SendTelegramNotificationScheduler
* @version   0.1, 2023-07-28
*/

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class SendTelegramNotificationScheduler {
	
	@Autowired
	private SendTelegramNotificationService  sendTelegramNotificationService;
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	//	Example
	//	"0 0 * * * *"                 the top of every hour of every day.
	//	"*/10 * * * * *"              every ten seconds.
	//	"0 0 8-10 * * *"              8, 9 and 10 o'clock of every day.
	//	"0 0/30 8-10 * * *"           8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
	//	"0 0 9-17 * * MON-FRI"        on the hour nine-to-five weekdays
	//	"0 0 0 25 12 ?"               every Christmas Day at midnight
	// second, minute, hour, day of month, month, day(s) of week
	@Scheduled( cron ="*/10 * * * * *")
	public void sendTelegramMessage() throws Exception {
		
		try {
			System.out.println(">>>>>>>>> send telegram message start >>>>>>>>>" );
			DataUtil param = new DataUtil();
			param.setString("invoiceNo", "#NO_001");
			param.setString("casherName", "lyly");
			param.setBigDecimal("transactionAmount", BigDecimal.valueOf( 75 ));
			param.setString("transactionCurrencyCode", CurrencyCodeType.USD.getValue() );
			param.setBigDecimal("cashAmount", BigDecimal.valueOf( 100 ));
			param.setBigDecimal("returnAmount", BigDecimal.valueOf( 25 ) );
			param.setString("groupName", "@sn_testing" );
			param.setString("token", "5791659744:AAH-sM4_RUYgeBWbUNtBOXiWHG9lUmvbqF0");
			//sendTelegramNotificationService.sendTelegramMessage(param);
			System.out.println(">>>>>>>>> send telegram message end >>>>>>>>>" );
		} catch ( Exception e) {
			throw e;
		}
		
	}

}
