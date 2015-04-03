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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ClientMessagerGUI {

	private JPanel messagerPanel;
	private ClientMainGUI mainGUI;
	private JPanel textMessagesPanel;
	private JTextArea inputTextField ;
	private JScrollPane textMessagesPanelScroll;
	private JPanel rightPanel;
	
	public ClientMessagerGUI(ClientMainGUI mainGUI){
		this.mainGUI = mainGUI;
		this.createMessager();
	}
	
	private void createMessager(){
		// Hauptpanel
		messagerPanel = new JPanel();
		messagerPanel.setLayout(null);
		messagerPanel.setLocation(0, 0);
		messagerPanel.setSize(800,600);
		
		// Links Panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(null);
		leftPanel.setLocation(0,0);
		leftPanel.setSize(400,600);
		

		// ------------------------------------------------------------------------------------
		// Recht Panel
		rightPanel = new JPanel();
		rightPanel.setLocation(400,0);
		rightPanel.setMinimumSize(new Dimension(400,600));
		rightPanel.setMaximumSize(new Dimension(400,600));
		rightPanel.setSize(400,600);
		

		// ------------------------------------------------------------------------------------
		
		// nachrichten Fenster
		textMessagesPanel = new JPanel();
		textMessagesPanel.setLayout(new BoxLayout(textMessagesPanel,BoxLayout.Y_AXIS));
		textMessagesPanel.setLocation(0,0);
		textMessagesPanel.setSize(400,525);
		
		// ------------------------------------------------------------------------------------
		
		JPanel sendPanel = new JPanel();
		sendPanel.setLayout(null);
		sendPanel.setLocation(0,525);
		sendPanel.setSize(400,50);
		
		inputTextField = new JTextArea("test",2, 2);
		inputTextField.setWrapStyleWord(true);
		inputTextField.setEditable(true);
		inputTextField.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(inputTextField); 
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(250, 50);
		
		// Sende Button
		JButton sendButton = new JButton("Senden");
		sendButton.setLocation(250, 0);
		sendButton.setSize(150, 50);
		sendButton.setActionCommand("send");
		
		sendButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 System.out.println("Zusenden: "+inputTextField.getText());
	        	 setMessageBox(inputTextField.getText(),mainGUI.getUserName(), new Date());
	        	 
	        	 mainGUI.getClientClass().clientOutput.sendMessage(mainGUI.getUserId(), mainGUI.getUserName(),"message", inputTextField.getText());
	          }          
	    });
		textMessagesPanelScroll = new JScrollPane(textMessagesPanel);
		textMessagesPanelScroll.setLocation(0, 0);
		textMessagesPanelScroll.setSize(400, 525);
		textMessagesPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		leftPanel.add(textMessagesPanelScroll);
		
		sendPanel.add(scrollPane);
		sendPanel.add(sendButton);
		
		leftPanel.add(sendPanel);
		messagerPanel.add(leftPanel);
		messagerPanel.add(rightPanel);		
	}
	
	public JPanel getPanel(){
		return this.messagerPanel;
	}
	
	// Eingehende Naricht darstellen
	public void setMessageBox(String nachricht, String user, Date date){
		
		JPanel MessagePanel = new JPanel();
		MessagePanel.setLayout(new BoxLayout(MessagePanel,BoxLayout.Y_AXIS));
		MessagePanel.setLocation(0, 0);
		MessagePanel.setMinimumSize(new Dimension(400,100));
		MessagePanel.setMaximumSize(new Dimension(400,100));
		MessagePanel.setSize(400, 120);
		
		JLabel infoLabel = new JLabel("Geschrieben von "+user);
		infoLabel.setLocation(0, 0);
		infoLabel.setMinimumSize(new Dimension(400,25));
		infoLabel.setMaximumSize(new Dimension(400,25));
		infoLabel.setSize(400, 25);
		MessagePanel.add(infoLabel);
		
		JLabel infoDateLabel = new JLabel("am "+date);
		infoDateLabel.setLocation(0, 0);
		infoDateLabel.setMinimumSize(new Dimension(400,25));
		infoDateLabel.setMaximumSize(new Dimension(400,25));
		infoDateLabel.setSize(400, 25);
		MessagePanel.add(infoDateLabel);
		
		JTextArea inpuMessageField = new JTextArea(nachricht);
		inpuMessageField.setWrapStyleWord(true);
		inpuMessageField.setEditable(false);
		inpuMessageField.setLineWrap(true);
		inpuMessageField.setMinimumSize(new Dimension(400,50));
		inpuMessageField.setMaximumSize(new Dimension(400,50));
		inpuMessageField.setSize(400, 25);
		JScrollPane scrollPane = new JScrollPane(inpuMessageField);
		scrollPane.setMinimumSize(new Dimension(400,50));
		scrollPane.setMaximumSize(new Dimension(400,50));
		scrollPane.setSize(400, 25);
		MessagePanel.add(scrollPane);
		
		textMessagesPanel.add(MessagePanel);
		textMessagesPanel.revalidate();

	}
	
	// Erstellt die User Liste
	public void setUserList(String userListe){
		// Liste säubern und neu aufbauen
		this.rightPanel.removeAll();
		
		// Sind mehre user vorhanden? wenn ja sind sie per , getrennt
		if(userListe.contains(",") == true){
			System.out.println("User hinzufügen: asdasd");
			String[] users = userListe.split(",");
			for(int i = 0; i < users.length; i++){
				System.out.println(users[i]);
				JLabel infoLabel = new JLabel(users[i]);
				infoLabel.setLocation(0, i*25);
				infoLabel.setMinimumSize(new Dimension(400,50));
				infoLabel.setMaximumSize(new Dimension(400,50));
				infoLabel.setSize(400, 50);
				infoLabel.setHorizontalAlignment(JLabel.CENTER);
				rightPanel.add(infoLabel);
			}
		} else{
			// ist momentan nur ein User online
			System.out.println(userListe);
			JLabel infoLabel = new JLabel(userListe);
			infoLabel.setLocation(0, 25);
			infoLabel.setMinimumSize(new Dimension(400,50));
			infoLabel.setMaximumSize(new Dimension(400,50));
			infoLabel.setHorizontalAlignment(JLabel.CENTER);
			infoLabel.setSize(400, 50);
			rightPanel.add(infoLabel);	
		}
		this.rightPanel.repaint();
	}
	
}
