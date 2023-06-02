package qss.vo;

public class MobileSalesOrderItemVo {
	private String prodType;
	private int quantity;
	private int unitPrice;
	private int parentProdIdx;
	private String displayName;

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getParentProdIdx() {
		return parentProdIdx;
	}

	public void setParentProdIdx(int parentProdIdx) {
		this.parentProdIdx = parentProdIdx;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SalesOrderItemVo [prodType=");
		builder.append(prodType);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unitPrice=");
		builder.append(unitPrice);
		builder.append(", parentProdIdx=");
		builder.append(parentProdIdx);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append("]");
		return builder.toString();
	}
}
