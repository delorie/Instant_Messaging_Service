/*
* @author Lennart Sommerfeld
* @copyright (c) 2015 Lennart Sommerfeld
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Lennart Sommerfeld <http://lennart-sommerfeld.de> 
* @link http://lennart-sommerfeld.de
* @version 1.0
*/
package console;

import java.util.Date;
import java.util.Scanner;

import client.ClientData;
import server.Server;

public class ConsoleListener implements Runnable {
	private Server server;
	private Scanner inputScanner;
	
	public ConsoleListener(Server server){
		this.server = server;
		this.inputScanner = new Scanner(System.in);
	}
	
	public void run(){
		String tempConsoleInput;
		while(this.server.isAktiv == true){
			if(inputScanner.hasNext() == true){
				tempConsoleInput = inputScanner.nextLine();
				server.log.info("Admin führt folgenden Befehl ('"+tempConsoleInput+"') aus", true);
				
				// Naricht spliten Anweißung:Daten
				String[] tempStringArray = tempConsoleInput.split(":");
	
				if(tempStringArray[0].equalsIgnoreCase("Send")){
					
					// Nachricht an allen verschicken
					this.server.getClientSender().sendMeassge(-1, "Admin", new Date(), "message", tempStringArray[1]);
					
				} else if(tempStringArray[0].equalsIgnoreCase("kickUser")){
					this.server.removeClient(Integer.parseInt(tempStringArray[1]));
				}
				else if(tempStringArray[0].equalsIgnoreCase("getUserListe")){
					for(ClientData tempClientData : this.server.getClients()){
						server.log.info("ClientName: "+tempClientData.getUserName() +"(ID:"+tempClientData.getID()+")", true);
					}
				} else if (tempStringArray[0].equalsIgnoreCase("help")){
					System.out.println("send:[TextNachricht] // Verschickt eine Nachricht an allen Usern");
					System.out.println("getUserListe         // Gibt eine Liste von allen aktiven Usern");
					System.out.println("kickUser:[ClientID]  // Kickt User");
					System.out.println("stop                 // Server abschalten");
				}else if (tempStringArray[0].equalsIgnoreCase("stop")){
					this.server.stop();
				}else{
				
					System.out.println("Befehl nicht gefunden! Geben Sie 'help' ein");
				}
			}
		}
	}
	
}
