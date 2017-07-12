package base;

public class Print {
	
	public static void print(Object o) {
		String cl = o.getClass().toString();
		System.out.println(cl);
	}
	
	public static void print(Object o, String s) {
		String cl = o.getClass().toString();
		System.out.println(cl + " : " + s);
	}
	
}
