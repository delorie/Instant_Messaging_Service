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

import clientGUI.ClientMainGUI;

public class Client {

	private Socket clientSocket;
	private ObjectInputStream socketInput;
	private ObjectOutputStream socketOutput;
	public ClientMainGUI mainGui;
	public ClientInput clientInput ;
	public ClientOutput clientOutput;
	
	public Client(ClientMainGUI mainGui){
		System.out.println("Socket starten...");
		this.mainGui = mainGui;
		
		this.init();
	}
	

	
	private void init(){
		try{
			// verbindung auf bauen
			System.out.println("Socket verbindung zu "+this.mainGui.getIpAdresse()+" Port:"+this.mainGui.getPort());
			
			this.clientSocket = new Socket(this.mainGui.getIpAdresse(),this.mainGui.getPort());
			System.out.println("Socket hergestellt...");
			
			// input erzeugen und Threads starten
			this.socketInput = new ObjectInputStream(this.clientSocket.getInputStream());
			this.clientInput = new ClientInput(this.socketInput,this.mainGui);
			Thread lising = new Thread(this.clientInput);
			lising.start();
			System.out.println("ClientInput Thread wurde gestartet");
			
			// output erzeugen und Threads starten
			this.socketOutput = new ObjectOutputStream(this.clientSocket.getOutputStream());
			this.clientOutput = new ClientOutput(this.socketOutput);
			Thread writerThread = new Thread(this.clientOutput);
			writerThread.start();
			System.out.println("ClientOutput Thread wurde gestartet");
			
			// Login anfrage verschicken
			this.clientOutput.sendMessage(mainGui.getUserId(), mainGui.getName(),"login",mainGui.getUserName());
			
			System.out.println("Streams erzeugt");
			
		}catch(IOException e){
			e.getStackTrace();
		}
	}
	
	// Verbindugen schliessen
	public void close(){
		try{
			this.clientOutput.close();
			this.socketOutput.close();
			this.clientInput.close();
			this.socketInput.close();
			this.clientSocket.close();
		}catch(IOException e){
			e.getStackTrace();
		}
	}
	

}
