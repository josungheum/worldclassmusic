package qss.vo.log;

import java.math.BigInteger;

//컨텐트 재생 로그
public class PlayLogH {
    private String domainIdx; // 도메인 일련번호
    private String brandIdx; // 브랜드 일련번호
    private String francIdx; // 가맹점 일련번호
    private BigInteger deviceIdx; // 단말 일련번호
    private String deviceCode; // 단말 코드
    private BigInteger mainScheduleIdx; // 메인스케줄 일련번호
    private BigInteger screenIdx; // 스크린 일련번호
    private BigInteger fileContentIdx; // 파일컨텐트 일련번호
    private String plPlaydate; // 재생일자
    private String plStarttime; // 재생시작시간
    private String plEndtime; // 재생 종료시간
    private int plPlaytime; // 재생시간
    private int plPlaymillitime; // 재생밀리초

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

    public BigInteger getMainScheduleIdx() {
        return mainScheduleIdx;
    }

    public void setMainScheduleIdx(BigInteger mainScheduleIdx) {
        this.mainScheduleIdx = mainScheduleIdx;
    }

    public BigInteger getScreenIdx() {
        return screenIdx;
    }

    public void setScreenIdx(BigInteger screenIdx) {
        this.screenIdx = screenIdx;
    }

    public BigInteger getFileContentIdx() {
        return fileContentIdx;
    }

    public void setFileContentIdx(BigInteger fileContentIdx) {
        this.fileContentIdx = fileContentIdx;
    }

    public String getPlPlaydate() {
        return plPlaydate;
    }

    public void setPlPlaydate(String plPlaydate) {
        this.plPlaydate = plPlaydate;
    }

    public String getPlStarttime() {
        return plStarttime;
    }

    public void setPlStarttime(String plStarttime) {
        this.plStarttime = plStarttime;
    }

    public String getPlEndtime() {
        return plEndtime;
    }

    public void setPlEndtime(String plEndtime) {
        this.plEndtime = plEndtime;
    }

    public int getPlPlaytime() {
        return plPlaytime;
    }

    public void setPlPlaytime(int plPlaytime) {
        this.plPlaytime = plPlaytime;
    }

    public int getPlPlaymillitime() {
        return plPlaymillitime;
    }

    public void setPlPlaymillitime(int plPlaymillitime) {
        this.plPlaymillitime = plPlaymillitime;
    }
}