package base;

public class LocationList {
	
	public static Location[] locations = new Location[9];
	
	public LocationList() {
		locations[0] = new Location(-1, "null", -1);
		locations[1] = new Location(0, "Деревенский дом", 0);
		locations[2] = new Location(0, "Деревня Маррачар: центр", 0);
		locations[3] = new Location(0, "Деревня Маррачар: восток", 0);
		locations[4] = new Location(0, "Деревня Маррачар: север", 0);
		locations[5] = new Location(0, "Деревня Маррачар: север", 0);
		locations[6] = new Location(0, "Алвиин: жилой квартал", 1);
	}
	
}
