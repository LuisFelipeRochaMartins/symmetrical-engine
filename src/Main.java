import trees.BinaryTree;

public class Main {

	public static void main(String[] args) {
		var arvore = new BinaryTree();

		arvore.insert(8);
		arvore.insert(3);
		arvore.insert(1);
		arvore.insert(6);
		arvore.insert(4);

		System.out.println(arvore);
		arvore.delete(4);
		System.out.println(arvore);
	}
}
