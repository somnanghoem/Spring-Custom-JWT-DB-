/*-----------------------------------------------------------------------------------------
 * NAME : MRFileReaderUtil.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
* <PRE>
*  -- detail description --
* </PRE>
*
* @logicalName MRFileReaderUtil
* @version   0.1, 2023-07-26
*/

public class FileReaderUtil extends FileUtil {
	
	/**
	 * -- Get List File --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param path
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static List<File> getListFile( String path ) {
		
		List<File> fileList = new ArrayList<File>(0);
		File directory = new File(path);
		if ( !directory.isDirectory() ) {
			return fileList;
		}
		File[] chkFiles = directory.listFiles( getCheckFileFilter() );
		for ( File file : chkFiles ) {
			String filePath = file.getAbsolutePath();
			filePath = filePath.substring( 0, filePath.length() - CHECK_FILE_EXTENSION.length() );
			File dataFile = new File( filePath );
			if ( dataFile != null ) {
				fileList.add( dataFile );
			}
		}
		return fileList;
		
	}
	
	/**
	 * -- Get Check File Filter --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	private static FileFilter getCheckFileFilter() {
		FileFilter checkFilefilter = new FileFilter() {
			public boolean accept( File file ) {
				if ( file.getName().endsWith( CHECK_FILE_EXTENSION ) ) {
					return true;
				}
				return false;
			}
		};
		return checkFilefilter;
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param file
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static String readFile( File file ) {
		return readFile( file.getAbsoluteFile() );
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param filePath
	 * @return
	 * @throws IOException 
	 * @exception 
	 * @fullPath 
	 */
	public static String readFile( String filePath ) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		File dataFile = new File( filePath );
		if ( !dataFile.exists() ) {
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader( new FileReader( filePath ) );
			String readLine = reader.readLine();
			while( readLine != null ) {
				sb.append( readLine );
				sb.append(System.lineSeparator());
				readLine = reader.readLine();
			}
		} catch ( FileNotFoundException e ) {
			throw e;
		} catch ( IOException e ) {
			throw e;
		} finally {
			if ( reader != null ) {
				try {
					reader.close();
				} catch ( IOException e ) {
					throw e;
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param path
	 * @param extension
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	public static List<File> listFilesWithExtension( String path, String extension ) {
		
		List<File> fileList = new ArrayList<File>(0);
		File directory = new File( path );
		if ( !directory.isDirectory() ) {
			return fileList;
		}
		if ( extension != null) {
			if ( !extension.startsWith( "." ) ) {
				extension = "." + extension;
			}
		} else {
			return fileList;
		}
		File[] getFiles = directory.listFiles( getFileFilterWithExtension( extension ) );
		for ( File file : getFiles ) {
			String filePath = file.getAbsolutePath();
			
			File dataFile = new File( filePath );
			if ( dataFile != null ) {
				fileList.add( dataFile );
			}
		}
		
		return fileList;
	}

	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param extension
	 * @return
	 * @exception 
	 * @fullPath 
	 */
	private static FileFilter getFileFilterWithExtension( String extension ) {
		FileFilter filefilter = new FileFilter() {
			public boolean accept( File file ) {
				if ( file.getName().endsWith( extension ) ) {
					return true;
				}
				return false;
			}
		};
		return filefilter;
	}
	
	/**
	 * -- detail description --
	 *
	 * @serviceID 
	 * @logicalName 
	 * @param oriFile
	 * @param newFile
	 * @exception 
	 * @fullPath 
	 */
	public static void moveToBackupManually( String sourceFile, String destinationFile ) throws Exception {
		try {
			Files.move( Paths.get( sourceFile ), Paths.get( destinationFile ) );
		} catch ( Exception e ) {
			throw e;
		}
	}

}
