package qss.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleResultVo {
	private int Idx;
	private int BrandIdx;
	private int FrancIdx;
	private String Name;
	private LocalDateTime StartDate;
	private LocalDateTime EndDate;
	private List<ScreenResultVo> Screens = new ArrayList<ScreenResultVo>();

	public int getIdx() {
		return Idx;
	}

	public void setIdx(int idx) {
		Idx = idx;
	}

	public int getBrandIdx() {
		return BrandIdx;
	}

	public void setBrandIdx(int brandIdx) {
		BrandIdx = brandIdx;
	}

	public int getFrancIdx() {
		return FrancIdx;
	}

	public void setFrancIdx(int francIdx) {
		FrancIdx = francIdx;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getStartDate() {
		return StartDate.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public void setStartDate(LocalDateTime startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public void setEndDate(LocalDateTime endDate) {
		EndDate = endDate;
	}

	public List<ScreenResultVo> getScreens() {
		return Screens;
	}

	public void setScreens(List<ScreenResultVo> screens) {
		Screens = screens;
	}
}
