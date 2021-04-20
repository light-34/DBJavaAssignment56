package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Business.Customers;
import Data.DataIO;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ServerGUI extends JFrame {

	private JPanel contentPane;
	private static ServerSocket ssHolder = null;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI frame = new ServerGUI();
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
	public ServerGUI() {
		setTitle("Cezmi's & Tim's DB Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 596);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtServer = new JTextArea();
		txtServer.setBounds(10, 102, 416, 446);
		contentPane.add(txtServer);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
				// to see whats been added for now 
				try {
					DataIO data = new DataIO();
					
					String str = "First Name" + "\t" + "Last Name\n";
					
					for(Customers list : data.getCustomers()) {
						str += list.getfName() + "\t" + list.getlName() +"\n";
					}
					txtServer.setText(str);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//System.exit(0); 
				
				/*
				try {					
					ssHolder.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
				
				/*
				if (ssHolder != null) {
				    try {
				      ssHolder.close();
				    } catch (Throwable ignored) {}
				  }
				  
				  */
				// another attempt - not working 
				/*
				int port = 8000;
				ServerSocket server = null;
				Socket socket = null;
				int clientCount = 0;
				
				try {
					server = new ServerSocket(port);
					server.close();
					System.out.println("Server closed");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				*/
				
				
				// not working properly 
				/*
				try {
					ssHolder.close();									
					System.out.println("Server closed");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				*/
			}
		});
		btnStop.setBackground(Color.RED);
		btnStop.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStop.setBounds(242, 21, 184, 48);
		contentPane.add(btnStop);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = 8000;
				ServerSocket server = null;
				Socket socket = null;
				int clientCount = 0;
				
				try {
					server = new ServerSocket(port);					
					server.setReuseAddress(true);	
					ssHolder = server; //doesn't seem to be working 
					while (true) {
						
						System.out.println("Server is running and waiting for client");
						
						socket = server.accept(); 	// gives java.net.SocketException: Socket is closed???
						//ssHolder = server;  
						// trying to save server info to close with close button 
						InetAddress cAddress = socket.getInetAddress();
						clientCount ++;
						
						System.out.println("New client connected - Number of clients that have connected: " + clientCount );
						System.out.println("Client Address: " + cAddress.getHostAddress());
						System.out.println("Client Host Name: " +cAddress.getHostName());
						
						ThreadHandler clientSocket = new ThreadHandler(socket);
						
						new Thread(clientSocket).start();
						
					}
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				} 				
				finally {
		            if (server != null) {
		                try {
		                    server.close();
		                }
		                catch (IOException ex) {
		                    ex.printStackTrace();
		                }
		            }
		        }
		        		        
			}
		});
		btnStart.setBackground(Color.GREEN);
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setBounds(10, 21, 184, 48);
		contentPane.add(btnStart);
		
		
	}
}
