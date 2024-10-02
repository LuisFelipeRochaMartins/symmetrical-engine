package trees;

public class BinaryTree {

	private Node root;
	private Integer size;

	public BinaryTree() {
		this.root = null;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	private Integer size() {
		return size;
	}

	public void insert(Node key) {
		root = insertData(root, key);
		size ++;
	}

	public void insert(Integer key) {
		var node = new Node(key);
		root = insertData(root, node);
		size ++;
	}

	public void delete(Node key) {
		root = removeData(root, key);
		size --;
	}

	public void delete(Integer key) {
		var node = new Node(key);
		removeData(root, node);
		size --;
	}

	private Node removeData(Node root, Node key) {
		if (root == null) {
			return null;
		}

		if (key.getKey() < root.getKey()) {
			root.setLeft(removeData(root.getLeft(), key));
		} else if (key.getKey() > root.getKey()) {
			root.setRight(removeData(root.getRight(),key));
		} else {
			if (root.getLeft() == null) {
				return root.getRight();
			} else if (root.getRight() == null) {
				return  root.getLeft();
			} else {
				var successor = findSuccessor(root.getRight());
				root.setKey(successor.getKey());

				root.setRight(removeData(root.getRight(), successor));
			}
		}
		return root;
	}

	private Node findSuccessor(Node root) {
		while(root.getLeft() != null) {
			root = root.getLeft();
		}

		return root;
	}

	private Node insertData(Node root, Node key) {
		if (root == null) {
			root = key;
			return root;
		}

		if (key.getKey() < root.getKey()) {
			root.setLeft(insertData(root.getLeft(), key));
		} else if (key.getKey() > root.getKey()) {
			root.setRight(insertData(root.getRight(), key));
		}

		return root;
	}

	public Integer showGreaterKey(Node root) {
		while(root.getRight() != null) {
			root = root.getRight();
		}

		return root.getKey();
	}

	public String toString() {
		return showDesc(this.root);
	}

	private String showDesc(Node root) {
		var sb = new StringBuilder();
		if (root != null) {
			sb.append(showDesc(root.getLeft()));
			sb.append(root.getKey());
			sb.append(showDesc(root.getRight()));
		}

		return sb.toString();
	}
}