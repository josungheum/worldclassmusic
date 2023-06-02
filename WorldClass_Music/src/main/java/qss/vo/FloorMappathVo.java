package qss.vo;

import java.math.BigInteger;

public class FloorMappathVo extends CommonVo {
    private static final long serialVersionUID = 1L;

    private BigInteger floorIdx; // 층 일련번호

    private int rowCnt; // 경로-행 갯수
    private int colCnt; // 경로-열 갯수

    private String pathList; // 경로리스트
    private String dispShortNm; // 층 단축표시 이름

    public BigInteger getFloorIdx() {
        return floorIdx;
    }

    public void setFloorIdx(BigInteger floorIdx) {
        this.floorIdx = floorIdx;
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

    public String getPathList() {
        return pathList;
    }

    public void setPathList(String pathList) {
        this.pathList = pathList;
    }

    public String getDispShortNm() {
        return dispShortNm;
    }

    public void setDispShortNm(String dispShortNm) {
        this.dispShortNm = dispShortNm;
    }
}