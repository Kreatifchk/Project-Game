package strategy;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Town extends JLabel implements MouseListener {
	
	int owner; //Владелец
	
	int id;
	
	String name;
	
	int x, y; //На каком тайле
	
	ArrayList<Building> build = new ArrayList<Building>();
	ArrayList<TypeArmy> line = new ArrayList<TypeArmy>();
	
	//Констаты, определяющие в какой части тайла будет находится город
	static final LocationTile UP_LEFT = new LocationTile(0, 0),
			UP_RIGTH = new LocationTile(92, 0),
			DOWN_RIGTH = new LocationTile(92, 92),
			DOWN_LEFT = new LocationTile(0, 92),
			CENTER = new LocationTile(46, 46);
	
	public Town(LocationTile lt, int x, int y) {
		super();
		setIcon(Images.town);
		setBounds(lt.getX(), lt.getY(), 36, 36);
		this.x = x;
		this.y = y;
		addMouseListener(this);
	}
	
	public Town(LocationTile lt, int x, int y, int owner) {
		super();
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

	@Override
	public void mouseClicked(MouseEvent e) {
		RigthPanel.name = name;
		RigthPanel.townId = id;
		Game.downInf.inform.repaint();
		CenterPanel.focus = true;
		Game.downCenter.unlocked();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
}
