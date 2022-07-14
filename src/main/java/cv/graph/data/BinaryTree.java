package cv.graph.data;

public class BinaryTree<E extends UiTreeNodeWithValue> extends Tree {
	private E left;
	private E right;
	public BinaryTree(E root) {
		super(root);
		this.left = null;
		this.right = null;
	}

	public BinaryTree(E root, E left, E right) {
		super(root);
		this.left = left;
		this.right = right;
	}
}
