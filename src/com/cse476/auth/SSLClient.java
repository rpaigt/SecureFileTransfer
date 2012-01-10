package com.cse476.auth;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.security.KeyStore;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class SSLClient {

	private String ksName = "client.jks";
	private String ksPass = "ClientJKS";
	private String ctPass = "ClientKey";
	private BufferedReader r;
	private BufferedWriter w;
	private SSLSocket c;
	
	public SSLClient(String host, String port, String path) {
	
		  String ksName = this.ksName;
	      char[] ksPass = this.ksPass.toCharArray();
	      char[] ctPass = this.ctPass.toCharArray();
	      System.setProperty("javax.net.ssl.trustStore", this.ksName);
	      System.setProperty("javax.net.ssl.trustStorePassword", 
	         this.ksPass);

	      
	      try {
	         KeyStore ks = KeyStore.getInstance("JKS");
	         ks.load(new FileInputStream(ksName), ksPass);
	         KeyManagerFactory kmf = 
	         KeyManagerFactory.getInstance("SunX509");
	         kmf.init(ks, ctPass);
	         
	         SSLContext sc = SSLContext.getInstance("SSL");
	         sc.init(kmf.getKeyManagers(), null, null);
	         SSLSocketFactory f = sc.getSocketFactory();
	         c =(SSLSocket) f.createSocket(host, Integer.parseInt(port));
	         printSocketInfo(c);
	         c.startHandshake();
	         
	         w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
	         r = new BufferedReader(new InputStreamReader(c.getInputStream()));
	         
	         w.write(path+"\n");
	         w.flush();
	         
	         BufferedInputStream in = 
	     			new BufferedInputStream(c.getInputStream());
	      
	     	 BufferedOutputStream out = 
	     			new BufferedOutputStream(new FileOutputStream("test.mp3"));

	     	
	     	 int BUFFER_SIZE = 1024 * 10;
	     	 byte[] buffer = new byte[BUFFER_SIZE];
	     	 int len = 0;
	     	 

	     	while ((len = in.read(buffer,0,buffer.length)) != -1) {
	     		out.write(buffer, 0, len);
	     		out.flush();
	     		System.out.println("Client # :" + len);
	     	}
	     	out.flush();
	      } catch (Exception e) {
	         System.err.println(e.toString());
	      }
	}
	
	private static void printSocketInfo(SSLSocket s) {
	      System.out.println("Socket class: "+s.getClass());
	      System.out.println("   Remote address = "
	         +s.getInetAddress().toString());
	      System.out.println("   Remote port = "+s.getPort());
	      System.out.println("   Local socket address = "
	         +s.getLocalSocketAddress().toString());
	      System.out.println("   Local address = "
	         +s.getLocalAddress().toString());
	      System.out.println("   Local port = "+s.getLocalPort());
	      System.out.println("   Need client authentication = "
	         +s.getNeedClientAuth());
	      SSLSession ss = s.getSession();
	      try {
	         System.out.println("Session class: "+ss.getClass());
	         System.out.println("   Cipher suite = "
	            +ss.getCipherSuite());
	         System.out.println("   Protocol = "+ss.getProtocol());
	         System.out.println("   PeerPrincipal = "
	            +ss.getPeerPrincipal().getName());
	         System.out.println("   LocalPrincipal = "
	            +ss.getLocalPrincipal().getName());
	         System.out.println("   PeerPrincipal = "
	            +ss.getPeerPrincipal().getName());
	      } catch (Exception e) {
	         System.err.println(e.toString());
	      }
	}
}

