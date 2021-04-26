package Data;

import java.sql.*;
import Business.Customers;

public class DataIO {

	private Connection conn = null;
	private String foundCustomers = "";
	private String updatedCustomer = "";
	private String addedCustomer = "";

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
		addedCustomer = customer.getfName() + " "+ customer.getlName() + "\nhas been added";
		stm.close();
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
		updatedCustomer = customer.getfName() + " "+ customer.getlName() + "\nhas been updated";
		stm.close();
	}

	//This method is used to find customers with specified first name
	public void findCustomers(String str) {
		String sqlQuery = "Select * from c_customers where fname = ? ";
		try {
			PreparedStatement prepState = conn.prepareStatement(sqlQuery);
			prepState.setString(1, str);
			ResultSet rst = prepState.executeQuery();

			while (rst.next()) {
				foundCustomers += String.valueOf(rst.getInt(1)) + "\t" + "\t" + rst.getString(2) + "\t" + "\t" +
						rst.getString(3) + "\t" + "\t" + rst.getString(4) + "\t" +
						rst.getString(5) + "\t" + "\t" + rst.getString(6) + "\t" + "\t" +
						rst.getString(7) + "\t" +  "\t" + rst.getString(8) + "\t" + "\t" +
						rst.getString(9) + "\n";
			}
			rst.close();
			prepState.close();
		} catch (Exception ex) {
			System.out.println("End of the DB records");
		}
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

	//This method will be used to send data to ThreadHandler Class
	public String getFoundCustomers() {
		return foundCustomers;
	}

	//This method will be used to send data to ThreadHandler Class
	public String getUpdatedCustomer() {
		return updatedCustomer;
	}

	//This method will be used to send data to ThreadHandler Class
	public String getAddedCustomer() {
		return addedCustomer;
	}
}
