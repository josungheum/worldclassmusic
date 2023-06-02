package qss.vo;

import java.util.ArrayList;
import java.util.List;

public class MenuResultVo {
	private long Idx;
	private String Name;
	private String EnName;
	private int Level;
	private int OrderSeq;
	private int ParentIdx;
	private List<MenuResultVo> menus = new ArrayList<MenuResultVo>();
	private List<OrderProdResultVo> Products = new ArrayList<OrderProdResultVo>();

	public long getIdx() {
		return Idx;
	}

	public void setIdx(long idx) {
		Idx = idx;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEnName() {
		return EnName;
	}

	public void setEnName(String enName) {
		EnName = enName;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public int getOrderSeq() {
		return OrderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		OrderSeq = orderSeq;
	}

	public int getParentIdx() {
		return ParentIdx;
	}

	public void setParentIdx(int parentIdx) {
		ParentIdx = parentIdx;
	}

	public List<MenuResultVo> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuResultVo> menus) {
		this.menus = menus;
	}

	public List<OrderProdResultVo> getProducts() {
		return Products;
	}

	public void setProducts(List<OrderProdResultVo> products) {
		Products = products;
	}
}