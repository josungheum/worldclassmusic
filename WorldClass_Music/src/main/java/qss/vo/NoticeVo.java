package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ NoticeVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class NoticeVo extends CommonVo{
	private static final long serialVersionUID = 1L;

	private BigInteger noticeIdx;		//일련번호
	private String title;				//제목
	private String content;				//내용
	private String isHtmlYn;			//html여부
	private String popupActiveYn;		//팝업활성화
	private String popupStartDate;		//팝업시작일자
	private String popupEndDate;		//팝업종료일자
	private String startDate;			//시작일자
	private String endDate;				//종료일자
	private String francIdx;			//가맹점일련번호
	private String brandOnlyYn;			//브랜드전용여부
	
	private BigInteger[] checkboxArr;		  // 체크된 배열값
	
	
	public BigInteger[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(BigInteger[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public String getFrancIdx() {
		return francIdx;
	}
	public void setFrancIdx(String francIdx) {
		this.francIdx = francIdx;
	}
	
	public BigInteger getNoticeIdx() {
		return noticeIdx;
	}
	public void setNoticeIdx(BigInteger noticeIdx) {
		this.noticeIdx = noticeIdx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsHtmlYn() {
		return isHtmlYn;
	}
	public void setIsHtmlYn(String isHtmlYn) {
		this.isHtmlYn = isHtmlYn;
	}
	public String getPopupActiveYn() {
		return popupActiveYn;
	}
	public void setPopupActiveYn(String popupActiveYn) {
		this.popupActiveYn = popupActiveYn;
	}
	public String getPopupStartDate() {
		return popupStartDate;
	}
	public void setPopupStartDate(String popupStartDate) {
		this.popupStartDate = popupStartDate;
	}
	public String getPopupEndDate() {
		return popupEndDate;
	}
	public void setPopupEndDate(String popupEndDate) {
		this.popupEndDate = popupEndDate;
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
	public String getBrandOnlyYn() {
		return brandOnlyYn;
	}
	public void setBrandOnlyYn(String brandOnlyYn) {
		this.brandOnlyYn = brandOnlyYn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
