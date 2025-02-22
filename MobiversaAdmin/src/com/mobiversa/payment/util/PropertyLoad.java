package com.mobiversa.payment.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyLoad {

	private static final Logger logger = Logger.getLogger(PropertyLoad.class);

	/*
	 * public static String getFilePath(String fileName){ //To get path Properties
	 * prop = new Properties(); InputStream input = null; String path=null;
	 * 
	 * try {
	 * 
	 * ClassLoader loader = Thread.currentThread().getContextClassLoader();
	 * InputStream stream = loader.getResourceAsStream("/config.properties");
	 * prop.load(stream);
	 * 
	 * //System.out.println("Path :"+prop.getProperty("path"));
	 * path=prop.getProperty("path"); return path; } catch (IOException io) {
	 * io.printStackTrace(); } finally { if (input != null) { try { input.close(); }
	 * catch (IOException e) { e.printStackTrace(); } } }
	 * 
	 * return path; }
	 */

	public static Properties getFile() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;

		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("config.properties");

//			input = loader.getResourceAsStream("config_demo.properties"); //$NON-NLS-1$

			prop.load(input);

			return prop;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}

	public static Properties getFileData() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;

		try {

			/*
			 * ClassLoader loader = Thread.currentThread().getContextClassLoader(); input =
			 * loader .getResourceAsStream("C:\\Mobi_config\\AutoRun\\payment.properties");
			 * prop.load(input);
			 */

			input = new FileInputStream("C:\\Mobi_config\\AutoRun\\vctid.properties");
			prop.load(input);

			return prop;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}

	public static void main(String ar[]) {

		System.out.println(" Data :" + PropertyLoad.getFile().getProperty("PaymentURL"));

	}

}