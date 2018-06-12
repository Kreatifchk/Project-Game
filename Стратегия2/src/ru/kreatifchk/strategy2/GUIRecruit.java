package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class GUIRecruit extends GUICenter implements ActionListener, ChangeListener {

	int value = 1;
	JLabel recruit;
	JButton hire; //Кнопка нанять
	PointMap pm;
	
	public GUIRecruit(PointMap pm) {
		super();
		this.pm = pm;
		
		JSlider js = new JSlider(1, GameFrame.pl.money, 1);
		js.addChangeListener(this);
		js.setMajorTickSpacing(GameFrame.pl.money / 12);
		js.setMinorTickSpacing(GameFrame.pl.money / 12 / 3);
		js.setPaintLabels(true);
		js.setPaintTicks(true);
		js.setSize(getWidth() / 10 * 9, 70);
		js.setLocation((GUIManager.getCenter(js.getWidth(), this.getWidth())), 200);
		add(js);
		
		recruit = new JLabel("Нанять " + value + " воинов");
		recruit.setBounds(100, 100, 120, 35);
		add(recruit);
		
		hire = new JButton("Нанять");
		hire.addActionListener(this);
		add(hire);
		GUIManager.setBoundsComponent(hire, -1, -1, 17, 7);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
	}

	@Override
	public void stateChanged(ChangeEvent a) {
		value = ((JSlider)a.getSource()).getValue();
		recruit.setText("Нанять " + value + " воинов");
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == hire) {
			pm.army += value;
			GameFrame.pl.money -= value;
			GameFrame.pl.pointAction--;
			
			GameFrame.pInf.setVisible(true);
			GameFrame.mainPane.remove(this);
		}
	}
	
}
