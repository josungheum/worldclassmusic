package qss.vo;

import java.util.List;
import java.util.Map;

/**
 * 
 * @FileName AdditionImageListVo.java
 * @Project SignageCMS
 * @Date 2020. 3. 13.
 * @author qvoss
 *
 */
public class AdditionImageListVo extends CommonVo {
	private static final long serialVersionUID = 4514616892764199872L;
	
	/*
	 * Addition_ImageList
	 */
	private int additionIdx;
	private int fileContentIdx;
	private String additionType;
	private int orderSeq;
	
	/*
	 * Desc
	 */
	private String thumbnailPath;
	private List<Map<String, Object>> fileList;
	private List<AdditionImageListVo> additionList;
	private String additionXML;
	
	public int getAdditionIdx() {
		return additionIdx;
	}
	public void setAdditionIdx(int additionIdx) {
		this.additionIdx = additionIdx;
	}
	public int getFileContentIdx() {
		return fileContentIdx;
	}
	public void setFileContentIdx(int fileContentIdx) {
		this.fileContentIdx = fileContentIdx;
	}
	public String getAdditionType() {
		return additionType;
	}
	public void setAdditionType(String additionType) {
		this.additionType = additionType;
	}
	public int getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public List<Map<String, Object>> getFileList() {
		return fileList;
	}
	public void setFileList(List<Map<String, Object>> fileList) {
		this.fileList = fileList;
	}
	public List<AdditionImageListVo> getAdditionList() {
		return additionList;
	}
	public void setAdditionList(List<AdditionImageListVo> additionList) {
		this.additionList = additionList;
	}
	public String getAdditionXML() {
		return additionXML;
	}
	public void setAdditionXML(String additionXML) {
		this.additionXML = additionXML;
	}
}