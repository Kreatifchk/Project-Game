package rpg;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Tiles extends JLabel {
	
	int id;
	
	boolean solid;
	
	boolean portal;
	
	boolean busy; //�����
	
	boolean anim = false;
	
	int number; //����� ������������� �� (��� ��������)
	
	int currentFrame = 0; //������� ���� (��� ��������)
	
	public Tiles(int id,boolean solid) {
		this.id = id;
		this.solid = solid;
	}
	
	public Tiles(int id, boolean solid, boolean anim, int number) {
		this.id = id;
		this.solid = solid;
		this.anim = anim;
		this.number = number;
	}
	
}
