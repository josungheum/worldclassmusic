package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ LayerContentVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class LayerContentVo extends CommonVo{
private static final long serialVersionUID = 1L;
	
	private BigInteger layerContentIdx;		//일련번호
	private BigInteger screenIdx;			//스크린 일련번호
	private BigInteger screenLayerIdx;		//스크린 레이어 일련번호
	private BigInteger fileContentIdx;		//파일컨텐트 일련번호
	private int playTime;					//재생시간
	
	public BigInteger getLayerContentIdx() {
		return layerContentIdx;
	}
	public void setLayerContentIdx(BigInteger layerContentIdx) {
		this.layerContentIdx = layerContentIdx;
	}
	public BigInteger getScreenIdx() {
		return screenIdx;
	}
	public void setScreenIdx(BigInteger screenIdx) {
		this.screenIdx = screenIdx;
	}
	public BigInteger getScreenLayerIdx() {
		return screenLayerIdx;
	}
	public void setScreenLayerIdx(BigInteger screenLayerIdx) {
		this.screenLayerIdx = screenLayerIdx;
	}
	public int getPlayTime() {
		return playTime;
	}
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
	
	public BigInteger getFileContentIdx() {
		return fileContentIdx;
	}
	public void setFileContentIdx(BigInteger fileContentIdx) {
		this.fileContentIdx = fileContentIdx;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
