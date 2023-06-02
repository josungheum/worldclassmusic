package qss.vo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class PlayReportVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	/*
	 * playlog_h
	 */
	private BigInteger playlogIdx;
	private BigInteger mainScheduleIdx;
	private BigInteger screenIdx;
	private BigInteger fileContentIdx;
	private String plPlaydate;
	private String plStarttime;
	private String plEndtime;
	private int plPlaytime;
	private int plPlaymilltime;
	
	/*
	 * DESC
	 */
	private String serviceName;
	private String siteName;
	private String playerName;
	private String screenName;
	private String fileName;
	private List<Map<String, Object>> serviceList;
	private List<Map<String, Object>> siteList;
	private List<Map<String, Object>> playerList;
	
	public BigInteger getPlaylogIdx() {
		return playlogIdx;
	}
	public void setPlaylogIdx(BigInteger playlogIdx) {
		this.playlogIdx = playlogIdx;
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
	public BigInteger getFileContentIdx() {
		return fileContentIdx;
	}
	public void setFileContentIdx(BigInteger fileContentIdx) {
		this.fileContentIdx = fileContentIdx;
	}
	public String getPlPlaydate() {
		return plPlaydate;
	}
	public void setPlPlaydate(String plPlaydate) {
		this.plPlaydate = plPlaydate;
	}
	public String getPlStarttime() {
		return plStarttime;
	}
	public void setPlStarttime(String plStarttime) {
		this.plStarttime = plStarttime;
	}
	public String getPlEndtime() {
		return plEndtime;
	}
	public void setPlEndtime(String plEndtime) {
		this.plEndtime = plEndtime;
	}
	public int getPlPlaytime() {
		return plPlaytime;
	}
	public void setPlPlaytime(int plPlaytime) {
		this.plPlaytime = plPlaytime;
	}
	public int getPlPlaymilltime() {
		return plPlaymilltime;
	}
	public void setPlPlaymilltime(int plPlaymilltime) {
		this.plPlaymilltime = plPlaymilltime;
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
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<Map<String, Object>> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<Map<String, Object>> serviceList) {
		this.serviceList = serviceList;
	}
	public List<Map<String, Object>> getSiteList() {
		return siteList;
	}
	public void setSiteList(List<Map<String, Object>> siteList) {
		this.siteList = siteList;
	}
	public List<Map<String, Object>> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<Map<String, Object>> playerList) {
		this.playerList = playerList;
	}
}