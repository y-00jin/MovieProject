package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import movieproject.DBconnect;
import movieproject.controller.Controller;
import movieproject.util.StyleSet;

public class Movie extends JFrame implements MouseListener, ActionListener, ListSelectionListener{
	private DefaultTableModel model;
	private JTable table;
	private String posurl;
	private JPanel main_panel;
	private String[] set = { " 예매" };
	private JScrollPane scroll;
	private BufferedImage im;
	private Controller con = Controller.getInstance();
	private JList<String> tiket = new JList<String>();
	private JLabel poster;
	private ImageIcon img;
	private String mv_name;
	private URL url;
	private static int cnt = 0;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	public Movie() {
		setTitle("INHA CINEMA");
		setSize(965, 600);
		setLocationRelativeTo(this); //모니터 가운데 위치
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		
		main_panel = new JPanel();
		//main_panel = new JPanel();
//	    main_panel.setLayout(new BorderLayout());
		main_panel.setLayout(null);
	    main_panel.setBackground(Color.WHITE);
	    
	    add(main_panel);
	    
	    addTitle();
		setmoviechoice();
		poster();
		
		setVisible(true);
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
		btnBack.addActionListener(this);
		
		pTitle.add(btnBack, BorderLayout.EAST);
		main_panel.add(pTitle);
	}
	private void poster() {
		
		JPanel poster_panal = new JPanel();
		poster_panal.setBounds(30, 80, 350, 450);
		img = new ImageIcon("resource/image/base.png");
		

        Image ximg = img.getImage();
        Image yimg = ximg.getScaledInstance(350, 450, java.awt.Image.SCALE_SMOOTH);
        img = new ImageIcon(yimg);
        
        poster = new JLabel(img);
        poster_panal.setBackground(Color.WHITE);
		poster_panal.add(poster);
		main_panel.add(poster_panal);
	}
	private void setmoviechoice() {
		int x = 500;
		int y = 450;
		JPanel table_panal = new JPanel();
		table_panal.setBounds(420, 80, x, y);
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
	      
	      String sql = "SELECT DISTINCT m.MOVIE_NAME, m.MOVIE_GENRE, m.MOVIE_LIMIT, m.RUNNINGTIME "
	      		+ "FROM MOVIE m, MOVIE_TIME mt WHERE m.MOVIE_ID = mt.MOVIE_ID";
	      
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
	      table.setBackground(Color.white);
	      tiket.setListData(set);
	      tiket.addListSelectionListener(this);
	      tiket.setSize(37, 20);
	      tiket.setBorder(new LineBorder(Color.PINK));
	      tiket.setVisible(false);
	      
	      
	      table.add(tiket);
	      
	      JTableHeader hd = table.getTableHeader();
	   
	      hd.setBackground(Color.pink);
	      Color c = new Color(254, 228, 254);
	      scroll.getViewport().setBackground(Color.white);
//	      main_panel.add(scroll, BorderLayout.EAST);
	      scroll.setPreferredSize(new Dimension(x, 440));
	      table_panal.setBackground(Color.white);
	      table_panal.add(scroll);
	      main_panel.add(table_panal);
        
	}
	
	
	public static void main(String[] args) {
		new Movie();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		Object ob = e.getSource();
		if(ob == table) {
			cnt = 0;
			tiket.setVisible(false);
//			테이블 클릭 이벤트
//			//////////////////////////////////////////////////////////////////////////
			int row = table.getSelectedRow();
	        int col = table.getSelectedColumn();
	         
			Object v = table.getValueAt(row, 0);
			
	        String ti = v.toString();
			System.out.println(ti);
			
			if (e.getClickCount() >= 2) {
				mv_name = ti;
	            tiket.setVisible(true);
	            tiket.setLocation(e.getX() + 10, e.getY() - 10);
	         }
			
			String postersql = "select url from MOVIE where movie_name = '" + v.toString() + "'";
	         ResultSet re = DBconnect.getResultSet(postersql);
	         try {
	            while (re.next()) {
	               posurl = re.getString(1);
	            }
	         } catch (SQLException e1) {
	            e1.printStackTrace();
	         }
	         System.out.println(posurl);
	         if (posurl == null)
	            posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";
//	         System.out.println(posurl);
	         try {
	            url = new URL(posurl);
	            im = ImageIO.read(url);

	            try {
	               ImageIcon con = new ImageIcon(im);

	               Image ximg = con.getImage();
	               Image yimg = ximg.getScaledInstance(350, 450, java.awt.Image.SCALE_SMOOTH);
	               img = new ImageIcon(yimg);
	            } catch (Exception eE) {
	               
	               	
	            }
	         } catch (Exception ee) {
	        	 posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";

	               try {
	                  url = new URL(posurl);
	                  im = ImageIO.read(url);

	                  ImageIcon con = new ImageIcon(im);
	                  Image ximg = con.getImage();
	                  Image yimg = ximg.getScaledInstance(350, 450, java.awt.Image.SCALE_SMOOTH);
	                  img = new ImageIcon(yimg);
	               } catch (IOException e2) {
	                  e2.printStackTrace();
	               }
	         }
	         poster.setIcon(img);
	         poster.repaint();
//	         /////////////////////////////////////////////////////////////////////////////////////////////////
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
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btnBack) {
			// new UserMain();	// 만약 메인에서 버튼 누를 때 창이 사라진다면 써줌,,!?
			dispose();
		}
	}
	////////////////////////////////////////////////////////////////////////
	@Override
	public void valueChanged(ListSelectionEvent e) { // 예매 클릭 했을때
		cnt++;
		if(cnt == 1) {
			con.setMovieName(mv_name);
			System.out.println(con.getMovieName());
			new Reservation();
		}
		tiket.clearSelection();
		con.frame.add(this);
	}
}
