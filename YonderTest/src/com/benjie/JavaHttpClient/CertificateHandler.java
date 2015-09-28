/**
 *
 * @author Gaurav Balyan
 * Date : 20th July 2012
 * 
 * This class is used to add the received certificates during the handshake
 */
package com.benjie.JavaHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class CertificateHandler {
	private static CustomTrustManager customTrustMgrObj = null; // Custom Trust Manager object

	private static KeyStore keyStoreObj = null; // KeyStore holder

	/**
	 * Key store is the place where the received cryptographic keys are stored. This method is used to fetch the key store
	 * 
	 * @return
	 */
	public static File getKeyStore() {
		File file = new File("dpcerts");
		// Check if the keystore dpcerts exists in the current folder, if yes return the keystore, else check if dpcerts keystore is present in the
		// jre security folder, else select cacerts as the keystore.
		if (file.isFile() == false) {
			char SEP = File.separatorChar; // Get the seperator based on the OS
			File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
			file = new File(dir, "dpcerts"); // Get the dpcerts file from the jre security folder
			// If dpcerts is not present in the jre security folder then select cacerts which is a default keystore
			if (file.isFile() == false) {
				file = new File(dir, "cacerts");
			}
		}
		return file; // return the keystore
	}

	/**
	 * This operation is used to fetch the SSLContext 
	 * 
	 * @return
	 */
	public static SSLContext getSSLContext() {
		try {
			File keyStore = getKeyStore(); // Get the keystore

			InputStream in = new FileInputStream(keyStore); // Open the keystore as a stream
			keyStoreObj = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStoreObj.load(in, "changeit".toCharArray()); // Decrypt the keystore
			in.close(); // close the keystore stream

			SSLContext context = SSLContext.getInstance("SSL"); // Get SSL Context
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); // Get the Trust Manager Factory
			tmf.init(keyStoreObj); // initialize the trust managers present in the keystore
			X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0]; // get the trust managers present in the keystore
			customTrustMgrObj = new CustomTrustManager(defaultTrustManager); // Create a custom trust manager
			context.init(null, new TrustManager[] { customTrustMgrObj }, null); // Initialize the context
			return context; // return the created SSL context
		} catch (Exception e) { // Any error Just exit
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	/**
	 * This operation is used to install the received certificate in the key store This operation internally tries to connect to the host and port specifed and stores the certificates received in the key store
	 * 
	 * @param host
	 * @param port
	 */
	public static void installCert(String host, int port) {
		char[] passphrase;
		passphrase = "changeit".toCharArray();
		boolean flagTrusted = false;

		File file = getKeyStore(); // Fetches the keystore name

		System.out.println("Loading KeyStore " + file + "...");
		try {
			SSLContext context = getSSLContext();
			SSLSocketFactory factory = context.getSocketFactory();

			System.out.println("Opening connection to " + host + ":" + port);
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port); // Creates the socket to the host at provided port
			socket.setSoTimeout(10000); // set the timeout to 10000
			try {
				// System.out.println("Starting SSL handshake...");
				socket.startHandshake(); // Start the SSL Handshake
				socket.close(); // close the socket
				System.out.println("Certificate is found trusted");
				flagTrusted = true;
			} catch (SSLException e) {
				System.out.println("New Certificate Found");
			}

			X509Certificate[] chain = customTrustMgrObj.chain;
			if (chain == null) {
				System.out.println("Failed obtaining server certificate chain");
				return;
			}

			MessageDigest sha1_md = MessageDigest.getInstance("SHA1");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			for (int i = 0; i < chain.length; i++) {
				X509Certificate cert = chain[i];
				sha1_md.update(cert.getEncoded());
				md5.update(cert.getEncoded());
			}

			// Check if a new certificate is found
			if (!flagTrusted) {
				for (int i = 0; i < chain.length; i++) {
					X509Certificate cert = chain[i]; // Get the certificate chain
					String alias = host + "-" + (i + 1); // Get the alias
					keyStoreObj.setCertificateEntry(alias, cert); // store the certificate in the keystore

					OutputStream out = new FileOutputStream("dpcerts"); // Open the keystore
					keyStoreObj.store(out, passphrase); // Store the received certificates in keystore
					out.close(); // close the keystore
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Custom class
	 * 
	 * @author Pradeep Pappu
	 * 
	 */
	private static class CustomTrustManager implements X509TrustManager {

		private final X509TrustManager tm;

		private X509Certificate[] chain;

		CustomTrustManager(X509TrustManager tm) {
			this.tm = tm;
		}

		/**
		 * Just implement the methods of the interface
		 */
		public X509Certificate[] getAcceptedIssuers() {
			throw new UnsupportedOperationException();
		}

		/**
		 * Just implement the methods of the interface
		 */
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			throw new UnsupportedOperationException();
		}

		/**
		 * check if server is trusted using the default Trust manager / trust Manager passed
		 */
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			this.chain = chain;
			tm.checkServerTrusted(chain, authType);
		}
	}
}
