package base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LocationFile {
	
	static Scanner s; //Сканер читающий файлы с локациями
	
	public static void readFile() {
		while(s.hasNext()) {
			for (int i = 0; i < Game.mapx[0].length; i++) {
				for (int j = 0; j < Game.mapx.length; j++) {
					Game.map[j][i] = s.nextInt(); 
				}
			}
		}
	}

	public static boolean openFile(InputStream f) {
		String hash = null;
		try {
			s = new Scanner(Game.f2);
			hash = getHash();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Проверяет не изменен ли файл
		if (!hash.equals(HashList.hashList[Game.currentLocation])) {
			return false;
		} else {
			return true;
		}
	}
	
	private static String getHash() {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] dataBytes = new byte[1024];
			int bytesRead;
			InputStream is = Game.class.getResourceAsStream
					("res/levels/" + Game.currentLocation + ".txt");
			while ((bytesRead = is.read(dataBytes)) > 0) {
				md.update(dataBytes, 0, bytesRead);
			}
			byte[] mdBytes = md.digest();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mdBytes.length; i++) {
				sb.append(Integer.toString((mdBytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			hash = sb.toString();
			
			is.close();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hash;
	}
	
}
