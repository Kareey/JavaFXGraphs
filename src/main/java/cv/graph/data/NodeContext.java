package cv.graph.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NodeContext {
	ObservableList<UiNodeWithValue> drawnUinodes;

	LinkedList<Integer> usedNumbers = new LinkedList<>();

	public NodeContext() {
		drawnUinodes = FXCollections.observableArrayList();
	}

	public ObservableList<UiNodeWithValue> getDrawnUinodes() {
		return drawnUinodes;
	}

	public void setDrawnUinodes(ObservableList<UiNodeWithValue> drawnUinodes) {
		this.drawnUinodes = drawnUinodes;
	}
	public void add(UiNodeWithValue node){
		drawnUinodes.add(node);
	}

	public UiNodeWithValue search(int number){
		for(UiNodeWithValue node : getDrawnUinodes()){
			if(node.getValue() == number){
				return node;
			}
		}
		return null;
	}
	public void removeNode(UiNodeWithValue node){
		UiNodeWithValue res = search(node.getValue().intValue());
		if(res!=null){
			usedNumbers.add(res.getValue());
			getDrawnUinodes().remove(res);
		}
	}

	public LinkedList<Integer> getUsedNumbers() {
		return usedNumbers;
	}

	public void setUsedNumbers(LinkedList<Integer> usedNumbers) {
		this.usedNumbers = usedNumbers;
	}
}
