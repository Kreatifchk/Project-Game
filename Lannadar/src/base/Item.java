package base;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import heroPanel.HeroPanelBag;
import initialize.InitFont;
import inventory.InventList;

@SuppressWarnings("serial")
public class Item extends JLabel implements MouseListener {
	
	int id, location, count;
	public int idInvent[];
	int mX, mY;
	String icon;
	
	/** 
	 * @param id - ID итема
	 * @param location - ID локации где будет спавнится данный item
	 * @param count - количество которое будет спавнится
	 * @param icon - изображение
	 * @param idInvent - ид этого предмета в инвентаре
	 */
	public Item(int id, int location, int count, String icon, int... idInvent) {
		this.id = id;
		this.location = location;
		this.count = count;
		this.icon = icon;
		this.idInvent = idInvent;
		addMouseListener(this);
	}
	
	ItemPane ip;
	boolean lock; //Сбор предмета не произойдет пока окно не закрыто
	//Сбор предмета
	private void collect() {
		if (lock != true) {
			Game.lock = true;
			lock = true;
			
			JProgressBar jpb = new JProgressBar();
			jpb.setMaximum(100);
			jpb.setMinimum(0);
			jpb.setValue(0);
			jpb.setStringPainted(true);
			jpb.setBounds((Game.mainPane.getWidth()-200)/2,
					(Game.mainPane.getHeight()-30)/2, 200, 30);
			Game.mainPane.add(jpb, new Integer(300));
			new SwingWorker<Object, Integer>() {
				@Override
				protected Object doInBackground() throws Exception {
					for (int i = 0; i < 200; i++) {
						publish(2);
						Animation.sleep(10);
					}
					Game.mainPane.remove(jpb);
					
					Game.mainPane.add(ip = new ItemPane(idInvent), new Integer(300));
					return null;
				}
				@Override
				protected void process(List<Integer> values) {
					jpb.setValue(jpb.getValue() + values.get(0));
				}
			}.execute();
		}
	}
	
	private void qwestTest() {
		int take;
		for (int i = 0; i < Game.takeQwests.length; i++) {
			if (Game.takeQwests[i] != -1) {
				//Если есть взятый квест
				take = Game.takeQwests[i];
				if (Game.qwest[take].idItem != null) {
					//Если квест не собирательный
					for (int j = 0; j < Game.qwest[take].idItem.length; j++) {
						//Пробегает по всем целям квеста (даже если одна)
						if (Game.qwest[take].idItem[j] == id) {
							//Если подобранный предмет сопадает с целью
							progressUp(take, j);
						}
					}
				}
			}
		}
	}
	
	private void progressUp(int take, int j) {
		if (Game.qwest[take].progress[j] < Game.qwest[take].count[j]) {
			//Если собрано таких целей меньше чем надо в квесте, увеличить прогресс
			Game.qwest[take].progress[j]++;
				if (Arrays.equals(Game.qwest[take].progress, Game.qwest[take].count)) {
				//И еще раз проверяешь и если это максимум то поменять статус
				Game.qwest[take].status = 3;
				sign(Game.qwest[take].id);
				QwestCollect.clearBag(idInvent);//Уберет итемы из инвертаря
			}
		}
	}
	
	private void sign(int id) {
		for (int i = 0; i < Game.npc.length; i++) {
			if (Game.npc[i].qwest != null) {
				for (int j = 0; j < Game.npc[i].qwest.length; j++) {
					if (Game.qwest[Game.npc[i].qwest[j]].status == 3) {
						SignQwest.sign(i);
						break;
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent a) {
		//Клик по предмету в мире (на карте)
		if ((Game.pl.mX+1 == mX & Game.pl.mY == mY) || (Game.pl.mX-1 == mX & Game.pl.mY == mY)
				|| (Game.pl.mX == mX & Game.pl.mY+1 == mY) || (Game.pl.mX == mX & Game.pl.mY-1 == mY)) {
			collect();
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent a) {
		if (a.getSource() == ip) {
			int x = a.getX(), y = a.getY();
			if (y >= 0 & y < 40) {
				//Подскветка кнопки "закрыть"
				ip.exitP = true;
			}
			if (y >= 167) {
				//Подсветка кнопки "Взять все"
				ip.getP = true;
			}
			if (y > 41 & y < 166) {
				int yPoint = y / 42 - 1, xPoint = x / 44;
				int numberP = 2 * yPoint + xPoint + yPoint;
				int length = idInvent.length;
				if (numberP < length) {
					if (idInvent[numberP] > -1 
							&& HeroPanelBag.take(idInvent[numberP], 1) == true) {
						Game.mapx[mX][mY].remove(Item.this);
						qwestTest();
						idInvent[numberP] = -2;
					} 
				}
			}
		}
	}
	@Override
	public void mouseReleased(MouseEvent a) {
		try {
			if (ip.getP == true) {
				//Кнопка - взять все
				boolean succes = true;
				for (int i = 0; i < idInvent.length; i++) {
					if (idInvent[i] > -1 && HeroPanelBag.take(idInvent[i], 1) == true) {
						qwestTest();
						idInvent[i] = -2;
					} else {
						//Если не может собрать предмет
						succes = false;
						break;
					}
				}
				if (succes == true) {
					Game.mainPane.remove(ip);
					ip = null;
					lock = false;
					Game.lock = false;
				}
				Game.mapx[mX][mY].remove(Item.this);
			}
			
			if (ip.exitP == true) {
				Game.mainPane.remove(ip);
				ip = null;
				lock = false;
				Game.lock = false;
			}
			ip.getP = false;
			ip.exitP = false;
		} catch (NullPointerException e) {
		}
	}
	
	public class ItemPane extends JLabel {
		//int[] item;
		boolean exitP, getP; //Подсветка этих областей когда нажата кнопка
		public ItemPane(int... item) {
			//Аргумент item - предметы которые лежат в мобе/предмете
			setBounds((Game.mainPane.getWidth() - 132) /2,
					(Game.mainPane.getHeight() - 191) / 2, 132, 207);
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
			addMouseListener(Item.this);
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(150, 75, 0));
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setStroke(new BasicStroke(3));
			g2d.setColor(Color.black);
			//Отрисовка таблицы
			int y = 39;
			for (int i = 0; i < 4; i++) {
				g2d.drawLine(0, y, getWidth(), y);
				y += 42;
			}
			g2d.drawLine(42, 39, 42, getHeight()-42);
			g2d.drawLine(86, 39, 86, getHeight()-42);
			//Надписи
			g2d.setFont(InitFont.smw.deriveFont(17F));
			g2d.drawString("Закрыть", 18, 25);
			g2d.drawString("Взять все", 12, 190);
			//Подсветка при нажатии
			if (exitP == true) {
				g2d.setColor(new Color(50, 34, 22, 56));
				g2d.fillRect(0, 0, getWidth(), 38);
			}
			if (getP == true) {
				g2d.setColor(new Color(50, 34, 22, 56));
				g2d.fillRect(0, getHeight()-42, getWidth(), 38);
			}
			//Отрисовка самих предметов
			int x = 0;
			y = 41;
			for (int i = 0; i < idInvent.length; i++) {
				if (idInvent[i] != -1) {
					if (idInvent[i] != -2) {
						//Чтоб при взятии этого предмета картинка следующего не перемещалась
						Image icon = InventList.inventory.get(idInvent[i]).icon;
						g2d.drawImage(icon, x, y, null);
					}
					x += 44;
					if (x > 122) {
						x = 38;
						y += 42;
					}
				}
			}
		}
	}
	
}
