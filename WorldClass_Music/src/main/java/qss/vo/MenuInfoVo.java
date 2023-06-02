package qss.vo;

import java.math.BigInteger;

public class MenuInfoVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private BigInteger menuIdx;			//메뉴정보 일련번호
	private String menuId;				//메뉴ID
	private String menuKorName;			//메뉴한글명
	private String menuEngName;			//메뉴영문명
	private String menuSubName;			//메뉴서브명
	private String menuLinkAddr;		//메뉴링크주소
	private String parentMenuId;		//상위메뉴ID
	private String parentMenuNm;		//상위메뉴명
	private String sysShowYn;			//시스템관리자노출여부
	private String brdShowYn;			//브랜드관리자노출여부
	private String frcShowYn;			//가맹점관리자노출여부
	private int oderSeq;				//정렬순서
	private String path;				//부모순번/부모menuId 텍스트 ( 예:400/SAL00,401/SAL01 )
	private String depth;				//트리뎁스
	private String activeYn;			//활성화여부
	private int leaf;					//자식개수
	private String treeUpDown;			//트리순서(U:up, D:down)
	private String pathCond;			//PATH 조회조건일 때 사용

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigInteger getMenuIdx() {
		return menuIdx;
	}

	public void setMenuIdx(BigInteger menuIdx) {
		this.menuIdx = menuIdx;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuKorName() {
		return menuKorName;
	}

	public void setMenuKorName(String menuKorName) {
		this.menuKorName = menuKorName;
	}

	public String getMenuEngName() {
		return menuEngName;
	}

	public void setMenuEngName(String menuEngName) {
		this.menuEngName = menuEngName;
	}

	public String getMenuSubName() {
		return menuSubName;
	}

	public void setMenuSubName(String menuSubName) {
		this.menuSubName = menuSubName;
	}

	public String getMenuLinkAddr() {
		return menuLinkAddr;
	}

	public void setMenuLinkAddr(String menuLinkAddr) {
		this.menuLinkAddr = menuLinkAddr;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getParentMenuNm() {
		return parentMenuNm;
	}

	public void setParentMenuNm(String parentMenuNm) {
		this.parentMenuNm = parentMenuNm;
	}

	public String getSysShowYn() {
		return sysShowYn;
	}

	public void setSysShowYn(String sysShowYn) {
		this.sysShowYn = sysShowYn;
	}

	public String getBrdShowYn() {
		return brdShowYn;
	}

	public void setBrdShowYn(String brdShowYn) {
		this.brdShowYn = brdShowYn;
	}

	public String getFrcShowYn() {
		return frcShowYn;
	}

	public void setFrcShowYn(String frcShowYn) {
		this.frcShowYn = frcShowYn;
	}

	public int getOderSeq() {
		return oderSeq;
	}

	public void setOderSeq(int oderSeq) {
		this.oderSeq = oderSeq;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}

	public String getTreeUpDown() {
		return treeUpDown;
	}

	public void setTreeUpDown(String treeUpDown) {
		this.treeUpDown = treeUpDown;
	}

	public String getPathCond() {
		return pathCond;
	}

	public void setPathCond(String pathCond) {
		this.pathCond = pathCond;
	}

}
