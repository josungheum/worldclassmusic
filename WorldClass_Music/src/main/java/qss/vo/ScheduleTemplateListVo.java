package qss.vo;

import java.math.BigInteger;

public class ScheduleTemplateListVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	private BigInteger scheduleTemplateIdx;     // 스케줄 템플릿 일련번호
	private BigInteger scheduleTemplatelistIdx; // 스케줄 템플릿 리스트 일련번호
	private BigInteger mainScheduleIdx;			// 메인 스케줄 일련번호


	public BigInteger getScheduleTemplateIdx() {
		return scheduleTemplateIdx;
	}
	public void setScheduleTemplateIdx(BigInteger scheduleTemplateIdx) {
		this.scheduleTemplateIdx = scheduleTemplateIdx;
	}
	public BigInteger getScheduleTemplatelistIdx() {
		return scheduleTemplatelistIdx;
	}
	public void setScheduleTemplatelistIdx(BigInteger scheduleTemplatelistIdx) {
		this.scheduleTemplatelistIdx = scheduleTemplatelistIdx;
	}
	public BigInteger getMainScheduleIdx() {
		return mainScheduleIdx;
	}
	public void setMainScheduleIdx(BigInteger mainScheduleIdx) {
		this.mainScheduleIdx = mainScheduleIdx;
	}




}
