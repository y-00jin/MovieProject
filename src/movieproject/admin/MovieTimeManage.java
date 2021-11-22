package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import movieproject.DBconnect;
import movieproject.util.Style;

public class MovieTimeManage extends JFrame implements ActionListener, MouseListener {

	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JPanel pCenter;
	private JLabel lblMovieSerarch;
	private JComboBox<String> movieNameCombo;
	private JButton btnSearch;
	private JPanel pTable;
	private Vector<String> returnColumn;
	private DefaultTableModel model;
	private JTable table;
	private String searchName = "";
	private String selectTable;
	private JButton btnReset;
	private JButton btnAdd;
	private JButton btnDel;
	private JTable mtable;
	private String clickCode = "";
	private String clickName = "";
	private String clickDate = "";
	private String clickTime = "";
	private String strCode;
	private int count;
	

	public MovieTimeManage() {
		setTitle("INHA CINEMA");
		setSize(850, 540);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료

		setBackground(Color.white);

		
		setLayout(new BorderLayout());

		// 타이틀 생성
		addPTitle();
		add(pTitle, BorderLayout.NORTH);

		// 가운데 패널 생성
		addPCenter();
		add(pCenter, BorderLayout.CENTER);

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
		Style.btnFont(btnBack, Font.PLAIN, 15);
		btnBack.setForeground(Color.white);
		btnBack.setBackground(Color.pink);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(this);
		pTitle.add(btnBack, BorderLayout.EAST);
	}

	private void addPCenter() {

		pCenter = new JPanel();
		pCenter.setLayout(null);
		pCenter.setBackground(Color.white);

		// 영화 선택
		lblMovieSerarch = new JLabel("영화 이름 ");
		Style.lblFont(lblMovieSerarch, Font.PLAIN, 15);
		lblMovieSerarch.setBounds(20, 30, 70, 30);
		pCenter.add(lblMovieSerarch);

		// DB로 모든 영화 이름 검색
		DBconnect.DB();
		String selectMovieName = "SELECT MOVIE_NAME FROM MOVIE";
		ResultSet rs = DBconnect.getResultSet(selectMovieName);
		Vector<String> vecMovieName = new Vector<String>();

		try {
			while (rs.next()) {

				vecMovieName.add(rs.getString(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 콤보박스 생성
		movieNameCombo = new JComboBox<String>(vecMovieName);
		movieNameCombo.setBounds(90, 30, 200, 30);
		movieNameCombo.setBackground(Color.white);
		movieNameCombo.setMaximumRowCount(10);
		pCenter.add(movieNameCombo);

		// 검색 버튼 생성
		btnSearch = new JButton("검색");
		btnSearch.setBounds(305, 30, 80, 30);
		btnSearch.setBackground(Color.pink);
		btnSearch.setForeground(Color.white);
		btnSearch.addActionListener(this);
		Style.btnFont(btnSearch, Font.PLAIN, 15);
		pCenter.add(btnSearch);

		// 새로고침 버튼 생성
		btnReset = new JButton("새로고침");
		btnReset.setBounds(395, 30, 100, 30);
		btnReset.setBackground(Color.pink);
		btnReset.setForeground(Color.white);
		btnReset.addActionListener(this);
		Style.btnFont(btnReset, Font.PLAIN, 15);
		pCenter.add(btnReset);

		// 테이블 생성
		addTable();

		btnAdd = new JButton("추가");
		btnAdd.setBackground(Color.pink);
		btnAdd.setForeground(Color.white);
		btnAdd.setBounds(20, 410, 80, 30);
		Style.btnFont(btnAdd, Font.PLAIN, 15);
		btnAdd.addActionListener(this);
		pCenter.add(btnAdd);

		btnDel = new JButton("삭제");
		btnDel.setBackground(Color.pink);
		btnDel.setForeground(Color.white);
		btnDel.setBounds(110, 410, 80, 30);
		Style.btnFont(btnDel, Font.PLAIN, 15);
		btnDel.addActionListener(this);
		pCenter.add(btnDel);
	}

	private void addTable() {
		count=0;
		// 테이블 생성
		pTable = new JPanel();
		pTable.setLayout(new BorderLayout());
		pTable.setBounds(20, 90, 800, 300);
		pTable.setBackground(Color.white);

		// 테이블 헤더 생성
		returnColumn = new Vector<String>();

		returnColumn.add("영화 코드");
		returnColumn.add("영화 이름");
		returnColumn.add("날짜");
		returnColumn.add("시간");

		model = new DefaultTableModel(returnColumn, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model); // 테이블에 추가
		if (searchName.equals("")) {
			selectTable = "SELECT DISTINCT m.MOVIE_ID, m.MOVIE_NAME , TO_CHAR(mt.MOVIE_DATE, 'YYYYMMDD'), mt.MOVIE_TIME FROM MOVIE m , MOVIE_TIME mt "
					+ "WHERE m.movie_id = mt.movie_id ORDER BY m.MOVIE_ID";
		} else {
			selectTable = "SELECT DISTINCT m.MOVIE_ID, m.MOVIE_NAME , TO_CHAR(mt.MOVIE_DATE, 'YYYYMMDD'), mt.MOVIE_TIME FROM MOVIE m , MOVIE_TIME mt "
					+ "WHERE m.movie_id = mt.movie_id AND m.MOVIE_NAME = '" + searchName + "' ORDER BY TO_CHAR(mt.MOVIE_DATE, 'YYYYMMDD')";
		}

		ResultSet re = DBconnect.getResultSet(selectTable);
		String[] reArr = new String[4];
		try {
			while (re.next()) {
				for (int i = 0; i < reArr.length; i++) {
					
					
						reArr[i] = re.getString(i + 1);
					
				}

				model.addRow(reArr);
				System.out.println(count);
				count++;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		table.getTableHeader().setReorderingAllowed(false);
		// 첫번째 행 숨김
		table.getColumn("영화 코드").setWidth(0);
		table.getColumn("영화 코드").setMinWidth(0);
		table.getColumn("영화 코드").setMaxWidth(0);

		table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		table.setFillsViewportHeight(true); // 테이블 배경색
		table.addMouseListener(this);
		JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(new Color(0xFFEAEA)); // 가져온 테이블 헤더의 색 지정
		// tableHeader.setForeground(Color.white);
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pTable.add(sc);
		pCenter.add(pTable);

	}

	public static void main(String[] args) {
		new MovieTimeManage();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnBack) {
			dispose();
		} else if (obj == btnAdd) {
			new MovieList();
		} else if (obj == btnDel) {
			if (clickCode.equals("")) {
				JOptionPane.showMessageDialog(null, "삭제할 데이터를 선택해 주세요.", "오류 메시지", JOptionPane.WARNING_MESSAGE);
			} else {
				String delTime = "DELETE FROM MOVIE_TIME WHERE MOVIE_ID='" + clickCode + "' AND MOVIE_DATE='"
						+ clickDate + "' AND MOVIE_TIME='" + clickTime + "'";
				DBconnect.DB();
				DBconnect.getupdate(delTime);
				reTable();
				JOptionPane.showMessageDialog(null, "삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
			}
			
		} else if (obj == btnReset) {
			searchName = "";
			reTable();
		} else if(obj == btnSearch) {
			searchName = movieNameCombo.getSelectedItem().toString();
			reTable();
		} 
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		mtable = (JTable) e.getComponent();
		model = (DefaultTableModel) table.getModel();

		Object ob = e.getSource();
		if (ob == table) {
			
			int row = table.getSelectedRow();
			if(row>=0 && row<=count) {
				TableModel data = table.getModel();
			// System.out.println(row);
			clickCode = (String) data.getValueAt(row, 0);
			clickName = (String) data.getValueAt(row, 1);
			clickDate = (String) data.getValueAt(row, 2);
			clickTime = (String) data.getValueAt(row, 3);
			System.out.println(clickCode + clickName + clickDate + clickTime);
			}
			
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

	public JPanel getpTable() {
		return pTable;
	}

	// 테이블 새로고침
	public void reTable() {
		getpTable().removeAll();
		addTable();
		getpTable().revalidate(); // 레이아웃 변화 재확인
		getpTable().repaint(); // 레이아웃 다시 가져오기
	}

}
