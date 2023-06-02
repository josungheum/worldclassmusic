package qss.vo;

import java.math.BigInteger;

public class FloorFacilitiesVo extends CommonVo {
    private static final long serialVersionUID = 1L;

    private BigInteger floorIdx; // 층 일련번호
    private String facilityType; // 편의시설 타입코드

    private int posX; // X 좌표
    private int posY; // Y 좌표

    private String codeGroup; // 코드그룹
    private String codeValue; // 코드값
    private String codeName; // 코드명
    private int codeLevel; // 코드레벨
    private String memo; // 메모

    public BigInteger getFloorIdx() {
        return floorIdx;
    }

    public void setFloorIdx(BigInteger floorIdx) {
        this.floorIdx = floorIdx;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
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

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public int getCodeLevel() {
        return codeLevel;
    }

    public void setCodeLevel(int codeLevel) {
        this.codeLevel = codeLevel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}