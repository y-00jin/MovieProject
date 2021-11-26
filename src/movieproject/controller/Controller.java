package movieproject.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import movieproject.DBconnect;
import movieproject.client.datacommunication.ClientSocket;

public class Controller {

	private static Controller singleton = new Controller();
	public String userId = "";
	public String movieName = "";
	public String sel_date = "";
	public String sel_time = "";
	public String peopleNum = "";
	public String seat = "";
	public String menu = "";

	public String show_movie_name = "";
	
	public ClientSocket clientSocket;

	private Controller() {
		clientSocket = new ClientSocket();

	}

	public static Controller getInstance() {

		return singleton;
	}

	/***
	 * 로그인
	 * 
	 * @param id
	 * @param pw
	 * @return
	 */
	public boolean checkIDPW(String id, String pw) {

		boolean check = false;
		String sql = "SELECT * FROM MOVIE_MEMBER WHERE ID = '" + id + "' AND PW='" + pw + "'";
		ResultSet rs = DBconnect.getResultSet(sql);
		try {
			if (rs.next()) {
				userId = id;
				check = true;
				clientSocket.startClient();
			} else {
				check = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return check;
	}

	/**
	 * id 리턴
	 * 
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * id로 이름, 전화번호 찾아 리턴
	 * 
	 * @param id
	 */
	public ArrayList<String> findInfo(String id) {
		ArrayList<String> arrUserInfo = new ArrayList<>();

		String selectUser = "SELECT NAME, PHONE FROM MOVIE_MEMBER WHERE ID = " + id;
		ResultSet rs = DBconnect.getResultSet(selectUser);

		try {
			while (rs.next()) {

			}
		} catch (SQLException e1) {
			System.out.println("사용자 찾기 오류");
			e1.printStackTrace();
		}
		return arrUserInfo;
	}

	// 영화이름
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getSel_date() {
		return sel_date;
	}

	public void setSel_date(String sel_date) {
		this.sel_date = sel_date;
	}

	public String getSel_time() {
		return sel_time;
	}

	public void setSel_time(String sel_time) {
		this.sel_time = sel_time;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
}
