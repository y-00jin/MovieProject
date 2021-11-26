package movieproject.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import movieproject.DBconnect;
import movieproject.controller.Controller;
import movieproject.movie.Movie;
import movieproject.util.StyleSet;

public class ReservationCheck extends JFrame implements ActionListener {

	private JPanel pInfo;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JLabel lblReservCheck;
	private JPanel pMain;
	private JLabel lblMovieName;
	private JLabel lblMvChoice;
	private JLabel lblDate;
	private JLabel lblDateChoice;
	private JLabel lblTime;
	private JLabel lblTimeChoice;
	private JLabel lblSeat;
	private JLabel lblSeatChoice;
	private Controller controller;
	private String userId;
	private String strMovieName;
	private String strDate;
	private String strTime;
	private String strSeat;
	private String strMenu;
	private JPanel pFoodTable;
	private JPanel pTable;
	private Vector<String> returnColumn;
	private DefaultTableModel model;
	private JTable table;
	private String strPrice;
	private int price;
	private JLabel lblSumTitle;
	private ArrayList<String> arrPrice = new ArrayList<>();
	private JLabel lblSum;
	private JButton btnPayment;
	private JButton btnReset;
	private JLabel lblMenu;
	private String strPeopleNum;
	private String countNum;
	private String insertReserv;
	private String countCheck;

	public ReservationCheck() {
		DBconnect.DB();
		controller = Controller.getInstance();
		userId = controller.getUserId();
		strMovieName = controller.movieName;
		strDate = controller.sel_date;
		strTime = controller.sel_time;
		strPeopleNum = controller.peopleNum;
		strSeat = controller.seat;
		strMenu = controller.menu;

		// 일단 데이터 우겨넣기
//		userId = "a";
//		strMovieName = "귀멸의 칼날: 남매의 연";
//		strDate = "20211130";
//		strTime = "14:00";
//		strPeopleNum = "2";
//		strSeat = "A1,A2,A3";
//		strMenu = "팝콘:1:8500,츄러스:1:2000";

		setTitle("INHA CINEMA");
		setSize(820, 480);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료

		pMain = new JPanel();
		pMain.setBackground(Color.white);
		pMain.setLayout(null);

		// 타이틀
		addPTitle();
		pMain.add(pTitle);

//		lblReservCheck = new JLabel(" | 예매 정보 확인");
//		Style.lblFont(lblReservCheck, Font.CENTER_BASELINE, 17);
//		lblReservCheck.setBounds(20, 60, 200, 20);
//		pMain.add(lblReservCheck);

		// SelectReservation();

		// 예매 정보 확인
		pInfo = new JPanel();
		TitledBorder pTimeBorder = new TitledBorder(new LineBorder(Color.black, 1), "  예매 정보 확인 ");
		pTimeBorder.setTitleFont(new Font("1훈새마을운동 R", Font.PLAIN, 17));
		pInfo.setBorder(pTimeBorder);
		pInfo.setBounds(40, 80, 340, 230);
		pInfo.setBackground(Color.white);

		pInfo.setLayout(null);

		lblMovieName = new JLabel("영화 이름 :");
		lblMovieName.setBounds(30, 40, 60, 20);
		lblMovieName.setBackground(new Color(0xF6F6F6));
		lblMovieName.setOpaque(true);
		StyleSet.lblFont(lblMovieName, Font.PLAIN, 15);
		pInfo.add(lblMovieName);

		lblMvChoice = new JLabel(strMovieName);
		lblMvChoice.setBounds(100, 40, 300, 20);
		StyleSet.lblFont(lblMvChoice, Font.PLAIN, 15);
		pInfo.add(lblMvChoice);

		lblDate = new JLabel("   날  짜   :");
		lblDate.setBounds(30, 80, 60, 20);
		lblDate.setBackground(new Color(0xF6F6F6));
		lblDate.setOpaque(true);
		StyleSet.lblFont(lblDate, Font.PLAIN, 15);
		pInfo.add(lblDate);

		lblDateChoice = new JLabel(strDate);
		lblDateChoice.setBounds(100, 80, 150, 20);
		StyleSet.lblFont(lblDateChoice, Font.PLAIN, 15);
		pInfo.add(lblDateChoice);

		lblTime = new JLabel("   시  간   :");
		lblTime.setBounds(30, 120, 60, 20);
		lblTime.setBackground(new Color(0xF6F6F6));
		lblTime.setOpaque(true);
		StyleSet.lblFont(lblTime, Font.PLAIN, 15);
		pInfo.add(lblTime);

		lblTimeChoice = new JLabel(strTime);
		lblTimeChoice.setBounds(100, 120, 150, 20);
		StyleSet.lblFont(lblTimeChoice, Font.PLAIN, 15);
		pInfo.add(lblTimeChoice);

		lblSeat = new JLabel("   좌  석   :");
		lblSeat.setBounds(30, 160, 60, 20);
		lblSeat.setBackground(new Color(0xF6F6F6));
		lblSeat.setOpaque(true);
		StyleSet.lblFont(lblSeat, Font.PLAIN, 15);
		pInfo.add(lblSeat);

		lblSeatChoice = new JLabel(strSeat);
		lblSeatChoice.setBounds(100, 160, 150, 20);
		StyleSet.lblFont(lblSeatChoice, Font.PLAIN, 15);
		pInfo.add(lblSeatChoice);

		pMain.add(pInfo);

		lblMenu = new JLabel("먹거리");
		StyleSet.lblFont(lblMenu, Font.PLAIN, 17);
		lblMenu.setBounds(420, 80, 100, 20);
		pMain.add(lblMenu);

		pFoodTable = new JPanel();
		pFoodTable.setBounds(420, 110, 350, 200);
		pFoodTable.setLayout(new BorderLayout());
		pFoodTable.setBackground(Color.white);

		// 테이블 헤더 생성
		returnColumn = new Vector<String>();

		returnColumn.add("메뉴");
		returnColumn.add("수량");
		returnColumn.add("금액");

		model = new DefaultTableModel(returnColumn, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model); // 테이블에 추가

		System.out.println("strFood : " + strMenu);
		if (strMenu == null) {

		} else {
			String[] arrMenu = strMenu.split(",");
			for (int i = 0; i < arrMenu.length; i++) {

				String[] arrCount = new String[3];

				System.out.println("arrMenu" + arrMenu[i]);

				arrCount = arrMenu[i].split(":");
				arrPrice.add(arrCount[2]);
//			String strSelectPrice = "select price from movie_menu where menu = '" + arrCount[0] + "'";
//			ResultSet re = DBconnect.getResultSet(strSelectPrice);
//			try {
//				while (re.next()) {
//					
//					price = re.getInt(1);
//					System.out.println("price : " +price);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String strCount = arrCount[1];
//			int sum = price * Integer.parseInt(strCount);
//			arrCount[2] = sum+"";

				model.addRow(arrCount);
			}
		}

		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		table.setFillsViewportHeight(true); // 테이블 배경색
		JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(new Color(0xFFEAEA)); // 가져온 테이블 헤더의 색 지정
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pFoodTable.add(sc);

		pMain.add(pFoodTable);

		lblSumTitle = new JLabel(" |  합계  : ");
		lblSumTitle.setBackground(new Color(0xFFEAEA));
		lblSumTitle.setOpaque(true);
		StyleSet.lblFont(lblSumTitle, Font.PLAIN, 18);
		lblSumTitle.setBounds(40, 360, 80, 30);
		pMain.add(lblSumTitle);

		int ticketNum = 0;

		String[] splitSeat = strSeat.split(",");
		for (int i = 0; i < splitSeat.length; i++) {
			ticketNum++;
		}
		int sum = 0;
		for (int j = 0; j < arrPrice.size(); j++) {
			sum += Integer.parseInt(arrPrice.get(j));
		}
		sum += ticketNum * 12000;
		System.out.println(sum);
		lblSum = new JLabel(sum + " 원");
		lblSum.setBackground(new Color(0xFFEAEA));
		lblSum.setOpaque(true);
		StyleSet.lblFont(lblSum, Font.PLAIN, 18);
		lblSum.setBounds(120, 360, 100, 30);

		pMain.add(lblSum);

		btnPayment = new JButton("결제");
		btnPayment.setForeground(Color.white);
		btnPayment.setBackground(Color.pink);
		btnPayment.addActionListener(this);
		StyleSet.btnFont(btnPayment, Font.PLAIN, 15);
		btnPayment.setBounds(235, 360, 100, 30);
		pMain.add(btnPayment);

		btnReset = new JButton("취소");
		btnReset.setForeground(Color.white);
		btnReset.setBackground(Color.pink);
		btnReset.addActionListener(this);
		StyleSet.btnFont(btnReset, Font.PLAIN, 15);
		btnReset.setBounds(350, 360, 100, 30);
		pMain.add(btnReset);

		add(pMain);

		setVisible(true);

	}

//	private void SelectReservation() {
//
//		DBconnect.DB();
//		userId = "a";
//		String selectReservation = "select * from MOVIE_RESERVATION where id = '" + userId + "'";
//
//		ResultSet re = DBconnect.getResultSet(selectReservation);
//		try {
//			while (re.next()) {
//
//				strMovieName = re.getString(3);
//				strDate = re.getString(4);
//				strTime = re.getString(5);
//				strSeat = re.getString(6);
//				strFood = re.getString(7);
//
//			}
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//	}

	private void addPTitle() {

		pTitle = new JPanel();
		pTitle.setBounds(0, 0, 900, 40);
		pTitle.setBackground(Color.pink);
		pTitle.setLayout(new BorderLayout());
		pTitle.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		// 타이틀 라벨
		lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 20));
		lblTitle.setForeground(Color.white);
		pTitle.add(lblTitle, BorderLayout.WEST);

	}

	public static void main(String[] args) {
		new ReservationCheck();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == btnPayment) {
			// JOptionPane.showMessageDialog(null, "시간을 선택해 주세요.", "오류 메시지",
			// JOptionPane.WARNING_MESSAGE);
			int paymentResult = JOptionPane.showConfirmDialog(null, "결제하시겠습니까?", "결제 확인", JOptionPane.OK_CANCEL_OPTION);

			if (paymentResult == 0) {
				insertReservation();

				new ReservationInfo();
				dispose();
			}

		} else if (obj == btnReset) {
			int resetResult = JOptionPane.showConfirmDialog(null, "결제를 취소하겠습니까?\n첫 화면으로 돌아갑니다.", "취소 확인",
					JOptionPane.OK_CANCEL_OPTION);
			if (resetResult == 0) {
				JOptionPane.showMessageDialog(null, "취소되었습니다.\n첫 화면으로 돌아갑니다.", "취소 완료",
						JOptionPane.INFORMATION_MESSAGE);
				new Movie();
				dispose();
			}
		}

	}

	private void insertReservation() {

		String reservCheck = "SELECT COUNT(*) FROM MOVIE_RESERVATION WHERE ID='" + userId + "' AND MOVIE_NAME='" + strMovieName + "' AND MOVIE_DATE='" + strDate + "' AND MOVIE_TIME='" + strTime + "' AND SEAT='" + strSeat+ "' AND FOOD='" + strMenu + "'";
		ResultSet re = DBconnect.getResultSet(reservCheck);
		
		System.out.println(reservCheck);
		try {
			while (re.next()) {
				countCheck = re.getString(1);
			}
		} catch (SQLException e1) {

			countCheck = "0";
		}

		if (countCheck.equals("0")) {
			
			String reservCount = "select COUNT(*) from MOVIE_RESERVATION";
			ResultSet rs = DBconnect.getResultSet(reservCount);
			try {
				while (rs.next()) {
					countNum = rs.getString(1);
					System.out.println(countNum);
				}
			} catch (SQLException e1) {

				countNum = "0";
			}

			if (countNum.equals("0")) {

				insertReserv = "INSERT INTO MOVIE_RESERVATION (RESERVATION_ID, ID, MOVIE_NAME, MOVIE_DATE, MOVIE_TIME, SEAT, FOOD, STATE) VALUES('1', '" + userId + "', '" + strMovieName + "', '" + strDate + "', '" + strTime + "', '" + strSeat+ "', '" + strMenu + "', 'false')";

				
				
			} else {
				insertReserv = "INSERT INTO MOVIE_RESERVATION (RESERVATION_ID, ID, MOVIE_NAME, MOVIE_DATE, MOVIE_TIME, SEAT, FOOD, STATE) VALUES('"+ (Integer.parseInt(countNum)+1) + "', '" + userId + "', '" + strMovieName + "', '" + strDate + "', '" + strTime+ "', '" + strSeat + "', '" + strMenu + "', 'false')";

			}

			DBconnect.getupdate(insertReserv);
			JOptionPane.showMessageDialog(null, "결제가 완료되었습니다.", "결제 완료", JOptionPane.INFORMATION_MESSAGE);
			
		}else {
			JOptionPane.showMessageDialog(null, "이미 예약한 영화입니다.\n결제가 취소됩니다.","오류", JOptionPane.ERROR_MESSAGE);
			
		}

	}

}
