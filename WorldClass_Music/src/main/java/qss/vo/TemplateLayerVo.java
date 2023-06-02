package qss.vo;

import java.math.BigInteger;
import java.util.List;

public class TemplateLayerVo extends CommonVo {

	private static final long serialVersionUID = 1L;

	private BigInteger templateLayerIdx;		//템플릿 레이어 일련번호
	private BigInteger templateIdx;			//템플릿 일련번호
	private String templateLayerName;		//템플릿 레이어명
	private int startCol;					//시작열
	private int startRow;					//시작행
	private int colSpan;					//열span
	private int rowSpan;					//행span
	private int playTime;					//재생시간

    private List<LayerContentVo> layerContentVoList; // LayerContentVo

    public BigInteger getTemplateLayerIdx() {
        return templateLayerIdx;
    }

    public void setTemplateLayerIdx(BigInteger templateLayerIdx) {
        this.templateLayerIdx = templateLayerIdx;
    }

    public BigInteger getTemplateIdx() {
        return templateIdx;
    }

    public void setTemplateIdx(BigInteger templateIdx) {
        this.templateIdx = templateIdx;
    }

    public String getTemplateLayerName() {
        return templateLayerName;
    }

    public void setTemplateLayerName(String templateLayerName) {
        this.templateLayerName = templateLayerName;
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

    public List<LayerContentVo> getLayerContentVoList() {
        return layerContentVoList;
    }

    public void setLayerContentVoList(List<LayerContentVo> layerContentVoList) {
        this.layerContentVoList = layerContentVoList;
    }
}