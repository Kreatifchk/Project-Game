package glxnew;

/** Класс - партия (технический) */
public class Party {
	
	//Сделать изменение влияния в зависимости от успехов партии
	
	String name;
	int nameD; //Длина имена (для переноса при отображении)
	
	/** Влияние в народе */
	int influence;
	/** Влияние в пролшом ходе */
	int influenceP;
	//Партия с наибольшим влиянем - лидер страны
	
	public Party(String name) {
		this.name = name;
		if (name.length() > 10) {
			nameD = 2;
		} else {
			nameD = 1;
		}
	}
	
}
