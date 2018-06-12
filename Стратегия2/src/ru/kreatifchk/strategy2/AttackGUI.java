package ru.kreatifchk.strategy2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class AttackGUI extends GUICenter implements ChangeListener, ActionListener {
	
	int selectArmy = 1;
	
	int x,y; //Координаты поля на которое нападют
	int selX, selY; //Координаты поля, которое нападает
	
	public AttackGUI(int x, int y, int selX, int selY) {
		this.x = x;
		this.y = y;
		this.selX = selX;
		this.selY = selY;
		
		JSlider js = new JSlider(1, GameFrame.selectPoint.army, 1);
		js.addChangeListener(this);
		js.setMajorTickSpacing(GameFrame.selectPoint.army/10);
		js.setPaintLabels(true);
		js.setPaintTicks(true);
		js.setBounds((this.getWidth() - 300) / 2, 200, 400, 70);
		add(js);
		
		JButton attack = new JButton("Атаковать");
		attack.setBounds(100, 100, 130, 30);
		attack.addActionListener(this);
		add(attack);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Calibri", Font.BOLD, 32));
		g2d.drawString("Выбранная армия:" + selectArmy, 90, 80);
	}

	@Override
	public void stateChanged(ChangeEvent a) {
		selectArmy = ((JSlider)a.getSource()).getValue();
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		//Если атака все-же нажата
		GameFrame.pm[selX][selY].army -= selectArmy; //Отправить в бой выбранное количество солдат
		Events.attackEvent(GameFrame.pm[selX][selY], selectArmy, GameFrame.pm[x][y], GameFrame.entity.get(GameFrame.idPlayer));
		GameFrame.mainPane.remove(this);
		GameFrame.attack = false;
		GameFrame.GUIOpened = false;
		GameFrame.pl.pointAction--;
	}

}
