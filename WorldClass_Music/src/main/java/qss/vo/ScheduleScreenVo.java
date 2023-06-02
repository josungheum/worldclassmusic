package qss.vo;

import java.math.BigInteger;
import java.util.List;


/**
 * <pre>
 * qss.vo
 *    |_ ScheduleScreenVo.java
 *
 * </pre>
 * @date : 2019. 1. 10. 오후 2:06:17
 * @version :
 * @author : ksh
 */
public class ScheduleScreenVo extends CommonVo{
	private static final long serialVersionUID = 1L;

	private BigInteger screenIdx;		//스크린 일련번호
	private String screenName;			//스크린 명
	private String playTime;			//플레이시간
	private String screenPlayTime;		//스크린 플레이시간
    private String rowCnt;       		/*행갯수*/
    private String colCnt;       		/*열갯수*/
    private String resolutionX;  		/*해상도X좌표*/
    private String resolutionY;  		/*해상도Y좌표*/
    private String screenType;			/*스크린 타입*/

	private List<ScreenLayerVo> screenLayerVoList;         	// ScreenLayerVo



	
	public String getScreenPlayTime() {
		return screenPlayTime;
	}
	public void setScreenPlayTime(String screenPlayTime) {
		this.screenPlayTime = screenPlayTime;
	}
	public String getScreenType() {
		return screenType;
	}
	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}
	public BigInteger getScreenIdx() {
		return screenIdx;
	}
	public void setScreenIdx(BigInteger screenIdx) {
		this.screenIdx = screenIdx;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getPlayTime() {
		return playTime;
	}
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(String rowCnt) {
		this.rowCnt = rowCnt;
	}
	public String getColCnt() {
		return colCnt;
	}
	public void setColCnt(String colCnt) {
		this.colCnt = colCnt;
	}
	public String getResolutionX() {
		return resolutionX;
	}
	public void setResolutionX(String resolutionX) {
		this.resolutionX = resolutionX;
	}
	public String getResolutionY() {
		return resolutionY;
	}
	public void setResolutionY(String resolutionY) {
		this.resolutionY = resolutionY;
	}
	public List<ScreenLayerVo> getScreenLayerVoList() {
		return screenLayerVoList;
	}
	public void setScreenLayerVoList(List<ScreenLayerVo> screenLayerVoList) {
		this.screenLayerVoList = screenLayerVoList;
	}



}
