/*-----------------------------------------------------------------------------------------
 * NAME : FileUtil.java
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
package com.security.springsecurity.util.file;

import java.io.File;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName FileUtil
* @version   0.1, 2023-07-26
*/

public class FileUtil {
	
	protected final static String END_STRING				= "@@";
	protected final static String ROOT_PATH					= "/files";
	protected final static String CHECK_FILE_EXTENSION		= ".chk";
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param folder
	 * @param fileName
	 * @param method
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static String getPath( String folder, String fileName, String method ) {
		StringBuilder path = new StringBuilder();
		path.append( ROOT_PATH );
		path.append( File.separator );
		path.append( folder );
		path.append( File.separator );
		path.append( fileName );
		path.append( File.separator );
		path.append( method );
		return path.toString();
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param folder
	 * @param fileName
	 * @param method
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static String makePath( String folder, String fileName, String method ) {
		String path = getPath( folder, fileName, method );
		return makePath( path );
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param filePath
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static String makePath( String filePath ) {
		File file = new File( filePath );
		if( !file.exists() ) {
			file.mkdirs();
		}
		return file.getPath();
	}
}
