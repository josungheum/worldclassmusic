package qss.vo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;


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
public class OrderProdVo extends CommonVo{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger orderProdIdx;			//주문 상품 일련번호
	private BigInteger optionProdIdx;			//주문 옵션 상품 일련번호
	private String optionProdCode;				//주문 옵션 상품 코드
	private String prodName;					//상품명
	private String prodDisplayName;				//상품 표시명
	@NotNull(message = "Please enter prodCode")
	private String prodCode;					//상품 코드
	private String prodEnName;					//상품 영문명
	private String prodAmount;					//상품 정상금액
	private String prodDisplayAmount;			//상품 판매금액
	private BigInteger prodImage;				//상품 이미지
	private String prodImagePath;				//상품 이미지 경로
	private int prodStock;						//상품 수량
	private String eventProdYn;					//행사상품 여부
	private String eventProdScript;				//주문 상품 일련번호
	private String prodBarcode;					//주문 바코드
	private String weight;						//무게
	private String prodDiscountAmount;			//상품 할인금액
	private BigInteger[] checkboxArr;			//체크된 배열값
	private String resultOptionProdList;		//적용 옵션 상품 목록
	// 적용 옵션 상품 리스트
	private List<BigInteger> optionProdIdxList;	
	private List<OptionProdVo> optionProdList = new ArrayList<OptionProdVo>();
	
	@NotNull(message = "Please enter deviceCode")
	private String deviceCode;					//api전용
	
	
	
	public String getOptionProdCode() {
		return optionProdCode;
	}

	public void setOptionProdCode(String optionProdCode) {
		this.optionProdCode = optionProdCode;
	}

	public List<OptionProdVo> getOptionProdList() {
		return optionProdList;
	}

	public void setOptionProdList(List<OptionProdVo> optionProdList) {
		this.optionProdList = optionProdList;
	}

	public List<BigInteger> getOptionProdIdxList() {
		return optionProdIdxList;
	}

	public BigInteger getOptionProdIdx() {
		return optionProdIdx;
	}

	public void setOptionProdIdx(BigInteger optionProdIdx) {
		this.optionProdIdx = optionProdIdx;
	}

	public void setOptionProdIdxList(List<BigInteger> optionProdIdxList) {
		this.optionProdIdxList = optionProdIdxList;
	}

	public String getResultOptionProdList() {
		return resultOptionProdList;
	}

	public void setResultOptionProdList(String resultOptionProdList) {
		this.resultOptionProdList = resultOptionProdList;
	}
	
	public String getProdDiscountAmount() {
		return prodDiscountAmount;
	}
	public void setProdDiscountAmount(String prodDiscountAmount) {
		this.prodDiscountAmount = prodDiscountAmount;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public BigInteger[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(BigInteger[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public String getProdImagePath() {
		return prodImagePath;
	}
	public void setProdImagePath(String prodImagePath) {
		this.prodImagePath = prodImagePath;
	}
	public String getProdBarcode() {
		return prodBarcode;
	}
	public void setProdBarcode(String prodBarcode) {
		this.prodBarcode = prodBarcode;
	}
	public BigInteger getOrderProdIdx() {
		return orderProdIdx;
	}
	public void setOrderProdIdx(BigInteger orderProdIdx) {
		this.orderProdIdx = orderProdIdx;
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
	
	
	public String getProdAmount() {
		return prodAmount;
	}
	public void setProdAmount(String prodAmount) {
		this.prodAmount = prodAmount;
	}
	public String getProdDisplayAmount() {
		return prodDisplayAmount;
	}
	public void setProdDisplayAmount(String prodDisplayAmount) {
		this.prodDisplayAmount = prodDisplayAmount;
	}
	public BigInteger getProdImage() {
		return prodImage;
	}
	public void setProdImage(BigInteger prodImage) {
		this.prodImage = prodImage;
	}
	public int getProdStock() {
		return prodStock;
	}
	public void setProdStock(int prodStock) {
		this.prodStock = prodStock;
	}
	public String getEventProdYn() {
		return eventProdYn;
	}
	public void setEventProdYn(String eventProdYn) {
		this.eventProdYn = eventProdYn;
	}
	public String getEventProdScript() {
		return eventProdScript;
	}
	public void setEventProdScript(String eventProdScript) {
		this.eventProdScript = eventProdScript;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
}