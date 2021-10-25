package movieproject.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;


import movieproject.DBconnect;
import movieproject.movie.MovieAPI;


public class RemoveMovie extends JFrame implements ActionListener {
   private JButton ok;
   MovieAPI api = new MovieAPI();
   JComboBox<String> name;
   Vector<String>movieName= new Vector<String>(10);
   
   
   public RemoveMovie(String Title, int width, int height) {
      this.setTitle(Title);
      setSize(width, height);
      //setLocation(475, 300);
      setLocationRelativeTo(this);
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JPanel main = new JPanel();
      main.setBackground(Color.WHITE);
//      main.setLayout(new GridLayout(1,1));
      setLayout(new BorderLayout());
      
      JLabel la2 = new JLabel("영화선택 : ");
      la2.setHorizontalAlignment(JLabel.CENTER);
      main.add(la2);
      
      JPanel namePan = new JPanel();
      namePan.setBackground(Color.white);
      namePan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      namePan.setLayout(new FlowLayout(FlowLayout.LEFT));
      name = new JComboBox<String>();
      name.setPreferredSize(new Dimension(200,40));
      name.addActionListener(this);
      namePan.add(name);
      main.add(namePan);
      
      String sql = "SELECT * FROM MOVIE";
      ResultSet re = DBconnect.getResultSet(sql);
      try {
          while(re.next()) {
             name.addItem(re.getString(1));
          }
       } catch (SQLException e) {
          e.printStackTrace();
       }
//      System.out.println(movieName);
      
      
      JPanel down = new JPanel();
      down.setBackground(Color.WHITE);
      ok = new JButton("  삭제  ");
      ok.addActionListener(this);
      
      
      class RoundedBorder implements Border {
         int radius;

         RoundedBorder(int radius) {
            this.radius = radius;
         }

         public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius);
         }

         public boolean isBorderOpaque() {
            return true;
         }

         public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
         }
      }
      ok.setBorder(new RoundedBorder(5));
      Font btnFont = new Font("맑은 고딕", Font.PLAIN, 12);
      ok.setFont(btnFont);
      ok.setBackground(Color.WHITE);
      ok.setForeground(new Color(53, 121, 247));
      down.add(ok);
      
      
      add(down,BorderLayout.SOUTH);
      add(main,BorderLayout.CENTER);
      
      setVisible(true);
   }
   
   public static void main(String[] args) {
	   DBconnect.DB();
      new RemoveMovie("영화 삭제",420, 150);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
	   Object obj = e.getSource();
	  
	   String mName = name.getSelectedItem().toString();
		if(obj == ok) {
			String sql = "DELETE FROM SEAT WHERE MOVIE_NAME='"+mName+"'";
			String sql2 = "DELETE FROM RESERVATION WHERE MOVIE_NAME='"+mName+"'";
			String sql3 = "DELETE FROM URL WHERE MOVIE_NAME='"+mName+"'";
			String sql4 = "DELETE FROM MOVIE WHERE MOVIE_NAME='"+mName+"'";
					System.out.println(sql);
			DBconnect.getupdate(sql);	
			DBconnect.getupdate(sql2);	
			DBconnect.getupdate(sql3);	
			DBconnect.getupdate(sql4);	
			dispose();
			
		}
	}

}