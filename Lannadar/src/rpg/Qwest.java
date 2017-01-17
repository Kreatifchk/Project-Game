package rpg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Qwest implements Serializable {
	
	String name, textN, textK, request;
	/*
	 * name - ��������
	 * textN - �������� � ������
	 * textK - �������� ��� �����
	 * request - ���������� �������
	 */
	
	int id, lastId, status, exp;
	/*
	 * id - ����� ������ (� ����)
	 * lastId - ����� ����� ���������� ��������� ���� �������� ����
	 * status - 0 (�� ��������), 1 (�� ����), 2 (����, �� �������),
	 * 3 (�������, �� ����), 4 (������� � ����)
	 * exp - ���-�� �����
	 */
	
	String nameMonster;
	int count, progress; //������� ���������� ����� � ������� ��� �����
	int idNPC; //� ����� NPC ���������� ����������
	
	public Qwest(int id, String name, String textN, String textK, String request, 
			int status, int exp) {
		this.id = id;
		this.name = name;
		this.textN = textN;
		this.textK = textK;
		this.request = request;
		this.status = status;
		this.exp = exp;
	}
	
	public Qwest(int id, String name, String textN, String textK, String request, 
			int status, int exp, int lastId) {
		this.id = id;
		this.name = name;
		this.textN = textN;
		this.textK = textK;
		this.request = request;
		this.lastId = lastId;
		this.status = status;
		this.exp = exp;
	}
	
}
