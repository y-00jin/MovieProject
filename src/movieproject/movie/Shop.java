package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import movieproject.CustomUI;
import movieproject.controller.Controller;
import movieproject.payment.ReservationCheck;
import movieproject.util.StyleSet;
import oracle.net.aso.b;

public class Shop extends JFrame implements ActionListener, MouseListener {
	private String path_food = "resource/movie_food/food";
	private String path_drink = "resource/movie_food/drink";
	private String path_sets = "resource/movie_food/set";
	private String path_ice = "resource/movie_food/ice";
	private JPanel main_panel;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JPanel menu;
	private JButton ice_btn;
	private JButton food_btn, drink_btn, set_btn;
	CustomUI cus = new CustomUI(main_panel);
	private Controller con = Controller.getInstance();
	private JScrollPane scroll;
	private File path;
	private int len;
	private String[] sel_menu = { "카라멜 팝콘", "팝콘", "나초", "핫도그", "오징어 버터구이", "포테이토", "순살치킨", "프레즐", 
			"콜라", "사이다", "오렌지", "자몽 에이드", "레몬 에이드", "아이스티", 
			"딸/바 스무디", "망고 스무디", "초코렛", "바닐라", "쿠키앤크림", 
			"오리지널 콤보", "즉석구이 콤보", "더블 콤보" }; // 메뉴
																														// 선택용
																														// 배열
	private int[] price = { 4500, 4500, 4500, 5000, 5500, 6000, 7000, 5000, 2200, 2200, 2200, 5000, 5000, 4000, 6000,
			6000, 6000, 6000, 6000, 9000, 13500, 12500 };
	private int[] menu_num = new int[22];
	private JPanel select;
	private JButton back;
	private JButton next;
	private DefaultTableModel model;
	private JTable table;
	private JLabel[] food;
	private JLabel j;

	public Shop() {
		setTitle("INHA CINEMA");
		setSize(980, 600);
		setLocationRelativeTo(this); // 모니터 가운데 위치
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창에서 닫기 버튼 누르면 콘솔 종료

		main_panel = new JPanel();
		main_panel.setLayout(null);
//		main_panel.setBounds(0, 0, 965, 600);
		main_panel.setBackground(Color.white);

		add(main_panel);
		addTitle();
		addmenu();

		food_set();
//		ice_set();
		System.out.println(sel_menu.length + " " + price.length);
		setVisible(true);
	}

	private void addmenu() {
		menu = new JPanel();
		int x = 130;
		int y = 90;
		menu.setBackground(Color.white);
		menu.setBounds(50, 130, 600, 400);

		food_btn = cus.setBtn("food", "스낵", 10);
//		best_btn = new JButton("추천메뉴");
		food_btn.setBounds(50, 90, x, 55);
		food_btn.setForeground(Color.cyan);
		food_btn.setBackground(Color.white);
		food_btn.addActionListener(this);
		

		////////////////////////////////

		drink_btn = cus.setBtn("drink", "음료", 10);
//		drink_btn = new JButton("음식");
		drink_btn.setBounds(y = y + 120, 90, x, 55);
		drink_btn.setBackground(Color.orange);
		drink_btn.addActionListener(this);

		ice_btn = cus.setBtn("ice", "ICE", 10);
		ice_btn.setBounds(y = y + 155, 90, x, 55);
		ice_btn.setBackground(Color.orange);
		ice_btn.addActionListener(this);

		set_btn = cus.setBtn("set", "Set", 10);
		set_btn.setBounds(520, 90, x, 55);
		set_btn.setBackground(Color.orange);
		set_btn.addActionListener(this);

		main_panel.add(menu);
		main_panel.add(set_btn);
		main_panel.add(drink_btn);
		main_panel.add(food_btn);
		main_panel.add(ice_btn);

		select_menu();

		scroll = new JScrollPane();
//		scroll.setBounds(0, 0, 600, 400);
		scroll.setPreferredSize(new Dimension(600, 400));
//		scroll.getViewport().setBackground(Color.white);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane = new JScrollPane(chatting, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		menu.add(scroll);
	}

	private void select_menu() {
		select = new JPanel();
		select.setBounds(665, 130, 285, 330);
		select.setBackground(Color.cyan);

//		table_panal = new JPanel();
//		table_panal.setBounds(140, 100, 450, 300);
		Vector<String> header = new Vector<String>();
		header.add("품목");
		header.add("개수");
		header.add("가격");
		model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.setBackground(Color.white);
		table.addMouseListener(this);
		JTableHeader hd = table.getTableHeader();

		hd.setBackground(Color.pink);
		scroll = new JScrollPane(table);
		scroll.getViewport().setBackground(Color.white);
		scroll.setPreferredSize(new Dimension(285,330));

	
		select.add(scroll);
		select.setBackground(Color.white);


		back = new JButton("이전");
		back.setForeground(Color.black);
		back.setBackground(Color.white);
		back.setBounds(695, 490, 80, 30);
		back.addActionListener(this);

		next = new JButton("다음");
		next.setForeground(Color.black);
		next.setBackground(Color.white);
		next.setBounds(695 + 130, 490, 80, 30);
		next.addActionListener(this);

		main_panel.add(back);
		main_panel.add(next);

		main_panel.add(select);
	}

	private void addTitle() {

		pTitle = new JPanel();
		pTitle.setBounds(0, 0, 965, 50);
		pTitle.setLayout(new BorderLayout());
		pTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		pTitle.setBackground(Color.pink);

		lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 25));
		lblTitle.setForeground(Color.white);
		pTitle.add(lblTitle, BorderLayout.WEST);

		btnBack = new JButton("돌아가기");
		StyleSet.btnFont(btnBack, Font.PLAIN, 15);
		btnBack.setForeground(Color.white);
		btnBack.setBackground(Color.pink);
		btnBack.setBorderPainted(false);
//		btnBack.addActionListener(this);

		pTitle.add(btnBack, BorderLayout.EAST);
		main_panel.add(pTitle);
	}

	////////////////////////////////////////////////////////////////////
	private void food_set() {
		JPanel set = new JPanel();
		String [] temp = {"카라멜 팝콘", "팝콘", "나초", "핫도그", "오징어 버터구이", "포테이토", "순살치킨", "프레즐"};
		set.setBackground(Color.white);
		set.setLayout(new GridLayout(2, 4, 3, 0));
		path = new File(path_food);
		len = path.list().length;

		food = new JLabel[len];

		for (int i = 0; i < len; i++) {
			System.out.println(path_food + "/food" + Integer.toString(i + 1) + ".png");
			ImageIcon img = imgcut(150, 150, path_food + "/food" + Integer.toString(i + 1) + ".png");
			food[i] = new JLabel(img);
			food[i].setName(temp[i]);
			food[i].addMouseListener(this);
			set.add(food[i]);
		}
		scroll.setViewportView(set);
	}

	private void drink_set() {
		JPanel set = new JPanel();
		String [] temp = {"콜라", "사이다", "오렌지", "자몽 에이드", "레몬 에이드", "아이스티"};
		set.setBackground(Color.white);
		set.setLayout(new GridLayout(2, 4, 3, 0));
		path = new File(path_drink);
		len = path.list().length;

		food = new JLabel[len];

		for (int i = 0; i < len; i++) {
			System.out.println(path_drink + "/drink" + Integer.toString(i + 1) + ".png");
			ImageIcon img = imgcut(150, 150, path_drink + "/drink" + Integer.toString(i + 1) + ".png");
			food[i] = new JLabel(img);
			food[i].setName(temp[i]);
			food[i].addMouseListener(this);
			set.add(food[i]);
		}
		scroll.setViewportView(set);
	}

	private void sets_set() {
		JPanel set = new JPanel();
		String [] temp = {"오리지널 콤보", "즉석구이 콤보", "더블 콤보"};
		set.setBackground(Color.white);
//		set.setLayout(new GridLayout(2, 4, 3, 0));
		set.setLayout(new FlowLayout(FlowLayout.LEFT));
		path = new File(path_sets);
		len = path.list().length;

		food = new JLabel[len];

		for (int i = 0; i < len; i++) {
			System.out.println(path_sets + "/set" + Integer.toString(i + 1) + ".png");
			ImageIcon img = imgcut(150, 150, path_sets + "/set" + Integer.toString(i + 1) + ".png");
			food[i] = new JLabel(img);
			food[i].setName(temp[i]);
			food[i].addMouseListener(this);
			set.add(food[i]);
		}
		scroll.setViewportView(set);
	}

	private void ice_set() {
		JPanel set = new JPanel();
		
		String [] temp = {"딸/바 스무디", "망고 스무디", "초코렛", "바닐라", "쿠키앤크림"};
		set.setBackground(Color.white);
		set.setLayout(new GridLayout(2, 4, 3, 0));
//		set.setLayout(new FlowLayout(FlowLayout.LEFT));
		path = new File(path_ice);
		len = path.list().length;

		food = new JLabel[len];

		for (int i = 0; i < len; i++) {
			System.out.println(path_ice + "/ice" + Integer.toString(i + 1) + ".png");
			ImageIcon img = imgcut(150, 150, path_ice + "/ice" + Integer.toString(i + 1) + ".png");
			food[i] = new JLabel(img);
			food[i].setName(temp[i]);
			food[i].addMouseListener(this);
			set.add(food[i]);
		}
		scroll.setViewportView(set);
	}

	///////////////////////////////////////////////////////////
	private ImageIcon imgcut(int x, int y, String path) {
		ImageIcon con = new ImageIcon(path);

		Image ximg = con.getImage();
		Image yimg = ximg.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
		con = new ImageIcon(yimg);
		return con;
	}

	public static void main(String[] args) {
		new Shop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		String save = new String();
		if(ob == next) {
			for(int i = 0; i < menu_num.length; i++) {
				if(menu_num[i] > 0) {
					save += sel_menu[i] + ":" + Integer.toString(menu_num[i]) + ":" + Integer.toString(menu_num[i]*price[i]) + ",";
				}
			}
			save = save.substring(0, save.length()-1);
			con.setMenu(save);
			System.out.println("//////////////////////////");
			System.out.println(con.getMovieName());
			System.out.println(con.getSel_date());
			System.out.println(con.getSel_time());
			System.out.println(con.getSeat());
			System.out.println(con.getMenu());
			new ReservationCheck();
		}
		
		if(ob == back) {
			dispose();
		}

		if (ob == ice_btn || ob == food_btn || ob == drink_btn || ob == set_btn) {
			JButton j = (JButton) ob;
			System.out.println(j.getName());
			if (ob == ice_btn) {
				food_btn.setForeground(Color.black);
				drink_btn.setForeground(Color.black);
				set_btn.setForeground(Color.black);
				ice_set();
			} else if (ob == food_btn) {
				ice_btn.setForeground(Color.black);
				drink_btn.setForeground(Color.black);
				set_btn.setForeground(Color.black);
				food_set();
			} else if (ob == drink_btn) {
				ice_btn.setForeground(Color.black);
				food_btn.setForeground(Color.black);
				set_btn.setForeground(Color.black);
				drink_set();
			} else if (ob == set_btn) {
				ice_btn.setForeground(Color.black);
				drink_btn.setForeground(Color.black);
				food_btn.setForeground(Color.black);
				sets_set();
			}
			j.setForeground(Color.cyan);
			scroll.repaint();
			scroll.requestFocus();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object ob = e.getSource();
		if(ob == table) {
			System.out.println("테이블");
			int row = table.getSelectedRow();
	        int col = table.getSelectedColumn();
	        
	        Object v = table.getValueAt(row, 0);
	        model.setNumRows(0);
	        String ti = v.toString();
	        System.out.println(ti);
	        for(int i = 0; i < sel_menu.length; i++) {
	        	if(ti.equals(sel_menu[i])) {
//	        		System.out.println(i);
	        		menu_num[i]--;
	        		System.out.println(menu_num[i]);
	        		break;
	        	}
	        }
	        ////////////////////////////////////////////
			for(int i = 0; i < sel_menu.length; i++) {
				if(menu_num[i] > 0) {
					String [] str = {sel_menu[i], Integer.toString(menu_num[i]), Integer.toString(menu_num[i]*price[i])};
					model.addRow(str);
				}
				
			}
//			System.out.println(ti);
		}
		for(int i = 0; i < sel_menu.length; i++) {
			if(ob != table) j = (JLabel) ob;
			if(j.getName().equals(sel_menu[i]) && ob != table) {
//				System.out.println(j.getName());
				menu_num[i]++;
			}
		}
		model.setNumRows(0);
		for(int i = 0; i < sel_menu.length; i++) {
			if(menu_num[i] > 0) {
				String [] str = {sel_menu[i], Integer.toString(menu_num[i]), Integer.toString(menu_num[i]*price[i])};
				model.addRow(str);
			}
			
		}
	}
	@Override
	public void mousePressed(MouseEvent e) { // 눌렀을때
//		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("3");
//		menu.repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) { // 버튼 위에 올라갈때
//		System.out.println("4");
//		menu.repaint();

	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println("6");
//		menu.repaint();
//		menu.revalidate();

	}
}
