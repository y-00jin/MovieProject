package movieproject.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import movieproject.DBconnect;
import movieproject.movie.MovieCheak;
import movieproject.movie.SeatSelection;

public class Reservation extends JFrame implements ActionListener {
   
   private JPanel panel1, panel2, panel3, panel4, panel5;
   private JLabel lblcheck;
   private JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, movieName, memInfo, movietime, movieSeat, moviePay, movieGrade;
   private JButton btn1;
   Payment main;
   MovieCheak ck;
   private JLabel lblB;
   private JComponent lblC;
   private Label movieB;
   private Label movieC;
   public Reservation(String title, int width, int height, MovieCheak ck) {
      this.ck = ck;
      setTitle(title);
      setSize(width, height);
//      setLocation(800, 300);
      setLocationRelativeTo(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
      setLayout(new BorderLayout());
   
         //로고
         panel1 = new JPanel();
         panel1.setBackground(Color.pink);
         lblcheck = new JLabel("예매 확인");
         lblcheck.setFont(new Font("맑은 고딕", Font.BOLD, 20));
         
         panel1.add(lblcheck);
         
         //예매 정보
         
         panel2 = new JPanel();
         panel2.setLayout(new GridLayout(7, 1, 10, 10));
         panel2.setBorder(new EmptyBorder(20, 20, 20, 20));
         
         lbl1 = new JLabel("상영 정보");
         lbl2 = new JLabel("회원 정보");
         lbl3 = new JLabel("날짜 정보");
         lbl4 = new JLabel("A구역좌석 정보");
         lblB = new JLabel("B구역좌석 정보");
         lblC = new JLabel("C구역좌석 정보");
         lbl5 = new JLabel("결제 금액");
         
         lbl1.setForeground(Color.gray);
         lbl1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
         
         lbl2.setForeground(Color.gray);
         lbl2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
         
         lbl3.setForeground(Color.gray);
         lbl3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
         
         lbl4.setForeground(Color.gray);
         lbl4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
         
         lbl5.setForeground(Color.gray);
         lbl5.setFont(new Font("맑은 고딕", Font.BOLD, 12));
         
         lblB.setForeground(Color.gray);
         lblB.setFont(new Font("맑은 고딕", Font.BOLD, 12));
         
         lblC.setForeground(Color.gray);
         lblC.setFont(new Font("맑은 고딕", Font.BOLD, 12));

         panel2.add(lbl1);
         panel2.add(lbl2);
         panel2.add(lbl3);
         panel2.add(lbl4);
         panel2.add(lblB);
         panel2.add(lblC);
         panel2.add(lbl5);
         
         // 가운데
         panel3 = new JPanel();
         panel3.setLayout(new GridLayout(7, 1, 10, 10));
         panel3.setBorder(new EmptyBorder(20, 20, 20, 20));
         
         
         
         movieName = new JLabel(ck.getN());
         memInfo = new JLabel(ck.getMem());
         movietime = new JLabel(ck.getD() + "  " + ck.getT());
         String A = "";
         String B = "";
         String C = "";
         String sql ="SELECT A, B, C FROM SEAT s WHERE "
               + "ID = '" + ck.getMem() + "' AND "
               + "MOVIE_NAME = '" + ck.getN() + "' "
               + "AND DATES = '" + ck.getD() + "' "
               + "AND MOVIE_TIME = '" + ck.getT() + "'";
         ResultSet re = DBconnect.getResultSet(sql);
         Boolean ck_A = false;
         Boolean ck_B = false;
         Boolean ck_C = false;
         System.out.println(sql);
         try {
            while (re.next()) {
               if(re.getString(1) != null) { 
                  A += re.getString(1);
                  System.out.println("A");
                  ck_A = true;
               }
               if(re.getString(2) != null) {
                  B += re.getString(2);
                  System.out.println("B");
                  ck_B = true;
               }
               if(re.getString(3) != null) { 
                  C += re.getString(3);
                  System.out.println("C");
                  ck_C = true;
               }
            }
         } catch (SQLException e3) {
            e3.printStackTrace();
         }
         String [] s_A = A.split(",");
         if(ck_A) {
            for(int i = 0; i < s_A.length; i++) {
               for(int j = 0; j < s_A.length; j++) {
                  if(Integer.parseInt(s_A[i]) < Integer.parseInt(s_A[j])) {
                     String tmp = s_A[j];
                     s_A[j] = s_A[i];
                     s_A[i] = tmp;
                  }
               }
            }
         }
         A = "";
         if(ck_A) {
            for(int i = 0; i < s_A.length; i++) {
               A += s_A[i] + " ";
            }
         }
         String [] s_B = B.split(",");
         if(ck_B) {
            for(int i = 0; i < s_B.length; i++) {
               System.out.println(s_B[i]);
            }
            for(int i = 0; i < s_B.length; i++) {
               for(int j = 0; j < s_B.length; j++) {
                  if(Integer.parseInt(s_B[i]) < Integer.parseInt(s_B[j])) {
                     String tmp = s_B[j];
                     s_B[j] = s_B[i];
                     s_B[i] = tmp;
                  }
               }
            }
         }
         
         B = "";
         if(ck_B) {
            for(int i = 0; i < s_B.length; i++) {
               B += s_B[i] + " ";
            }   
         }
         String [] s_C = C.split(",");
         if(ck_C) {
            for(int i = 0; i < s_C.length; i++) {
               for(int j = 0; j < s_C.length; j++) {
                  if(Integer.parseInt(s_C[i]) < Integer.parseInt(s_C[j])) {
                     String tmp = s_C[j];
                     s_C[j] = s_C[i];
                     s_C[i] = tmp;
                  }
               }
            }
         }
         C = "";
         if(ck_C) {
            for(int i = 0; i < s_C.length; i++) {
               C += s_C[i] + " ";
            }
         }
         
         //13,14,3,5,

         movieSeat = new JLabel(A);
         movieB = new Label(B);
         movieC = new Label(C);
         
         sql = "SELECT * FROM RESERVATION r WHERE "
               + "ID = '" + ck.getMem() + "' AND "
                     + "MOVIE_NAME = '" + ck.getN() + "' AND "
                           + "DATES = '" + ck.getD() + "  " + ck.getT() + "'";
         re = DBconnect.getResultSet(sql);
         System.out.println(sql);
         int sum = 0;
          try {
            while (re.next()) {
               if(re.getString(1) != null) {
                  String [] n = re.getString(1).split("원");
                  sum += Integer.parseInt(n[0]); 
               }
            }
         } catch (SQLException e3) {
            e3.printStackTrace();
         }
         
         moviePay = new JLabel(Integer.toString(sum) + "원");
         
//         String sql = "SELECT RES_PAY, ID, MOVIE_NAME, \"TIME\", DATES\r\n"
//               + "FROM AMOVIE.RESERVATION";
//          ResultSet re = DBconnect.getResultSet(sql);
//          try {
//            while (re.next()) {
//               movieName.setText(re.getString("MOVIE_NAME"));
//               memInfo.setText(re.getString("ID"));
//               movietime.setText(re.getString("TIME")+"  "+re.getString("DATES"));
//            }
//         } catch (SQLException e) {
//            e.printStackTrace();
//         }
         
         panel3.add(movieName);
         panel3.add(memInfo);
         panel3.add(movietime);
         panel3.add(movieSeat);
         panel3.add(movieB);
         panel3.add(movieC);
         panel3.add(moviePay);
         
         //오른쪽 공백
         panel4 = new JPanel();
         panel4.setLayout(new GridLayout(6, 1, 10, 10));
         panel4.setBorder(new EmptyBorder(20, 20, 20, 20));
         
         
         //버튼
         panel5 = new JPanel();
         btn1 = new JButton("예매확인 완료");
         panel5.add(btn1);
         Color a = new Color(255, 228, 225);
         btn1.setBackground(a);
         btn1.addActionListener(this);
         
         
         add(panel1, BorderLayout.NORTH);
         add(panel2, BorderLayout.WEST);
         add(panel3, BorderLayout.CENTER);
         add(panel4, BorderLayout.EAST);
         add(panel5, BorderLayout.SOUTH);
      
      setVisible(true); // 이게 없으면 창이 뜨지 않음 
   }

   public static void main(String[] args) {
      DBconnect.DB();
      //new Reservation("예매 확인", 400, 400, this); // title, width, height
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if(obj==btn1) {
         this.dispose();
      }
   }

}