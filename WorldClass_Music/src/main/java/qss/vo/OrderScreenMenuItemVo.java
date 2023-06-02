/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package qss.vo; 

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ OrderScreenMenuVo.java
 * 
 * </pre>
 * @date : 2019. 1. 24. 오전 11:36:58
 * @version : 
 * @author : p
 */
public class OrderScreenMenuItemVo {

	private String prodName;					//상품명
	private String prodDisplayName;				//상품 표시명
	private String prodCode;					//상품 코드
	private String prodEnName;					//상품 영문명
	private BigInteger prodAmount;				//상품 가격
	private BigInteger prodStock;				//상품 재고
	private BigInteger orderScreenMenuIdx;		//주문화면  메뉴 일련번호
	private BigInteger orderScreenMenuItemIdx;	//주문화면  메뉴 일련번호
	private BigInteger orderScreenIdx;			//주문화면 일련번호
	private BigInteger orderProdIdx;			//주문화면 일련번호
	private String stateCode;					//주문화면 메뉴 상태코드
	
	
	
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public BigInteger getProdStock() {
		return prodStock;
	}
	public void setProdStock(BigInteger prodStock) {
		this.prodStock = prodStock;
	}
	public String getProdDisplayName() {
		return prodDisplayName;
	}
	public void setProdDisplayName(String prodDisplayName) {
		this.prodDisplayName = prodDisplayName;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdEnName() {
		return prodEnName;
	}
	public void setProdEnName(String prodEnName) {
		this.prodEnName = prodEnName;
	}
	public BigInteger getProdAmount() {
		return prodAmount;
	}
	public void setProdAmount(BigInteger prodAmount) {
		this.prodAmount = prodAmount;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public BigInteger getOrderScreenMenuIdx() {
		return orderScreenMenuIdx;
	}
	public void setOrderScreenMenuIdx(BigInteger orderScreenMenuIdx) {
		this.orderScreenMenuIdx = orderScreenMenuIdx;
	}
	public BigInteger getOrderScreenMenuItemIdx() {
		return orderScreenMenuItemIdx;
	}
	public void setOrderScreenMenuItemIdx(BigInteger orderScreenMenuItemIdx) {
		this.orderScreenMenuItemIdx = orderScreenMenuItemIdx;
	}
	public BigInteger getOrderScreenIdx() {
		return orderScreenIdx;
	}
	public void setOrderScreenIdx(BigInteger orderScreenIdx) {
		this.orderScreenIdx = orderScreenIdx;
	}
	public BigInteger getOrderProdIdx() {
		return orderProdIdx;
	}
	public void setOrderProdIdx(BigInteger orderProdIdx) {
		this.orderProdIdx = orderProdIdx;
	}
	
	
	
	
	
	
	
}
