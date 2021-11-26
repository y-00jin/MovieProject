package movieproject.client.datacommunication;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import movieproject.movie.Chat;
import movieproject.server.datacommunication.Message;


public class ClientSocket {

	Socket socket;
	private BufferedReader br;
	private PrintWriter pw;

	public void startClient() { // controller에서 사용

		Thread thread = new Thread(() -> {
			try {
				socket = new Socket(); // 소켓 생성
				socket.connect(new InetSocketAddress("localhost", 5000)); // 연결 요청 (ip, 포트 번호)
				System.out.println("연결 요청");
				// -> socket 생성 및 연결 요청
			} catch (IOException e) { // 연결x
				System.out.println("서버 통신 안됨");
				e.printStackTrace();
			}
		});

		thread.start(); // 스레드 시작

	}

	/**
	 * 클라이언트 종료
	 */
	public void stopClient() { // 클라이언트 종료

		try {
			if (socket != null && !socket.isClosed()) { // 소켓이 열려있으면 //isClosed -> 연결 닫혀있으면 true 그렇지 않으면 false
				socket.close(); // 소켓 닫기
			}

		} catch (IOException e) {
			System.out.println("stopclient 오류");
		}
	}

	/**
	 * 서버로 메시지를 보내는 역할
	 * @param messageInfo 메시지 정보
	 */
	public void send(Message messageInfo) {

		Thread thread = new Thread(() -> {

			try {

				//System.out.println("messageInfo : " + messageInfo);
				pw = new PrintWriter(socket.getOutputStream()); // 출력스트림 생성

				ArrayList<Message> list = new ArrayList<>();
				list.add(messageInfo);	// 메시지 정보를 list에 저장

				JSONObject jo = new JSONObject();
				jo.put("list", list);
				//System.out.println("!!!!" + jo.toString());

				pw.println(new Gson().toJson(jo));	//JSON 형태로 변환
				pw.flush();

				// 발송 후 메시지 받기
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while (true) {
					try {

						String returnMessage = br.readLine();	//스트림에서 한 줄을 읽어 returnMessage에 저장

						//System.out.println("리턴메시지 : " + returnMessage);
						Gson gson = new Gson();	//JSONObject를 Java Object로 변환
						JSONObject json = gson.fromJson(returnMessage, JSONObject.class);
						
						System.out.println(json.get("list").toString());
						HashMap<Object, Object> map = new HashMap<Object, Object>();
						List<Map<String, String>> list1 = (ArrayList<Map<String, String>>) json.get("list");
						Map<String, String> map1 = list1.get(0);
						
						
						Message msg = new Message((String) map1.get("sendUserID"), (String) map1.get("sendComment"), LocalTime.now(), (String) map1.get("messageType"),(String) map1.get("receiveMoiveName"));
						
						Chat.displayComment(msg);	//메시지를 화면에 출력

					//	System.out.println("맵으로 가져오는 이름" + (String) map1.get("sendUserName"));
					} catch (Error e) {
						System.out.println("에러,,");
					}
				}

			} catch (IOException e1) {
				System.out.println("send 오류");
				System.out.println(e1);
				e1.printStackTrace();
			}

		});
		thread.start(); // 스레드 시작
	}

}
