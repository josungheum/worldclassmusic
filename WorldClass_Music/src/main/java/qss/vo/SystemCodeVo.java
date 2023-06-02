package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo
 *    |_ SystemCodeVo.java
 *
 * </pre>
 * @date : 2018. 12. 21. 오후 5:16:55
 * @version :
 * @author : khk
 */
public class SystemCodeVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private BigInteger codeIdx;   // 코드 일련번호
	private String codeGroup;	  // 코드그룹
	private String groupName;	  // 그룹명
	private String codeValue;	  // 코드값
	private String codeName;	  // 코드명
	private int codeLevel;		  // 코드레벨
	private String useYn;		  // 사용여부
	private String memo;		  // 상세 설명
	private String modYn;		  // 수정 가능 여부

	private String[] checkboxArr; // 삭제 배열

	private int orderSeq;		  // 정렬순서

	public String[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(String[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public BigInteger getCodeIdx() {
		return codeIdx;
	}
	public void setCodeIdx(BigInteger codeIdx) {
		this.codeIdx = codeIdx;
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
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}


	public String getModYn() {
		return modYn;
	}
	public void setModYn(String modYn) {
		this.modYn = modYn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

}