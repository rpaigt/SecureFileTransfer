package com.cse476.auth;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

public class ClientUserInterface extends JFrame{

	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem connect;
	private JMenuItem exit;
	private JPanel panel;
	private LoginCredentialInterface login;
	private String host;
	private String port;
	private SSLServer server;
	private SSLClient client;
	private TextField filePathText;
	private JButton downloadButton;

	public ClientUserInterface() throws IOException {

		panel = new JPanel();
		
		setTitle("Secure File Transfer");
		setIconImage(ImageIO.read(this.getClass().getResource("/images/icon.png")));
		Toolkit toolkit=getToolkit();
		Dimension screenSize=toolkit.getScreenSize();
		setLocation(screenSize.width/2 - getWidth()/2, screenSize.height/2 -getHeight()/2);
		
		menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		
		connect = new JMenuItem("Connect",new ImageIcon(getClass().getResource("/images/connect.png")));
		connect.setMnemonic(KeyEvent.VK_C);
		connect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(login == null){
					login = new LoginCredentialInterface();
					login.setVisible(true);
					login.setDefaultCloseOperation(EXIT_ON_CLOSE);
					login.setSize(450, 275);
					login.requestFocus();
					login.getLoginButton().addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							host = login.getTextServer().getText();
							port = login.getTextPort().getText();
							login.dispose();
						}
					});
				}
			}
		});
		
		exit = new JMenuItem("Exit", new ImageIcon(getClass().getResource("/images/exit.png")));
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(-1);
			}
		});
		
		menu.add(connect);
		menu.add(exit);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		filePathText = new TextField("Enter file path here..");
		panel.add(filePathText);
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(filePathText.getText().length() != 0){

					Thread t = new Thread(new Runnable() {
					    @Override
					    public void run() {
					    	client = new SSLClient(host, port, filePathText.getText());
					    }
					});
					t.start();

				}else{
					JOptionPane.showMessageDialog(ClientUserInterface.this,
						    "Enter valid and existing file path to download",
						    "File path error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(downloadButton);
		
		add(panel);
	}

	
	public static void main(String[] args) throws IOException {
		
		ClientUserInterface ui = new ClientUserInterface();
		ui.setSize(500,500);
		ui.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ui.setVisible(true);

		Thread t = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	new SSLServer();
		    }
		});
		t.start();
		
	}
}
