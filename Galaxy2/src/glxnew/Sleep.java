package glxnew;

public class Sleep {
	
	//git
	
	public static void sleep(int sl) {
		try {
			Thread.sleep(sl);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
