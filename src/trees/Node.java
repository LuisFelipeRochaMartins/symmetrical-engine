package trees;

public class Node {
	private Integer key;
	private Node left, right;

	public Node(Integer key) {
		this.key = key;
		left = right = null;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
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