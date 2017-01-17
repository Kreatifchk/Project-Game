package strategy;

/** ����� - ����� ���� */
public class EndTurn {
	
	public EndTurn() {
		Game.turn++;
		
		hiring();
		
		//Game.jlp.
	}
	
	//���� �����
	private void hiring() {
		for (int i = 0; i < Game.town.size(); i++) {
			if (Game.town.get(i).line.size() > 0) {
				//�������� � ����� �����, ��� ���� ����� ��� - ������� ��
				int number, own;
				/* number - ���-�� ����� � ������� (���� ���������� ����� ����.) 
				   own - �������� ����� (������� �����-��) */
				if (Game.town.get(i).army != true) {
					//���� ����� � ������ ��� ����
					Game.town.get(i).army = true;
					Game.town.get(i).idArmy = Game.emp.get(0).troop.size();
					own = Game.town.get(i).owner;
					number = Game.emp.get(own).troop.size();
					Game.emp.get(own).troop.add(new Army().setTown(i).setId(number));
					Game.emp.get(own).troop.get(number).arm.add(Game.town.get(i).line.get(0));
				} else {
					//���� ����� ��� ���������� � ���� ������
					own = Game.town.get(i).owner;
					number = Game.town.get(i).idArmy; //ID ����� � ���� ������
					Game.emp.get(own).troop.get(number).arm.add(Game.town.get(i).line.get(0));
				}
				//������ �� ������� �����
				Game.town.get(i).line.remove(0);
				for (int z = 0; z < Game.town.get(i).line.size(); z++) {
					Game.town.get(i).line.get(i).lineNumber --;
				}
			}
		}
	}
	
}
