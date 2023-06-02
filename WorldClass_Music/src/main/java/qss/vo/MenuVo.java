package qss.vo;

public class MenuVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private int AS_AUINDEX;
	private int SM_INDEX;
	private int SM_FORMINDEX;
	private String SM_NAME;
	private String SM_URL;
	private int SM_PINDEX;
	private String SM_PNAME;
	private int SM_DEPTH;
	private int SM_ORDERSEQ;
	private int[] SM_ARRINDEX;
	private String SM_REGDATE;
	private String SM_ETC;
	private String SM_CHECK;
	private int returnCnt = 0;
	private int cnt;
	
	public int getAS_AUINDEX() {
		return AS_AUINDEX;
	}
	public void setAS_AUINDEX(int aS_AUINDEX) {
		AS_AUINDEX = aS_AUINDEX;
	}
	public int getSM_INDEX() {
		return SM_INDEX;
	}
	public void setSM_INDEX(int sM_INDEX) {
		SM_INDEX = sM_INDEX;
	}
	public int getSM_FORMINDEX() {
		return SM_FORMINDEX;
	}
	public void setSM_FORMINDEX(int sM_FORMINDEX) {
		SM_FORMINDEX = sM_FORMINDEX;
	}
	public String getSM_NAME() {
		return SM_NAME;
	}
	public void setSM_NAME(String sM_NAME) {
		SM_NAME = sM_NAME;
	}
	public String getSM_URL() {
		return SM_URL;
	}
	public void setSM_URL(String sM_URL) {
		SM_URL = sM_URL;
	}
	public int getSM_PINDEX() {
		return SM_PINDEX;
	}
	public void setSM_PINDEX(int sM_PINDEX) {
		SM_PINDEX = sM_PINDEX;
	}
	public String getSM_PNAME() {
		return SM_PNAME;
	}
	public void setSM_PNAME(String sM_PNAME) {
		SM_PNAME = sM_PNAME;
	}
	public int getSM_DEPTH() {
		return SM_DEPTH;
	}
	public void setSM_DEPTH(int sM_DEPTH) {
		SM_DEPTH = sM_DEPTH;
	}
	public int getSM_ORDERSEQ() {
		return SM_ORDERSEQ;
	}
	public void setSM_ORDERSEQ(int sM_ORDERSEQ) {
		SM_ORDERSEQ = sM_ORDERSEQ;
	}
	public int[] getSM_ARRINDEX() {
		return SM_ARRINDEX;
	}
	public void setSM_ARRINDEX(int[] sM_ARRINDEX) {
		SM_ARRINDEX = sM_ARRINDEX;
	}
	public String getSM_REGDATE() {
		return SM_REGDATE;
	}
	public void setSM_REGDATE(String sM_REGDATE) {
		SM_REGDATE = sM_REGDATE;
	}
	public String getSM_ETC() {
		return SM_ETC;
	}
	public void setSM_ETC(String sM_ETC) {
		SM_ETC = sM_ETC;
	}
	public String getSM_CHECK() {
		return SM_CHECK;
	}
	public void setSM_CHECK(String sM_CHECK) {
		SM_CHECK = sM_CHECK;
	}
	public int getReturnCnt() {
		return returnCnt;
	}
	public void setReturnCnt(int returnCnt) {
		this.returnCnt = returnCnt;
	}
	/**
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}
	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}	
}
