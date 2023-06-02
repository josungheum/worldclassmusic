package qss.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class VaildateVo extends CommonVo{
	private static final long serialVersionUID = 1L;
	
	public static final String MSG_001 = "디바이스 아이디를 확인해주세요.";
	public static final String MSG_002 = "존재하지 않는 디바이스 입니다.";
	public static final String MSG_003 = "관리자에게 문의하세요.";
	public static final String MSG_SUC = "성공";
	public static final String RES_SUC = "SUCCESS";
	public static final String RES_FAIL = "FAIL";
	public static final int default_pollingTime = 120; 
	
	private String deviceId;
	private String systemTime;
	private int polling;
	private String result;
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSystemTime() {
		return systemTime;
	}
	public void setSystemTime(String systemTime) {
		this.systemTime = systemTime;
	}
	public int getPolling() {
		return polling;
	}
	public void setPolling(int polling) {
		this.polling = polling;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
