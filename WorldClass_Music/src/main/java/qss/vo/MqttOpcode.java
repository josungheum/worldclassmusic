package qss.vo;

public enum MqttOpcode {
	
	SCHEDULE_START("OP0006"),
	
	SCHEDULE_END("OP0007");
	
	
	private String code;

	private MqttOpcode(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}
}
