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
	
	private String file = "res/Image/Tiles/";
	
	public TilesImage() {
		grass = new ImageIcon(getClass().getResource(file + "grass.png"));
		grassFire = new ImageIcon(getClass().getResource(file + "Animate/GrassFire/1.png"));
		three = new ImageIcon(getClass().getResource(file + "three.png"));
		water  = new ImageIcon(getClass().getResource(file + "water.png"));
		mount  = new ImageIcon(getClass().getResource(file + "mount.png"));
		brickWallP = new ImageIcon(getClass().getResource(file + "brickWallP.png"));
		brickWall = new ImageIcon(getClass().getResource(file + "brickWall.png"));
		three2 = new ImageIcon(getClass().getResource(file + "three2.png"));
		water1 = new ImageIcon(getClass().getResource(file + "water1.png"));
		grassDark = new ImageIcon(getClass().getResource(file + "grassDark.png"));
		threeDark = new ImageIcon(getClass().getResource(file + "threeDark.png"));
		threeDark2 = new ImageIcon(getClass().getResource(file + "three2Dark.png"));
		well = new ImageIcon(getClass().getResource(file + "well.png"));
		
		brickPath  = new ImageIcon(getClass().getResource(file + "brickPath.png"));
		brickPathG  = new ImageIcon(getClass().getResource(file + "brickPathG.png"));
		bridge  = new ImageIcon(getClass().getResource(file + "bridge.png"));
		path = new ImageIcon(getClass().getResource(file + "path.png"));
		
		home01 = new ImageIcon(getClass().getResource(file + "TilesBuilding/home01.png"));
		home02 = new ImageIcon(getClass().getResource(file + "TilesBuilding/home02.png"));
		home03 = new ImageIcon(getClass().getResource(file + "TilesBuilding/home03.png"));
		homeFire = new ImageIcon(getClass().getResource(file + "TilesBuilding/homeFire.png"));
		
		flooring = new ImageIcon(getClass().getResource(file + "flooring.png"));
		homeWall = new ImageIcon(getClass().getResource(file + "HomeWall.png"));
		homeWallRigth = new ImageIcon(getClass().getResource(file + "HomeWallRight.png"));
		flooringRigth = new ImageIcon(getClass().getResource(file + "flooringRigth.png"));
		homeWallLeft = new ImageIcon(getClass().getResource(file + "HomeWallLeft.png"));
		flooringLeft = new ImageIcon(getClass().getResource(file + "flooringLeft.png"));
		voidI = new ImageIcon(getClass().getResource(file + "void.png"));
		window = new ImageIcon(getClass().getResource(file + "window.png"));
	}
	
}
