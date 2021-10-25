package movieproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBconnect {
	private static Connection conn;
	private static Statement stat;
	public static void DB() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@114.71.137.174:53994:XE", "AMOVIE", "popcorn");
			 //System.out.println("연동");
			 stat = conn.createStatement();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 없음");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("연동실패");
			e.printStackTrace();
		}
	}
	
	public static ResultSet getResultSet(String sql) {
	      try {
	         Statement stmt = conn.createStatement();
	         return stmt.executeQuery(sql);
	   
	      }catch(Exception e){
	         System.out.println(e);   //오류 메시지
	         return null;
	      }
	   }
	
	public static void getupdate(String sql) {
		try {
			stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
