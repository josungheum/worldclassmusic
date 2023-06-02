package qss.vo;

import java.math.BigInteger;

public class FloorVo extends CommonVo {
    private static final long serialVersionUID = 1L;

    private BigInteger floorIdx; // 층 일련번호

    private String dispShortNm; // 단축표시이름
    private String dispNmKr; // 층 표시명 (한)
    private String dispNmEn; // 층 표시명 (영)
    private String dispNmCh; // 층 표시명 (중)
    private String dispNmJp; // 층 표시명 (일)

    private int kioskCount; // 단말개수
    private int facilitiesCount; // 시설개수
    private int shopCount; // 브랜드개수

    private String[] checkboxArr; // 삭제 배열

    private String floorFullName; // 층 표기 full name

    private BigInteger mapImg; // 층 이미지 파일 컨텐트
    private String mapThumbnailPath; // 층 썸네일 이미지 경로
    private String mapImgPath; // 층 이미지 경로

    private BigInteger logoImg; // 층 로고 이미지 파일 컨텐트
    private String logoThumbnailPath; // 층 로고 썸네일 이미지 경로
    private String logoImgPath; // 층 로고 이미지 경로

    private String floorType; // 층 매장 타입 코드
    private String codeName; // 층 매장 타입 명

    public BigInteger getFloorIdx() {
        return floorIdx;
    }

    public void setFloorIdx(BigInteger floorIdx) {
        this.floorIdx = floorIdx;
    }

    public String getDispShortNm() {
        return dispShortNm;
    }

    public void setDispShortNm(String dispShortNm) {
        this.dispShortNm = dispShortNm;
    }

    public String getDispNmKr() {
        return dispNmKr;
    }

    public void setDispNmKr(String dispNmKr) {
        this.dispNmKr = dispNmKr;
    }

    public String getDispNmEn() {
        return dispNmEn;
    }

    public void setDispNmEn(String dispNmEn) {
        this.dispNmEn = dispNmEn;
    }

    public String getDispNmCh() {
        return dispNmCh;
    }

    public void setDispNmCh(String dispNmCh) {
        this.dispNmCh = dispNmCh;
    }

    public String getDispNmJp() {
        return dispNmJp;
    }

    public void setDispNmJp(String dispNmJp) {
        this.dispNmJp = dispNmJp;
    }

    public int getKioskCount() {
        return kioskCount;
    }

    public void setKioskCount(int kioskCount) {
        this.kioskCount = kioskCount;
    }

    public int getFacilitiesCount() {
        return facilitiesCount;
    }

    public void setFacilitiesCount(int facilitiesCount) {
        this.facilitiesCount = facilitiesCount;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public String[] getCheckboxArr() {
        return checkboxArr;
    }

    public void setCheckboxArr(String[] checkboxArr) {
        this.checkboxArr = checkboxArr;
    }

    public String getFloorFullName() {
        return floorFullName;
    }

    public void setFloorFullName(String floorFullName) {
        this.floorFullName = floorFullName;
    }

    public BigInteger getMapImg() {
        return mapImg;
    }

    public void setMapImg(BigInteger mapImg) {
        this.mapImg = mapImg;
    }

    public String getMapThumbnailPath() {
        return mapThumbnailPath;
    }

    public void setMapThumbnailPath(String mapThumbnailPath) {
        this.mapThumbnailPath = mapThumbnailPath;
    }

    public String getMapImgPath() {
        return mapImgPath;
    }

    public void setMapImgPath(String mapImgPath) {
        this.mapImgPath = mapImgPath;
    }

    public BigInteger getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(BigInteger logoImg) {
        this.logoImg = logoImg;
    }

    public String getLogoThumbnailPath() {
        return logoThumbnailPath;
    }

    public void setLogoThumbnailPath(String logoThumbnailPath) {
        this.logoThumbnailPath = logoThumbnailPath;
    }

    public String getLogoImgPath() {
        return logoImgPath;
    }

    public void setLogoImgPath(String logoImgPath) {
        this.logoImgPath = logoImgPath;
    }

    public String getFloorType() {
        return floorType;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}