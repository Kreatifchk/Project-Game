package warriors;

import strategy.Images;
import strategy.TypeArmy;

public class Cavalry extends TypeArmy {
	
	public Cavalry(int count) {
		cost = 300;
		
		force = 12;
		speedAttack = 7;
		
		setCount(count);
		
		name = "Лучник";
		
		icon = Images.cavalry;
	}
	
}
