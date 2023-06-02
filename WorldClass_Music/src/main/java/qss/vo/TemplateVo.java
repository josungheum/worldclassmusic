package qss.vo;

import java.math.BigInteger;

public class TemplateVo extends CommonVo {

    private static final long serialVersionUID = 1L;

    // template
    private BigInteger templateIdx; //템플릿 일련번호
    private String templateName; //템플릿명
    private int rowCnt; //행갯수
    private int colCnt; //열갯수
    private int playTime; //재생시간
    private int resolutionX; //해상도x좌표
    private int resolutionY; //해상도y좌표

    // template_layer
    private BigInteger templateLayerIdx; // 템플릿 레이어 일련번호
    private String templateLayerName; // 템플릿 레이어명
    private int startCol; // 시작열
    private int startRow; // 시작행
    private int colSpan; // 열SPAN
    private int rowSpan; // 행SPAN
    private int orderSeq; // 정렬순서

    private String layerXml; // 레이어 정보(저장용 변수)
    private int layerLength; // 레이어 정보 count(저장용 변수)

    private String[] checkboxArr; // 체크된 배열값

    // Layer_Content
    private BigInteger layerContentIdx; // 레이어 컨텐트 일련번호

    private BigInteger scheduleVersionIdx; // 스케줄버전 일련번호

    // file content
    private BigInteger fileContentIdx; // 파일컨텐트 일련번호
    private String fileName;
    private String fileSaveName;
    private String savePath;
    private BigInteger fileSize;
    private String checkSum;

    public BigInteger getTemplateIdx() {
        return templateIdx;
    }

    public void setTemplateIdx(BigInteger templateIdx) {
        this.templateIdx = templateIdx;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public int getRowCnt() {
        return rowCnt;
    }

    public void setRowCnt(int rowCnt) {
        this.rowCnt = rowCnt;
    }

    public int getColCnt() {
        return colCnt;
    }

    public void setColCnt(int colCnt) {
        this.colCnt = colCnt;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
    }

    public BigInteger getTemplateLayerIdx() {
        return templateLayerIdx;
    }

    public void setTemplateLayerIdx(BigInteger templateLayerIdx) {
        this.templateLayerIdx = templateLayerIdx;
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

    public int getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getLayerXml() {
        return layerXml;
    }

    public void setLayerXml(String layerXml) {
        this.layerXml = layerXml;
    }

    public int getLayerLength() {
        return layerLength;
    }

    public void setLayerLength(int layerLength) {
        this.layerLength = layerLength;
    }

    public String[] getCheckboxArr() {
        return checkboxArr;
    }

    public void setCheckboxArr(String[] checkboxArr) {
        this.checkboxArr = checkboxArr;
    }

    public BigInteger getLayerContentIdx() {
        return layerContentIdx;
    }

    public void setLayerContentIdx(BigInteger layerContentIdx) {
        this.layerContentIdx = layerContentIdx;
    }

    public BigInteger getScheduleVersionIdx() {
        return scheduleVersionIdx;
    }

    public void setScheduleVersionIdx(BigInteger scheduleVersionIdx) {
        this.scheduleVersionIdx = scheduleVersionIdx;
    }

    public BigInteger getFileContentIdx() {
        return fileContentIdx;
    }

    public void setFileContentIdx(BigInteger fileContentIdx) {
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

    public BigInteger getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigInteger fileSize) {
        this.fileSize = fileSize;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }
}