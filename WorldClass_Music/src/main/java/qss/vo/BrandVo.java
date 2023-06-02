package qss.vo;

import java.math.BigInteger;


/**
 * <pre>
 * qss.vo
 *    |_ BrandVO.java
 *
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version :
 * @author : khk
 */
public class BrandVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private String brandCode;				//브랜드코드
	private String name;					//이름
	private int orderSheetType;				//가맹점 수
	private BigInteger logoFile;			//로고파일(파일 테이블과 연결)
	private BigInteger brandImg;			//브랜드이미지파일(파일 테이블과 연결)

	private String brandPaymentType01;		//결제 종류
	private String brandPaymentType02;		//포인트 종류
	private String brandDeviceType;			//단말 종류
	private String payTypeGroup;			//결제, 포인트인지 구분값
	
	//할인율
	private BigInteger discountIdx;			//일련번호
	private String discountName;			//할인명
	private String discountLevel;			//할인등급
	private float discountPercent;			//할인율
	private String startDate;				//적용기간 시작일
	private String endDate;					//적용기간 종료일

	private String[] checkboxArr;		  	//체크된 배열값

	private String returnBrandIdx;			//리턴 키값


	/**
	 * file
	 */
	private String brandThumbnailPath;			// 브랜드 대표 썸네일 이미지
	private String brandMainImgPath;			// 브랜드 대표 메인 이미지


	public String getPayTypeGroup() {
		return payTypeGroup;
	}
	public void setPayTypeGroup(String payTypeGroup) {
		this.payTypeGroup = payTypeGroup;
	}
	public String getReturnBrandIdx() {
		return returnBrandIdx;
	}
	public void setReturnBrandIdx(String returnBrandIdx) {
		this.returnBrandIdx = returnBrandIdx;
	}
	public String[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(String[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}

	public BigInteger getDiscountIdx() {
		return discountIdx;
	}
	public void setDiscountIdx(BigInteger discountIdx) {
		this.discountIdx = discountIdx;
	}
	public String getDiscountName() {
		return discountName;
	}
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	public String getDiscountLevel() {
		return discountLevel;
	}
	public void setDiscountLevel(String discountLevel) {
		this.discountLevel = discountLevel;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBrandPaymentType01() {
		return brandPaymentType01;
	}
	public void setBrandPaymentType01(String brandPaymentType01) {
		this.brandPaymentType01 = brandPaymentType01;
	}
	public String getBrandPaymentType02() {
		return brandPaymentType02;
	}
	public void setBrandPaymentType02(String brandPaymentType02) {
		this.brandPaymentType02 = brandPaymentType02;
	}
	public BigInteger getLogoFile() {
		return logoFile;
	}
	public void setLogoFile(BigInteger logoFile) {
		this.logoFile = logoFile;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrderSheetType() {
		return orderSheetType;
	}
	public void setOrderSheetType(int orderSheetType) {
		this.orderSheetType = orderSheetType;
	}

	public BigInteger getBrandImg() {
		return brandImg;
	}
	public void setBrandImg(BigInteger brandImg) {
		this.brandImg = brandImg;
	}

	public String getBrandThumbnailPath() {
		return brandThumbnailPath;
	}

	public void setBrandThumbnailPath(String brandThumbnailPath) {
		this.brandThumbnailPath = brandThumbnailPath;
	}

	public String getBrandMainImgPath() {
		return brandMainImgPath;
	}
	public void setBrandMainImgPath(String brandMainImgPath) {
		this.brandMainImgPath = brandMainImgPath;
	}

	public String getBrandDeviceType() {
		return brandDeviceType;
	}
	public void setBrandDeviceType(String brandDeviceType) {
		this.brandDeviceType = brandDeviceType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}