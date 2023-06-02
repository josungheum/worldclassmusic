package qss.vo;

import java.util.ArrayList;
import java.util.List;

public class LayerResultVo {
	private int ScheduleIdx;
	private int ScreenIdx;
	private int Idx;
	private String Name;
	private int StartCol;
	private int StartRow;
	private int ColSpan;
	private int RowSpan;
	private int PlayTime;
	private int OrderSeq;
	private List<ContentResultVo> Contents = new ArrayList<ContentResultVo>();

	public int getScheduleIdx() {
		return ScheduleIdx;
	}

	public void setScheduleIdx(int scheduleIdx) {
		ScheduleIdx = scheduleIdx;
	}

	public int getScreenIdx() {
		return ScreenIdx;
	}

	public void setScreenIdx(int screenIdx) {
		ScreenIdx = screenIdx;
	}

	public int getIdx() {
		return Idx;
	}

	public void setIdx(int idx) {
		Idx = idx;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getStartCol() {
		return StartCol;
	}

	public void setStartCol(int startCol) {
		StartCol = startCol;
	}

	public int getStartRow() {
		return StartRow;
	}

	public void setStartRow(int startRow) {
		StartRow = startRow;
	}

	public int getColSpan() {
		return ColSpan;
	}

	public void setColSpan(int colSpan) {
		ColSpan = colSpan;
	}

	public int getRowSpan() {
		return RowSpan;
	}

	public void setRowSpan(int rowSpan) {
		RowSpan = rowSpan;
	}

	public int getPlayTime() {
		return PlayTime;
	}

	public void setPlayTime(int playTime) {
		PlayTime = playTime;
	}

	public int getOrderSeq() {
		return OrderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		OrderSeq = orderSeq;
	}

	public List<ContentResultVo> getContents() {
		return Contents;
	}

	public void setContents(List<ContentResultVo> contents) {
		Contents = contents;
	}
}
