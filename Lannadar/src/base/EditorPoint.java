package base;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class EditorPoint extends JLabel {
	
	ImageIcon oldIcon = Editor.whiteOpaque;
	
	public EditorPoint() {
		super();
	}
	
	public EditorPoint(String text) {
		super(text);
	}
	
	public EditorPoint(String text, int n) {
		super(text, n);
	}
	
	public void returnIcon() {
		setIcon(oldIcon);
	}
	
}
