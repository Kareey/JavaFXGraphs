package cv.graph.data;

public class UIDirectedDataLine extends UIDataLine<UiNodeWithValue> {
	protected float angleX;
	protected float angleY;

	public UIDirectedDataLine(UIDataLine theOther, double endX, double endY) {
		super(theOther, endX, endY);
		computeAngleX();
		computeAngleY();
	}

	protected void computeAngleX(){}

	protected void computeAngleY(){}


}
