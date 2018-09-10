package ru.kreatifchk.tools;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/** Класс шифровки AES256 */
public class Aes256 {
	
	public static SecretKey secretKey;
	
	public Aes256() {
		/*KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(256);
		Генерация случайного ключа
		this.secretKey = keyGenerator.generateKey();*/

		//Ключ подерживает 16, 24 или 32 байта (1 символ - 1 байт)
		String keyString = "LannadarSecret00";
		secretKey = new SecretKeySpec(keyString.getBytes(), "AES");
	}

	public byte[] makeAes(byte[] rawMessage, int cipherMode){
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(cipherMode, secretKey);
			byte [] output = cipher.doFinal(rawMessage);
			return output;

		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
