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
import java.net.ServerSocket;

import server.Server;

public class ClientConnection implements Runnable {

	private Server server;
	private ServerSocket serverSocket;
	
	public ClientConnection(ServerSocket serverSocket, Server server){
	
		this.serverSocket = serverSocket;
		this.server = server;
	}
	
	public void run(){
		// So lange laufen wie der Server aktiv ist
		while(this.server.isAktiv == true){
			// Auf ein gehenden verbindungen warten
			try{
				// Client verbindung in Arraylist speichern
				this.server.addClient(serverSocket.accept());
				
			}catch(IOException e){
				e.getStackTrace();
			}
		}
	}
}
