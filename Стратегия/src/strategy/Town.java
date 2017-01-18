package strategy;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class Town extends JLabel implements MouseListener {
	
	int owner; //Владелец
	
	int id;
	
	String name;
	
	int x, y; //На каком тайле
	
	boolean ent;
	
	boolean army; //Есть ли в этом городе армия
	int idArmy = -1; //Номер армии
	
	ArrayList<Building> build = new ArrayList<Building>();
	ArrayList<TypeArmy> line = new ArrayList<TypeArmy>();
	
	LocationTile locTile; //Для запросов от других классов
	
	//Констаты, определяющие в какой части тайла будет находится город
	static final LocationTile UP_LEFT = new LocationTile(0, 0),
			UP_RIGTH = new LocationTile(92, 0),
			DOWN_RIGTH = new LocationTile(92, 92),
			DOWN_LEFT = new LocationTile(0, 92),
			CENTER = new LocationTile(46, 46);
	
	public Town(LocationTile lt, int x, int y) {
		super();
		this.locTile = lt;
		setIcon(Images.town);
		setBounds(lt.getX(), lt.getY(), 36, 36);
		this.x = x;
		this.y = y;
		addMouseListener(this);
	}
	
	public Town(LocationTile lt, int x, int y, int owner) {
		super();
		this.locTile = lt;
		setIcon(Images.town);
		setBounds(x, y, 36, 36);
		this.x = x;
		this.y = y;
		this.owner = owner;
		addMouseListener(this);
	}
	
	public Town setOwner(int owner) {
		this.owner = owner;
		return this;
	}
	
	public Town setNam(String name) {
		this.name = name;
		return this;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	//Отображает всплывающую подсказку о городе
	private void informTown() {
		ent = true;
		new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				boolean z = true;
				for (int i = 0; i <= 16; i++) {
					if (ent != false) {
						Sleep.sleep(25);
					} else {
						z = false;
						break;
					}
				}
				if (z == true) {
					String name = Game.emp.get(owner).name;
					Tip inf = new Tip("<html><p align=\"center\">Владелец: " + name + "</p><html>");
					int x = Town.this.getParent().getX() + 50;
					int y = 0;
					if (Town.this.getY() == 46) {
						y = Town.this.getParent().getY() + 60;
						x += 40;
					} else {
						y = Town.this.getParent().getY() + 28;
					}
					inf.setBounds(x, y, 160, 40);
					Game.jlp.add(inf, new Integer(10));
					while (ent != false) {
						Sleep.sleep(50);
					}
					Sleep.sleep(80);
					Game.jlp.remove(inf);
					Game.jlp.repaint();
				}
				return null;
			}
		}.execute();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (owner == 0) {
			//Добавляет информацию в правую панель
			RigthPanel.name = name;
			RigthPanel.townId = id;
			Game.downInf.inform.repaint();
			//Подготавливает центральную панель
			CenterPanel.focus = true;
			CenterPanel.townId = id;
			Game.downCenter.unlocked();
			Game.downCenter.city.setColor(Game.downCenter.active);
			Game.downCenter.addPanel();
			//Указывает тайлу что ID города на которыц нажали
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		informTown();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		ent = false;
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
