package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	// DB에 접근하게 해주는 객체

	private PreparedStatement pstmt;
	private ResultSet rs;
	// 정보를 담을 객체

	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			// localhost의 MySQL 3306포트 BBS라는 DB 접속 경로를 dbURL에 저장.

			String dbID = "root";
			String dbPassword = "0458whtjddls";
			Class.forName("com.mysql.jdbc.Driver");
			// MySQL에 접속할 수 있도록 매개체 역할을 해주는 하나의 라이브러리, JDBC 드라이버 로드

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			// DB 접속되면 conn 객체에 접속정보가 저장됨.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 위 코드까지가 MySQL DB에 접근할 수 있도록 설정하는 과정

	public int login(String userID, String userPassword) // 로그인 처리하는 함수
	{
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		// 각 userID에 해당하는 userPassword를 조회하는 쿼리

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID); // 위의 SQL 쿼리 ?에 조회할 userID적용
			rs = pstmt.executeQuery(); // 쿼리의 실행 결과를 rs 객체에 저장

			if (rs.next()) // rs에 결과가 존재한다면
			{
				if (rs.getString(1).equals(userPassword))
				// MySQL DB의 userPassword와 로그인 시도한 userPassword와 일치하면
				{
					return 1; // 로그인 성공
				} else
					return 0; // 로그인 실패(비밀번호 틀림)
			}
			return -1; // 아이디가 없음 userID=?부분 확인

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}

	public int join(User user) {
		// String SQL = "INSERT INTO VALUES(?,?,?,?,?)"; 영상속 코드는 이건데, 이건 왜 안될까?
		String SQL = "INSERT INTO USER (userID, userPassword, userName, userGender, userEmail) VALUES (?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // DB 오류
	}
}