package com.cse476.auth;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class SSLServer {

	private String ksName = "server.jks";
	private String ksPass = "ServerJKS";
	private String ctPass = "ServerKey";
	private BufferedWriter w;
	private BufferedReader r;
	
	public SSLServer() {
	
		String ksName = this.ksName;
	    char[] ksPass = this.ksPass.toCharArray();
	    char[] ctPass = this.ctPass.toCharArray();
	    System.setProperty("javax.net.ssl.trustStore", this.ksName);
	    System.setProperty("javax.net.ssl.trustStorePassword", this.ksPass);

	    
	    try {
	         KeyStore ks = KeyStore.getInstance("JKS");
	         ks.load(new FileInputStream(ksName), ksPass);
	         KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
	         kmf.init(ks, ctPass);
	         SSLContext sc = SSLContext.getInstance("SSL");
	         
	         sc.init(kmf.getKeyManagers(), null, null);
	         SSLServerSocketFactory ssf = sc.getServerSocketFactory();
	         SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(8888);
	         s.setNeedClientAuth(true);
	         
	         printServerSocketInfo(s);
	         SSLSocket c = (SSLSocket) s.accept();
	         printSocketInfo(c);
	         
	         w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
	         r = new BufferedReader(new InputStreamReader(c.getInputStream()));
	         
	         String m = r.readLine();
	         
	         
	         BufferedInputStream in = 
	     			new BufferedInputStream(
	     				new FileInputStream(new File(m)));
	      
	     	BufferedOutputStream out = 
	     			new BufferedOutputStream(c.getOutputStream());
	     	
	     	int BUFFER_SIZE = 1024 * 10;
	     	byte[] buffer = new byte[BUFFER_SIZE];
	     	int len = 0;

	    	while ((len = in.read(buffer,0,buffer.length)) != -1) {
	    			out.write(buffer, 0, len);
	    			out.flush();
	    			System.out.println("Server # len : " + len);
	    	}
	    	out.flush();
	    } catch (FileNotFoundException e) {
	         System.err.println(e.toString());
	    } catch (Exception e) {
			// TODO: handle exception
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
	      } catch (Exception e) {
	         System.err.println(e.toString());
	      }
	 }
	
	  
	private static void printServerSocketInfo(SSLServerSocket s) {
	      System.out.println("Server socket class: "+s.getClass());
	      System.out.println("   Socker address = "
	         +s.getInetAddress().toString());
	      System.out.println("   Socker port = "
	         +s.getLocalPort());
	      System.out.println("   Need client authentication = "
	         +s.getNeedClientAuth());
	      System.out.println("   Want client authentication = "
	         +s.getWantClientAuth());
	      System.out.println("   Use client mode = "
	         +s.getUseClientMode());
	} 
}