package warriors;

import strategy.Images;
import strategy.TypeArmy;

public class Footman extends TypeArmy {
	
	public Footman(int count) {
		cost = 100;
		
		force = 10;
		speedAttack = 5;
		
		speed = 1;
		
		setCount(count);
		
		name = "Пехотинец";
		
		icon = Images.footman;
	}
	
}
