package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo
 *    |_ FranchiseeVo.java
 *
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version :
 * @author : khk
 */
public class FranchiseeVo extends CommonVo {
	private static final long serialVersionUID = 1L;


	private String francCode;			// 사이트코드
	private String francName;			// 사이트명
	private String sapMsCd;				// 사업자코드
	private BigInteger mainImg;			// 대표이미지
	private String mainimgPath;			// 대표이미지 경로
	private String francAddr;			// 사이트주소
	private String francAddrDetail;		// 사이트주소상세
	private String telNum;				// 전화번호
	private String salesStartTime;		// 영업시작시간
	private String salesEndTime;		// 영업종료시간
	private String oderStartTime;		// 주문시작시간
	private String oderEndTime;			// 주문종료시간
	private String allDaySalesYn;		// 종일영업여부
	private String closedDayInfo;		// 휴무일
	private String categoryType;		// 업종
	private String categoryTypeName;	// 업종 명
	private String memo;				// 메모
	private String francPaymentType01;	// 결제 종류
	private String francPaymentType02;	// 포인트 종류
	
	//단말
	private String deviceCode;			// 단말 코드
	private String deviceName;			// 단말 명
	private String deviceType;			// 단말 종류
	private String colCountType;		// 단말에따른 열갯수(스탠드형, 테이블형만 사용)
	private BigInteger[] checkboxArr;	// 체크된 배열값
	private String returnFrancIdx;		// 반환 키
	private String deviceResType;
	private String deviceSiteType;
	private String deviceTransType;

	//그룹
	private String groupName;
	private String parentName;
    private String parentGroupIdx;
	private int groupDepth;
	private String returnGroupIdx;		// 반환 키
	private int deviceCnt;
	private String[] strArr;
	private String realGroupIdx;


	public String getReturnFrancIdx() {
		return returnFrancIdx;
	}
	public void setReturnFrancId(String returnFrancIdx) {
		this.returnFrancIdx = returnFrancIdx;
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
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getMainimgPath() {
		return mainimgPath;
	}
	public void setMainimgPath(String mainimgPath) {
		this.mainimgPath = mainimgPath;
	}
	public String getClosedDayInfo() {
		return closedDayInfo;
	}
	public void setClosedDayInfo(String closedDayInfo) {
		this.closedDayInfo = closedDayInfo;
	}
	public String getFrancPaymentType01() {
		return francPaymentType01;
	}
	public void setFrancPaymentType01(String francPaymentType01) {
		this.francPaymentType01 = francPaymentType01;
	}
	public String getFrancPaymentType02() {
		return francPaymentType02;
	}
	public void setFrancPaymentType02(String francPaymentType02) {
		this.francPaymentType02 = francPaymentType02;
	}
	
	public String getFrancCode() {
		return francCode;
	}
	public void setFrancCode(String francCode) {
		this.francCode = francCode;
	}
	public String getFrancName() {
		return francName;
	}
	public void setFrancName(String francName) {
		this.francName = francName;
	}

	public String getSapMsCd() {
		return sapMsCd;
	}
	public void setSapMsCd(String sapMsCd) {
		this.sapMsCd = sapMsCd;
	}
	public BigInteger getMainImg() {
		return mainImg;
	}
	public void setMainImg(BigInteger mainImg) {
		this.mainImg = mainImg;
	}
	public String getFrancAddr() {
		return francAddr;
	}
	public void setFrancAddr(String francAddr) {
		this.francAddr = francAddr;
	}
	public String getFrancAddrDetail() {
		return francAddrDetail;
	}
	public void setFrancAddrDetail(String francAddrDetail) {
		this.francAddrDetail = francAddrDetail;
	}
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public String getSalesStartTime() {
		return salesStartTime;
	}
	public void setSalesStartTime(String salesStartTime) {
		this.salesStartTime = salesStartTime;
	}
	public String getSalesEndTime() {
		return salesEndTime;
	}
	public void setSalesEndTime(String salesEndTime) {
		this.salesEndTime = salesEndTime;
	}
	public String getOderStartTime() {
		return oderStartTime;
	}
	public void setOderStartTime(String oderStartTime) {
		this.oderStartTime = oderStartTime;
	}
	public String getOderEndTime() {
		return oderEndTime;
	}
	public void setOderEndTime(String oderEndTime) {
		this.oderEndTime = oderEndTime;
	}
	public String getAllDaySalesYn() {
		return allDaySalesYn;
	}
	public void setAllDaySalesYn(String allDaySalesYn) {
		this.allDaySalesYn = allDaySalesYn;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryTypeName() {
		return categoryTypeName;
	}
	public void setCategoryTypeName(String categoryTypeName) {
		this.categoryTypeName = categoryTypeName;
	}



	public String getColCountType() {
		return colCountType;
	}
	public void setColCountType(String colCountType) {
		this.colCountType = colCountType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupDepth() {
		return groupDepth;
	}
	public void setGroupDepth(int groupDepth) {
		this.groupDepth = groupDepth;
	}
	public String getReturnGroupIdx() {
		return returnGroupIdx;
	}
	public void setReturnGroupIdx(String returnGroupIdx) {
		this.returnGroupIdx = returnGroupIdx;
	}
	public int getDeviceCnt() {
		return deviceCnt;
	}
	public void setDeviceCnt(int deviceCnt) {
		this.deviceCnt = deviceCnt;
	}
	public String getDeviceResType() {
		return deviceResType;
	}
	public void setDeviceResType(String deviceResType) {
		this.deviceResType = deviceResType;
	}
	public String getDeviceSiteType() {
		return deviceSiteType;
	}
	public void setDeviceSiteType(String deviceSiteType) {
		this.deviceSiteType = deviceSiteType;
	}
	public String getDeviceTransType() {
		return deviceTransType;
	}
	public void setDeviceTransType(String deviceTransType) {
		this.deviceTransType = deviceTransType;
	}
	public String[] getStrArr() {
		return strArr;
	}
	public void setStrArr(String[] strArr) {
		this.strArr = strArr;
	}
	public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    public String getRealGroupIdx() {
        return realGroupIdx;
    }
    public void setRealGroupIdx(String realGroupIdx) {
        this.realGroupIdx = realGroupIdx;
    }
    public String getParentGroupIdx() {
        return parentGroupIdx;
    }
    public void setParentGroupIdx(String parentGroupIdx) {
        this.parentGroupIdx = parentGroupIdx;
    }
}