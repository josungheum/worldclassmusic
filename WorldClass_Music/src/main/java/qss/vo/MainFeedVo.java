package qss.vo;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class MainFeedVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private String mainFeedIdx;
	
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
	
	// file_content_group
	private String GROUP_IDX;
	private String DOMAIN_IDX;
	private String USERS_IDX;
	private String PARENT_GROUP_IDX;
	private String GROUP_NAME;
	private int GROUP_DEPTH;
	private String AVTIVE_YN;
	private int groupCnt;
	private int fileCnt;
	private String returnGroupIdx;
	private String groupName;
	private int[] checkboxArr;
	private String contType;
	private String regDt;
	
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
	public String getGROUP_IDX() {
		return GROUP_IDX;
	}
	public void setGROUP_IDX(String gROUP_IDX) {
		GROUP_IDX = gROUP_IDX;
	}
	public String getDOMAIN_IDX() {
		return DOMAIN_IDX;
	}
	public void setDOMAIN_IDX(String dOMAIN_IDX) {
		DOMAIN_IDX = dOMAIN_IDX;
	}
	public String getUSERS_IDX() {
		return USERS_IDX;
	}
	public void setUSERS_IDX(String uSERS_IDX) {
		USERS_IDX = uSERS_IDX;
	}
	public String getPARENT_GROUP_IDX() {
		return PARENT_GROUP_IDX;
	}
	public void setPARENT_GROUP_IDX(String pARENT_GROUP_IDX) {
		PARENT_GROUP_IDX = pARENT_GROUP_IDX;
	}
	public String getGROUP_NAME() {
		return GROUP_NAME;
	}
	public void setGROUP_NAME(String gROUP_NAME) {
		GROUP_NAME = gROUP_NAME;
	}
	public int getGROUP_DEPTH() {
		return GROUP_DEPTH;
	}
	public void setGROUP_DEPTH(int gROUP_DEPTH) {
		GROUP_DEPTH = gROUP_DEPTH;
	}
	public String getAVTIVE_YN() {
		return AVTIVE_YN;
	}
	public void setAVTIVE_YN(String aVTIVE_YN) {
		AVTIVE_YN = aVTIVE_YN;
	}
	public int getGroupCnt() {
		return groupCnt;
	}
	public void setGroupCnt(int groupCnt) {
		this.groupCnt = groupCnt;
	}
	public int getFileCnt() {
		return fileCnt;
	}
	public void setFileCnt(int fileCnt) {
		this.fileCnt = fileCnt;
	}
	public String getReturnGroupIdx() {
		return returnGroupIdx;
	}
	public void setReturnGroupIdx(String returnGroupIdx) {
		this.returnGroupIdx = returnGroupIdx;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
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
	public String getMainFeedIdx() {
		return mainFeedIdx;
	}
	public void setMainFeedIdx(String mainFeedIdx) {
		this.mainFeedIdx = mainFeedIdx;
	}

}