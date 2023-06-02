package qss.vo;


public class LoginVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private int UR_INDEX;
	private String UR_ID;
	private String UR_PWD;
	private String UR_NEW_PWD;
	private String UR_NAME;
	private int UR_UGINDEX;
	private String UR_TYPE;
	private String UR_PWDCDATE;
	private String LH_ACCESSIP;
	private String PH_PWD;
	private int UG_AUINDEX;
	//해당 서비스의 최상위 폴더 아이디 저장 
	private int DG_INDEX;
	
	
	
	public int getUR_INDEX() {
		return UR_INDEX;
	}
	public void setUR_INDEX(int uR_INDEX) {
		UR_INDEX = uR_INDEX;
	}
	public String getUR_ID() {
		return UR_ID;
	}
	public void setUR_ID(String uR_ID) {
		UR_ID = uR_ID;
	}
	public String getUR_PWD() {
		return UR_PWD;
	}
	public void setUR_PWD(String uR_PWD) {
		UR_PWD = uR_PWD;
	}
	public String getUR_NEW_PWD() {
		return UR_NEW_PWD;
	}
	public void setUR_NEW_PWD(String uR_NEW_PWD) {
		UR_NEW_PWD = uR_NEW_PWD;
	}
	public String getUR_NAME() {
		return UR_NAME;
	}
	public void setUR_NAME(String uR_NAME) {
		UR_NAME = uR_NAME;
	}
	public int getUR_UGINDEX() {
		return UR_UGINDEX;
	}
	public void setUR_UGINDEX(int uR_UGINDEX) {
		UR_UGINDEX = uR_UGINDEX;
	}
	public String getUR_TYPE() {
		return UR_TYPE;
	}
	public void setUR_TYPE(String uR_TYPE) {
		UR_TYPE = uR_TYPE;
	}
	public String getUR_PWDCDATE() {
		return UR_PWDCDATE;
	}
	public void setUR_PWDCDATE(String uR_PWDCDATE) {
		UR_PWDCDATE = uR_PWDCDATE;
	}
	public String getLH_ACCESSIP() {
		return LH_ACCESSIP;
	}
	public void setLH_ACCESSIP(String lH_ACCESSIP) {
		LH_ACCESSIP = lH_ACCESSIP;
	}
	public String getPH_PWD() {
		return PH_PWD;
	}
	public void setPH_PWD(String pH_PWD) {
		PH_PWD = pH_PWD;
	}
	public int getUG_AUINDEX() {
		return UG_AUINDEX;
	}
	public void setUG_AUINDEX(int uG_AUINDEX) {
		UG_AUINDEX = uG_AUINDEX;
	}
	
	public int getDG_INDEX() {
		return DG_INDEX;
	}
	public void setDG_INDEX(int uDG_INDEX) {
		DG_INDEX = uDG_INDEX;
	}
	
}
