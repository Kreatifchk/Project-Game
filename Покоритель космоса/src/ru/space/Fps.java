package ru.space;

public class Fps implements Runnable {
	
	public static int fps;
	
	static long time = System.currentTimeMillis();
	
	@Override
	public void run() {
		/*while(true) {
			currentFps++;
			if(System.currentTimeMillis() - time >= 1000) {
				fps = currentFps;
				currentFps = 0;
				time = System.currentTimeMillis();
			}
			//System.out.println(fps);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		//long time = System.currentTimeMillis();
		while(true) {
			fps++;
			if (System.currentTimeMillis() - time >= 1000) {
				//System.out.println(fps);
				Game.fps.setText("" + fps);
				time = System.currentTimeMillis();
				fps = 0;
			}
			Sleep.sleep(15);//17
		}
	}

}
