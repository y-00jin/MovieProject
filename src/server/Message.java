package server;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {

	private String sendUserID; //접속한 유저아이디

	private String sendComment; //보내는 메시지

	private LocalTime sendTime; // 보내는 시간 | 안쓸수도있음

	private String messageType; // 보내는 메시지의 타입

	private String receiveMoiveName; // 내가 본 영화이름 // ex) 기본이 베이스고 기본 + 영화제목

	public Message(String sendUserName, String sendComment, LocalTime sendTime, String messageType,
			String receiveFriendName) {

		this.sendUserID = sendUserName;
		this.sendComment = sendComment;
		this.sendTime = sendTime;
		this.messageType = messageType;
		this.receiveMoiveName = receiveFriendName;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public String getSendUserName() {

		return sendUserID;
	}

	public void setSendUserName(String sendUserName) {

		this.sendUserID = sendUserName;
	}

	public String getSendComment() {

		return sendComment;
	}

	public void setSendComment(String sendComment) {

		this.sendComment = sendComment;
	}

	public LocalTime getSendTime() {

		return sendTime;
	}

	public void setSendTime(LocalTime sendTime) {

		this.sendTime = sendTime;
	}

	public String getMessageType() {

		return messageType;
	}

	public void setMessageType(String messageType) {

		this.messageType = messageType;
	}

	public String getReceiveFriendName() {

		return receiveMoiveName;
	}

	public void setReceiveFriendName(String receiveFriendName) {

		this.receiveMoiveName = receiveFriendName;
	}
	public void sysms() {
		System.out.println(sendUserID);
		System.out.println(sendComment);
		System.out.println(sendTime);
		System.out.println(messageType);
		System.out.println(receiveMoiveName);
	}
}
