package ru.kreatifchk.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ru.kreatifchk.main.Fonts;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Img;

/** Класс диалогового окна - создание карты */
@SuppressWarnings("serial")
public class CreateDialog extends JLabel implements ChangeListener {
	
	JButton create = new JButton("Создать");
	JButton cancel = new JButton("Отмена");
	JLabel widtValue = new JLabel("Ширина: " + 20);
	JLabel heigValue = new JLabel("Высота: " + 13);
	Image bg = Img.Image(Main.class.getResource("/ru/kreatifchk/res/image/editor/dialogBackground.png"));
	
	JSlider widt, heig; //Слайдеры во всплывающем окне для выбора размера карты
	
	public CreateDialog() {
		setSize((int)(270*Main.INC), (int)(220*Main.INC));
		Center.cnt(this, Editor.mainPane);
		Editor.mainPane.add(this, new Integer(10));
		
		create.setBounds((int)(30*Main.INC), (int)(175*Main.INC), (int)(85*Main.INC), (int)(35*Main.INC));
		create.addActionListener((e) -> {
			//Перед созданием новой карты очистка старой
			Map.field = null;
			Editor.centerPanel.removeAll();
			//Создание карты заданных размеров
			Editor.mainPane.remove(this);
			
			Editor.openDialog = false;
			int x = widt.getValue(), y = heig.getValue(); //Кол-во полей по x и по y
			Map.field = new PointEditor[x][y];
			int xPoint = (int)(4*Main.INC), yPoint = 0;
			for (int i = 0; i < Map.field[0].length; i++) {
				for (int j = 0; j < Map.field.length; j++) {
					Map.field[j][i] = new PointEditor(j, i);
					Map.field[j][i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
					Map.field[j][i].setBounds(xPoint, yPoint, (int)(48*Main.INC), (int)(48*Main.INC));
					Map.field[j][i].setOpaque(true);
					Map.field[j][i].setBackground(new Color(255, 255, 255, 240));
					Editor.centerPanel.add(Map.field[j][i]);
					xPoint += (int)(48*Main.INC);
				}
				yPoint += (int)(48*Main.INC);
				xPoint = (int)(4*Main.INC);
			}
			
			Editor.field = Map.field;
		});
		add(create);
		cancel.setBounds((int)(145*Main.INC), (int)(175*Main.INC), (int)(85*Main.INC), (int)(35*Main.INC));
		cancel.addActionListener((e) -> {Editor.mainPane.remove(this); Editor.openDialog = false;});
		add(cancel);
		
		widtValue.setSize((int)(90*Main.INC), (int)(18*Main.INC));
		widtValue.setFont(Fonts.chemuRetro.deriveFont(12*Main.INC));
		Center.cnt(widtValue, this, (int)(10*Main.INC));
		add(widtValue);
		widt = new JSlider(20, 120, 20);
		widt.setBounds((int)(13*Main.INC), (int)(37*Main.INC), (int)(245*Main.INC), (int)(35*Main.INC));
		widt.setMajorTickSpacing(10);
		widt.setMinorTickSpacing(5);
		widt.setPaintTicks(true);
		widt.setPaintLabels(true);
		widt.addChangeListener(this);
		widt.setUI(new LannadarSliderUI(widt));
		add(widt);
		
		/*LannadarSlider lnd = new LannadarSlider(20, 120, 12);
		lnd.setBounds((int)(13*Main.INC), (int)(105*Main.INC), (int)(245*Main.INC), (int)(35*Main.INC));
		lnd.setMajorTickSpacing(10);
		lnd.setMinorTickSpacing(5);
		add(lnd);*/
		heigValue.setSize((int)(90*Main.INC), (int)(18*Main.INC));
		heigValue.setFont(Fonts.chemuRetro.deriveFont(12*Main.INC));
		Center.cnt(heigValue, this, (int)(82*Main.INC));
		add(heigValue);
		heig = new JSlider(13, 120, 13);
		heig.setBounds((int)(13*Main.INC), (int)(109*Main.INC), (int)(245*Main.INC), (int)(35*Main.INC));
		heig.setMajorTickSpacing(10);
		heig.setMinorTickSpacing(5);
		heig.setPaintTicks(true);
		heig.setPaintLabels(true);
		heig.addChangeListener(this);
		heig.setUI(new LannadarSliderUI(heig));
		add(heig);
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg, 0, 0, null);
	}
	
	//Лисенер для JSLider во всплывающем окне
	@Override
	public void stateChanged(ChangeEvent a) {
		if (a.getSource() == widt) {
			widtValue.setText("Ширина: " + widt.getValue());
		}
		if (a.getSource() == heig) {
			heigValue.setText("Высота: " + heig.getValue());
		}
		repaint();
	}
	
}
