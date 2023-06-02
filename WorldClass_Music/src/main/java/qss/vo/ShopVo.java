package qss.vo;

import java.math.BigInteger;
import java.util.List;

/**
 * 
 * @FileName ShopVo.java
 * @Project SignageCMS
 * @Date 2020. 3. 13.
 * @author qvoss
 *
 */
public class ShopVo extends CommonVo {
    private static final long serialVersionUID = 5025773686178808701L;

    /*
     * SHOP
     */
    private BigInteger shopIdx;
    private String dispNmKr;
    private String dispNmEn;
    private String dispNmCh;
    private String dispNmJp;
    private String telNum;
    private BigInteger logoImg;
    private BigInteger detailImg;
    private String detailDispYn;
    private String detailConceptTxt;
    private String detailConceptTxtEn;
    private String activeYn;

    /*
     * FLOOR_SHOP
     */
    private BigInteger floorIdx;
    private int posX;
    private int posY;
    private String floorNm;

    /*
     * SHOP_IMAGELIST
     */
    private int fileContentIdx;
    private int orderSeq;
    private String detailMenuTitle;
    private String detailMenuTitleEn;
    private String detailMenuDesc;
    private String detailMenuDescEn;
    /*
     * DESC
     */
    private String logoThumbnailPath;
    private String detailThumbnailPath;
    private String detailMenuXML;
    private List<ShopVo> detailMenuList;
    private BigInteger[] checkboxArr;
    
	public BigInteger getShopIdx() {
		return shopIdx;
	}
	public void setShopIdx(BigInteger shopIdx) {
		this.shopIdx = shopIdx;
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
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public BigInteger getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(BigInteger logoImg) {
		this.logoImg = logoImg;
	}
	public BigInteger getDetailImg() {
		return detailImg;
	}
	public void setDetailImg(BigInteger detailImg) {
		this.detailImg = detailImg;
	}
	public String getDetailDispYn() {
		return detailDispYn;
	}
	public void setDetailDispYn(String detailDispYn) {
		this.detailDispYn = detailDispYn;
	}
	public String getDetailConceptTxt() {
		return detailConceptTxt;
	}
	public void setDetailConceptTxt(String detailConceptTxt) {
		this.detailConceptTxt = detailConceptTxt;
	}
	public String getActiveYn() {
		return activeYn;
	}
	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}
	public BigInteger getFloorIdx() {
		return floorIdx;
	}
	public void setFloorIdx(BigInteger floorIdx) {
		this.floorIdx = floorIdx;
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
	public String getFloorNm() {
		return floorNm;
	}
	public void setFloorNm(String floorNm) {
		this.floorNm = floorNm;
	}
	public int getFileContentIdx() {
		return fileContentIdx;
	}
	public void setFileContentIdx(int fileContentIdx) {
		this.fileContentIdx = fileContentIdx;
	}
	public int getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}
	public String getDetailMenuTitle() {
		return detailMenuTitle;
	}
	public void setDetailMenuTitle(String detailMenuTitle) {
		this.detailMenuTitle = detailMenuTitle;
	}
	public String getDetailMenuDesc() {
		return detailMenuDesc;
	}
	public void setDetailMenuDesc(String detailMenuDesc) {
		this.detailMenuDesc = detailMenuDesc;
	}
	public String getLogoThumbnailPath() {
		return logoThumbnailPath;
	}
	public void setLogoThumbnailPath(String logoThumbnailPath) {
		this.logoThumbnailPath = logoThumbnailPath;
	}
	public String getDetailMenuXML() {
		return detailMenuXML;
	}
	public void setDetailMenuXML(String detailMenuXML) {
		this.detailMenuXML = detailMenuXML;
	}
	public List<ShopVo> getDetailMenuList() {
		return detailMenuList;
	}
	public void setDetailMenuList(List<ShopVo> detailMenuList) {
		this.detailMenuList = detailMenuList;
	}
	public String getDetailThumbnailPath() {
		return detailThumbnailPath;
	}
	public void setDetailThumbnailPath(String detailThumbnailPath) {
		this.detailThumbnailPath = detailThumbnailPath;
	}
	public BigInteger[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(BigInteger[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public String getDetailConceptTxtEn() {
		return detailConceptTxtEn;
	}
	public void setDetailConceptTxtEn(String detailConceptTxtEn) {
		this.detailConceptTxtEn = detailConceptTxtEn;
	}
	public String getDetailMenuTitleEn() {
		return detailMenuTitleEn;
	}
	public void setDetailMenuTitleEn(String detailMenuTitleEn) {
		this.detailMenuTitleEn = detailMenuTitleEn;
	}
	public String getDetailMenuDescEn() {
		return detailMenuDescEn;
	}
	public void setDetailMenuDescEn(String detailMenuDescEn) {
		this.detailMenuDescEn = detailMenuDescEn;
	}
}