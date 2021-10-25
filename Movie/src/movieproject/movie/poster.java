package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class poster extends JFrame {

	private movietiketing main;	
	private JLabel pos = new JLabel();
	private ImageIcon img;

	private JPanel m = new JPanel();
	public poster(String Title, int width, int height, movietiketing main) {
		this.main = main;
		this.setTitle(Title);
		setSize(width, height);
		setLocation(112, 100);
		//setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setLayout(new BorderLayout());

		add(pos);
		
		//add(m);
		
		setVisible(false);
	}
	
	public static void main(String[] args) {
		//new poster("좌석선택",250, 300, null);
	}
	public JLabel getPos() {
		return pos;
	}
	public JPanel getM() {
		return m;
	}
}
