package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import movieproject.DBconnect;
import movieproject.controller.Controller;
import movieproject.util.DateLabelFormatter;
import movieproject.util.StyleSet;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MovieTimeSet extends JFrame implements ActionListener, KeyListener {

	Controller controller;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JPanel pCenter;
	private JLabel lblMovieName;
	private JPanel pInfo;
	private UtilDateModel dateModel;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private JLabel lblChoiceMovieName;
	private JLabel lblDate;
	private JLabel lblTime;
	private JPanel pTime;
	private JLabel lblAm;
	private JButton btnTime9;
	private JButton btnTime10;
	private JButton btnTime11;
	private JLabel lblPm;
	private ArrayList<JButton> arrTime;
	private JButton btnTimeAm;
	private JButton btnTimePm;
	private JPanel pCheck;
	private JPanel pCheckInfo;
	private JLabel lblCheckDate;
	private JLabel lblChoiceDate;
	private JLabel lblCheckTime;
	private JLabel lblChoiceTime;
	private SimpleDateFormat dateFormatter;
	private Date SelectedDate;
	private String choiceDate;
	private String choiceTime;
	private JButton btnAdd;
	private String strmovieId;
	private Container c;
	private String countNum;
	private String insertTime;

	public MovieTimeSet() {

		controller = Controller.getInstance();

		setTitle("INHA CINEMA");
		setSize(900, 570);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료
		setLayout(new BorderLayout());

		// 타이틀 생성
		addPTitle();
		add(pTitle, BorderLayout.NORTH);

		// 가운데 패널 생성
		addPCenter();
		add(pCenter, BorderLayout.CENTER);

		c = getContentPane();
		c.requestFocus();
		c.setFocusable(true);
		c.addKeyListener(this);
		setVisible(true);
	}

	private void addPTitle() {

		pTitle = new JPanel();
		// pTitle.setBounds(0, 0, 900, 40);
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
		StyleSet.btnFont(btnBack, Font.PLAIN, 15);
		btnBack.setForeground(Color.white);
		btnBack.setBackground(Color.pink);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(this);
		pTitle.add(btnBack, BorderLayout.EAST);

	}

	private void addPCenter() {
		pCenter = new JPanel();
		// pCenter.setBounds(0, 40, 900, 560);
		pCenter.setLayout(null);
		pCenter.setBackground(Color.white);

		// 영화 이름, 날짜 선택 패널
		addPInfo();
		pCenter.add(pInfo);

//		// 시간 지정 타이틀
//		lblTime = new JLabel("    |  시간 지정");
//		lblTime.setBounds(0, 60, 100, 18);
//		Style.lblFont(lblTime, Font.CENTER_BASELINE, 15);
//		pCenter.add(lblTime);

		// 시간 지정 버튼
		addpTime();
		pCenter.add(pTime);

		addCheck();
		pCenter.add(pCheck);
	}

	private void addPInfo() {

		// 영화 이름, 날짜 선택
		pInfo = new JPanel();
		pInfo.setBounds(0, 0, 900, 60);
		pInfo.setBackground(Color.white);
		pInfo.setLayout(new FlowLayout(FlowLayout.LEFT));
		pInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

		lblMovieName = new JLabel("   |  선택한 영화 : ");
		StyleSet.lblFont(lblMovieName, Font.CENTER_BASELINE, 15);
		pInfo.add(lblMovieName);

		String getMovieName = controller.getMovieName();
		lblChoiceMovieName = new JLabel(getMovieName);
		// lblChoiceMovieName = new JLabel("냥냥냥");
		StyleSet.lblFont(lblChoiceMovieName, Font.PLAIN, 15);
		pInfo.add(lblChoiceMovieName);

		lblDate = new JLabel("         |   날짜 선택  ");
		StyleSet.lblFont(lblDate, Font.CENTER_BASELINE, 15);
		pInfo.add(lblDate);

		// 날짜 데이터피커
		dateModel = new UtilDateModel(); // 데이터모델 객체 생성
		datePanel = new JDatePanelImpl(dateModel); // 모델 객체를 이용해 JDatePanelInterfaceimplements 객체 생성 -> 달력 생성
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter()); // 생성된 달력을 텍스트필드와 ...버튼으로 나타냄 /
		datePanel.addActionListener(this); // 달력이 선택되었을 때 액션이벤트
		pInfo.add(datePicker);

	}

	private void addpTime() {

		pTime = new JPanel();

		// 패널 테두리 설정
		TitledBorder pTimeBorder = new TitledBorder(new LineBorder(Color.black, 1), "  시간 지정  ");
		pTimeBorder.setTitleFont(new Font("1훈새마을운동 R", Font.PLAIN, 17));
		pTime.setBorder(pTimeBorder);
		pTime.setBounds(20, 60, 845, 360);
		pTime.setBackground(Color.white);
		pTime.setLayout(null);

		// 오전
		lblAm = new JLabel("  |  A.M.  ");
		lblAm.setBackground(new Color(0xF6F6F6));
		lblAm.setOpaque(true);
		StyleSet.lblFont(lblAm, Font.PLAIN, 15);
		lblAm.setBounds(25, 30, 100, 20);
		pTime.add(lblAm);

		int amX = 25;
		arrTime = new ArrayList<JButton>();
		for (int i = 0; i < 3; i++) {
			// 오전 시간 버튼
			btnTimeAm = new JButton((9 + i) + ":00 ~");
			if (i == 0)
				btnTimeAm.setBounds(amX, 70, 100, 60);
			else {
				btnTimeAm.setBounds(amX + 110, 70, 100, 60);
				amX += 110;
			}
			StyleSet.btnFontStyle(btnTimeAm, Font.PLAIN, 15, 0xFFEAEA);
			btnTimeAm.addActionListener(this);
			pTime.add(btnTimeAm);
			arrTime.add(btnTimeAm);
		}

		// 오후
		lblPm = new JLabel("  |  P.M.  ");
		lblPm.setBackground(new Color(0xF6F6F6));
		lblPm.setOpaque(true);
		StyleSet.lblFont(lblPm, Font.PLAIN, 15);
		lblPm.setBounds(25, 160, 100, 20);
		pTime.add(lblPm);

		int pmX = 25, pmX2 = 25;
		int timeSet = 12;

		for (int i = 0; i < 10; i++) {
			// 오전 시간 버튼
			if (i == 0) {
				btnTimePm = new JButton("12:00 ~");
				btnTimePm.setBounds(pmX, 200, 100, 60);
			} else if (i > 0 && i <= 4) {
				btnTimePm = new JButton((timeSet + 1) + ":00 ~");
				btnTimePm.setBounds(pmX + 110, 200, 100, 60);
				timeSet += 1;
				pmX += 110;
			} else if (i == 5) {
				btnTimePm = new JButton((timeSet + 1) + ":00 ~");
				btnTimePm.setBounds(pmX2, 270, 100, 60);
				timeSet += 1;
			} else if (i == 6) {
				btnTimePm = new JButton((timeSet + 1) + ":00 ~");
				btnTimePm.setBounds(pmX2 + 110, 270, 100, 60);
				pmX2 += 110;
				timeSet += 1;
			} else {
				btnTimePm = new JButton((timeSet + 2) + ":00 ~");
				btnTimePm.setBounds(pmX2 + 110, 270, 100, 60);
				timeSet += 2;
				pmX2 += 110;
			}

			StyleSet.btnFontStyle(btnTimePm, Font.PLAIN, 15, 0xFFEAEA);
			btnTimePm.addActionListener(this);
			pTime.add(btnTimePm);
			arrTime.add(btnTimePm);
		}

	}

	private void addCheck() {
		pCheck = new JPanel();
		pCheck.setBackground(Color.white);
		pCheck.setBounds(20, 440, 840, 28);
		pCheck.setLayout(new BorderLayout());

		pCheckInfo = new JPanel();
		pCheckInfo.setBackground(Color.white);
		pCheckInfo.setLayout(new FlowLayout(FlowLayout.LEFT));

		lblCheckDate = new JLabel("|  선택한 날짜 : ");
		StyleSet.lblFont(lblCheckDate, Font.PLAIN, 15);
		pCheckInfo.add(lblCheckDate);

		lblChoiceDate = new JLabel("");
		StyleSet.lblFont(lblChoiceDate, Font.PLAIN, 15);
		pCheckInfo.add(lblChoiceDate);

		lblCheckTime = new JLabel("      |  선택한 시간 : ");
		StyleSet.lblFont(lblCheckTime, Font.PLAIN, 15);
		pCheckInfo.add(lblCheckTime);

		lblChoiceTime = new JLabel("");
		StyleSet.lblFont(lblChoiceTime, Font.PLAIN, 15);
		pCheckInfo.add(lblChoiceTime);

		btnAdd = new JButton("   추가   ");
		btnAdd.setForeground(Color.white);
		btnAdd.setBackground(Color.pink);
		StyleSet.btnFont(btnAdd, Font.PLAIN, 15);
		btnAdd.addActionListener(this);

		pCheck.add(pCheckInfo, BorderLayout.WEST);
		pCheck.add(btnAdd, BorderLayout.EAST);
	}

	public static void main(String[] args) {
		new MovieTimeSet();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnBack) {
			dispose(); // 창 닫기
		} else if (obj == datePanel) {

			String datePattern = "yyyyMMdd"; // 데이터 포맷 형식 지정
			dateFormatter = new SimpleDateFormat(datePattern); // 데이터 포맷 형식 지정한 객체 생성 -> 출력할 때 사용하기 위함
			SelectedDate = (Date) datePicker.getModel().getValue(); // 달력에서 클릭된 날짜 값 가져오기
			choiceDate = dateFormatter.format(SelectedDate);

			lblChoiceDate.setText(choiceDate);

			System.out.println("선택한 날짜 : " + choiceDate);
		} else if (obj == btnAdd) {

			addInsertTime();

		} else {
			for (int i = 0; i < arrTime.size(); i++) {
				if (obj == arrTime.get(i)) {

					String[] arrSplit = arrTime.get(i).getText().split(" ");
					choiceTime = arrSplit[0];

					lblChoiceTime.setText(choiceTime);
					System.out.println("선택한 시간 : " + choiceTime);

				}
			}
		}
		c.requestFocus();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			addInsertTime();
		}
		c.requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void addInsertTime() {

		if (lblChoiceDate.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "날짜를 선택해 주세요.", "오류 메시지", JOptionPane.WARNING_MESSAGE);
		} else if (lblChoiceTime.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "시간을 선택해 주세요.", "오류 메시지", JOptionPane.WARNING_MESSAGE);
		} else {

			String countCheck = "";

			// 영화 코드 검색
			String selectMovieId = "SELECT MOVIE_ID	FROM MOVIE WHERE MOVIE_NAME = '" + lblChoiceMovieName.getText()
					+ "'";
			ResultSet re = DBconnect.getResultSet(selectMovieId);

			try {
				while (re.next()) {

					strmovieId = re.getString(1);

				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String timeCheck = "SELECT COUNT(*) FROM MOVIE_TIME WHERE MOVIE_ID ='" + strmovieId + "' AND MOVIE_DATE='"
					+ lblChoiceDate.getText() + "' AND MOVIE_TIME = '" + lblChoiceTime.getText() + "'";
			ResultSet re1 = DBconnect.getResultSet(timeCheck);

			try {
				while (re1.next()) {
					countCheck = re1.getString(1);
				}
			} catch (SQLException e1) {

				countCheck = "0";
			}

			if (countCheck.equals("0")) {
				
				
				String selectCount = "SELECT COUNT(*) FROM MOVIE_TIME";
				ResultSet rs = DBconnect.getResultSet(selectCount);
				try {
					while (rs.next()) {
						countNum = rs.getString(1);
					}
				} catch (SQLException e1) {

					countNum = "0";
				}
				
				if(countNum.equals("0")) {
					insertTime = "INSERT INTO MOVIE_TIME (MOVIE_TIME_ID, MOVIE_ID, MOVIE_DATE, MOVIE_TIME) VALUES('1', '" + strmovieId + "', '" + choiceDate + "', '" + choiceTime + "')";
				}
				else {
					insertTime = "INSERT INTO MOVIE_TIME (MOVIE_TIME_ID, MOVIE_ID, MOVIE_DATE, MOVIE_TIME) VALUES('" + (Integer.parseInt(countNum)+1)  +"', '" + strmovieId + "', '" + choiceDate + "', '" + choiceTime + "')";
				
				}
				
				DBconnect.getupdate(insertTime);

				JOptionPane.showMessageDialog(null, "영화 이름 : " + lblChoiceMovieName.getText() + "\n날짜 : " + lblChoiceDate.getText()+ "\n시간 : " + lblChoiceTime.getText() + "\n추가되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(null, "영화 이름 : " + lblChoiceMovieName.getText() + "\n날짜 : " + lblChoiceDate.getText()+ "\n시간 : " + lblChoiceTime.getText() + "\n중복된 데이터입니다.","오류", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

}
