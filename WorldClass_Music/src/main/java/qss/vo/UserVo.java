package qss.vo;

/**
 * <pre>
 * qss.vo 
 *    |_ UsersVo.java
 * 
 * </pre>
 * @date : 2018. 12. 21. 오후 5:16:55
 * @version : 
 * @author : ksh
 */
public class UserVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private String usersIdx;		  // 사용자 일련번호
	private String id;                // 아이디
	private String adminType;		  // 관리자종류
	private String name;		      // 이름
	private String password;		  // 비밀번호
	
	private String[] checkboxArr;	  // 체크된 배열값
		
	public String[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(String[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public String getUsersIdx() {
		return usersIdx;
	}
	public void setUsersIdx(String usersIdx) {
		this.usersIdx = usersIdx;
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
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}