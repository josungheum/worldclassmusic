package qss.vo;

public class StateVo {
	private boolean opened = true;
	private boolean disabled;
	private boolean selected;
	
	public boolean isOpened() {
		return opened;
	}
	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StateVo [opened=");
		builder.append(opened);
		builder.append(", disabled=");
		builder.append(disabled);
		builder.append(", selected=");
		builder.append(selected);
		builder.append("]");
		return builder.toString();
	}
}
