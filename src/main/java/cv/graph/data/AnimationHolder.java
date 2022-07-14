package cv.graph.data;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AnimationHolder {
	public static FillTransition FILL_ANIMATION =  new FillTransition(Duration.seconds(1), Color.GREEN,Color.YELLOW);
	public static PathTransition PATH_TRANSITION =  new PathTransition(Duration.seconds(3), null);
}
