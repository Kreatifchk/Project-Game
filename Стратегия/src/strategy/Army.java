package strategy;

import java.util.ArrayList;

import javax.swing.JLabel;

/** ����� ��� ����� �� ����� */
@SuppressWarnings("serial")
public class Army extends JLabel {
	
	int id;
	int owner; //��������
	
	ArrayList<TypeArmy> arm = new ArrayList<TypeArmy>(); //���� �������
	
	public Army() {
	}
	
}
