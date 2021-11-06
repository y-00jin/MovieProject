package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import movieproject.DBconnect;
import movieproject.controller.Controller;
import movieproject.login.TextHint;
import movieproject.login.login;
import movieproject.util.Style;

public class MemberModify extends JFrame implements ActionListener {

	Controller controller;
	public JPanel jp1, jp2, jp3, jp4, jp5;
	public JLabel lbl1, lblBk = new JLabel(), lblBk1, lblBk2, lblId, lblPw, lblPw2, lblName, lblPhone;
	public JButton btnOK, btn1, btn2, btn3;
	public JTextField tfPw, tfPw2, tfName, tfPhone;
	public JLabel lblIdFix;
	public ImageIcon img;

	public static Connection conn;
	private Main log;
	private Font tfFont;
	private Style style = new Style();
	private String  uName="", uPhone="";
	private boolean userCheck;
	private String uId;

	public MemberModify() {

		controller = Controller.getInstance();
		uId = controller.getUserId();
		setTitle("INHA CINEMA");
		setSize(600, 400);
		setLocationRelativeTo(this); // 가운데로
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //모든 프레임 닫기

		// 전체 레이아웃
		setLayout(new BorderLayout());

		// 패널1
		jp1 = new JPanel();
		jp1.setBackground(Color.WHITE);
		jp1.setBackground(Color.pink);

		lbl1 = new JLabel("INHA CINEMA");
		lbl1.setFont(new Font("1훈새마을운동 R", Font.BOLD, 25));

		jp1.add(lbl1); // add
		// jp1.add(lblImg);
		add(jp1, BorderLayout.NORTH);

		// 패널2
		jp2 = new JPanel();
		jp2.setBackground(Color.WHITE);
		jp2.setLayout(new GridLayout(5, 1, 10, 10));
		jp2.setBorder(new EmptyBorder(20, 20, 20, 20));

		String tfIdFix = controller.getUserId();

		// String tfIdFix = "1";
		// tfId = new JTextField(tfIdFix);
		lblIdFix = new JLabel(tfIdFix);
		style.lblFont(lblIdFix, Font.CENTER_BASELINE, 20);

		
		tfPw = new JTextField();
		style.tfFont(tfPw, Font.PLAIN, 13);
		TextHint hint2 = new TextHint(tfPw, "비밀번호");

		tfPw2 = new JTextField();
		style.tfFont(tfPw2, Font.PLAIN, 13);
		TextHint hint3 = new TextHint(tfPw2, "비밀번호"); // 재확인

		tfName = new JTextField();
		style.tfFont(tfName, Font.PLAIN, 13);
		TextHint hint4 = new TextHint(tfName, "이름");

		tfPhone = new JTextField();
		style.tfFont(tfPhone, Font.PLAIN, 13);
		TextHint hint5 = new TextHint(tfPhone, "휴대전화");

		jp2.add(lblIdFix); // add
		jp2.add(tfPw);
		jp2.add(tfPw2);
		jp2.add(tfName);
		jp2.add(tfPhone);
		add(jp2, BorderLayout.CENTER);

		// 패널3
		jp3 = new JPanel();
		jp3.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
		jp3.setBackground(Color.WHITE);

		btnOK = new JButton("수정완료");
		btnOK.addActionListener(this);
		style.btnFont(btnOK, Font.PLAIN, 13);
		Color a = new Color(255, 228, 225);
		btnOK.setBackground(a);
		// btnOK.setPreferredSize(new Dimension(100, 40));

		jp3.add(btnOK);
		add(jp3, BorderLayout.SOUTH); // add

		// 패널4
		jp4 = new JPanel();
		jp4.setBackground(Color.WHITE);
		jp4.setLayout(new GridLayout(5, 1, 10, 10));
		jp4.setBorder(new EmptyBorder(20, 20, 20, 20));

//      btn1 = new JButton("중복확인");
//      btn1.setBackground(a);

		lblBk1 = new JLabel(" "); // 빈칸
		btn2 = new JButton("일치");
		style.btnFont(btn2, Font.PLAIN, 13);
		btn2.addActionListener(this);
		btn2.setBackground(a);
		lblBk2 = new JLabel(" "); // 빈칸

		jp4.add(lblBk);
		jp4.add(lblBk1);
		jp4.add(btn2);
		jp4.add(lblBk2);
		add(jp4, BorderLayout.EAST);

		// 패널5
		jp5 = new JPanel();
		jp5.setBackground(Color.WHITE);
		jp5.setLayout(new GridLayout(5, 1, 10, 10));
		jp5.setBorder(new EmptyBorder(20, 20, 20, 20));

		lblId = new JLabel("아이디");
		style.lblFont(lblId, Font.CENTER_BASELINE, 13);

		lblPw = new JLabel("비밀번호");
		style.lblFont(lblPw, Font.CENTER_BASELINE, 13);

		lblPw2 = new JLabel("비밀번호 재확인");
		style.lblFont(lblPw2, Font.CENTER_BASELINE, 13);

		lblName = new JLabel("이름");
		style.lblFont(lblName, Font.CENTER_BASELINE, 15);

		lblPhone = new JLabel("휴대전화");
		style.lblFont(lblPhone, Font.CENTER_BASELINE, 15);

		jp5.add(lblId);
		jp5.add(lblPw);
		jp5.add(lblPw2);
		jp5.add(lblName);
		jp5.add(lblPhone);
		add(jp5, BorderLayout.WEST);

		setVisible(true);
	}

	public static void main(String[] args) {
		
		new MemberModify();
	}

	public void fontStyle(JLabel lbl, int size) {
		lbl.setFont(new Font("1훈새마을운동 R", Font.CENTER_BASELINE, size));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = lblIdFix.getText();
		String pw = tfPw.getText();
		String pw2 = tfPw2.getText();
		String name = tfName.getText();
		String phone = tfPhone.getText();

		Object obj = e.getSource();

		if (obj == btn2) {
			if (tfPw.getText().equals("비밀번호") && tfPw2.getText().equals("비밀번호")) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.", "비밀번호 입력", JOptionPane.ERROR_MESSAGE);
			} else if (tfPw.getText().equals(tfPw2.getText())) {
				btn2.setText("일치 !");
			} else {
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요.", "비밀번호 입력", JOptionPane.ERROR_MESSAGE);
				btn2.setText("일치");
			}
		}

		if (obj == btnOK) {
			if (btn2.getText() == "일치 !") {

				// 회원 데이터 수정
				String insertSQL = "UPDATE MOVIE_MEMBER " + "SET PW='" + pw + "', " + "NAME='" + name + "', "
						+ "PHONE='" + phone + "'" + "WHERE ID='" + id + "'";

				DBconnect.getupdate(insertSQL);
				JOptionPane.showMessageDialog(null, "회원정보 수정완료");
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "일치 오류", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}