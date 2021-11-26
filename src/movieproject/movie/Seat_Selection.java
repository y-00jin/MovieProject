package movieproject.movie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import movieproject.DBconnect;
import movieproject.controller.Controller;

public class Seat_Selection extends JFrame implements MouseListener, ActionListener {
	private Controller con = Controller.getInstance();
	JLabel Ala[] = new JLabel[25];
	private int Aseat[] = new int[25];
	private int Bseat[] = new int[25];
	private int Cseat[] = new int[25];
	JLabel Bla[] = new JLabel[25];
	JLabel Cla[] = new JLabel[25];
	private JPanel main_panel;
	private String posurl;
	private URL url;
	private ImageIcon img;
	private BufferedImage im;
	private JLabel poster;
	private JLabel personnel;
	private int person_cnt = 0;
	private JButton back;
	private JButton next;
	Font font1 = new Font("맑은 고딕", Font.PLAIN, 18);
	private JPanel seat_panel; 
	public Seat_Selection() {
		DBconnect.DB();
		setTitle("INHA CINEMA");
		setSize(1100, 600);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료

		main_panel = new JPanel();
		// main_panel = new JPanel();
//	    main_panel.setLayout(new BorderLayout());
		main_panel.setLayout(null);
		main_panel.setBackground(Color.WHITE);
		add(main_panel);

		print g = new print();
		g.setBounds(350, -30, 10, 700);

		///////////////////////////////// 좌측
		addtitle();
		addposter();
		addmovie_info();
		addbtn();
		///////////////////////////// 우측
		addseat_panel();
		creatseat();
		seatcheck();
		
		main_panel.add(g);
		setVisible(true);
	}
	private void seatcheck() {
		String t = new String();
		String sql2 = "SELECT SEAT FROM MOVIE_RESERVATION mr "
				+ "WHERE mr.MOVIE_NAME = '"+ con.getMovieName() +"' "
				+ "AND mr.MOVIE_DATE = '"+ con.getSel_date() + "' "
				+ "AND mr.MOVIE_TIME = '" + con.getSel_time() + "'";
		System.out.println(sql2);
		ResultSet r = DBconnect.getResultSet(sql2);
		try {
			while(r.next()) {
				t = r.getString(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String seat_temp[] = t.split(",");
		String temp = "";
		for(int i = 0; i < seat_temp.length; i++) {
			if(seat_temp[i].contains("A")) {
				temp = seat_temp[i].replace("A", "");
				temp = temp.trim();
				Ala[Integer.parseInt(temp) - 1].setForeground(Color.RED);
				Aseat[Integer.parseInt(temp) - 1] = 2;
				System.out.println(temp);
			}
			else if(seat_temp[i].contains("B")) {
				temp = seat_temp[i].replace("B", "");
				temp = temp.trim();
				Bla[Integer.parseInt(temp) - 1].setForeground(Color.RED);
				Bseat[Integer.parseInt(temp) - 1] = 2;
				System.out.println(temp);
			} 
			else if(seat_temp[i].contains("C")) {
				temp = seat_temp[i].replace("C", "");
				temp = temp.trim();
				Cla[Integer.parseInt(temp) - 1].setForeground(Color.RED);
				Cseat[Integer.parseInt(temp) - 1] = 2;
				System.out.println(temp);
			}
		}
	}
	private void creatseat() {
		JPanel A = new JPanel();
		A.setLayout(new GridLayout(5, 5));
		for (int i = 0; i < Ala.length; i++) {
			Ala[i] = new JLabel(" A" + Integer.toString(i + 1));
			Ala[i].addMouseListener(this);
			Ala[i].setFont(font1);
			A.add(Ala[i]);
		}
		JPanel B = new JPanel();
		B.setLayout(new GridLayout(5, 5));
		for (int i = 0; i < Bla.length; i++) {
			Bla[i] = new JLabel(" B" + Integer.toString(i + 1));
			Bla[i].addMouseListener(this);
			Bla[i].setFont(font1);
			B.add(Bla[i]);
		}
		JPanel C = new JPanel();
		C.setLayout(new GridLayout(5, 5));
		for (int i = 0; i < Cla.length; i++) {
			Cla[i] = new JLabel(" C" + Integer.toString(i + 1));
			Cla[i].addMouseListener(this);
			Cla[i].setFont(font1);
			C.add(Cla[i]);
		}
		A.setBackground(Color.WHITE);
		B.setBackground(Color.WHITE);
		C.setBackground(Color.WHITE);
		seat_panel.add(A);
		seat_panel.add(B);
		seat_panel.add(C);
	}
	private void addseat_panel() {
		seat_panel = new JPanel();
		seat_panel.setBackground(Color.white);
		seat_panel.setBounds(380, 50, 670, 450);
		seat_panel.setLayout(new GridLayout(1, 3, 10, 0));
		seat_panel.setBorder(new TitledBorder(new LineBorder(Color.black, 3)));
		main_panel.add(seat_panel);
	}
	private void addbtn() {
		back = new JButton("이전");
		back.setForeground(Color.black);
		back.setBackground(Color.white);
		back.setBounds(70, 470, 80, 30);
		back.addActionListener(this);
		
		next = new JButton("다음");
		next.setForeground(Color.black);
		next.setBackground(Color.white);
		next.setBounds(200, 470, 80, 30);
		next.addActionListener(this);
		
		main_panel.add(back);
		main_panel.add(next);
	}
	private void addmovie_info() {
		JLabel movie_name = new JLabel(con.getMovieName());
		movie_name.setFont(new Font("배달의민족 도현", Font.PLAIN, 15));
		movie_name.setBounds(10, 310, 320, 30);
		movie_name.setHorizontalAlignment(JLabel.CENTER);
		
//		con.sel_date = "20211122";
//		con.sel_time = "14:00";
		
		JLabel movie_date = new JLabel("날짜   " +con.sel_date);
		movie_date.setFont(new Font("배달의민족 도현", Font.PLAIN, 15));
		movie_date.setBounds(10, 340, 320, 30);
		movie_date.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel movie_time = new JLabel("시간   " +con.sel_time);
		movie_time.setFont(new Font("배달의민족 도현", Font.PLAIN, 15));
		movie_time.setBounds(10, 370, 320, 30);
		movie_time.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel person = new JLabel("인원  :  ");
		person.setFont(new Font("배달의민족 도현", Font.PLAIN, 15));
		person.setBounds(135, 400, 80, 30);
		
		personnel = new JLabel(Integer.toString(person_cnt));
		personnel.setFont(new Font("배달의민족 도현", Font.PLAIN, 15));
		personnel.setBounds(152, 400, 80, 30);
		personnel.setHorizontalAlignment(JLabel.CENTER);
		
		main_panel.add(movie_name);
		main_panel.add(movie_date);
		main_panel.add(movie_time);
		main_panel.add(person);
		main_panel.add(personnel);
//		person_cnt++;
//		personnel.setText(Integer.toString(person_cnt));
//		personnel.revalidate();
//		personnel.repaint();
	}	

	private void addposter() {
//		con.setMovieName("귀멸의 칼날: 남매의 연");
		String postersql = "select url from MOVIE where movie_name = '" + con.getMovieName() + "'";
		ResultSet re = DBconnect.getResultSet(postersql);
		try {
			while (re.next()) {
				posurl = re.getString(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println(posurl);
		if (posurl == null)
			posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";
//        System.out.println(posurl);
		try {
			url = new URL(posurl);
			im = ImageIO.read(url);

			try {
				ImageIcon con = new ImageIcon(im);

				Image ximg = con.getImage();
				Image yimg = ximg.getScaledInstance(180, 220, java.awt.Image.SCALE_SMOOTH);
				img = new ImageIcon(yimg);
			} catch (Exception eE) {

			}
		} catch (Exception ee) {
			posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";
			try {
				url = new URL(posurl);
				im = ImageIO.read(url);

				ImageIcon con = new ImageIcon(im);
				Image ximg = con.getImage();
				Image yimg = ximg.getScaledInstance(180, 220, java.awt.Image.SCALE_SMOOTH);
				img = new ImageIcon(yimg);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		poster = new JLabel(img);
		poster.setBounds(85, 80, 180, 200);
		
		main_panel.add(poster);
	}

	private void addtitle() {
		JLabel lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 21));
		lblTitle.setForeground(Color.pink);
//		lblTitle.setBackground(Color.cyan);
		lblTitle.setBounds(10, 0, 150, 50);

		main_panel.add(lblTitle);
	}

	class print extends JLabel {
		public void paintComponent(Graphics g) {
			g.drawLine(0, 0, 0, 700);
		}
	}

	public static void main(String[] args) {
		new Seat_Selection();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		String save = "";
		String saveA = "";
		String saveB = "";
		String saveC = "";
		if(ob == next) {
			for (int i = 0; i < 25; i++) {
				if (Aseat[i] == 1) {
					Ala[i].setForeground(Color.red);
					save += "A" + Integer.toString(i + 1) + ", ";
					Aseat[i] = 2;
				}
			}
			for (int i = 0; i < 25; i++) {
				if (Bseat[i] == 1) {
					Bla[i].setForeground(Color.red);
					save += "B" + Integer.toString(i + 1) + ", ";
					Bseat[i] = 2;
				}
			}
			for (int i = 0; i < 25; i++) {
				if (Cseat[i] == 1) {
					Cla[i].setForeground(Color.red);
					save += "C" + Integer.toString(i + 1) + ", ";
					Cseat[i] = 2;
				}
			}
			save = save.substring(0, save.length()-2);
			System.out.println(save);
			con.setSeat(save);
			new Shop();
			con.frame.add(this);
		}
		if(ob == back) {
			dispose();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Object ob = e.getSource();
		for (int i = 0; i < 25; i++) {
			if (ob == Ala[i]) {
				if (Aseat[i] == 0) {
					person_cnt++;
					Ala[i].setForeground(Color.CYAN);
					Aseat[i] = 1;
				} else if (Aseat[i] == 1) {
					person_cnt--;
					Ala[i].setForeground(Color.black);
					Aseat[i] = 0;
				}
			} else if (ob == Bla[i]) {
				if (Bseat[i] == 0) {
					person_cnt++;
					Bla[i].setForeground(Color.CYAN);
					Bseat[i] = 1;
				} else if (Bseat[i] == 1) {
					person_cnt--;
					Bla[i].setForeground(Color.black);
					Bseat[i] = 0;
				}
			} else if (ob == Cla[i]) {
				if (Cseat[i] == 0) {
					person_cnt++;
					Cla[i].setForeground(Color.CYAN);
					Cseat[i] = 1;
				} else if (Cseat[i] == 1) {
					person_cnt--;
					Cla[i].setForeground(Color.black);
					Cseat[i] = 0;
				}
			}
			personnel.setText(Integer.toString(person_cnt));
			personnel.revalidate();
			personnel.repaint();
		}		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
