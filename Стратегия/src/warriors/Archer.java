package warriors;

import strategy.Images;
import strategy.TypeArmy;

public class Archer extends TypeArmy {
	
	public Archer(int count) {
		cost = 150;
		
		force = 8;
		speedAttack = 7;
		
		setCount(count);
		
		name = "������";
		
		icon = Images.archer;
	}
	
}
