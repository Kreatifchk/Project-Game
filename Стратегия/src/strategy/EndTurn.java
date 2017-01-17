package strategy;

/** Класс - конец хода */
public class EndTurn {
	
	public EndTurn() {
		Game.turn++;
		
		hiring();
		
		//Game.jlp.
	}
	
	//Наем войск
	private void hiring() {
		for (int i = 0; i < Game.town.size(); i++) {
			if (Game.town.get(i).line.size() > 0) {
				//Добавить в армию отряд, или если армии нет - создать ее
				int number, own;
				/* number - кол-во армий в империи (чтоб определить место след.) 
				   own - владелец город (империя такая-то) */
				if (Game.town.get(i).army != true) {
					//Если армии в городе еще нету
					Game.town.get(i).army = true;
					Game.town.get(i).idArmy = Game.emp.get(0).troop.size();
					own = Game.town.get(i).owner;
					number = Game.emp.get(own).troop.size();
					Game.emp.get(own).troop.add(new Army().setTown(i).setId(number));
					Game.emp.get(own).troop.get(number).arm.add(Game.town.get(i).line.get(0));
				} else {
					//Если армия уже существует в этом городе
					own = Game.town.get(i).owner;
					number = Game.town.get(i).idArmy; //ID армии в этом городе
					Game.emp.get(own).troop.get(number).arm.add(Game.town.get(i).line.get(0));
				}
				//Убрать из очереди отряд
				Game.town.get(i).line.remove(0);
				for (int z = 0; z < Game.town.get(i).line.size(); z++) {
					Game.town.get(i).line.get(i).lineNumber --;
				}
			}
		}
	}
	
}
