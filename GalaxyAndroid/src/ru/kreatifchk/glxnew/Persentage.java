package ru.kreatifchk.glxnew;

public class Persentage {
	
	/* Процент от числа (пример 70% от 500)
	Аргументы: pers - процент, number - число */
	public static int pers(int pers, int number) {
		double retNumb = (double) pers / 100 * number;
		return (int) retNumb;
	}
	
}
