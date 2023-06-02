package qss.vo.log;

import java.math.BigInteger;

//단말 별 사용자 action 로그
public class KioskLogH {
    private String domainIdx; // 도메인 일련번호
    private String brandIdx; // 브랜드 일련번호
    private String francIdx; // 가맹점 일련번호
    private BigInteger deviceIdx; // 단말 일련번호
    private String deviceCode; // 단말 코드
    private BigInteger floorIdx; // 층 일련번호
    private String actionType; // 액션 타입
    private String actionName; // 액션 명
    private String dayofweek; // 요일
    private String playdate; // 액션 일자
    private String starttime; // 액션 시간

    public String getDomainIdx() {
        return domainIdx;
    }

    public void setDomainIdx(String domainIdx) {
        this.domainIdx = domainIdx;
    }

    public String getBrandIdx() {
        return brandIdx;
    }

    public void setBrandIdx(String brandIdx) {
        this.brandIdx = brandIdx;
    }

    public String getFrancIdx() {
        return francIdx;
    }

    public void setFrancIdx(String francIdx) {
        this.francIdx = francIdx;
    }

    public BigInteger getDeviceIdx() {
        return deviceIdx;
    }

    public void setDeviceIdx(BigInteger deviceIdx) {
        this.deviceIdx = deviceIdx;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public BigInteger getFloorIdx() {
        return floorIdx;
    }

    public void setFloorIdx(BigInteger floorIdx) {
        this.floorIdx = floorIdx;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getPlaydate() {
        return playdate;
    }

    public void setPlaydate(String playdate) {
        this.playdate = playdate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
}