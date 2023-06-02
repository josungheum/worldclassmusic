package qss.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SalesOrderItemVo  extends CommonVo{
	private static final long serialVersionUID = 1L;

	private BigInteger orderMasterIdx;	/*주문마스터 일련번호*/
	private BigInteger orderItemIdx;	/*주문아이템 일련번호*/
	private String prodType;			/*상품유형*/
	private BigDecimal orderProdIdx;	/*상품일련번호*/
	private int quantity;				/*수량*/
	private BigDecimal unitPrice;		/*가격*/
	private String  prodDisplayName;	/*상품 표시명*/
	public BigInteger getOrderMasterIdx() {
		return orderMasterIdx;
	}
	public void setOrderMasterIdx(BigInteger orderMasterIdx) {
		this.orderMasterIdx = orderMasterIdx;
	}
	public BigInteger getOrderItemIdx() {
		return orderItemIdx;
	}
	public void setOrderItemIdx(BigInteger orderItemIdx) {
		this.orderItemIdx = orderItemIdx;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public BigDecimal getOrderProdIdx() {
		return orderProdIdx;
	}
	public void setOrderProdIdx(BigDecimal orderProdIdx) {
		this.orderProdIdx = orderProdIdx;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getProdDisplayName() {
		return prodDisplayName;
	}
	public void setProdDisplayName(String prodDisplayName) {
		this.prodDisplayName = prodDisplayName;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
