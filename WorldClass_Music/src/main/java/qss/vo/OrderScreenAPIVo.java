package qss.vo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class OrderScreenAPIVo {
	private static final long serialVersionUID = 1L;
	
	//주문화면 테이블
	private BigInteger orderScreenIdx;			//주문화면 일련번호
	private String orderScreenName;				//주문화면 명
	private List<OrderScreenMenuVo> orderScreenMenuList = new ArrayList<OrderScreenMenuVo>();

	public BigInteger getOrderScreenIdx() {
		return orderScreenIdx;
	}

	public void setOrderScreenIdx(BigInteger orderScreenIdx) {
		this.orderScreenIdx = orderScreenIdx;
	}

	public String getOrderScreenName() {
		return orderScreenName;
	}

	public void setOrderScreenName(String orderScreenName) {
		this.orderScreenName = orderScreenName;
	}

	public List<OrderScreenMenuVo> getOrderScreenMenuList() {
		return orderScreenMenuList;
	}

	public void setOrderScreenMenuList(List<OrderScreenMenuVo> orderScreenMenuList) {
		this.orderScreenMenuList = orderScreenMenuList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
	
}