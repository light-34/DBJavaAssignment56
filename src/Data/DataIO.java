package Data;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Business.Customers;

public class DataIO {

	private Connection conn = null;
	public static int pos = 0;
	
	// This Constructor is used to connect the DB
	public DataIO() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(DBProps.getDBURL(), DBProps.getConnectionProps());
	}
	
	// This method is used to INSERT data into the customers table
	public void insertCustomer(Customers customer) throws SQLException {

		System.out.println("Insert Customer Table works");

		String strSQL = "Insert into c_customers (fname, lname, phone, email, street, city, province, post_code) "
				+ "values ('" + customer.getfName() + "', '" + customer.getlName() + "','" + customer.getPhoneNo()
				+ "','" + customer.getEmail() + "','" + customer.getStreet() + "','" + customer.getCity() + "','"
				+ customer.getProvince() + "','" + customer.getPostalCode() + "')";
		Statement stm = conn.createStatement();
		stm.executeUpdate(strSQL);

		System.out.println("Insert Customer Table Data Inserted");
		JOptionPane.showMessageDialog(null,"Customer added\nCustomer Name: " +  customer.getfName() + ", " + customer.getlName()
		+ "\nPhone No: "+customer.getPhoneNo()
		+ "\nEmail: "+customer.getEmail()
		+ "\nStreet: "+customer.getStreet()
		+ "\nCity: "+customer.getCity()
		+ "\nProv: "+customer.getProvince()
		+ "\nPostal Code: "+customer.getPostalCode()
		);

		stm.close();
	}
	// This method is used to INSERT data into the products table
	

	// This method is designed to add all rows in Customers Table into an ArrayList
	public ArrayList<Customers> getCustomers() throws SQLException {
		ArrayList<Customers> custList = new ArrayList<Customers>();

		String sqlQuery = "Select * from C_CUSTOMERS";

		Statement stm = conn.createStatement();

		ResultSet rst = stm.executeQuery(sqlQuery);

		while (rst.next()) {
			Customers cust1 = new Customers(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
					rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9));
			custList.add(cust1);
		}
		

		rst.close();
		stm.close();
		return custList;
	}

	// This method is designed to update rows in Customers Table
	public void updateCustomer(Customers customer) throws SQLException {
		System.out.println("Update Customer Table works");
		String strSQL = "Update C_CUSTOMERS set FNAME = '" + customer.getfName() + "'" + ", LNAME = '"
				+ customer.getlName() + "', PHONE = '" + customer.getPhoneNo() + "', EMAIL = '" + customer.getEmail()
				+ "', STREET = '" + customer.getStreet() + "', CITY = '" + customer.getCity() + "', PROVINCE = '"
				+ customer.getProvince() + "', POST_CODE = '" + customer.getPostalCode() + "' where CustomerID = "
				+ customer.getCustomerid();

		Statement stm = conn.createStatement();
		stm.executeUpdate(strSQL);
		System.out.println("Updated Customer Table Data Updated");
		stm.close();
		JOptionPane.showMessageDialog(null,"Customer Updated\nCustomer Name: " +  customer.getfName() + ", " + customer.getlName()
		+ "\nPhone No: "+customer.getPhoneNo()
		+ "\nEmail: "+customer.getEmail()
		+ "\nStreet: "+customer.getStreet()
		+ "\nCity: "+customer.getCity()
		+ "\nProv: "+customer.getProvince()
		+ "\nPostal Code: "+customer.getPostalCode()
		);
	}

	public String[] comboBoxLoader() throws SQLException {
		String[] list = new String[14];
		int i = 0;

		String sqlQuery = "SELECT * FROM PROVINCES ORDER BY PROVINCENAME";

		Statement stm = conn.createStatement();

		ResultSet rst = stm.executeQuery(sqlQuery);

		while (rst.next()) {
			list[i] = rst.getString("provincename");
			i++;
		}

		stm.close();

		return list;
	}
}
