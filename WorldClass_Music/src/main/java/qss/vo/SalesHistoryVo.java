package qss.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class SalesHistoryVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private BigInteger orderMasterIdx;	/*주문마스터 일련번호*/
	private String receiptNo;			/*영수번호*/
	private String orderDate;			/*주문일*/
	private String orderTime;			/*주문시간*/
	private BigDecimal orderAmt;		/*구매액*/
	private BigDecimal discAmt;			/*할인액*/
	private BigDecimal payAmt;			/*결제액*/
	private BigDecimal cardAmt;			/*카드사용금액*/
	private String payType;				/*결제종류*/
	private String payProcCode;			/*결제진행코드*/
	private String pointTypeCode;		/*포인트구분코드*/
	private BigDecimal pointUseAmt;		/*포인트 사용금액*/
	private BigDecimal pointSaveAmt;	/*포인트 적립금액*/
	private String calculateDate;       /*정산일*/
	private String calculateYn;			/*정산여부*/
	private int orderCount;				/*주문횟수*/

	private String deviceName;       	/*단말 명*/

	private String searchDay;       	/*검색 시작 일*/
	private String searchEndDay;       	/*검색 종료 일*/
	private String codeGroup;	  		/* 코드그룹*/

	// SalesItemList
	private List<OrderItemVo> orderItemVo; /*주문아이템 리스트*/



	public String getCodeGroup() {
		return codeGroup;
	}

	public void setCodeGroup(String codeGroup) {
		this.codeGroup = codeGroup;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public BigInteger getOrderMasterIdx() {
		return orderMasterIdx;
	}

	public void setOrderMasterIdx(BigInteger orderMasterIdx) {
		this.orderMasterIdx = orderMasterIdx;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public BigDecimal getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(BigDecimal discAmt) {
		this.discAmt = discAmt;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayProcCode() {
		return payProcCode;
	}

	public void setPayProcCode(String payProcCode) {
		this.payProcCode = payProcCode;
	}

	public String getPointTypeCode() {
		return pointTypeCode;
	}

	public void setPointTypeCode(String pointTypeCode) {
		this.pointTypeCode = pointTypeCode;
	}

	public BigDecimal getCardAmt() {
		return cardAmt;
	}

	public void setCardAmt(BigDecimal cardAmt) {
		this.cardAmt = cardAmt;
	}

	public BigDecimal getPointUseAmt() {
		return pointUseAmt;
	}

	public void setPointUseAmt(BigDecimal pointUseAmt) {
		this.pointUseAmt = pointUseAmt;
	}

	public BigDecimal getPointSaveAmt() {
		return pointSaveAmt;
	}

	public void setPointSaveAmt(BigDecimal pointSaveAmt) {
		this.pointSaveAmt = pointSaveAmt;
	}

	public String getCalculateDate() {
		return calculateDate;
	}

	public void setCalculateDate(String calculateDate) {
		this.calculateDate = calculateDate;
	}

	public String getCalculateYn() {
		return calculateYn;
	}

	public void setCalculateYn(String calculateYn) {
		this.calculateYn = calculateYn;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSearchDay() {
		return searchDay;
	}

	public void setSearchDay(String searchDay) {
		this.searchDay = searchDay;
	}

	public String getSearchEndDay() {
		return searchEndDay;
	}

	public void setSearchEndDay(String searchEndDay) {
		this.searchEndDay = searchEndDay;
	}


	public List<OrderItemVo> getOrderItemVo() {
		return orderItemVo;
	}

	public void setOrderItemVo(List<OrderItemVo> orderItemVo) {
		this.orderItemVo = orderItemVo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}