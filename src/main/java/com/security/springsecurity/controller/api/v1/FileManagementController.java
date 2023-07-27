/*-----------------------------------------------------------------------------------------
 * NAME : FileManagementController.java
 * VER  : v0.1
 * PROJ : spring-security
 * Copyright 2023
 *-----------------------------------------------------------------------------------------
 *				      H      I      S      T      O      R      Y
 *-----------------------------------------------------------------------------------------
 *   DATE		AUTHOR		 DESCRIPTION						
 * ----------  --------------  ------------------------------------------------------------
 * 2023-07-26   Hoem Somnang		  creation
 *---------------------------------------------------------------------------------------*/
package com.security.springsecurity.controller.api.v1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.springsecurity.util.currency.CurrencyUtil;
import com.security.springsecurity.util.data.DataUtil;
import com.security.springsecurity.util.data.ListDataUtil;
import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.file.FileReaderUtil;
import com.security.springsecurity.util.file.FileUtil;
import com.security.springsecurity.util.request.RequestData;
import com.security.springsecurity.util.response.ResponseData;
import com.security.springsecurity.util.response.ResponseHeader;
import com.security.springsecurity.util.resultmessage.ResponseMessageTypeCode;
import com.security.springsecurity.util.type.CurrencyCodeType;
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
	// Text File
	private final static String TXT_READ		 = "read";
	private final static String TXT_UPLOAD		 = "upload";
	private final static String TXT_FOLDER		 = "text";
	private final static String TXT_BACKUP		 = "backup";
	private final static String TXT_EXTENSION	 = "txt";
	private final static String TXT_FILE_NAME	 = "file";
	// Excel File
	private final static String EXCEL_READ		 = "read";
	private final static String EXCEL_UPLOAD	 = "upload";
	private final static String EXCEL_FOLDER	 = "excel";
	private final static String EXCEL_BACKUP	 = "backup";
	private final static String EXCEL_EXTENSION  = "xlsx";
	private final static String EXCEL_FILE_NAME  = "file";
	// JSON
	private final static String JSON_READ		 = "read";
	private final static String JSON_UPLOAD		 = "upload";
	private final static String JSON_FOLDER		 = "json";
	private final static String JSON_BACKUP		 = "backup";
	private final static String JSON_EXTENSION	 = "json";
	private final static String JSON_FILE_NAME	 = "file";
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
					FileReaderUtil.moveFile(sourceFile, destinationFile);
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
	
	
	/**
	 * -- Read Excel File --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/read-excel")
	public ResponseData<DataUtil> readExcelFile( RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			logger.error(">>>>>>>>>>> read excel file start >>>>>>>>>>");
			String path = FileUtil.getPath( EXCEL_FOLDER , EXCEL_FILE_NAME, EXCEL_READ);
			List<File> files = FileReaderUtil.listFilesWithExtension( path, EXCEL_EXTENSION );
			if( files.size() > 0 ) {
				for ( File file: files ) {
					ListDataUtil list = new ListDataUtil();
					Workbook workbook = new XSSFWorkbook( file );
					Sheet curSheet;
					Row curRow;
					Cell curCell;
					// Searching for sheet
					for ( int sheetIndex = 0;sheetIndex < workbook.getNumberOfSheets(); sheetIndex ++ ) {
						// Current Sheet
						curSheet = workbook.getSheetAt( sheetIndex );
						long rowCount = curSheet.getPhysicalNumberOfRows();
						// searching row
						for ( int rowIndex = 0; rowIndex < rowCount ; rowIndex++ ) { 
							// skip row 0 - header or title
							if ( rowIndex > 0 ) { 
								// current row
								curRow = curSheet.getRow( rowIndex );
								DataUtil data = new DataUtil();
								String emptyRowYN = YnTypeCode.NO.getValue();
								try {
									curRow.getPhysicalNumberOfCells();
								} catch ( Exception e ) {
									emptyRowYN = YnTypeCode.YES.getValue();
								}
								// Avoid Empty Row
								if ( YnTypeCode.NO.getValue().equals( emptyRowYN ) ) {
									// searching column
									for ( int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells() ; cellIndex++ ) { 
										curCell = curRow.getCell( cellIndex );
										if ( curCell != null ) {
											// Search Column By Index
											switch ( cellIndex ) {
												case 0: 
													String transactionDate = curCell.toString();
													if ( StringUtils.isNotBlank( transactionDate ) && StringUtils.isNotEmpty( transactionDate ) ) {
														data.setString("transactionDate", transactionDate );
													}
													break;
												case 1: 
													String transactionAmount = curCell.toString();
													if ( StringUtils.isNotBlank( transactionAmount ) &&  StringUtils.isNotEmpty(transactionAmount)  ) {
														if ( transactionAmount.contains( "," ) ) { 
															transactionAmount = StringUtils.replace( transactionAmount, ",", "" );  // 100,000.00
														} else if ( ( StringUtils.isEmpty( transactionAmount ) ) || ( StringUtils.isBlank( transactionAmount ) ) ) {
															transactionAmount = "0.0";
														}
														Double amount =  Double.parseDouble( transactionAmount );
														data.setBigDecimal( "transactionAmount", BigDecimal.valueOf( amount ) );
													}
													break;
												default :
													break;
											}
										}
										// Add Data to List
										list.add(data);
									}
								}
							}
						}
					}
					workbook.close();
					body.setListDataUtil("list", list );
					/*==================================
					 * Move Excel File To Backup Folder
					 *==================================*/
					String backupPath = FileUtil.makePath( EXCEL_FOLDER , EXCEL_FILE_NAME, EXCEL_BACKUP);
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
					FileReaderUtil.moveFile(sourceFile, destinationFile);
				}
			}
			
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>>> read excel file error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			body = new DataUtil();
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.error(">>>>>>>>>>> read excel file end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}
	
	
	/**
	 * -- Upload Excel File --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/upload-excel")
	public ResponseData<DataUtil> uploadExcelFile( RequestData<DataUtil> param ) throws Exception{
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			
			logger.error(">>>>>>>>>>> upload excel file start >>>>>>>>>>");
			String[] columns = { "transactionDate", "transactionAmount" };
			// Create a workbook
			Workbook workbook  = new XSSFWorkbook();
			// Create a sheet
			Sheet sheet = workbook.createSheet( "Sheet" );
			sheet.createFreezePane(0,1);
			// Header Font
			Font headerFont = workbook.createFont();
			headerFont.setFontName( "Times New Roman" );
			headerFont.setBold( true );
			headerFont.setFontHeightInPoints( (short) 12 );
			headerFont.setColor( IndexedColors.BLACK.getIndex() );
			// Header Style
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont( headerFont );
			headerCellStyle.setBorderTop( BorderStyle.THIN );
			headerCellStyle.setBottomBorderColor( (short)8 );
			headerCellStyle.setBorderBottom( BorderStyle.THIN );
			headerCellStyle.setLeftBorderColor( (short)8);
			headerCellStyle.setBorderLeft( BorderStyle.THIN );
			headerCellStyle.setRightBorderColor( (short)8);
			headerCellStyle.setBorderRight( BorderStyle.THIN );
			headerCellStyle.setAlignment( HorizontalAlignment.CENTER );
			// Create a Row
			Row headerRow = sheet.createRow( 0 );
			// Create cells
			for ( int i = 0; i < columns.length; i++ ) {
				Cell cell = headerRow.createCell( i );
				cell.setCellValue( columns[i] );
				cell.setCellStyle( headerCellStyle );
			}
			DataFormat format = workbook.createDataFormat();
			// Cell Font
			Font cellFont = workbook.createFont();
			cellFont.setFontName( "Times New Roman" );
			cellFont.setFontHeightInPoints( (short) 11 );
			cellFont.setColor( IndexedColors.BLACK.getIndex() );
			// Cell Text Style
			CellStyle cellTextStyle = workbook.createCellStyle();
			cellTextStyle.setFont( cellFont );
			cellTextStyle.setBorderTop( BorderStyle.THIN );
			cellTextStyle.setBottomBorderColor( (short)8 );
			cellTextStyle.setBorderBottom( BorderStyle.THIN );
			cellTextStyle.setLeftBorderColor( (short)8);
			cellTextStyle.setBorderLeft( BorderStyle.THIN );
			cellTextStyle.setRightBorderColor( (short)8);
			cellTextStyle.setBorderRight( BorderStyle.THIN );
			cellTextStyle.setAlignment( HorizontalAlignment.CENTER );
			cellTextStyle.setDataFormat( format.getFormat( "Text" ) );
			// Cell Text Style
			CellStyle cellTextLeftStyle = workbook.createCellStyle();
			cellTextLeftStyle.setFont( cellFont );
			cellTextLeftStyle.setBorderTop( BorderStyle.THIN );
			cellTextLeftStyle.setBottomBorderColor( (short)8 );
			cellTextLeftStyle.setBorderBottom( BorderStyle.THIN );
			cellTextLeftStyle.setLeftBorderColor( (short)8);
			cellTextLeftStyle.setBorderLeft( BorderStyle.THIN );
			cellTextLeftStyle.setRightBorderColor( (short)8);
			cellTextLeftStyle.setBorderRight( BorderStyle.THIN );
			cellTextLeftStyle.setAlignment( HorizontalAlignment.LEFT );
			cellTextLeftStyle.setDataFormat( format.getFormat( "Text" ) );
			// Add data to cell
			int rowNum = 1;
			for ( int cellCount = 0 ; cellCount < 10 ; cellCount ++ ) {
				Row row = sheet.createRow( rowNum++ );
				Cell transactionDate = row.createCell( 0 );
				// Transaction Date
				String formatTransactionDate =DateUtil.getCurrentFormatDate( DateUtil.DATE );
				transactionDate.setCellValue( formatTransactionDate );
				transactionDate.setCellStyle( cellTextStyle );
				// transactionAmount
				Cell transactionAmount = row.createCell( 1 );
				String amount = CurrencyUtil.convertAmountFormat( BigDecimal.valueOf(1000 ), CurrencyCodeType.USD.getValue() );
				transactionAmount.setCellValue( amount );
				transactionAmount.setCellStyle( cellTextLeftStyle );
			}
			// Resize all columns to fit the content size
			for ( int i = 0; i < columns.length; i++ ) {
				sheet.autoSizeColumn( i );
			}
			String filePath = FileReaderUtil.makePath( EXCEL_FOLDER, EXCEL_FILE_NAME, EXCEL_UPLOAD );
			String uploadFileName = DateUtil.getCurrentFormatDate( DateUtil.DATETIME ).concat( "_upload" );
			StringBuilder srcPath = new StringBuilder();
			srcPath.append( filePath );
			srcPath.append( File.separator );
			srcPath.append( uploadFileName );
			srcPath.append( "." );
			srcPath.append( EXCEL_EXTENSION );
			String uploadFile = srcPath.toString();
			File file = new File( uploadFile );
			//Create the file
			if (file.createNewFile()){
			} else {
			}
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream( file );
			workbook.write( fileOut );
			fileOut.close();
			// Closing the workbook
			workbook.close();

		} catch ( Exception e ) {
			logger.error(">>>>>>>>>>> upload excel file error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			body = new DataUtil();
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.error(">>>>>>>>>>> upload excel file end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
		
	}
	
	
	/**
	 * -- Read Json File --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/read-json")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseData<DataUtil> readJsonFile( RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {
			logger.error(">>>>>>>>>>> read json file start >>>>>>>>>>");
			String path = FileUtil.getPath( JSON_FOLDER , JSON_FILE_NAME, JSON_READ);
			List<File> files = FileReaderUtil.listFilesWithExtension( path, JSON_EXTENSION );
			if( files.size() > 0 ) {
				for ( File file: files ) {
					FileReader fileReader = new FileReader(file);
					Object obj = new JSONParser().parse( fileReader );
					// typecasting  obj to JSONObject
					JSONObject jObj = ( JSONObject ) obj;
					// Getting Value
					String firstName = (String) jObj.get("firstName");
					String lastName  = (String) jObj.get("lastName");
					long age = (long) jObj.get("age");
					Map<String, Object> address = ((Map)jObj.get("address") );
					JSONArray phoneNumbers = ( (JSONArray)jObj.get("phoneNumbers") );
					// Set OuputData
					body.setString("firstName", firstName);
					body.setString("lastName", lastName);
					body.setLong("age", age);
					body.set("address", address);
					body.set("phoneNumbers", phoneNumbers );
					/*==================================
					 * Move Excel File To Backup Folder
					 *==================================*/
					jObj.clear();
					fileReader.close();
					String backupPath = FileUtil.makePath( JSON_FOLDER , JSON_FILE_NAME, JSON_BACKUP);
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
					FileReaderUtil.moveFile(sourceFile, destinationFile);
				}
			}
			
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>>> read json file error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			body = new DataUtil();
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.error(">>>>>>>>>>> read json file end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}
	
	
	/**
	 * -- Upload Json File --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param param
	 * @return
	 * @throws Exception
	 * @exception 
	 * @fullPath 
	 */
	@PostMapping("/upload-json")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseData<DataUtil> uploadJsonFile( RequestData<DataUtil> param ) throws Exception {
		
		DataUtil body = new DataUtil();
		String successYN = YnTypeCode.YES.getValue();
		String resultCode = ResponseMessageTypeCode.SUCCESS.getResultCode();
		String resultMessage = ResponseMessageTypeCode.SUCCESS.getResultMessage();
		try {	
			logger.error(">>>>>>>>>>> upload json file start >>>>>>>>>>");
			// creating JSONObject
			JSONObject jo = new JSONObject();
			// putting data to JSONObject
			jo.put("firstName", "John");
			jo.put("lastName", "Smith");
			jo.put("age", 25);
			// for address data, first create LinkedHashMap
			Map m = new LinkedHashMap(4);
			m.put("streetAddress", "21 2nd Street");
			m.put("city", "New York");
			m.put("state", "NY");
			m.put("postalCode", 10021);
			// putting address to JSONObject
			jo.put("address", m);
			// for phone numbers, first create JSONArray 
			JSONArray ja = new JSONArray();
			m = new LinkedHashMap<>(2);
			m.put("type", "home");
			m.put("number", "212 555-1234");
			// adding map to list
			ja.add(m);
			m = new LinkedHashMap(2);
			m.put("type", "fax");
			m.put("number", "212 555-1234");
			// adding map to list
			ja.add(m);
			// putting phoneNumbers to JSONObject
			jo.put("phoneNumbers", ja);
			// writing JSON to file:"JSONExample.json" in cwd
			String filePath = FileReaderUtil.makePath( JSON_FOLDER, JSON_FILE_NAME, JSON_UPLOAD );
			String uploadFileName = DateUtil.getCurrentFormatDate( DateUtil.DATETIME ).concat( "_upload" );
			StringBuilder srcPath = new StringBuilder();
			srcPath.append( filePath );
			srcPath.append( File.separator );
			srcPath.append( uploadFileName );
			srcPath.append( "." );
			srcPath.append( JSON_EXTENSION );
			String uploadFile = srcPath.toString();
			PrintWriter pw = new PrintWriter( uploadFile );
			pw.write(jo.toJSONString());
			pw.flush();
			pw.close();
		} catch ( Exception e ) {
			logger.error(">>>>>>>>>>> upload json file error >>>>>>>>>>" + ExceptionUtils.getStackTrace(e) );
			body = new DataUtil();
			successYN = YnTypeCode.NO.getValue();
			resultCode = ResponseMessageTypeCode.GENERAL_ERROR.getResultCode();
			resultMessage = ResponseMessageTypeCode.GENERAL_ERROR.getResultMessage();
		}
		logger.error(">>>>>>>>>>> upload json file end >>>>>>>>>>");
		ResponseHeader header = new ResponseHeader(successYN, resultCode, resultMessage);
		return new ResponseData<DataUtil>(header, body);
	}
	
	
}
