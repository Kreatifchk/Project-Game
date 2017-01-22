package others;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

/** Кнопка - стрелка. */
@SuppressWarnings("serial")
public class ArrowButton extends JButton  implements MouseListener {
	
	Polygon p;
	int arrayX[], arrayY[];
	
	private int b;
	private int startY;
	private int sizeY = 4;
	private int type;
	
	private Color bcColor = Color.WHITE;
	private Color borderColor = Color.BLACK;
	
	public ArrowButton(int type) {
		this.type = type;
		setContentAreaFilled(false);
		addMouseListener(this);
		
		init();
	}
	
	private void init() {
		int sizeY = getWidth() / this.sizeY; //Непосредственный размер стрелки по Y
		startY = (getWidth() - sizeY) / 2; //Начальная координата по Y
		//startX = (getWidth() / 2) - (razmX / 2);
		
		arrayX = new int[7];
		arrayY = new int[7];
		
		if (type == 1) {
			arrayX[0] = 0;
			arrayY[0] = startY;
			
			arrayX[1] = getWidth() / 10 * 6; //1 - элемент, 60 % от общего квадрата
			arrayY[1] = startY;
			
			arrayX[2] = getWidth() / 10 * 6;
			arrayY[2] = startY - sizeY / 4;
			int prob = startY - sizeY / 4;
			
			arrayX[3] = getWidth();
			arrayY[3] = startY + sizeY / 2;
			
			arrayX[4] = getWidth() / 10 * 6;
			arrayY[4] = getHeight() - prob;
			
			arrayX[5] = getWidth() / 10 * 6;
			arrayY[5] = getHeight() - startY;
			
			arrayX[6] = 0;
			arrayY[6] = getHeight() - startY;
			p = new Polygon(arrayX, arrayY, 7);
		}
		if (type == 2) {
			arrayX[0] = getWidth();
			arrayY[0] = startY;
			
			int xx = getWidth() - (getWidth() / 10 * 6);
			 //1 - элемент, 60 % от общего квадрата
			
			arrayX[1] = xx;
			arrayY[1] = startY;
			
			arrayX[2] = xx;
			arrayY[2] = startY - sizeY / 4;
			int prob = startY - sizeY / 4;
			
			arrayX[3] = 0;
			arrayY[3] = startY + sizeY / 2;
			
			arrayX[4] = xx;
			arrayY[4] = getHeight() - prob;
			
			arrayX[5] = xx;
			arrayY[5] = getHeight() - startY;
			
			arrayX[6] = getWidth();
			arrayY[6] = getHeight() - startY;
			p = new Polygon(arrayX, arrayY, 7);
		}
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		init();
		repaint();
	}
	
	/** Устанавливает размер стрелки по Y */
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
		repaint();
	}
	
	public void setColor(Color bcColor) {
		this.bcColor = bcColor;
	}
	
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	@Override
	public boolean contains(int x, int y) {
		Shape shape;
		shape = p;
		return shape.contains(x, y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(bcColor);
		g2d.fillPolygon(p);
		if (b == 1) {
			g2d.setColor(new Color(128, 128, 128, 128));
			g2d.fillPolygon(p);
		}
	}
	
	@Override
	public void paintBorder(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(borderColor);
		if (b == 2) {
			g2d.setStroke(new BasicStroke(4));
		} else {
			g2d.setStroke(new BasicStroke(2));
		}
		g2d.drawLine(arrayX[0], arrayY[0], arrayX[1], arrayY[0]);
		g2d.drawLine(arrayX[1], arrayY[0] ,arrayX[1], arrayY[2]);
		g2d.drawLine(arrayX[1], arrayY[2], arrayX[3], arrayY[3]);
		g2d.drawLine(arrayX[3], arrayY[3], arrayX[2], arrayY[4]);
		g2d.drawLine(arrayX[4], arrayY[4], arrayX[4], arrayY[5]);
		g2d.drawLine(arrayX[5], arrayY[5], arrayX[6], arrayY[5]);
		g2d.drawLine(arrayX[6], arrayY[5], arrayX[6], arrayY[0]);
		//Также можно было использовать drawPolygon(p);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent a) {
		if (a.getSource() == this) {
			b = 1;
		}
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent a) {
		if (a.getSource() == this) {
			b = 0;
		}
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent a) {
		if (a.getSource() == this) {
			b = 2;
			repaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent a) {
		if (a.getSource() == this) {
			b = 1;
			repaint();
		}
	}
	
}
