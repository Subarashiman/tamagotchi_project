package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAO {

	int result = 0;

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;

	// DAO -> DataBase Access Object

	// 동적로딩 및 데이터베이스 접속을 위한 메소드!
	public void getConn() {
		try {

			// 1. 동적로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. 데이터베이스 접속
			String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
			String db_user = "hr";
			String db_pw = "12345";

			conn = DriverManager.getConnection(db_url, db_user, db_pw);

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	// 객체를 반납할 수 있는 메소드 생성! -> 사용순서의 역순으로 닫기 !
	public void close() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 회원가입
	public int signUp(MemberDTO dto) {

		getConn();
		String id = dto.getId();
		String pw = dto.getPw();
		String sql = "insert into tb_member (id, pw) values(?, ?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			result = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}

		return result;
	}

	// 로그인 MemberDAO
	public int signIn(MemberDTO dto) {
		getConn();	// DB연결
		
		String id = dto.getId();
		String pw = dto.getPw();
		String sql = "select PW from tb_member where ID = ?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			while (rs.next()) {
				if (rs.getString(1).equals(pw)) {
					result = 1;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}

		return result; // id or pw가 다를시 0을 리턴함 일치하면 1을 리턴 !
	}

	public ArrayList<RankDTO> check_rank() {
		ArrayList<RankDTO> result_list = new ArrayList<>();

		getConn();

		String sql = "select tama_id, lv from tb_tamagotchi order by lv desc";
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			int rank = 1;
			while (rs.next()) {
				RankDTO dto = new RankDTO();
				dto.setRank(rank++);
				dto.setId(rs.getString(1));
				dto.setLv(rs.getInt(2));
				result_list.add(dto);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}

		return result_list;

	}

}
