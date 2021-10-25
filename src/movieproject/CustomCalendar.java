package movieproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class CustomCalendar extends JFrame implements ActionListener, WindowListener{
	// 상단 지역
	JPanel bar = new JPanel();
		JButton lastMonth = new JButton("◀");
	
		JComboBox<Integer> yearCombo = new JComboBox<Integer>(); 
			DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
		
		JLabel yLbl = new JLabel("년 ");
		
		JComboBox<Integer> monthCombo = new JComboBox<Integer>(); 
			DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();
		JLabel mLbl = new JLabel("월");
		JButton nextMonth = new JButton("▶");
	// 중앙 지역
	JPanel center = new JPanel(new BorderLayout());
		// 중앙 상단 지역
		JPanel cntNorth = new JPanel(new GridLayout(0,7));
		// 중앙 중앙 지역
		JPanel cntCenter = new JPanel(new GridLayout(0,7));
	
	// 요일 입력
	String dw[] = {"일","월","화","수","목","금","토"};
	
	
	Calendar now = Calendar.getInstance();
	int year, month, date;
	
	public CustomCalendar() {
		year = now.get(Calendar.YEAR);// 2021년
		month = now.get(Calendar.MONTH)+1; // 0월 == 1월
		date = now.get(Calendar.DATE);
		for(int i=year; i<=year+50; i++){yearModel.addElement(i);}
		for(int i=1; i<=12; i++) { monthModel.addElement(i); }
		//////////////////////////프레임///////////////////////////////////////////
		// 상단 지역
		add("North", bar);
			bar.setLayout(new FlowLayout());
			bar.setSize(300,400);
			bar.add(lastMonth);
			//////////////////////////달력/////////////////////////////////////////////
			bar.add(yearCombo);
				yearCombo.setModel(yearModel);
				yearCombo.setSelectedItem(year);

			bar.add(yLbl);
			bar.add(monthCombo);
				monthCombo.setModel(monthModel);
				monthCombo.setSelectedItem(month);

			bar.add(mLbl);
			bar.add(nextMonth);
			bar.setBackground(new Color(0,210,180));

		// 중앙 지역
		add("Center", center);
			// 중앙 상단 지역
			center.add("North",cntNorth);
			for(int i=0; i<dw.length; i++) {
				JLabel dayOfWeek = new JLabel(dw[i],JLabel.CENTER);
				if(i==0) dayOfWeek.setForeground(Color.red);
				else if(i==6) dayOfWeek.setForeground(Color.blue);
				cntNorth.add(dayOfWeek);
			}

			// 중앙 중앙 지역
			center.add("Center",cntCenter);
			dayPrint(year,month);
			
			
		// 이벤트
		yearCombo.addActionListener(this);
		monthCombo.addActionListener(this);
		lastMonth.addActionListener(this);
		nextMonth.addActionListener(this);
		addWindowListener(this);
		
		// frame 기본 셋팅
		setSize(400,300);
		setLocationRelativeTo(this);
		setVisible(true);
		setResizable(false);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public static void main(String[] args) {
		new CustomCalendar();
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void dayPrint(int y,int m) {
		Calendar cal = Calendar.getInstance();
		cal.set(y, m-1, 1);
		int week = cal.get(Calendar.DAY_OF_WEEK); // 1일에 대한 요일
		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 1월에 대한 마지막 요일
		for(int i =1; i<week; i++) { // 1월 1일 전까지 공백을 표시해라
			cntCenter.add(new JLabel(""));
		}
		
		for(int i =0;i<=lastDate-1;i++) { // 1월 마지막 날까지 숫자를 적어라, 그리고 토요일 일요일은 색깔을 입혀라
			JLabel day = new JLabel();
			day.setHorizontalAlignment(JLabel.CENTER);
			if((week+i)%7==0) {
				cntCenter.add(day).setForeground(Color.blue);
				day.setText(1+i+"");
			} else if((week+i)%7==1) {
				cntCenter.add(day).setForeground(Color.red);
				day.setText(1+i+"");
			} else {
				cntCenter.add(day);
				day.setText(1+i+"");
			}
			day.addMouseListener(new MouseAdapter() {
				private int clickCheck;
				private AbstractButton startDateField;
				private AbstractButton arriveDateField;

				public void mouseClicked(MouseEvent me) {
					JLabel mouseClick = (JLabel)me.getSource();
					String str= mouseClick.getText();
					String y = ""+yearCombo.getSelectedItem();
					String m = ""+monthCombo.getSelectedItem();
					
					// 받은 "요일"이 1자리면 0을 붙여라
					if(str.equals("")) ;
					else if(str.length()==1) str = "0"+str; 
					
					// 받은 "월"이 1자리면 0을 붙여라
					if(m.length()==1) m = "0"+m;
					
					if(clickCheck==0) {
						startDateField.setText(y+"/"+m+"/"+str);
						startDateField.setEnabled(false);
						clickCheck++;
					} else if(clickCheck==1) {
						arriveDateField.setText(y+"/"+m+"/"+str);
						arriveDateField.setEnabled(false);
						clickCheck--;
					}
				}
			});
		}
	}
}