package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo
 *    |_ KioskResourceVo.java
 *
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version :
 * @author : khk
 */
public class KioskResourceVo extends CommonVo{

	private static final long serialVersionUID = 1L;

	private String resourceName;			//리소스명
	private String resourceValue;		//리소스값
	private String deviceCode;				//api 조회용
	private String connectStartTime;		//접속시작 시간
	private String connectStartTimeYn;		//접속시작 시간 update 유무




	public String getConnectStartTimeYn() {
		return connectStartTimeYn;
	}
	public void setConnectStartTimeYn(String connectStartTimeYn) {
		this.connectStartTimeYn = connectStartTimeYn;
	}
	public String getConnectStartTime() {
		return connectStartTime;
	}
	public void setConnectStartTime(String connectStartTime) {
		this.connectStartTime = connectStartTime;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getResourceValue() {
		return resourceValue;
	}
	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}



}
