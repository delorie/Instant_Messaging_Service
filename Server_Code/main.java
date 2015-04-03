/*
* @author Lennart Sommerfeld
* @copyright (c) 2015 Lennart Sommerfeld
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* Written by Lennart Sommerfeld <http://lennart-sommerfeld.de> 
* @link http://lennart-sommerfeld.de
* @version 1.0
*/
import java.util.Scanner;

import server.Server;

public class main {
	
	//public static Log log = new Log("log");

	public static void main(String[] args) {
		System.out.println("Auf welchem Port soll der Server gestartet werden?");
		Scanner consoleScanner = new Scanner(System.in);
		
		int port = 6666;

		String tempDate = consoleScanner.nextLine();
		if (tempDate.matches("[0-9]+") == true) {
			port = Integer.parseInt(tempDate);
		}


		
		Server server = new Server(port);
		
	}

}
