package ru.kreatifchk.editor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import ru.kreatifchk.editor.Editor.Mode;
import ru.kreatifchk.game.Monster;
import ru.kreatifchk.game.MonsterList;
import ru.kreatifchk.game.Tile;
import ru.kreatifchk.main.Fonts;
import ru.kreatifchk.main.Main;
import ru.kreatifchk.tools.Center;
import ru.kreatifchk.tools.Resize;

/** Диалоговое окно для выбора и конфигурирования монстров */
@SuppressWarnings("serial")
public class MonsterDialog extends JLabel {
	
	JLabel inDialog; //Панель на которую будет добавлен скролл
	
	JButton mon[] = new JButton[MonsterList.monsters.length];
	
	static Monster mr;
	
	public MonsterDialog(TileButton comp) {
		setSize((int)(342*Main.INC), (int)(300*Main.INC));//240
		Center.cnt(this, Editor.mainPane);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black, (int)(4*Main.INC)));
		
		JLabel hpN = new JLabel("Здоровье");
		JLabel mpN = new JLabel("Мана");
		JLabel lvlN = new JLabel("Уровень");
		JLabel dangerN = new JLabel("Опасность");
		hpN.setFont(Fonts.chemuRetro.deriveFont(12*Main.INC));
		mpN.setFont(Fonts.chemuRetro.deriveFont(12*Main.INC));
		lvlN.setFont(Fonts.chemuRetro.deriveFont(12*Main.INC));
		dangerN.setFont(Fonts.chemuRetro.deriveFont(12*Main.INC));
		hpN.setBounds((int)(12*Main.INC), (int)(5*Main.INC), (int)(70*Main.INC), (int)(17*Main.INC));
		mpN.setBounds((int)(108*Main.INC), (int)(5*Main.INC), (int)(40*Main.INC), (int)(17*Main.INC));
		lvlN.setBounds((int)(180*Main.INC), (int)(5*Main.INC), (int)(60*Main.INC), (int)(17*Main.INC));
		dangerN.setBounds((int)(257*Main.INC), (int)(5*Main.INC), (int)(80*Main.INC), (int)(17*Main.INC));
		add(hpN);
		add(mpN);
		add(lvlN);
		add(dangerN);
		
		DocumentListener dc = new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {}
			@Override
			public void insertUpdate(DocumentEvent a) {
				SwingUtilities.invokeLater(() -> {
					try {
						String txt = a.getDocument().getText(0, a.getDocument().getLength());
						a.getDocument().remove(0, a.getDocument().getLength());
						String txt2 = txt.replaceAll("[^\\d]", "");
						if (txt2.length() > 6) {
							txt2 = txt2.substring(0, 7);
						}
						a.getDocument().insertString(0, txt2, new SimpleAttributeSet());
					} catch (BadLocationException e) {}
				});
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {}
		};
		
		JTextField hpT = new JTextField();
		JTextField mpT = new JTextField();
		JTextField lvlT = new JTextField();
		hpT.setBounds((int)(6*Main.INC), (int)(23*Main.INC), (int)(80*Main.INC), (int)(17*Main.INC));
		mpT.setBounds((int)(89*Main.INC), (int)(23*Main.INC), (int)(80*Main.INC), (int)(17*Main.INC));
		lvlT.setBounds((int)(172*Main.INC), (int)(23*Main.INC), (int)(80*Main.INC), (int)(17*Main.INC));
		hpT.setFont(Fonts.digitalThin.deriveFont(14*Main.INC));
		mpT.setFont(Fonts.digitalThin.deriveFont(14*Main.INC));
		lvlT.setFont(Fonts.digitalThin.deriveFont(14*Main.INC));
		hpT.setHorizontalAlignment(JTextField.CENTER);
		mpT.setHorizontalAlignment(JTextField.CENTER);
		lvlT.setHorizontalAlignment(JTextField.CENTER);
		hpT.getDocument().addDocumentListener(dc);
		mpT.getDocument().addDocumentListener(dc);
		lvlT.getDocument().addDocumentListener(dc);
		add(hpT);
		add(mpT);
		add(lvlT);
		
		String[] el = {"Обычный", "Сильный", "Мини-босс", "Босс"};
		JComboBox<String> jcb = new JComboBox<String>(el);
		jcb.setBounds((int)(255*Main.INC), (int)(23*Main.INC), (int)(80*Main.INC), (int)(17*Main.INC));
		jcb.setFont(Fonts.harpseal.deriveFont(9*Main.INC));
		add(jcb);
		
		inDialog = new JLabel();
		inDialog.setPreferredSize(new Dimension(getWidth(), (int)(480*Main.INC)));
		inDialog.setOpaque(true);
		
		JScrollPane jsp = new JScrollPane(inDialog);
		jsp.setBounds(0, (int)(40*Main.INC), getWidth(), getHeight()-(int)(60*Main.INC));
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		add(jsp);
		
		int x = (int)(30 * Main.INC), y = (int)(10*Main.INC), j = 0;
		for (Monster i: MonsterList.monsters) {
			mon[j] = new JButton();
			mon[j].setBounds(x, y, Tile.SIZE, Tile.SIZE);
			mon[j].setIcon(i.currentView);
			mon[j].setContentAreaFilled(false);
			inDialog.add(mon[j]);
			
			//Добавление слушателя
			final int j2 = j;
			mon[j].addActionListener((e) -> {
				mr = MonsterList.monsters[j2];
				hpT.setText(mr.getHpMax() + "");
				mpT.setText(mr.getMpMax() + "");
				lvlT.setText(mr.getLevel() + "");
				jcb.setSelectedIndex(mr.getDanger() - 1);
			});
			
			x += (int)((30+48)*Main.INC);
			if (x > (int)(264*Main.INC)) {
				x = (int)(30 * Main.INC);
				y += (int)((30+48) * Main.INC);
			}
			
			j++;
		}
		
		JButton ok = new JButton("Выбор");
		ok.setBounds((int)(4*Main.INC), getHeight()-(int)(20*Main.INC), (int)(167*Main.INC), (int)(16*Main.INC));
		ok.addActionListener((e) -> {
			if (mr == null) {
				JOptionPane.showMessageDialog(this, "Выберите монстра", "Ошибка!", JOptionPane.ERROR_MESSAGE);
			} else {
				mr = new Monster(mr.getName(), mr.getImageName(), Integer.parseInt(hpT.getText()),
						Integer.parseInt(mpT.getText()), Integer.parseInt(lvlT.getText()), jcb.getSelectedIndex() + 1);
				Editor.mainPane.remove(this);
				Editor.openDialog = false;
				Editor.currentMode = Mode.monster;
				Editor.selectTile.setIcon(new ImageIcon(Resize.resizeA(comp.monsterI, (int)(46*Main.INC), (int)(46*Main.INC))));
			}
		});
		add(ok);
		
		JButton cancel = new JButton("Отмена");
		cancel.setBounds((int)(171*Main.INC), getHeight()-(int)(20*Main.INC), (int)(168*Main.INC), (int)(16*Main.INC));
		cancel.addActionListener((e) -> {mr = null; Editor.mainPane.remove(this); Editor.openDialog = false;});
		add(cancel);
	}

}
