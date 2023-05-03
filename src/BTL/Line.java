package BTL;

import java.awt.Point;

public class Line {
	public Point p1;
	public Point p2;
	public Line(Point p1, Point p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}
	@Override
	public String toString() {
		String line = "("+ p1.x + "," + p1.y +") and (" + p2.x + ","+ p2.y+")";
		return line;
	}
}
