package com.benjie.JavaHttpClient;

/**
 *
 * @author Gaurav Balyan
 * Date : 20th July 2012
 * This class is used to invoke XML Management Interface over HTTPS.
 */
import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.google.common.io.BaseEncoding;

public class HTTPSClient {

	private URL dpUrlObj = null;

	private HttpsURLConnection httpsUrlConnectionObj = null;

	private String username = "";

	private String password = "";

	public HTTPSClient() {
		try {
			dpUrlObj = new URL("http://127.0.0.1:9550");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HTTPSClient(String host, int port, String username, String password) throws Exception {
		setUrl("https://" + host + ":" + port);
		setUsername(username);
		setPassword(password);
	}

	/**
	 * Returns the Host name configured, default is 127.0.0.1
	 *
	 * @return
	 */
	public String getHost() {
		return dpUrlObj.getHost();
	}

	/**
	 * Sets the Host name to connect
	 *
	 * @param host
	 */
	public void setHost(String host) throws Exception {
		dpUrlObj = new URL(dpUrlObj.getProtocol() + "://" + host + ":" + dpUrlObj.getPort());
	}

	/**
	 * Returns the Port number configured, default is 80
	 *
	 * @return
	 */
	public int getPort() {
		return dpUrlObj.getPort();
	}

	/**
	 * Sets the Port number to connect
	 *
	 * @param port
	 */
	public void setPort(int port) throws Exception {
		dpUrlObj = new URL(dpUrlObj.getProtocol() + "://" + dpUrlObj.getHost() + ":" + port);
	}

	/**
	 * Sets the Username for authentication
	 *
	 * @param port
	 */
	public void setUsername(String username) throws Exception {
		this.username = username;
	}

	/**
	 * Sets the password for authentication
	 *
	 * @param port
	 */
	public void setPassword(String password) throws Exception {
		this.password = password;
	}

	/**
	 * Returns the qualified URL, default is http://127.0.0.1:80
	 *
	 * @return
	 */
	public String getUrl() {
		return dpUrlObj.getProtocol() + "://" + dpUrlObj.getHost() + ":" + dpUrlObj.getPort();
	}

	/**
	 * Sets the URL to connect - overrides the current host, port, protocol settings
	 *
	 * @param url
	 */
	public void setUrl(String url) throws Exception {
		dpUrlObj = new URL(url);
	}

	/**
	 * Opens a connection to the specified URL
	 * XML Mgmt Calls are soap calls, so pass true 
	 * @throws Exception
	 */
	public void connect(boolean isSOAPCall) throws Exception {
		System.out.println("Connecting to " + dpUrlObj);
		synchronized (this) {
			try {
				httpsUrlConnectionObj = (HttpsURLConnection) dpUrlObj.openConnection();
				useBasicAuthentication();
				if (isSOAPCall) {
					setSOAPActionHeader("");
				}
				httpsUrlConnectionObj.setRequestMethod("GET");
				this.setDoOutput(true);
				httpsUrlConnectionObj.connect();
				httpsUrlConnectionObj.getOutputStream();
			} catch (Exception e) {
				System.out.println("Reopening the connection with auto certificate acceptor");
				CertificateHandler.installCert(getHost(), getPort());
				HttpsURLConnection.setDefaultSSLSocketFactory(CertificateHandler.getSSLContext().getSocketFactory());
				HostnameVerifier hv = new HostnameVerifier() {
					public boolean verify(String urlHostName, SSLSession session) {
						System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
						return true;
					}
				};
				HttpsURLConnection.setDefaultHostnameVerifier(hv);
				httpsUrlConnectionObj = (HttpsURLConnection) dpUrlObj.openConnection();
				useBasicAuthentication();
				if (isSOAPCall) {
					setSOAPActionHeader("");
				}
				httpsUrlConnectionObj.setRequestMethod("GET");
				this.setDoOutput(true);
				httpsUrlConnectionObj.connect();
				httpsUrlConnectionObj.getOutputStream();
			}
		}
	}

	/**
	 * This operation is used, if the credentials are to be passed as a basic authentication token
	 *
	 */
	public void useBasicAuthentication() {
		String basicAuth = username + ":" + password;
		String encoding = BaseEncoding.base64().encode(basicAuth.getBytes());
		httpsUrlConnectionObj.setRequestProperty("Authorization", "Basic " + encoding);
	}

	/**
	 * This operation is used, if the request is a soap call
	 *
	 * @param value
	 */
	public void setSOAPActionHeader(String value) {
		httpsUrlConnectionObj.setRequestProperty("SOAPAction", value);
	}

	/**
	 * Sets the URLConnection's doOutput flag.
	 *
	 * @param value
	 * @return
	 */
	public boolean setDoOutput(boolean value) {
		httpsUrlConnectionObj.setDoOutput(value);
		return httpsUrlConnectionObj.getDoOutput();
	}

	/**
	 * Sets the URLConnection's doInput flag.
	 *
	 * @param value
	 * @return
	 */
	public boolean setDoInput(boolean value) {
		httpsUrlConnectionObj.setDoInput(value);
		return httpsUrlConnectionObj.getDoInput();
	}

	/**
	 * Sends the data to the specified URL connection
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public int sendData(String data) throws Exception {
		if (httpsUrlConnectionObj.getDoOutput()) {
			httpsUrlConnectionObj.getOutputStream().write(data.getBytes());
		} else {
			httpsUrlConnectionObj.disconnect();
			throw new IllegalArgumentException("getDoOutput field not set");
		}
		return httpsUrlConnectionObj.getResponseCode();
	}

	/**
	 * Reads the data from the InputStream specified and sends the read data to the specified URL Connection
	 *
	 * @param fis
	 * @return
	 * @throws Exception
	 */
	public int sendData(InputStream is) throws Exception {
		int x;
		StringBuffer sb = new StringBuffer();
		while ((x = is.read()) != -1) {
			sb.append((char) x);
		}
		// System.out.println("Message Sent: " + sb.toString());
		sendData(sb.toString());
		return httpsUrlConnectionObj.getResponseCode();
	}

	/**
	 * Used to release the connection established.
	 *
	 */
	public void releaseConnection() {
		// System.out.println("Releasing Connection " + urlObj);
		httpsUrlConnectionObj.disconnect();
		// System.out.println("Connection released");
	}

	/**
	 * Returns the response from the server
	 *
	 * @return
	 * @throws Exception
	 */
	public String getResponse() throws Exception {
		InputStream is = httpsUrlConnectionObj.getInputStream();
		int x;
		StringBuffer sb = new StringBuffer();
		while ((x = is.read()) != -1) {
			sb.append((char) x);
		}
		return sb.toString();
	}

	/**
	 * Returns the error occured, if there is any error
	 *
	 * @return
	 * @throws Exception
	 */
	public String getError() throws Exception {
		InputStream is = httpsUrlConnectionObj.getErrorStream();
		int x;
		StringBuffer sb = new StringBuffer();
		while ((x = is.read()) != -1) {
			sb.append((char) x);
		}
		return sb.toString();
	}

	/**
	 * Send the request and return the response received
	 *
	 * @param message
	 * @return
	 */
	public String sendMessage(String message) {
		String result = "";
		System.out.println("Message Sent: " + message);
		try {
			int status = sendData(message);
			System.out.println("HTTP Status: " + status);
			if (status == 200) {
				result = getResponse();
			} else {
				result = getError();
			}
		} catch (Exception e) {
			result = e.getMessage();
		}
		System.out.println("Response: " + result);
		return result;
	}

	/**
	 * Send the request and return the response received
	 *
	 * @param messageStream
	 * @return
	 */
	public void sendMessage(InputStream messageStream) {
		try {
			int status = sendData(messageStream);
			System.out.println("HTTP Status: " + status);
			if (status == 200) {
				System.out.println("Response :" + getResponse());
			} else {
				System.err.println("Response :" + getError());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String args[]) throws Exception {
		String ip = "127.0.0.1"; // ipaddress of DataPower device
        int port = 9550; // SOMA port number
        String user = "admin";
        String pass = "password";  
        HTTPSClient httpsCleintObj = new HTTPSClient(ip, port, user, pass);
        httpsCleintObj.connect(true);		
		String environmentalSensorsReq = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Body><dp:request xmlns:dp=\"http://www.datapower.com/schemas/management\"><dp:get-status class=\"EnvironmentalSensors\"/></dp:request></env:Body></env:Envelope>";
        String environmentalSensorsResp = httpsCleintObj.sendMessage(environmentalSensorsReq);
        System.out.println("Response from Datapower device :"+environmentalSensorsResp);
		httpsCleintObj.releaseConnection();
	}
}