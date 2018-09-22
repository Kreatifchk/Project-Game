package ru.kreatifchk.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.swing.JOptionPane;

import org.nustaq.serialization.FSTObjectInput;

import ru.kreatifchk.editor.Map;
import ru.kreatifchk.editor.PointEditor;
import ru.kreatifchk.tools.Aes256;

/** Загружает карту из файла */
public class LoadMap {
	
	@SuppressWarnings("unchecked")
	public LoadMap(String location) {
		PointEditor[][] p = null;
		String password = null;
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(new File("data/maps/" + location + ".lnd"));
			buffer = new byte[fis.available()];
			fis.read(buffer, 0, fis.available());
			fis.close();
					
			byte[] shifr = new Aes256().makeAes(buffer, Cipher.DECRYPT_MODE);
			File tempFile = null;
					
			tempFile = File.createTempFile("serial1", ".lnd");
			FileOutputStream fw = new FileOutputStream(tempFile);
			fw.write(shifr);
			fw.close();
			
			FSTObjectInput ois = new FSTObjectInput(new FileInputStream(tempFile));
					
			p = (PointEditor[][]) ois.readObject();
			Map.monsters = (ArrayList<Monster>) ois.readObject();
			password = (String) ois.readObject();
			ois.close();
					
			tempFile.deleteOnExit();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
				
		//Проверяем не подменили ли карту
		if (password.equals("Miku")) {
			//Загрузка непосредственно карты
			Game.map = new MapPoint[p.length][p[0].length];
			for (int i = 0; i < Game.map[0].length; i++) {
				for (int j = 0; j < Game.map.length; j++) {
					Game.map[j][i] = new MapPoint();
					Game.map[j][i].solid = TilesList.tiles[p[j][i].number].solid;
					Game.map[j][i].number = p[j][i].number;
					Game.map[j][i].transfer = p[j][i].dirTrans;
					Game.map[j][i].xTrans = p[j][i].xTrans;
					Game.map[j][i].yTrans = p[j][i].yTrans;
					Game.map[j][i].newLocation = p[j][i].transition;
				}
			}
			p = null;
		} else {
			JOptionPane.showMessageDialog(Game.mainPane, "\u041e\u0448\u0438\u0431\u043a\u0430 "
					+ "\u043e\u0442\u043a\u0440\u044b\u0442\u0438\u044f \u043a\u0430\u0440\u0442\u044b\u0021");
		}
	}
	
}
