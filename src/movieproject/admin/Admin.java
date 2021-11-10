package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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

import movieproject.DBconnect;
import movieproject.CustomUI;

public class Admin extends JFrame implements ActionListener{
	private JButton add, movie_time, poster, del;
	private JPanel main;

	public Admin(String Title, int width, int height) {
		this.setTitle(Title);
		setSize(width, height);
		//setLocation(475, 300);
		setLocationRelativeTo(this);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		main = new JPanel();
		main.setBackground(Color.WHITE);
		
		CustomUI cus = new CustomUI(main);
	
		add = cus.setBtnWhite("add", "영화추가", 100);
		add.addActionListener(this);
		main.add(add);
		
		movie_time = cus.setBtnWhite("movie_time", "기존영화 시간추가", 100);
		movie_time.addActionListener(this);
		main.add(movie_time);
		
		poster = cus.setBtnWhite("Poster", "포스터 추가", 100);
		poster.addActionListener(this);
		main.add(poster);
		
		del = cus.setBtnWhite("del", "영화삭제", 100);
		del.addActionListener(this);
		main.add(del);
		
		add(main);
		
		setVisible(true);
		main.requestFocus();		
	}
	
	
	public static void main(String[] args) {
		DBconnect.DB();
		new Admin("관리자용",600, 110);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == add) {
			new MovieAdd();
		}
		else if(ob == poster) {
			new AddURL("URL추가",570, 140);
		}
		else if(ob == movie_time) {
			new AddmoiveTime("기존 영화시간 추가",370, 220);
		}
		else if(ob == del) {
			new RemoveMovie("영화 삭제",420, 150);
		}
	}

}
