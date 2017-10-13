package glxnew;

import java.util.Random;

/** Генератор имен и названий */
public class GenNamesParties {
	
	static Random r = new Random();
	
	//Одно слово
	static String[] oneWord = {"Яблоко", "Солнце", "Город", "Могущество", "Решение",
			"Платформа", "Небо", "Империя", "Облако", "Кинжал", "Свет"};
	
	//Два слова
	//Единственное число, мужской род
	static String[] eC_mR_P = {"Единый", "Грозный", "Туманный", "Волшебный",
			"Золотой", "Первый"};
	static String[] eC_mR_S = {"фронт", "дом", "крест", "удар"};
	
	//Единственное число, женский род
	static String[] eC_jR_P = {"Единая", "Грозная", "Туманная", "Волшебная",
			"Золотая", "Первая"};
	static String[] eC_jR_S = {"страна", "родина", "семья", "cила"};
	
	//Единственное число, средний род
	static String[] eC_sR_P = {"Единое", "Грозное", "Туманное", "Волшебное",
			"Золотое", "Первое"};
	static String[] eC_sR_S = {"небо", "время", "решение", "сердце"};
	
	static String[] mC_S = {"Освободители", "Хранители", "Защитники"};
	static String[] mC_S2 = {"вселенной", "верности", "терпения"};
	
	public GenNamesParties() {
	}
	
	protected static String genName() {
		String name = "";
		int g = r.nextInt(2);
		if (g == 0) {
			//Одно слово
			g = r.nextInt(oneWord.length);
			name = oneWord[g];
		} else {
			//Два слова (еще разветвления)
			g = r.nextInt(4);
			if (g == 0) {
				//Ед. число, муж. род
				g = r.nextInt(eC_mR_P.length);
				name = eC_mR_P[g];
				g = r.nextInt(eC_mR_S.length);
				name = name + " " + eC_mR_S[g];
			} else if (g == 1) {
				//Ед. число, жен. род
				g = r.nextInt(eC_jR_P.length);
				name = eC_jR_P[g];
				g = r.nextInt(eC_jR_S.length);
				name = name + " " + eC_jR_S[g];
			} else if (g == 2){
				//Ед. число, ср. род
				g = r.nextInt(eC_sR_P.length);
				name = eC_sR_P[g];
				g = r.nextInt(eC_sR_S.length);
				name = name + " " + eC_sR_S[g];
			} else {
				//Множ. число
				g = r.nextInt(mC_S.length);
				name = mC_S[g];
				g = r.nextInt(mC_S2.length);
				name = name + " " + mC_S2[g];
			}
		}
		return name;
	}
	
}
