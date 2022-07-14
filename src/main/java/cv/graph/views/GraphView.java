package cv.graph.views;

import java.util.Stack;

import cv.graph.data.Constants;
import cv.graph.data.NodeContext;
import cv.graph.data.UIDataLine;
import cv.graph.data.UiNodeWithValue;
import cv.graph.handlers.IBasicCMHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public abstract class GraphView<E extends UiNodeWithValue> {
	protected Stage stage;
	protected NodeContext nodeContext;

	protected Integer value = 1;
	protected boolean isContextMenuAction = false;
	protected ContextMenu cm = null;
	protected Pane newPane;
	protected Scene newScene;
	protected UIDataLine<E> currentPath = null;
	protected Label lbCoord = new Label("");


	protected EventHandler<MouseEvent> highlight = e -> {
	Shape shape = (Shape) e.getTarget();
	if(shape instanceof  Line){
		((Line)shape).setStroke(Paint.valueOf("yellow"));
	}
		shape.setFill(Paint.valueOf("yellow"));
	};
	protected EventHandler<MouseEvent> handleGraphSetup;


	protected Stack<UiNodeWithValue> actionStack = new Stack<>();
	protected IBasicCMHandler cmHandler;
	protected boolean initialized = false;


	public GraphView(Stage stage) {
		this.stage = stage;
		this.nodeContext = new NodeContext();
	}

	public  void beforeSetup(){
		newScene = stage.getScene();
		newPane = (Pane) newScene.getRoot();
		lbCoord.setTranslateX(0);
		lbCoord.setTranslateY(newPane.getHeight()-50);
		newPane.getChildren().add(lbCoord);
		setupCmHandler();
	}
	public  void setup(){
		beforeSetup();
	}





	private boolean isInside(MouseEvent event, UiNodeWithValue uiNode) {
		return isInside(event.getX(), event.getY(), uiNode);
	}

	private boolean isInside(double x, double y, UiNodeWithValue uiNode) {
		double dx = Math.abs(x - uiNode.getCenterX());
		double dy = Math.abs(y - uiNode.getCenterY());
		return (dx * dx + dy * dy <= Constants.RADIUS * Constants.RADIUS);
	}

	protected boolean cursorInsideAnotherNode(MouseEvent event) {
		for (UiNodeWithValue node : nodeContext.getDrawnUinodes()) {
			if (isInside(event, node)) {
				return true;
			}
		}
		return false;
	}

	private UiNodeWithValue searchUiNodeWithCoords(double x, double y) {
		for (UiNodeWithValue node : nodeContext.getDrawnUinodes()) {
			if (isInside(x, y, node)) {
				return node;
			}
		}
		return null;

	}
	protected  void addListeners(Button buttons[], EventHandler<ActionEvent> ... handlers){

	}
	protected abstract void  setupCmHandler();

	protected void removeNode(UiNodeWithValue node){
		newPane.getChildren().remove(node);
		for (Path path:node.getPaths()) {
			newPane.getChildren().remove(path);
		}

		nodeContext.removeNode(node);
		newPane.getChildren().remove(node.getText());
	}

	public abstract void addMousePressedHandler(UiNodeWithValue node);
	public abstract void addMouseClickedHandler(UiNodeWithValue node);
	public abstract void addMouseDraggedHandler(UiNodeWithValue node);



}
