package mainClasses;

import java.io.IOException;

import Client.Client;
import Server.Server;
import crazyClient.CrazyClient;

public class CrazyMain {

	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		try {
			
			for (int i = 0; i < 10; i++) {
				new CrazyClient(8189);
				//new CrazyClient(8189);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			new Client(8189);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//*/
	}
	
}
