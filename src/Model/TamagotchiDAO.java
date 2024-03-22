package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TamagotchiDAO {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	// 결과를 처리하기 위한 변수선언
	int result = 0;

	static int turn = 0; // 턴 == tb_tamagotchi의 lv
	static int hp = 0; // 헬스포인트 == tb_tamagotchi의 health_point
	static int poo = 0; // 똥 == tb_tamagotchi의 poo
	static int eat_count = 0; // 밥 먹은 횟수 == tb_tamagotchi의 eat_count

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
			if (psmt != null) {
				psmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 타마고치 생성
	public int create(TamagotchiDTO dto) {
		/*
		 * db에 접속해 controller에서 dto로 받아온 tama_id, nickname, type을 db에 저장한 후 결과를
		 * Controller에 다시 돌려주기
		 */
		getConn();

		String sql = "insert into TB_TAMAGOTCHI(tama_id,nickname,tp) values(?,?,?)";

		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTama_id());
			psmt.setString(2, dto.getNickname());
			psmt.setString(3, dto.getType());

			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;

	}

	// 타마고치 욕구 들어주기
	/*
	 * 먹이주기 : turn + 1 , HP + 1 (3번 먹으면 똥쌈) 재우기 : turn + 1, HP - 2, 3초 간 수면 (thread)
	 * 청소하기 : 똥 유무 판단 산책하기 : turn + 1, HP + 1
	 */
	public int raise(TamagotchiDTO dto) {

		getConn();
		String desire = dto.getDesire();
		String id = dto.getTama_id();
		int is_dead = 0;
		String sql = "select lv, health_point, poo, is_dead, eat_count from tb_tamagotchi where tama_id = ?";
		try {

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			rs = psmt.executeQuery();

			while (rs.next()) {
				turn = rs.getInt(1);
				hp = rs.getInt(2);
				poo = rs.getInt(3);
				is_dead = rs.getInt(4);
				eat_count = rs.getInt(5);
			}
			if (is_dead == 1) {
				return -1;	// -> 다마고치 죽음 ㅜ
			}

			switch (desire) {
			case "먹이주기":
				feed();
				break;
			case "재우기":
				sleep();
				break;
			case "청소하기":
				clean();
				break;
			case "산책하기":
				walk();
				break;
			}

			if (hp < 0) {
				sql = "update tb_tamagotchi set is_dead = 1, health_point=? where tama_id = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, hp);
				psmt.setString(2, id);

				result = psmt.executeUpdate();
				return -1;  // -> 다마고치 죽음 ㅜ

			} else {
				// 욕구를 들어준 후 데이터 업데이트
				sql = "update tb_tamagotchi set lv=?,health_point=?, poo=?, eat_count=? where tama_id=?";
				psmt = conn.prepareStatement(sql);
				psmt.setInt(1, turn);
				psmt.setInt(2, hp);
				psmt.setInt(3, poo);
				psmt.setInt(4, eat_count);
				psmt.setString(5, id);

				result = psmt.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}

		return result;
	}

	/*
	 * 먹이주기 : turn + 1 , HP + 1 (3번 먹으면 똥쌈) 재우기 : turn + 1, HP - 2, 3초 간 수면 (thread)
	 * 청소하기 : 똥 유무 판단 산책하기 : turn + 1, HP + 1
	 */

	// 먹이주기
	private void feed() {
		check_poo();
		turn++;
		eat_count++;
		hp++;
		if (eat_count > 3) {
			eat_count = 0;
			poo++;
		}
	}

	// 재우기
	private void sleep() {
		check_poo();
		// thread 사용
		turn++;
		hp -= 2;
		Thread thread = new Thread();
		try {
			for (int i = 0; i < 3; i++) {
				thread.sleep(1000);
				System.out.println("쿨쿨쿨zzz");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 청소하기
	private void clean() {
		if (poo > 0) {
			poo--;
		}
	}

	// 산책하기
	private void walk() {
		check_poo();
		turn++;
		hp++;
	}

	private void check_poo() {
		if (poo > 0) {
			hp -= poo;
		}
	}
	

	// 타마고치의 상태를 확인하는 메소드
	public ArrayList<TamagotchiDTO> status(TamagotchiDTO dto) {
		ArrayList<TamagotchiDTO> result = new ArrayList<>();
		getConn();

		String tama_id = dto.getTama_id();
		String sql = "select nickname, health_point, eat_count, lv, poo from tb_tamagotchi where tama_id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, tama_id);

			rs = psmt.executeQuery();
			while (rs.next()) {
				String nickname = rs.getString(1);
				int health_point = rs.getInt(2);
				int eat_count = rs.getInt(3);
				int level = rs.getInt(4);
				int poo = rs.getInt(5);

				result.add(new TamagotchiDTO(nickname, health_point, level, eat_count, poo));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			close();
		}
		return result;
	}

	public int check_death(TamagotchiDTO dto) {

		getConn();
		String tama_id = dto.getTama_id();
		String sql = "select is_dead from tb_tamagotchi where tama_id = ? ";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, tama_id);

			rs = psmt.executeQuery();
			result = rs.getInt(1); // 0 또는 1이 반환됨
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	public int delete(TamagotchiDTO dto) {
		
		getConn();
		String tama_id = dto.getTama_id();
		String sql = "delete from tb_tamagotchi where tama_id = ?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, tama_id);
			
			result = psmt.executeUpdate();
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return result;
	}

}
