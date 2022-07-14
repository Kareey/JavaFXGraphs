package cv.graph.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import cv.graph.data.UiNodeWithValue;
import cv.graph.data.UiTreeNodeWithValue;

public class BaseAlgorithms {

	public static void bfs(UiTreeNodeWithValue elem, long delay, int counter) {
		Queue<UiTreeNodeWithValue> queue = new ArrayDeque<>();
		List<UiTreeNodeWithValue> visited = new ArrayList<>();
		queue.add(elem);
		visited.add(elem);
		while (!queue.isEmpty()) {
			UiTreeNodeWithValue inner = queue.poll();
			inner.playAnimation(delay * counter);
			if (inner.getNodeChildren() != null) {
				for (UiNodeWithValue child : inner.getNodeChildren()) {
					if (!visited.contains(child)) {
						queue.add((UiTreeNodeWithValue) child);
						visited.add((UiTreeNodeWithValue) child);
					}
				}
			}
			counter++;
		}
	}

	public static void dfs(UiTreeNodeWithValue elem, long delay, int counter) {
		Stack<UiTreeNodeWithValue> stack = new Stack<>();
		List<UiTreeNodeWithValue> visited = new ArrayList<>();
		stack.add(elem);
		visited.add(elem);
		while (!stack.isEmpty()) {
			UiTreeNodeWithValue inner = stack.pop();
			inner.playAnimation(delay * counter);
			if (inner.getNodeChildren() != null) {

				for (UiNodeWithValue child : inner.getNodeChildren()) {
					UiTreeNodeWithValue current = (UiTreeNodeWithValue) child;
					while(current!=null){
						current = (UiTreeNodeWithValue) current.getNodeChildren().get(0);
					}

				}
			}
			counter++;
		}

	}
}
