package qss.vo;

/**
 * <pre>
 * qss.vo
 *    |_ KioskVo.java
 *
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version :
 * @author : khk
 */
public class KioskVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private String deviceCode;			//단말코드
	private String deviceName;			//단말명
	private String deviceType;			//단말타입
	private String colCountType;		//단말형태 타입

	private String deviceVersion;		//버전
	private String connectTime;			//접속유지시간




	public String getDeviceVersion() {
		return deviceVersion;
	}
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public String getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(String connectTime) {
		this.connectTime = connectTime;
	}
	public String getColCountType() {
		return colCountType;
	}
	public void setColCountType(String colCountType) {
		this.colCountType = colCountType;
	}
	
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}




}