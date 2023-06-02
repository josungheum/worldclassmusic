package qss.vo;

public class ProductVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private int IDX;
	private int DOMAINIDX;
	private int BRANDIDX;
	private int FRANCIDX;
	private String NAME;
	private String CODE;
	private String LAST_DTIME;
	private String GOODS_NM;
	private String GOODS_CD;
	private String PRICE;

	private int TR_IDX;
	private String TR_CD;
	private String TR_NAME;
	private String TR_DEPTHNAME;
	private String TR_PARENTIDX;
	private int TR_DEPTH;
	private String PARENTNM;

	private String BRANDCODE;
	private String FRANCCODE;
	
	public int getIDX() {
		return IDX;
	}
	public void setIDX(int iDX) {
		IDX = iDX;
	}
	public int getTR_IDX() {
		return TR_IDX;
	}
	public void setTR_IDX(int tR_IDX) {
		TR_IDX = tR_IDX;
	}
	public int getDOMAINIDX() {
		return DOMAINIDX;
	}
	public void setDOMAINIDX(int dOMAINIDX) {
		DOMAINIDX = dOMAINIDX;
	}
	public int getBRANDIDX() {
		return BRANDIDX;
	}
	public void setBRANDIDX(int bRANDIDX) {
		BRANDIDX = bRANDIDX;
	}
	public int getFRANCIDX() {
		return FRANCIDX;
	}
	public void setFRANCIDX(int fRANCIDX) {
		FRANCIDX = fRANCIDX;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getLAST_DTIME() {
		return LAST_DTIME;
	}
	public void setLAST_DTIME(String lAST_DTIME) {
		LAST_DTIME = lAST_DTIME;
	}
	public String getGOODS_NM() {
		return GOODS_NM;
	}
	public void setGOODS_NM(String gOODS_NM) {
		GOODS_NM = gOODS_NM;
	}
	public String getGOODS_CD() {
		return GOODS_CD;
	}
	public void setGOODS_CD(String gOODS_CD) {
		GOODS_CD = gOODS_CD;
	}
	public String getPRICE() {
		return PRICE;
	}
	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}
	public String getTR_CD() {
		return TR_CD;
	}
	public void setTR_CD(String tR_CD) {
		TR_CD = tR_CD;
	}
	public String getTR_NAME() {
		return TR_NAME;
	}
	public void setTR_NAME(String tR_NAME) {
		TR_NAME = tR_NAME;
	}
	public String getTR_DEPTHNAME() {
		return TR_DEPTHNAME;
	}
	public void setTR_DEPTHNAME(String tR_DEPTHNAME) {
		TR_DEPTHNAME = tR_DEPTHNAME;
	}
	public String getTR_PARENTIDX() {
		return TR_PARENTIDX;
	}
	public void setTR_PARENTIDX(String tR_PARENTIDX) {
		TR_PARENTIDX = tR_PARENTIDX;
	}
	public int getTR_DEPTH() {
		return TR_DEPTH;
	}
	public void setTR_DEPTH(int tR_DEPTH) {
		TR_DEPTH = tR_DEPTH;
	}
	public String getPARENTNM() {
		return PARENTNM;
	}
	public void setPARENTNM(String pARENTNM) {
		PARENTNM = pARENTNM;
	}
	public String getBRANDCODE() {
		return BRANDCODE;
	}
	public void setBRANDCODE(String bRANDCODE) {
		BRANDCODE = bRANDCODE;
	}
	public String getFRANCCODE() {
		return FRANCCODE;
	}
	public void setFRANCCODE(String fRANCCODE) {
		FRANCCODE = fRANCCODE;
	}
}