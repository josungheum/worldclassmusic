package qss.vo;

import java.util.ArrayList;
import java.util.List;

public class OrderProdResultVo {
	private int Idx;
	private int BrandIdx;
	private int FrancIdx;
	private int BaseIdx;
	private String Name;
	private String EnName;
	private String DisplayName;
	private String ProdCode;
	private String ListImg;
	private String ListImgCheckSum;
	private String DescImg;
	private String DescImgCheckSum;
	private String EnDescImg;
	private String EnDescImgCheckSum;
	private String BrandCode;
	private String FrancCode;
	private String LargeCategoryCode;
	private String MidCategoryCode;
	private String SmallCategoryCode;
	private String DisplayAmount;
	private int Amount;
	private String ActiveYn;
	private String BadgeType;
	private String PrintGroup;
	private int OrderSeq;
	private int ParentIdx;
	private String PrintGroupName;
	private String PrintGroupCode;
	private List<ProdSetResultVo> Sets = new ArrayList<ProdSetResultVo>();

	public int getIdx() {
		return Idx;
	}

	public void setIdx(int idx) {
		Idx = idx;
	}

	public int getBrandIdx() {
		return BrandIdx;
	}

	public void setBrandIdx(int brandIdx) {
		BrandIdx = brandIdx;
	}

	public int getFrancIdx() {
		return FrancIdx;
	}

	public void setFrancIdx(int francIdx) {
		FrancIdx = francIdx;
	}

	public int getBaseIdx() {
		return BaseIdx;
	}

	public void setBaseIdx(int baseIdx) {
		BaseIdx = baseIdx;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEnName() {
		return EnName;
	}

	public void setEnName(String enName) {
		EnName = enName;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public String getProdCode() {
		return ProdCode;
	}

	public void setProdCode(String prodCode) {
		ProdCode = prodCode;
	}

	public String getListImg() {
		return ListImg;
	}

	public void setListImg(String listImg) {
		ListImg = listImg;
	}

	public String getListImgCheckSum() {
		return ListImgCheckSum;
	}

	public void setListImgCheckSum(String listImgCheckSum) {
		ListImgCheckSum = listImgCheckSum;
	}

	public String getDescImg() {
		return DescImg;
	}

	public void setDescImg(String descImg) {
		DescImg = descImg;
	}

	public String getDescImgCheckSum() {
		return DescImgCheckSum;
	}

	public void setDescImgCheckSum(String descImgCheckSum) {
		DescImgCheckSum = descImgCheckSum;
	}

	public String getEnDescImg() {
		return EnDescImg;
	}

	public void setEnDescImg(String enDescImg) {
		EnDescImg = enDescImg;
	}

	public String getEnDescImgCheckSum() {
		return EnDescImgCheckSum;
	}

	public void setEnDescImgCheckSum(String enDescImgCheckSum) {
		EnDescImgCheckSum = enDescImgCheckSum;
	}

	public String getBrandCode() {
		return BrandCode;
	}

	public void setBrandCode(String brandCode) {
		BrandCode = brandCode;
	}

	public String getFrancCode() {
		return FrancCode;
	}

	public void setFrancCode(String francCode) {
		FrancCode = francCode;
	}

	public String getLargeCategoryCode() {
		return LargeCategoryCode;
	}

	public void setLargeCategoryCode(String largeCategoryCode) {
		LargeCategoryCode = largeCategoryCode;
	}

	public String getMidCategoryCode() {
		return MidCategoryCode;
	}

	public void setMidCategoryCode(String midCategoryCode) {
		MidCategoryCode = midCategoryCode;
	}

	public String getSmallCategoryCode() {
		return SmallCategoryCode;
	}

	public void setSmallCategoryCode(String smallCategoryCode) {
		SmallCategoryCode = smallCategoryCode;
	}

	public String getDisplayAmount() {
		return DisplayAmount;
	}

	public void setDisplayAmount(String displayAmount) {
		DisplayAmount = displayAmount;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	public String getActiveYn() {
		return ActiveYn;
	}

	public void setActiveYn(String activeYn) {
		ActiveYn = activeYn;
	}

	public String getBadgeType() {
		return BadgeType;
	}

	public void setBadgeType(String badgeType) {
		BadgeType = badgeType;
	}

	public String getPrintGroup() {
		return PrintGroup;
	}

	public void setPrintGroup(String printGroup) {
		PrintGroup = printGroup;
	}

	public int getOrderSeq() {
		return OrderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		OrderSeq = orderSeq;
	}

	public int getParentIdx() {
		return ParentIdx;
	}

	public void setParentIdx(int parentIdx) {
		ParentIdx = parentIdx;
	}

	

	public List<ProdSetResultVo> getSets() {
		return Sets;
	}

	public void setSets(List<ProdSetResultVo> sets) {
		Sets = sets;
	}

	public String getPrintGroupName() {
		return PrintGroupName;
	}

	public void setPrintGroupName(String printGroupName) {
		PrintGroupName = printGroupName;
	}

	public String getPrintGroupCode() {
		return PrintGroupCode;
	}

	public void setPrintGroupCode(String printGroupCode) {
		PrintGroupCode = printGroupCode;
	}
}
