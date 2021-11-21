package movieproject.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import movieproject.DBconnect;

public class Controller {

	private static Controller singleton = new Controller();
	public String userId = "";
	public String movieName = "";
	public String sel_date = "";
	public String sel_time = "";

	private Controller() {
		// clientSocket = new ClientSocket(); 클라이언트 소켓 (나중에 추가해야함)

	}

	public static Controller getInstance() {

		return singleton;
	}

	/***
	 * 로그인
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
	 * @return
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * id로 이름, 전화번호 찾아 리턴
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
	
	
}
