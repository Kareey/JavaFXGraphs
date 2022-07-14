package cv.graph.data;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class UiNodeWithValue extends Circle {
	public static class Edge{
		private UiNodeWithValue source;
		private UiNodeWithValue target;


		public Edge(UiNodeWithValue source, UiNodeWithValue target) {
			this.source = source;
			this.target = target;
		}

		public UiNodeWithValue getSource() {
			return source;
		}

		public void setSource(UiNodeWithValue source) {
			this.source = source;
		}

		public UiNodeWithValue getTarget() {
			return target;
		}

		public void setTarget(UiNodeWithValue target) {
			this.target = target;
		}
	}
	private Integer value;
	private Text text = new Text();


	private List<UiNodeWithValue> children;
	protected List<Edge> edges;

	protected List<Path> paths = new ArrayList<>();

	public List<Path> getPaths() {
		return paths;
	}

	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}


	public UiNodeWithValue(){
		super(Constants.RADIUS,Paint.valueOf("green"));
	}

	public UiNodeWithValue(Integer value) {
		super(Constants.RADIUS,Paint.valueOf("green"));
		this.value = value;
		this.text.xProperty().bind(centerXProperty());
		this.text.yProperty().bind(centerYProperty());

		this.text.setText(""+value);
		this.text.setFont(Font.font("arial", FontWeight.BOLD,12));
	}
	public UiNodeWithValue(double radius, Paint fill) {
		super(radius, fill);
	}

	public UiNodeWithValue(double centerX, double centerY, Integer value) {
		super(centerX, centerY, Constants.RADIUS,Paint.valueOf("green"));
		this.value = value;
		this.text.xProperty().bind(centerXProperty());
		this.text.yProperty().bind(centerYProperty());
		this.text.setText(""+value);
		this.text.setFont(Font.font("arial", FontWeight.BOLD,12));
	}

	public void addChild(UiNodeWithValue child){
		if(child!= null){
			if(child.value.intValue() == this.value.intValue()){
				throw new IllegalArgumentException("New child must have unique value!");
			}
			children.add(child);

		}
	}

	public  List<UiNodeWithValue> getNodeChildren() {
		return children;
	}

	public void setNodeChildren(List<UiNodeWithValue> children) {
		this.children = children;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}





	public void addEdge(Edge edge){
		if(edges == null)edges = new ArrayList<>();
		edges.add(edge);
	}
	public List<Edge> getEdges(){
		return edges;
	}
	public boolean hasEdge(UiNodeWithValue source,UiNodeWithValue target){
		return getEdge(source,target)!=null;
	}
	public Edge getEdge(UiNodeWithValue source,UiNodeWithValue target){
		if(edges != null){
			for(Edge edge : getEdges()){
				if(edge.getSource().equals(source) && edge.getTarget().equals(target)){
					return edge;
				}
			}
		}
		return null;
	}
	public void playAnimation(long delay){
		FillTransition anim = new FillTransition(Duration.seconds(1), Color.GREEN,Color.YELLOW);
		anim.setShape(this);
		anim.setDelay(Duration.millis(delay));
		anim.play();
//		AnimationHolder.FILL_ANIMATION.setShape(this);
//		AnimationHolder.FILL_ANIMATION.jumpTo(Duration.ZERO);
//		AnimationHolder.FILL_ANIMATION.play();
	}

	@Override
	public boolean equals(Object obj) {
			if(!(obj instanceof UiNodeWithValue)) return false;
			UiNodeWithValue other = (UiNodeWithValue)obj;
			return other.getValue().intValue() == this.getValue().intValue();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return ""+getValue();
	}

}
