package rpg;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javazoom.jlgui.basicplayer.BasicPlayerException;

@SuppressWarnings("serial")
public class Settings extends JFrame implements ChangeListener, ActionListener{
	
	Image icon = new ImageIcon(getClass().getResource("res/icon.png")).getImage();
	
	static int startValue = 12;
	JLabel t = new JLabel("Громкость музыки");
	JSlider value = new JSlider(0, 100, startValue);
	JButton back = new JButton("В меню");
	static double volume = 0.12;
	
	public Settings() {
		super("Lannadar");
		setLayout(null);
		setIconImage(icon);
		Font f = new Font("Verdana", Font.BOLD, 15);
		t.setBounds(220, 20, 200, 70);
		t.setFont(f);
		value.setBounds(150, 70, 300, 70);
		value.setPaintTicks(true);
		value.setPaintLabels(true);
		value.setMajorTickSpacing(10);
		value.addChangeListener(this);
		back.setBounds(200, 460, 200, 40);
		back.addActionListener(this);
		add(t);
		add(value);
		add(back);
	}

	@Override
	public void stateChanged(ChangeEvent a) {
		double z = 0;
		int c = 0;
		if (a.getSource() == value) {
			c = value.getValue();
			startValue = c;
			z = (double) c / 100;
			try {
				Menu.player.setGain(z);
				if (Music.player != null) {
					volume = z;
					//Music.player.setGain(z);
				}
			} catch (BasicPlayerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == back) {
			dispose();
			Menu m = new Menu();
			m.setVisible(true);
			m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			m.setSize(600, 561);
			m.setResizable(false);
			m.setLocationRelativeTo(null);
		}
	}
	
}
