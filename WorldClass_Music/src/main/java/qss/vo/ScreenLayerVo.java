package qss.vo;

import java.math.BigInteger;
import java.util.List;

/**
 * <pre>
 * qss.vo
 *    |_ ScreenLayerVo.java
 *
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version :
 * @author : khk
 */
public class ScreenLayerVo extends CommonVo {

	private static final long serialVersionUID = 1L;

	private BigInteger screenLayerIdx;		//스크린 레이어 일련번호
	private BigInteger screenIdx;			//스크린 일련번호
	private String screenLayerName;		//스크린 레이어명
	private int startCol;					//시작열
	private int startRow;					//시작행
	private int colSpan;					//열span
	private int rowSpan;					//행span
	private int playTime;					//재생시간

	private List<LayerContentVo> layerContentVoList;        // LayerContentVo

	public BigInteger getScreenLayerIdx() {
		return screenLayerIdx;
	}
	public void setScreenLayerIdx(BigInteger screenLayerIdx) {
		this.screenLayerIdx = screenLayerIdx;
	}
	public BigInteger getScreenIdx() {
		return screenIdx;
	}
	public void setScreenIdx(BigInteger screenIdx) {
		this.screenIdx = screenIdx;
	}
	public String getScreenLayerName() {
		return screenLayerName;
	}
	public void setScreenLayerName(String screenLayerName) {
		this.screenLayerName = screenLayerName;
	}
	public int getStartCol() {
		return startCol;
	}
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}
	public int getPlayTime() {
		return playTime;
	}
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<LayerContentVo> getLayerContentVoList() {
		return layerContentVoList;
	}
	public void setLayerContentVoList(List<LayerContentVo> layerContentVoList) {
		this.layerContentVoList = layerContentVoList;
	}




}