package Server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import Business.Customers;
import Data.DataIO;

public class ThreadHandler extends Thread{
	private final Socket clientSocket;

	//Fields Data Input/Output Stream
	private final DataInputStream inStream;
	private final DataOutputStream outStream;

	// Constructor
	public ThreadHandler(Socket clientSocket, DataInputStream inStream, DataOutputStream outStream) {
		this.clientSocket = clientSocket;
		this.inStream = inStream;
		this.outStream = outStream;
	}

	@Override
	public void run() {
		try {
			DataIO data;	
			String action;
			String customer;
			String[] customerInfo;

			do {
				customer = inStream.readUTF(); // expecting input here, giving error 
				customerInfo = customer.split(",");
				action = customerInfo[0];

				switch (action) {
					case "Add" -> {
						// logic for adding customer
						data = new DataIO();
						Customers addCustomer = new Customers(customerInfo[1],
								customerInfo[2],
								customerInfo[7],
								customerInfo[8],
								customerInfo[3],
								customerInfo[4],
								customerInfo[5],
								customerInfo[6]);
						data.insertCustomer(addCustomer);
						outStream.writeUTF(data.getAddedCustomer());
					}
					case "Update" -> {						
						// logic for updating customer
						data = new DataIO();
						Customers updatedCustomer = new Customers(Integer.parseInt(customerInfo[9]), customerInfo[1],
								customerInfo[2],
								customerInfo[7],
								customerInfo[8],
								customerInfo[3],
								customerInfo[4],
								customerInfo[5],
								customerInfo[6]);
						data.updateCustomer(updatedCustomer);
						outStream.writeUTF(data.getUpdatedCustomer());
					}
					case "Find" -> {
						data = new DataIO();
						data.findCustomers(customerInfo[1]);
						outStream.writeUTF(data.getFoundCustomers());
					}
					//System.out.println("something went wrong");
					default -> System.out.println("something went wrong");
				}

			}while(true);

		} catch (Exception e) {
			System.out.println("Connection lost");
		}
	}
}
