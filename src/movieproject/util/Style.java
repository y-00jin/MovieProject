package movieproject.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Style {

	public void btnStyle() {
		
	}
	
	public static void lblFont(JLabel lbl, int style, int size) {
		lbl.setFont(new Font("1훈새마을운동 R", style, size));
		
	}
	public static void tfFont(JTextField tf, int style, int size) {
		tf.setFont(new Font("1훈새마을운동 R", style, size));
		
	}
	public static void btnFont(JButton btn, int style, int size) {
		btn.setFont(new Font("1훈새마을운동 R", style, size));
	}
	public static void btnFontStyle(JButton btn, int style, int size, int color) {
		btn.setFont(new Font("1훈새마을운동 R", style, size));
		btn.setBackground(new Color(color));
	}
	public static void BtnDateStyle(JButton btn) {
    	Font fontBtn = new Font("HY헤드라인M", Font.PLAIN, 15);
    	btn.setFont(fontBtn); // 폰트 스타일 적용
    	btn.setForeground(Color.white); // 글자색
    	btn.setBackground(new Color(18, 52, 120));
    	//btn.setBackground(new Color(0x123478));
    	btn.setBorderPainted(false); // 테두리 없애기
    }

//	public static void BtnStyle(JButton btnFont){
//        Font fontBtn = new Font("HY헤드라인M", Font.PLAIN, 15);
//        btnFont.setFont(fontBtn); // 폰트 스타일 적용
//        btnFont.setForeground(new Color(0x5D5D5D)); // 글자색
//        btnFont.setBackground(new Color(0xD9E5FF));
//        //btnFont.setBorderPainted(false); // 테두리 없애기
//     }
}
