package cv.graph.views;

import java.util.ArrayList;
import java.util.Map;

import cv.graph.data.UIDataLine;
import cv.graph.data.UiNodeWithValue;
import cv.graph.handlers.ButtonListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class NormalGraphView extends GraphView<UiNodeWithValue> implements ButtonListener {


	public NormalGraphView(Stage stage) {
		super(stage);
	}

	@Override
	public void setup() {
		super.setup();
		setupNodes();
	}
	protected void setupNodes(){
		newPane.setOnMouseClicked(event -> {
			if (!(event.getTarget() == newPane)) {
				return;
			}
			if (!cursorInsideAnotherNode(event) && event.getButton().equals(MouseButton.PRIMARY)) {
				UiNodeWithValue node = new UiNodeWithValue(event.getX(), event.getY(), ++value);
				node.setOnMouseClicked(cmHandler);
				node.setOnMousePressed(event1 -> {
					UIDataLine<UiNodeWithValue> path = null;
					if (event1.getButton().equals(MouseButton.PRIMARY) && actionStack.isEmpty()) {
						path = new UIDataLine<>();
						path.setStrokeWidth(1);
						path.setStroke(Color.BLACK);
//						path.getElements().add(new MoveTo(((UiNodeWithValue) event1.getTarget()).getCenterX(), ((UiNodeWithValue) event1.getTarget()).getCenterY()));
						actionStack.push((UiNodeWithValue) event1.getTarget());
						currentPath = path;
					} else if (event1.getButton().equals(MouseButton.PRIMARY) && actionStack.peek() != event1.getTarget()) {
						UiNodeWithValue source = actionStack.peek();
						UiNodeWithValue target = (UiNodeWithValue) event1.getTarget();
						if (source.getNodeChildren() == null) {
							source.setNodeChildren(new ArrayList<>());
						}
						source.getNodeChildren().add(target);
//						currentPath.getElements().add(new LineTo(target.getCenterX(), target.getCenterY()));
						newPane.getChildren().add(currentPath);
						actionStack.pop();
						currentPath = null;
					}
				});
				node.setOnMouseEntered(highlight);
				node.setOnMouseExited(e -> {
					Shape shape = (Shape) e.getTarget();
					shape.setFill(Paint.valueOf("green"));
				});
				newPane.getChildren().addAll(node, node.getText());
				nodeContext.add(node);
			}
		});

	}

	@Override
	protected void setupCmHandler() {
		if(cm == null){
			cm = new ContextMenu();
		}

		cmHandler = event->{
				cm.getItems().clear();
				if (event.getTarget() != null &&  event.getButton().equals(MouseButton.SECONDARY)) {

					UiNodeWithValue innerNode = (UiNodeWithValue) event.getTarget();
					MenuItem drawLine = new MenuItem("Draw line");
					drawLine.setOnAction(event2 -> {
						isContextMenuAction = true;
						event2.consume();
					});
					cm.getItems().add(drawLine);
					cm.show(newScene.getWindow(), stage.getX() + event.getX(), stage.getY() + event.getY());
				}
				event.consume();
		};
	}

	@Override
	public void addMousePressedHandler(UiNodeWithValue node) {

	}

	@Override
	public void addMouseClickedHandler(UiNodeWithValue node) {

	}

	@Override
	public void addMouseDraggedHandler(UiNodeWithValue node) {

	}

	public void addListener(Button btn, EventHandler<ActionEvent> event, Map<String,Object> params) {
		btn.setOnAction(event);
	}
}
