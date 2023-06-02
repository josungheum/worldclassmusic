package qss.vo;

import java.math.BigInteger;

public class FloorShopVo extends CommonVo {
    private static final long serialVersionUID = 1L;

    private BigInteger floorIdx; // 층 일련번호
    private BigInteger shopIdx; // 매장 일련번호

    private int posX; // X 좌표
    private int posY; // Y 좌표

    private String dispNmKr; // 표시이름(한)
    private String dispNmEn; // 표시이름(영)
    private String dispNmCh; // 표시이름(중)
    private String dispNmJp; // 표시이름(일)

    public BigInteger getFloorIdx() {
        return floorIdx;
    }
    public void setFloorIdx(BigInteger floorIdx) {
        this.floorIdx = floorIdx;
    }
    public BigInteger getShopIdx() {
        return shopIdx;
    }
    public void setShopIdx(BigInteger shopIdx) {
        this.shopIdx = shopIdx;
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
}