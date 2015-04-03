/*
* @author Lennart Sommerfeld
* @copyright (c) 2015 Lennart Sommerfeld
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Lennart Sommerfeld <http://lennart-sommerfeld.de> 
* @link http://lennart-sommerfeld.de
* @version 1.0
*/
package clientGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;

public class ClientMainGUI extends JFrame{

	private JPanel main;
	public ClientLoginGUI loginGui;
	public ClientMessagerGUI messageGui;
	private String UserName;
	private int UserId;
	private String ipAdresse;
	private int port;
	private Client client;
	
	public ClientMainGUI(){
		this.init();
		
		showLogin();
	}
	
	private void init(){
		this.setTitle("IM Client");
		this.getContentPane().setLayout(null);
		
		this.main = new JPanel();
		this.main.setLayout(null);
		this.main.setLocation(0, 0);
		this.main.setSize(800, 600);
		this.add(this.main);
		this.setResizable(false);
		
		//JFrame.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		
		this.setVisible(true);
	}
		
	public void showLogin(){
		
		this.loginGui = new ClientLoginGUI(this);
		this.main.add(loginGui.getPanel());
		
		this.pack();
		this.setSize(800, 600);
		this.repaint();
	}
	
	public void showMessager(){
		System.out.println("User: "+this.UserName);
		System.out.println("Port: "+this.port);
		System.out.println("IP-Adress: "+this.ipAdresse);
		messageGui = new ClientMessagerGUI(this);
		
		this.main.removeAll();
		this.main.add(messageGui.getPanel());
		
		this.pack();
		this.setSize(800, 600);
		
		this.repaint();
		
		this.createSocket();
	}
	
	public void createSocket(){
		this.client = new Client(this);
		
		this.addWindowListener(new WindowAdapter() {
			private Client clientThread = client;

			public void windowClosing(WindowEvent we) {
				System.out.println("CLOSE!");
				close();
			}
		});
	}
	
	public void close(){
		this.client.close();
		System.exit(0);
	}

	
	// Setter und Getter --------------------------------------------------------------------------
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIpAdresse() {
		return ipAdresse;
	}

	public void setIpAdresse(String ipAdresse) {
		this.ipAdresse = ipAdresse;
	}
	
	public Client getClientClass(){
		return this.client;
	}

}
