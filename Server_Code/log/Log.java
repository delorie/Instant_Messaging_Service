/*
* @author Lennart Sommerfeld
* @copyright (c) 2015 Lennart Sommerfeld
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Lennart Sommerfeld <http://lennart-sommerfeld.de> 
* @link http://lennart-sommerfeld.de
* @version 1.0
*/
package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	private String logFolderDir;
	private BufferedWriter writer;
	private DateFormat format = new SimpleDateFormat("dd_MM_yyyy");
	private Date date = new Date();
	
	public Log(String Folderdir){
		this.logFolderDir = Folderdir;
		
		File theDir = new File(Folderdir);

		  if (!theDir.exists()) {
			  theDir.mkdir();
		  }
	}
	
	public void info(String infoText){
		System.out.println(infoText);
	}
	
	// Wurde true mit übergeben wird die info in File geschrieben
	public void info(String infoText, boolean saveinFile){
		this.info(infoText);
		
		if(saveinFile){
			this.writeInFile(infoText);
		}
	}
	
	// FEHLER Info hinzufügen
	public void error(String errorText){
		this.info("[FEHLER]: "+errorText, true);
	}
	
	private void writeInFile(String Text){
		
		
		// Tägliche Logdatei öffnen
		try {
			this.writer = new BufferedWriter(new FileWriter(new File(logFolderDir +"/"+ this.getCurrentTime() +"_ServerLog.txt"), true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// In die Log Datei schreiben
		date = new Date();
		try {
			this.writer.write("["+date+"] "+Text);
			this.writer.newLine();
			this.writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Aktuelles Datum holen, Format wird bei initialisierung festgelegt
	private String getCurrentTime(){
		return this.format.format(date);
	}
}
