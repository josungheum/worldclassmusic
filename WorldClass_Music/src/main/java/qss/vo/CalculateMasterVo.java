package qss.vo;

/**
 * <pre>
 * qss.vo 
 *    |_ CalculateMasterVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class CalculateMasterVo extends CommonVo {
	private static final long serialVersionUID = 1L;
	
	private String calculateDate;	//정산일
	private String deviceName;		//단말명
	
	public String getCalculateDate() {
		return calculateDate;
	}
	public void setCalculateDate(String calculateDate) {
		this.calculateDate = calculateDate;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}