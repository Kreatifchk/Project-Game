package ru.kreatifchk.galaxy;

public class Interest {
	
	public static Integer interest(int number, int proc) {
		double c = (double) number / 100 * proc;
		c = Math.ceil(c);
		int d = (int) c;
		return d;
	}
	
	public static Integer roundMin(double number) {
		number = Math.floor(number);
		int x = (int) number;
		return x;
	}
	
	public static Integer roundMax(double number) {
		number = Math.ceil(number);
		int x = (int) number;
		return x;
	}

}
