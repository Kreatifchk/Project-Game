package rpg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class ExpPanel extends JLabel {
	
	Image expI = new ImageIcon(getClass().getResource("res/exp.png")).getImage();
	
	int persent = 0, persentOld = 0; //текущий и старый процент exp 
	
	int exp, var; //Старое значение exp и на сколько оно выросло
	
	int x = 2; //На каком месте остановилась полоска
	
	int coor;
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		persent = Maths.whatPersentage(Game.pl.exp, Game.pl.maxExp);
		x = 2;
		//Если опыт прибавился
		if (exp < Game.pl.exp) {
			persentOld = Maths.whatPersentage(exp, Game.pl.maxExp);
			//Старый опыт в процентах
			var = persent - persentOld;
			//Разница между старым и новым опытом (в процентах)
			exp = Game.pl.exp;
			//Сохраняем новый опыт
			//Открываем второй поток который будет управлять плавной отрисовкой
			new SwingWorker<Void, Void>() {
				public Void doInBackground() {
					for (int j = 0; j < var; j++) {
						persentOld++;
						Animation.sleep(20);
					}
					return null;
				}
			}.execute();
		}
		//Если получил новый уровень
		if (exp > Game.pl.exp) {
			//Обнуляет полоску опыта, а дальше все делает условие сверху
			var = 100 - persentOld;
			new SwingWorker<Void, Void>() {
				public Void doInBackground() {
					for (int j = 0; j < var; j++) {
						persentOld++;
						Animation.sleep(20);
					}
					persentOld = 0;
					exp = 0;
					return null;
				}
			}.execute();
		}
		//Отрисовка
		for (int i = 0; i < persentOld; i++) {
			g2d.drawImage(expI, x, 1, null);
			x++;
		}
		x = 2;
	}
	
}
