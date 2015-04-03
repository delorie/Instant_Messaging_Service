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
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import protocol.Protocol;

public class ClientOutput implements Runnable {
	private ObjectOutputStream socketOutput;
	private Queue<Protocol> MessgeQueue = new ArrayBlockingQueue<Protocol>(10);
	private boolean isRunning = true;
	
	public ClientOutput(ObjectOutputStream socketOutput){
		this.socketOutput = socketOutput;
	}
	
	public void run(){
		// Schlange durch laufen und an server verschicken
		while(isRunning == true){
			if(this.MessgeQueue.isEmpty() == false){
				try{
					this.socketOutput.writeObject(this.MessgeQueue.poll());
					this.socketOutput.flush();
				}catch(IOException e){
					e.getStackTrace();
				}
			}else{
				try{
					synchronized (this){
						this.wait();
					}
				}catch(InterruptedException e){
					e.getStackTrace();
				}
			}
		}
	}
	
	// Protocol erzeugen und in Schlange schieben
	public void sendMessage(int id, String name,String typ, String message){

		this.MessgeQueue.add(new Protocol(id, name,new Date(),typ,message));
		
		synchronized (this){
			this.notify();
		}
	}
	
	public void close(){
		System.out.println("Output CLOSE!");
		this.isRunning = false;
	}
}
