package ru.kreatifchk.glxnew;

public class Sleep {
	
	public static void sleep(int sl) {
		try {
			Thread.sleep(sl);
		}
		catch (InterruptedException e) {
		}
	}
	
}
