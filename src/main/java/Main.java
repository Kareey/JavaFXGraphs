import java.util.logging.Logger;

import cv.graph.data.Constants;
import cv.graph.data.UiNodeWithValue;
import cv.graph.views.DirectedGraphView;
import cv.graph.views.GraphView;
import cv.graph.views.NormalGraphView;
import cv.graph.views.NormalTreeView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private final Logger LOGGER = Logger.getGlobal();
	public void start(Stage stage) {
		LOGGER.info("Application started with params: "+this.getParameters().getRaw());
		StackPane pane = new StackPane();
		VBox vbox = new VBox();
		Button startDirected = new Button("Start Directed");
		Button startNormal = new Button("Start Normal");
		Button startNormalTree = new Button("Start Normal Tree");

		startDirected.setOnAction(actionEvent -> {
			Stage newStage = launchNewScene(new Stage(), Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
			GraphView<UiNodeWithValue> directedGraphView = new DirectedGraphView(newStage);
			directedGraphView.beforeSetup();
			directedGraphView.setup();
		});
		startNormal.setOnAction(actionEvent -> {
			Stage newStage = launchNewScene(new Stage(), Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
			GraphView<UiNodeWithValue> normalView = new NormalGraphView(newStage);
			normalView.beforeSetup();
			normalView.setup();

		});
		startNormalTree.setOnAction(actionevent->{
			Stage newStage = launchNewScene(new Stage(), Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
			GraphView<UiNodeWithValue> treeView = new NormalTreeView(newStage);
			treeView.beforeSetup();
			treeView.setup();
		});

		vbox.setSpacing(100);

		vbox.getChildren().addAll(startDirected, startNormal,startNormalTree);

		pane.getChildren().add(vbox);


		Scene scene = new Scene(pane, Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
		scene.getStylesheets().add("main.css");
		stage.setScene(scene);
		stage.setOnCloseRequest(windowEvent -> {
			Platform.exit();
		});
		stage.show();
	}

	public void resetPane(Pane pane) {
		pane.getChildren().clear();
	}

	private Stage launchNewScene(Stage childStage, double w, double h) {
		childStage.setWidth(w);
		childStage.setHeight(h);
		Pane childPane = new Pane();
		childPane.setMaxSize(w, h);

		Scene scene = new Scene(childPane, w, h);
		scene.getStylesheets().add("main.css");
		childStage.setScene(scene);
		childStage.setOnCloseRequest(event -> {
			resetPane(childPane);
		});
		childStage.show();
		return childStage;

	}


}
