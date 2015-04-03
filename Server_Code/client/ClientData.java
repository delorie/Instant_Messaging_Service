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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import log.Log;
import protocol.Protocol;
import server.Server;

public class ClientData implements Runnable {

	private Socket clientSocket;
	private int id;
	private String userName;
	private Server  server;
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	private boolean isAktiv = true;
	public static Log log = new Log("log");
	
	public ClientData(Socket socketAccept, int id,Server  server){
		this.clientSocket = socketAccept;
		this.id = id;
		this.server = server;
		
		log.info("Neuer Client wurde hinzugefügt:"+socketAccept.getInetAddress(),true);
		
		try{
			
			// Output stream erstellen
			this.objectOutput = new ObjectOutputStream(this.clientSocket.getOutputStream());
			// Input Stream erstellen
			this.objectInput = new ObjectInputStream(this.clientSocket.getInputStream());
			
			
			// Anmeldung vom Client auswerten
			Protocol tempProtocol;
			try{
				
				tempProtocol = (Protocol) this.objectInput.readObject();

				if(tempProtocol.getMessageType().equalsIgnoreCase("login") == true){
					log.info("Client ("+socketAccept.getInetAddress()+") hat eine Login anfrag geschickt und bekommt die ID ("+this.id+")",true);
					
					// Name vom User Speichern
					this.userName = tempProtocol.getMessageText();
					
					// bestätigung an den Client schicken mit dem zugeordenen ID
					this.sendDate(new Protocol(-2,"Server",new Date(),"login", ""+this.id));
					

				} else {
					log.info("Client ("+socketAccept.getInetAddress()+") hat keine Login anfrage geschickt -> close",true);
					this.close();
				}
								
			}catch(Exception e){
				e.getStackTrace();
			}
			
		}catch(IOException e){
			e.getStackTrace();
		}
	}
	
	// Hört auf eingehende Nachrichten
	public void run(){
		Protocol tempProtocol;

		try{
			while((tempProtocol = (Protocol) this.objectInput.readObject()) != null){

				if(tempProtocol.getMessageType().equalsIgnoreCase("message") == true){
					
					this.server.getClientSender().sendMeassge(tempProtocol.getSenderID(), tempProtocol.getName(), tempProtocol.getDate(), "message", tempProtocol.getMessageText());
				} 
				else if (tempProtocol.getMessageType().equalsIgnoreCase("getUsers") == true){
					
					String userList = "Admin";
					for(ClientData tempClientData : this.server.getClients()){
							userList += ","+tempClientData.getUserName();
					}
					
					this.sendDate(new Protocol(-2, "Server", new Date(), "newUserList", userList));
				}
						
			}
		}catch(Exception e){
			e.getStackTrace();
		}
		
		
		if(isAktiv == true){
			System.out.println("Narchicht konnte nicht verschickt werden");
			this.server.removeClient(this.id);
		}
		
	}
		
	public void sendDate(Protocol protocol){
		try{
			this.objectOutput.writeObject(protocol);
			this.objectOutput.flush();		
		} catch(IOException e){
			this.close();
			e.getStackTrace();
		}
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public boolean close(){
		this.isAktiv = false;
		
		try{
			this.objectInput.close();
		} catch(Exception e){
			
			e.getStackTrace();
		}
		
		try{
			this.objectOutput.close();
		} catch(Exception e){
			
			e.getStackTrace();
		}
		
		try{
			this.clientSocket.close();
		} catch(Exception e){
			
			e.getStackTrace();
		}
			log.info("Client ("+this.id+") wurde entfernt", true);
			return true;	
	}
	
	public int getID(){
		return this.id;
	}
	
	public boolean getisAktiv(){
		return this.isAktiv;
	}
	
	public Socket getSocket(){
		return this.clientSocket;
	}
}
