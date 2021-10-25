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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import movieproject.DBconnect;
import movieproject.login.login;

public class Main extends JFrame implements ActionListener, MouseListener, ListSelectionListener {

   private ImageIcon img;
   private DefaultTableModel model;
   private JPanel main_panel;
   private JScrollPane scroll;
   private JMenu main, movie_management, poster;
   private JMenuBar mb;
   // Font font = new Font("", Font.BOLD, 30);
   Font font = new Font("휴먼편지체", Font.BOLD, 30);
   private JLabel movie_sel, member_mod, Recommen, information;
   private JTable table;
   private String[] set = { " 예매" };
   private JList<String> tiket = new JList<String>();
   private String str, target, posurl;
   private Poster pos;
   private int w = 450, h = 600;
   private BufferedImage im;
   private String ti = "광해";
   private login log;
   private String id;
   public Main(String Title, int width, int height, login log) {
      this.log = log;
      id = log.getTfid().getText();
      this.setTitle(Title);
      setSize(width, height);
      setLocation(550, 100);
      // setLocationRelativeTo(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      main_panel = new JPanel();
      main_panel.setLayout(new BorderLayout());
      main_panel.setBackground(Color.WHITE);
      main_panel.addMouseListener(this);

      mb = new JMenuBar();
      main = new JMenu("영화선택");
      main.addMouseListener(this);
      main.addActionListener(this);
      movie_management = new JMenu("영화관리");
      movie_management.addActionListener(this);
      poster = new JMenu("포스터등록");
      poster.addActionListener(this);

      mb.add(main);
      mb.add(movie_management);
      mb.add(poster);

      // this.setJMenuBar(mb);
      String str = "https://img.freepik.com/free-photo/interior-of-cinema-hall-with-chairs_23-2147807425.jpg?size=626&ext=jpg&ga=GA1.2.1281513565.1614729600";
      try {
         URL url = new URL(str);
         Image im = ImageIO.read(url);

         ImageIcon con = new ImageIcon(im);
         Image ximg = con.getImage();
         Image yimg = ximg.getScaledInstance(width, 590, java.awt.Image.SCALE_SMOOTH);
         img = new ImageIcon(yimg);

      } catch (IOException e) {
         e.printStackTrace();
      }

      JLabel background = new JLabel(img);

      movie_sel = new JLabel("영화선택");
      movie_sel.addMouseListener(this);
      movie_sel.setFont(font);
      movie_sel.setBounds(215, 130, 130, 30);
      background.add(movie_sel);

      member_mod = new JLabel("회원정보수정");
      member_mod.addMouseListener(this);
      member_mod.setFont(font);
      member_mod.setBounds(350, 130, 170, 30);
      background.add(member_mod);

//      Recommen = new JLabel("추천영화");
//      Recommen.addMouseListener(this);
//      Recommen.setFont(font);
//      Recommen.setBounds(525, 130, 170, 30);
//      background.add(Recommen);
//
      information = new JLabel("예매정보");
      information.addMouseListener(this);
      information.setFont(font);
      information.setBounds(525, 130, 170, 30);
      background.add(information);

      main_panel.add(background);

      // setmoviechoice();

      add(main_panel);

      setVisible(true);

      pos = new Poster("포스터", w, h, this);
   }

   private void setmoviechoice() {
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
                           + "m.RUNNINGTIME FROM MOVIE m , TIME t "
                           + "WHERE m.MOVIE_NAME = t.MOVIE_NAME";
      //ResultSet re = DBconnect.getResultSet("select * from movie");
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
      tiket.addListSelectionListener(this);
      tiket.setSize(37, 20);
      tiket.setBorder(new LineBorder(Color.PINK));
      tiket.setVisible(false);
      table.add(tiket);
      
      JTableHeader hd = table.getTableHeader();
   
      hd.setBackground(Color.pink);

      scroll.setBackground(Color.WHITE);
      // .setBackground(Color.WHITE);
      main_panel.add(scroll);
//      System.out.println(log.getTfid().toString());
   }

   public static void main(String[] args) {
      //DBconnect.DB();
      // movietiketing("메인화면", 900, 600);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      
   }

   @Override
   public void mouseClicked(MouseEvent e) {
      Object ob = e.getSource();
      // System.out.println(e.FOCUS_EVENT_MASK);
      // System.out.println(e.getModifiers() + " " + MouseEvent.BUTTON3_MASK);
//      System.out.println(ob);
      if (ob == movie_sel) {
         main_panel.removeAll();
         setmoviechoice();
         main_panel.revalidate();
         main_panel.repaint();
      }
      else if(ob == member_mod) {
         new MemberModify("회원정보 수정", 600, 400, this);
      }
      else if(ob == information) {
         new MovieCheak("예매 정보",530, 300, this);
      }
      else if (ob == table) {
         int row = table.getSelectedRow();
         int col = table.getSelectedColumn();
         tiket.setVisible(false);

//         System.out.println(e.getModifiers());
//         System.out.println(e.getClickCount());
         if (e.getClickCount() >= 2) {
            tiket.setVisible(true);
            tiket.setLocation(e.getX() + 10, e.getY() - 10);
         }

         Object v = table.getValueAt(row, 0);
         ti = v.toString();
//         System.out.println(v);
//         str = "select * from movie where MOVIE_NAME = '" + v + "'";
//         ResultSet re = DBconnect.getResultSet(str);
//         
//         try {
//            while(re.next()) {
//               target = re.getString(1);
//            }
//         } catch (SQLException e1) {
//            e1.printStackTrace();
//         }

         String poster = "select url from url where movie_name = '" + v.toString() + "'";
         ResultSet re = DBconnect.getResultSet(poster);
         try {
            while (re.next()) {
               posurl = re.getString(1);
            }
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
         if (posurl == null)
            posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";
//         System.out.println(posurl);
         try {
            URL url = new URL(posurl);
            im = ImageIO.read(url);

            try {
               ImageIcon con = new ImageIcon(im);

               Image ximg = con.getImage();
               Image yimg = ximg.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
               img = new ImageIcon(yimg);
            } catch (Exception eE) {
               posurl = "https://png.pngtree.com/png-clipart/20190916/ourlarge/pngtree-tile-free-font-spot-text.jpg";

               try {
                  url = new URL(posurl);
                  im = ImageIO.read(url);

                  ImageIcon con = new ImageIcon(im);
                  Image ximg = con.getImage();
                  Image yimg = ximg.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
                  img = new ImageIcon(yimg);
               } catch (IOException e2) {
                  e2.printStackTrace();
               }

            }
         } catch (Exception ee) {

         }
         pos.setVisible(true);
         pos.getPos().setIcon(img);
         pos.revalidate();
         pos.repaint();

         // System.out.println(row + " " + col);
//         for (int i = 0; i < table.getColumnCount(); i++) {
//            System.out.print(table.getModel().getValueAt(row,i )+"\n");   
//         }
      } else {

      }
   }

   @Override
   public void mousePressed(MouseEvent e) {

   }

   @Override
   public void mouseReleased(MouseEvent e) {

   }

   @Override
   public void mouseEntered(MouseEvent e) {
      Object ob = e.getSource();
      if (ob == movie_sel)
         movie_sel.setForeground(Color.pink);
      if (ob == member_mod)
         member_mod.setForeground(Color.pink);
      if (ob == information)
         information.setForeground(Color.pink);
      
   }

   @Override
   public void mouseExited(MouseEvent e) {
      Object ob = e.getSource();
      if (ob == movie_sel)
         movie_sel.setForeground(Color.BLACK);
      if (ob == member_mod)
         member_mod.setForeground(Color.BLACK);
      if (ob == information)
         information.setForeground(Color.BLACK);
   }

   @Override
   public void valueChanged(ListSelectionEvent e) {
      new SeatSelection("좌석선택", 800, 520, this);
   }

   public String getTi() {
      return ti;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }
   
   
}