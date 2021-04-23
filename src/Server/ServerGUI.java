package Server;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ServerGUI extends JFrame {

	private JPanel contentPane;
	private ServerSocket server = null;
	private Socket socket = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server.ServerGUI frame = new Server.ServerGUI();
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
		btnStop.addActionListener(e -> {
			try {
				server.close();
				socket.close();
				System.exit(0);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		});
		btnStop.setBackground(Color.RED);
		btnStop.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStop.setBounds(242, 21, 184, 48);
		contentPane.add(btnStop);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(e -> {
			int clientCount = 0;
			try {
				while (true) {
					server = new ServerSocket(8000);

					System.out.println("Server is running and waiting for client");

					socket = server.accept(); 	// gives java.net.SocketException: Socket is closed???

					//DataInputStream and DataOutputStream for receiving and sending data
					DataInputStream inStream = new DataInputStream(socket.getInputStream());
					DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

					// trying to save server info to close with close button
					InetAddress cAddress = socket.getInetAddress();
					clientCount ++;

					txtServer.setText("New client connected - Number of clients that have connected: " + clientCount +
							"\nClient Address: " + cAddress.getHostAddress() + "\nClient Host Name: " +cAddress.getHostName());

					Thread clientSocket = new ThreadHandler(socket, inStream, outStream);
					clientSocket.start();
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
		});

		btnStart.setBackground(Color.GREEN);
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setBounds(10, 21, 184, 48);
		contentPane.add(btnStart);

	}
}
