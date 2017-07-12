package base;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Animation {
	
	Image[] imAnim;
	
	Image thisCadr;
	
	String put;
	
	int i = 0;
	
	public void anim(int length, String folder) {
		imAnim = new Image[length+1];
		for (int i = 0; i <= length; i++) {
			imAnim[i] = new ImageIcon(getClass().getResource("res/Player/HeroDown2.png")).getImage();
			//imAnim[i] = new ImageIcon(getClass().getResource("res/" + folder + "/" + i + ".png")).getImage();
		}
	}
	
	public static void sleep(int s) {
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void animPlay() {
		/*for (int i = 0; i <= imAnim.length - 1; i++) {
			System.out.println("y " + i);
			thisCadr = imAnim[i];
			sleep(speed);
			if (i == imAnim.length) {
				i = 0;
			}
		}*/
		thisCadr = imAnim[i];
		if (i == imAnim.length - 1) {
			i = 0;
		} else {
			i++;
		}
	}
	
}
