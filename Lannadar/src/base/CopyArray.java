package base;

public class CopyArray {
	
	public static int[] copyInt(int a[]) {
		int[] b = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			b[i] = a[i];
		}
		return b;
	}
	
}
