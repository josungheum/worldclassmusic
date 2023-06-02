package qss.vo;

import java.math.BigInteger;


/**
 * <pre>
 * qss.vo 
 *    |_ OrderProdVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class OptionProdVo extends CommonVo{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger orderProdIdx;		//주문 상품 일련번호
	private BigInteger optionProdIdx;		//옵션 상품 일련번호
	private String optionProdName;			//상품명
	private String optionProdCode;			//옵션 상품 코드
	private String optionProdDisplayName;	//상품 표시명
	private String optionProdEnName;		//상품 영문명
	private String optionProdAmount;		//상품 정상금액
	private String optionProdDisplayAmount;	//상품 판매금액
	private String optionProdDiscountAmount;//상품 할인금액
	private BigInteger[] checkboxArr;		//체크된 배열값
	private String deviceCode;				//api전용



	
	public String getOptionProdCode() {
		return optionProdCode;
	}
	public void setOptionProdCode(String optionProdCode) {
		this.optionProdCode = optionProdCode;
	}
	public BigInteger getOrderProdIdx() {
		return orderProdIdx;
	}
	public void setOrderProdIdx(BigInteger orderProdIdx) {
		this.orderProdIdx = orderProdIdx;
	}


	public BigInteger getOptionProdIdx() {
		return optionProdIdx;
	}


	public void setOptionProdIdx(BigInteger optionProdIdx) {
		this.optionProdIdx = optionProdIdx;
	}


	public String getOptionProdName() {
		return optionProdName;
	}


	public void setOptionProdName(String optionProdName) {
		this.optionProdName = optionProdName;
	}


	public String getOptionProdDisplayName() {
		return optionProdDisplayName;
	}


	public void setOptionProdDisplayName(String optionProdDisplayName) {
		this.optionProdDisplayName = optionProdDisplayName;
	}


	public String getOptionProdEnName() {
		return optionProdEnName;
	}


	public void setOptionProdEnName(String optionProdEnName) {
		this.optionProdEnName = optionProdEnName;
	}


	public String getOptionProdAmount() {
		return optionProdAmount;
	}


	public void setOptionProdAmount(String optionProdAmount) {
		this.optionProdAmount = optionProdAmount;
	}


	public String getOptionProdDisplayAmount() {
		return optionProdDisplayAmount;
	}


	public void setOptionProdDisplayAmount(String optionProdDisplayAmount) {
		this.optionProdDisplayAmount = optionProdDisplayAmount;
	}


	public String getOptionProdDiscountAmount() {
		return optionProdDiscountAmount;
	}


	public void setOptionProdDiscountAmount(String optionProdDiscountAmount) {
		this.optionProdDiscountAmount = optionProdDiscountAmount;
	}


	public BigInteger[] getCheckboxArr() {
		return checkboxArr;
	}


	public void setCheckboxArr(BigInteger[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}


	public String getDeviceCode() {
		return deviceCode;
	}


	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}