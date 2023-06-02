package qss.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

public class RestAPIVo extends CommonVo implements Serializable  {
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Please enter deviceCode")
	private String deviceCode;		//장치코드

	private String version;								//버전
	private String searchDay;							//검색시작일
	private String endDay;								//검색종료일

	private String RetData;								//반환값
	private Object RetObjectData;						//반환값(object)
	private String errorCode;							//에러 코드
	private String errorMessage;						//에러 메시지
	private int lastVersion;							//최종버전
	private String lastClientVersion;					//최종버전
	private List<OrderProdVo> orderProdList; 			//상품 목록
	private List<OrderMasterVo> orderMasterList; 		//주문 목록
	private List<KioskResourceVo> kioskResourceList; 	//리소스 목록
	private String clientType;
	
	public Object getRetObjectData() {
		return RetObjectData;
	}

	public void setRetObjectData(Object retObjectData) {
		RetObjectData = retObjectData;
	}

	public int getLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(int lastVersion) {
		this.lastVersion = lastVersion;
	}

	public String getLastClientVersion() {
		return lastClientVersion;
	}

	public void setLastClientVersion(String lastClientVersion) {
		this.lastClientVersion = lastClientVersion;
	}

	public List<OrderMasterVo> getOrderMasterList() {
		return orderMasterList;
	}

	public void setOrderMasterList(List<OrderMasterVo> orderMasterList) {
		this.orderMasterList = orderMasterList;
	}

	public List<KioskResourceVo> getKioskResourceList() {
		return kioskResourceList;
	}

	public void setKioskResourceList(List<KioskResourceVo> kioskResourceList) {
		this.kioskResourceList = kioskResourceList;
	}

	public List<OrderProdVo> getOrderProdList() {
		return orderProdList;
	}

	public void setOrderProdList(List<OrderProdVo> orderProdList) {
		this.orderProdList = orderProdList;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRetData() {
		return RetData;
	}

	public void setRetData(String retData) {
		RetData = retData;
	}


	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}



	public String getSearchDay() {
		return searchDay;
	}

	public void setSearchDay(String searchDay) {
		this.searchDay = searchDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}



}