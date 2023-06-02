package qss.vo;

public class DiscountResultVo {
	private String DiscountName;
	private String DiscountLevel;
	private double DiscountPercent;
	private String StartDate;
	private String EndDate;

	public String getDiscountName() {
		return DiscountName;
	}

	public void setDiscountName(String discountName) {
		DiscountName = discountName;
	}

	public String getDiscountLevel() {
		return DiscountLevel;
	}

	public void setDiscountLevel(String discountLevel) {
		DiscountLevel = discountLevel;
	}

	public double getDiscountPercent() {
		return DiscountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		DiscountPercent = discountPercent;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}
}
