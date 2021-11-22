package movieproject.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import movieproject.DBconnect;
import movieproject.controller.Controller;
import movieproject.util.Style;

public class ReservationInfo extends JFrame implements ActionListener {

	private JPanel pMain;
	private JPanel pTitle;
	private JLabel lblTitle;
	private Controller controller;
	private String userId;
	private String strMovieName;
	private String strDate;
	private String strTime;
	private String strSeat;
	private String strFood;
	private JPanel pNoSearch;
	private JLabel lblNoSearch;
	private JButton btnBack;
	private JPanel pSearch;
	private String posurl;
	private ImageIcon img;
	private JLabel poster;
	private URL url;
	private BufferedImage im;
	private JLabel lblMovieName;
	private JPanel pReservInfo;
	private JLabel lblDateTitle;
	private JLabel lblTimeTitle;
	private JLabel lblSeatTitle;
	private JLabel lblMenuTitle;
	private JLabel lblDate;
	private JLabel lblTime;
	private JLabel lblSeat;
	private JPanel pFoodTable;
	private Vector<String> returnColumn;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnExit;
	private JLabel lblState;

	public ReservationInfo() {

		controller = Controller.getInstance();
		userId = controller.getUserId();

		setTitle("INHA CINEMA");
		setSize(900, 600);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료

		pMain = new JPanel();
		pMain.setLayout(null);
		pMain.setBackground(Color.WHITE);

		addTitle();

		SelectReservation();

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


		pMain.add(pTitle);
	}

	private void SelectReservation() {

		DBconnect.DB();
		userId = "a";
		String selectReservation = "SELECT MOVIE_NAME, TO_CHAR(MOVIE_DATE , 'YYYYMMDD'), MOVIE_TIME, SEAT, FOOD FROM MOVIE_RESERVATION WHERE ID='" + userId + "'";
		boolean check = false;

		ResultSet re = DBconnect.getResultSet(selectReservation);
		try {
			while (re.next()) {

				strMovieName = re.getString(1);
				strDate = re.getString(2);
				strTime = re.getString(3);
				strSeat = re.getString(4);
				strFood = re.getString(5);

				check = true;
			}
		} catch (SQLException e1) {
			check = false;
//			e1.printStackTrace();
		}

		if (check == true) {

			pSearch = new JPanel();
			pSearch.setBounds(20, 70, 840, 480);
			pSearch.setLayout(null);
			pSearch.setBackground(Color.white);

			JPanel poster_panal = new JPanel();
			poster_panal.setBounds(40, 30, 250, 350);
			img = new ImageIcon("resource/image/base.png");

			Image ximg = img.getImage();
			Image yimg = ximg.getScaledInstance(250, 350, java.awt.Image.SCALE_SMOOTH);
			img = new ImageIcon(yimg);

			poster = new JLabel(img);
			poster_panal.setBackground(Color.WHITE);
			poster_panal.add(poster);
			pSearch.add(poster_panal);

			String posterSql = "select url from MOVIE where movie_name = '" + strMovieName + "'";
			ResultSet rs = DBconnect.getResultSet(posterSql);
			try {
				while (rs.next()) {
					posurl = rs.getString(1);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// url이 없을 때
			if (posurl == null)
				posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";

			try {
				url = new URL(posurl);
				im = ImageIO.read(url);

				try {
					ImageIcon con = new ImageIcon(im);

					Image ximg2 = con.getImage();
					Image yimg2 = ximg2.getScaledInstance(250, 350, java.awt.Image.SCALE_SMOOTH);
					img = new ImageIcon(yimg2);
				} catch (Exception eE) {

				}
			} catch (Exception ee) {
				posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";

				try {
					url = new URL(posurl);
					im = ImageIO.read(url);

					ImageIcon con = new ImageIcon(im);
					Image ximg2 = con.getImage();
					Image yimg2 = ximg2.getScaledInstance(250, 350, java.awt.Image.SCALE_SMOOTH);
					img = new ImageIcon(yimg2);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			
			poster.setIcon(img);

			
			// 영화 이름
			lblMovieName = new JLabel(strMovieName);
			lblMovieName.setHorizontalAlignment(JLabel.CENTER);
			lblMovieName.setBounds(40, 400, 250, 20);
			Style.lblFont(lblMovieName, Font.PLAIN, 18);
			pSearch.add(lblMovieName);
			
			// 예매 정보
			
			pReservInfo = new JPanel();
			pReservInfo.setBounds(330, 20, 480, 440);
			pReservInfo.setBackground(Color.white);
			pReservInfo.setLayout(null);
			
			TitledBorder pInfoBorder = new TitledBorder(new LineBorder(Color.black, 1), "  예매 정보 ");
			pInfoBorder.setTitleFont(new Font("1훈새마을운동 R", Font.PLAIN, 17));
			pReservInfo.setBorder(pInfoBorder);
			
			lblDateTitle = new JLabel("날짜");
			lblTitle(lblDateTitle);
			lblDateTitle.setBounds(60, 50, 100, 20);
			pReservInfo.add(lblDateTitle);
			
			lblDate = new JLabel(strDate);
			Style.lblFont(lblDate, Font.PLAIN, 15);
			lblDate.setBounds(180, 50, 260, 20);
			pReservInfo.add(lblDate);
			
			lblTimeTitle = new JLabel("시간");
			lblTitle(lblTimeTitle);
			lblTimeTitle.setBounds(60, 90, 100, 20);
			pReservInfo.add(lblTimeTitle);
			
			lblTime = new JLabel(strTime);
			Style.lblFont(lblTime, Font.PLAIN, 15);
			lblTime.setBounds(180, 90, 260, 20);
			pReservInfo.add(lblTime);
			
			lblSeatTitle = new JLabel("좌석 정보");
			lblTitle(lblSeatTitle);
			lblSeatTitle.setBounds(60, 130, 100, 20);
			pReservInfo.add(lblSeatTitle);
			
			lblSeat = new JLabel(strSeat);
			Style.lblFont(lblSeat, Font.PLAIN, 15);
			lblSeat.setBounds(180, 130, 260, 20);
			pReservInfo.add(lblSeat);
			
			
			lblMenuTitle = new JLabel("먹거리");
			lblTitle(lblMenuTitle);
			lblMenuTitle.setBounds(60, 170, 100, 20);
			pReservInfo.add(lblMenuTitle);
			
			pFoodTable = new JPanel();
			pFoodTable.setBounds(60, 210, 360, 150);
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

			System.out.println("strFood : " + strFood);
			if (strFood == null) {

			} else {
				String[] arrMenu = strFood.split(",");
				for (int i = 0; i < arrMenu.length; i++) {

					String[] arrCount = new String[3];

					arrCount = arrMenu[i].split(":");

					model.addRow(arrCount);
				}
				
				lblState = new JLabel("준비중...");
				Style.lblFont(lblState, Font.PLAIN, 15);
				lblState.setBounds(180, 170, 260, 20);
				pReservInfo.add(lblState);
			}

			table.getColumnModel().getColumn(0).setPreferredWidth(200);
			table.getTableHeader().setReorderingAllowed(false); // 테이블 편집X
			table.setFillsViewportHeight(true); // 테이블 배경색
			JTableHeader tableHeader = table.getTableHeader(); // 테이블 헤더 값 가져오기
			tableHeader.setBackground(new Color(0xFFEAEA)); // 가져온 테이블 헤더의 색 지정
			JScrollPane sc = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			pFoodTable.add(sc);
			pReservInfo.add(pFoodTable);
			
			btnExit = new JButton("닫기");
			Style.btnFontStyle(btnExit, Font.PLAIN, 15, 0xFFEAEA);
			btnExit.addActionListener(this);
			btnExit.setBounds(60, 380, 100, 30);
			
			
			pReservInfo.add(btnExit);
			
			
			pSearch.add(pReservInfo);

			
			
			
			
			pMain.add(pSearch);

		} else {
			
			btnBack = new JButton("돌아가기");
			Style.btnFont(btnBack, Font.PLAIN, 15);
			btnBack.setForeground(Color.white);
			btnBack.setBackground(Color.pink);
			btnBack.setBorderPainted(false);
			btnBack.addActionListener(this);

			pTitle.add(btnBack, BorderLayout.EAST);
			
			pNoSearch = new JPanel();
			pNoSearch.setBounds(300, 250, 250, 50);
			pNoSearch.setBackground(Color.white);

			lblNoSearch = new JLabel("예매 정보가 없습니다.");
			Style.lblFont(lblNoSearch, Font.PLAIN, 23);

			pNoSearch.add(lblNoSearch);
			pMain.add(pNoSearch);

		}

	}

	
	public void lblTitle(JLabel lbl) {
		lbl.setHorizontalAlignment(JLabel.CENTER);
		Style.lblFont(lbl, Font.PLAIN, 15);
		lbl.setBackground(new Color(0xF6F6F6));
		lbl.setOpaque(true);
	}
	public static void main(String[] args) {
		new ReservationInfo();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnExit) {
			dispose();
		}
		else if(obj == btnBack) {
			dispose();
		}
	}
	
}
