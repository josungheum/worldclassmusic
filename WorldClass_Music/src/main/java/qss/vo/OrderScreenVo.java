package qss.vo;

import java.math.BigInteger;

public class OrderScreenVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	//주문화면 테이블
	private BigInteger orderScreenIdx;			//주문화면 일련번호
	private String orderScreenName;				//주문화면 명
	private String stateYn;						//주문상태 적용 여부
	private int targetCount;					//적용 단말기
	
	//주문화면 메뉴 테이블
	private BigInteger orderScreenMenuIdx;		//주문화면  메뉴 일련번호
	private BigInteger parentIdx;				//부모 일련번호
	private int menuLevel;						//메뉴등급
	private String menuName;					//메뉴명
	private String menuEngName;					//메뉴영문명
	
	//주문화면 메뉴 아이템 테이블
	private BigInteger orderProdIdx;			//주문상품 일련번호
	private BigInteger orderScreenMenuItemIdx;	//주문상품 메뉴 아이템 일련번호
	private String stateCode;//주문상품 메뉴 아이템 상태코드
	
	//주문화면 메뉴 아이템 조회용
	private String prodName;					//상품명
	private String prodDisplayName;				//상품 표시명
	private String prodCode;					//상품 코드
	private String prodEnName;					//상품 영문명
	private BigInteger prodAmount;				//상품 가격
	private BigInteger prodDisplayAmount;		//할인 상품 가격
	private BigInteger prodDiscountAmount;		//할인 상품 가격
	
	
	private BigInteger[] checkboxArr;		  		// 체크된 배열값
	private String menuJson;		     		// 추가된 메뉴 저장
	private String prodList;					// 메뉴에 추가되는 상품 저장
	
	
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public BigInteger getProdDiscountAmount() {
		return prodDiscountAmount;
	}
	public void setProdDiscountAmount(BigInteger prodDiscountAmount) {
		this.prodDiscountAmount = prodDiscountAmount;
	}
	public BigInteger getProdDisplayAmount() {
		return prodDisplayAmount;
	}
	public void setProdDisplayAmount(BigInteger prodDisplayAmount) {
		this.prodDisplayAmount = prodDisplayAmount;
	}
	public String getProdList() {
		return prodList;
	}
	public void setProdList(String prodList) {
		this.prodList = prodList;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
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
	public String getMenuJson() {
		return menuJson;
	}
	public void setMenuJson(String menuJson) {
		this.menuJson = menuJson;
	}
	
	public BigInteger[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(BigInteger[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public int getTargetCount() {
		return targetCount;
	}
	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}
	public String getStateYn() {
		return stateYn;
	}
	public void setStateYn(String stateYn) {
		this.stateYn = stateYn;
	}
	public BigInteger getOrderProdIdx() {
		return orderProdIdx;
	}
	public void setOrderProdIdx(BigInteger orderProdIdx) {
		this.orderProdIdx = orderProdIdx;
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
	public String getOrderScreenName() {
		return orderScreenName;
	}
	public void setOrderScreenName(String orderScreenName) {
		this.orderScreenName = orderScreenName;
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
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuEngName() {
		return menuEngName;
	}
	public void setMenuEngName(String menuEngName) {
		this.menuEngName = menuEngName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
}