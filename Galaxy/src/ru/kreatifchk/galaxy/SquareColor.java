package ru.kreatifchk.galaxy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SquareColor extends JFrame implements Runnable, ActionListener {
	
	JLabel one = new JLabel();
	
	int red = 0, blue = 0, green = 0;
	int c = 1;
	boolean logR, logB, logG;
	
	Color cl = new Color(red, blue, green);
	
	Thread change = new Thread(this);
	
	Timer t = new Timer(15, this);
	
	public SquareColor() {
		super("Galaxy");
		
		t.start();
		change.start();
		
		setLayout(null);
		one.setBounds(260, 130, 180, 180);
		//cl = new Color(100, 100, 0);
		//one.setBackground(cl);
		one.setOpaque(true);
		add(one);
	}

	@Override
	public void run() {
		while(true) {
			if (red < 255 & c == 1 & logR == false) {
				red++;
			} else if (c == 1) {
				logR = true;
				c = 2;
			}
			
			if (green < 255 & c == 2 & logG == false) {
				green++;
			} else if (c == 2) {
				logG = true;
				c = 3;
			}
			
			if (blue < 255 & c == 3 & logB == false) {
				blue++;
			} else if (c == 3) {
				logB = true;
				c = 1;
			}
			
			
			/*if (red > 0 & c == 1 & logR == true) {
				red--;
			} else if (c == 1) {
				logR = false;
				c = 2;
			}*/
			
			cl = new Color(red, blue, green);
			Sleep.sleep(15);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		one.setBackground(cl);
		repaint();
	}
	
}
