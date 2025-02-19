package com.mobiversa.payment.controller.bean;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.json.JSONObject;

public class DeviceIdApi {
	
	public static String deviceIdUpdate() {
        // To get path
        Properties prop = new Properties();
        InputStream input = null;
        String path = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            input = loader.getResourceAsStream("/config.properties");
            prop.load(input);
            System.out.println("Path :" + prop.getProperty("deviceIdUpdate"));
            path = prop.getProperty("updatyeQuery1");
            return path;
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
        return path;
    }
	

	@SuppressWarnings("nls")
	public JSONObject updatyeQuery(String contactName,String deviceId,String activeStatus,String olddeviceId) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
			url = new URL(deviceIdUpdate());
		

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("contactName", contactName);
			params.put("deviceId", deviceId);
			params.put("activeStatus", activeStatus);
			params.put("olddeviceId", olddeviceId);
			
			
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			System.out.println("The params That passed" + paramss);
			
			os.flush();
			
			StringBuffer response = new StringBuffer();
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			
			while ((inputLine = br.readLine()) != null) {
				
				response.append(inputLine);
				
			}
			
			output = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
}
