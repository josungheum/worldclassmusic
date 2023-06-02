package qss.vo;

import java.util.List;

public class ScreenResultVo {
	private List<MainScheduleVo> mainScheduleVoList;         // MainScheduleVo
	private List<UploadVo> uploadVoList;    				 // 파일 리스트
	private List<ControlScheduleVo> controlScheduleVoList;    		 // d

	
	public List<ControlScheduleVo> getControlScheduleVoList() {
		return controlScheduleVoList;
	}
	public void setControlScheduleVoList(List<ControlScheduleVo> controlScheduleVoList) {
		this.controlScheduleVoList = controlScheduleVoList;
	}
	public List<MainScheduleVo> getMainScheduleVoList() {
		return mainScheduleVoList;
	}
	public void setMainScheduleVoList(List<MainScheduleVo> mainScheduleVoList) {
		this.mainScheduleVoList = mainScheduleVoList;
	}
	public List<UploadVo> getUploadVoList() {
		return uploadVoList;
	}
	public void setUploadVoList(List<UploadVo> uploadVoList) {
		this.uploadVoList = uploadVoList;
	}

}
