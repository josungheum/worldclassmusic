package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ BrandPaymentTypeVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class BrandPaymentTypeVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private BigInteger payTypeIdx;	//일련번호
	private String payType;			//코드
	private String payTypeGroup;	//타입 구분
	private String brandOnlyYn;		//브랜드전용여부
	
	
	public String getPayTypeGroup() {
		return payTypeGroup;
	}
	public void setPayTypeGroup(String payTypeGroup) {
		this.payTypeGroup = payTypeGroup;
	}
	
	public BigInteger getPayTypeIdx() {
		return payTypeIdx;
	}
	public void setPayTypeIdx(BigInteger payTypeIdx) {
		this.payTypeIdx = payTypeIdx;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getBrandOnlyYn() {
		return brandOnlyYn;
	}
	public void setBrandOnlyYn(String brandOnlyYn) {
		this.brandOnlyYn = brandOnlyYn;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
}