package movieproject.payment;

import javax.swing.JFrame;

public class ReservationInfo extends JFrame {

	public ReservationInfo(){
		
		setTitle("INHA CINEMA");
		setSize(900, 600);
		setLocationRelativeTo(this); //모니터 가운데 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창에서 닫기 버튼 누르면 콘솔 종료
		
		
		
		
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new ReservationInfo();
	}

}
