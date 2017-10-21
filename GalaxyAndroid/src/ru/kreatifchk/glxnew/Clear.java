package ru.kreatifchk.glxnew;

import android.widget.*;

public class Clear {
	
	int id;
	
	public Clear(int id) {
		this.id = id;
		//Подсчет кол-ва территорий
		Game.emp.get(id).count = 0;
		for (int j = 0; j < Game.pole[0].length; j++) {
			for (int i = 0; i < Game.pole.length; i++) {
				if (Game.pole[i][j].owner == id) {
					Game.emp.get(id).count++;
					Game.pole[i][j].newP = false; //Под конец хода делает все ново
					//захваченные клетки, рабочими (могут атаковать)
				}
			}
		}
		
		if (Game.emp.get(id).count <= 0) {
			//Потеря статуса столицы
			int capX = Game.emp.get(id).capital.x;
			int capY = Game.emp.get(id).capital.y;
			Game.pole[capX][capY].capital = false;
			//Удаление империи
			Game.emp.remove(id);
			//Перезапись id (из-за удаления смещаются)
			for (int i = 0; i < Game.emp.size(); i++) {
				Game.emp.get(i).oldId = Game.emp.get(i).id;
				Game.emp.get(i).id = i;
			}
			for (int j = 0; j < Game.pole[0].length; j++) {
				for (int i = 0; i < Game.pole.length; i++) {
					for (int z = 0; z < Game.emp.size(); z++) {
						if (Game.emp.get(z).oldId == Game.pole[i][j].owner) {
							Game.pole[i][j].owner = Game.emp.get(z).id;
						}
					}
				}
			}
		}
	}
	
}
