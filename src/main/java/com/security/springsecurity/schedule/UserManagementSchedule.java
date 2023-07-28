package com.security.springsecurity.schedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.security.springsecurity.util.date.DateUtil;
import com.security.springsecurity.util.file.FileReaderUtil;
import com.security.springsecurity.util.file.FileUtil;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class UserManagementSchedule {
	
	// Text File	
	private final static String TXT_READ		 = "read";
	private final static String TXT_FOLDER		 = "text";
	private final static String TXT_BACKUP		 = "backup";
	private final static String TXT_EXTENSION	 = "txt";
	private final static String TXT_FILE_NAME	 = "file";
	//	Example
	//	"0 0 * * * *"                 the top of every hour of every day.
	//	"*/10 * * * * *"              every ten seconds.
	//	"0 0 8-10 * * *"              8, 9 and 10 o'clock of every day.
	//	"0 0/30 8-10 * * *"           8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
	//	"0 0 9-17 * * MON-FRI"        on the hour nine-to-five weekdays
	//	"0 0 0 25 12 ?"               every Christmas Day at midnight
	// second, minute, hour, day of month, month, day(s) of week
	@Scheduled( cron ="*/10 * * * * *")
	public void readUserTextFile() throws Exception {
		
		System.out.println(">>>>>>>>> read text file start >>>>>>>>>" );
		String path = FileUtil.getPath(  TXT_FOLDER , TXT_FILE_NAME, TXT_READ);
		List<File> files = FileReaderUtil.listFilesWithExtension( path, TXT_EXTENSION );
		if( files.size() > 0 ) {
			for ( File file: files ) {
				BufferedReader read = new BufferedReader( new FileReader( file ) );
				String line = "";
				while( ( line = read.readLine() ) != null ) {
					System.out.println( line );
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
		System.out.println(">>>>>>>>> read text file end >>>>>>>>>" );
	}

}
