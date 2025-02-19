package com.mobiversa.payment.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyLoader {

	private static final Logger logger = Logger.getLogger(PropertyLoader.class);

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

	// Taking data from config without using current tread
	// This is we create Ezysettle purpose
	public static Properties getFileData() {

		Properties prop = new Properties();
		try (InputStream input = PropertyLoader.class.getResourceAsStream("/config.properties")) {
//		try (InputStream input = PropertyLoader.class.getResourceAsStream("/config_demo.properties")) {

			if (input == null) {
				logger.info("Unable to find config.properties file");
			}
			prop.load(input);

		} catch (Exception e) {
			logger.info("Exception in getFileData : " + e.getMessage());
		}

		return prop;
	}

}