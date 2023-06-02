package qss.vo;

public class ContentResultVo {
	private int ScheduleIdx;
	private int ScreenIdx;
	private int LayerIdx;
	private int LayerContentIdx;
	private int Idx;
	private int PlayTime;
	private String FileName;
	private String FileSaveName;
	private String SavePath;
	private String FileSize;
	private String CheckSum;
	private int OrderSeq;

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

	public int getLayerIdx() {
		return LayerIdx;
	}

	public void setLayerIdx(int layerIdx) {
		LayerIdx = layerIdx;
	}

	public int getLayerContentIdx() {
		return LayerContentIdx;
	}

	public void setLayerContentIdx(int layerContentIdx) {
		LayerContentIdx = layerContentIdx;
	}

	public int getIdx() {
		return Idx;
	}

	public void setIdx(int idx) {
		Idx = idx;
	}

	public int getPlayTime() {
		return PlayTime;
	}

	public void setPlayTime(int playTime) {
		PlayTime = playTime;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getFileSaveName() {
		return FileSaveName;
	}

	public void setFileSaveName(String fileSaveName) {
		FileSaveName = fileSaveName;
	}

	public String getSavePath() {
		return SavePath;
	}

	public void setSavePath(String savePath) {
		SavePath = savePath;
	}

	public String getFileSize() {
		return FileSize;
	}

	public void setFileSize(String fileSize) {
		FileSize = fileSize;
	}

	public String getCheckSum() {
		return CheckSum;
	}

	public void setCheckSum(String checkSum) {
		CheckSum = checkSum;
	}

	public int getOrderSeq() {
		return OrderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		OrderSeq = orderSeq;
	}
}
