package glxnew;

public class Clear {
	
	//git
	
	public Clear(int id) {
		//Подсчет кол-ва территорий
		Game.emp.get(id).count = 0;
		for (int i = 0; i < Game.pole.length; i++) {
			for (int j = 0; j < Game.pole[0].length; j++) {
				if (Game.pole[i][j].owner == id) {
					Game.emp.get(id).count++;
				}
			}
		}
		//Удаление империй
		if (Game.emp.get(id).count <= 0) {
			//Клетка более не столица (вообще надо это делать уже при ее захвате)
			//int xCap = Game.emp.get(id).capital.x;
			//int yCap = Game.emp.get(id).capital.y;
			//Game.pole[xCap][yCap].capital = false;
			//Удаление империи
			Game.emp.remove(id);
		}
		//Перезапись id (из-за удаления смещаются)
		for (int i = 0; i < Game.emp.size(); i++) {
			Game.emp.get(i).oldId = Game.emp.get(i).id;
			Game.emp.get(i).id = i;
		}
		//Перезапись id владельцев
		for (int i = 0; i < Game.pole.length; i++) {
			for (int j = 0; j < Game.pole[0].length; j++) {
				for (int z = 0; z < Game.emp.size(); z++) {
					if (Game.emp.get(z).oldId == Game.pole[i][j].owner) {
						Game.pole[i][j].owner = Game.emp.get(z).id;
					}
					Game.pole[i][j].newP = false; //Под конец хода делает все ново
					//захваченные клетки, рабочими (могут атаковать)
				}
			}
		}
	}
	
}
