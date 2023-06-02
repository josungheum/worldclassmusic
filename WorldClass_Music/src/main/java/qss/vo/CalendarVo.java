package qss.vo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class CalendarVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	// 메인 스케줄(schedule List)
	private BigInteger mainScheduleIdx;			// 메인 스케줄 일련번호
	private String mainScheduleName;			// 메인 스케줄명
	private String startDate;					// 시작일자
	private String endDate;						// 종료일자

	private String startTime;					// 시작시간
	private String endTime;						// 종료시간


	private String scheduleType;				// 스크린 스케줄 타입(기본R, 예약T, 이벤트E)
	private String dayOfWeek;					// 선택 주
	private String eventType;					// 이벤트
	private String countType;					// 실행횟수



	private List<BigInteger> screenList;		// 적용 스케줄 스크린 리스트(ScreenIdx List)
	private List<String> screenTypeList;		// 적용 스케줄 운영 리스트(ScreenIdx List)
	private List<BigInteger> deviceList;		// 적용 단말 리스트(deviceIdx List)

	private String resultScreenList;			// 적용 스케줄 스크린 리스트
	private String resultDeviceList;             // 적용 단말 리스트

	private BigInteger scheduleVersionIdx;		// 스케줄버전 일련번호
	private String searchDate;     			    /*검색 시작 일*/

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

	private int checkDateIdx;




	public int getCheckDateIdx() {
		return checkDateIdx;
	}

	public void setCheckDateIdx(int checkDateIdx) {
		this.checkDateIdx = checkDateIdx;
	}

	public String getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	public String getScreenType() {
		return screenType;
	}

	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}



	public List<String> getScreenTypeList() {
		return screenTypeList;
	}

	public void setScreenTypeList(List<String> screenTypeList) {
		this.screenTypeList = screenTypeList;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
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

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getMainScheduleName() {
		return mainScheduleName;
	}

	public void setMainScheduleName(String mainScheduleName) {
		this.mainScheduleName = mainScheduleName;
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

	public List<BigInteger> getScreenList() {
		return screenList;
	}

	public void setScreenList(List<BigInteger> screenList) {
		this.screenList = screenList;
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

	public String getResultScreenList() {
		return resultScreenList;
	}

	public void setResultScreenList(String resultScreenList) {
		this.resultScreenList = resultScreenList;
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

	public BigInteger getMainScheduleIdx() {
		return mainScheduleIdx;
	}

	public void setMainScheduleIdx(BigInteger mainScheduleIdx) {
		this.mainScheduleIdx = mainScheduleIdx;
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
}
