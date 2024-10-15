package trees;

public class Node {
	public Integer key;
	public Node left, right;
	private Integer leftHeight, rightHeight = 0;

	public Node(Integer key) {
		this.key = key;
		left = right = null;
	}

	public Integer getLeftHeight() {
		return leftHeight;
	}

	public void setLeftHeight(Integer leftHeight) {
		this.leftHeight = leftHeight;
	}

	public Integer getRightHeight() {
		return rightHeight;
	}

	public void setRightHeight(Integer rightHeight) {
		this.rightHeight = rightHeight;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("key = ").append(key);
		sb.append(", left = ").append(left);
		sb.append(", right = ").append(right);
		return sb.toString();
	}
}