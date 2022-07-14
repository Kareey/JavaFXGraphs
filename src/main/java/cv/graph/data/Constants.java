package cv.graph.data;

public interface Constants {
	double RADIUS=20;
	double STAGE_WIDTH = 1024;
	double STAGE_HEIGHT = 768;
	double PLUS_D45_COS = Math.cos(Math.PI/4);
	double PLUS_D45_SIN = Math.sin(Math.PI/4);

	double MINUS_D45_COS = PLUS_D45_COS;
	double MINUS_D45_SIN = -1*PLUS_D45_SIN;
}
