package trees;

public class BalancedTree {

    private Node root;

    public BalancedTree(Node root) {
        this.root = root;
    }

    public void insert(Node key) {
        root = insertNode(root, key);
    }

    private Node insertNode(Node root, Node key) {
        if (root == null) {
            return key;
        }

        if (key.key < root.key) {
            root.left = insertNode(root.left, key);
            root.setLeftHeight(Math.max(root.left.getLeftHeight(), root.left.getRightHeight()) + 1);
        } else if (key.key > root.key) {
            root.right = insertNode(root.right, key);
            root.setRightHeight(Math.max(root.right.getLeftHeight(), root.right.getRightHeight()) + 1);
        }

        return balance(root);
    }

    private Node balance(Node root) {
        var balanceFactor = root.getRightHeight() - root.getLeftHeight();

        if (balanceFactor == 2) {
            if (root.right.getRightHeight() >= root.right.getLeftHeight()) {
                return leftRotation(root);
            }

            root.right = rightRotation(root.right);
            return leftRotation(root);
        } else if (balanceFactor == -2) {
            if (root.left.getLeftHeight() >= root.left.getRightHeight()) {
                return rightRotation(root);
            }

            root.left = leftRotation(root.left);
            return rightRotation(root);
        }

        return root;
    }

    private Node leftRotation(Node root) {
        var newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private Node rightRotation(Node root) {
        var newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;

        updateHeight(root);
        updateHeight(newRoot);

        return newRoot;
    }

    private void updateHeight(Node root) {
        if (root != null) {
            root.setLeftHeight(root.left == null ? 0 : Math.max(root.left.getLeftHeight(), root.left.getRightHeight()));

            root.setRightHeight(root.right == null ? 0 : Math.max(root.right.getLeftHeight(), root.right.getRightHeight()));
        }
    }

    public String toString() {
        return show(root);
    }

    private String show(Node root) {
        final var sb = new StringBuilder();
        if (root != null) {
            sb.append(show(root.left));
            sb.append(root.key);
            sb.append(show(root.right));
        }

        return sb.toString();
    }
}
