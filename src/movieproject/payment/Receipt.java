package movieproject.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import movieproject.DBconnect;
import movieproject.movie.SeatSelection;

public class Receipt extends JFrame implements ActionListener {
	
	private JPanel panel1, panel2, panel3, panel4, panel5;
	private JLabel lblcheck;
	private JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, movieName, memInfo, movietime, movieSeat, moviePay, movieGrade;
	private JButton btn1;
	Payment main;
	public Receipt(String title, int width, int height, Payment main) {
		this.main = main;
		setTitle(title);
		setSize(width, height);
//		setLocation(800, 300);
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		setLayout(new BorderLayout());
		
		
			//로고
			panel1 = new JPanel();
			panel1.setBackground(Color.pink);
			lblcheck = new JLabel("영수증");
			lblcheck.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			
			panel1.add(lblcheck);
			
			//예매 정보
			
			panel2 = new JPanel();
			panel2.setLayout(new GridLayout(5, 1, 10, 10));
			panel2.setBorder(new EmptyBorder(20, 20, 20, 20));
			
			lbl1 = new JLabel("상영 정보");
			lbl2 = new JLabel("회원 정보");
			lbl3 = new JLabel("날짜 정보");
			lbl4 = new JLabel("좌석 정보");
			lbl5 = new JLabel("결제 금액");
			
			lbl1.setForeground(Color.gray);
			lbl1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			
			lbl2.setForeground(Color.gray);
			lbl2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			
			lbl3.setForeground(Color.gray);
			lbl3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			
			lbl4.setForeground(Color.gray);
			lbl4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
			
			lbl5.setForeground(Color.gray);
			lbl5.setFont(new Font("맑은 고딕", Font.BOLD, 12));

			panel2.add(lbl1);
			panel2.add(lbl2);
			panel2.add(lbl3);
			panel2.add(lbl4);
			panel2.add(lbl5);
			
			// 가운데
			panel3 = new JPanel();
			panel3.setLayout(new GridLayout(5, 1, 10, 10));
			panel3.setBorder(new EmptyBorder(20, 20, 20, 20));
			
			String sMovieName = main.getsTitle();
			String sMemInfo = main.getsMemInfo();
			String sMovieTime = main.getsDate();
			String sMovieSeat = main.getsSeat();
			String sMoviePay = main.getsPay();
			
			
			movieName = new JLabel(sMovieName);
			memInfo = new JLabel(sMemInfo);
			movietime = new JLabel(sMovieTime);
			movieSeat = new JLabel(sMovieSeat);
			moviePay = new JLabel(sMoviePay);
			
//			String sql = "SELECT RES_PAY, ID, MOVIE_NAME, \"TIME\", DATES\r\n"
//					+ "FROM AMOVIE.RESERVATION";
//		    ResultSet re = DBconnect.getResultSet(sql);
//		    try {
//				while (re.next()) {
//					movieName.setText(re.getString("MOVIE_NAME"));
//					memInfo.setText(re.getString("ID"));
//					movietime.setText(re.getString("TIME")+"  "+re.getString("DATES"));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			panel3.add(movieName);
			panel3.add(memInfo);
			panel3.add(movietime);
			panel3.add(movieSeat);
			panel3.add(moviePay);
			
			//오른쪽 공백
			panel4 = new JPanel();
			panel4.setLayout(new GridLayout(6, 1, 10, 10));
			panel4.setBorder(new EmptyBorder(20, 20, 20, 20));
			
			
			//버튼
			panel5 = new JPanel();
			btn1 = new JButton("확인 완료 및 종료");
			panel5.add(btn1);
			Color a = new Color(255, 228, 225);
			btn1.setBackground(a);
			btn1.addActionListener(this);
			
			
			add(panel1, BorderLayout.NORTH);
			add(panel2, BorderLayout.WEST);
			add(panel3, BorderLayout.CENTER);
			add(panel4, BorderLayout.EAST);
			add(panel5, BorderLayout.SOUTH);
		
		setVisible(true); // 이게 없으면 창이 뜨지 않음 
	}

	public static void main(String[] args) {
		DBconnect.DB();
		//new Receipt("예매 확인", 400, 400, this); // title, width, height
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==btn1) {
			System.exit(0);
			
		}
	}

}