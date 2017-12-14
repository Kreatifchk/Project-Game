package base;

public class Location {
	
	int id, groupId;
	String name, music;
	
	/**
	 * @param id - номер локации
	 * @param name - названии локации
	 * @param groupId - ид группы локаций
	 * @param music - музыка
	 */
	public Location(int id, String name, int groupId, String music) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
		this.music = music;
	}
	/**
	 * @param id - номер локации
	 * @param name - названии локации
	 * @param groupId - ид группы локаций
	 */
	public Location (int id, String name, int groupId) {
		this.id = id;
		this.name = name;
		this.groupId = groupId;
	}
	
}
