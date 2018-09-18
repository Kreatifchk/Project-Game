package ru.kreatifchk.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ru.kreatifchk.editor.Editor.Mode;
import ru.kreatifchk.game.Player.Direction;
import ru.kreatifchk.game.TilesList;
import ru.kreatifchk.main.Fonts;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Resize;

/** Диалоговое окно для выбора названия файла и стороны перемещения*/
@SuppressWarnings("serial")
public class TransitDialog extends JLabel {
	
	JLabel sections[] = new JLabel[3];
	
	int xTrans = 93, yTrans = 140;
	
	public TransitDialog(TileButton comp) {
		setOpaque(true);
		setBackground(Color.LIGHT_GRAY);
		setSize((int)(220*Main.INC), (int)(190*Main.INC));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		Center.cnt(this, Editor.mainPane);
		
		//Инициализация JLabel меток
		for (int i = 0; i < sections.length; i++) {
			sections[i] = new JLabel();
			sections[i].setOpaque(true);
			sections[i].setBackground(Color.WHITE);
			sections[i].setVerticalAlignment(JLabel.CENTER);
			sections[i].setHorizontalAlignment(JLabel.CENTER);
			sections[i].setFont(Fonts.chemuRetro.deriveFont(10*Main.INC));
			add(sections[i]);
		}
		
		sections[0].setBounds((int)(3*Main.INC), (int)(2*Main.INC), (int)(215*Main.INC), (int)(18*Main.INC));
		sections[0].setText("Название локации для перехода");
		sections[1].setBounds((int)(3*Main.INC), (int)(50*Main.INC), (int)(215*Main.INC), (int)(18*Main.INC));
		sections[1].setText("Сторона с переходом");
		sections[2].setBounds((int)(3*Main.INC), (int)(94*Main.INC), (int)(215*Main.INC), (int)(18*Main.INC));
		sections[2].setText("Место назначения по X и Y");
		
		//Поле для ввода названия локации
		JTextField jtf = new JTextField(12);
		jtf.setBounds((int)(10*Main.INC), (int)(25*Main.INC), (int)(200*Main.INC), (int)(20*Main.INC));
		jtf.setFont(Fonts.digitalThin.deriveFont(26F));
		add(jtf);
		
		String[] elements = {"Левая", "Правая", "Центр", "Низ", "Верх"};
		JComboBox<String> jcb = new JComboBox<String>(elements);
		jcb.setBounds((int)(58*Main.INC), (int)(71*Main.INC), (int)(110*Main.INC), (int)(20*Main.INC));
		jcb.setFont(Fonts.harpseal.deriveFont(15F));
		add(jcb);
		
		//Метки выбора X координаты и Y координаты
		JLabel x = new JLabel("X: " + xTrans);
		JLabel y = new JLabel("Y: " + yTrans);
		x.setBounds((int)(39*Main.INC), (int)(130*Main.INC), (int)(50*Main.INC), (int)(10*Main.INC));
		y.setBounds((int)(150*Main.INC), (int)(130*Main.INC), (int)(50*Main.INC), (int)(10*Main.INC));
		x.setFont(Fonts.digitalThin.deriveFont(15F));
		y.setFont(Fonts.digitalThin.deriveFont(15F));
		add(x);
		add(y);
		
		//Кнопки выбора X координаты и Y координаты
		JButton minusX2 = new IncDecButton("<<"), minusX = new IncDecButton("<");
		JButton plusX = new IncDecButton(">"), plusX2 = new IncDecButton(">>");
		JButton minusY2 = new IncDecButton("<<"), minusY = new IncDecButton("<");
		JButton plusY = new IncDecButton(">"), plusY2 = new IncDecButton(">>");
		
		ActionListener act = e -> {
			if  (e.getSource() == minusX & xTrans - 1 >= 0) {
				xTrans--;
				x.setText("X: " + xTrans);
			}
			if (e.getSource() == minusX2 & xTrans - 10 >= 0) {
				xTrans -= 10;
				x.setText("X: " + xTrans);
			}
			
			if  (e.getSource() == plusX & xTrans + 1 <= 140) {
				xTrans++;
				x.setText("X: " + xTrans);
			}
			if (e.getSource() == plusX2 & xTrans + 10 <= 140) {
				xTrans += 10;
				x.setText("X: " + xTrans);
			}
			
			if  (e.getSource() == minusY & yTrans - 1 >= 0) {
				yTrans--;
				y.setText("Y: " + yTrans);
			}
			if (e.getSource() == minusY2 & yTrans - 10 >= 0) {
				yTrans -= 10;
				y.setText("Y: " + yTrans);
			}
			
			if  (e.getSource() == plusY & yTrans + 1 <= 140) {
				yTrans++;
				y.setText("Y: " + yTrans);
			}
			if (e.getSource() == plusY2 & yTrans + 10 <= 140) {
				yTrans += 10;
				y.setText("Y: " + yTrans);
			}
		};
		
		minusX2.setBounds((int)(28*Main.INC), (int)(115*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		minusX.setBounds((int)(28*Main.INC), (int)(143*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		plusX.setBounds((int)(55*Main.INC), (int)(143*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		plusX2.setBounds((int)(55*Main.INC), (int)(115*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		minusY2.setBounds((int)(139*Main.INC), (int)(115*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		minusY.setBounds((int)(139*Main.INC), (int)(143*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		plusY.setBounds((int)(166*Main.INC), (int)(143*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		plusY2.setBounds((int)(166*Main.INC), (int)(115*Main.INC), (int)(25*Main.INC), (int)(14*Main.INC));
		minusX.addActionListener(act);
		minusX2.addActionListener(act);
		plusX.addActionListener(act);
		plusX2.addActionListener(act);
		minusY.addActionListener(act);
		minusY2.addActionListener(act);
		plusY.addActionListener(act);
		plusY2.addActionListener(act);
		add(minusX2);
		add(minusX);
		add(plusX);
		add(plusX2);
		add(minusY2);
		add(minusY);
		add(plusY);
		add(plusY2);
		
		//Кнопки потверждения или отмены
		JButton ok = new JButton("Потвердить");
		ok.setBounds((int)(10*Main.INC), (int)(160*Main.INC), (int)(90*Main.INC), (int)(25*Main.INC));
		ok.setFont(Fonts.harpseal.deriveFont(8*Main.INC));
		add(ok);
		
		JButton cancel = new JButton("Закрыть");
		cancel.setBounds((int)(120*Main.INC), (int)(160*Main.INC), (int)(90*Main.INC), (int)(25*Main.INC));
		cancel.addActionListener((e) -> {
			Editor.mainPane.remove(this);
			Editor.openDialog = false;
			Editor.selectTile.setIcon(Resize.resizeIcon(TilesList.tiles[Editor.actTile].icon.getImage(), (int)(46*Main.INC), (int)(46*Main.INC)));
		});
		cancel.setFont(Fonts.harpseal.deriveFont(8*Main.INC));
		add(cancel);
		
		Pattern pat = Pattern.compile("[A-Za-z0-9А-я\\-]+");
		
		ok.addActionListener((e) -> {
			Matcher m1 = pat.matcher(jtf.getText());
			if (jtf.getText().length() > 17 || jtf.getText().length() <= 0) {
				JOptionPane.showMessageDialog(this, "Длина названия должна быть в пределах от 0 до 17 символов!",
						"Ошибка", JOptionPane.DEFAULT_OPTION);
			} else if (!m1.matches()) {
				JOptionPane.showMessageDialog(this, "В названии можно использовать только буквы"
						+ "  английского \nи русского алфавита, цифры и тире!",
						"Ошибка", JOptionPane.DEFAULT_OPTION);
			} else {
				//Если все умпешно закрываем окно и устанавливаем данные
				Editor.transitLocation = jtf.getText();
				if (jcb.getSelectedIndex() == 0) {
					Editor.dirTransit = Direction.left;
				}
				if (jcb.getSelectedIndex() == 1) {
					Editor.dirTransit = Direction.right;
				}
				if (jcb.getSelectedIndex() == 2) {
					Editor.dirTransit = Direction.stand;
				}
				if (jcb.getSelectedIndex() == 3) {
					Editor.dirTransit = Direction.down;
				}
				if (jcb.getSelectedIndex() == 4) {
					Editor.dirTransit = Direction.up;
				}
				
				Editor.xTrans = xTrans;
				Editor.yTrans = yTrans;
				
				Editor.mainPane.remove(this);

				Editor.currentMode = Mode.transit;
				Editor.openDialog = false;
				Editor.selectTile.setIcon(new ImageIcon(Resize.resizeA(comp.transitI, (int)(43*Main.INC), (int)(43*Main.INC))));
			}
		});
	}
	
	private class IncDecButton extends JButton {
		String symb;
		public IncDecButton(String symb) {
			this.symb = symb;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(2));
			if (symb.equals("<")) {
				g2d.drawLine(0, getHeight() / 2, getWidth() / 2 + (int)(5*Main.INC), 0);
				g2d.drawLine(0, getHeight() / 2, getWidth() / 2 + (int)(5*Main.INC), getHeight());
			} else if (symb.equals("<<")) {
				g2d.drawLine(0, getHeight() / 2, getWidth() / 2 + (int)(3*Main.INC), 0);
				g2d.drawLine(0, getHeight() / 2, getWidth() / 2 + (int)(3*Main.INC), getHeight());
				g2d.drawLine((int)(6*Main.INC), getHeight() / 2, getWidth(), 0);
				g2d.drawLine((int)(6*Main.INC), getHeight() / 2, getWidth(), getHeight());
			} else if (symb.equals(">")) {
				g2d.drawLine(getWidth(), getHeight() / 2, getWidth() / 2 - (int)(5*Main.INC), 0);
				g2d.drawLine(getWidth(), getHeight() / 2, getWidth() / 2 - (int)(5*Main.INC), getHeight());
			} else if (symb.equals(">>")) {
				g2d.drawLine(getWidth(), getHeight() / 2, getWidth() / 2 - (int)(3*Main.INC), 0);
				g2d.drawLine(getWidth(), getHeight() / 2, getWidth() / 2 - (int)(3*Main.INC), getHeight());
				g2d.drawLine(getWidth() - (int)(6*Main.INC), getHeight() / 2, 0, 0);
				g2d.drawLine(getWidth() - (int)(6*Main.INC), getHeight() / 2, 0, getHeight());
			}
		}
	}
	
}
