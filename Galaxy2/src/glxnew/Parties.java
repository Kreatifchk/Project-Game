package glxnew;

public class Parties {
	
	Empery emp;
	
	public Parties(int empId) {
		emp = Game.emp.get(empId);
		points(); //Изм. влияние партии в зависимости от увел. или уменьшения терр.
		balancing(); //Изм. влияния всех партий, чтоб общее не превышало 100
	}
	
	private void points() {
		if (emp.countP > 0) {
			if (emp.countP < emp.count) {
				//Если кол-во терр. увеличилось
				if (emp.parties.get(emp.partyP).influence < 98) {
					//Увеличивать влияние пока оно не достигнет 98
					int difference = emp.count - emp.countP; //Сколько новых территорий
					if (difference / 5 == 0) {
						emp.parties.get(emp.partyP).influence += 1;
					} else {
						emp.parties.get(emp.partyP).influence += difference / 5;
					}
					//Если вдруг вышло за 98 
					if (emp.parties.get(emp.partyP).influence > 98) {
						emp.parties.get(emp.partyP).influence = 98;
					}
				}
			} else {
				//Если уменьшилось
				if (emp.parties.get(emp.partyP).influence > 1) {
					int difference = emp.countP - emp.count; //Сколько территорий потеряно
					if (difference / 5 == 0) {
						emp.parties.get(emp.partyP).influence -= 1;
					} else {
						emp.parties.get(emp.partyP).influence -= difference / 5;
					}
					//Если вдруг меньше чем 1
					if (emp.parties.get(emp.partyP).influence < 1) {
						emp.parties.get(emp.partyP).influence = 1;
					}
				}
			}
			emp.countP = emp.count; //Обновляем значение
		} else {
			//Если значение еще не назначено (в начале существования империи)
			emp.countP = emp.count;
		}
	}
	
	Party partyP;
	private void balancing() {
		partyP = emp.parties.get(emp.partyP);
		//Если поле еще не заполнено (в начале игры)
		if (partyP.influenceP == 0) {
			partyP.influenceP = partyP.influence;
		} else {
			if (partyP.influence > partyP.influenceP) {
				//Влияние увеличилось
				int difference = partyP.influence - partyP.influenceP;
				//Разница во влиянии прошлого хода и текущего
				balancing(difference, true);
			} else {
				//Влияние уменьшилось
				int difference = partyP.influenceP - partyP.influence;
				balancing(difference, false);
			}
			//Обновляем значение
			partyP.influenceP = partyP.influence;
		}
		//Проверяем не надо ли изменить правящую партию
		if (emp.parties.get(0).influence > emp.parties.get(1).influence
				& emp.parties.get(0).influence > emp.parties.get(2).influence) {
			emp.partyP = 0;
		} else if (emp.parties.get(1).influence > emp.parties.get(0).influence
				& emp.parties.get(1).influence > emp.parties.get(2).influence) {
			emp.partyP = 1;
		} else if (emp.parties.get(2).influence > emp.parties.get(0).influence
				& emp.parties.get(2).influence > emp.parties.get(1).influence) {
			emp.partyP = 2;
		}
	}
	
	//Вспомогательный метод
	private void balancing(int diff, boolean inc) {
		if (inc = true) {
			//Уменьшаем влияние двух других партий
			while (diff > 0) {
				System.out.println("rpt" + emp.id + " " + diff);
				for (int i = 0; i < emp.parties.size(); i++) {
					if (i != emp.partyP & diff > 0 &
							emp.parties.get(i).influence > 1) {
						emp.parties.get(i).influence--;
						diff--;
					}
				}
			}
		} else {
			//Увеличиваем влияние двух других партий
			while (diff > 0) {
				for (int i = 0; i < emp.parties.size(); i++) {
					if (i != emp.partyP & diff > 0 &
							emp.parties.get(i).influence < 98) {
						emp.parties.get(i).influence++;
						diff--;
					}
				}
			}
		}
	}
	
}
