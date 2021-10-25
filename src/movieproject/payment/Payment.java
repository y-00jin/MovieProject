package movieproject.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.print.attribute.AttributeSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import movieproject.DBconnect;
import movieproject.movie.SeatSelection;

public class Payment extends JFrame implements ActionListener {

   JComboBox<String> cbbox;
   JLabel pay;
   JLabel discountPay;
   JComboBox<String> monCombo;
   JComboBox<String> yearCombo;
   int grade;
   JButton jbOk;
   JButton jbCancle;
   JPasswordField jpwf;
   public static final int moivePay = 8000;
   JTextField tf1, tf2, tf3, tf4;
   String sMonth, sYear, jpw;
   SeatSelection main;
   private Vector<String> info = new Vector<String>();
   private String last;
   int people = 0;
   String sPay = "", sMemInfo = "", sTitle = "", sDate = "", sSeat = "";

   public String getsSeat() {
      return sSeat;
   }

   public String getsPay() {
      return sPay;
   }

   public String getsMemInfo() {
      return sMemInfo;
   }

   public String getsTitle() {
      return sTitle;
   }

   public String getsDate() {
      return sDate;
   }

   public Payment(String title, int width, int height, SeatSelection main) {
      this.main = main;
      setTitle(title);
      setSize(width, height);
      // setLocation(400, 200);
      setLocationRelativeTo(this);
      // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      setLayout(new BorderLayout());

      northPan();
      eastPan();
      centerPan();
      westPan();
      southPan();

      setVisible(true);
   }

   private void westPan() {
      JPanel wp = new JPanel();
      wp.setLayout(new GridLayout(10, 1, 10, 10));
      wp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

      JLabel wplb01 = new JLabel("영화 제목 : ");
      wplb01.setPreferredSize(new Dimension(120, 40));
      wplb01.setFont(wplb01.getFont().deriveFont(15.0f));
      wp.add(wplb01);

      JLabel wplb0 = new JLabel("날짜 정보 : ");
      wplb0.setPreferredSize(new Dimension(120, 40));
      wplb0.setFont(wplb0.getFont().deriveFont(15.0f));
      wp.add(wplb0);

      JLabel wplb = new JLabel("회원 정보 : ");
      wplb.setPreferredSize(new Dimension(120, 40));
      wplb.setFont(wplb.getFont().deriveFont(15.0f));
      wp.add(wplb);

      JLabel wblb7 = new JLabel("좌석 정보 : ");
      wblb7.setPreferredSize(new Dimension(120, 40));
      wblb7.setFont(wblb7.getFont().deriveFont(15.0f));
      wp.add(wblb7);

      JLabel wplb1 = new JLabel("결제 금액 :");
      wplb1.setPreferredSize(new Dimension(120, 40));
      wplb1.setFont(wplb1.getFont().deriveFont(15.0f));
      wp.add(wplb1);

      JLabel wplb2 = new JLabel("할인 제도 :");
      wplb2.setPreferredSize(new Dimension(120, 40));
      wplb2.setFont(wplb2.getFont().deriveFont(15.0f));
      wp.add(wplb2);

      JLabel wplb3 = new JLabel("최종 금액 :");
      wplb3.setPreferredSize(new Dimension(120, 40));
      wplb3.setFont(wplb3.getFont().deriveFont(15.0f));
      wp.add(wplb3);

      JLabel wplb4 = new JLabel("카드 번호 :");
      wplb4.setPreferredSize(new Dimension(120, 40));
      wplb4.setFont(wplb4.getFont().deriveFont(15.0f));
      wp.add(wplb4);

      JLabel wplb6 = new JLabel("유효기간  :");
      wplb6.setPreferredSize(new Dimension(50, 40));
      wplb6.setFont(wplb6.getFont().deriveFont(15.0f));
      wp.add(wplb6);

      JLabel wplb5 = new JLabel("비밀 번호 : ");
      wplb5.setPreferredSize(new Dimension(120, 40));
      wplb5.setFont(wplb5.getFont().deriveFont(15.0f));
      wp.add(wplb5);

      add(wp, BorderLayout.WEST);
   }

   private void eastPan() {

   }

   private void centerPan() {

//     String sqlString = "SELECT DISTINCT m.ID , mv.MOVIE_NAME, t.DATES, t.\"TIME\", s.A, s.B, s.C FROM \"MEMBER\" m, MOVIE mv, SEAT s , \"TIME\" t \r\n"
//           + "WHERE m.ID = s.ID AND "
//           + "mv.MOVIE_NAME = s.MOVIE_NAME AND "
//           + "mv.MOVIE_NAME = t.MOVIE_NAME AND "
//           + "t.\"TIME\" = s.MOVIE_TIME AND "
//           + "s.MOVIE_TIME = '" + main.getInfo().get(0) + "' AND " 
//           + "mv.MOVIE_NAME = '" + main.getInfo().get(1) + "'";
//     System.out.println(sqlString);
//     ResultSet re = DBconnect.getResultSet(sqlString);
//     try {
//      while(re.next()) {
//         System.out.println(re.getString(1) + " " + re.getString(2) + " " + re.getString(3) + " " + re.getString(4) + " " + re.getString(5) + " " + re.getString(6) + " " + re.getString(7) + " ");
//      }
//   } catch (SQLException e) {
//      e.printStackTrace();
//   }

      JPanel cp = new JPanel();
      cp.setLayout(new GridLayout(10, 1, 10, 10));
      cp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

      JLabel title = new JLabel(main.getInfo().get(1));
      title.setPreferredSize(new Dimension(120, 40));
      title.setFont(title.getFont().deriveFont(12.0f));
      cp.add(title);
      sTitle = title.getText();

      sDate = main.getInfo().get(2) + "  " + main.getInfo().get(3);
      JLabel resInfo = new JLabel(sDate);

      resInfo.setPreferredSize(new Dimension(120, 40));
      resInfo.setFont(resInfo.getFont().deriveFont(12.0f));
      cp.add(resInfo);

      JLabel memInfo = new JLabel(main.getInfo().get(0));
      memInfo.setPreferredSize(new Dimension(120, 40));
      memInfo.setFont(memInfo.getFont().deriveFont(12.0f));
      cp.add(memInfo);
      sMemInfo = memInfo.getText();

      String aZone = "A구역 : ";
      String bZone = "B구역 : ";
      String cZone = "C구역 : ";
      int aPeople = 0, bPeople = 0, cPeople = 0;
      String tmp[] = main.getInfo().get(4).toString().split(",");
      String tmp1[] = main.getInfo().get(5).toString().split(",");
      String tmp2[] = main.getInfo().get(6).toString().split(",");
      
      for (int i = 0; i < tmp.length; i++) {
         aZone += tmp[i] + " ";
         if (tmp.length > 0) {
            people++;
            aPeople++;
         }
      }
      for (int i = 0; i < tmp1.length; i++) {
         bZone += tmp1[i] + " ";
         if (tmp1.length > 0) {
            people++;
            bPeople++;
         }
      }
      for (int i = 0; i < tmp2.length; i++) {
         cZone += tmp2[i] + " ";
         if (tmp2.length > 0) {
            people++;
            cPeople++;
         }
      }
      last = "";
//      System.out.println(aZone);
//      System.out.println(bZone);
//      System.out.println(cZone);
//      System.out.println("A존"+aPeople + "B존"+bPeople+"C존"+cPeople);
      
      if (aPeople > 0) {
         last += aZone;
      }
      if (bPeople > 0) {
         last += bZone;
      }
      if (cPeople > 0) {
         last += cZone;
      }

      JLabel seat = new JLabel(last);
      seat.setPreferredSize(new Dimension(120, 40));
      seat.setFont(seat.getFont().deriveFont(12.0f));
      sSeat = seat.getText();
      cp.add(seat);

      pay = new JLabel((moivePay * people) + "");
      pay.setPreferredSize(new Dimension(120, 40));
      pay.setFont(pay.getFont().deriveFont(12.0f));
      cp.add(pay);

      String[] discount = { "할인없음", "평일할인(20%)", "조조할인(30%)", "쿠폰할인(40%)" };
      cbbox = new JComboBox<String>(discount);
      cbbox.setFont(cbbox.getFont().deriveFont(12.0f));
      cbbox.setPreferredSize(new Dimension(160, 30));
      cbbox.addActionListener(this);
      cp.add(cbbox);

      discountPay = new JLabel((moivePay * people) + "");
      discountPay.setPreferredSize(new Dimension(150, 40));
      discountPay.setFont(discountPay.getFont().deriveFont(12.0f));
      cp.add(discountPay);

//      JLabel cplb2 = new JLabel("카드 번호 : ");
//      cplb2.setPreferredSize(new Dimension(80, 40));
//      cplb2.setFont(cplb2.getFont().deriveFont(15.0f));
//      cp.add(cplb2);

      JPanel cardPan = new JPanel();
      cardPan.setLayout(new GridLayout(1, 4, 5, 5));
      cardPan.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
      tf1 = new JTextField(3);
      tf1.setDocument(new JTextFieldLimit(4));
      tf2 = new JPasswordField(3);
      tf2.setDocument(new JTextFieldLimit(4));
      tf3 = new JPasswordField(3);
      tf3.setDocument(new JTextFieldLimit(4));
      tf4 = new JTextField(3);
      tf4.setDocument(new JTextFieldLimit(4));

      cardPan.add(tf1);
      cardPan.add(tf2);
      cardPan.add(tf3);
      cardPan.add(tf4);
      cp.add(cardPan);

      JPanel timePan = new JPanel();
      timePan.setLayout(new FlowLayout(FlowLayout.LEFT));
      String[] month = { "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" };
      monCombo = new JComboBox<String>(month);
      monCombo.setPreferredSize(new Dimension(70, 20));
      monCombo.setFont(monCombo.getFont().deriveFont(12.0f));
      monCombo.addActionListener(this);
      timePan.add(monCombo);

      String[] year = { "2022", "2023", "2024", "2025", "2026" };
      yearCombo = new JComboBox<String>(year);
      yearCombo.setPreferredSize(new Dimension(70, 20));
      yearCombo.setFont(yearCombo.getFont().deriveFont(12.0f));
      yearCombo.addActionListener(this);
      timePan.add(yearCombo);
      cp.add(timePan);

      JPanel pwPan = new JPanel();
      pwPan.setLayout(new FlowLayout(FlowLayout.LEFT));
//      pwPan.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
      jpwf = new JPasswordField(16);
//      jpw.setPreferredSize(new Dimension(70,20));
      pwPan.add(jpwf);
      cp.add(pwPan);

      add(cp);
   }

   private void northPan() {
      JPanel np = new JPanel();
//      np.setLayout(new BorderLayout());

      JLabel nplb = new JLabel("결제창입니다.");
      np.setBackground(Color.pink);
      np.setSize(new Dimension(70, 70));
      nplb.setForeground(Color.black);

      nplb.setFont(new Font("맑은 고딕", Font.BOLD, 20));
      ;
//      nplb.setSize(new Dimension(80,40));
//      nplb.setFont(nplb.getFont().deriveFont(20.0f));//폰트 사이즈
      nplb.setHorizontalAlignment(JLabel.CENTER);
      np.setPreferredSize(new Dimension(40, 70));
      np.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      np.add(nplb);

      add(np, BorderLayout.NORTH);
   }

   private void southPan() {
      JPanel sp = new JPanel();
      jbOk = new JButton("완료");
      jbOk.addActionListener(this);
      sp.add(jbOk);

      jbCancle = new JButton("취소");
      jbCancle.addActionListener(this);
      sp.add(jbCancle);

      add(sp, BorderLayout.SOUTH);

   }

   public static void main(String[] args) {
      // DBconnect.DB();

//      new Payment("결제 ", 500, 600, null);

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if (obj == cbbox) {
         grade = cbbox.getSelectedIndex();
         double dPay = Double.parseDouble(pay.getText());
         
         switch (grade) {

         case 0:
            dPay = dPay;
            break;
         case 1:
            dPay = dPay * 0.8;
            break;

         case 2:
            dPay = dPay * 0.7;
            break;

         case 3:
            dPay = dPay * 0.6;
            break;

         default:
            throw new IllegalArgumentException("Unexpected value: " + grade);
         }
         discountPay.setText((int) dPay + "원");

         sPay = discountPay.getText();

      } else if (obj == monCombo) {
         int iMonth = monCombo.getSelectedIndex();

         switch (iMonth) {
         case 0:
            sMonth = "1월";
            break;
         case 1:
            sMonth = "2월";
            break;
         case 2:
            sMonth = "3월";
            break;
         case 3:
            sMonth = "4월";
            break;
         case 4:
            sMonth = "5월";
            break;
         case 5:
            sMonth = "6월";
            break;
         case 6:
            sMonth = "7월";
            break;
         case 7:
            sMonth = "8월";
            break;
         case 8:
            sMonth = "9월";
            break;
         case 9:
            sMonth = "10월";
            break;
         case 10:
            sMonth = "11월";
            break;
         case 11:
            sMonth = "12월";
            break;
         default:
            throw new IllegalArgumentException("Unexpected value: " + iMonth);
         }
      } else if (obj == yearCombo) {
         int iYear = monCombo.getSelectedIndex();

         switch (iYear) {
         case 0:
            sYear = "2022년";
            break;
         case 1:
            sYear = "2023년";
            break;
         case 2:
            sYear = "2024년";
            break;
         case 3:
            sYear = "2025년";
            break;
         case 4:
            sYear = "2026년";
            break;

         default:
            throw new IllegalArgumentException("Unexpected value: " + iYear);
         }
      } else if (obj == jbOk) {

         String cardNum = tf1.getText() + tf2.getText() + tf3.getText() + tf4.getText();
         jpw = jpwf.getText();
         if(sPay=="") {
            sPay = pay.getText()+"원";
         }
         String insertSQL = "INSERT INTO AMOVIE.RESERVATION\r\n"
               + "(RES_PAY, ID, MOVIE_NAME, DATES, SEAT)\r\n"
               + "VALUES('" + sPay + "', '" + sMemInfo + "', '" + sTitle + "', '" + sDate + "','"+sSeat+"')";
               
               
//               "INSERT INTO AMOVIE.RESERVATION\r\n" + "(RES_PAY, ID, MOVIE_NAME, DATES)\r\n"
//               + "VALUES('" + sPay + "', '" + sMemInfo + "', '" + sTitle + "', '" + sDate + "')";
//         System.out.println(insertSQL);

         DBconnect.getupdate(insertSQL);

         String sql = "INSERT INTO AMOVIE.SEAT " + "(MOVIE_NAME, MOVIE_TIME, A, B, C, ID, DATES) " + "VALUES('"
               + main.getInfo().get(1).toString() + "', '" + main.getInfo().get(3).toString() + "', '"
               + main.getInfo().get(4).toString() + "', '" + main.getInfo().get(5).toString() + "', '"
               + main.getInfo().get(6).toString() + "', '" + main.getInfo().get(0) + "', '"
               + main.getInfo().get(2).toString() + "')";
//         System.out.println(sql);
         DBconnect.getupdate(sql);

         info.add(main.getInfo().get(0));
         info.add(main.getInfo().get(1));
         info.add(main.getInfo().get(2));
         info.add(main.getInfo().get(3));
         info.add(last);
         this.dispose();
         new Receipt("영수증", 400, 450, this); 
         
         //new Reservation("예매 확인", 400, 400, this); // title, width, height
      } else if (obj == jbCancle) {
         System.exit(0);
      }

   }

   public Vector<String> getInfo() {
      return info;
   }

}