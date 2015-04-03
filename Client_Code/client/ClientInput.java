/*
* @author Lennart Sommerfeld
* @copyright (c) 2015 Lennart Sommerfeld
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Lennart Sommerfeld <http://lennart-sommerfeld.de> 
* @link http://lennart-sommerfeld.de
* @version 1.0
*/
package client;

import java.io.ObjectInputStream;

import protocol.Protocol;
import clientGUI.ClientMainGUI;

public class ClientInput implements Runnable{
	private ObjectInputStream socketInput;
	private ClientMainGUI mainGui;
	private boolean isRunning = true;
	
	public ClientInput(ObjectInputStream socketInput , ClientMainGUI mainGui){
		this.socketInput = socketInput;
		this.mainGui = mainGui;
	}
	
	// Prüft auf eingehende Narichten
	public void run(){
		Protocol tempProtocol;
		try{
			while((tempProtocol = (Protocol) this.socketInput.readObject()) != null){
					
					// wurde vom Server login geschickt wird die ID übermittelt und gespeichert
					if(tempProtocol.getMessageType().equalsIgnoreCase("login") == true){
						this.mainGui.setUserId(Integer.parseInt(tempProtocol.getMessageText()));
						this.mainGui.setTitle("IM Dienst (ID:"+this.mainGui.getUserId()+")");
						System.out.println("Zugewisene Client ID vom Server ist: "+this.mainGui.getUserId());
					} 
					else if(tempProtocol.getMessageType().equalsIgnoreCase("message") == true){
						// Naricht grafisch darstellen
						if(tempProtocol.getSenderID() == -1){
							this.mainGui.messageGui.setMessageBox(tempProtocol.getMessageText(), "Admin", tempProtocol.getDate());
						} else{
							this.mainGui.messageGui.setMessageBox(tempProtocol.getMessageText(), tempProtocol.getName(), tempProtocol.getDate());
						}
						
					}
					else if(tempProtocol.getMessageType().equalsIgnoreCase("newUserList") == true){
						// User Liste grafisch darstellen
						this.mainGui.messageGui.setUserList(tempProtocol.getMessageText());
					}
			}
		
		}catch(Exception e){
			e.getStackTrace();
		}
		
		// gibt es kein Input mehr verbindungen schliessen
		this.mainGui.close();
	}
	
	public void close(){
		System.out.println("Input CLOSE!");
		this.isRunning = false;
	}
}
