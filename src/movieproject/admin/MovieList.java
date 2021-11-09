package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import movieproject.DBconnect;
import movieproject.movie.MovieAPI;
import movieproject.util.Style;



class CalendarDataManager{ // 6*7배열에 나타낼 달력 값을 구하는 class

	static final int CAL_WIDTH = 7; // 너비 ( 일~월 까지 7개)
	final static int CAL_HEIGHT = 6; // 최대 6주
	int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
	int calYear; // 년도
	int calMonth; // 월
	int calDayOfMon; // 일
	final int calLastDateOfMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; // 1월부터 12월까지 마지막 날
	int calLastDate;
	Calendar today = Calendar.getInstance(); // 캘린더 객체 선언
	Calendar cal;

	public CalendarDataManager() {
		setToday(); // 현재 날짜 가져오기
	}

	public void setToday() {
		calYear = today.get(Calendar.YEAR); // 현재 년도
		calMonth = today.get(Calendar.MONTH); // 현재 월
		calDayOfMon = today.get(Calendar.DAY_OF_MONTH); // 현재 일
		makeCalData(today); // 캘린더의 today 객체를 이용해 달력을 만듦
	}

	private void makeCalData(Calendar cal) {
		// 1일의 위치와 마지막 날짜를 구함
		// DAY_OF_WEEK : 요일 / 1~7까지의 값을 리턴 / 일, 월 ~ 토요일
		// DAY_OF_MONTH : 현재 월의 날짜
		// 요일값을 가져오고, 월 날짜를 가져와 1일의 위치와 마지막 날짜를 구함
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK) + 7 - (cal.get(Calendar.DAY_OF_MONTH)) % 7) % 7;
		if (calMonth == 1) // 1일일 때 위치
			calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear); // 현재 위치(날짜) 뽑아오기
		else
			calLastDate = calLastDateOfMonth[calMonth];
		// 달력 배열 0으로 초기화 수정수정
		for (int i = 0; i < CAL_HEIGHT; i++) {
			for (int j = 0; j < CAL_WIDTH; j++) {
				calDates[i][j] = 0;
			}
		}
		// 달력 배열에 값 채워넣기
		for (int i = 0, num = 1, k = 0; i < CAL_HEIGHT; i++) {
			if (i == 0)
				k = calStartingPos; // 시작위치부터 출력
			else
				k = 0;
			for (int j = k; j < CAL_WIDTH; j++) {
				if (num <= calLastDate)
					calDates[i][j] = num++; // 마지막 날까지 달력 구성
			}
		}
	}

	private int leapCheck(int year) { // 윤년인지 확인하는 함수
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
			return 1;
		else
			return 0;
	}

	public void moveMonth(int mon) { // 현재달로 부터 n달 전후를 받아 달력 배열을 만드는 함수(1년은 +12, -12달로 이동 가능)
		calMonth += mon;
		if (calMonth > 11)
			while (calMonth > 11) {
				calYear++;
				calMonth -= 12;
			}
		else if (calMonth < 0)
			while (calMonth < 0) {
				calYear--;
				calMonth += 12;
			}
		cal = new GregorianCalendar(calYear, calMonth, calDayOfMon);
		makeCalData(cal); // 새로 선택된 년 , 달, 일을 사용하여 달력 재구성
	}
}

public class MovieList extends CalendarDataManager implements ActionListener, MouseListener {
	
	MovieAPI api = new MovieAPI();	// API 생성
	
	private String selectStr;

	// 창 구성요소와 배치도
	JFrame mainFrame;

	JPanel calOpPanel;
	JButton todayBut;
	JLabel todayLab;
	JButton lYearBut;
	JButton lMonBut;
	JLabel curMMYYYYLab;
	JButton nMonBut;
	JButton nYearBut;
	ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons(); // 화살표 버튼 클릭 시 이벤트

	JPanel calPanel; // 달력 구성
	JButton weekDaysName[];
	JButton dateButs[][] = new JButton[6][7];
	listenForDateButs lForDateButs = new listenForDateButs(); // 달력 클릭 시 이벤ㄴ트

	JPanel infoPanel;
	JLabel infoClock;

	JPanel memoPanel;
	JLabel selectedDate;
	JTextArea memoArea;
	JScrollPane memoAreaSP;
	JPanel memoSubPanel;
	JButton saveBut;
	JButton delBut;
	JButton clearBut;

	// 상수, 메세지
	final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" }; // 요일 선언
	final String title = "반납";

	private JPanel panelTable;
	private Font lblFont = new Font("HY헤드라인M", Font.PLAIN, 15);
	private Vector<String> returnColumn;
	private DefaultTableModel model;
	private JTable table;
	private JPanel panelTitle, panelTop, panelSearch, panelTopInfo, CalendarSub, JPanelBtn;
	private JLabel lblLogo, lblDate;
	private JTextField tfDate;

	private JButton btnSearch, btnReset;

	private JPanel pCenter;

	private JPanel panelURL;

	private JTextField tfURL;

	private JPanel pUrlTitle;

	private JLabel lblMovieName;

	private JLabel lblChoiceName;

	private JPanel pUrlInfo;

	private JLabel lblUrl;

	private JButton btnAdd;

	private String clickName;

	private String clickGenre;

	private String clickLimit;

	private String clickTime;

	private JTable mtable;


	public static void main(String[] args) {
		DBconnect.DB();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MovieList(); // invokeLater는 나중에 호출해라 / 나중에 호출할 대상이 바로 파라미터로 주어지는 Runnable
			}
		});
	}

	public MovieList() {
		// 레이아웃 구성

		mainFrame = new JFrame(title);
		mainFrame.setBackground(Color.white);
		mainFrame.setSize(1110, 550); // 메인 프레임 크기
		mainFrame.setLocationRelativeTo(null);

		panelTop = new JPanel();
		panelTop.setBackground(Color.white);
		panelTop.setLayout(new BorderLayout());
		panelTop.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		// 로고, 검색 등의 텍스트 필드를 포함하는 패널
		addPTopInfo();

		// 달력 구성
		addCOpP(); // 달력 구성

		// 그리드 백 레이아웃 : 그리드와 유사하지만 여러 셀에 하나의 컴포넌트를 배치 가능
		addCOpPGridBag();

		// 달력
		calPanel = new JPanel();
		calPanel.setBackground(Color.white);

		// 요일 스타일 지정
		addWeekDaysStyle();

		// 일 스타일
		addCalStyle();

		showCal(); // 달력을 표시

		// 구성한 달력 이벤트 or 스타일 or 구성
		addOpPStyle();

		// 검색 버튼, 초기화버튼
		addBottomBtn();

		panelTop.add(panelTopInfo, BorderLayout.NORTH);
		panelTop.add(CalendarSub, BorderLayout.CENTER);
		panelTop.add(JPanelBtn, BorderLayout.SOUTH);

		// 테이블 생성
		addPCenter();

		// frame에 전부 배치
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(panelTop, BorderLayout.WEST);
		mainFrame.add(pCenter, BorderLayout.CENTER);
		mainFrame.setVisible(true);

		focusToday(); // 현재 날짜에 focus를 줌 (mainFrame.setVisible(true) 이후에 배치해야함)

	}

	// 로고 및 날짜 검색 부분
	private void addPTopInfo() {
		panelTopInfo = new JPanel(); // 로고, 검색(텍스트 필드) 등 포함
		panelTopInfo.setLayout(new BorderLayout());

		// 로고 생성
		addPTitle();

		// 날짜 검색(텍스트 필드, 라벨 등)
		addPSearch();

		panelTopInfo.add(panelTitle, BorderLayout.NORTH);
		panelTopInfo.add(panelSearch, BorderLayout.CENTER);

	}

	// 로고 생성
	private void addPTitle() {

		panelTitle = new JPanel();
		panelTitle.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
		panelTitle.setBackground(Color.white);
		panelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));
		ImageIcon icontitle = new ImageIcon("libs/폼로고.jpg");
		Image changeIcon = icontitle.getImage().getScaledInstance(350, 40, Image.SCALE_SMOOTH);
		ImageIcon lblIcontitle = new ImageIcon(changeIcon);
		lblLogo = new JLabel(lblIcontitle);
		panelTitle.add(lblLogo);

	}

	// 날짜 검색(텍스트 필드, 레이블 등)
	private void addPSearch() {
		panelSearch = new JPanel();
		panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelSearch.setBackground(Color.white);
		panelSearch.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
		// 날짜 검색 라벨
		lblDate = new JLabel("날짜 검색");
		lblDate.setFont(lblFont);
		panelSearch.add(lblDate);

		// 텍스트 필드
		tfDate = new JTextField(20);
		//tfDate.setHorizontalAlignment(SwingConstants.CENTER);
		panelSearch.add(tfDate);

//		// -
//		lblHyphen = new JLabel(" - ");
//		lblHyphen.setFont(lblFont);
//		panelSearch.add(lblHyphen);
//
//		// 텍스트 필드
//		tfDate2 = new JTextField(15);
//		tfDate2.setHorizontalAlignment(SwingConstants.CENTER);
//		tfDate2.setSize(100, 40);
//		tfDate2.setLocation(240, 100);
//		panelSearch.add(tfDate2);

	}

	// 달력 구성
	private void addCOpP() {
		calOpPanel = new JPanel();
		calOpPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
		calOpPanel.setBackground(Color.white);
		calOpPanel.setLayout(new BorderLayout());

		// today 버튼 ( 현재 날짜 달력 재구성 )
		todayBut = new JButton("Today");
		Style.BtnDateStyle(todayBut);
		todayBut.setToolTipText("Today");
		todayBut.addActionListener(lForCalOpButtons);

		// 현재 날짜 라벨로 출력
		todayLab = new JLabel(today.get(Calendar.MONTH) + 1 + "/" + today.get(Calendar.DAY_OF_MONTH) + "/"
				+ today.get(Calendar.YEAR));
		todayLab.setFont(lblFont);

		// 이전 년도
		lYearBut = new JButton("<<");
		Style.BtnDateStyle(lYearBut);
		lYearBut.setToolTipText("Previous Year");
		lYearBut.addActionListener(lForCalOpButtons);

		// 이전 달
		lMonBut = new JButton("<");
		Style.BtnDateStyle(lMonBut);
		lMonBut.setToolTipText("Previous Month");
		lMonBut.addActionListener(lForCalOpButtons);

		// 달력에서 클릭 or 이전, 다음 화살표 클릭 시 해당하는 날짜 라벨에 표시
		curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "&nbsp;" : "")
				+ (calMonth + 1) + " / " + calYear + "</th></tr></table></html>"); // today누르면 년도와 월 텍스트 변경함

		// 다음 달
		nMonBut = new JButton(">");
		Style.BtnDateStyle(nMonBut);
		nMonBut.setToolTipText("Next Month");
		nMonBut.addActionListener(lForCalOpButtons);

		// 다음 년도
		nYearBut = new JButton(">>");
		Style.BtnDateStyle(nYearBut);
		nYearBut.setToolTipText("Next Year");
		nYearBut.addActionListener(lForCalOpButtons);

	}

	// 그리드 백 레이아웃 지정
	private void addCOpPGridBag() {
		calOpPanel.setLayout(new GridBagLayout()); // 그리드 백 레이아웃
		calOpPanel.setBackground(Color.white);
		GridBagConstraints calOpGC = new GridBagConstraints(); // 그리드백 콘스트레인트 만들기

		// today
		calOpGC.gridx = 1; // 컴포넌트 위치 값 Component가 표시될 격자의 좌표 지정_ 좌측상단은 gridx=0, gridy=0 _ 지정하지 않으면 왼쪽에서 오른
							// 쪽으로 차례대로 붙음
		calOpGC.gridy = 1;
		calOpGC.gridwidth = 2;
		calOpGC.gridheight = 1;

		calOpGC.weightx = 1; // Component가 크기를 비율로 지정 _ 0 : Container 크기가 변해도 원래 크기 유지 _ 0 이외의 값 : 같은 행에 있는
		calOpGC.weighty = 1; // Component간의 비율 계산

		calOpGC.insets = new Insets(0, 0, 10, 10); // 격자와 격자 사이의 거리
		calOpGC.anchor = GridBagConstraints.WEST; // 격자 안에서의 Component 위치 CENTER, NORTH, SOUTH, EAST, WEST 등

		calOpGC.fill = GridBagConstraints.VERTICAL; // NONE : Component크기 유지 _ BOTH :격자 크기에 맞춤 _ HORIZONTAL : 수평만 맞춤
													// _VERTICAL : 수직만
		calOpPanel.add(todayBut, calOpGC); // today

		// today 라벨
		calOpGC.gridwidth = 3;
		calOpGC.gridx = 2;
		calOpGC.gridy = 1;
		calOpPanel.add(todayLab, calOpGC); // today 라벨

		// 이전 년도
		calOpGC.anchor = GridBagConstraints.CENTER;
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 1;
		calOpGC.gridy = 2;
		calOpPanel.add(lYearBut, calOpGC);

		// 이전 달
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 2;
		calOpGC.gridy = 2;
		calOpPanel.add(lMonBut, calOpGC);

		// 날짜 라벨 재구성
		calOpGC.gridwidth = 2;
		calOpGC.gridx = 3;
		calOpGC.gridy = 2;
		calOpPanel.add(curMMYYYYLab, calOpGC);

		// 다음 달
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 5;
		calOpGC.gridy = 2;
		calOpPanel.add(nMonBut, calOpGC);

		// 다음 년도
		calOpGC.gridwidth = 1;
		calOpGC.gridx = 6;
		calOpGC.gridy = 2;
		calOpPanel.add(nYearBut, calOpGC);

	}

	// 요일 스타일 지정
	private void addWeekDaysStyle() {
		weekDaysName = new JButton[7];

		for (int i = 0; i < CAL_WIDTH; i++) { // CAL_WIDTH = 7 ( 일~토 )
			weekDaysName[i] = new JButton(WEEK_DAY_NAME[i]);
			weekDaysName[i].setBorderPainted(false); // 테두리
			weekDaysName[i].setContentAreaFilled(false); // 내용영역 채우기 x
			weekDaysName[i].setForeground(Color.WHITE); // 배경색

			if (i == 0)
				weekDaysName[i].setBackground(new Color(255, 162, 162)); // 일요일
			else if (i == 6)
				weekDaysName[i].setBackground(new Color(178, 204, 255)); // 토요일
			else
				weekDaysName[i].setBackground(new Color(18, 52, 120)); // 월요일~금요일
			weekDaysName[i].setOpaque(true); // 투명도
			weekDaysName[i].setFocusPainted(false);
			calPanel.add(weekDaysName[i]);
		}

	}

	// 달력 안 일 스타일 지정
	private void addCalStyle() {
		for (int i = 0; i < CAL_HEIGHT; i++) { // CAL_HEIGHT = 6 / 1~ 31 ...까지 추가
			for (int j = 0; j < CAL_WIDTH; j++) {
				dateButs[i][j] = new JButton();
				dateButs[i][j].setBorderPainted(false);
				dateButs[i][j].setContentAreaFilled(false);
				dateButs[i][j].setBackground(Color.WHITE);
				dateButs[i][j].setOpaque(true);
				dateButs[i][j].addActionListener(lForDateButs);
				calPanel.add(dateButs[i][j]);
			}
		}
		calPanel.setLayout(new GridLayout(0, 7, 2, 2));
		calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	}

	// 구성한 달력 스타일 지정
	private void addOpPStyle() {
		CalendarSub = new JPanel();
		CalendarSub.setBackground(Color.white);
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanelSize.height = 90;
		calOpPanel.setPreferredSize(calOpPanelSize);
		CalendarSub.setLayout(new BorderLayout());
		CalendarSub.add(calOpPanel, BorderLayout.NORTH);
		CalendarSub.add(calPanel, BorderLayout.CENTER);

	}

	// 검색 및 초기화 버튼
	private void addBottomBtn() {
		JPanelBtn = new JPanel();
		JPanelBtn.setLayout(new GridLayout(1, 2, 20, 0));
		JPanelBtn.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		JPanelBtn.setBackground(Color.white);

		btnSearch = new JButton("검색");
		BtnStyle(btnSearch);
		btnSearch.addActionListener(this);
		JPanelBtn.add(btnSearch);

		btnReset = new JButton("초기화");
		BtnStyle(btnReset);
		btnReset.addActionListener(this);
		JPanelBtn.add(btnReset);

	}

	// 테이블 생성
	private void addPCenter() {
		
		pCenter = new JPanel();
		pCenter.setLayout(new BorderLayout());
		pCenter.setBackground(Color.white);
		
		addPTable();
		
		panelURL = new JPanel();
		panelURL.setBackground(Color.white);
		panelURL.setLayout(new BorderLayout());
		panelURL.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

		// URL 타이틀
		pUrlTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pUrlTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		pUrlTitle.setBackground(Color.white);
		
		lblMovieName = new JLabel("선택된 영화 : ");
		Style.lblFont(lblMovieName, Font.PLAIN, 15);
		pUrlTitle.add(lblMovieName);
		
		lblChoiceName = new JLabel("");
		Style.lblFont(lblChoiceName, Font.PLAIN, 15);
		pUrlTitle.add(lblChoiceName);
		
		panelURL.add(pUrlTitle, BorderLayout.NORTH);
		
		// url 입력 부분
		pUrlInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pUrlInfo.setBackground(Color.white);
		
		lblUrl = new JLabel("URL 입력 : ");
		Style.lblFont(lblUrl, Font.PLAIN, 15);
		pUrlInfo.add(lblUrl);
		
		tfURL = new JTextField(45);
		pUrlInfo.add(tfURL);
		
		btnAdd = new JButton("  추가  ");
		Style.btnFont(btnAdd, Font.PLAIN, 12);
		btnAdd.setForeground(Color.white); // 글자색
		btnAdd.setBackground(new Color(0x123478));
		btnAdd.addActionListener(this);
		
		pUrlInfo.add(btnAdd);
		
		panelURL.add(pUrlInfo, BorderLayout.CENTER);
		
		pCenter.add(panelTable, BorderLayout.CENTER);
		pCenter.add(panelURL, BorderLayout.SOUTH);
		
		
	}

	private void addPTable() {
		
		
		panelTable = new JPanel();
		panelTable.setBackground(new Color(0xFFFFFF));
		panelTable.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelTable.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));

		
		
		// 테이블 헤더 생성
		returnColumn = new Vector<String>();
		returnColumn.add("영화 이름");
		returnColumn.add("영화 장르");
		returnColumn.add("연령 제한");
		returnColumn.add("영화 시간");

		model = new DefaultTableModel(returnColumn, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		// 테이블 생성
		returnTable();

		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		table.setFillsViewportHeight(true); // 테이블 배경색
		table.addMouseListener(this);
		JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(Color.pink); // 가져온 테이블 헤더의 색 지정

		// 스크롤 팬
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sc.setPreferredSize(new Dimension(600, 375));
		
		
		panelTable.add(sc);
		
	}

	// 테이블 재구성 시 사용 (초기화 등)
	private void returnTable() {
		
		
		model.setNumRows(0);;
		
		//String strToday = today.get(Calendar.YEAR) + today.get(Calendar.MONTH -1) + today.get(Calendar.DATE -1) +"";
		if(tfDate.getText().equals("")) {
			System.out.println("tfDate : " + tfDate.getText());
			api.setDATE_FMT("20161010");
		}else {
			System.out.println("tfDate 2: " + tfDate.getText());
			api.setDATE_FMT(tfDate.getText());
		}
		api.requestAPI();
		
		String[] rsArr = new String[4]; // 값 받아올 배열
		for(int i=0; i<api.getCo().size();i++) {
			
			rsArr[0] = api.getName().get(i);
			rsArr[1] = api.getGenre().get(i);
			rsArr[2] = api.getLimit().get(i);
			rsArr[3] = api.getTime().get(i);
			
			System.out.println(rsArr[0] + " " + rsArr[1] + " " + rsArr[2] + " " + rsArr[3]);
			model.addRow(rsArr);
			
		}
		
//		String returnSelect = "select return.RETURNID, um.UMBRELLAID, st.STUDENTID, st.NAME, TO_CHAR(rental.rentaldate, \'YYYY-MM-DD\'), TO_CHAR(return.returndate, \'YYYY-MM-DD\') "
//				+ " from RETURN return, RENTAL rental, STUDENT st, UMBRELLA um where return.rentalid = rental.rentalid"
//				+ "  and rental.studentid = st.studentid and rental.umbrellaid = um.umbrellaid ORDER BY RETURN.RETURNDATE DESC , RETURN.RENTALID DESC";
//
//		ResultSet rs = DBconnect.getResultSet(returnSelect);
//		
//		
//		try {
//			while (rs.next()) {
//				
//				for (int i = 0; i < rsArr.length; i++) {
//					rsArr[i] = rs.getString(i + 1); // 값 저장
//				}
//
//				model.addRow(rsArr); // 모델에 추가
//
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

		table = new JTable(model); // 테이블에 추가
		
		table.requestFocus();
	}

	// 현재 날짜에 focus
	private void focusToday() {
		if (today.get(Calendar.DAY_OF_WEEK) == 1)
			dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
		else
			dateButs[today.get(Calendar.WEEK_OF_MONTH) - 1][today.get(Calendar.DAY_OF_WEEK) - 1].requestFocusInWindow();
	}

	//
	private void showCal() {
		for (int i = 0; i < CAL_HEIGHT; i++) {
			for (int j = 0; j < CAL_WIDTH; j++) {
				String fontColor = "black";
				if (j == 0)
					fontColor = "red";
				else if (j == 6)
					fontColor = "blue";

				File f = new File("MemoData/" + calYear + ((calMonth + 1) < 10 ? "0" : "") + (calMonth + 1)
						+ (calDates[i][j] < 10 ? "0" : "") + calDates[i][j] + ".txt");
				if (f.exists()) {
					dateButs[i][j]
							.setText("<html><b><font color=" + fontColor + ">" + (calDates[i][j]<10? ("0" +calDates[i][j]): calDates[i][j]) + "</font></b></html>");
				} else
					dateButs[i][j].setText("<html><font color=" + fontColor + ">" + (calDates[i][j]<10? ("0" +calDates[i][j]): calDates[i][j]) + "</font></html>");

				JLabel todayMark = new JLabel("<html><font color=green>*</html>");
				dateButs[i][j].removeAll();
				if (calMonth == today.get(Calendar.MONTH) && calYear == today.get(Calendar.YEAR)
						&& calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)) {
					dateButs[i][j].add(todayMark);
					dateButs[i][j].setToolTipText("Today");
				}

				if (calDates[i][j] == 0)
					dateButs[i][j].setVisible(false);
				else
					dateButs[i][j].setVisible(true);
			}
		}
	}

	// 달력 부분 클릭 시 처리 이벤트
	private class ListenForCalOpButtons implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == todayBut) {
				setToday();
				lForDateButs.actionPerformed(e);
				focusToday();
			} else if (e.getSource() == lYearBut)
				moveMonth(-12);
			else if (e.getSource() == lMonBut)
				moveMonth(-1);
			else if (e.getSource() == nMonBut)
				moveMonth(1);
			else if (e.getSource() == nYearBut)
				moveMonth(12);

			curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>" + ((calMonth + 1) < 10 ? "0" : "")
					+ (calMonth + 1) + " / " + calYear + "</th></tr></table></html>");
			showCal();
		}
	}

	// 달력 날짜 선택 시 액션
	private class listenForDateButs implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int k = 0, l = 0;
			for (int i = 0; i < CAL_HEIGHT; i++) {
				for (int j = 0; j < CAL_WIDTH; j++) {
					if (e.getSource() == dateButs[i][j]) { // 선택된 날짜 저장
						k = i;
						l = j;

					}
				}
			}
			if (!(k == 0 && l == 0))
				calDayOfMon = calDates[k][l]; // today버튼을 눌렀을때도 이 actionPerformed함수가 실행되기 때문에 넣은 부분

			for (int i = 0; i < CAL_HEIGHT; i++) {
				for (int j = 0; j < CAL_WIDTH; j++) {
					if (e.getSource() == dateButs[i][j]) {
						k = i;
						l = j;
						
						
//						System.out.println(calMonth+1);
						
						selectStr = calYear + "" + ( (calMonth + 1) <10 ? "0":"") + (calMonth+1) + "" + ( (calDayOfMon <10) ? "0":"" )+calDayOfMon;
						System.out.println(selectStr);
						tfDate.setText(selectStr);
							

					}
				}
			}

			System.out.println(dateButs[k][l].getText());
			
			cal = new GregorianCalendar(calYear, calMonth, calDayOfMon);

			// 날짜
			String dDayString = new String();
			int dDay = ((int) ((cal.getTimeInMillis() - today.getTimeInMillis()) / 1000 / 60 / 60 / 24));
			if (dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR))
					&& (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
					&& (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)))
				dDayString = "Today";
			else if (dDay >= 0)
				dDayString = "D-" + (dDay + 1);
			else if (dDay < 0)
				dDayString = "D+" + (dDay) * (-1);

		}
	}

	public static void main1(String[] args) {
		
		new MovieList();

	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnSearch) {
			
			
		    if (tfDate.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "날짜를 입력해주세요.", "오류 메시지", JOptionPane.WARNING_MESSAGE);
			}
			else {
				
				
				returnTable();
				//panelTable.requestFocus();
//				model.setNumRows(0);
//				
//				System.out.println("tf : " + tfDate.getText());
//				
//				api.setDATE_FMT(tfDate.getText());
//				api.requestAPI();
//				
//				String[] rsArr = new String[4]; // 값 받아올 배열
//				for(int i=0; i<api.getCo().size();i++) {
//					
//					rsArr[0] = api.getName().get(i);
//					rsArr[1] = api.getGenre().get(i);
//					rsArr[2] = api.getLimit().get(i);
//					rsArr[3] = api.getTime().get(i);
//					
//					model.addRow(rsArr);
//					
//				}
				
//				String returnSelect = "SELECT return.returnid, umbrella.UMBRELLAID ,student.STUDENTID , student.NAME , TO_CHAR(rental.rentaldate, \'YYYY-MM-DD\'), TO_CHAR(return.returndate, \'YYYY-MM-DD\') "
//						+ "FROM \"RETURN\" return , STUDENT student , UMBRELLA umbrella, RENTAL rental "
//						+ "WHERE return.RENTALID = rental.rentalid AND rental.studentid = student.studentid AND rental.umbrellaid = umbrella.umbrellaid and return.RETURNDATE BETWEEN '"
//						+ StrS + "' AND '" + strL
//						+ "'ORDER BY RETURN.RETURNDATE DESC , RETURN.RENTALID DESC";
//				
//				ResultSet rs = DB.getResultSet(returnSelect); // 데이터 불러오기
//				String[] rsArr = new String[6];
//				try {
//					while (rs.next()) {
//
//						for (int i = 0; i < rsArr.length; i++) {
//							rsArr[i] = rs.getString(i + 1);
//						}
//
//						model.addRow(rsArr); // 테이블에 추가
//
//					}
//				} catch (SQLException exception) {
//					// TODO Auto-generated catch block
//					exception.printStackTrace();
//				}
//
//				table = new JTable(model);
				//tfDate.setText("");
				JOptionPane.showMessageDialog(null, "검색이 완료되었습니다.", "검색 완료", JOptionPane.INFORMATION_MESSAGE);
				
			}
			
		}

		else if (obj == btnReset) {
			tfDate.setText("");
			returnTable();
		}
		
		else if(obj == btnAdd) {
			if(lblChoiceName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "영화를 선택해주세요.", "오류 메시지", JOptionPane.WARNING_MESSAGE);
			}
			else if(tfURL.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "URL을 입력해주세요.", "오류 메시지", JOptionPane.WARNING_MESSAGE);
			}
			else {
				
				DBconnect.DB();   	
		    	String val = "0";
		    	String str = "SELECT COUNT(*) FROM MOVIE m WHERE MOVIE_NAME LIKE '" + clickName + "'";
		    	String cnt = "select COUNT(*) FROM MOVIE ";
		    	String num = "0";
		    	
		    	ResultSet re = DBconnect.getResultSet(str);
		    	try {
					while(re.next()) {
						JOptionPane.showMessageDialog(null, "동일한 영화가 있습니다.", "오류 메시지", JOptionPane.WARNING_MESSAGE);
						val = re.getString(1); 
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		    	
		    	if(val.equals("0")) {
		    		ResultSet result = DBconnect.getResultSet(cnt);
		    		try {
		    			while(result .next()) {
		    				num = result .getString(1); 
		    			}
		    		} catch (SQLException e2) {
		    			e2.printStackTrace();
		    		}
		    		int n = Integer.parseInt(num)+1;
		    		num = Integer.toString(n);
		    		System.out.println(num);
		    		String sql = "INSERT INTO MOVIE "
		        			+ "(MOVIE_ID, MOVIE_NAME, MOVIE_GENRE, MOVIE_LIMIT, RUNNINGTIME, URL) "
		        			+ "VALUES('" +
		        			num +  "', '" +
		        			clickName +  "', '" + 
		        			clickGenre + "', '" + 
		        			clickLimit + "', '" + 
		        			clickTime +  "', '" +
		        			tfURL.getText() +"')";
		    		DBconnect.getupdate(sql);
		    		JOptionPane.showMessageDialog(null, "< " + clickName + " > 추가되었습니다.", "추가 완료", JOptionPane.INFORMATION_MESSAGE);
					
		    	}
		    	else {
		    		System.out.println("실패!");
		    	}
		    	tfURL.setText("");
		    	lblChoiceName.setText("");
			}
		}
	}
	public void BtnStyle(JButton btnFont){
	      Font fontBtn = new Font("HY헤드라인M", Font.PLAIN, 15);
	      btnFont.setFont(fontBtn); // 폰트 스타일 적용
	      btnFont.setForeground(Color.white); // 글자색
	      btnFont.setBackground(new Color(18, 52, 120));
	      btnFont.setBorderPainted(false); // 테두리 없애기
	   }

	@Override
	public void mouseClicked(MouseEvent e) {
		mtable = (JTable) e.getComponent();
	    model = (DefaultTableModel) table.getModel();
		
		Object ob = e.getSource();
		if(ob == table) {
			
			int row = table.getSelectedRow();
			TableModel data = table.getModel();
			
			clickName = (String) data.getValueAt(row, 0);
			clickGenre = (String) data.getValueAt(row, 1);
			clickLimit = (String) data.getValueAt(row, 2);
			clickTime = (String) data.getValueAt(row, 3);
			
//			
//			// 테이블 내용 저장
//			Object value = table.getValueAt(row, 0);
//			clickName = value.toString();
//			
//			Object value1 = table.getValueAt(row, 1);
//			clickGenre =  value1.toString();
//					
//			Object value2 = table.getValueAt(row, 2);
//			clickLimit =  value2.toString();
//					
//			Object value3 = table.getValueAt(row, 3);
//			clickTime =  value3.toString();
			
			lblChoiceName.setText(clickName);
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
