package rpg;

import javax.swing.ImageIcon;

public class TilesImage {
	
	//Улица
	static ImageIcon grass, grassFire, three, water, mount,
	brickWallP, brickWall, three2, water1, grassDark, threeDark, threeDark2, well;
	
	//Дороги
	static ImageIcon path, brickPath, brickPathG, bridge;
	
	//Здания
	static ImageIcon home01, home02, home03, homeFire;
	
	//Внутри дома
	static ImageIcon homeWall, flooring, homeWallRigth, homeWallLeft, flooringRigth,
	flooringLeft, voidI, window;
	
	public TilesImage() {
		grass = new ImageIcon(getClass().getResource("res/Tiles/grass.png"));
		grassFire = new ImageIcon(getClass().getResource("res/Tiles/Animate/GrassFire/1.png"));
		three = new ImageIcon(getClass().getResource("res/Tiles/three.png"));
		water  = new ImageIcon(getClass().getResource("res/Tiles/water.png"));
		mount  = new ImageIcon(getClass().getResource("res/Tiles/mount.png"));
		brickWallP = new ImageIcon(getClass().getResource("res/Tiles/brickWallP.png"));
		brickWall = new ImageIcon(getClass().getResource("res/Tiles/brickWall.png"));
		three2 = new ImageIcon(getClass().getResource("res/Tiles/three2.png"));
		water1 = new ImageIcon(getClass().getResource("res/Tiles/water1.png"));
		grassDark = new ImageIcon(getClass().getResource("res/Tiles/grassDark.png"));
		threeDark = new ImageIcon(getClass().getResource("res/Tiles/threeDark.png"));
		threeDark2 = new ImageIcon(getClass().getResource("res/Tiles/three2Dark.png"));
		well = new ImageIcon(getClass().getResource("res/Tiles/well.png"));
		
		brickPath  = new ImageIcon(getClass().getResource("res/Tiles/brickPath.png"));
		brickPathG  = new ImageIcon(getClass().getResource("res/Tiles/brickPathG.png"));
		bridge  = new ImageIcon(getClass().getResource("res/Tiles/bridge.png"));
		path = new ImageIcon(getClass().getResource("res/Tiles/path.png"));
		
		home01 = new ImageIcon(getClass().getResource("res/Tiles/TilesBuilding/home01.png"));
		home02 = new ImageIcon(getClass().getResource("res/Tiles/TilesBuilding/home02.png"));
		home03 = new ImageIcon(getClass().getResource("res/Tiles/TilesBuilding/home03.png"));
		homeFire = new ImageIcon(getClass().getResource("res/Tiles/TilesBuilding/homeFire.png"));
		
		flooring = new ImageIcon(getClass().getResource("res/Tiles/flooring.png"));
		homeWall = new ImageIcon(getClass().getResource("res/Tiles/HomeWall.png"));
		homeWallRigth = new ImageIcon(getClass().getResource("res/Tiles/HomeWallRight.png"));
		flooringRigth = new ImageIcon(getClass().getResource("res/Tiles/flooringRigth.png"));
		homeWallLeft = new ImageIcon(getClass().getResource("res/Tiles/HomeWallLeft.png"));
		flooringLeft = new ImageIcon(getClass().getResource("res/Tiles/flooringLeft.png"));
		voidI = new ImageIcon(getClass().getResource("res/Tiles/void.png"));
		window = new ImageIcon(getClass().getResource("res/Tiles/window.png"));
	}
	
}
