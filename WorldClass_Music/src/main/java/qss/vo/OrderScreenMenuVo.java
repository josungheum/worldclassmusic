/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package qss.vo; 

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
public class OrderScreenMenuVo {

	private BigInteger orderScreenMenuIdx;		//주문화면  메뉴 일련번호
	private BigInteger parentIdx;				//부모 일련번호
//	private int menuLevel;						//메뉴등급
	private String menuName;					//메뉴명
//	private String menuEngName;					//메뉴영문명
	private BigInteger orderScreenIdx;			//주문화면 일련번호
	private List<OrderScreenMenuItemVo> orderScreenMenuItemList = new ArrayList<OrderScreenMenuItemVo>();
		
	public List<OrderScreenMenuItemVo> getOrderScreenMenuItemList() {
		return orderScreenMenuItemList;
	}
	public void setOrderScreenMenuItemList(List<OrderScreenMenuItemVo> orderScreenMenuItemList) {
		this.orderScreenMenuItemList = orderScreenMenuItemList;
	}
	
	public BigInteger getOrderScreenMenuIdx() {
		return orderScreenMenuIdx;
	}
	public void setOrderScreenMenuIdx(BigInteger orderScreenMenuIdx) {
		this.orderScreenMenuIdx = orderScreenMenuIdx;
	}
	public BigInteger getParentIdx() {
		return parentIdx;
	}
	public void setParentIdx(BigInteger parentIdx) {
		this.parentIdx = parentIdx;
	}
//	public int getMenuLevel() {
//		return menuLevel;
//	}
//	public void setMenuLevel(int menuLevel) {
//		this.menuLevel = menuLevel;
//	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
//	public String getMenuEngName() {
//		return menuEngName;
//	}
//	public void setMenuEngName(String menuEngName) {
//		this.menuEngName = menuEngName;
//	}
	public BigInteger getOrderScreenIdx() {
		return orderScreenIdx;
	}
	public void setOrderScreenIdx(BigInteger orderScreenIdx) {
		this.orderScreenIdx = orderScreenIdx;
	}
	
	
	
}
