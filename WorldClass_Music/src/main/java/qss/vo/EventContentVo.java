package qss.vo;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EventContentVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private String fileContentIdx;
	private String fileName;
	private String fileSaveName;
	private String savePath;
	private long fileSize;
	private String checkSum;
	private String RegDate;
	private String ModDate;
	private String thumbnailPath;
	private int playTime;

	private MultipartFile[] uploadFile;
	private String saveFolder;
	private int groupId;
	private String extens;

	private String messages;
	private int errorCode;

	private int[] checkboxArr;
	private String contType;
	private String modDt;
	private int eventContentIdx;
	private String startDate;
	private String endDate;
	private String eventTagList;
	private String adminType;

	private String searchDvType;
	private String searchDvRes;
	private String searchDvSite;
	
	private List<String> deviceList;
	private List<Integer> deviceList2;
	
	private String target;
	private int cnt;
	
	private String searchStartDt;
	private String searchEndDt;
	private String searchContentNm;
	private String searchTag;
	private String searchKioskNm;
	
	public String getMessages() {
		return messages;
	}




	public void setMessages(String messages) {
		this.messages = messages;
	}




	public int getErrorCode() {
		return errorCode;
	}




	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}




	public String getFileContentIdx() {
		return fileContentIdx;
	}




	public void setFileContentIdx(String fileContentIdx) {
		this.fileContentIdx = fileContentIdx;
	}




	public String getFileName() {
		return fileName;
	}




	public void setFileName(String fileName) {
		this.fileName = fileName;
	}




	public String getFileSaveName() {
		return fileSaveName;
	}




	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}




	public String getSavePath() {
		return savePath;
	}




	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}




	public long getFileSize() {
		return fileSize;
	}




	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}




	public String getCheckSum() {
		return checkSum;
	}




	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}




	public String getRegDate() {
		return RegDate;
	}




	public void setRegDate(String regDate) {
		RegDate = regDate;
	}




	public String getModDate() {
		return ModDate;
	}




	public void setModDate(String modDate) {
		ModDate = modDate;
	}




	public String getThumbnailPath() {
		return thumbnailPath;
	}




	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}




	public int getPlayTime() {
		return playTime;
	}




	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}




	public MultipartFile[] getUploadFile() {
		return uploadFile;
	}




	public void setUploadFile(MultipartFile[] uploadFile) {
		this.uploadFile = uploadFile;
	}




	public String getSaveFolder() {
		return saveFolder;
	}




	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}




	public int getGroupId() {
		return groupId;
	}




	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}




	public String getExtens() {
		return extens;
	}




	public void setExtens(String extens) {
		this.extens = extens;
	}






	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UploadVo [Idx=");
		builder.append("");
		builder.append(", FileName=");
		builder.append(fileName);
		builder.append(", FileSaveName=");
		builder.append(fileSaveName);
		builder.append(", SavePath=");
		builder.append(savePath);
		builder.append(", FileSize=");
		builder.append(fileSize);
		builder.append(", CheckSum=");
		builder.append(checkSum);
		builder.append(", RegUser=");
		builder.append(RegDate);
		builder.append(", ModUser=");
		builder.append(", ModDate=");
		builder.append(", ThumbnailPath=");
		builder.append(thumbnailPath);
		builder.append(", PlayTime=");
		builder.append(playTime);
		builder.append(", UploadFile=");
		builder.append(Arrays.toString(uploadFile));
		builder.append(", SaveFolder=");
		builder.append(saveFolder);
		builder.append(", GroupId=");
		builder.append(groupId);
		builder.append(", Extens=");
		builder.append(extens);
		builder.append("]");
		return builder.toString();
	}

	public int[] getCheckboxArr() {
		return checkboxArr;
	}




	public void setCheckboxArr(int[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}




	public String getContType() {
		return contType;
	}




	public void setContType(String contType) {
		this.contType = contType;
	}




	public String getModDt() {
		return modDt;
	}




	public void setModDt(String modDt) {
		this.modDt = modDt;
	}




	public int getEventContentIdx() {
		return eventContentIdx;
	}




	public void setEventContentIdx(int eventContentIdx) {
		this.eventContentIdx = eventContentIdx;
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




	public String getEventTagList() {
		return eventTagList;
	}




	public void setEventTagList(String eventTagList) {
		this.eventTagList = eventTagList;
	}




	public String getAdminType() {
		return adminType;
	}




	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}




	public String getSearchDvType() {
		return searchDvType;
	}




	public void setSearchDvType(String searchDvType) {
		this.searchDvType = searchDvType;
	}




	public String getSearchDvRes() {
		return searchDvRes;
	}




	public void setSearchDvRes(String searchDvRes) {
		this.searchDvRes = searchDvRes;
	}




	public String getSearchDvSite() {
		return searchDvSite;
	}




	public void setSearchDvSite(String searchDvSite) {
		this.searchDvSite = searchDvSite;
	}




	public List<String> getDeviceList() {
		return deviceList;
	}




	public void setDeviceList(List<String> deviceList) {
		this.deviceList = deviceList;
	}




	public List<Integer> getDeviceList2() {
		return deviceList2;
	}




	public void setDeviceList2(List<Integer> deviceList2) {
		this.deviceList2 = deviceList2;
	}




	public String getTarget() {
		return target;
	}




	public void setTarget(String target) {
		this.target = target;
	}




	public int getCnt() {
		return cnt;
	}




	public void setCnt(int cnt) {
		this.cnt = cnt;
	}




	public String getSearchStartDt() {
		return searchStartDt;
	}




	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}




	public String getSearchEndDt() {
		return searchEndDt;
	}




	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}




	public String getSearchContentNm() {
		return searchContentNm;
	}




	public void setSearchContentNm(String searchContentNm) {
		this.searchContentNm = searchContentNm;
	}




	public String getSearchTag() {
		return searchTag;
	}




	public void setSearchTag(String searchTag) {
		this.searchTag = searchTag;
	}




	public String getSearchKioskNm() {
		return searchKioskNm;
	}




	public void setSearchKioskNm(String searchKioskNm) {
		this.searchKioskNm = searchKioskNm;
	}



}