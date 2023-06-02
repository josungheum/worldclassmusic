package qss.vo;

public class ReceiptVo extends CommonVo {
	
	private static final long serialVersionUID = 1L;
	
	private int IDX;
	private int IMGPATH;
	private String ADDR1;
	private String ADDR2;
	private String ADDR3;
	private String CHARGER;
	private String CONTACT;
	private String COMMENT1;
	private String COMMENT2;
	private String COMMENT3;
	private String COMMENT4;
	private String COMMENT5;
	
	// 맺음말 타입변수 Text Barcode
	private String commentType1;
	private String commentType2;
	private String commentType3;
	private String commentType4;
	private String commentType5;
	
	// 유저 소속정보
	private int DOMAIN_IDX;
	private int BRAND_IDX;
	private int FRANCHISEE_IDX;
	
	// 가맹점 목록 Tree
	private int TR_IDX;
	private String TR_NAME;
	private String TR_DEPTHNAME;
	private String TR_PARENTIDX;
	private int TR_DEPTH;
	
	// Tree Index
	private String SM_PINDEX;
	
	// sql 반환값
	private int Result;
	
	// FILE 정보
	private int FileIdx;
	private String FileName;
	private String FileSaveName;
	private String SavePath;
	private int FileSize;
	private String CheckSum;
	
	
	public int getIDX() {
		return IDX;
	}
	public void setIDX(int iDX) {
		IDX = iDX;
	}
	public int getIMGPATH() {
		return IMGPATH;
	}
	public void setIMGPATH(int iMGPATH) {
		IMGPATH = iMGPATH;
	}
	public String getADDR1() {
		return ADDR1;
	}
	public void setADDR1(String aDDR1) {
		ADDR1 = aDDR1;
	}
	public String getADDR2() {
		return ADDR2;
	}
	public void setADDR2(String aDDR2) {
		ADDR2 = aDDR2;
	}
	public String getADDR3() {
		return ADDR3;
	}
	public void setADDR3(String aDDR3) {
		ADDR3 = aDDR3;
	}
	public String getCHARGER() {
		return CHARGER;
	}
	public void setCHARGER(String cHARGER) {
		CHARGER = cHARGER;
	}
	public String getCONTACT() {
		return CONTACT;
	}
	public void setCONTACT(String cONTACT) {
		CONTACT = cONTACT;
	}
	public String getCOMMENT1() {
		return COMMENT1;
	}
	public void setCOMMENT1(String cOMMENT1) {
		COMMENT1 = cOMMENT1;
	}
	public String getCOMMENT2() {
		return COMMENT2;
	}
	public void setCOMMENT2(String cOMMENT2) {
		COMMENT2 = cOMMENT2;
	}
	public String getCOMMENT3() {
		return COMMENT3;
	}
	public void setCOMMENT3(String cOMMENT3) {
		COMMENT3 = cOMMENT3;
	}
	public String getCOMMENT4() {
		return COMMENT4;
	}
	public void setCOMMENT4(String cOMMENT4) {
		COMMENT4 = cOMMENT4;
	}
	public String getCOMMENT5() {
		return COMMENT5;
	}
	public void setCOMMENT5(String cOMMENT5) {
		COMMENT5 = cOMMENT5;
	}
	public int getDOMAIN_IDX() {
		return DOMAIN_IDX;
	}
	public void setDOMAIN_IDX(int dOMAIN_IDX) {
		DOMAIN_IDX = dOMAIN_IDX;
	}
	public int getBRAND_IDX() {
		return BRAND_IDX;
	}
	public void setBRAND_IDX(int bRAND_IDX) {
		BRAND_IDX = bRAND_IDX;
	}
	public int getFRANCHISEE_IDX() {
		return FRANCHISEE_IDX;
	}
	public void setFRANCHISEE_IDX(int fRANCHISEE_IDX) {
		FRANCHISEE_IDX = fRANCHISEE_IDX;
	}
	public int getTR_IDX() {
		return TR_IDX;
	}
	public void setTR_IDX(int tR_IDX) {
		TR_IDX = tR_IDX;
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
	public String getSM_PINDEX() {
		return SM_PINDEX;
	}
	public void setSM_PINDEX(String sM_PINDEX) {
		SM_PINDEX = sM_PINDEX;
	}
	public String getCommentType1() {
		return commentType1;
	}
	public void setCommentType1(String commentType1) {
		this.commentType1 = commentType1;
	}
	public String getCommentType2() {
		return commentType2;
	}
	public void setCommentType2(String commentType2) {
		this.commentType2 = commentType2;
	}
	public String getCommentType3() {
		return commentType3;
	}
	public void setCommentType3(String commentType3) {
		this.commentType3 = commentType3;
	}
	public String getCommentType4() {
		return commentType4;
	}
	public void setCommentType4(String commentType4) {
		this.commentType4 = commentType4;
	}
	public String getCommentType5() {
		return commentType5;
	}
	public void setCommentType5(String commentType5) {
		this.commentType5 = commentType5;
	}
	public int getResult() {
		return Result;
	}
	public void setResult(int result) {
		Result = result;
	}
	public int getFileIdx() {
		return FileIdx;
	}
	public void setFileIdx(int fileIdx) {
		FileIdx = fileIdx;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFileSaveName() {
		return FileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		FileSaveName = fileSaveName;
	}
	public String getSavePath() {
		return SavePath;
	}
	public void setSavePath(String savePath) {
		SavePath = savePath;
	}
	public int getFileSize() {
		return FileSize;
	}
	public void setFileSize(int fileSize) {
		FileSize = fileSize;
	}
	public String getCheckSum() {
		return CheckSum;
	}
	public void setCheckSum(String checkSum) {
		CheckSum = checkSum;
	}
	
	
}
