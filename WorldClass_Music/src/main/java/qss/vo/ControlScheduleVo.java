package qss.vo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ControlScheduleVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	// 메인 스케줄(schedule List)
	private BigInteger controlScheduleIdx;		// 제어 스케줄 일련번호
	private String controlScheduleName;			// 제어 스케줄명
	private String startDate;					// 시작일자
	private String endDate;						// 종료일자
	private String startTime;					// 시작시간
	
	private String dayOfWeek;					// 선택 주
	private int displayBrightness;				// 밝기
	private int volume;							// 볼륨
	private String displayPower;				// 화면 꺼짐
	private String playerReboot;				// 플레이어 재부팅
	private String reboot;						// 재부팅
	
	private List<BigInteger> deviceList;		// 적용 단말 리스트(deviceIdx List)

	private String resultDeviceList;             // 적용 단말 리스트

	private BigInteger scheduleVersionIdx;		// 스케줄버전 일련번호

	// 조회 Screen List
	private BigInteger screenIdx;
	private String playTime;
	private String screenName;
	private BigInteger scheduleScreenIdx;

	// map DATA
	private Map<String, Object> mapData;

	// 저장 결과
	private int resultCode;

	// 배열 처리
	private String[] checkboxArr;		  // 체크된 배열값
	
	private String screenType;


	private String searchDvType;
	private String searchDvRes;
	private String searchDvSite;
	
	private String adminType;
	private String controlType;
	private String controlValue;

	public int getDisplayBrightness() {
		return displayBrightness;
	}

	public void setDisplayBrightness(int displayBrightness) {
		this.displayBrightness = displayBrightness;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getDisplayPower() {
		return displayPower;
	}

	public void setDisplayPower(String displayPower) {
		this.displayPower = displayPower;
	}

	public String getPlayerReboot() {
		return playerReboot;
	}

	public void setPlayerReboot(String playerReboot) {
		this.playerReboot = playerReboot;
	}

	public String getReboot() {
		return reboot;
	}

	public void setReboot(String reboot) {
		this.reboot = reboot;
	}

	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}

	

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	public List<BigInteger> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<BigInteger> deviceList) {
		this.deviceList = deviceList;
	}

	public void setResultDeviceList(String resultDeviceList) {
		this.resultDeviceList = resultDeviceList;
	}

	public Map<String, Object> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String[] getCheckboxArr() {
		return checkboxArr;
	}

	public void setCheckboxArr(String[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}

	public String getResultDeviceList() {
		return resultDeviceList;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public BigInteger getScheduleScreenIdx() {
		return scheduleScreenIdx;
	}

	public void setScheduleScreenIdx(BigInteger scheduleScreenIdx) {
		this.scheduleScreenIdx = scheduleScreenIdx;
	}

	

	public BigInteger getScreenIdx() {
		return screenIdx;
	}

	public void setScreenIdx(BigInteger screenIdx) {
		this.screenIdx = screenIdx;
	}

	public BigInteger getScheduleVersionIdx() {
		return scheduleVersionIdx;
	}

	public void setScheduleVersionIdx(BigInteger scheduleVersionIdx) {
		this.scheduleVersionIdx = scheduleVersionIdx;
	}

	public BigInteger getControlScheduleIdx() {
		return controlScheduleIdx;
	}

	public void setControlScheduleIdx(BigInteger controlScheduleIdx) {
		this.controlScheduleIdx = controlScheduleIdx;
	}

	public String getControlScheduleName() {
		return controlScheduleName;
	}

	public void setControlScheduleName(String controlScheduleName) {
		this.controlScheduleName = controlScheduleName;
	}

	public String getSearchDvType() {
		return searchDvType;
	}

	public void setSearchDvType(String searchDvType) {
		this.searchDvType = searchDvType;
	}

	public String getSearchDvRes() {
		return searchDvRes;
	}

	public void setSearchDvRes(String searchDvRes) {
		this.searchDvRes = searchDvRes;
	}

	public String getSearchDvSite() {
		return searchDvSite;
	}

	public void setSearchDvSite(String searchDvSite) {
		this.searchDvSite = searchDvSite;
	}

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getControlValue() {
		return controlValue;
	}

	public void setControlValue(String controlValue) {
		this.controlValue = controlValue;
	}

}
