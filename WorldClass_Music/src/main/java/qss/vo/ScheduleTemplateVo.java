package qss.vo;

import java.math.BigInteger;

public class ScheduleTemplateVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	private BigInteger scheduleTemplateIdx;     // 스케줄 템플릿 일련번호
	private String scheduleTemplateName;   		// 스케줄 템플릿 명



	public BigInteger getScheduleTemplateIdx() {
		return scheduleTemplateIdx;
	}
	public void setScheduleTemplateIdx(BigInteger scheduleTemplateIdx) {
		this.scheduleTemplateIdx = scheduleTemplateIdx;
	}
	public String getScheduleTemplateName() {
		return scheduleTemplateName;
	}
	public void setScheduleTemplateName(String scheduleTemplateName) {
		this.scheduleTemplateName = scheduleTemplateName;
	}


}
