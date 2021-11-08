package movieproject.movie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import movieproject.DBconnect;

 
public class MovieAPI {
	
    // 상수 설정
    //   - 요청(Request) 요청 변수
	
	 private final String REQUEST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
	 private final String URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
	 
    private final String AUTH_KEY = "0a12aeaa0a5445322e3b83211ca11804";
    
	String [] code = new String[10]; 
	Vector<String> co = new Vector<String>();
	Vector<String> name = new Vector<String>();
	Vector<String> genre = new Vector<String>();
	Vector<String> limit = new Vector<String>();
	Vector<String> time = new Vector<String>();
 
    //   - 일자 포맷
	private SimpleDateFormat DATE_FMT;
//	private SimpleDateFormat DATE_FMT = new SimpleDateFormat("20211010");
    
    public void setDATE_FMT(String str) {
    	DATE_FMT = new SimpleDateFormat(str);
	}

	// Map -> QueryString
    public String makeQueryString(Map<String, String> paramMap) {
        final StringBuilder sb = new StringBuilder();
 
        paramMap.entrySet().forEach(( entry )->{
            if( sb.length() > 0 ) {
                sb.append('&');
            }
            sb.append(entry.getKey()).append('=').append(entry.getValue());
        });
 
        return sb.toString();
    }
 
    // API요청
    public void requestAPI() {
        // 변수설정
        //   - 하루전 닐찌
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
 
        // 변수 설정
        //   - 요청(Request) 인터페이스 Map
        //   - 어제자 다양성 한국영화 10개 조회
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("key"          , AUTH_KEY);                        // 발급받은 인증키
        paramMap.put("targetDt"     , DATE_FMT.format(cal.getTime()));  // 조회하고자 하는 날짜
        paramMap.put("itemPerPage"  , "10");                            // 결과 ROW 의 개수( 최대 10개 )
        paramMap.put("multiMovieYn" , "N");                             // Y:다양성 영화, N:상업영화, Default:전체
        paramMap.put("repNationCd"  , "");                             // K:한국영화, F:외국영화, Default:전체

        try {
            // Request URL 연결 객체 생성
            URL requestURL = new URL(REQUEST_URL+"?"+makeQueryString(paramMap));
            HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
 
            // GET 방식으로 요청
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
 
            // 응답(Response) 구조 작성
            //   - Stream -> JSONObject
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String readline = null;
            StringBuffer response = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                response.append(readline);
            }
            // JSON 객체로  변환
            JSONObject responseBody = new JSONObject(response.toString());
 
            // 데이터 추출
            JSONObject boxOfficeResult = responseBody.getJSONObject("boxOfficeResult");
 
            // 박스오피스 주제 출력
            String boxofficeType = boxOfficeResult.getString("boxofficeType");
            //System.out.println(boxofficeType);
 
            // 박스오피스 목록 출력
            JSONArray dailyBoxOfficeList = boxOfficeResult.getJSONArray("dailyBoxOfficeList");
            Iterator<Object> iter = dailyBoxOfficeList.iterator();
            int i = 0;
            while(iter.hasNext()) {
                JSONObject boxOffice = (JSONObject) iter.next();
                //code[i++] = (String) boxOffice.get("movieCd");
                co.add((String) boxOffice.get("movieCd"));
                //System.out.printf("  %s  %s  %s %s\n", boxOffice.get("rnum"), boxOffice.get("movieCd"), boxOffice.get("movieNm")
                		//,boxOffice.get("openDt"));
                //String [] movieInfo = new String[10];
//                for (int i = 0; i < movieInfo.length; i++) {
//					movieInfo[i] =
//				}
                //boxOffice.get("rnum");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int z = 0; z < co.size(); z++) {
        	request(co.get(z));
        }
//        for(int z = 0; z < co.size(); z++) {
//        	AddDB(z);
//        }
    }
    public void request(String code) {
    	// 변수설정
        //   - 하루전 닐찌
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        
        // 변수 설정
        //   - 요청(Request) 인터페이스 Map
        //   - 어제자 다양성 한국영화 10개 조회
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("key"          , AUTH_KEY);                        // 발급받은 인증키
        paramMap.put("movieCd"          , code);                        // 발급받은 인증키
        try {
            // Request URL 연결 객체 생성
            URL requestURL = new URL(URL+"?"+makeQueryString(paramMap));
            HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
            //System.out.println(requestURL);
            // GET 방식으로 요청
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
 
            // 응답(Response) 구조 작성
            //   - Stream -> JSONObject
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String readline = null;
            StringBuffer response = new StringBuffer();
            while ((readline = br.readLine()) != null) {
                response.append(readline);
            }
            // JSON 객체로  변환
            JSONObject responseBody = new JSONObject(response.toString());
 
            // 데이터 추출
            JSONObject boxOfficeResult = responseBody.getJSONObject("movieInfoResult");
 
            // 박스오피스 주제 출력
           // String boxofficeType = boxOfficeResult.getString("movieInfo");
           // System.out.println(boxofficeType);
 
            // 박스오피스 목록 출력
            JSONObject movieInfo = (JSONObject) boxOfficeResult.get("movieInfo");
            JSONArray genres = movieInfo.getJSONArray("genres");
            JSONArray audits = movieInfo.getJSONArray("audits");
            JSONObject audits_watchGradeNm = (JSONObject)audits.get(0);
            JSONObject genres_genreNm = (JSONObject)genres.get(0);
            
	        //System.out.printf("%s %s %s %s %s\n", 
	            		name.add((String) movieInfo.get("movieNm"));
	            	    genre.add((String) genres_genreNm.get("genreNm"));
	            		limit.add((String) audits_watchGradeNm.get("watchGradeNm"));
	            		time.add((String) movieInfo.get("showTm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void AddDB(int i) {
    	DBconnect.DB();   	
    	String val = "1";
    	String str = "SELECT COUNT(*) FROM MOVIE m WHERE MOVIE_NAME LIKE '" + name.get(i) + "'";
    	String cnt = "select COUNT(*) FROM MOVIE ";
    	String num = "0";
    	
//    	String sql2 = "INSERT INTO URL "
//    			+ "(MOVIE_NAME, URL) "
//    			+ "VALUES('" + name.get(i) + "', '')";
//    	INSERT INTO AMOVIE.MOVIE
//    	(MOVIE_NAME, MOVIE_GENRE, MOVIE_LIMIT, RUNNINGTIME)
//    	VALUES('', '', '', '');

    	ResultSet re = DBconnect.getResultSet(str);
    	try {
			while(re.next()) {
				val = re.getString(1); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	if(val.equals("0")) {
    		ResultSet result = DBconnect.getResultSet(cnt);
    		try {
    			while(result .next()) {
    				num = result .getString(1); 
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		int n = Integer.parseInt(num)+1;
    		num = Integer.toString(n);
    		System.out.println(num);
    		String sql = "INSERT INTO MOVIE "
        			+ "(MOVIE_ID, MOVIE_NAME, MOVIE_GENRE, MOVIE_LIMIT, RUNNINGTIME) "
        			+ "VALUES('" +
        			num +  "', '" +
        			name.get(i) +  "', '" + 
        			genre.get(i) + "', '" + 
        			limit.get(i) + "', '" + 
        			time.get(i) +  "')";
    		DBconnect.getupdate(sql);
    		//DBconnect.getupdate(sql2);
    	}
    	else {
    		System.out.println("실패!");
    	}
	}
 
    public static void main(String[] args) {
        // API 객체 생성
        MovieAPI api = new MovieAPI();
        // API 요청
        api.requestAPI();
        //for(int i = 0; i < code.length; i++) {
        //	api.request(code[i]);
       // }
    }
	public Vector<String> getCo() {
		return co;
	}


	public Vector<String> getName() {
		return name;
	}

	public Vector<String> getGenre() {
		return genre;
	}

	public Vector<String> getLimit() {
		return limit;
	}

	public Vector<String> getTime() {
		return time;
	}
    
}
 