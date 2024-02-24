package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn;
	// DB�� �����ϰ� ���ִ� ��ü

	private PreparedStatement pstmt;
	private ResultSet rs;
	// ������ ���� ��ü

	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			// localhost�� MySQL 3306��Ʈ BBS��� DB ���� ��θ� dbURL�� ����.

			String dbID = "root";
			String dbPassword = "0458whtjddls";
			Class.forName("com.mysql.jdbc.Driver");
			// MySQL�� ������ �� �ֵ��� �Ű�ü ������ ���ִ� �ϳ��� ���̺귯��, JDBC ����̹� �ε�

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			// DB ���ӵǸ� conn ��ü�� ���������� �����.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// �� �ڵ������ MySQL DB�� ������ �� �ֵ��� �����ϴ� ����

	public int login(String userID, String userPassword) // �α��� ó���ϴ� �Լ�
	{
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		// �� userID�� �ش��ϴ� userPassword�� ��ȸ�ϴ� ����

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID); // ���� SQL ���� ?�� ��ȸ�� userID����
			rs = pstmt.executeQuery(); // ������ ���� ����� rs ��ü�� ����

			if (rs.next()) // rs�� ����� �����Ѵٸ�
			{
				if (rs.getString(1).equals(userPassword))
				// MySQL DB�� userPassword�� �α��� �õ��� userPassword�� ��ġ�ϸ�
				{
					return 1; // �α��� ����
				} else
					return 0; // �α��� ����(��й�ȣ Ʋ��)
			}
			return -1; // ���̵� ���� userID=?�κ� Ȯ��

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // �����ͺ��̽� ����
	}

	public int join(User user) {
		// String SQL = "INSERT INTO VALUES(?,?,?,?,?)"; ����� �ڵ�� �̰ǵ�, �̰� �� �ȵɱ�?
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
		return -1; // DB ����
	}
}