package ru.kreatifchk.editor;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Ячейка, содержащие необходимые свойства
public class PointEditor extends JLabel implements Serializable {
	
	//transient запрещает сериализовать перменную/объект
	private static final long serialVersionUID = 31082007L;
	public int number = -1; //Номер тайла на указанной клетке
	transient ImageIcon oldIcon;
	transient boolean izm; //Изменяли ли иконку (для предпросмотра тайлов
	boolean anim; //Анимированный ли тайл
	
	//String password = "Miku";
	
	public PointEditor(int j, int i) {
		//Подпись номеров боковых клеток
		if (i == 0) {
			setText(j + "");
		} else if (j == 0) {
			setText(i + "");
		}
	}
	
}
