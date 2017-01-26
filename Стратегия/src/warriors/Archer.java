package warriors;

import strategy.Images;
import strategy.TypeArmy;

public class Archer extends TypeArmy {
	
	public Archer(int count) {
		cost = 150;
		
		force = 8;
		speedAttack = 7;
		
		speed = 2;
		
		setCount(count);
		
		name = "Лучник";
		
		icon = Images.archer;
	}
	
}
