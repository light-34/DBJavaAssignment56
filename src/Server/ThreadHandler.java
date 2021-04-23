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
		// TODO Auto-generated method stub

		try {
			DataIO data;
			//Customers newCustomer;
			String fName;
			String lName;
			String address;
			String city;
			String prov;
			String postal;
			String phone;
			String email;
			int id;
			String action;
			String customer;
			String[] customerInfo;

			// below is not working properly,
			// doesn't want to add and update in one session

			do {
				customer = inStream.readUTF();
				customerInfo = customer.split(",");
				action = customerInfo[0];

				switch (action) {
					case "Add" -> {
						fName = customerInfo[1];
						lName = customerInfo[2];
						address = customerInfo[3];
						city = customerInfo[4];
						prov = customerInfo[5];
						postal = customerInfo[6];
						phone = customerInfo[7];
						email = customerInfo[8];

						// logic for adding customer
						data = new DataIO();
						Customers addCustomer = new Customers(fName,
								lName,
								phone,
								email,
								address,
								city,
								prov,
								postal);
						data.insertCustomer(addCustomer);
					}
					case "Update" -> {
						fName = customerInfo[1];
						lName = customerInfo[2];
						address = customerInfo[3];
						city = customerInfo[4];
						prov = customerInfo[5];
						postal = customerInfo[6];
						phone = customerInfo[7];
						email = customerInfo[8];
						id = Integer.parseInt(customerInfo[9]);

						// logic for updating customer
						data = new DataIO();
						Customers updatedCustomer = new Customers(id, fName,
								lName,
								phone,
								email,
								address,
								city,
								prov,
								postal);
						data.updateCustomer(updatedCustomer);
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

		} catch (IOException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
