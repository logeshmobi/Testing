package com.mobiversa.payment.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.mobiversa.payment.exception.MobileApiException;
import com.mobiversa.payment.exception.Status;



public class SockTcpip {
	
	static Socket socket;
	private static String ip; 
	private static int port;
	private static Semaphore sem = new Semaphore(1, true);
	private static Logger logger = Logger.getLogger(SockTcpip.class);
	
	private BufferedInputStream in;
	private BufferedOutputStream out;
	
	static {
		ip = PropertyLoad.getFile().getProperty("HSM_IP");
		port = Integer.parseInt(PropertyLoad.getFile().getProperty("HSM_PORT"));
		try{
			
//			socket = openSocket("172.18.81.99", 1500);
			//socket = openSocket("192.168.10.99", 1500);
			socket = openSocket(ip,port);
//			socket.setSoTimeout(1000);
		}catch(Exception e){logger.info("error " + e);}
	}
	
	
	
	public String WriteReadsock(String thalesCMD) throws MobileApiException,Exception {
		String data = null;
		logger.info("Thales Cmd Request Start ");
		//logger.info("Thales Cmd : "+thalesCMD);
		try {
			//logger.info("sem"+sem);
			sem.acquire();
			
			/*in = new BufferedInputStream(socket.getInputStream());
			out = new BufferedOutputStream(socket.getOutputStream());*/
			// thalesCMD = thalesCMD.trim();
			thalesCMD = thalesCMD.toUpperCase();
			int ln = thalesCMD.length();
			String mlen = Integer.toHexString(ln);
			for (int i = mlen.length(); i < 4; i++)
				mlen = "0" + mlen;
			byte[] msglen = str2Bcd(mlen);
			byte[] bMsg = concat(msglen, thalesCMD.getBytes());
			
			out = new BufferedOutputStream(socket.getOutputStream());
			out.write(bMsg);
			out.flush();
			// ****************************************************
			// logger.info("Data Sent.. Waiting for response.. ");
			in = new BufferedInputStream(socket.getInputStream());
			data = readData(in);
			//logger.info("Data got =>"+data+"<=");
			logger.info("Data got Response from HSM");
			sem.release();
		} catch (Exception e) {
			sem.release();
			e.printStackTrace();
			socket.close();
			connectHsm(ip, port);

			throw new MobileApiException(Status.HOST_DOWN);
		}/*finally{
			close(in);
			close1(out);
		}*/
		
		/*
		 * finally { socket.close(); }
		 */
		return data;
	}
	
	@SuppressWarnings("unused")
	private static void connectHsm(String ip, int port) {
		String ip2 = ip;
		int port2 = port;

		boolean Hstatus = true;
		socket = null;
		while (Hstatus) {
			try {
				socket = openSocket(ip, port);// openSocket(testServerName,
												// port);
				if (socket != null) {
					Hstatus = false;
				}
				// Else if it null.What will be the action.
			} catch (Exception e1) {
				// e1.printStackTrace();
			}
		}
	}
	
	
	public static Socket openSocket(String server, int port) {
		Socket socket = null;
		// create a socket with a timeout
		logger.info("Trying to Connect HSM ");
		try {
			InetAddress inteAddress = InetAddress.getByName(server);
			SocketAddress socketAddress = new InetSocketAddress(inteAddress,
					port);
			// create a socket
			socket = new Socket();
			// this method will block no more than timeout ms.
			int timeoutInMs = 1000; // 5 seconds
			socket.connect(socketAddress, timeoutInMs);
			//logger.info("HSM Successfully connected");
			//return socket;
		} catch (Exception ste) {
			// pcinextLogger.info("Timed out for the socket." + server + " : "
			// +port);
			// ste.printStackTrace();
			//return socket = null;
		}
		//logger.info("HSM Successfully connected");
		return socket;
	}
	
	private static String readData(BufferedInputStream in) throws IOException {
		int i = in.read();
		StringBuffer dataGot = new StringBuffer();
		if (i != -1) {
			// System.out.println("Got 1 char : "+(char)i);
			dataGot.append((char) i);
			int l = in.available();
			if (l > 0) {
				byte[] byteData = new byte[l];
				in.read(byteData);
				dataGot.append(new String(byteData));
			}
			return dataGot.toString();
		} else {
			return null;
		}
	}

	private static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte[] abt = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte[] bbt = new byte[len];
		abt = asc.getBytes();
		int j, k;
		int ascLen = asc.length() / 2;
		int abtPa1, abtPa2;
		for (int p = 0; p < ascLen; p++) {
			abtPa1 = 2 * p;
			abtPa2 = 2 * p + 1;
			if ((abt[abtPa1] >= '0') && (abt[abtPa1] <= '9')) {
				j = abt[abtPa1] - '0';
			} else if ((abt[abtPa1] >= 'a') && (abt[abtPa1] <= 'z')) {
				j = abt[abtPa1] - 'a' + 0x0a;
			} else {
				j = abt[abtPa1] - 'A' + 0x0a;
			}
			if ((abt[abtPa2] >= '0') && (abt[abtPa2] <= '9')) {
				k = abt[abtPa2] - '0';
			} else if ((abt[abtPa2] >= 'a') && (abt[abtPa2] <= 'z')) {
				k = abt[abtPa2] - 'a' + 0x0a;
			} else {
				k = abt[abtPa2] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	
	
	private static byte[] concat(byte[] b1, byte[] b2) {
		byte[] b3 = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, b3, 0, b1.length);
		System.arraycopy(b2, 0, b3, b1.length, b2.length);
		return b3;
	}
	
	
}
