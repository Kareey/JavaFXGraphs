package cv.graph.views;

import java.util.ArrayList;
import java.util.Optional;

import cv.graph.algorithm.BaseAlgorithms;
import cv.graph.data.AnimationHolder;
import cv.graph.data.Tree;
import cv.graph.data.UIDataLine;
import cv.graph.data.UiNodeWithValue;
import cv.graph.data.UiTreeNodeWithValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NormalTreeView extends NormalGraphView {
	protected UiTreeNodeWithValue root = null;
	private boolean drawing;
	protected Tree<UiTreeNodeWithValue> tree;


	public NormalTreeView(Stage stage) {
		super(stage);
	}

	@Override
	public void setup() {
		newPane.setStyle("-fx-background-color: #7ad9fa");
		Button btnBFS = new Button("Breath First Search");
		Button btnDFS = new Button("Depth First Search");
		Button btnPreorder = new Button("Preorder");
		Button btnInorder = new Button("Inorder");
		Button btnPostOrder = new Button("Postorder");
		VBox frameOptions = new VBox(20, btnBFS, btnDFS, btnPreorder, btnInorder, btnPostOrder);
		frameOptions.setStyle("-fx-border-radius: 10 0 10 10; -fx-border-color: black; -fx-border-width: 1; -fx-background-color: rgba(246,230,230,0.67)");

		frameOptions.setBorder(new Border(new BorderStroke(Paint.valueOf("black"), BorderStrokeStyle.SOLID, null, BorderStroke.THIN)));
		newPane.getChildren().add(frameOptions);
		VBox frameArrange = new VBox();
		frameArrange.setBorder(new Border(new BorderStroke(Paint.valueOf("black"), BorderStrokeStyle.SOLID, null, BorderStroke.THIN)));
		frameArrange.setTranslateX(newPane.getWidth() - 70);
		frameArrange.setTranslateY(newPane.getTranslateY());
		frameArrange.setPrefHeight(100);
		Button btnArrange = new Button("Arrange");
		VBox.setVgrow(btnArrange, Priority.ALWAYS);
		frameArrange.getChildren().add(btnArrange);
		newPane.getChildren().add(frameArrange);


		Button btnInsert = new Button("Insert");

		Button btnDelete = new Button("Delete");
		Label lbKey = new Label("Enter a Key: ");
		TextField tfKey = new TextField();
		HBox editPanel = new HBox(10, lbKey, tfKey, btnInsert, btnDelete);
		editPanel.setTranslateY(newPane.getHeight() - 50);
		editPanel.setTranslateX(newPane.getWidth() / 2 - editPanel.getWidth() / 2 - 150);
		newPane.getChildren().add(editPanel);
		tree = new Tree<UiTreeNodeWithValue>();

		newPane.setOnMouseClicked(event -> {
			if (!cursorInsideAnotherNode(event) && event.getButton().equals(MouseButton.PRIMARY)) {
				Integer theValue = null;
				if (!nodeContext.getUsedNumbers().isEmpty()) {
					theValue = nodeContext.getUsedNumbers().stream().min(Integer::compareTo).get();
					nodeContext.getUsedNumbers().remove(theValue);
					value = theValue;
				} else {
					theValue = value;
				}
				setupNewNode(event, theValue);
			} else if (MouseButton.SECONDARY.equals(event.getButton()) && drawing && currentPath != null) {
				newPane.getChildren().remove(currentPath);
				actionStack.pop();
				currentPath = null;
				return;
			}
		});
		newPane.setOnMouseMoved(event -> {
			if (drawing && !cursorInsideAnotherNode(event)) {
				if (currentPath != null) {
					newPane.getChildren().remove(currentPath);
					currentPath = new UIDataLine<>(currentPath, event.getX(), event.getY());
					UiTreeNodeWithValue currentNode = (UiTreeNodeWithValue) actionStack.peek();
					currentPath.startXProperty().bind(currentNode.centerXProperty());
					currentPath.startYProperty().bind(currentNode.centerYProperty());
					currentPath.setStrokeWidth(2);
					currentPath.setStroke(Color.BLACK);
					newPane.getChildren().add(currentPath);
				}
			}
			event.consume();
		});

		addListener(btnBFS, event -> {
			TextInputDialog dialog = new TextInputDialog();
			String contentText = "Enter starting vertex number: ";
			dialog.setContentText(contentText);
			dialog.setHeaderText("Enter information");

			Optional<String> result = dialog.showAndWait();
			if (!result.isPresent()) {
				throw new IllegalArgumentException("Must not be empty!");
			}
			while ((result.get().equals(""))) {
				dialog.setContentText(contentText + " \nMust not be empty!");
				result = dialog.showAndWait();
			}
			BaseAlgorithms.bfs((UiTreeNodeWithValue) nodeContext.search(Integer.parseInt(result.get())), 1000, 1);
		},null);
		addListener(btnDFS,event -> {
			TextInputDialog dialog = new TextInputDialog();
			String contentText = "Enter starting vertex number: ";
			dialog.setContentText(contentText);
			dialog.setHeaderText("Enter information");

			Optional<String> result = dialog.showAndWait();
			if (!result.isPresent()) {
				throw new IllegalArgumentException("Must not be empty!");
			}
			while ((result.get().equals(""))) {
				dialog.setContentText(contentText + " \nMust not be empty!");
				result = dialog.showAndWait();
			}
			BaseAlgorithms.dfs((UiTreeNodeWithValue) nodeContext.search(Integer.parseInt(result.get())), 1000, 1);
		},null);

		addListener(btnInsert,event -> {
			int candidate = Integer.parseInt(tfKey.getText());
			if(nodeContext.search(candidate) != null){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("A node with this key already exists, please choose another one!");
				alert.show();
			}else{
				setupNewNode(candidate);
			}

		},null);

		initialized = true;

	}

	private void setupNewNode(MouseEvent event, Integer theValue) {
		UiTreeNodeWithValue node = new UiTreeNodeWithValue(event.getX(), event.getY(), theValue, value++ == 1);

		if (node.isRoot()) {
			makeRoot(node);
			tree.setRoot(node);
			tree.levels++;
		}
		setupNodeHandlers(node);
	}



	public void setupNewNode(int value) {
		UiTreeNodeWithValue node = new UiTreeNodeWithValue(newPane.getWidth() / 2, newPane.getHeight() / 2, value , false);
		if(value == this.value.intValue()){
			this.value++;
		}
		setupNodeHandlers(node);
	}


	protected void makeRoot(UiTreeNodeWithValue elem) {
		if (root != null) {
			root.getText().setText(root.getText().getText().substring(0, root.getText().getText().indexOf('R')));
		}
		root = elem;
		root.setRoot();
		root.setParentNode(null);
		root.getText().setText(root.getText().getText() + "\nR");
	}
	private void setupNodeHandlers(UiTreeNodeWithValue node) {
		addMousePressedHandler(node);
		node.setOnMouseClicked(cmHandler);
		node.setOnMouseEntered(highlight);
		node.setOnMouseExited(e -> {
			Shape shape = (Shape) e.getTarget();
			shape.setFill(Paint.valueOf("green"));
		});
		node.setOnMouseDragged(event1 -> {
			node.setCenterX(event1.getX());
			node.setCenterY(event1.getY());
			lbCoord.setText("X: " + event1.getX() + "\n" + "Y: " + event1.getY());
		});
		newPane.getChildren().addAll(node, node.getText());
		nodeContext.add(node);
	}

	@Override
	protected void setupCmHandler() {
		if (cm == null) {
			cm = new ContextMenu();
		}
		cmHandler = event -> {
			cm.getItems().clear();
			UiTreeNodeWithValue innerNode = (UiTreeNodeWithValue) event.getTarget();
			if (event.getButton().equals(MouseButton.SECONDARY)) {
				MenuItem printGraph = new MenuItem("Print tree");
				printGraph.setOnAction(event2 -> {
					innerNode.printTree(true);
				});
				cm.getItems().add(printGraph);
				MenuItem makeRoot = new MenuItem("Make Root");
				makeRoot.setOnAction(event2 -> {
					makeRoot((UiTreeNodeWithValue) event.getTarget());
				});
				cm.getItems().add(makeRoot);

				MenuItem playAnimation = new MenuItem("Play Animation");
				playAnimation.setOnAction(event2 -> {
					((UiTreeNodeWithValue) event.getTarget()).playAnimation(1000);
				});
				cm.getItems().add(playAnimation);

				MenuItem removeNode = new MenuItem("Remove");
				removeNode.setOnAction(event2 -> {
					removeNode(innerNode);
					event2.consume();
				});
				cm.getItems().add(removeNode);
				MenuItem organizeRoot = new MenuItem("Organize Root");
				organizeRoot.setOnAction(event2 -> {
					if (innerNode.isRoot()) {
						moveRootToNorthCenter(innerNode);
					}
					event2.consume();
				});
				cm.getItems().add(organizeRoot);
				MenuItem properties = new MenuItem("Properties");
				properties.setOnAction(event2 -> {
					Alert dialog = new Alert(Alert.AlertType.INFORMATION);
					dialog.setContentText(innerNode.toString() + "\n" + "X coordinate: " + innerNode.getCenterX() + "\nY coordinate: " + innerNode.getCenterY());
					dialog.show();
				});
				cm.getItems().add(properties);
				cm.show(newScene.getWindow(), stage.getX() + event.getX(), stage.getY() + event.getY());
			}
			event.consume();
		};

	}

	private void moveRootToNorthCenter(UiTreeNodeWithValue innerNode) {
		Path path = new Path();
		path.getElements().add(new MoveTo(innerNode.getCenterX(), innerNode.getCenterY()));
		path.getElements().add(new LineTo(newPane.getWidth() / 2, 20));
		AnimationHolder.PATH_TRANSITION.setNode(innerNode);
		AnimationHolder.PATH_TRANSITION.setPath(path);
		AnimationHolder.PATH_TRANSITION.play();
	}

	@Override
	public void addMousePressedHandler(UiNodeWithValue node) {
		node.setOnMousePressed(event1 -> {
			event1.consume();
			UIDataLine<UiNodeWithValue> path = null;
			if (event1.getButton().equals(MouseButton.PRIMARY) && event1.getClickCount() == 2 && actionStack.isEmpty()) {
				UiTreeNodeWithValue source = (UiTreeNodeWithValue) event1.getTarget();
				path = new UIDataLine<>();
				path.setStart(source);

				path.setStartX(source.getCenterX());
				path.setStartY(source.getCenterY());
				path.startXProperty().bind(source.centerXProperty());
				path.startYProperty().bind(source.centerYProperty());
				path.setStrokeWidth(2);
				path.setStroke(Color.BLACK);
				actionStack.push(source);

				currentPath = path;
				drawing = true;
			} else if (event1.getButton().equals(MouseButton.PRIMARY) && !actionStack.isEmpty() && actionStack.peek() != event1.getTarget() && drawing) {
				UiTreeNodeWithValue source = (UiTreeNodeWithValue) actionStack.peek();
				UiTreeNodeWithValue target = (UiTreeNodeWithValue) event1.getTarget();
				if (UiTreeNodeWithValue.isChildOf(source, target.getParentNode())) {
					return;
				}
				UiNodeWithValue.Edge edge = new UiNodeWithValue.Edge(source, target);
				if (!source.hasEdge(source, target) || !target.hasEdge(source, target)) {
					newPane.getChildren().remove(currentPath);
					source.addEdge(edge);
					target.addEdge(edge);
					target.setParentNode(source);
					if (source.getNodeChildren() == null) {
						source.setNodeChildren(new ArrayList<>());
					}
					currentPath.setEndX(target.getCenterX());
					currentPath.setEndY(target.getCenterY());


					currentPath.endXProperty().bind(target.centerXProperty());
					currentPath.endYProperty().bind(target.centerYProperty());
					currentPath.setEnd(target);
					currentPath.setOnMouseEntered(highlight);

					currentPath.setOnMouseExited(ev1 -> {
						Shape shape = (Shape) ev1.getTarget();
						if (shape instanceof Line) {
							shape.setStroke(Paint.valueOf("black"));
						}
						shape.setFill(Paint.valueOf("black"));
					});
					currentPath.setOnMouseClicked(ev1 -> {
						if (MouseButton.SECONDARY.equals(ev1.getButton()) && currentPath != null) {
							UiTreeNodeWithValue start = (UiTreeNodeWithValue) currentPath.getStart();
							UiTreeNodeWithValue end = (UiTreeNodeWithValue) currentPath.getEnd();

							UiNodeWithValue.Edge theEdge = start.getEdge(start, end);
							if (theEdge != null) {
								start.getEdges().remove(theEdge);
								end.getEdges().remove(edge);
							}
							start.getNodeChildren().remove(end);
							newPane.getChildren().remove(ev1.getTarget());
						}
					});
					source.getNodeChildren().add(target);
					newPane.getChildren().add(currentPath);
					actionStack.pop();
					drawing = false;
				}
			}
		});

	}

	@Override
	protected void removeNode(UiNodeWithValue node) {
		super.removeNode(node);
		UiTreeNodeWithValue theNode = (UiTreeNodeWithValue) node;
		if (theNode.getParentNode() != null) {
			theNode.getParentNode().getNodeChildren().remove(theNode);
			theNode.setParentNode(null);
		}

	}
}
