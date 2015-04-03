/*
* @author Lennart Sommerfeld
* @copyright (c) 2015 Lennart Sommerfeld
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Lennart Sommerfeld <http://lennart-sommerfeld.de> 
* @link http://lennart-sommerfeld.de
* @version 1.0
*/
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;

import console.ConsoleListener;

import log.Log;

import client.ClientConnection;
import client.ClientData;
import client.ClientSender;

public class Server {

	private ServerSocket serversocket;
	private ArrayList<ClientData> clientList = new ArrayList<ClientData>();
	private int idCounter = 1;
	public boolean isAktiv;
	private ClientConnection clientConnection;
	//private ClientListener clientListener;
	private ClientSender clientSender;
	private ConsoleListener consoleListener;
	
	public static Log log = new Log("log");
	
	public Server(int port){
		// Das des Server auf true setzen
		this.isAktiv = true;
		try{
			// ServerSocket auf übergegebenen Port öffnen
			this.serversocket = new ServerSocket(port);
			
			log.info("Server wurde auf Port: "+ port +" gestartet!", true );
			
			// Auf einkommenden Client anfragen hören, auf einen extra Thread
			this.clientConnection = new ClientConnection(this.serversocket, this);
			Thread cCThread = new Thread(this.clientConnection);
			// Thread starten
			cCThread.start();
			
			log.info("ClientConnection Thread wurde gestartet", true);
			
			// Thread zum verschicken von nachrichten an den Client
			this.clientSender = new ClientSender(this);
			Thread cSThread = new Thread(this.clientSender);
			cSThread.start();
				
			log.info("ClientSender Thread wurde gestartet", true);
			
			// Thread hört auf Consolen eingabe des Admin
			this.consoleListener = new ConsoleListener(this);
			Thread cLThread = new Thread(this.consoleListener);
			cLThread.start();
			
			log.info("ConsolenListener Thread wurde gestartet", true);
			
		}catch(IOException e){
			e.getStackTrace();
		}
	}
	
	
	
	// Wurde isAktiv auf false gesetzt werden alle unter geordene Threads gestopt
	public void stop(){
		this.isAktiv = false;
		log.info("Server wird abgeschalten!",true);
		System.exit(0);
	}
		
	// neuer Client in der ArrayList speichern
	public void addClient(Socket clientSocket){
		
		ClientData TempClient = new ClientData(clientSocket, this.idCounter,this);
		
		// Client in der List eintragen
		this.clientList.add(TempClient);
		Thread teampThread = new Thread(TempClient);
		teampThread.start();
		
		// Counter um eins erhöhen
		this.idCounter++;
		// Neue Liste von Usern verteilen
		this.clientSender.sendAllUserList();
	}
	
	public ArrayList<ClientData> getClients(){
		return clientList;
	}
	
	public ClientSender getClientSender(){
		return this.clientSender;
	}

	// Client aus der Liste werfen
	public void removeClient(int clientID){
		
		synchronized(this.getClients()){
			ListIterator<ClientData> li = this.getClients().listIterator();
			 
			while(li.hasNext()) {
				ClientData tempClient = li.next();
				if(tempClient.getID() == clientID){
					if(tempClient.getisAktiv() == true){
						tempClient.close();
						li.remove();
					}
				}
			}
		}
		
		log.info("Client ("+clientID+") wurde gelöscht", true);

		// Neue Client Liste verteilen
		this.clientSender.sendAllUserList();
	}
}
