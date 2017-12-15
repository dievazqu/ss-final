package model;

import java.util.LinkedList;
import java.util.List;

public class Segment {

	private Point p1, p2;
	private double a, b, c;

	@Override
	public String toString() {
		return "Segment [p1=" + p1 + ", p2=" + p2 + "]";
	}

	public Segment(Point p11, Point p22) {
		this.p1 = p11.clone();
		this.p2 = p22.clone();

		a = +(p1.y - p2.y);
		b = -(p1.x - p2.x);
		c = p1.x * p2.y - p2.x * p1.y;
	}

	public Point intersect(Segment line) {
		double d = a * line.b - line.a * b;
		if (sign(d) == 0) {
			return null;
		}
		double x = -(c * line.b - line.c * b) / d;
		double y = -(a * line.c - line.a * c) / d;
		return new Point(x, y);
	}

	public Point nearestPoint(Point p) {
		Point tangentDirection = Point.sub(p2, p1).rotate90();
		Segment l = new Segment(p, Point.sum(p, tangentDirection));
		Point intersection = this.intersect(l);
		if(	EPS >=	Point.dist(p1, intersection)
				+ Point.dist(intersection, p2) - Point.dist(p1, p2)){
			return intersection;
		}
		if(Point.dist(p1, p) < Point.dist(p2, p)){
			return p1;
		}
		return p2;
	}

	public static int sign(double a) {
		return a < -EPS ? -1 : a > EPS ? 1 : 0;
	}

	static final double EPS = 1e-7;

	public Iterable<Point> points() {
		Point direction = Point.sub(p2, p1).normalize().times(0.02);
		List<Point> points = new LinkedList<Point>();
		for(Point current = p1.clone(); Point.dist(current, p2) > 0.03; current.add(direction)){
			points.add(current.clone());
		}
		points.add(p2.clone());
		return points;
	}
}
