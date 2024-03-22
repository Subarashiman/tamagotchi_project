package View;

import java.util.ArrayList;
import java.util.Scanner;

import Controller.MemberController;
import Controller.TamagotchiController;
import Model.RankDTO;
import Model.TamagotchiDTO;

public class Main {

	static String tama_name = null  ;
	static int tamaQ ;
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		MemberController memberController = new MemberController();
		TamagotchiController tamagotchiController = new TamagotchiController();
		int result = 0;

		while (true) {
			System.out.print("[1]회원가입 [2]로그인 [3]랭킹 [4]종료 >> ");
			int menu = sc.nextInt();

			if (menu == 1) {
				System.out.println("=====회원가입=====");
				System.out.print("가입 id : ");
				String id = sc.next();

				// 아이디 중복 검사
//             if(control.checkIdDuplicate(id)) {
//                 System.out.println("이미 사용중인 아이디입니다. 다른 아이디를 사용해 주세요.");
//                 continue; // 다시 메인 메뉴로 돌아감
//             }

				System.out.print("가입 pw : ");
				String pw = sc.next();

				// controller에 필요한 정보들 전달하기!

				result = memberController.signUp(id, pw);
				// 메소드 이름을 맞춰서 넣기
				if (result > 0) {
					System.out.println("회원가입 성공!");
				} else {
					System.out.println("실패");
				}
				// 요구사항정의서에 제시한대로 중복ID 검사

			} else if (menu == 2) {
				System.out.println("=====로그인=====");
				System.out.print("id 입력 : ");
				String id = sc.next();
				System.out.print("pw 입력 : ");
				String pw = sc.next();
				// if문으로 id,pw가 db에 있는 회원 정보와 일치하면 로그인 성공 코드
				result = memberController.signIn(id, pw);
				System.out.println(pw);
				System.out.println(result);
				if (result > 0) {
					System.out.println("로그인 성공 ~");
					String tama_id = id;
					System.out.print("[1]캐릭터 생성   [2]캐릭터 접속");
					int nextQ = sc.nextInt();
					if (nextQ == 1) {
						// 꽉꽉이, 냥냥이, 순돌이 케릭터 고르기
						System.out.println("==== 캐릭터를 선택해주세요 ====");
						System.out.println("[1]꽉꽉이 ༼'๑◕⊖◕๑༽   [2]냥냥이 ( ^ ⓛ ω ⓛ ^ )   [3]순돌이 (๑・‿・๑)");
						tamaQ = sc.nextInt();
						System.out.print("캐릭터의 이름을 입력해주세요 :");
						tama_name = sc.next();
						String type = "";
						if (tamaQ == 1) {
							type = "꽉꽉이";
							// 꽉꽉이 이모티콘 저장
							String k_default = "༼'๑◕⊖◕๑༽  ";
							String k_sleppy = "༼'๑ᵕ⊖ᵕ๑༽...zzZ";
							String k_excite = "༼'๑>⊖<๑༽ ";
							String k_hugry = "༼'๑≖⊖≖๑༽ ";
							// 케릭터성이 있는 전용 멘트도 몇가지 같이 해주면 됨

						} else if (tamaQ == 2) {
							type = "냥냥이";
							// 냥냥이 이모티콘 저장
							String n_default = "( ^ ⓛ ω ⓛ ^ ) ";
							String n_sleppy = "( ^ = ω = ^ ) ...zzZ ";
							String n_excite = "ヾ  ( ^ ◕ˇ ω ˇ◕ ^ ) ノ”";
							String n_hugry = "( ^ =` ω ´= ^ )  !!";

						} else if (tamaQ == 3) {
							type = "순돌이";
							// 순돌이 이모티콘 저장
							String s_default = "(๑・‿・๑)";
							String s_sleppy = "(๑=‿=๑)...zzZ";
							String s_excite = "p(๑>‿<๑)q";
							String s_hugry = "(๑ Ĭ ‿ Ĭ ๑)...zzZ";

						}
						result = tamagotchiController.con_create(tama_id, tama_name, type);
						if (result > 0) {
							System.out.println("캐릭터 생성 성공 !");
						} else {
							System.out.println("캐릭터 생성 실패ㅜㅜ");
						}

					} else if (nextQ == 2) {
						// 본격적인 타마고치 게임 시작!
						// DAO에서 정의한 각종 메소드 들어가고 코드 길어짐
						while (true) { System.out.println();
							System.out.print("[1]먹이주기 [2]재우기 [3]산책하기 [4]청소하기 [5]상태확인 [6]로그아웃 >> ");
							int tamaAct = sc.nextInt();
							int raise_result = 0;
							System.out.println();
							if (tamaAct == 1) {
								raise_result = tamagotchiController.con_raise("먹이주기", tama_id);
								if(tamaQ == 1) {
									System.out.println(tama_name + "(이)가 먹이를 맛있게 먹습니다.");
									System.out.println( "༼'๑≖⊖≖๑༽ ");
								} else if (tamaQ == 2) {
									System.out.println("( ^ =` ω ´= ^ )  !!");
								} else {
									System.out.println("(๑ Ĭ ‿ Ĭ ๑)...zzZ");
								}
								if (raise_result == -1) {
									System.out.println(tama_name +" 죽음 ㅜ");
								}
							} else if (tamaAct == 2) {
								raise_result = tamagotchiController.con_raise("재우기", tama_id);
								System.out.println(tama_name +"(이)가 편안하게 잠이 듭니다.");
								if(tamaQ == 1) {
									System.out.println( "༼'๑ᵕ⊖ᵕ๑༽...zzZ");
								} else if (tamaQ == 2) {
									System.out.println("( ^ = ω = ^ ) ...zzZ ");
								} else {
									System.out.println("(๑=‿=๑)...zzZ");
								}
								if (raise_result == -1) {
									System.out.println(tama_name + "죽음 ㅜ");
								}
								// 임티와 간단한 캐릭터 고유의 멘트 출력
							} else if (tamaAct == 3) {
								raise_result = tamagotchiController.con_raise("산책하기", tama_id);
								System.out.println(tama_name +"(이)와 즐거운 산책을 합니다.");
								if(tamaQ == 1) {
									System.out.println( "༼'๑>⊖<๑༽ ");
								} else if (tamaQ == 2) {
									System.out.println("ヾ  ( ^ ◕ˇ ω ˇ◕ ^ ) ノ”");
								} else {
									System.out.println("p(๑>‿<๑)q");
								}
								if (raise_result == -1) {
									System.out.println(tama_name +"죽음 ㅜ");
								}
								// 임티와 간단한 캐릭터 고유의 멘트 출력
							} else if (tamaAct == 4) {
								raise_result = tamagotchiController.con_raise("청소하기", tama_id);
								System.out.println(tama_name + "의 집을 깨끗이 청소합니다.");
								System.out.println("ヽ(￣д￣;)ノ=3=3=3");
								if (raise_result == -1) {
									System.out.println(tama_name + "죽음 ㅜ");
								}
								// 청소가 끝나고 난 뒤 안내 메시지 출력 캐릭터의 소리
							} else if (tamaAct == 5) {
								ArrayList<TamagotchiDTO> status = new ArrayList<>();
								status = tamagotchiController.con_status(tama_id);
								System.out.println("다마고치의 현재 상태를 확인합니다.");
								for (TamagotchiDTO t : status) {
									System.out.println("[이름] " + t.getNickname());
									System.out.println("[hp] " + t.getHealth_point());
									System.out.println("[레벨] "+ t.getLv() );
									System.out.println("[먹은횟수] "+ t.getEat_count());
									System.out.println("[똥] " + t.getPoo());
								}

							} else if (tamaAct == 6) {
								// 진행상황을 DB에 저장
								System.out.println("게임 진행 상황을 저장하고 로그아웃합니다.");
								break; // while 루프를 빠져나옴
							} else {
								System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
							}
						}

					} else {
						System.out.println("잘못된 입력입니다.");
						// 다시 돌아가서 "[1]캐릭터 생성 [2]캐릭터 접속" 고르도록
					}

				} else {
					System.out.println("로그인 실패");
					continue;
				}
			} else if (menu == 3) {
				// 랭킹 조회 기능
				// 아이디어 필요
				System.out.println("=====랭킹 조회=====");
				ArrayList<RankDTO> rankingList = new ArrayList<>();
				rankingList = memberController.check_rank();
				// DAO에서 랭킹 데이터 조회
				for (RankDTO rank : rankingList) {
					System.out.println("RANK : " + rank.getRank() + "ID: " + rank.getId() + ", 점수: " + rank.getLv());
				}
			} else if (menu == 4) {
				System.out.println("시스템을 종료합니다");
				break;

			}
		}

	}

}