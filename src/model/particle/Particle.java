package model.particle;

import model.Point;


public class Particle {
	
	private int id;
	protected Point position;
	protected Point velocity;
	protected double radius;

	public Particle(int id, double x, double y, double vx, double vy, double r) {
		this.id = id;
		this.position = new Point(x, y);
		this.velocity = new Point(vx, vy);
		this.radius = r;
	}

	public int getId() {
		return id;
	}

	public double getX() {
		return position.x;
	}
	
	public double getY(){
		return position.y;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public double getXVelocity(){
		return velocity.x;
	}
	
	public double getYVelocity(){
		return velocity.y;
	}
	
	public Point getVelocity(){
		return velocity;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void move(double time){
		Point deltaPosition = velocity.clone();
		deltaPosition.applyFunction(v->v*time);
		position.add(deltaPosition);
	}
	
	public static <T extends Particle> boolean areOverlapped(T p, T q){
		return Point.dist2(p.position, q.position) <= Math.pow(p.getRadius()+q.getRadius(),2);
	}

	public double getSpeed() {
		return velocity.abs();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Particle other = (Particle) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "["+id+" "+position+"]";
	}
	
}
