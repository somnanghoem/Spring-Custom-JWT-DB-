/*-----------------------------------------------------------------------------------------
 * NAME : FileManagementController.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *                      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE        AUTHOR         DESCRIPTION                        
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-26   Hoem Somnang          creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.controller.api.v1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.file.FileReaderUtil;
import com.security.springsecurity.util.file.FileUtil;
import com.security.springsecurity.util.request.RequestData;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.YnTypeCode;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName FileManagementController
* @version   0.1, 2023-07-26
*/

@RestController
@RequestMapping("/api/v1/file")
public class FileManagementController {

	private final static Logger logger = LoggerFactory.getLogger( FileManagementController.class );
	private final static String TXT_EXTENSION = "txt";
	private final static String TXT_FOLDER = "text";
	private final static String TXT_FILE_NAME = "file";
	private final static String TXT_READ = "read";
	private final static String TXT_BACKUP = "backup";
	private final static String TXT_UPLOAD = "upload";
	/**
	 * -- Read Text File --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/read-text")
	public ResponseData<DataUtil> readTextFile( RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			logger.error(">>>>>>>>>>> read text file start >>>>>>>>>>");
			/*==============================
			 * 		Read File From Folder
			 *==============================*/
			String path = FileUtil.getPath(  TXT_FOLDER , TXT_FILE_NAME, TXT_READ);
			List<File> files = FileReaderUtil.listFilesWithExtension( path, TXT_EXTENSION );
			if( files.size() > 0 ) {
				for ( File file: files ) {
					BufferedReader read = new BufferedReader( new FileReader( file ) );
					String line = "";
					while( ( line = read.readLine() ) != null ) {
						body.setString( line , line );
					}
					read.close();
					String backupPath = FileUtil.makePath( TXT_FOLDER , TXT_FILE_NAME, TXT_BACKUP);
					// source path
					StringBuilder srcPath = new StringBuilder();
					srcPath.append( path );
					srcPath.append( File.separator );
					srcPath.append( file.getName() );
					String sourceFile = srcPath.toString();
					// destination path
					StringBuilder desPath = new StringBuilder();
					desPath.append( backupPath );
					desPath.append( File.separator );
					desPath.append( "backup_" );
					desPath.append( DateUtil.getCurrentFormatDate( DateUtil.DATETIME ) );
					desPath.append( "_" );
					desPath.append( file.getName() );
					String destinationFile = desPath.toString();
					/*===========================
					 * Move File To Backup folder
					 *===========================*/
					FileReaderUtil.moveToBackupManually(sourceFile, destinationFile);
				}
			}
			
		} catch ( Exception e) {
			logger.error(">>>>>>>>>>> read text file error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			body = new DataUtil();
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.error(">>>>>>>>>>> read text file end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}
	
	
	/**
	 * -- Upload Text File --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/upload-text-file")
	public ResponseData<DataUtil> uploadTextFile( RequestData<DataUtil> param ) throws Exception, IOException {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			logger.error(">>>>>>>>>>> upload text file start >>>>>>>>>>");
			String uploadPath = FileUtil.makePath( TXT_FOLDER , TXT_FILE_NAME, TXT_UPLOAD);
			StringBuilder path = new StringBuilder();
			path.append( uploadPath );
			path.append( File.separator );
			path.append( DateUtil.getCurrentFormatDate( DateUtil.DATETIME ) );
			path.append("_");
			path.append("upload.");
			path.append( TXT_EXTENSION );
			String uploadFile = path.toString();
			// Write File
			String text = "To create a file in a specific directory (requires permission), specify the path of the";
			String text1 = "aa";
			BufferedWriter writer = new BufferedWriter( new FileWriter( uploadFile ) ) ;
			writer.write( text );
			writer.newLine();
			writer.write( text1 );
			writer.close();
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>>> upload text file error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			body = new DataUtil();
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.error(">>>>>>>>>>> upload text file end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
		
	}
	
}
