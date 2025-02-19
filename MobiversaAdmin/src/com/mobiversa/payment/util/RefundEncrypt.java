package com.mobiversa.payment.util;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

public class RefundEncrypt {

	@SuppressWarnings("nls")
	public static String TDES(String src, String key)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes());
		String encryptedValue = Base64.getEncoder().encodeToString(b);

		return encryptedValue;
	}

	@SuppressWarnings("nls")
	public static String tngSignData(String pvtKeyFileName, String dataToSign) throws Exception {

		Security.addProvider(new BouncyCastleProvider());

		try (PEMReader pemReader = new PEMReader(new FileReader(pvtKeyFileName))) {
			KeyPair pair = (KeyPair) pemReader.readObject();

			PrivateKey privateKey = pair.getPrivate();

			Signature signature = Signature.getInstance("SHA1withRSA", "BC");
			signature.initSign(privateKey);
			signature.update(dataToSign.getBytes());
			byte[] signatureBytes = signature.sign();
			return byteArrayToHexString(signatureBytes);
		}
	}

	public static String byteArrayToHexString(byte b[]) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

}
