package com.mobiversa.payment.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class UrlSigner {

	private static String keyString = "GMxjCKdgfCdvjY-oexqk7J47B9k="; // generated secretKey

	private static byte[] key;

	public static String GenerateMapImage(String lat, String lon)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException {

		String urlString = null;
		urlString = "https://maps.googleapis.com/maps/api/staticmap?" + "center=" + lat + "," + lon
				+ "&zoom=14&markers=" + lat + "," + lon + "%7C" + lat + "," + lon + ""
				+ "&maptype=roadmap&path=color:0x0000FF80%7Cweight:5%7C" + lat + "," + lon + ""
				+ "&size=350x200&key=AIzaSyD7Yaz1aSMli9-dEkNLkzigM3fBUai64Io&sensor=TRUE_OR_FALSE";
		URL url = new URL(urlString);
		UrlSigner signer = new UrlSigner(keyString);
		String request = signer.signRequest(url.getPath(), url.getQuery());
		String mapUrl = url.getProtocol() + "://" + url.getHost() + request;
		return mapUrl;

	}

	public UrlSigner(String keyString) throws IOException {
		keyString = keyString.replace('-', '+');
		keyString = keyString.replace('_', '/');
		this.key = Base64.getDecoder().decode(keyString);
	}

	public static String signRequest(String path, String query)
			throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, URISyntaxException {
		String resource = path + '?' + query;
		SecretKeySpec sha1Key = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(sha1Key);
		byte[] sigBytes = mac.doFinal(resource.getBytes());
		String signature = Base64.getEncoder().encodeToString(sigBytes);
		signature = signature.replace('+', '-');
		signature = signature.replace('/', '_');
		return resource + "&signature=" + signature;
	}
	
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IOException, URISyntaxException {
		String lat="3.134709";
		String lon="101.692450";
		String mapURL=GenerateMapImage(lat, lon);
		System.out.println(mapURL);
	}

}