package qss.vo;

import java.math.BigInteger;

/**
 * <pre>
 * qss.vo 
 *    |_ ScheduleTargetVo.java
 * 
 * </pre>
 * @date : 2018. 12. 24. 오후 5:12:29
 * @version : 
 * @author : khk
 */
public class ScheduleTargetVo extends CommonVo{
	private static final long serialVersionUID = 1L;
	
	private BigInteger mainScheduleIdx;		//메인 스케줄 일련번호
	
	
	public BigInteger getMainScheduleIdx() {
		return mainScheduleIdx;
	}
	public void setMainScheduleIdx(BigInteger mainScheduleIdx) {
		this.mainScheduleIdx = mainScheduleIdx;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
