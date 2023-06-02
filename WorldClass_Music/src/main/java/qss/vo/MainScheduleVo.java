package qss.vo;

import java.math.BigInteger;
import java.util.List;

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
public class MainScheduleVo extends CommonVo{
	private static final long serialVersionUID = 1L;

	private BigInteger mainScheduleIdx;		//일련번호
	private String mainScheduleName;			//메인 스케줄명
	private String startDate;				//시작일자
	private String endDate;					//종료일자
	private String startTime;				//시작시간
	private String endTime;					//종료시간
	private String DayOfWeek;				//요일
	
	private List<ScheduleScreenVo> scheduleScreenVoList;    // API 용

	public BigInteger getMainScheduleIdx() {
		return mainScheduleIdx;
	}
	public void setMainScheduleIdx(BigInteger mainScheduleIdx) {
		this.mainScheduleIdx = mainScheduleIdx;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<ScheduleScreenVo> getScheduleScreenVoList() {
		return scheduleScreenVoList;
	}
	public void setScheduleScreenVoList(List<ScheduleScreenVo> scheduleScreenVoList) {
		this.scheduleScreenVoList = scheduleScreenVoList;
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
	public String getDayOfWeek() {
		return DayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		DayOfWeek = dayOfWeek;
	}
}
