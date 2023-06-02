package qss.vo;

/**
 * <pre>
 * qss.vo 
 *    |_ DomainVo.java
 * 
 * </pre>
 * @date : 2018. 12. 21. 오후 5:12:29
 * @version : 
 * @author : ksh
 */


public class DomainVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private String domainName;        // 도메인명
	
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}