package qss.vo;

public class TreeVo extends CommonVo {
	private static final long serialVersionUID = 1L;

	private String id;
	private String parent;
	private String text;
	private boolean checked;
	private StateVo state = new StateVo();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		state.setSelected(checked);
		this.checked = checked;
	}

	public StateVo getState() {
		return state;
	}

	public void setState(StateVo state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TreeVo [id=");
		builder.append(id);
		builder.append(", parent=");
		builder.append(parent);
		builder.append(", text=");
		builder.append(text);
		builder.append(", checked=");
		builder.append(checked);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}
}
