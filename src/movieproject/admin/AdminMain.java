package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import movieproject.login.login;

public class AdminMain extends JFrame implements ActionListener{

	
	private JPanel pMain;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnLogout;
	private JPanel pCenter;
	private JButton btnProfile;
	private ArrayList<JButton> arrBtnProfile = new ArrayList<>();



	public AdminMain() {
		setTitle("INHA CINEMA");
		setSize(900, 600);
		setLocationRelativeTo(this); //모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		
		// 메인 패널 생성
		pMain = new JPanel();
		pMain.setLayout(new BorderLayout());
		pMain.setBackground(Color.white);
		
		// 로고, 로그아웃 넣을 패널
		pTitle = new JPanel();
		pTitle.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		pTitle.setBackground(new Color(0xCC3D3D));
		pTitle.setLayout(new BorderLayout());
		
		// 로고
		lblTitle = new JLabel("INHA CINEMA _ ADMIN");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 25));
		lblTitle.setForeground(Color.white);
		
		// 로그아웃
		btnLogout = new JButton("로그아웃");
		btnLogout.setFont(new Font("1훈새마을운동 R", Font.PLAIN, 18));
		btnLogout.setForeground(Color.white);
		BtnStyle(btnLogout);
		
		pTitle.add(lblTitle, BorderLayout.WEST);
		pTitle.add(btnLogout, BorderLayout.EAST);
		
		// 센터 패널
		pCenter = new JPanel();
		pCenter.setBackground(Color.black);
		//pCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		
		pCenter.setLayout(new GridLayout(1,4));
		
		for(int i=0;i<4;i++) {
			//ImageIcon ImgProfile = new ImageIcon("resource/image/profile"+ (i+1) +".jpg");
			ImageIcon ImgProfile = new ImageIcon("resource/image/adminProfile"+ (i+1) +".png");
			
			Image changeImgProfile = ImgProfile.getImage().getScaledInstance(130, 180, Image.SCALE_SMOOTH);
			ImageIcon iconProfile = new ImageIcon(changeImgProfile);
			
			btnProfile = new JButton(iconProfile);
			BtnStyle(btnProfile);
			
			arrBtnProfile.add(btnProfile);
			pCenter.add(btnProfile);
		}
		
		
		
		pMain.add(pTitle, BorderLayout.NORTH);
		pMain.add(pCenter, BorderLayout.CENTER);
		
		add(pMain);
		
		setVisible(true);
	}
	
	// 버튼 스타일
	public void BtnStyle(JButton btn) {
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);
		btn.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new AdminMain();
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==btnLogout) {
			new login();
			dispose();
		}
		else {
			if(obj == arrBtnProfile.get(0)) {	// 영화 목록
				
			}
			if(obj == arrBtnProfile.get(1)) {	// 영화 관리
				
			}
			if(obj == arrBtnProfile.get(2)) {	// 영화 시간 관리
				
			}
			if(obj == arrBtnProfile.get(3)) {	// 주문 확인
				
			}
		}
		
	}

}
