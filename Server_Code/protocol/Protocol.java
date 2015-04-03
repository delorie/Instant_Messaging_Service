/*
* @author Lennart Sommerfeld
* @copyright (c) 2015 Lennart Sommerfeld
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Lennart Sommerfeld <http://lennart-sommerfeld.de> 
* @link http://lennart-sommerfeld.de
* @version 1.0
*/
package protocol;

import java.io.Serializable;
import java.util.Date;

public class Protocol implements Serializable {

	private static final long serialVersionUID = 2584203323009771108L;
	private int senderID;
	private String name;
	private Date date;
	private String messageType;
	private String messageText;
	
	public Protocol(int id,String userName, Date date, String type, String text){
		this.senderID = id;
		this.name = userName;
		this.date = date;
		this.messageType = type;
		this.messageText = text;
	}
		
	public int getSenderID() {
		return senderID;
	}
	
	public String getName(){
		return name;
	}

	public Date getDate() {
		return date;
	}

	public String getMessageType() {
		return messageType;
	}

	public String getMessageText() {
		return messageText;
	}

}
