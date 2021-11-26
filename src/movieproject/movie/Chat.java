package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

import movieproject.controller.Controller;
import movieproject.util.BtnStyle;
import movieproject.util.Style;

public class Chat extends JFrame implements ActionListener, KeyListener{
	private String userName;
	private Controller controller;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JPanel main_panel;
	private BtnStyle btnStyle = new BtnStyle();
	private JPanel pChat;
	private DefaultStyledDocument document;
	private JTextPane jtpChat;
	private JScrollPane scChat;
	private String ImagePath = "icon/";
	private JPanel pWrite;
	private JPanel pWriteMsg;
	private JTextArea taWriteMsg;
	private JPanel pEmoji;
	private JButton btnSend;
	private JPanel pSend;
	private JButton btnEmoji;
	private JButton btnFile;
	private JScrollPane scWriteMsg;
	private JPanel name_panel;
	private JComboBox<String> movie_name;
	private JPanel file_imog_panel;
	
	public Chat() {
		controller = Controller.getInstance();
		
		controller.userId = "a";
		controller.show_movie_name = "귀멸의 칼날";
		
		userName = controller.getUserId(); // 로그인 한 이름 저장
		setTitle("XD Talk");
		setSize(600, 590);
		setLocationRelativeTo(this);
		
		main_panel = new JPanel();
		main_panel.setLayout(null);
		main_panel.setBackground(Color.white);
		
		addTitle();
		ShowChat();
		showPWrite();
		moviename();
		show_bottom();
		
		pChat.setBounds(0, 50, 588, 380);
		movie_name.setBounds(0, 427, 200, 30);
		pWrite.setBounds(0, 457, 588, 65);
		file_imog_panel.setBounds(0, 522, 588, 30);
		
		add(main_panel);
		setVisible(true); 
	}
	
	private void show_bottom() {
		file_imog_panel = new JPanel(null);
		file_imog_panel.setBackground(Color.pink);
		
		ImageIcon folderIcon = btnStyle.BtnImage(ImagePath + "folder", 23, 23);	// 이미지 설정
		btnFile = new JButton(folderIcon);
		addBtnStyle(btnFile);
		
		ImageIcon emojiIcon = btnStyle.BtnImage(ImagePath + "in-love", 23, 23);	// 이미지 설정
		btnEmoji = new JButton(emojiIcon);
		addBtnStyle(btnEmoji);
		
		
		btnFile.setBounds(10, 5, 20, 20);
		btnEmoji.setBounds(45, 5, 20, 20);
		
		main_panel.add(file_imog_panel);
		file_imog_panel.add(btnFile);
		file_imog_panel.add(btnEmoji);
	}

	private void showPWrite() {
		pWrite = new JPanel(null);
		pWrite.setBackground(Color.cyan);
		main_panel.add(pWrite);
		
		taWriteMsg = new JTextArea(3, 50); // rows*cols 여러줄의 입력창
		taWriteMsg.setLineWrap(true); // 자동 줄바꿈
		taWriteMsg.addKeyListener(this);
		scWriteMsg = new JScrollPane(taWriteMsg); // 스크롤 팬
		scWriteMsg.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); // 수직 스크롤 항상
		scWriteMsg.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 수평 스크롤 없애기
		scWriteMsg.setBorder(BorderFactory.createLineBorder(Color.white));
		
		ImageIcon sendIcon = btnStyle.BtnImage(ImagePath + "sendMessage", 20, 20);	//이미지 설정
		btnSend = new JButton(sendIcon);
		addBtnStyle(btnSend);
		btnSend.setBackground(new Color(0xFFEAEA));
		
		scWriteMsg.setBounds(0, 0, 500, 65);
		btnSend.setBounds(490, 0, 100, 65);
		
		pWrite.add(scWriteMsg);
		pWrite.add(btnSend);
	}


	private void moviename() {
//		name_panel = new JPanel();
//		name_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		name_panel.setBackground(Color.black);
//		name_panel.setBounds(0, 440, 588, 30);
		Vector<String> list = new Vector<String>();
		list.add("기본");
		list.add(controller.show_movie_name);
		
		movie_name = new JComboBox<String>(list);
		movie_name.setBackground(Color.white);
		movie_name.setFocusable(false);
		
		main_panel.add(movie_name);
	}

	/**
	 * 버튼 스타일 지정
	 * @param btn
	 */
	private void addBtnStyle(JButton btn) {
		btn.setContentAreaFilled(false);	//버튼 영역 배경 표시 설정
		btn.setBorderPainted(false);	// 버튼 테두리 설정
		btn.addActionListener(this);	//액션 리스너
	}
	private void ShowChat() {
		pChat = new JPanel();
		pChat.setLayout(new BorderLayout());
		main_panel.add(pChat);
		
		StyleContext context = new StyleContext(); // 편집 가능/편집 불가능 텍스트 컴퍼넌트를 처리하는 클래스와 인터페이스를 제공
		document = new DefaultStyledDocument(context); // 서식 있는 텍스트 형식과 유사한 방식으로 문자 및 단락 스타일로 마크업할 수 있는 문서
		jtpChat = new JTextPane(document);
		jtpChat.setBackground(new Color(0xE7FEF9)); // 배경 색
		jtpChat.setEditable(false); // 입력 불가능
		scChat = new JScrollPane(jtpChat); // 스크롤 팬
		scChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 수직 항상
		scChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 수평 없애기
		pChat.add(scChat, BorderLayout.CENTER);
	}
	private void addTitle() {

		pTitle = new JPanel();
		pTitle.setBounds(0, 0, 600, 50);
		pTitle.setLayout(new BorderLayout());
		pTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		pTitle.setBackground(Color.pink);
		
		lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 25));
		lblTitle.setForeground(Color.white);
		pTitle.add(lblTitle, BorderLayout.WEST);
		
		
		btnBack = new JButton("돌아가기");
		Style.btnFont(btnBack, Font.PLAIN, 15);
		btnBack.setForeground(Color.white);
		btnBack.setBackground(Color.pink);
		btnBack.setBorderPainted(false);
//		btnBack.addActionListener(this);
		
		pTitle.add(btnBack, BorderLayout.EAST);
		main_panel.add(pTitle);
	}
	public static void main(String[] args) {
		new Chat();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
