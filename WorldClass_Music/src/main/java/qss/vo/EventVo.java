package qss.vo;

import java.math.BigInteger;
import java.util.List;

/**
 * 
 * @FileName EventVo.java
 * @Project SignageCMS
 * @Date 2020. 3. 13.
 * @author qvoss
 *
 */
public class EventVo extends CommonVo {
	private static final long serialVersionUID = -1985196902974963527L;
	
	/*
	 * Event
	 */
	private BigInteger eventIdx;
	private String dispNm1;
	private String dispNm2;
	private String startDate;
	private String endDate;
	private BigInteger thumbImg;
	
	/*
	 * Event_List
	 */
	private BigInteger fileContentIdx;
	private String eventType;
	
	/**
	 * DESC
	 */
	private String thumbnailPath;
	private BigInteger[] checkboxArr;
	private List<EventVo> horiList;
	private List<EventVo> vertList;
	private List<EventVo> imageList;
	private String horiXML;
	private String vertXML;
	
	public BigInteger getEventIdx() {
		return eventIdx;
	}
	public void setEventIdx(BigInteger eventIdx) {
		this.eventIdx = eventIdx;
	}
	public String getDispNm1() {
		return dispNm1;
	}
	public void setDispNm1(String dispNm1) {
		this.dispNm1 = dispNm1;
	}
	public String getDispNm2() {
		return dispNm2;
	}
	public void setDispNm2(String dispNm2) {
		this.dispNm2 = dispNm2;
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
	public BigInteger getThumbImg() {
		return thumbImg;
	}
	public void setThumbImg(BigInteger thumbImg) {
		this.thumbImg = thumbImg;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public BigInteger[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(BigInteger[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public BigInteger getFileContentIdx() {
		return fileContentIdx;
	}
	public void setFileContentIdx(BigInteger fileContentIdx) {
		this.fileContentIdx = fileContentIdx;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getHoriXML() {
		return horiXML;
	}
	public void setHoriXML(String horiXML) {
		this.horiXML = horiXML;
	}
	public String getVertXML() {
		return vertXML;
	}
	public void setVertXML(String vertXML) {
		this.vertXML = vertXML;
	}
	public List<EventVo> getHoriList() {
		return horiList;
	}
	public void setHoriList(List<EventVo> horiList) {
		this.horiList = horiList;
	}
	public List<EventVo> getVertList() {
		return vertList;
	}
	public void setVertList(List<EventVo> vertList) {
		this.vertList = vertList;
	}
	public List<EventVo> getImageList() {
		return imageList;
	}
	public void setImageList(List<EventVo> imageList) {
		this.imageList = imageList;
	}
}