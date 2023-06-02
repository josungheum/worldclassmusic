package qss.vo;

import java.util.List;
import java.util.Map;

public class KioskWaitingResultVo {
	private int IDX;
	private int FRANCIDX;
	private String STARTDATE;
	private String ENDDATE;
	private String STARTTIME;
	private String ENDTIME;
	private String ACTIVEYN;
	private int REGUSER;
	private String REGDATE;
	private int MODUSER;
	private String MODDATE;
	private String DELETEYN;
	private String WAITINGTEXT;
	private String ENWAITINGTEXT;
	private String KIOSKIDX;
	private List<Map<String, String>> KIOSKLIST;
	public List<Map<String, String>> getKIOSKLIST() {
		return KIOSKLIST;
	}
	public void setKIOSKLIST(List<Map<String, String>> kIOSKLIST) {
		KIOSKLIST = kIOSKLIST;
	}
	public int getIDX() {
		return IDX;
	}
	public void setIDX(int iDX) {
		IDX = iDX;
	}
	public int getFRANCIDX() {
		return FRANCIDX;
	}
	public void setFRANCIDX(int fRANCIDX) {
		FRANCIDX = fRANCIDX;
	}
	public String getSTARTDATE() {
		return STARTDATE;
	}
	public void setSTARTDATE(String sTARTDATE) {
		STARTDATE = sTARTDATE;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public void setENDDATE(String eNDDATE) {
		ENDDATE = eNDDATE;
	}
	public String getSTARTTIME() {
		return STARTTIME;
	}
	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}
	public String getENDTIME() {
		return ENDTIME;
	}
	public void setENDTIME(String eNDTIME) {
		ENDTIME = eNDTIME;
	}
	public String getACTIVEYN() {
		return ACTIVEYN;
	}
	public void setACTIVEYN(String aCTIVEYN) {
		ACTIVEYN = aCTIVEYN;
	}
	public int getREGUSER() {
		return REGUSER;
	}
	public void setREGUSER(int rEGUSER) {
		REGUSER = rEGUSER;
	}
	public String getREGDATE() {
		return REGDATE;
	}
	public void setREGDATE(String rEGDATE) {
		REGDATE = rEGDATE;
	}
	public int getMODUSER() {
		return MODUSER;
	}
	public void setMODUSER(int mODUSER) {
		MODUSER = mODUSER;
	}
	public String getMODDATE() {
		return MODDATE;
	}
	public void setMODDATE(String mODDATE) {
		MODDATE = mODDATE;
	}
	public String getDELETEYN() {
		return DELETEYN;
	}
	public void setDELETEYN(String dELETEYN) {
		DELETEYN = dELETEYN;
	}
	public String getWAITINGTEXT() {
		return WAITINGTEXT;
	}
	public void setWAITINGTEXT(String wAITINGTEXT) {
		WAITINGTEXT = wAITINGTEXT;
	}
	public String getENWAITINGTEXT() {
		return ENWAITINGTEXT;
	}
	public void setENWAITINGTEXT(String eNWAITINGTEXT) {
		ENWAITINGTEXT = eNWAITINGTEXT;
	}
	public String getKIOSKIDX() {
		return KIOSKIDX;
	}
	public void setKIOSKIDX(String kIOSKIDX) {
		KIOSKIDX = kIOSKIDX;
	}
}