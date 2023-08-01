/*-----------------------------------------------------------------------------------------
 * NAME : GenerateSequenceManagementServiceImpl.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-31   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import com.security.springsecurity.dao.SequenceNoDetail;
import com.security.springsecurity.dao.SequenceNoMaster;
import com.security.springsecurity.service.GenerateSequenceManagementService;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.SequenceNoStatusCode;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName GenerateSequenceManagementServiceImpl
* @version   0.1, 2023-07-31
*/
@Service
public class GenerateSequenceManagementServiceImpl implements GenerateSequenceManagementService {

	@Autowired
	private SequenceNoMaster sequenceNoMaster;
	@Autowired
	private SequenceNoDetail sequenceNoDetail;
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	private final static Logger logger = LoggerFactory.getLogger( GenerateSequenceManagementServiceImpl.class );
	
	/**
	 * -- Generate Commit SeqNo --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 *   	String		seqNoCode
	 * 		String		seqNoUniqueCode
	 * 		String		seqNoName
	 * 		String		userName
	 * 		Long		seqNoDigit
	 * 		Long		seqNoStart
	 * 		Long		seqNoEnd
	 * @throws Exception
	 * @return outputData
	 *		String	seqNoUniqueCode
	 * @exception 
	 * @fullPath 
	 */
	@Override
	public String generateCommitSeqNo(DataUtil param) throws Exception {
		
		// Open New Transaction
		TransactionStatus transactionStatus = platformTransactionManager.getTransaction( new DefaultTransactionAttribute( TransactionDefinition.PROPAGATION_REQUIRES_NEW ) );
		try {
			
			logger.debug(">>>>>>>>>> generate commit seqNo start >>>>>>>>>>" );
			// Validate data
			validateRequestData(param);
			// Process Register SeqNo Master( SEQUENCE_NO ) if not exist
			registerMasterSeqNo(param);
			
			Long bdLastNo			= 0L;
			Long bdSeqNoStart 		= 0L;
			Long bdSeqNoEnd			= 0L;
			String seqNoUniqueCode 	= StringUtils.EMPTY;
			
			DataUtil sequenceMasterInfo = sequenceNoMaster.retrieveSequenceMasterInfo(param);
			DataUtil sequenceDetailInfo = sequenceNoDetail.retrieveSequenceNoDetailInfo(param);
			if ( sequenceDetailInfo !=  null ) {
				bdSeqNoStart = sequenceMasterInfo.getLong( "seqNoStart" );
				bdSeqNoEnd = sequenceDetailInfo.getLong( "seqNoLast" );
				if( bdSeqNoStart > bdSeqNoEnd ) {
					bdLastNo = bdSeqNoStart;
				} else {
					bdLastNo = bdSeqNoEnd + 1L ; 
				}
			} else {
				bdLastNo = sequenceMasterInfo.getLong( "seqNoStart" );
			}
			// check Digit
			int iDigit = sequenceMasterInfo.getInt( "seqNoDigit" );
			// padding
			seqNoUniqueCode = StringUtils.leftPad( String.valueOf( bdLastNo ) + "", iDigit, "0" );
			
			DataUtil sequenceDetailParam = new DataUtil() ;
			//Update
			if ( sequenceDetailInfo != null ) {
				sequenceDetailParam.setString("seqNoCode", param.getString("seqNoCode"));
				sequenceDetailParam.setString("seqNoUniqueCode", param.getString("seqNoUniqueCode"));
				sequenceDetailParam.setLong("seqNoLast", Long.valueOf( seqNoUniqueCode ).longValue() );
				sequenceDetailParam.setString("updateBy", param.getString("userName"));
				sequenceDetailParam.setString("updateDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
				sequenceDetailParam.setString("updateTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
				sequenceNoDetail.updateSequenceNoDetailInfo(sequenceDetailParam);
			// Register
			} else {
				sequenceDetailParam.setString("seqNoCode", param.getString("seqNoCode"));
				sequenceDetailParam.setString("seqNoUniqueCode", param.getString("seqNoUniqueCode"));
				sequenceDetailParam.setLong("seqNoLast", Long.valueOf( seqNoUniqueCode ).longValue() );
				sequenceDetailParam.setString("createBy", param.getString("userName"));
				sequenceDetailParam.setString("updateBy", param.getString("userName"));
				sequenceDetailParam.setString("registerDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
				sequenceDetailParam.setString("registerTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
				sequenceDetailParam.setString("updateDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
				sequenceDetailParam.setString("updateTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
				sequenceNoDetail.regsiterSequenceNoDetailInfo(sequenceDetailParam);
			}
			logger.debug(">>>>>>>>>> generate commit seqNo end >>>>>>>>>>" );
			platformTransactionManager.commit(transactionStatus);
			return seqNoUniqueCode;
		} catch ( Exception e ) {
			platformTransactionManager.rollback(transactionStatus);
			logger.debug(">>>>>>>>>> generate commit seqNo error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e));
			throw e;
		}
	}
	
	private void validateRequestData( DataUtil param ) throws Exception {
		
		if ( StringUtils.isEmpty( param.getString("seqNoCode") ) || StringUtils.isBlank( param.getString("seqNoCode") ) ) {
			throw new Exception( ResponseMessageTypeCode.SEQUENCE_CODE_EMPTY.getResultCode() );
		} else if ( StringUtils.isEmpty( param.getString("seqNoUniqueCode") ) || StringUtils.isBlank( param.getString("seqNoUniqueCode") ) ) {
			throw new Exception( ResponseMessageTypeCode.SEQUENCE_UNIQUE_CODE_EMPTY.getResultCode() );
		} 
		
	}
	
	private void registerMasterSeqNo( DataUtil param ) throws Exception {
		
		try {
			DataUtil sequenceNoMasterInfo = sequenceNoMaster.retrieveSequenceMasterInfo(param);
			if ( sequenceNoMasterInfo == null ) {
				if ( StringUtils.isEmpty( param.getString("seqNoName") ) || StringUtils.isBlank( param.getString("seqNoName") ) ) {
					throw new Exception( ResponseMessageTypeCode.SEQUENCE_CODE_NAME_EMPTY.getResultCode() );
				} else if  ( StringUtils.isEmpty( param.getString("userName") ) || StringUtils.isBlank( param.getString("userName") ) ) {
					throw new Exception( ResponseMessageTypeCode.USERNAME_EMPTY.getResultCode() );
				} 
				// Data Register
				DataUtil registerMasterParam = new DataUtil();
				registerMasterParam.setString("seqNoCode", param.getString("seqNoCode") );
				registerMasterParam.setString("seqNoStatus", SequenceNoStatusCode.NORMAL.getValue() );
				registerMasterParam.setString("seqNoName", param.getString("seqNoName") );
				if ( StringUtils.isEmpty( param.getString("seqNoDigit")) || StringUtils.isBlank(param.getString("seqNoDigit")) ) {
					registerMasterParam.setLong("seqNoDigit", 8L );
				} else {
					registerMasterParam.setLong("seqNoDigit", param.getLong("seqNoDigit") );
				}
				if ( StringUtils.isEmpty( param.getString("seqNoStart")) || StringUtils.isBlank(param.getString("seqNoStart")) ) {
					registerMasterParam.setLong("seqNoStart", 1L );
				} else {
					registerMasterParam.setLong("seqNoStart", param.getLong("seqNoStart") );
				}
				if ( StringUtils.isEmpty( param.getString("seqNoEnd")) || StringUtils.isBlank(param.getString("seqNoEnd")) ) {
					registerMasterParam.setLong("seqNoEnd", 99999999L );
				} else {
					registerMasterParam.setLong("seqNoEnd", param.getLong("seqNoEnd") );
				}
				registerMasterParam.setString("createBy", param.getString("userName"));
				registerMasterParam.setString("updateBy", param.getString("userName"));
				registerMasterParam.setString("registerDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
				registerMasterParam.setString("registerTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
				registerMasterParam.setString("updateDate", DateUtil.getCurrentFormatDate( DateUtil.DATE ));
				registerMasterParam.setString("updateTime", DateUtil.getCurrentFormatDate( DateUtil.TIME ));
				// Register SEQUENCE_NO
				sequenceNoMaster.regsiterSequenceMasterInfo( registerMasterParam );
			}
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>>> register Master SeqNo error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			throw e;
		}
		
	}

}
