package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ DiscountVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class DiscountVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private BigInteger discountIdx;	//일련번호
	private String discountName;	//할인명
	private String discountLevel;	//할인등급
	private float discountPercent;	//할인율
	private String startDate;		//적용기간 시작일
	private String endDate;			//적용기간 종료일
	
	public BigInteger getDiscountIdx() {
		return discountIdx;
	}
	public void setDiscountIdx(BigInteger discountIdx) {
		this.discountIdx = discountIdx;
	}
	
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public String getDiscountLevel() {
		return discountLevel;
	}
	public void setDiscountLevel(String discountLevel) {
		this.discountLevel = discountLevel;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
