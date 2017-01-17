package rpg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**����� - ������ ������� */
public class QwestList {
	
	//����� ����������� ������ ������ ������� � ������ Game
	
	public QwestList() {
		//���������: id, name, textN, textK, request, status, exp, lastId(�����������)
		//��� ��������: nameMonster, number � ����� � ��� �� �������
		//��� ��������� idNPC � ����� �����
		Game.qwest[0] = new QwestTalk(1, 0, name(0), textN(0), textK(0), request(0), 1, 10);
		Game.qwest[1] = new QwestTalk(2, 1, name(1), textN(1), textK(1), request(1), 1, 10, 0);
		Game.qwest[2] = new QwestTalk(1, 2, name(2), textN(2), textK(2), request(2), 1, 10, 1);
		Game.qwest[3] = new QwestKilling("�����", 3, 3, name(3), textN(3), textK(3), request(3), 1, 20, 2);
		Game.qwest[4] = new QwestTalk(3, 4, name(4), textN(4), textK(4), request(4), 1, 14, 3);
		Game.qwest[5] = new QwestKilling("�����", 2, 5, name(5), textN(5), textK(5), request(5), 1, 25, 4);
		Game.qwest[6] = new QwestKilling("������", 3, 6, name(6), textN(6), textK(6), request(6), 1, 35, 5);
		Game.qwest[7] = new QwestTalk(5, 7, name(7), textN(7), textK(7), request(7), 1, 40, 6);
		Game.qwest[8] = new QwestKilling("���������", 5, 8, name(8), textN(8), textK(8), request(8), 1, 60, 7);
		Game.qwest[9] = new QwestTalk(7, 9, name(9), textN(9), textK(9), request(9), 1, 40, 8);
		Game.qwest[10] = new QwestTalk(999, 10, name(10), textN(10), textK(10), request(10), 1, 999, 9);
	}
	
	private String name(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String name = "";
		try {
			sc = new Scanner(f);
			name = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	private String textN(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String textN = "";
		try {
			sc = new Scanner(f);
			sc.nextLine();
			textN = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return textN;
	}
	
	private String textK(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String textK = "";
		try {
			sc = new Scanner(f);
			sc.nextLine();
			sc.nextLine();
			textK = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return textK;
	}
	
	private String request(int id) {
		File f = new File("res/Qwests/" + id);
		Scanner sc;
		String request = "";
		try {
			sc = new Scanner(f);
			sc.nextLine();
			sc.nextLine();
			sc.nextLine();
			request = sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return request;
	}
	
}
