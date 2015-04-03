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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientLoginGUI {
	public JPanel loginMainPanel;
	
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField userNameTextField;
	private ClientMainGUI mainGUI;
	
	public ClientLoginGUI(ClientMainGUI mainGUI){
		this.mainGUI = mainGUI;
		this.createLogin();
	}
	
	private void createLogin(){
		
		loginMainPanel = new JPanel();
		loginMainPanel.setLayout(null);
		loginMainPanel.setLocation(200,200);
		loginMainPanel.setSize(400, 150);
		
		// IP-ADRESSE
		JPanel ipPanel = new JPanel();
			ipPanel.setLayout(null);
			ipPanel.setSize(400, 25);
			
		JLabel iplabel = new JLabel("IP-Adresse");
			iplabel.setLocation(0,0);
			iplabel.setSize(150,25);
			
		ipTextField = new JTextField("localhost");
			ipTextField.setLocation(150,0);
			ipTextField.setSize(250,25);
			
			
			ipPanel.add(iplabel);
			ipPanel.add(ipTextField);
			loginMainPanel.add(ipPanel);
			
		//----------------------------------------------------------------------	
		// PORT
		JPanel portPanel = new JPanel();
			portPanel.setLayout(null);
			portPanel.setLocation(0, 25);
			portPanel.setSize(400,25);
			
		JLabel portlabel = new JLabel("Port");
			portlabel.setLocation(0,0);
			portlabel.setSize(150,25);
			
		portTextField = new JTextField("6666");
			portTextField.setLocation(150,0);
			portTextField.setSize(250,25);
					
			portPanel.add(portlabel);
			portPanel.add(portTextField);
			loginMainPanel.add(portPanel);
			
		//----------------------------------------------------------------------
		// USERNAME
		JPanel userPanel = new JPanel();
			userPanel.setLayout(null);
			userPanel.setLocation(0, 50);
			userPanel.setSize(400,25);
				
		JLabel userlabel = new JLabel("Username");
			userlabel.setLocation(0,0);
			userlabel.setSize(150,25);
				
		userNameTextField = new JTextField("testUser");
			userNameTextField.setLocation(150,0);
			userNameTextField.setSize(250,25);
				
			userPanel.add(userlabel);
			userPanel.add(userNameTextField);
			loginMainPanel.add(userPanel);
			
		//----------------------------------------------------------------------
		// Anmelde Button
		JButton loginButton = new JButton("Anmelden");
		loginButton.setLocation(0, 75);
		loginButton.setSize(400,25);
		loginButton.setActionCommand("login");
		
		loginButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 try { 
	        		 mainGUI.setPort(Integer.parseInt(portTextField.getText()));
	        		 mainGUI.setIpAdresse(ipTextField.getText());
		        	 mainGUI.setUserName(userNameTextField.getText());
		        	 
		        	 mainGUI.showMessager();
	        	 }catch(NumberFormatException nfe){
	        		 System.out.println("Falsche Eingabe (Port)");
	        	 }
	          }          
	    });
		
		loginMainPanel.add(loginButton);
	}
	
	public JPanel getPanel(){
			return this.loginMainPanel;
	}
}
