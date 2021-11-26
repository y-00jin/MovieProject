package movieproject.util;

import java.awt.Image;

import javax.swing.ImageIcon;

public class BtnStyle {

	public ImageIcon BtnImage(String imagePath, int width, int height) {

		ImageIcon ImgIcon = new ImageIcon("resource/" + imagePath + ".png");
		Image changeIcon = ImgIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon returnIcon = new ImageIcon(changeIcon);

		return returnIcon;

	}
	
}
