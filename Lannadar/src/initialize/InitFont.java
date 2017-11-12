package initialize;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import base.Game;

public class InitFont {
	
	public static Font uph, smw;
	
	public InitFont() {
		try {
			InputStream s = Game.class.getResourceAsStream("res/Font/UpheavalPro.ttf");
			InputStream s2 = Game.class.getResourceAsStream("res/Font/SMW_Text_NC.ttf");
			uph = Font.createFont(Font.TRUETYPE_FONT, s).deriveFont(22F);
			smw = Font.createFont(Font.TRUETYPE_FONT, s2).deriveFont(22F);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
