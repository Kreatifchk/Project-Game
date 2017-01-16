package strategy;

import javax.swing.ImageIcon;

/** ���� ������, �� ������ ����� ������ �������� ���������� */
public abstract class TypeArmy {
	
	@SuppressWarnings("unused")
	private int count; //���������� ������ � ������
	
	protected int cost; //��������� �����
	protected int force; //����
	protected int speedAttack; //�������� �����
	protected int lineNumber; //����� � ������� �����
	
	protected String name; //��� �����
	
	protected ImageIcon icon; //������ �����
	
	protected void setCount(int count) {
		this.count = count;
	}
	
	protected void setLineNumber(int l) {
		lineNumber = l;
	}
	
}
