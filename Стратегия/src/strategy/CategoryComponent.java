package strategy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class CategoryComponent extends JComponent {
	//Пример кастомной кнопки

	//private BufferedImage img;
	private Color componentColor = new Color(232, 143, 23);
	
	public CategoryComponent() {
		addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
                componentColor = new Color(255, 143, 23);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                componentColor = componentColor.brighter();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                componentColor = new Color(232, 143, 23);
                repaint();
            }
        });
		setMinimumSize(new Dimension(500, 100));
		setPreferredSize(new Dimension(500, 100));

        //setBorder(BorderFactory.createLineBorder(componentColor.darker(), 2));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(componentColor);
        //g.fillRect(0, 0, getWidth(), getHeight());
        g.fillOval(0, 0, getWidth(), getHeight());

        //g.drawImage(img, 5, 5, null);

        g.setColor(Color.black);
        Font large = new Font("Arial", Font.BOLD, 24);

        g.setFont(large);
        g.drawString("text", 80, 29);
    }
}
