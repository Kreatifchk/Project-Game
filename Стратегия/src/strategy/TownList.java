package strategy;

public class TownList {
	
	public static void list() {
		//������ ����� - y, ������ - x
		Game.town.add(new Town(Town.CENTER, 2, 2).setNam("��������").setOwner(0));
		Game.town.add(new Town(Town.CENTER, 5, 2).setNam("������"));
		Game.town.add(new Town(Town.UP_LEFT, 0, 0).setNam("���").setOwner(1));
		Game.town.add(new Town(Town.UP_LEFT, 0, 1).setNam("�����").setOwner(1));
		Game.town.add(new Town(Town.UP_LEFT, 1, 0).setNam("�����������").setOwner(2));
		Game.town.add(new Town(Town.UP_RIGTH, 0, 14).setNam("�����"));
		Game.town.add(new Town(Town.UP_RIGTH, 0, 13).setNam("�����"));
		Game.town.add(new Town(Town.UP_RIGTH, 1, 14).setNam("������������"));
		Game.town.add(new Town(Town.DOWN_RIGTH, 14, 0).setNam("���������"));
		
		add();
	}
	
	private static void add() {
		for (int i = 0; i < Game.town.size(); i++) {
			Game.town.get(i).setId(i);
			int x = Game.town.get(i).x, y = Game.town.get(i).y;
			Game.bcTiles[x][y].add(Game.town.get(i));
		}
	}
	
}
