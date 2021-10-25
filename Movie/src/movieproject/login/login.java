package movieproject.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;//
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import movieproject.DBconnect;
import movieproject.admin.Admin;
import movieproject.movie.movietiketing;


public class login extends JFrame implements ActionListener {
		
	public static Connection conn;
	public JPanel panel1, panel2, panel3, panel4;
	public JLabel lbllog, lbljoin, lblmanager;
	public JTextField tfid;
	public JPasswordField tfpw;
	public JButton btnlogin, btnjoin, btnmanager;
	private JLabel lblID;
	private JLabel lblPW;

	public login(String title, int width, int height) {
		setTitle(title);
		setSize(width, height);
//		setLocation(800, 300);
		setLocationRelativeTo(this); //모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
				
		//전체
		setLayout(new BorderLayout());
	
		//로고
		panel1 = new JPanel();
		panel1.setBackground(Color.pink);
		lbllog = new JLabel("INHA CINEMA");
		lbllog.setFont(new Font("Segoe UI Black", Font.BOLD, 30));
		
		panel1.add(lbllog);
		
		//로그인
		panel2 = new JPanel();
		panel2.setBackground(Color.white);
		panel2.setLayout(new GridLayout(3, 1, 10, 10));
		panel2.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		
		//panel2.setLayout(new FlowLayout(10, 100, 20));
		tfid = new JTextField();
		TextHint hint1 = new TextHint(tfid, "아이디");
		tfid.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		
		tfpw = new JPasswordField();
		TextHint hint2 = new TextHint(tfpw, "비밀번호");
		
		tfpw.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		btnlogin = new JButton("로그인");
		btnlogin.addActionListener(this);
		

		btnlogin.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		Color a = new Color(255, 228, 225);
		//btnlogin.setBackground(a);
		btnlogin.setBackground(a);
		btnlogin.setPreferredSize(new Dimension(396, 35));
		
		panel2.add(tfid);
		panel2.add(tfpw);
		panel2.add(btnlogin);
	
		
		//회원가입
		panel3 = new JPanel();
		panel3.setBackground(Color.white);
		panel3.setLayout(new GridLayout(4, 1, 10, 10));
		panel3.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		lbljoin = new JLabel("아이디가 없으신가요?");
		lbljoin.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		btnjoin = new JButton("회원가입");
		btnjoin.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		btnjoin.setBackground(a);
		btnjoin.setPreferredSize(new Dimension(396, 35));
		btnjoin.addActionListener(this);
		
		panel3.add(lbljoin);
		panel3.add(btnjoin);
		
		//관리자 로그인
				
		lblmanager = new JLabel("관리자로 로그인 하시겠습니까?");
		lblmanager.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 15));
		btnmanager = new JButton("관리자 로그인");
		btnmanager.addActionListener(this);
		btnmanager.setFont(new Font("맑은 고딕", Font.CENTER_BASELINE, 13));
		btnmanager.setBackground(a);
		btnmanager.setPreferredSize(new Dimension(396, 35));

		panel3.add(lblmanager);
		panel3.add(btnmanager);
	
		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.CENTER);
		add(panel3, BorderLayout.SOUTH);
		
		setVisible(true); // 이게 없으면 창이 뜨지 않음 
	}

	public static void main(String[] args) {
		DBconnect.DB();
		new login("로그인", 600, 500); // title, width, height
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnjoin) {
			new MovieJoin("회원가입", 600, 400);
		}
		
		if(obj == btnlogin) {
			String id = tfid.getText();
			String pw = tfpw.getText();
			//System.out.println(id + " : " + pw);
			
			boolean check = checkIDPW(id, pw);
			if(check) {
				//System.out.println("로그인 성공");
			    new movietiketing("좌석선택", 900, 600, this);
				this.dispose();
				
			} else {
				System.out.println("로그인 실패");
				JOptionPane.showMessageDialog(null, "로그인 실패했습니다.");
				tfid.setText("");
				tfpw.setText("");
				tfid.requestFocus();
			}
			
		}
		
		if(obj == btnmanager) {
			String managerlogin = JOptionPane.showInputDialog("관리자 핀번호를 입력하세요.");
			int pin = Integer.parseInt(managerlogin);
			if(pin == 1234) {
				new Admin("관리자용",600, 110);
				//System.out.println("관리자 접속");
			} else {
				JOptionPane.showMessageDialog(null, "관리자가 핀번호가 아닙니다.", "핀번호 오류", 
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	private boolean checkIDPW(String id, String pw) {
		
		boolean check = false;
		String sql = "SELECT * FROM MEMBER WHERE ID = '" + id + "' AND PW='" + pw + "'";
		ResultSet rs = DBconnect.getResultSet(sql);
		try {
			if(rs.next()) {
				System.out.println(rs.getString(1));
				check = true;
			} else {
				System.out.println("해당 사용자가 없습니다.");
				check = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return check;
	}

	public JTextField getTfid() {
		return tfid;
	}

	public JLabel getLblID() {
		return lblID;
	}

}
