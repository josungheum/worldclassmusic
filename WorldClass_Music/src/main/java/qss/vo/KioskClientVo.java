package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ KioskClientVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class KioskClientVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private BigInteger kioskClientIdx;	//일련번호
	private String version;			//버전
	private String fileName;		//파일이름
	private String oldFileName;		//파일이름
	private String changes;			//변경내용
	private String detail;			//상세내용
	private int distCount;			//배포건수
	
	private String[] checkboxArr;	//체크된 배열값
	
	
	
	
	public BigInteger getKioskClientIdx() {
		return kioskClientIdx;
	}
	public void setKioskClientIdx(BigInteger kioskClientIdx) {
		this.kioskClientIdx = kioskClientIdx;
	}
	public String[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(String[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public String getOldFileName() {
		return oldFileName;
	}
	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getChanges() {
		return changes;
	}
	public void setChanges(String changes) {
		this.changes = changes;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public int getDistCount() {
		return distCount;
	}
	public void setDistCount(int distCount) {
		this.distCount = distCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
}