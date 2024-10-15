package trees;

public class BinaryTree {

	private Node root;
	private Integer size = 0;

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

		if (key.key < root.key) {
			root.left = removeData(root.left, key);
		} else if (key.key > root.key) {
			root.right = removeData(root.right,key);
		} else {
			if (root.left == null) {
				return root.right;
			} else if (root.right == null) {
				return  root.left;
			} else {
				var successor = findSuccessor(root.right);
				root.key = successor.key;

				root.right = removeData(root.right, successor);
			}
		}
		return root;
	}

	private Node findSuccessor(Node root) {
		while(root.left != null) {
			root = root.left;
		}

		return root;
	}

	private Node insertData(Node root, Node key) {
		if (root == null) {
			root = key;
			return root;
		}

		if (key.key < root.key) {
			root.left = insertData(root.left, key);
		} else if (key.key > root.key) {
			root.right = insertData(root.right, key);
		}

		return root;
	}

	public Integer showGreaterKey(Node root) {
		while(root.right != null) {
			root = root.right;
		}

		return root.key;
	}

	public String toString() {
		return showDesc(this.root);
	}

	private String showDesc(Node root) {
		var sb = new StringBuilder();
		if (root != null) {
			sb.append(showDesc(root.left));
			sb.append(root.key);
			sb.append(showDesc(root.right));
		}

		return sb.toString();
	}
}