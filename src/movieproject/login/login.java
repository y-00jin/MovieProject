package movieproject.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import movieproject.DBconnect;
import movieproject.admin.AdminMain;
import movieproject.controller.Controller;
import movieproject.movie.UserMain;

public class login extends JFrame implements ActionListener {

	Controller controller;
	public static Connection conn;
	public JPanel panel1, panel2, panel3, panel4;
	public JLabel lbllog, lbljoin, lblmanager;
	public JTextField tfid;
	public JPasswordField tfpw;
	public JButton btnlogin, btnjoin, btnmanager;
	private JLabel lblID;
	private JLabel lblPW;
	private JPanel p2Img;
	private JPanel pLogin;
	private JLabel lblUser;
	private String strMovieTimeId;
	private String strMovieDate;
	private Date tempDate;
	private Date tempDate1;
	private String strReservId;
	private String strReservMovieDate;
	private Date tempReservDate;

	public login() {

		delete();

		controller = Controller.getInstance();

		setTitle("INHA CINEMA");
		setSize(1000, 700);
//		setLocation(800, 300);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료

		// 전체
		setLayout(new BorderLayout());
		setBackground(Color.white);

		// 로고
		panel1 = new JPanel();
		panel1.setBackground(Color.pink);
		lbllog = new JLabel("INHA CINEMA");
		lbllog.setForeground(Color.black);
		lbllog.setFont(new Font("1훈새마을운동 R", Font.BOLD, 30));

		panel1.add(lbllog);

		// 로그인
		panel2 = new JPanel();
		panel2.setBackground(Color.white);
		panel2.setLayout(new BorderLayout());
		panel2.setBorder(new EmptyBorder(30, 100, 50, 100));

		// 이미지 추가
		p2Img = new JPanel();
		p2Img.setBorder(new EmptyBorder(0, 0, 40, 0));
		p2Img.setBackground(Color.white);

		ImageIcon userIcon = new ImageIcon("resource/image/user1.png");
		Image changeUserIcon = userIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
		ImageIcon lblIUserIcon = new ImageIcon(changeUserIcon);
		lblUser = new JLabel(lblIUserIcon);
		p2Img.add(lblUser);

		// 로그인 정보 입력 패널
		pLogin = new JPanel();
		pLogin.setLayout(new GridLayout(3, 1, 25, 25));
		pLogin.setBackground(Color.white);

		// 아이디
		tfid = new JTextField();
		TextHint hint1 = new TextHint(tfid, "아이디");
		tfid.setFont(new Font("1훈새마을운동 R", Font.CENTER_BASELINE, 15));

		// 비밀번호
		tfpw = new JPasswordField();
		TextHint hint2 = new TextHint(tfpw, "비밀번호");
		tfpw.setFont(new Font("맑은고딕", Font.CENTER_BASELINE, 15));

		// 로그인 버튼
		btnlogin = new JButton("로그인");
		btnlogin.addActionListener(this);
		btnlogin.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 15));
		Color a = new Color(255, 228, 225);
		// btnlogin.setBackground(a);
		btnlogin.setBackground(a);
		// btnlogin.setPreferredSize(new Dimension(396, 35));

		pLogin.add(tfid);
		pLogin.add(tfpw);
		pLogin.add(btnlogin);

		panel2.add(p2Img, BorderLayout.NORTH);
		panel2.add(pLogin, BorderLayout.CENTER);

		// 회원가입
		panel3 = new JPanel();
		panel3.setBackground(Color.white);
		panel3.setLayout(new GridLayout(4, 1, 5, 5));
		panel3.setBorder(new EmptyBorder(0, 100, 30, 100));

		lbljoin = new JLabel("아이디가 없으신가요?");
		lbljoin.setFont(new Font("1훈새마을운동 R", Font.CENTER_BASELINE, 15));

		btnjoin = new JButton("회원가입");
		btnjoin.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 13));
		btnjoin.setBackground(a);
		btnjoin.setPreferredSize(new Dimension(396, 35));
		btnjoin.addActionListener(this);

		panel3.add(lbljoin);
		panel3.add(btnjoin);

		// 관리자 로그인
		lblmanager = new JLabel("관리자로 로그인 하시겠습니까?");
		lblmanager.setFont(new Font("1훈새마을운동 R", Font.CENTER_BASELINE, 15));

		btnmanager = new JButton("관리자 로그인");
		btnmanager.addActionListener(this);
		btnmanager.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 13));
		btnmanager.setBackground(a);
		btnmanager.setPreferredSize(new Dimension(396, 35));

		panel3.add(lblmanager);
		panel3.add(btnmanager);

		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.CENTER);
		add(panel3, BorderLayout.SOUTH);

		setVisible(true); // 이게 없으면 창이 뜨지 않음
	}

	private void delete() {

		DBconnect.DB();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String date = formatter.format(today);
		
		Date setDate = null;
		try {
			setDate = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar calToday = new GregorianCalendar(Locale.KOREA);
		calToday.setTime(setDate);
		
		
		// TIME테이블에서 오늘 날짜 전 데이터는 삭제
		String selectTime = "select MOVIE_TIME_ID, TO_CHAR(MOVIE_DATE , 'YYYYMMDD') from MOVIE_TIME";
		
		ResultSet re = DBconnect.getResultSet(selectTime);
		try {
			while (re.next()) {

				strMovieTimeId = re.getString(1);
				strMovieDate = re.getString(2);

				try {
					
					tempDate = formatter.parse(strMovieDate);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Calendar calSelect = new GregorianCalendar(Locale.KOREA);
				calSelect.setTime(tempDate);
				
				if(calToday.compareTo(calSelect) == 1) {

					String del = "DELETE FROM MOVIE_TIME WHERE MOVIE_TIME_ID='" + strMovieTimeId + "'";
					DBconnect.getupdate(del);
				}
				
			}
		} catch (SQLException e1) {
			//e1.printStackTrace();
		}
		
		
		
		// RESERVATION 테이블에서 오늘 날짜 전 데이터는 삭제
		String selectReserv = "select RESERVATION_ID, TO_CHAR(MOVIE_DATE , 'YYYYMMDD') from MOVIE_RESERVATION";
		ResultSet re1 = DBconnect.getResultSet(selectReserv);
		try {
			while (re1.next()) {

				strReservId = re1.getString(1);
				strReservMovieDate = re1.getString(2);

				try {
					
					tempReservDate = formatter.parse(strReservMovieDate);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Calendar calSelect = new GregorianCalendar(Locale.KOREA);
				calSelect.setTime(tempReservDate);
				
				if(calToday.compareTo(calSelect) == 1) {

					String del = "DELETE FROM MOVIE_RESERVATION WHERE RESERVATION_ID='" + strReservId + "'";
					DBconnect.getupdate(del);
				
				}
				
			}
		} catch (SQLException e1) {
			//e1.printStackTrace();
		}
		

	}

	public static void main(String[] args) {
		DBconnect.DB();
		new login(); // title, width, height
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnjoin) {
			new MovieJoin("회원가입");
		}

		if (obj == btnlogin) {
			String id = tfid.getText();
			String pw = tfpw.getText();

			boolean check = controller.checkIDPW(id, pw); // Controller에 id값 저장함
			if (check) {
				// System.out.println("로그인 성공");
				// new Main(this);
				new UserMain();
				this.dispose();

			} else {
				JOptionPane.showMessageDialog(null, "로그인 실패했습니다.");
				tfid.setText("");
				tfpw.setText("");
				tfid.requestFocus();
			}

		}

		if (obj == btnmanager) {
			String managerlogin = JOptionPane.showInputDialog("관리자 핀번호를 입력하세요.");
			if (managerlogin.equals("****")) {
				controller.userId ="admin";
				new AdminMain();
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "관리자가 핀번호가 아닙니다.", "핀번호 오류", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public JTextField getTfid() {
		return tfid;
	}

	public JLabel getLblID() {
		return lblID;
	}

}
