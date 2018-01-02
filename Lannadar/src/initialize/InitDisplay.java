package initialize;

import java.awt.Dimension;
import java.awt.Toolkit;

public class InitDisplay {
	
	@SuppressWarnings("unused")
	public InitDisplay() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int width = size.width, height = size.height;
	}
	
}
