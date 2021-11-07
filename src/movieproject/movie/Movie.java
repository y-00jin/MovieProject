package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import movieproject.DBconnect;

public class Movie extends JFrame implements MouseListener{
	private DefaultTableModel model;
	private JTable table;
	private JPanel main_panel;
	private String[] set = { " 예매" };
	private JScrollPane scroll;
	private JList<String> tiket = new JList<String>();
	public Movie() {
		setTitle("INHA CINEMA");
		setSize(900, 600);
		setLocationRelativeTo(this); //모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		
		main_panel = new JPanel();
		main_panel = new JPanel();
	    main_panel.setLayout(new BorderLayout());
	    main_panel.setBackground(Color.WHITE);
	    
	    add(main_panel);
	    
		setmoviechoice();
		
		
		setVisible(true);
	}
	private void setmoviechoice() {
		DBconnect.DB();
		Vector<String> header = new Vector<String>();
	      header.add("영화제목");
	      header.add("영화장르");
	      header.add("관람등급");
	      header.add("러닝타임");
	      model = new DefaultTableModel(header, 0) {
	         public boolean isCellEditable(int rowIndex, int mColIndex) {
	            return false;
	         }
	      };
	      table = new JTable(model);
	      table.getTableHeader().setReorderingAllowed(false);
	      
	      scroll = new JScrollPane(table);
	      
	      String sql = "SELECT DISTINCT m.MOVIE_NAME, "
	              + "m.MOVIE_GENRE, "
	              + "m.MOVIE_LIMIT, "
	              + "m.RUNNINGTIME FROM MOVIE m";
	      
	      ResultSet re = DBconnect.getResultSet(sql);
	      try {
	         while (re.next()) {
	            String name = re.getString(1);
	            String genre = re.getString(2);
	            String limit = re.getString(3);
	            String running_time = re.getString(4) + "분";

	            String[] str = { name, genre, limit, running_time };

	            model.addRow(str);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }

	      table.setRowHeight(40);
	      table.addMouseListener(this);
	      
	      DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
	      tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

	      TableColumnModel tm = table.getColumnModel();

	      for (int i = 0; i < tm.getColumnCount(); i++) {
	         tm.getColumn(i).setCellRenderer(tScheduleCellRenderer);
	      }

	      table.getColumnModel().getColumn(0).setPreferredWidth(150);
	      table.getColumnModel().getColumn(2).setPreferredWidth(5);
	      table.getColumnModel().getColumn(3).setPreferredWidth(5);

	      tiket.setListData(set);
	      //tiket.addListSelectionListener(this);
	      tiket.setSize(37, 20);
	      tiket.setBorder(new LineBorder(Color.PINK));
	      tiket.setVisible(false);
	      table.add(tiket);
	      
	      JTableHeader hd = table.getTableHeader();
	   
	      hd.setBackground(Color.pink);

	      scroll.setBackground(Color.WHITE);
	      main_panel.add(scroll);
        
	}
	public static void main(String[] args) {
		
		new Movie();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
