package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ CalculateItemVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class CalculateItemVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private String itemType;		//거래구분
	private String calculateDate;	//정산일
	private int itemCount;			//거래건수
	private BigInteger itemMount;	//금액
	private int itemAfCount;		//거래건수(변경)
	private BigInteger itemAfMount;	//금액(변경)
	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getCalculateDate() {
		return calculateDate;
	}
	public void setCalculateDate(String calculateDate) {
		this.calculateDate = calculateDate;
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
		return itemAfCount;
	}
	public void setItemAfCount(int itemAfCount) {
		this.itemAfCount = itemAfCount;
	}
	public BigInteger getItemAfMount() {
		return itemAfMount;
	}
	public void setItemAfMount(BigInteger itemAfMount) {
		this.itemAfMount = itemAfMount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}