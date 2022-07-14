package cv.graph.data;

import java.util.ArrayList;
import java.util.List;

public class Tree<E extends UiTreeNodeWithValue> extends Graph {
	protected E root;
	private List<E> elements;
	public int levels;

	public Tree() {
	}

	public Tree(E root) {
		this.root = root;
	}

	public E getRoot() {
		return root;
	}

	public void setRoot(E root) {
		this.root = root;
	}

	public List<E> getElements() {
		return elements;
	}

	public List<E> getPath(E start){
		List<E> result = new ArrayList<>();
		result.add(start);
		E temp = start;
		while(temp.getParentNode()!=null){
			temp = (E)temp.getParentNode();
			if(temp!=null){
				result.add(temp);
			}
		}

		return result;
	}
}
