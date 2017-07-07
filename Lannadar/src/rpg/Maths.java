package rpg;

public class Maths {
	
	public static int persentageNumber(int number, int persentage) {
		//Вычисляет процент от числа
		double a = (double) number;
		a = a / 100;
		a = a * persentage;
		int b = (int) Math.round(a);
		return b;
	}
	
	public static int whatPersentage(int number1, int number2) {
		//Какой процент составляет одно число от другого
		double a, b;
		a = (double) number2 / 100;
		b = number1 / a;
		int c = (int) Math.round(b);
		return c;
	}
	
}
