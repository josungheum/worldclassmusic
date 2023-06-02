package qss.vo;

import java.math.BigInteger;

public class FloorKioskVo extends CommonVo {
    private static final long serialVersionUID = 1L;

    private BigInteger floorIdx; // 층 일련번호

    private String francCode; // 가맹점 코드
    private String francName; // 가맹점명

    private String deviceName; // 단말명
    private String deviceType; // 단말타입

    private int posX; // X 좌표
    private int posY; // Y 좌표

    private String dispShortNm; // 층 단축표시이름
    private String direction; // 단말방향

    public BigInteger getFloorIdx() {
        return floorIdx;
    }

    public void setFloorIdx(BigInteger floorIdx) {
        this.floorIdx = floorIdx;
    }

    public String getFrancCode() {
        return francCode;
    }

    public void setFrancCode(String francCode) {
        this.francCode = francCode;
    }

    public String getFrancName() {
        return francName;
    }

    public void setFrancName(String francName) {
        this.francName = francName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getDispShortNm() {
        return dispShortNm;
    }

    public void setDispShortNm(String dispShortNm) {
        this.dispShortNm = dispShortNm;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}