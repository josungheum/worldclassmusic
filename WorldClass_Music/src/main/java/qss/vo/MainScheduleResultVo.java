package qss.vo;

import java.util.List;

public class MainScheduleResultVo {
	private int Version;
	private List<ScheduleResultVo> schedules;

	public int getVersion() {
		return Version;
	}

	public void setVersion(int version) {
		Version = version;
	}

	public List<ScheduleResultVo> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ScheduleResultVo> schedules) {
		this.schedules = schedules;
	}
}
