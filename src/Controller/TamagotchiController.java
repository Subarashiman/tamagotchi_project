package Controller;

import java.util.ArrayList;

import Model.TamagotchiDAO;
import Model.TamagotchiDTO;

public class TamagotchiController {

	TamagotchiDAO dao = new TamagotchiDAO();
	int result = 0; // 결과 리턴을 위한 변수

	// 다마고치 등록
	public int con_create(String tama_id, String nickname, String type) {
		TamagotchiDTO dto = new TamagotchiDTO();
		dto.setTama_id(tama_id);
		dto.setNickname(nickname);
		dto.setType(type);

		result = dao.create(dto);

		return result;
	}

	// 다마고치 키우기(욕구 들어주기) (타마고치 아이디 필요함)
	// view에서 사용자의 선택에 따른 desire를 문자열로 보내주기!!
	// ex) 1 -> desire = '먹기' / 2 -> desire = '자기'
	public int con_raise(String desire, String tama_id) {
		TamagotchiDTO dto = new TamagotchiDTO();
		dto.setDesire(desire);
		dto.setTama_id(tama_id);
		
		result = dao.raise(dto);
		
		return result;
	}
	
	// 다마고치 상태 확인
	public ArrayList<TamagotchiDTO> con_status(String tama_id ) {
		ArrayList<TamagotchiDTO> status_list = new ArrayList<TamagotchiDTO>();
		
		TamagotchiDTO dto = new TamagotchiDTO();
		dto.setTama_id(tama_id);
	
		status_list = dao.status(dto);
		
		return status_list;
		
	}
	
	
	// 최종접속날짜 등록
	
	
	// 다마고치 사망확인 (타마고치 아이디 필요)
	public int check_death(String tama_id) {
		TamagotchiDTO dto = new TamagotchiDTO();
		
		dto.setTama_id(tama_id);
		
		result = dao.check_death(dto);
		
		return result; // 0 또는 1이 반환됨
		
		
		
	}
	
	public int delete(String tama_id) {
		TamagotchiDTO dto = new TamagotchiDTO();
		dto.setTama_id(tama_id);
		
		result = dao.delete(dto);
		return result;
	}
	
	
}

