package qss.vo;

import java.util.List;

public class BrandResultVo {
	private String Name;
	private String Code;
	private String ProdSelectType;
	private String OrderSheetType;
	private String LogoFile;
	private List<DiscountResultVo> DiscountList;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getProdSelectType() {
		return ProdSelectType;
	}

	public void setProdSelectType(String prodSelectType) {
		ProdSelectType = prodSelectType;
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

	public List<DiscountResultVo> getDiscountList() {
		return DiscountList;
	}

	public void setDiscountList(List<DiscountResultVo> discountList) {
		DiscountList = discountList;
	}
}
