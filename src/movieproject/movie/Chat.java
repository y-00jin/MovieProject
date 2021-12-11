package movieproject.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import movieproject.DBconnect;
import movieproject.controller.Controller;
import movieproject.server.datacommunication.Message;
import movieproject.util.AlignEnum;
import movieproject.util.BtnStyle;
import movieproject.util.Emoji;
import movieproject.util.StyleSet;

public class Chat extends JFrame implements ActionListener, KeyListener {
	private static String userName;
	private Controller controller;
	private JPanel pTitle;
	private JLabel lblTitle;
	private JButton btnBack;
	private JPanel main_panel;
	private BtnStyle btnStyle = new BtnStyle();
	private JPanel pChat;

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
	private StyledDocument document;

	public Chat() {
		controller = Controller.getInstance();
		DBconnect.DB();
		//controller.userId = "a";
		//controller.show_movie_name = "귀멸의 칼날";

		userName = controller.getUserId(); // 로그인 한 이름 저장
		setTitle("XD Talk");
		setSize(900, 590);
		setLocationRelativeTo(this);

		main_panel = new JPanel();
		main_panel.setLayout(null);
		main_panel.setBackground(Color.white);

		addTitle();
		ShowChat();
		showPWrite();
		moviename();
		show_bottom();

		pChat.setBounds(0, 50, 888, 380);
		movie_name.setBounds(10, 513, 200, 30);
		pWrite.setBounds(0, 430, 900, 75);
		file_imog_panel.setBounds(0, 510, 588, 30);

		add(main_panel);
		setVisible(true);
	}

	private void show_bottom() {
		file_imog_panel = new JPanel(null);
		file_imog_panel.setBackground(Color.white);

		ImageIcon folderIcon = btnStyle.BtnImage(ImagePath + "folder", 23, 23); // 이미지 설정
		btnFile = new JButton(folderIcon);
		addBtnStyle(btnFile);

		ImageIcon emojiIcon = btnStyle.BtnImage(ImagePath + "in-love", 23, 23); // 이미지 설정
		btnEmoji = new JButton(emojiIcon);
		addBtnStyle(btnEmoji);

		btnFile.setBounds(225, 5, 24, 24);
		btnEmoji.setBounds(265, 5, 24, 24);

		main_panel.add(file_imog_panel);
		file_imog_panel.add(btnFile);
		file_imog_panel.add(btnEmoji);
	}

	private void showPWrite() {
		pWrite = new JPanel(null);
		pWrite.setBackground(new Color(0xFFD8D8));
		main_panel.add(pWrite);
		
		taWriteMsg = new JTextArea(3, 50); // rows*cols 여러줄의 입력창
		taWriteMsg.setLineWrap(true); // 자동 줄바꿈
		taWriteMsg.addKeyListener(this);
		scWriteMsg = new JScrollPane(taWriteMsg); // 스크롤 팬
		scWriteMsg.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); // 수직 스크롤 항상
		scWriteMsg.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 수평 스크롤 없애기
		scWriteMsg.setBorder(BorderFactory.createLineBorder(new Color(0xEAEAEA)));

		ImageIcon sendIcon = btnStyle.BtnImage(ImagePath + "sendMessage", 20, 20); // 이미지 설정
		btnSend = new JButton(sendIcon);
		addBtnStyle(btnSend);
		btnSend.setBackground(new Color(0xFFEAEA));

		scWriteMsg.setBounds(0, 0, 790, 75);
		btnSend.setBounds(800, 0, 75, 75);

		pWrite.add(scWriteMsg);
		pWrite.add(btnSend);
	}

	private void moviename() {
//		name_panel = new JPanel();
//		name_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		name_panel.setBackground(Color.black);
//		name_panel.setBounds(0, 440, 588, 30);
		
		String selectMovieName = "SELECT MOVIE_NAME FROM MOVIE_RESERVATION WHERE ID='"+ userName +"'";
		
		ResultSet re = DBconnect.getResultSet(selectMovieName);
		
		Vector<String> list = new Vector<String>();
		
		list.add(controller.show_movie_name);
		try {
			while (re.next()) {
				list.add(re.getString(1));
				
			}
		} catch (SQLException e1) {
			//e1.printStackTrace();
		}
		
		

		movie_name = new JComboBox<String>(list);
		movie_name.setBackground(Color.white);
		movie_name.setFocusable(false);
		movie_name.addActionListener(this);

		main_panel.add(movie_name);
	}

	/**
	 * 버튼 스타일 지정
	 * 
	 * @param btn
	 */
	private void addBtnStyle(JButton btn) {
		btn.setContentAreaFilled(false); // 버튼 영역 배경 표시 설정
		btn.setBorderPainted(false); // 버튼 테두리 설정
		btn.addActionListener(this); // 액션 리스너
	}

	private void ShowChat() {
		pChat = new JPanel();
		pChat.setLayout(new BorderLayout());
		main_panel.add(pChat);

		StyleContext context = new StyleContext(); // 편집 가능/편집 불가능 텍스트 컴퍼넌트를 처리하는 클래스와 인터페이스를 제공
		document = new DefaultStyledDocument(context); // 서식 있는 텍스트 형식과 유사한 방식으로 문자 및 단락 스타일로 마크업할 수 있는 문서
		jtpChat = new JTextPane(document);
		jtpChat.setBackground(Color.white); // 배경 색
		jtpChat.setEditable(false); // 입력 불가능
		scChat = new JScrollPane(jtpChat); // 스크롤 팬
		scChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 수직 항상
		scChat.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 수평 없애기
		pChat.add(scChat, BorderLayout.CENTER);
	}

	private void addTitle() {

		pTitle = new JPanel();
		pTitle.setBounds(0, 0, 900, 50);
		pTitle.setLayout(new BorderLayout());
		pTitle.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		pTitle.setBackground(Color.pink);

		lblTitle = new JLabel("INHA CINEMA");
		lblTitle.setFont(new Font("배달의민족 도현", Font.ITALIC, 18));
		lblTitle.setForeground(Color.white);
		pTitle.add(lblTitle, BorderLayout.WEST);

		btnBack = new JButton("돌아가기");
		StyleSet.btnFont(btnBack, Font.PLAIN, 15);
		btnBack.setForeground(Color.white);
		btnBack.setBackground(Color.pink);
		btnBack.setBorderPainted(false);
		btnBack.addActionListener(this);

		pTitle.add(btnBack, BorderLayout.EAST);
		main_panel.add(pTitle);
	}

	public static void main(String[] args) {
		new Chat();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == btnBack) {
			dispose();
		}
		if (ob == movie_name) {
			controller.show_movie_name = movie_name.getSelectedItem().toString();
			System.out.println(controller.show_movie_name);

		}
		if (ob == btnEmoji) {
			Emoji emoji = new Emoji(this);
			taWriteMsg.requestFocus(true); // 포커스를 메시지 입력 창으로
		}
		if (ob == btnFile) {
			JFileChooser chooser = new JFileChooser(); // 파일 선택
			FileNameExtensionFilter fiter = new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png"); // 필터
																														// 지정
			chooser.setFileFilter(fiter); // 필터 설정해줌

			int ret = chooser.showOpenDialog(null); // 다이얼로그
			if (ret != JFileChooser.APPROVE_OPTION) { // 취소버튼이나 강제로 닫았을 경우
				JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			} else {
				File file = chooser.getSelectedFile(); // 선택한 파일 정보
				taWriteMsg.setText(file.toString()); // 메시지 입력창에 추가
			}
			taWriteMsg.requestFocus(true); // 포커스를 메시지 입력 창으로
		}
		if(ob == btnSend) {
			MessageSend(); // 전송 버튼 이벤트
		}
	}

	private void MessageSend() {
		
		Controller controller = Controller.getInstance();

		String messageType = null;
		// 메시지 타입 file or tex로 구분
		if (taWriteMsg.getText().contains(".jpg") || taWriteMsg.getText().contains(".png")
				|| taWriteMsg.getText().contains(".gif") || taWriteMsg.getText().contains(".JPG")
				|| taWriteMsg.getText().contains(".PNG") || taWriteMsg.getText().contains(".GIF")) {
			messageType = "file"; // 메시지 타입 file로 저장
		} else {
			messageType = "text"; // 아닌 경우 text로 저장
		}

		Message message = null;
		
		if (messageType.equals("file")) { // 메시지 타입이 file일 경우
			// 이름, 보내는 메시지, 시간, 메시지 타입, 친구 이름
			message = new Message(userName, taWriteMsg.getText()+"", LocalTime.now(), messageType, controller.show_movie_name);

		} else {
			// 이름, 보내는 메시지, 시간, 메시지 타입, 친구 이름
			message = new Message(userName, taWriteMsg.getText()+"\n", LocalTime.now(), messageType, controller.show_movie_name);
			System.out.println(userName);

		}
		controller.clientSocket.send(message);
		taWriteMsg.setText(""); // 미시지 입력 창 리셋
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
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) { // 엔터 키 눌렸을 때
			if (taWriteMsg.getText().equals("")) {

			} else {
				taWriteMsg.setText(taWriteMsg.getText().trim());
				MessageSend();	//메시지 전송
			}
		}
	}

	public JTextArea getTaWriteMsg() {
		return taWriteMsg;
	}

	public static void displayComment(Message message) {
		Controller con = Controller.getInstance();
		
		for (Chat chatName : UserMain.chatPanelName) {

			if (userName.equals(message.getSendUserID())) {
//				&& chatName.panelName.equals(message.getReceiveFriendName())) {

				chatName.textPrint(message.getSendTime().format(DateTimeFormatter.ofPattern("a HH:mm")) // 화면에 출력 -> 시간
																										// <이름>
						+ "   [ " + message.getSendUserID() + " ]", AlignEnum.RIGHT);

				if (message.getMessageType().equals("file")) { // 파일일 경우
					chatName.imgPrint(message.getSendComment()); // 이미지 메시지 전송
				} else { // 텍스트일 경우
					chatName.textPrint(message.getSendComment(), AlignEnum.RIGHT); // 텍스트 메시지 전송
				}

			} else {
				if(con.show_movie_name.equals(message.getReceiveMovieName())) {
					chatName.textPrint(message.getSendTime().format(DateTimeFormatter.ofPattern("a HH:mm")) + "   [ " // 화면에 출력
																												// -> 시간
																												// <이름>
						+ message.getSendUserID() + " ]", AlignEnum.LEFT);
				if (message.getMessageType().equals("file")) {
					chatName.imgPrint(message.getSendComment()); // 이미지 크기 설정해서 전송
				} else {
					chatName.textPrint(message.getSendComment(), AlignEnum.LEFT); // 왼쪽 출력
				}
				}
			}
		}
	}
	
	/**
	 * 이미지 파일 메시지 전송
	 * @param sendImage
	 */
	private void imgPrint(String sendImage) { // 이미지 전송

		ImageIcon imgFile = new ImageIcon(sendImage); // 사진파일
		Image imgFileResize = imgFile.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 크기 설정
		ImageIcon image = new ImageIcon(imgFileResize); // 설정된 크기로 이미지 선언

		StyledDocument doc2 = (StyledDocument) jtpChat.getDocument(); // 서식 첨부 범용 인터페이스
		Style style2 = doc2.addStyle("StyleName", null); // 새로운 서식 추가
		StyleConstants.setIcon(style2, image); // 편집 가능한 텍스트 컴퍼넌트와 편집 불능인 텍스트 컴퍼넌트를 처리하는 클래스와 인터페이스를 제공

		try {
			doc2.insertString(doc2.getLength(), "invisible text" + "\n", style2);	//서식 추가

			jtpChat.setAutoscrolls(true);
			jtpChat.setCaretPosition(jtpChat.getDocument().getLength());	// 스크롤을 가장 아래로 
			
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 텍스트 메시지 전송
	 * @param string
	 * @param alignEnum
	 */
	private void textPrint(String string, AlignEnum alignEnum) { // 텍스트 전송

		try {
			document = jtpChat.getStyledDocument();
			SimpleAttributeSet sortMethod = new SimpleAttributeSet(); // 새로운 속성 세트를 작성

			if (alignEnum == AlignEnum.RIGHT) { // 오른쪽 전송
				StyleConstants.setAlignment(sortMethod, StyleConstants.ALIGN_RIGHT);	//오른쪽으로
			} else if (alignEnum == AlignEnum.LEFT) { // 왼쪽 전송
				StyleConstants.setAlignment(sortMethod, StyleConstants.ALIGN_LEFT);		//왼쪽으로
			}
			document.setParagraphAttributes(document.getLength(), document.getLength() + 1, sortMethod, true); // 문단 특성을 정함 (문단 정렬, 들여쓰기, 문단 사이 간격)
			document.insertString(document.getLength(), string + "\n", sortMethod); // 메시지 추가
			jtpChat.setAutoscrolls(true);
			jtpChat.setCaretPosition(jtpChat.getDocument().getLength()); // 스크롤을 가장 아래로
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
	
}
