package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import movieproject.util.Style;

public class OrderCheck extends JFrame implements ActionListener, MouseListener {

	private JPanel pMain;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JLabel lblOrder;
	private JPanel pTable;
	private Vector<String> returnColumn;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnOk;
	private JPanel pDetailsTable;
	private Vector<String> returnColumnDetail;
	private DefaultTableModel modelDetails;
	private JTable tableDetails;
	private JLabel lblDetails;
	private String clickMenu = "";
	private JTable mtable;

	public OrderCheck() {

		setTitle("INHA CINEMA");
		setSize(900, 600);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료

		pMain = new JPanel();
		pMain.setLayout(null);
		pMain.setBackground(Color.WHITE);

		// 타이틀
		addTitle();

		// 테이틀 라벨&버튼
		addTableLabel();

		// 테이블
		addTable();

		addTableDetails();

		add(pMain);

		setVisible(true);

	}

	private void addTitle() {

		pTitle = new JPanel();
		pTitle.setBounds(0, 0, 900, 50);
		pTitle.setLayout(new BorderLayout());
		pTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		pTitle.setBackground(Color.pink);

		lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 20));
		lblTitle.setForeground(Color.white);
		pTitle.add(lblTitle, BorderLayout.WEST);

		btnBack = new JButton("돌아가기");
		Style.btnFont(btnBack, Font.PLAIN, 15);
		btnBack.setForeground(Color.white);
		btnBack.setBackground(Color.pink);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(this);

		pTitle.add(btnBack, BorderLayout.EAST);
		pMain.add(pTitle);
	}

	private void addTableLabel() {

		lblOrder = new JLabel("| 주문");
		Style.lblFont(lblOrder, Font.PLAIN, 18);
		lblOrder.setBounds(40, 90, 100, 20);
		pMain.add(lblOrder);

		btnOk = new JButton("완료");
		Style.btnFontStyle(btnOk, Font.PLAIN, 15, 0xFFEAEA);
		btnOk.addActionListener(this);
		btnOk.setBounds(40, 450, 100, 30);

		pMain.add(btnOk);
	}

	private void addTable() {

		pTable = new JPanel();
		pTable.setLayout(new BorderLayout());
		pTable.setBounds(40, 130, 400, 300);
		pTable.setBackground(Color.white);

		// 테이블 헤더 생성
		returnColumn = new Vector<String>();

		returnColumn.add("ID");
		returnColumn.add("주문 내역");
		returnColumn.add("시간");

		model = new DefaultTableModel(returnColumn, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table = new JTable(model); // 테이블에 추가

		// 데이터 서버로부터 받아야함.
		String[] reArr = new String[3];
		
		
		
		
		for (int i = 0; i < 3; i++) {
			if(i==0) {
				reArr[0] = "a";
				reArr[1] = "팝콘:1:8500,츄러스:3:6000";
				reArr[2] = "20211122 13:00";
				model.addRow(reArr);
			}
			else if(i==1) {
				reArr[0] = "b";
				reArr[1] = "콜라:2:1500";
				reArr[2] = "20211122 14:00";
				model.addRow(reArr);
			}
			else {
				reArr[0] = "c";
				reArr[1] = "나쵸:1:7500,사이다:1:1500";
				reArr[2] = "20211122 15:00";
				model.addRow(reArr);
			}
			
		}

		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFillsViewportHeight(true); // 테이블 배경색
		table.addMouseListener(this);
		JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(new Color(0xFFEAEA)); // 가져온 테이블 헤더의 색 지정
		// tableHeader.setForeground(Color.white);
		JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pTable.add(sc);
		pMain.add(pTable);

	}

	private void addTableDetails() {

		lblDetails = new JLabel("| 상세 주문 내역");
		Style.lblFont(lblDetails, Font.PLAIN, 18);
		lblDetails.setBounds(480, 90, 200, 20);
		pMain.add(lblDetails);

		pDetailsTable = new JPanel();
		pDetailsTable.setBounds(480, 130, 360, 300);
		pDetailsTable.setLayout(new BorderLayout());
		pDetailsTable.setBackground(Color.white);

		// 테이블 헤더 생성
		returnColumnDetail = new Vector<String>();

		returnColumnDetail.add("메뉴");
		returnColumnDetail.add("수량");

		modelDetails = new DefaultTableModel(returnColumnDetail, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		tableDetails = new JTable(modelDetails); // 테이블에 추가

		if (clickMenu.equals("")) {

		} else {
			String[] arrMenu = clickMenu.split(",");
			for (int i = 0; i < arrMenu.length; i++) {

				String[] arrCount = new String[3];

				arrCount = arrMenu[i].split(":");

				modelDetails.addRow(arrCount);
			}

		}

		tableDetails.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableDetails.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
		tableDetails.setFillsViewportHeight(true); // 테이블 배경색
		JTableHeader tableHeader = tableDetails.getTableHeader(); // 테이블 헤더 값 가져오기
		tableHeader.setBackground(new Color(0xFFEAEA)); // 가져온 테이블 헤더의 색 지정
		JScrollPane sc = new JScrollPane(tableDetails, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		pDetailsTable.add(sc);
		pMain.add(pDetailsTable);

	}

	public static void main(String[] args) {
		new OrderCheck();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		model = (DefaultTableModel) table.getModel();

		Object ob = e.getSource();
		if (ob == table) {

			int row = table.getSelectedRow();

			TableModel data = table.getModel();
			// System.out.println(row);

			clickMenu = (String) data.getValueAt(row, 1);
			reTableDetails();
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

	// 테이블 새로고침
	public void reTableDetails() {
		getpDetailsTable().removeAll();
		addTableDetails();
		getpDetailsTable().revalidate(); // 레이아웃 변화 재확인
		getpDetailsTable().repaint(); // 레이아웃 다시 가져오기
	}

	public JPanel getpDetailsTable() {
		return pDetailsTable;
	}

	
}
