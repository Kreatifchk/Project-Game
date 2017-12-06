package initialize;

import javax.swing.ImageIcon;

public class TilesImage {
	
	//Улица
	public static ImageIcon grass, grassFire, water, mount, brickWallP, brickWall,
	water1, grassDark, well;
	
	//Деревья
	public static ImageIcon three, three2, three3, threeDark, threeDark2;
	
	//Дороги
	public static ImageIcon townRoad, path, brickPath, brickPathG, bridge;
	
	//Здания
	public static ImageIcon home01, home02, home03, homeFire, home04, home05L,
	home05R;
	
	//Внутри дома
	public static ImageIcon homeWall, flooring, homeWallRigth, homeWallLeft,
	flooringRigth, flooringLeft, voidI, window;
	
	//Город
	public static ImageIcon flowerbed, clDown, clUp, fountain, statues, grassC,
	cityF, cityFL, cityFR;
	
	public static ImageIcon[] allImage = new ImageIcon[43];
	
	public String file = "/base/res/Image/Tiles/";
	
	public TilesImage() {
		grass = new ImageIcon(getClass().getResource(file + "grass.png"));
		grassFire = new ImageIcon(getClass().getResource(file + "Animate/GrassFire/1.png"));
		water  = new ImageIcon(getClass().getResource(file + "water.png"));
		mount  = new ImageIcon(getClass().getResource(file + "mount.png"));
		brickWallP = new ImageIcon(getClass().getResource(file + "brickWallP.png"));
		brickWall = new ImageIcon(getClass().getResource(file + "brickWall.png"));
		water1 = new ImageIcon(getClass().getResource(file + "water1.png"));
		grassDark = new ImageIcon(getClass().getResource(file + "grassDark.png"));
		well = new ImageIcon(getClass().getResource(file + "well.png"));
		
		three = new ImageIcon(getClass().getResource(file + "three.png"));
		three2 = new ImageIcon(getClass().getResource(file + "three2.png"));
		threeDark = new ImageIcon(getClass().getResource(file + "threeDark.png"));
		threeDark2 = new ImageIcon(getClass().getResource(file + "three2Dark.png"));
		three3 = new ImageIcon(getClass().getResource(file + "three3.png"));
		
		townRoad = new ImageIcon(getClass().getResource(file + "townRoad.png"));
		brickPath  = new ImageIcon(getClass().getResource(file + "brickPath.png"));
		brickPathG  = new ImageIcon(getClass().getResource(file + "brickPathG.png"));
		bridge  = new ImageIcon(getClass().getResource(file + "bridge.png"));
		path = new ImageIcon(getClass().getResource(file + "path.png"));
		
		home01 = new ImageIcon(getClass().getResource(file + "TilesBuilding/home01.png"));
		home02 = new ImageIcon(getClass().getResource(file + "TilesBuilding/home02.png"));
		home03 = new ImageIcon(getClass().getResource(file + "TilesBuilding/home03.png"));
		homeFire = new ImageIcon(getClass().getResource(file + "TilesBuilding/homeFire.png"));
		home04 = new ImageIcon(getClass().getResource(file + "TilesBuilding/home04.png"));
		home05L = new ImageIcon(getClass().getResource(file + "TilesBuilding/home05L.png"));
		home05R = new ImageIcon(getClass().getResource(file + "TilesBuilding/home05R.png"));
		
		flooring = new ImageIcon(getClass().getResource(file + "flooring.png"));
		homeWall = new ImageIcon(getClass().getResource(file + "HomeWall.png"));
		homeWallRigth = new ImageIcon(getClass().getResource(file + "HomeWallRight.png"));
		flooringRigth = new ImageIcon(getClass().getResource(file + "flooringRigth.png"));
		homeWallLeft = new ImageIcon(getClass().getResource(file + "HomeWallLeft.png"));
		flooringLeft = new ImageIcon(getClass().getResource(file + "flooringLeft.png"));
		voidI = new ImageIcon(getClass().getResource(file + "void.png"));
		window = new ImageIcon(getClass().getResource(file + "window.png"));
		
		flowerbed = new ImageIcon(getClass().getResource(file + "flowerbed.png"));
		clDown = new ImageIcon(getClass().getResource(file + "columnDown.png"));
		clUp = new ImageIcon(getClass().getResource(file + "columnUp.png"));
		fountain = new ImageIcon(getClass().getResource(file + "fountain.png"));
		statues = new ImageIcon(getClass().getResource(file + "statues.png"));
		grassC = new ImageIcon(getClass().getResource(file + "cityGrass.png"));
		cityF = new ImageIcon(getClass().getResource(file + "cityF.png"));
		cityFL = new ImageIcon(getClass().getResource(file + "cityFL.png"));
		cityFR = new ImageIcon(getClass().getResource(file + "cityFR.png"));
		
		massiv();
	}
	
	private void massiv() {
		allImage[0] = grass;
		allImage[1] = path;
		allImage[2] = three;
		allImage[3] = water;
		allImage[4] = mount;
		allImage[5] = home01;
		allImage[6] = brickPath;
		allImage[7] = brickWallP;
		allImage[8] = brickWall;
		allImage[9] = brickPathG;
		allImage[10] = bridge;
		allImage[11] = three2;
		allImage[12] = water1;
		allImage[13] = home02;
		allImage[14] = home03;
		allImage[15] = homeFire;
		allImage[16] = flooring;
		allImage[17] = homeWall;
		allImage[18] = homeWallRigth;
		allImage[19] = flooringRigth;
		allImage[20] = homeWallLeft;
		allImage[21] = flooringLeft;
		allImage[22] = voidI;
		allImage[23] = window;
		allImage[24] = grassDark;
		allImage[25] = threeDark;
		allImage[26] = threeDark2;
		allImage[27] = grassFire;
		allImage[28] = well;
		allImage[29] = townRoad;
		allImage[30] = grassC;
		allImage[31] = home04;
		allImage[32] = home05L;
		allImage[33] = home05R;
		allImage[34] = flowerbed;
		allImage[35] = fountain;
		allImage[36] = clUp;
		allImage[37] = clDown;
		allImage[38] = statues;
		allImage[39] = three3;
		allImage[40] = cityF;
		allImage[41] = cityFL;
		allImage[42] = cityFR;
	}
	
}
