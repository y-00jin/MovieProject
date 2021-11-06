package movieproject.util;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Style {

	public void btnStyle() {
		
	}
	
	public void lblFont(JLabel lbl, int style, int size) {
		lbl.setFont(new Font("1훈새마을운동 R", style, size));
		
	}
	public void tfFont(JTextField tf, int style, int size) {
		tf.setFont(new Font("1훈새마을운동 R", style, size));
		
	}
	public void btnFont(JButton btn, int style, int size) {
		btn.setFont(new Font("1훈새마을운동 R", style, size));
	}
	
}
