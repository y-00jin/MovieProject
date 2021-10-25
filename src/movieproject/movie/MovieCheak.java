package movieproject.movie;

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
import java.sql.ResultSet;
import java.sql.SQLException;

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

import movieproject.DBconnect;
import movieproject.payment.Reservation;

public class MovieCheak extends JFrame implements ActionListener {

	private JButton ok;
	private JTextField tf;
	//MovieAPI api = new MovieAPI();
	private JComboBox<String> name;
	private JComboBox<String> date = new JComboBox<String>();
	private JComboBox<String> time = new JComboBox<String>();
	String n;
	String mem;
	String d;
	String t;
	Main id;
	public MovieCheak(String Title, int width, int height, Main id) {
		this.id = id;
		//dispose();
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
		up.setLayout(new GridLayout(4, 4, 0, 0));
		
		JLabel la = new JLabel("아이디");
		la.setHorizontalAlignment(JLabel.CENTER);
		up.add(la);
		
		JLabel ID = new JLabel(id.getId().toString());
		ID.setHorizontalAlignment(JLabel.CENTER);
		up.add(ID);
		
		JLabel la2 = new JLabel("예매한 영화");
		la2.setHorizontalAlignment(JLabel.CENTER);
		up.add(la2);
		
		name = new JComboBox<String>();
		name.addActionListener(this);
		String sql = "SELECT DISTINCT MOVIE_NAME FROM SEAT s WHERE ID = '" + id.getId().toString() + "'";
		ResultSet re = DBconnect.getResultSet(sql);
		
		try {
			while(re.next()) {		
				name.addItem(re.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		up.add(name);
		
		////////////////////////////////////////
		JLabel la3 = new JLabel("날짜");
		la3.setHorizontalAlignment(JLabel.CENTER);
		up.add(la3);
		
		
		date.addActionListener(this);
		up.add(date);
		
		JLabel la4 = new JLabel("시간");
		la4.setHorizontalAlignment(JLabel.CENTER);
		up.add(la4);
		
		//time = new JComboBox<String>();
		up.add(time);
	
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
		//name.setBorder(new RoundedBorder());
		name.setBackground(Color.WHITE);
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
		DBconnect.DB();
		//new MovieCheak("URL추가",530, 135);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == ok) {
			mem = id.getId().toString();
			n = name.getSelectedItem().toString();
			d = date.getSelectedItem().toString();
			t = time.getSelectedItem().toString();
			new Reservation("예매 확인", 400, 400, this);
		}
		if(ob == name) {
			date.removeAllItems();
			String sql = "SELECT DISTINCT dates FROM TIME t WHERE MOVIE_NAME = "
					+ "'" + name.getSelectedItem() + "' ORDER BY dates asc";
			ResultSet re = DBconnect.getResultSet(sql);
//			System.out.println(sql);
			try {
				while(re.next()) {		
					date.addItem(re.getString(1));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		if(ob == date) {
			time.removeAllItems();
			String sql = "SELECT DISTINCT TIME FROM TIME t WHERE MOVIE_NAME = "
					+ "'" + name.getSelectedItem() + "' AND DATES = '" + 
					date.getSelectedItem() + "' ORDER BY TIME asc";
			ResultSet re = DBconnect.getResultSet(sql);
//			System.out.println(sql);
			try {
				while(re.next()) {		
					time.addItem(re.getString(1));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	public String getN() {
		return n;
	}

	public String getD() {
		return d;
	}

	public String getT() {
		return t;
	}

	public String getMem() {
		return mem;
	}
	
}
