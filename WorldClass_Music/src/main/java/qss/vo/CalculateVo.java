package qss.vo;

import java.math.BigInteger;
import java.util.List;

public class CalculateVo extends CommonVo {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String calculateYn; 		/*정산여부*/
	private String calculateDate;      	/*정산일*/
	private String itemType;           	/*거래구분*/
	private String itemTypeName;        /*거래구분 명*/
	private int itemCount;          	/*거래건수*/
	private BigInteger itemMount;       /*금액*/
	private int ItemAfCount;       		/*거래건수(변경)*/
	private BigInteger itemAfMount;     /*금액(변경)*/

	private String deviceName;        	/*단말명*/
	private String francName;			/*가맹점명*/

	private BigInteger orderAmt;     /*구매액*/
	private BigInteger discAmt;     /*할인액*/
	private BigInteger payAmt;     /*결제액*/

	private List<OrderMasterVo> orderMasterList;	// 주문마스터 리스트


	public BigInteger getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(BigInteger orderAmt) {
		this.orderAmt = orderAmt;
	}
	public BigInteger getDiscAmt() {
		return discAmt;
	}
	public void setDiscAmt(BigInteger discAmt) {
		this.discAmt = discAmt;
	}
	public BigInteger getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigInteger payAmt) {
		this.payAmt = payAmt;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFrancName() {
		return francName;
	}
	public void setFrancName(String francName) {
		this.francName = francName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getCalculateYn() {
		return calculateYn;
	}
	public void setCalculateYn(String calculateYn) {
		this.calculateYn = calculateYn;
	}
	public String getCalculateDate() {
		return calculateDate;
	}
	public void setCalculateDate(String calculateDate) {
		this.calculateDate = calculateDate != null ? calculateDate.replaceAll("-", "") : calculateDate;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public BigInteger getItemMount() {
		return itemMount;
	}
	public void setItemMount(BigInteger itemMount) {
		this.itemMount = itemMount;
	}
	public int getItemAfCount() {
		return ItemAfCount;
	}
	public void setItemAfCount(int itemAfCount) {
		ItemAfCount = itemAfCount;
	}
	public BigInteger getItemAfMount() {
		return itemAfMount;
	}
	public void setItemAfMount(BigInteger itemAfMount) {
		this.itemAfMount = itemAfMount;
	}
	public List<OrderMasterVo> getOrderMasterList() {
		return orderMasterList;
	}
	public void setOrderMasterList(List<OrderMasterVo> orderMasterList) {
		this.orderMasterList = orderMasterList;
	}


}