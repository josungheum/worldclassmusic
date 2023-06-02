package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo
 *    |_ BrandDeviceTypeVo.java
 *
 * </pre>
 * @date : 2018. 2. 13. 오후 5:12:29
 * @version :
 * @author : ksh
 */
public class BrandDeviceTypeVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private BigInteger deviceTypeIdx;	//일련번호
	private String deviceType;			//디바이스 코드타입
	private String deviceTypeName;		//디바이스 코드타입 명



	public BigInteger getDeviceTypeIdx() {
		return deviceTypeIdx;
	}



	public void setDeviceTypeIdx(BigInteger deviceTypeIdx) {
		this.deviceTypeIdx = deviceTypeIdx;
	}



	public String getDeviceType() {
		return deviceType;
	}







	public String getDeviceTypeName() {
		return deviceTypeName;
	}



	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}



	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}




}