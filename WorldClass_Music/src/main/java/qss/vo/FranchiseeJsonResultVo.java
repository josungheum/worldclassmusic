package qss.vo;

public class FranchiseeJsonResultVo {
	private int Version;
	private FranchiseeResultVo franchisee;

	public int getVersion() {
		return Version;
	}

	public void setVersion(int version) {
		Version = version;
	}

	public FranchiseeResultVo getFranchisee() {
		return franchisee;
	}

	public void setFranchisee(FranchiseeResultVo franchisee) {
		this.franchisee = franchisee;
	}
}
