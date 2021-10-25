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


public class AddmoiveTime extends JFrame implements ActionListener {
   private JButton ok;
   MovieAPI api = new MovieAPI();
   JComboBox<String> name;
   JComboBox<String> hour;
   JComboBox<String> Minute, date;
   
   
   public AddmoiveTime(String Title, int width, int height) {
      this.setTitle(Title);
      setSize(width, height);
      //setLocation(475, 300);
      setLocationRelativeTo(this);
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      JPanel main = new JPanel();
      main.setBackground(Color.WHITE);
      main.setLayout(new GridLayout(3,2));
      setLayout(new BorderLayout());
      
      JLabel lbdate = new JLabel("날짜입력");
      lbdate.setHorizontalAlignment(JLabel.CENTER);
      main.add(lbdate);
      
      //날짜 출력 함수
      JPanel datePan = new JPanel();
      datePan.setBackground(Color.white);
      datePan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      datePan.setLayout(new FlowLayout(FlowLayout.LEFT));
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      Calendar cal = Calendar.getInstance();
      String[] sDate = new String[7];
      for(int i = 0; i <= 6; i++) {
    	sDate[i]=format.format(cal.getTime());
      	cal.add(Calendar.DAY_OF_MONTH, +1);
      }
      
      date = new JComboBox<String>(sDate);
      datePan.add(date);
      date.addActionListener(this);
      main.add(datePan);
      
      JLabel la = new JLabel("시간입력");
      la.setHorizontalAlignment(JLabel.CENTER);
      main.add(la);
      
      JPanel timePan = new JPanel();
      timePan.setLayout(new FlowLayout(FlowLayout.LEFT));
      timePan.setBackground(Color.white);
      timePan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      
     
      
      String[]sHour = {"9","10","11","12","13","14","15","16","17","18","19","20","21","22"};
      hour = new JComboBox<String>(sHour);
      timePan.add(hour,BorderLayout.WEST);
      hour.addActionListener(this);
      
      String[]sMinute = {"00","15","30","45"};
      Minute = new JComboBox<String>(sMinute);
      timePan.add(Minute,BorderLayout.CENTER);
      Minute.addActionListener(this);
      main.add(timePan);
  
      JLabel la2 = new JLabel("영화선택");
      la2.setHorizontalAlignment(JLabel.CENTER);
      main.add(la2);
      
      JPanel namePan = new JPanel();
      namePan.setBackground(Color.white);
      namePan.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
      namePan.setLayout(new FlowLayout(FlowLayout.LEFT));
      name = new JComboBox<String>();
      name.setPreferredSize(new Dimension(160,20));
      
      String sql = "SELECT * FROM MOVIE";
      ResultSet re = DBconnect.getResultSet(sql);
      name.addActionListener(this);
      namePan.add(name);
      main.add(namePan);
      
      try {
          while(re.next()) {
             name.addItem(re.getString(1));
          }
       } catch (SQLException e) {
          e.printStackTrace();
       }
      
      
      JPanel down = new JPanel();
      down.setBackground(Color.WHITE);
      ok = new JButton("  추가  ");
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
      new AddmoiveTime("기존 영화시간 추가",370, 220);
      
   }

   @Override
   public void actionPerformed(ActionEvent e) {
	   Object obj = e.getSource();
	   
	   String mHour = hour.getSelectedItem().toString();
	   String mMinute =Minute.getSelectedItem().toString();
	   String mName = name.getSelectedItem().toString();
	   String mDate = date.getSelectedItem().toString();
		if(obj == ok) {
			String sql = "INSERT INTO AMOVIE.\"TIME\""
					+ "(MOVIE_NAME, \"TIME\", \"DATES\")"
					+ "VALUES('"+mName+"','"+mHour+":"+mMinute+"','"+mDate+"')";
//					System.out.println(sql);
			DBconnect.getupdate(sql);	
			dispose();
		}
	}
}