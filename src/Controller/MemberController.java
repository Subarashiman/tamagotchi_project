package Controller;

import java.util.ArrayList;

import Model.MemberDAO;
import Model.MemberDTO;
import Model.RankDTO;

public class MemberController {

	MemberDAO dao = new MemberDAO();
	int result = 0;
	

	// 회원가입
	
	public int signUp(String id, String pw) {
		
		MemberDTO dto = new MemberDTO();
		
		dto.setId(id);
		dto.setPw(pw);
		
		result = dao.signUp(dto);
		
		
		return result;
	}

	// 로그인 (MemberController)
	
	public int signIn(String id, String pw) {
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		
		result = dao.signIn(dto);  // 성공시 1 , 실패시 0리턴
		
		
		return result; // result는 0으로 초기화 되어있음
	}
	
	
	// 랭킹확인하기
	public ArrayList<RankDTO> check_rank() {
		ArrayList<RankDTO> rank_list = new ArrayList<>();
		
		rank_list = dao.check_rank();
		
		return rank_list;
	}
	
	
	
}
