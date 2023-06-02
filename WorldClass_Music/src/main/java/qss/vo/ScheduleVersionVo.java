package qss.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleVersionVo {
	private int Idx;
	private int KioskIdx;
	private String ScheduleType;
	private int Version;
	private String ModReason;
	private LocalDateTime RegDtime;

	public int getIdx() {
		return Idx;
	}

	public void setIdx(int idx) {
		Idx = idx;
	}

	public int getKioskIdx() {
		return KioskIdx;
	}

	public void setKioskIdx(int kioskIdx) {
		KioskIdx = kioskIdx;
	}

	public String getScheduleType() {
		return ScheduleType;
	}

	public void setScheduleType(String scheduleType) {
		ScheduleType = scheduleType;
	}

	public int getVersion() {
		return Version;
	}

	public void setVersion(int version) {
		Version = version;
	}

	public String getModReason() {
		return ModReason;
	}

	public void setModReason(String modReason) {
		ModReason = modReason;
	}

	public String getRegDtime() {
		return RegDtime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public void setRegDtime(LocalDateTime regDtime) {
		RegDtime = regDtime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScheduleVersionVo [Idx=");
		builder.append(Idx);
		builder.append(", KioskIdx=");
		builder.append(KioskIdx);
		builder.append(", ScheduleType=");
		builder.append(ScheduleType);
		builder.append(", Version=");
		builder.append(Version);
		builder.append(", ModReason=");
		builder.append(ModReason);
		builder.append(", RegDtime=");
		builder.append(RegDtime);
		builder.append("]");
		return builder.toString();
	}
}
