package Server;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextArea;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGUI extends JFrame {

	private JPanel contentPane;
	private ServerSocket server;
	private Socket socket;
	private JTextArea txtServer;
	private DataInputStream inStream;
	private DataOutputStream outStream;

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
		setBounds(100, 100, 450, 596);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 224));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtServer = new JTextArea();
		txtServer.setBounds(10, 102, 416, 446);
		contentPane.add(txtServer);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(e -> {
			try {
				socket.close();
				server.close();
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
			new Thread (() -> {
				//Need ServerSocket object to establish a server and add a port
				try {
					server = new ServerSocket(8000);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}

				txtServer.setText("Server is running and waiting for a client");
				//Need a socket object to connect and communicate with the client
				try {
					while (true) {

						System.out.println("Server is running");
						//Socet is accepting
						socket = server.accept();

						//DataInputStream and DataOutputStream for receiving and sending data
						inStream = new DataInputStream(socket.getInputStream());
						outStream = new DataOutputStream(socket.getOutputStream());

						InetAddress iAddress = socket.getInetAddress();
						txtServer.setText("Host address : " + iAddress.getHostAddress() +
								"\nName is : " + iAddress.getHostName());
						System.out.println("\nName is : " + iAddress.getHostName());

						//Create a new Thread
						Thread mThread = new ThreadHandler(socket, inStream, outStream);
						//Start Thread
						mThread.start();
						System.out.println("Multi-thread started");
					}

				} catch (IOException ex) {
					assert socket != null;
					try {
						socket.close();
					} catch (IOException ioException) {
						ioException.printStackTrace();
					}
					ex.printStackTrace();
				}
			}).start();
		});

		btnStart.setBackground(Color.GREEN);
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setBounds(10, 21, 184, 48);
		contentPane.add(btnStart);
	}
}
