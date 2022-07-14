package cv.graph.data;

import javafx.scene.shape.Line;

public class UIDataLine<E extends UiNodeWithValue> extends Line {
	private E start;
	private E end;
	public UIDataLine() {

	}
	public UIDataLine(UIDataLine theOther) {
			super(theOther.getStartX(),theOther.getStartY(),theOther.getEndX(),theOther.getEndY());
			this.start = (E) theOther.getStart();
			this.end = (E) theOther.getEnd();
	}
	public UIDataLine(UIDataLine theOther,double endX,double endY) {
		super(theOther.getStartX(),theOther.getStartY(),endX,endY);
		this.start = (E) theOther.getStart();
		this.end = (E) theOther.getEnd();
	}
	public UIDataLine(double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
	}

	public E getStart() {
		return start;
	}

	public void setStart(E start) {
		this.start = start;
	}

	public E getEnd() {
		return end;
	}

	public void setEnd(E end) {
		this.end = end;
	}

}
