package movieproject.movie;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import movieproject.DBconnect;
import movieproject.controller.Controller;

public class Reservation extends JFrame implements ActionListener, MouseListener{
//	나이트 오브 더 데이 오브 더 돈 오브 더 손 오브 더 브라이드
//	private String[] str = {"2021-11-20","2021-11-21","2021-11-23","2021-11-24","2021-11-25","2021-11-26","2021-11-27"};
	private JPanel main_panel;
	private JComboBox<String> movie_time;
	private DefaultTableModel model;
	private JTable table;
	private JButton back,next;
	private Controller con = Controller.getInstance();
	private JScrollPane scroll;
	private JPanel table_panal;
	private JLabel movie_name;
	public Reservation() {
		
		DBconnect.DB();
		setTitle("INHA CINEMA");
		setSize(765, 520);
		setLocationRelativeTo(this); //모니터 가운데 위치
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		
		main_panel = new JPanel();
		main_panel.setLayout(null);
	    main_panel.setBackground(Color.WHITE);
	    
	    add(main_panel);
	    
	    addtitle();
	    addmovie_infor();
	    addtable();
	    addbtn();
	    
	    setVisible(true);
	}
	private void addbtn() {
		back = new JButton("이전");
		back.setForeground(Color.black);
		back.setBackground(Color.white);
		back.setBounds(530, 425, 80, 30);
		back.addActionListener(this);
		
		next = new JButton("다음");
		next.setForeground(Color.black);
		next.setBackground(Color.white);
		next.setBounds(630, 425, 80, 30);
		next.addActionListener(this);
		
		main_panel.add(back);
		main_panel.add(next);
	}
	private void addtable() {
		table_panal = new JPanel();
		table_panal.setBounds(140, 100, 450, 300);
		Vector<String> header = new Vector<String>();
	      header.add("시작시간");
	      header.add("러닝타임");
	      header.add("남은 좌석");
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
	      
	      table_panal.add(scroll);
	      table_panal.setBackground(Color.white);
	      main_panel.add(table_panal);
	}
	private void addmovie_infor() {
//		con.setSel_date("20211123");
//		con.setSel_time("14:00");
//		JLabel movie_name = new JLabel("나이트 오브 더 데이 오브 더 돈 오브 더 손 오브 더 브라이드");
//		movie_name = new JLabel("귀멸의 칼날: 남매의 연");
		JLabel movie_name = new JLabel(con.getMovieName());
		movie_name.setFont(new Font("배달의민족 도현", Font.ITALIC, 15));
		movie_name.setBounds(50, 55, 350, 30);
		movie_name.setHorizontalAlignment(JLabel.RIGHT);
		
		String temp = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate now = LocalDate.now();
		String formatedNow = now.format(formatter);
//		System.out.println(formatedNow); 
	
		Vector<String> list = new Vector<String>();
		try {
			String sql = "SELECT DISTINCT(mt.MOVIE_DATE) FROM MOVIE_TIME mt, MOVIE m WHERE mt.MOVIE_ID = m.MOVIE_ID AND m.MOVIE_NAME = '"+ movie_name.getText() +"'"
					+ "ORDER BY MOVIE_DATE";
			ResultSet re = DBconnect.getResultSet(sql);
			while(re.next()) {
				temp = re.getString(1);
				String date = temp.substring(0, 10);
				System.out.println(date);
				Date date1 = dateFormat.parse(formatedNow);
	            Date date2 = dateFormat.parse(date);
				if(date1.compareTo(date2) != -1) { 
					list.add(date);
				}
				main_panel.requestFocus();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("sql 실패2");
		}
		
		movie_time = new JComboBox<String>(list);
		movie_time.addActionListener(this);
		movie_time.setBackground(Color.white);
		movie_time.setBounds(450, 55, 100, 30);
		movie_time.setFocusable(false);
		
		main_panel.add(movie_name);
		main_panel.add(movie_time);
		
	}
	private void addtitle() {
		JLabel lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 25));
		lblTitle.setForeground(Color.pink);
//		lblTitle.setBackground(Color.cyan);
		lblTitle.setBounds(10, 0, 200, 50);
		
		main_panel.add(lblTitle);
	}
	public static void main(String[] args) {
		
		new Reservation();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if(ob == movie_time) {
			con.setSel_date(movie_time.getSelectedItem().toString());
			model.setNumRows(0);
			System.out.println("1");
			Vector<String> time = new Vector<String>();
			Vector<String> run_time = new Vector<String>();
			Vector<String> seat = new Vector<String>();
			String sql = "SELECT mt.MOVIE_DATE, mt.MOVIE_TIME, m.RUNNINGTIME FROM MOVIE_TIME mt, MOVIE m WHERE mt.MOVIE_ID = m.MOVIE_ID "
					+ "AND m.MOVIE_NAME = '"+ con.getMovieName() +"' "
					+ "AND mt.MOVIE_DATE = '"+ con.getSel_date() +"'"
					+ "ORDER BY MOVIE_DATE";
			System.out.println(sql);
			ResultSet re = DBconnect.getResultSet(sql);
			try {
				while(re.next()) {
					System.out.println("2");
					time.add(re.getString(2));
					run_time.add(re.getString(3));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			for(int i = 0; i < time.size(); i++) {
				int len = 75;
				String sql2 = "SELECT SEAT FROM MOVIE_RESERVATION mr "
						+ "WHERE mr.MOVIE_NAME = '"+ con.getMovieName() +"' "
						+ "AND mr.MOVIE_DATE = '"+ con.getSel_date() + "' "
						+ "AND mr.MOVIE_TIME = '" + time.get(i) + "'";
				System.out.println(sql2);
				ResultSet r = DBconnect.getResultSet(sql2);
				try {
					while(r.next()) {
						String[] str = r.getString(1).split(",");
						len -= str.length;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				seat.add(Integer.toString(len));
				String[] str = {time.get(i) , run_time.get(i), seat.get(i)};
				model.addRow(str);
			}
//			model.addRow(str);
		}
		if(ob == back) {
			dispose();
		}
		if(ob == next) {
			System.out.println("next");
			new Seat_Selection();
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Object ob = e.getSource();
		if(ob == table) {
			int row = table.getSelectedRow();
	        int col = table.getSelectedColumn();
	        Object v = table.getValueAt(row, 0);
	        String ti = v.toString();
//	        System.out.println(ti);
			con.setSel_time(ti);
			System.out.println(con.getSel_time());
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
