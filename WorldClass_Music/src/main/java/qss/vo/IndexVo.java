package qss.vo;

public class IndexVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private String id;                // 아이디
	private String adminType;		  // 관리자종류
	private String name;		      // 이름
	private String password;		  // 비밀번호
	private int cnt;
		
	/*private int IDX;
	private String MID;
	private String ORL_PWD;
	private String NEW_PWD;
	private int MODIDATE;
	private int PASSWORDEXPIREDATE;
	private String mainModYn;
	private String orderModYn;
	private int CNT;
	private int cnt;
	private int ERR;*/
	
	
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	
	
}
