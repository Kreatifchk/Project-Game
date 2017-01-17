package strategy;

import java.util.ArrayList;

import javax.swing.JLabel;

/** ����� ��� ����� �� ����� */
@SuppressWarnings("serial")
public class Army extends JLabel {
	
	int id;
	int owner; //��������
	
	int town; //� ����� ������ ���������, ���� ��� ������ �� -1
	int tile; //�� ����� �����
	
	ArrayList<TypeArmy> arm = new ArrayList<TypeArmy>(); //���� �������
	
	public Army() {
	}
	
	public Army setTown(int town) {
		this.town = town;
		return this;
	}
	
	public Army setId(int id) {
		this.id = id;
		return this;
	}
	
}
