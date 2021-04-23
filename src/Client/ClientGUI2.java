package Client;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Data.DataIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtFName;
	private JTextField txtID;
	private JTextField txtLName;
	private JTextField txtPCode;
	private JTextField txtAddress;
	private JTextField txtCity;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JComboBox cmbBxProv;
	private JTextArea txtDisplay;
	public static int count = 0;
	private Socket socket = null;
	private DataOutputStream outStream = null;
	private DataInputStream inStream = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client.ClientGUI frame = new Client.ClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientGUI() {
		setTitle("Cezmi's & Tim's DB Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 797, 503);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblID = new JLabel("ID");
		lblID.setForeground(Color.RED);
		lblID.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblID.setBounds(10, 11, 82, 29);
		contentPane.add(lblID);

		JLabel lblFName = new JLabel("First Name");
		lblFName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFName.setBounds(10, 51, 82, 29);
		contentPane.add(lblFName);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddress.setBounds(10, 91, 82, 29);
		contentPane.add(lblAddress);

		JLabel lblCity = new JLabel("City");
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCity.setBounds(10, 131, 82, 29);
		contentPane.add(lblCity);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPhone.setBounds(10, 171, 82, 29);
		contentPane.add(lblPhone);

		JLabel lblLName = new JLabel("Last Name");
		lblLName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLName.setBounds(315, 51, 88, 29);
		contentPane.add(lblLName);

		JLabel lblProv = new JLabel("Province");
		lblProv.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProv.setBounds(285, 131, 82, 29);
		contentPane.add(lblProv);

		JLabel lblPCode = new JLabel("Postal Code");
		lblPCode.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPCode.setBounds(413, 131, 88, 29);
		contentPane.add(lblPCode);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(285, 171, 82, 29);
		contentPane.add(lblEmail);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(e -> {
			try {
				// try to establish connection with server
				socket = new Socket("localhost",8000);
				count ++;
				System.out.println("Client " + count + " connected");
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				count--;
				ex.printStackTrace();
			}
		});
		btnConnect.setBackground(Color.GREEN);
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnConnect.setBounds(639, 11, 134, 35);
		contentPane.add(btnConnect);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> {
			try {
				outStream = new DataOutputStream(socket.getOutputStream());

				//can get all, send in string and split string on other end using comma
				String action = "Add";
				String fName = txtFName.getText();
				String lName = txtLName.getText();
				String address = txtAddress.getText();
				String city = txtCity.getText();
				String prov = (String) cmbBxProv.getSelectedItem();
				String postal = txtPCode.getText();
				String phone = txtPhone.getText();
				String email = txtEmail.getText();
				outStream.writeUTF( action + "," +fName + ","+ lName + ","+ address + ","+ city + ","+
						prov + ","+ postal + ","+ phone + ","+ email);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnAdd.setBackground(Color.CYAN);
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAdd.setBounds(639, 51, 134, 35);
		contentPane.add(btnAdd);

		JButton btnFind = new JButton("Find");
		btnFind.addActionListener(e -> {
			try {
				outStream = new DataOutputStream(socket.getOutputStream());
				inStream = new DataInputStream(socket.getInputStream());
				String displayInfo = "ID    First Name   Last Name  " +
						"Phone Number  Email \t Street \t City " +
						"Province  Postal Code \n";
				// can get all, send in string and split string on other end using comma
				String action = "Find";
				String fName = txtFName.getText();
				outStream.writeUTF(  action + "," + fName);
				displayInfo += inStream.readUTF();
				txtDisplay.setText(displayInfo);
				outStream.close();
				inStream.close();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnFind.setBackground(Color.ORANGE);
		btnFind.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFind.setBounds(639, 91, 134, 35);
		contentPane.add(btnFind);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> {
			try {
				outStream = new DataOutputStream(socket.getOutputStream());
				String regExp = "[1-9]{1,4}";
				// can get all, send in string and split string on other end using comma
				if (txtID.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Customer ID can not be null");
				} else if (!txtID.getText().matches(regExp)) {
					JOptionPane.showMessageDialog(null, "Customer ID must be number");
				} else {
					String action = "Update";
					String fName = txtFName.getText();
					String lName = txtLName.getText();
					String address = txtAddress.getText();
					String city = txtCity.getText();
					String prov = (String) cmbBxProv.getSelectedItem();
					String postal = txtPCode.getText();
					String phone = txtPhone.getText();
					String email = txtEmail.getText();
					String id = txtID.getText();
					outStream.writeUTF( action + "," + fName + ","+ lName + ","+ address + ","+ city + ","+
							prov + ","+ postal + ","+ phone + ","+ email + "," + id);
				}
				outStream.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnUpdate.setBackground(Color.ORANGE);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdate.setBounds(639, 131, 134, 35);
		contentPane.add(btnUpdate);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(e -> {
			try {
				// try to establish connection with server
				// need to reference current socket from connect button
				socket.close();	// closed connection
				System.out.println("Client " + count + " disconnected");
				count --;
				System.exit(0); // close app


			} catch (IOException ex) {
				// TODO Auto-generated catch block
				count--;
				ex.printStackTrace();
			}
		});
		btnExit.setBackground(Color.RED);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnExit.setBounds(639, 172, 134, 35);
		contentPane.add(btnExit);

		JLabel lblIDInfo = new JLabel("--> will be genrated for you!");
		lblIDInfo.setForeground(Color.RED);
		lblIDInfo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblIDInfo.setBounds(285, 11, 216, 29);
		contentPane.add(lblIDInfo);

		txtFName = new JTextField();
		txtFName.setBounds(102, 51, 203, 26);
		contentPane.add(txtFName);
		txtFName.setColumns(10);

		txtID = new JTextField();
		txtID.setColumns(10);
		txtID.setBounds(102, 11, 173, 26);
		contentPane.add(txtID);

		txtLName = new JTextField();
		txtLName.setColumns(10);
		txtLName.setBounds(413, 51, 216, 26);
		contentPane.add(txtLName);

		txtPCode = new JTextField();
		txtPCode.setColumns(10);
		txtPCode.setBounds(511, 134, 118, 26);
		contentPane.add(txtPCode);

		txtAddress = new JTextField();
		txtAddress.setColumns(10);
		txtAddress.setBounds(102, 91, 527, 26);
		contentPane.add(txtAddress);

		txtCity = new JTextField();
		txtCity.setColumns(10);
		txtCity.setBounds(102, 131, 173, 26);
		contentPane.add(txtCity);

		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(102, 174, 173, 26);
		contentPane.add(txtPhone);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(377, 174, 252, 26);
		contentPane.add(txtEmail);

		txtDisplay = new JTextArea();
		txtDisplay.setBounds(10, 224, 763, 231);
		contentPane.add(txtDisplay);


		try {
			DataIO dat = new DataIO();
			cmbBxProv = new JComboBox(dat.comboBoxLoader());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		cmbBxProv.setBounds(356, 136, 47, 22);
		contentPane.add(cmbBxProv);
	}
}