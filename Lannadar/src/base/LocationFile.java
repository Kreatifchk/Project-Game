package base;

import java.io.InputStream;
import java.util.Scanner;

public class LocationFile {
	
	static Scanner s; //Сканер читающий файлы с локаци¤ми
	
	public static void readFile() {
		while(s.hasNext()) {
			for (int i = 0; i < Game.mapx[0].length; i++) {
				for (int j = 0; j < Game.mapx.length; j++) {
					Game.map[j][i] = s.nextInt(); 
				}
			}
		}
	}

	//public static void openFile(File f) {
	public static void openFile(InputStream f) {
		try {
			if (f != null) {
				s = new Scanner(f); //Для респавна после смерти
			} else {
				//Game.f2 = new File("res/levels/" + Game.currentLocation + ".txt");
				s = new Scanner(Game.f2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
