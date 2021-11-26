package movieproject.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import movieproject.movie.Chat;

public class Emoji extends JFrame implements ActionListener {

	private JPanel pEmojiTab, pBase, pEmojis1, pEmojis2, pEmojis;

	private JScrollPane jsEmoji;

	private ImageIcon iconTab2, iconTab1, iconEmoji;

	private JButton btnTab1, btnTab2;

	private String setFolderNum;

	private String setFileNum;
//	CustomUI cus = new CustomUI();
	private Chat chat;
	private String tabImagePath = "emojis/";
	private String emojis1 = "emojis/emoji1/emoji";
	private String emojis2 = "emojis/emoji2/emoji";
	private ArrayList<JButton> arrEmoji1 = new ArrayList<>();
	private ArrayList<JButton> arrEmoji2 = new ArrayList<>();

	private BtnStyle btnStyle = new BtnStyle();

	public Emoji(Chat chatPanel) {
		chat = chatPanel;
		setTitle("이모티콘");
		setSize(350, 350);
		setLocation(200, 390);
		// setLocationRelativeTo(this);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 기본 패널 생성
		addPBase();

		add(pBase);
		setVisible(true);
	}

	/**
	 * 기본 패널 base 생성
	 */
	private void addPBase() {

		// 기본 패널 생성
		pBase = new JPanel();
		pBase.setLayout(new BorderLayout());

		addPEmojiTab(); // 이모지 탭 패널 생성

		addPEmojis(); // 이모지 패널 생성
		pBase.add(pEmojiTab, BorderLayout.NORTH);
		pBase.add(pEmojis, BorderLayout.CENTER);

	}

	/**
	 * 이모지 탭 패널 생성
	 */
	private void addPEmojiTab() {

		// 이모지 탭 패널 생성
		pEmojiTab = new JPanel();
		pEmojiTab.setLayout(new GridLayout(1, 2, 10, 10));
		pEmojiTab.setBackground(new Color(0xE7FEF9));
		pEmojiTab.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		iconTab1 = btnStyle.BtnImage(tabImagePath + "emoji1/emoji1", 35, 35);	// 이미지 설정
		btnTab1 = new JButton(iconTab1);
		btnEmojiStyle(btnTab1);
		pEmojiTab.add(btnTab1);

		iconTab2 = btnStyle.BtnImage(tabImagePath + "emoji2/emoji1", 35, 35);	//이미지 설정
		btnTab2 = new JButton(iconTab2);
		btnEmojiStyle(btnTab2);
		pEmojiTab.add(btnTab2);

	}

	/**
	 * 이모지를 담는 전체 패널 생성
	 */
	private void addPEmojis() {

		// 이모지를 담는 전체 패널
		pEmojis = new JPanel();
		pEmojis.setLayout(new BorderLayout());

		addPEmojis1(); // 실행했을 때 처음 보이는 이모티콘 창

		pEmojis.add(jsEmoji, BorderLayout.CENTER);

	}

	/**
	 * 탭1을 구성하는 패널 생성
	 */
	private void addPEmojis1() {
		// 이모지1 패널 생성
		pEmojis1 = new JPanel();
		pEmojis1.setLayout(new GridLayout(4, 3, 20, 20));
		pEmojis1.setBackground(Color.WHITE);
		pEmojis1.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

		addBtnEmoji1(); // 이모지1 패널에 들어가는 이모지 버튼 생성

		jsEmoji = new JScrollPane(pEmojis1);
		jsEmoji.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); // 수직 스크롤 항상
		jsEmoji.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 수평 스크롤 없애기

		pEmojis.add(jsEmoji, BorderLayout.CENTER);

	}

	/**
	 * 이모지 1에 들어가는 이모지 버튼 생성
	 */
	private void addBtnEmoji1() {
		// 이모지 탭 1 생성
		for (int i = 1; i <= 12; i++) {

			iconEmoji = btnStyle.BtnImage(emojis1 + i, 55, 55);	// 이미지 크기 설정
			JButton btnEmoji = new JButton(iconEmoji);	// 버튼 생성
			btnEmojiStyle(btnEmoji);	//스타일 적용
			pEmojis1.add(btnEmoji);	// 패널에 추가
			arrEmoji1.add(btnEmoji);	// ArrayList에 버튼들 추가

		}
	}

	/**
	 * 탭2를 구성하는 패널 생성
	 */
	private void addPEmojis2() {

		pEmojis2 = new JPanel();
		pEmojis2.setLayout(new GridLayout(4, 2, 20, 20));
		pEmojis2.setBackground(Color.WHITE);
		pEmojis2.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

		addBtnEmoji2();	// 이모지2 패널에 들어가는 이모지 버튼 생성

		jsEmoji = new JScrollPane(pEmojis2);
		jsEmoji.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); // 수직 스크롤 항상
		jsEmoji.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 수평 스크롤 없애기

		pEmojis.add(jsEmoji, BorderLayout.CENTER);
	}

	/**
	 * 이모지 2에 들어가는 이모지 버튼 생성
	 */
	private void addBtnEmoji2() {
		// 이모지 탭 2 생성
		for (int i = 1; i <= 8; i++) {

			iconEmoji = btnStyle.BtnImage(emojis2 + i, 55, 55);	// 이미지 크기 설정
			JButton btnEmoji = new JButton(iconEmoji);	// 버튼 생성
			btnEmojiStyle(btnEmoji);	// 버튼 스타일 적용
			pEmojis2.add(btnEmoji);	// 패널에 추가
			arrEmoji2.add(btnEmoji);	// ArrayList에 추가

		}
	}

	/**
	 * 버튼 스타일 적용
	 * @param btn
	 */
	private void btnEmojiStyle(JButton btn) {

		btn.setContentAreaFilled(false); // 버튼 영역 배경 설정
		btn.setBorderPainted(false); // 테두리
		btn.addActionListener(this); // 액션 리스너 추가
	}

	public static void main(String[] args) {
		// new Emoji(chat);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnTab1 || obj == btnTab2) {	// 탭1이나 탭2를 선택할 경우
			
			if (obj == btnTab1) {	// 탭1 선택
				addPEmojis1();	//이모지1을 패널에 추가
			} else if (obj == btnTab2) {	// 탭2 선택
				addPEmojis2();	//이모지2 패널에 추가
			}
			pEmojis.removeAll(); // 연결되있는 모든 구성요소 제거
			pEmojis.add(jsEmoji); // 새로운 패널을 받아 컨텐트팬에 부착시킴 (새로운 구성요소 추가)
			revalidate(); // 레이아웃 관리자에게 새 구성 요소 목록을 기반으로 재설정하도록 지시하는 명령
			repaint(); // 다시 페인트하도록 지시
		} else {	// 이모지 버튼 눌렀을 때
			
			for (int i = 0; i < arrEmoji1.size(); i++) {	//이모지 1인경우
				if (obj == arrEmoji1.get(i)) {
					setFolderNum = "1";	//폴더 경로
					setFileNum = i + 1 + "";	// 선택한 번호
				}
			}

			for (int i = 0; i < arrEmoji2.size(); i++) {	//이모지 2인경우
				if (obj == arrEmoji2.get(i)) {
					setFolderNum = "2";	// 폴더 경로
					setFileNum = i + 1 + "";	// 선택한 번호
				}
			}
			// 선택한 이모지의 폴더 경로와 이미지 번호를 이용해 메시지 입력창에 텍스트로 전송
			chat.getTaWriteMsg().setText("resource/emojis/emoji" + setFolderNum + "/emoji" + setFileNum + ".png");
			dispose();	//창 닫기

		}

	}

}
