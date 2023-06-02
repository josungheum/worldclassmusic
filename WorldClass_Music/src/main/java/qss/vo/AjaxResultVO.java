package qss.vo;

import java.util.Map;

public class AjaxResultVO {
	// 0 : Ajax 처리 성공
	// 1 : Logic 오류
	// 2 : System 오류
	private int resultCode;
	private Map<String, Object> messages;
	private Object data;
	private int iTotalDisplayRecords;
	private Object totalData;
	
	
	
	public Object getTotalData() {
		return totalData;
	}
	public void setTotalData(Object totalData) {
		this.totalData = totalData;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public Map<String, Object> getMessages() {
		return messages;
	}
	public void setMessages(Map<String, Object> messages) {
		this.messages = messages;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AjaxResultVO [resultCode=");
		builder.append(resultCode);
		builder.append(", messages=");
		builder.append(messages);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
}
