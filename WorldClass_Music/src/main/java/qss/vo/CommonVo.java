package qss.vo;

import java.io.Serializable;
import java.math.BigInteger;


public class CommonVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String idx;				//도메인+브랜드+가맹점 일련번호
	private String domainIdx;		//도메인 일련번호
	private String brandIdx;		//브랜드 일련번호
	private String francIdx;		//가맹점 일련번호
	private String groupIdx;		//그룹 일련번호
	private BigInteger deviceIdx;	//장치 일련번호
	private String activeYn;		//활성화여부
	private int orderSeq;			//정렬순서
	private String delYn;			//삭제 여부
	private String regUser;			//등록자
	private String regDate;			//등록일
	private String modUser;			//수정자
	private String modDate;			//수정일
	private String sessionAdminType;//관리자 타입
	private String sessionDomainIdx;//관리자 도메인정보
	private String sessionBrandIdx;//관리자 브랜드정보
	private String sessionFrancIdx;//관리자 매장정보

	/**
	 * DataTable
	 */
	private int iDisplayStart;		//페이징 시작
	private int iDisplayLength;		//페이징 길이
	private String sSearch;			//검색어
	private int rowIdx;				//번호

	/**
	 * file
	 */
	private String thumbnailPath;	//썸네일 경로

	private String caseString = "";
	private int rowCount = 0;		// 데이타  row count
	private String serchKey;        // 검색 key
	private String sendUrl;         // 리턴 url

	//스케줄 비전
	private String modReason;		//사유
	private BigInteger returnIdx;	//반환고유키

	// 사용자 이력 컬럼
	private String ctrlName;		//사용메뉴명
	private String actionName;		//동작명
	private String id;				//id
	private String ip;				//ip
	private String detailInfo;		//상세정보

	// 파일 실제 다운경로
	private String realPath;		//파일 실제 다운경로
	private String clientPath;		//클라이언트 경로

	private String sSortDir_0;		//SORT 타입 DESC, ASC
	private String sSortName;		//sort 대상

	private String defaultOrderName;	//기본 sort 대상
	private String defaultOrderType;	//기본 sort 타입 DESC, ASC

	private String searhSystemCode;		//조회코드 값

	private BigInteger newIdx;
	
	private String searchStart;
	private String searchEnd;
	private int cntTotal;

	public String getSearhSystemCode() {
		return searhSystemCode;
	}
	public void setSearhSystemCode(String searhSystemCode) {
		this.searhSystemCode = searhSystemCode;
	}
	public String getDefaultOrderName() {
		return cameTounderscoreStyle(defaultOrderName);
	}
	public void setDefaultOrderName(String defaultOrderName) {
		this.defaultOrderName = defaultOrderName;
	}
	public String getDefaultOrderType() {
		return defaultOrderType;
	}
	public void setDefaultOrderType(String defaultOrderType) {
		this.defaultOrderType = defaultOrderType;
	}
	public String getsSortDir_0() {
		return sSortDir_0;
	}
	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	public String getsSortName() {
		return cameTounderscoreStyle(sSortName);
	}
	public void setsSortName(String sSortName) {
		this.sSortName = sSortName;
	}

	public String getClientPath() {
		return clientPath;
	}
	public void setClientPath(String clientPath) {
		this.clientPath = clientPath;
	}
	public String getRealPath() {
		return realPath;
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getCtrlName() {
		return ctrlName;
	}
	public void setCtrlName(String ctrlName) {
		this.ctrlName = ctrlName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDetailInfo() {
		return detailInfo;
	}
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}
	public BigInteger getReturnIdx() {
		return returnIdx;
	}
	public void setReturnIdx(BigInteger returnIdx) {
		this.returnIdx = returnIdx;
	}
	public String getModReason() {
		return modReason;
	}
	public void setModReason(String modReason) {
		this.modReason = modReason;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public int getRowIdx() {
		return rowIdx;
	}
	public void setRowIdx(int rowIdx) {
		this.rowIdx = rowIdx;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public int getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getBrandIdx() {
		return brandIdx;
	}
	public void setBrandIdx(String brandIdx) {
		this.brandIdx = brandIdx;
	}
	public String getDomainIdx() {
		return domainIdx;
	}
	public void setDomainIdx(String domainIdx) {
		this.domainIdx = domainIdx;
	}

	public BigInteger getDeviceIdx() {
		return deviceIdx;
	}
	public void setDeviceIdx(BigInteger deviceIdx) {
		this.deviceIdx = deviceIdx;
	}
	public String getActiveYn() {
		return activeYn;
	}
	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModUser() {
		return modUser;
	}
	public void setModUser(String modUser) {
		this.modUser = modUser;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFrancIdx() {
		return francIdx;
	}
	public void setFrancIdx(String francIdx) {
		this.francIdx = francIdx;
	}
	public String getCaseString() {
		return caseString;
	}
	public void setCaseString(String caseString) {
		this.caseString = caseString;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public String getSerchKey() {
		return serchKey;
	}
	public void setSerchKey(String serchKey) {
		this.serchKey = serchKey;
	}
	public String getSendUrl() {
		return sendUrl;
	}
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getSessionAdminType() {
		return sessionAdminType;
	}
	public void setSessionAdminType(String sessionAdminType) {
		this.sessionAdminType = sessionAdminType;
	}
	public String getSessionDomainIdx() {
		return sessionDomainIdx;
	}
	public void setSessionDomainIdx(String sessionDomainIdx) {
		this.sessionDomainIdx = sessionDomainIdx;
	}
	public String getSessionBrandIdx() {
		return sessionBrandIdx;
	}
	public void setSessionBrandIdx(String sessionBrandIdx) {
		this.sessionBrandIdx = sessionBrandIdx;
	}
	public String getSessionFrancIdx() {
		return sessionFrancIdx;
	}
	public void setSessionFrancIdx(String sessionFrancIdx) {
		this.sessionFrancIdx = sessionFrancIdx;
	}


	/**
	 * <pre>
	 * 1. 개요 :
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : cameTounderscoreStyle
	 * @date : 2019. 6. 4.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 4.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param str
	 * @return
	 */
	public static String cameTounderscoreStyle(String str) {
		String value = "";
		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1_$2";

		if(null == str || "".equals(str)) {
			value = "";
		}else {
			value = str.replaceAll(regex, replacement).toUpperCase();
		}

		return value;
	}
	public BigInteger getNewIdx() {
		return newIdx;
	}
	public void setNewIdx(BigInteger newIdx) {
		this.newIdx = newIdx;
	}
	public String getGroupIdx() {
		return groupIdx;
	}
	public void setGroupIdx(String groupIdx) {
		this.groupIdx = groupIdx;
	}
    public String getSearchStart() {
        return searchStart;
    }
    public void setSearchStart(String searchStart) {
        this.searchStart = searchStart;
    }
    public String getSearchEnd() {
        return searchEnd;
    }
    public void setSearchEnd(String searchEnd) {
        this.searchEnd = searchEnd;
    }
    public int getCntTotal() {
        return cntTotal;
    }
    public void setCntTotal(int cntTotal) {
        this.cntTotal = cntTotal;
    }

}