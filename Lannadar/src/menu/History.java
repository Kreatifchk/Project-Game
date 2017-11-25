package menu;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class History extends JFrame{
	
	//Исправить: NPC переходят из локации в локацию (+)
	//Добавить: проверка квеста перед отображением на то проходил ли его игрок (+)
	//Добавить: сохранение прогресса прохождения квестов (+)
	//Исправить: игрок может брать квест не находясь рядом с NPC (+)
	//Исправить: опыт не переносится на новый уровень (+)
	//Переделать шрифт квестов (+)
	//Перенести всю инициализацию тайлов в отдельный класс (+)
	//Исправить: при взятии квеста не отображаются никакие квесты (+)
	//Сделать сериализацию (как для квестов, так и для персонажа) (+)
	//Переделать кнопку взять (в квестах) (+)
	//Сделать опознавательный знак того, что у NPC есть квест (+)
	//Сделать чтоб в редакторе можно было заливать любым тайлом (+)
	//Сделать награды за квесты (+)
	//Сделать чтоб некоторые порталы были заблокированы (+)
	//Сделать чтоб значок квеста не показывался (для разговорных квестов, у NPC которым надо сдавать) (+)
	//Сделать чтоб показывалось имя NPC (+)
	//Сделать информацию в начале игры (+)
	//Сделать значок квеста для сдачи (+)
	//Заменить требование при сдаче квеста на награды
	//Сделать респаун мобов (+)
	//Сделать чтоб по клику открывалась панель хп и информации об npc и монстрах, а по двойному взаимодействие
	//Сделать порталы в виде класса с ArrayList
	//Сделать чтоб прогресс не показывался в разговорных квестах в панели квестов
	//Сделать всплывающее окно показывающее прогресс выполнения задания
	
	JLabel menu = new JLabel();
	
	JLabel t = new JLabel("0.0.1 - создана игровая карта.");
	JLabel t2 = new JLabel("0.0.3 - создан редактор карт.");
	JLabel t3 = new JLabel("0.1.0 - в редакторе добавлена возможность сохранять уровни.");
	JLabel t4 = new JLabel("0.1.2 - добавлены переходы между локациями.");
	JLabel t005 = new JLabel("0.2.0 - переходы между уровнями заменены на порталы.");
	JLabel t05 = new JLabel("Добавлена полоса здоровья. В редакторе инструменты делятся");
	JLabel t5 = new JLabel("на вкладки. Созданные уровни можно открывать в редакторе.");
	JLabel t06 = new JLabel("0.2.2 - добавлены монстры. Атаковать их невозможно.");
	JLabel t6 = new JLabel("Обновлено меню.");
	JLabel t7 = new JLabel("0.2.6 - можно атаковать монстров.");
	JLabel t8 = new JLabel("0.2.7 - исправлен баг с зависанием при атаке первого монстра");
	JLabel t9 = new JLabel("0.2.8 - игрок может умирать.");
	
	JLabel t10 = new JLabel("0.2.9 - добавлен вывод здоровья монстров");
	JLabel t11 = new JLabel("0.2.11 - добавлены уровни и опыт");
	JLabel t12 = new JLabel("0.2.12 - добавлена музыка");
	JLabel t13 = new JLabel("0.2.13 - добавлены настройки громкости и NPC(взаимодействия нет)");
	JLabel t14 = new JLabel("0.2.14 - улучшено меню, добавлена панель игрока");
	JLabel t15 = new JLabel("0.3.1 - у NPC можно открыть список квестов");
	JLabel t16 = new JLabel("0.3.1.2 - исправлен баг с отрисовкой квестов, добавлены имена NPC");
	JLabel t17 = new JLabel("0.3.2 - добавлена возможность сохранения игрока");
	JLabel t18 = new JLabel("0.3.3 - добавлены описания квестов, квесты можно взять");
	JLabel t19 = new JLabel("0.3.4 - квесты можно сдавать, добавлены некоторые анимации");
	JLabel t20 = new JLabel("0.4.0 (ALPHA) - добавлена панель взятых квестов и значки квестов");
	JLabel t21 = new JLabel("0.4.1 - сделаны нормальные сохранения, исправлены баги");
	
	JLabel t22 = new JLabel("0.4.2 - сделаны разговорные квесты, опознавательный");
	JLabel t022 = new JLabel("знак доступного квеста");
	JLabel t23 = new JLabel("0.4.3 - улучшена графика, исправлены баги");
	JLabel t24 = new JLabel("0.4.5 - улучшен интерфейс, полоса опыта заполняется красиво");
	JLabel t25 = new JLabel("0.5.0 - добавлен инвертарь, без взаимодействия с ним");
	JLabel t26 = new JLabel("0.5.2 - добавлено взаимодействие с инвертарем, ");
	JLabel t27 = new JLabel("0.5.3 - улучшения верхней панели, респаун монстров");
	JLabel t28 = new JLabel("0.5.5 - добавлены собирательные квесты, большой рефакторинг кода");
	
	JTabbedPane jtp;
	
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	
	Listener l = new Listener();
	
	public History() {
		super("Lannadar");
		addKeyListener(l);
		setLayout(null);
		p1.setLayout(null);
		p2.setLayout(null);
		p3.setLayout(null);
		Font f = new Font("Verdana", Font.BOLD, 14);
		t.setBounds(10, 20, 300, 30);
		t.setFont(f);
		t2.setBounds(10, 60, 300, 30);
		t2.setFont(f);
		t3.setBounds(10, 100, 550, 30);
		t3.setFont(f);
		t4.setBounds(10, 140, 450, 30);
		t4.setFont(f);
		t005.setBounds(10, 180, 600, 30);
		t005.setFont(f);
		t05.setBounds(10, 220, 600, 30);
		t05.setFont(f);
		t5.setBounds(10, 260, 600, 30);
		t5.setFont(f);
		t06.setBounds(10, 300, 600, 30);
		t06.setFont(f);
		t6.setBounds(10, 340, 600, 30);
		t6.setFont(f);
		t7.setBounds(10, 380, 600, 30);
		t7.setFont(f);
		t8.setBounds(10, 420, 600, 30);
		t8.setFont(f);
		t9.setBounds(10, 460, 600, 30);
		t9.setFont(f);
		
		t10.setBounds(10, 20, 600, 30);
		t10.setFont(f);
		t11.setBounds(10, 60, 600, 30);
		t11.setFont(f);
		t12.setBounds(10, 100, 600, 30);
		t12.setFont(f);
		t13.setBounds(10, 140, 600, 30);
		t13.setFont(f);
		t14.setBounds(10, 180, 600, 30);
		t14.setFont(f);
		t15.setBounds(10, 220, 600, 30);
		t15.setFont(f);
		t16.setBounds(10, 260, 600, 30);
		t16.setFont(f);
		t17.setBounds(10, 300, 600, 30);
		t17.setFont(f);
		t18.setBounds(10, 340, 600, 30);
		t18.setFont(f);
		t19.setBounds(10, 380, 600, 30);
		t19.setFont(f);
		t20.setBounds(10, 420, 600, 30);
		t20.setFont(f);
		t21.setBounds(10, 460, 600, 30);
		t21.setFont(f);
		
		t22.setBounds(10, 20, 600, 30);
		t22.setFont(f);
		t022.setBounds(10, 60, 600, 30);
		t022.setFont(f);
		t23.setBounds(10, 100, 600, 30);
		t23.setFont(f);
		t24.setBounds(10, 140, 600, 30);
		t24.setFont(f);
		t25.setBounds(10, 180, 600, 30);
		t25.setFont(f);
		t26.setBounds(10, 220, 600, 30);
		t26.setFont(f);
		t27.setBounds(10, 260, 600, 30);
		t27.setFont(f);
		t28.setBounds(10, 300, 600, 30);
		t28.setFont(f);
		
		p1.add(t);
		p1.add(t2);
		p1.add(t3);
		p1.add(t4);
		p1.add(t005);
		p1.add(t05);
		p1.add(t5);
		p1.add(t06);
		p1.add(t6);
		p1.add(t7);
		p1.add(t8);
		p1.add(t9);
		
		p2.add(t10);
		p2.add(t11);
		p2.add(t12);
		p2.add(t13);
		p2.add(t14);
		p2.add(t15);
		p2.add(t16);
		p2.add(t17);
		p2.add(t18);
		p2.add(t19);
		p2.add(t20);
		p2.add(t21);
		
		p3.add(t22);
		p3.add(t022);
		p3.add(t23);
		p3.add(t24);
		p3.add(t25);
		p3.add(t26);
		p3.add(t27);
		p3.add(t28);
		jtp = new JTabbedPane();
		jtp.setBounds(0, 0, 600, 541);
		add(jtp);
		jtp.addTab("1", p1);
		jtp.addTab("2", p2);
		jtp.addTab("3", p3);
		setFocusable(true);
	}
	
	private class Listener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent a) {
			int key = a.getExtendedKeyCode();
			if (key == KeyEvent.VK_ESCAPE) {
				dispose();
				Menu m = new Menu();
				m.setVisible(true);
				m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				m.setSize(600, 561);
				m.setResizable(false);
				m.setLocationRelativeTo(null);
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
}
