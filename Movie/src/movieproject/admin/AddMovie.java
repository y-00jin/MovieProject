package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import movieproject.movie.MovieAPI;

public class AddMovie extends JFrame implements ActionListener {
	private JButton ok;
	private JTextField tf;
	MovieAPI api = new MovieAPI();
	public AddMovie(String Title, int width, int height) {
		this.setTitle(Title);
		setSize(width, height);
		//setLocation(475, 300);
		setLocationRelativeTo(this);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel main = new JPanel();
		main.setBackground(Color.WHITE);
		main.setLayout(new BorderLayout());
		
		JPanel p1 = new JPanel();
		
		JPanel up = new JPanel();
		p1.setBackground(Color.WHITE);
		up.setBackground(Color.WHITE);
		up.setLayout(new GridLayout(2, 1));
		
		JLabel la = new JLabel("날짜입력");
		la.setHorizontalAlignment(JLabel.CENTER);
		up.add(la);
		tf = new JTextField(20);
		tf.setHorizontalAlignment(JTextField.CENTER);
		up.add(tf);
		
		JPanel down = new JPanel();
		down.setBackground(Color.WHITE);
		ok = new JButton("  확인  ");
		ok.addActionListener(this);
		class RoundedBorder implements Border {
			int radius;

			RoundedBorder(int radius) {
				this.radius = radius;
			}

			public Insets getBorderInsets(Component c) {
				return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius);
			}

			public boolean isBorderOpaque() {
				return true;
			}

			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
			}
		}
		ok.setBorder(new RoundedBorder(5));
		Font btnFont = new Font("맑은 고딕", Font.PLAIN, 12);
		ok.setFont(btnFont);
		ok.setBackground(Color.WHITE);
		ok.setForeground(Color.PINK);
		down.add(ok);
		
		
		p1.add(up);
		main.add(p1, BorderLayout.CENTER);
		main.add(down, BorderLayout.SOUTH);
		add(main);
				
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new AddMovie("영화추가",330, 120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == ok) {
			api.setDATE_FMT(tf.getText());
			api.requestAPI();
			dispose();
		}
	}

}
