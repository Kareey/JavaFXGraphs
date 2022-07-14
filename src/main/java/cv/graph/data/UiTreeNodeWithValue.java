package cv.graph.data;

import javafx.scene.paint.Paint;

public class UiTreeNodeWithValue extends UiNodeWithValue {
	private UiTreeNodeWithValue parentNode;
	public int level =-1;
	public UiTreeNodeWithValue(boolean root) {
		this.root = root;
	}

	public UiTreeNodeWithValue(Integer value, boolean root) {
		super(value);
		this.root = root;
	}

	public UiTreeNodeWithValue(double radius, Paint fill, boolean root) {
		super(radius, fill);
		this.root = root;
	}

	public UiTreeNodeWithValue(double centerX, double centerY, Integer value, boolean root) {
		super(centerX, centerY, value);
		this.root = root;
	}

	private boolean root;

	public boolean isRoot() {
		return root;
	}

	public UiTreeNodeWithValue getParentNode() {
		return parentNode;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public void setParentNode(UiTreeNodeWithValue parentNode) {
		this.parentNode = parentNode;
	}

	public void setRoot() {
		if(this.getParentNode() != null) setParentNode(null);
		this.root = true;
	}

	public void printTree(boolean needRoot){
		UiTreeNodeWithValue root = this;
		while( needRoot &&!root.isRoot())root = root.getParentNode();
		System.out.println(root);
		if(root.getNodeChildren() != null){
			for (UiNodeWithValue child : root.getNodeChildren()){
				((UiTreeNodeWithValue)child).printTree(false);
			}
		}
	}

	public static boolean isChildOf(UiTreeNodeWithValue current, UiTreeNodeWithValue parent){
		boolean contains = false;
		if(parent!=null){
			if(parent.getNodeChildren()!=null && !parent.getNodeChildren().isEmpty() && parent.getNodeChildren().contains(current)){
				return true;
			}
			if(parent.getNodeChildren()!=null && !parent.getNodeChildren().isEmpty()){
				for (UiNodeWithValue child : parent.getNodeChildren()) {
					 contains  = isChildOf(current,(UiTreeNodeWithValue) child);
				}
			}
		}
		return contains;

	}

}
