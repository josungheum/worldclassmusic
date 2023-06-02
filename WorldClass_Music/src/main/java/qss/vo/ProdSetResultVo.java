package qss.vo;

import java.util.ArrayList;
import java.util.List;

public class ProdSetResultVo {
	private int Idx;
	private int ProdIdx;
	private String Name;
	private int OrderSeq;
	private String PrintGroupName;
	private String PrintGroupCode;
	private List<ProdSetItemResultVo> Items = new ArrayList<ProdSetItemResultVo>();

	public int getIdx() {
		return Idx;
	}

	public void setIdx(int idx) {
		Idx = idx;
	}

	public int getProdIdx() {
		return ProdIdx;
	}

	public void setProdIdx(int prodIdx) {
		ProdIdx = prodIdx;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getOrderSeq() {
		return OrderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		OrderSeq = orderSeq;
	}

	public List<ProdSetItemResultVo> getItems() {
		return Items;
	}

	public void setItems(List<ProdSetItemResultVo> items) {
		Items = items;
	}

	public String getPrintGroupName() {
		return PrintGroupName;
	}

	public void setPrintGroupName(String printGroupName) {
		PrintGroupName = printGroupName;
	}

	public String getPrintGroupCode() {
		return PrintGroupCode;
	}

	public void setPrintGroupCode(String printGroupCode) {
		PrintGroupCode = printGroupCode;
	}
}
