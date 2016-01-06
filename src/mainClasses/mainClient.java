package mainClasses;

import java.io.IOException;

import Client.Client;

public class mainClient {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		try {
			new Client(8189);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			new Client(8189);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
