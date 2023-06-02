package qss.vo;

public class DomainScheduleVo {
	private int Idx;
	private String Code;
	private String Name;
	private String ProductSelectType;
	private String OrderSheetType;
	private String LogoFile;
	private String CheckSum;
	
	public int getIdx() {
		return Idx;
	}
	public void setIdx(int idx) {
		Idx = idx;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getProductSelectType() {
		return ProductSelectType;
	}
	public void setProductSelectType(String productSelectType) {
		ProductSelectType = productSelectType;
	}
	public String getOrderSheetType() {
		return OrderSheetType;
	}
	public void setOrderSheetType(String orderSheetType) {
		OrderSheetType = orderSheetType;
	}
	public String getLogoFile() {
		return LogoFile;
	}
	public void setLogoFile(String logoFile) {
		LogoFile = logoFile;
	}
	public String getCheckSum() {
		return CheckSum;
	}
	public void setCheckSum(String checkSum) {
		CheckSum = checkSum;
	}
}
