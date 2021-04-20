package Server;

import java.io.*;
import java.net.*;
import java.sql.SQLException;

import Business.Customers;
import Data.DataIO;

public class ThreadHandler implements Runnable{

	private final Socket clientSocket;
	  
    // Constructor
    public ThreadHandler(Socket socket)
    {
        this.clientSocket = socket;
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub		
				
		DataInputStream dIn = null;
		try {					
			dIn = new DataInputStream(clientSocket.getInputStream());
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
				customer = dIn.readUTF();
				customerInfo = customer.split(",");
				action = customerInfo[8];
				switch(action)
				{
				case"Add":
					fName = customerInfo[0];
					lName = customerInfo[1];
					address = customerInfo[2];
					city = customerInfo[3];
					prov = customerInfo[4];
					postal = customerInfo[5];
					phone = customerInfo[6];
					email = customerInfo[7];
					
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
					break;
					
				case"Update":
					fName = customerInfo[0];
					lName = customerInfo[1];
					address = customerInfo[2];
					city = customerInfo[3];
					prov = customerInfo[4];
					postal = customerInfo[5];
					phone = customerInfo[6];
					email = customerInfo[7];
					id = Integer.parseInt(customerInfo[9]);
							
					
					// logic for updating customer 
					data = new DataIO();
					Customers updatedCustomer = new Customers(id,fName, 
													   lName, 
													   phone,
													   email,
													   address,
													   city,
													   prov,
													   postal);
					
					data.updateCustomer(updatedCustomer);
					//System.out.println("something went wrong");
					break;
					
				default:
					System.out.println("something went wrong");
				}
				//clientSocket.close();
			}while(action != null);		
			
		} catch (IOException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
