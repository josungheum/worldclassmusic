package qss.vo;

public class ScheduleReportVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	/*
	 * schedule_stat_h
	 */
	private String spDate;
	private int spSchedplaytime;
	private String spSchedstarttime;
	private String spSchedendtime;
	private String spRealstarttime;
	private String spRealendtime;
	private int spRealplaytime;
	private String spStatus;
	private int spDiff;
	
	/*
	 * DESC
	 */
	private String serviceName;
	private String siteName;
	private String playerName;
	
	public String getSpDate() {
		return spDate;
	}
	public void setSpDate(String spDate) {
		this.spDate = spDate;
	}
	public int getSpSchedplaytime() {
		return spSchedplaytime;
	}
	public void setSpSchedplaytime(int spSchedplaytime) {
		this.spSchedplaytime = spSchedplaytime;
	}
	public String getSpSchedstarttime() {
		return spSchedstarttime;
	}
	public void setSpSchedstarttime(String spSchedstarttime) {
		this.spSchedstarttime = spSchedstarttime;
	}
	public String getSpSchedendtime() {
		return spSchedendtime;
	}
	public void setSpSchedendtime(String spSchedendtime) {
		this.spSchedendtime = spSchedendtime;
	}
	public String getSpRealstarttime() {
		return spRealstarttime;
	}
	public void setSpRealstarttime(String spRealstarttime) {
		this.spRealstarttime = spRealstarttime;
	}
	public String getSpRealendtime() {
		return spRealendtime;
	}
	public void setSpRealendtime(String spRealendtime) {
		this.spRealendtime = spRealendtime;
	}
	public int getSpRealplaytime() {
		return spRealplaytime;
	}
	public void setSpRealplaytime(int spRealplaytime) {
		this.spRealplaytime = spRealplaytime;
	}
	public String getSpStatus() {
		return spStatus;
	}
	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}
	public int getSpDiff() {
		return spDiff;
	}
	public void setSpDiff(int spDiff) {
		this.spDiff = spDiff;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}