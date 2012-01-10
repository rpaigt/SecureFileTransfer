package com.cse476.auth;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginCredentialInterface extends JFrame{
	
	private JPanel mainPanel;
	
	private JLabel loginLabel;
	private JLabel infoLabel;
	private JLabel server;
	private JTextField textServer;
	private JLabel port;
	private JTextField textPort;
	private JButton loginButton;
	
	public LoginCredentialInterface() {
		
		setTitle("Log in");
		setResizable(false);
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		
		Toolkit toolkit=getToolkit();
		Dimension screenSize=toolkit.getScreenSize();
		setLocation(screenSize.width/2 - getWidth()/2, screenSize.height/2 -getHeight()/2);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.weightx = 0.5;
		c.insets = new Insets(2, 2, 2, 2);
		loginLabel = new JLabel(new ImageIcon(getClass().getResource("/images/loginlabel.png")));
		mainPanel.add(loginLabel,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth =6;
		infoLabel = new JLabel("Connect To Server");
		mainPanel.add(infoLabel,c);
		
		c.gridwidth = 1;
		c.gridheight =2;
		c.gridx = 0;
		c.gridy = 2;
		server = new JLabel("Server");
		server.requestFocus();
		mainPanel.add(server,c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth =2;
		textServer = new JTextField();
		mainPanel.add(textServer,c);
		
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth=1;
		port = new JLabel("Port");
		mainPanel.add(port,c);
		
		c.gridx = 4;
		c.gridy = 2;
		c.gridwidth = 2;
		textPort = new JTextField();
		mainPanel.add(textPort,c);
		
		c.gridx = 3;
		c.gridy = 8;
		c.gridwidth =3;
		loginButton = new JButton("Log in",new ImageIcon(getClass().getResource("/images/btnlogin.png")));
		mainPanel.add(loginButton,c);
		
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		add(mainPanel);	
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JLabel getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(JLabel loginLabel) {
		this.loginLabel = loginLabel;
	}

	public JLabel getInfoLabel() {
		return infoLabel;
	}

	public void setInfoLabel(JLabel infoLabel) {
		this.infoLabel = infoLabel;
	}

	public JLabel getServer() {
		return server;
	}

	public void setServer(JLabel server) {
		this.server = server;
	}

	public JTextField getTextServer() {
		return textServer;
	}

	public void setTextServer(JTextField textServer) {
		this.textServer = textServer;
	}

	public JLabel getPort() {
		return port;
	}

	public void setPort(JLabel port) {
		this.port = port;
	}

	public JTextField getTextPort() {
		return textPort;
	}

	public void setTextPort(JTextField textPort) {
		this.textPort = textPort;
	}

	public JButton getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}
}