package Model;

public class TamagotchiDTO {

	private String nickname;
	private String type;
	private int health_point;
	private int lv;
	private int eat_count;
	private int poo;
	private String last_accessed = "sysdate";
	private int is_dead;
	private String tama_id; // 맴버 아이디와 같음
	private String desire;

	public TamagotchiDTO(String nickname, String tp, int health_point, int lv, int eat_count, int poo,
			String last_accessed, int is_dead, String tama_id, String desire) {
		this.nickname = nickname;
		this.type = tp;
		this.health_point = health_point;
		this.lv = lv;
		this.eat_count = eat_count;
		this.poo = poo;
		this.last_accessed = last_accessed;
		this.is_dead = is_dead;
		this.tama_id = tama_id;
		this.desire = desire;
	}

	
	
	
	public TamagotchiDTO(String nickname, int health_point, int lv, int eat_count, int poo) {
		super();
		this.nickname = nickname;
		this.health_point = health_point;
		this.lv = lv;
		this.eat_count = eat_count;
		this.poo = poo;
	}




	public TamagotchiDTO() {
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getHealth_point() {
		return health_point;
	}

	public void setHealth_point(int health_point) {
		this.health_point = health_point;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getEat_count() {
		return eat_count;
	}

	public void setEat_count(int eat_count) {
		this.eat_count = eat_count;
	}

	public int getPoo() {
		return poo;
	}

	public void setPoo(int poo) {
		this.poo = poo;
	}

	public String getLast_accessed() {
		return last_accessed;
	}

	public void setLast_accessed(String last_accessed) {
		this.last_accessed = last_accessed;
	}

	public int getIs_dead() {
		return is_dead;
	}

	public void setIs_dead(int is_dead) {
		this.is_dead = is_dead;
	}

	public String getTama_id() {
		return tama_id;
	}

	public void setTama_id(String tama_id) {
		this.tama_id = tama_id;
	}

	public String getDesire() {
		return desire;
	}

	public void setDesire(String desire) {
		this.desire = desire;
	}

}
