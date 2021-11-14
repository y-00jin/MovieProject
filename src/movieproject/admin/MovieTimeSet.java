package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import movieproject.controller.Controller;
import movieproject.util.Style;

public class MovieTimeSet extends JFrame implements ActionListener {

	Controller controller;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JPanel pCenter;
	
	public MovieTimeSet(){
		
		controller = Controller.getInstance();
		
		setTitle("INHA CINEMA");
		setSize(900, 600);
		setLocationRelativeTo(this); //모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		setLayout(new BorderLayout());
		
		// 타이틀 생성
		addPTitle();
		
		
		// 가운데 패널 생성
		addPCenter();
		
		add(pTitle, BorderLayout.NORTH);
		add(pCenter, BorderLayout.CENTER);
		
		setVisible(true);
	}
	

	private void addPTitle() {
		
		pTitle = new JPanel();
		pTitle.setBackground(Color.pink);
		pTitle.setLayout(new BorderLayout());
		pTitle.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
		
		// 타이틀 라벨
		lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 20));
		lblTitle.setForeground(Color.white);
		pTitle.add(lblTitle, BorderLayout.WEST);
		
		
		// 돌아가기 버튼
		btnBack = new JButton("돌아가기");
		Style.btnFont(btnBack, Font.PLAIN, 15);
		btnBack.setForeground(Color.white);
		btnBack.setBackground(Color.pink);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(this);
		pTitle.add(btnBack, BorderLayout.EAST);
		
	}

	
	
	private void addPCenter() {
		pCenter = new JPanel();
		pCenter.setLayout(new BorderLayout());
		
		
		
	}

	public static void main(String[] args) {
		new MovieTimeSet();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
