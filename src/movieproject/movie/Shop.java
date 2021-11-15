package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import movieproject.CustomUI;
import movieproject.util.Style;
import oracle.net.aso.b;

public class Shop extends JFrame implements ActionListener, MouseListener{
	private JPanel main_panel;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JPanel menu;
	private JButton best_btn;
	private JButton food_btn, drink_btn, set_btn;
	CustomUI cus = new CustomUI(main_panel);
	private JScrollPane scroll;
	public Shop() {
		setTitle("INHA CINEMA");
		setSize(965, 600);
		setLocationRelativeTo(this); //모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		
		main_panel = new JPanel();
		main_panel.setLayout(null);
//		main_panel.setBounds(0, 0, 965, 600);
		main_panel.setBackground(Color.white);
		
		add(main_panel);
		addTitle();
		addmenu();
		
		food_set();
		
		setVisible(true);
	}
	
	private void addmenu() {
		menu = new JPanel();
		int x = 130;
		int y = 90;
		menu.setBackground(Color.white);
		menu.setBounds(50, 130, 600, 400);
		
		best_btn = cus.setBtn("best", "추천메뉴", 10);
//		best_btn = new JButton("추천메뉴");
		best_btn.setBounds(50, 90, x, 55);
		best_btn.setBackground(Color.white);
		best_btn.addActionListener(this);
		best_btn.addMouseListener(this);
		
		////////////////////////////////
		
		food_btn = cus.setBtn("food", "음식", 10);
//		food_btn = new JButton("음식");
		food_btn.setBounds(y = y + 120, 90, x, 55);
		food_btn.setBackground(Color.orange);
		food_btn.addActionListener(this);
		food_btn.addMouseListener(this);		
		
		drink_btn = cus.setBtn("drink", "음료", 10);
		drink_btn.setBounds(y = y + 155, 90, x, 55);
		drink_btn.setBackground(Color.orange);
		drink_btn.addActionListener(this);
		drink_btn.addMouseListener(this);
		
		set_btn = cus.setBtn("set", "세트메뉴", 10);
		set_btn.setBounds(520, 90, x, 55);
		set_btn.setBackground(Color.orange);
		set_btn.addActionListener(this);
		set_btn.addMouseListener(this);
		
		main_panel.add(menu);
		main_panel.add(set_btn);
		main_panel.add(drink_btn);
		main_panel.add(food_btn);
		main_panel.add(best_btn);
		
		scroll = new JScrollPane();
//		scroll.setBounds(0, 0, 600, 400);
		scroll.setPreferredSize(new Dimension(600, 400));
		scroll.getViewport().setBackground(Color.white);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane = new JScrollPane(chatting, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		menu.add(scroll);
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
		Style.btnFont(btnBack, Font.PLAIN, 15);
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
		set.setBackground(Color.white);
		set.setLayout(new GridLayout(1, 2));
		ImageIcon img = imgcut(150, 150, "resource/movie_food/food1.png");
		JLabel la = new JLabel(img);
		
		img = imgcut(150, 150, "resource/movie_food/food2.png");
		JLabel la2 = new JLabel(img);
		
		set.add(la);
		set.add(la2);
		scroll.setViewportView(set);
//   		scroll.add(set);
	}
	
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
		
		if(ob == best_btn || ob == food_btn || ob == drink_btn || ob == set_btn) {
			
			JButton j = (JButton) ob;
			System.out.println(j.getName());
			if(ob == best_btn) {
				food_btn.setForeground(Color.black);
				drink_btn.setForeground(Color.black);
				set_btn.setForeground(Color.black);
			}
			else if(ob == food_btn) {
				best_btn.setForeground(Color.black);
				drink_btn.setForeground(Color.black);
				set_btn.setForeground(Color.black);
			}
			else if(ob == drink_btn) {
				best_btn.setForeground(Color.black);
				food_btn.setForeground(Color.black);
				set_btn.setForeground(Color.black);
			}
			else if(ob == set_btn) {
				best_btn.setForeground(Color.black);
				drink_btn.setForeground(Color.black);
				food_btn.setForeground(Color.black);
			}
			j.setForeground(Color.cyan);
			System.out.println("1");
			menu.repaint();
			menu.requestFocus();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) { // 눌렀을때
//		System.out.println("2");
//		menu.repaint();
		
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
