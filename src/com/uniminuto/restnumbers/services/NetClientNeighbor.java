package com.uniminuto.restnumbers.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetClientNeighbor {

	public String getNumsNeightbor(String ip, String ListNeighborsCosult) {
		String json = "";
		try {
			URL url = new URL("http://" + ip + ":8080/RestNumbers/api/numbers?neighbors=" + ListNeighborsCosult);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			// si exite hay un error
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			
			String output;
			while ((output = br.readLine()) != null) {
				json += output;
			}
			
			conn.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return json;
	}
}
