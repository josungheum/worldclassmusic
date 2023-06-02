package qss.vo;

import java.util.Map;

public class ResultAPIVo {
	// 0 : Ajax 처리 성공
	// 1 : Logic 오류
	// 2 : System 오류
	private int resultCode;
	private Map<String, Object> messages;
	private Object data;
	
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
}