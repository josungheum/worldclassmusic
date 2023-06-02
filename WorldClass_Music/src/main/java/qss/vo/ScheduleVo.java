package qss.vo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ScheduleVo extends CommonVo {
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
	private String screenPlayTime;				// 총 스크린 플레이 시간
	
	
	private String distributeYn;				//배포여부
	


	private List<BigInteger> screenList;		// 적용 스케줄 스크린 리스트(ScreenIdx List)
	private List<String> screenTypeList;		// 적용 스케줄 운영 리스트(ScreenIdx List)
	private List<String> screenPlayTimeList;	// 적용 스케줄 스크린 리스트(ScreenIdx List)
	private List<BigInteger> deviceList;		// 적용 단말 리스트(deviceIdx List)

	private String resultScreenList;			// 적용 스케줄 스크린 리스트
	private String resultDeviceList;             // 적용 단말 리스트

	private BigInteger scheduleVersionIdx;		// 스케줄버전 일련번호
//	private String TargetFranc;

	// 유저 정보
//	private int ModUser;

	// 사이트 목록 Tree
//	private int trIDX;
//	private String TR_NAME;
//	private String TR_DEPTHNAME;
//	private String TR_PARENTIDX;
//	private int TR_DEPTH;

	// Tree Index
//	private String SmPindex;
//
//	// 수정 권한
//	private String ModIAuth;

	// 조회 Screen List
	private BigInteger screenIdx;
	private String playTime;
	private String screenName;
	private BigInteger scheduleScreenIdx;
	
	private String searchDvType;
	private String searchDvRes;
	private String searchDvSite;

	// map DATA
	private Map<String, Object> mapData;

	// 저장 결과
	private int resultCode;

	// 배열 처리
	private String[] checkboxArr;		  // 체크된 배열값
	
	private String screenType;

	private List<String> checkedDeviceList;		// 적용 단말 리스트(deviceIdx List)
	
	private String adminType;
	

	

	public List<String> getScreenPlayTimeList() {
		return screenPlayTimeList;
	}

	public void setScreenPlayTimeList(List<String> screenPlayTimeList) {
		this.screenPlayTimeList = screenPlayTimeList;
	}

	public String getDistributeYn() {
		return distributeYn;
	}

	public void setDistributeYn(String distributeYn) {
		this.distributeYn = distributeYn;
	}

	

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

	public List<String> getCheckedDeviceList() {
		return checkedDeviceList;
	}

	public void setCheckedDeviceList(List<String> checkedDeviceList) {
		this.checkedDeviceList = checkedDeviceList;
	}

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}


}
