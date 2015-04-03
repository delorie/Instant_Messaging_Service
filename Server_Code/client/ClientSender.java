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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.Date;
import java.util.ListIterator;
import java.util.Queue;

import server.Server;
import protocol.Protocol;

public class ClientSender implements Runnable{

	private Queue<Protocol> MessgeQueue = new ArrayBlockingQueue<Protocol>(100);
	private Server server;
	
	public ClientSender(Server server){
		this.server = server;
	}
	
	public void run(){
	
		while(this.server.isAktiv == true){
			if(MessgeQueue.isEmpty() == false){

				// Wenn in der MessageQueue was drinnen ist Client durch laufen
				// und Protocol verschicken
				Protocol protocol = MessgeQueue.poll();
				this.server.log.info("Es wird verschickt: "+protocol.getSenderID()+" , "+protocol.getName()+" , "+protocol.getDate()+" , "+protocol.getMessageType()+" , "+protocol.getMessageText(), true);
				
				 synchronized(this.server.getClients()){
					 ListIterator<ClientData> li = this.server.getClients().listIterator();
				 
					while(li.hasNext()) {
						ClientData tempClientData = li.next();
						if(tempClientData.getID() != protocol.getSenderID()){
							tempClientData.sendDate(protocol);
						}
					}
				 }
			}else{
				try {
					// Wenn die Queue leer ist Thread schlafen legen
					synchronized (this){
						this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// Nachrichten Protokol objekt erzeugen und in die Warte schlange zum verschicken legen
	public void sendMeassge(int id, String name,Date date, String type, String message){
		this.MessgeQueue.add(new Protocol(id, name, date, type, message));
		synchronized (this){
			this.notify();
		}
	}
	
	// Aktuele Client liste ermitteln und verschicken
	public void sendAllUserList(){
		
		String userList = "Admin";
		for(ClientData tempClientData : this.server.getClients()){
				userList += ","+tempClientData.getUserName();
		}
		
		this.sendMeassge(-2, "Server", new Date(), "newUserList", userList);
	
	}
}
