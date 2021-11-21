package movieproject.movie;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
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

public class Reservation extends JFrame implements ActionListener{
//	나이트 오브 더 데이 오브 더 돈 오브 더 손 오브 더 브라이드
//	private String[] str = {"2021-11-20","2021-11-21","2021-11-23","2021-11-24","2021-11-25","2021-11-26","2021-11-27"};
	private JPanel main_panel;
	private JComboBox<String> movie_time;
	private DefaultTableModel model;
	private JTable table;
	private JButton back,next;
	private Controller con = Controller.getInstance();
	private JScrollPane scroll;
	public Reservation() {
		DBconnect.DB();
		setTitle("INHA CINEMA");
		setSize(765, 520);
		setLocationRelativeTo(this); //모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		
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
		
		main_panel.add(back);
		main_panel.add(next);
	}
	private void addtable() {
		JPanel table_panal = new JPanel();
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
	      JTableHeader hd = table.getTableHeader();
		   
	      hd.setBackground(Color.pink);
	      scroll = new JScrollPane(table);
	      scroll.getViewport().setBackground(Color.cyan);
	      
	      table_panal.add(scroll);
	      table_panal.setBackground(Color.white);
	      main_panel.add(table_panal);
	}
	private void addmovie_infor() {
//		JLabel movie_name = new JLabel("나이트 오브 더 데이 오브 더 돈 오브 더 손 오브 더 브라이드");
		JLabel movie_name = new JLabel("귀멸의 칼날: 남매의 연");
//		JLabel movie_name = new JLabel(con.getMovieName());
		movie_name.setFont(new Font("배달의민족 도현", Font.ITALIC, 15));
		movie_name.setBounds(50, 55, 350, 30);
		movie_name.setHorizontalAlignment(JLabel.RIGHT);
		
		String temp = "";
		movie_time = new JComboBox<String>();
		try {
			String sql = "SELECT DISTINCT(mt.MOVIE_DATE) FROM MOVIE_TIME mt, MOVIE m WHERE mt.MOVIE_ID = m.MOVIE_ID AND m.MOVIE_NAME = '"+ movie_name.getText() +"'"
					+ "ORDER BY MOVIE_DATE";
			ResultSet re = DBconnect.getResultSet(sql);
			while(re.next()) {
				temp = re.getString(1);
				con.sel_date = temp;
				String date = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6, 8);
				movie_time.addItem(date);
			}
		} catch (Exception e) {
			System.out.println("sql 실패2");
		}

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
		if(ob == back) {
			dispose();
		}
		
	}
}
