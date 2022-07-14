package cv.graph.handlers;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public interface ButtonListener {

	void addListener(Button btn, EventHandler<ActionEvent> event, Map<String,Object> params);
}
