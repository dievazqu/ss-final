package model.particle;

import java.util.LinkedList;
import java.util.List;

import model.Plane;
import model.Point;


public class Human extends Particle {
	
	private final double rMin;
	private final double rMax;
	private final double vdMax;
	private final double beta;
	private final double tau;
	private final double ve;
	private final List<Point> desireLocations;
	private final Point finalDestination;
	private final List<Particle> overlappingParticles;
	private Point currentVe;

	public Human(int id, Point position, double rMin, double rMax, double vdMax, double beta, double tau, double ve, Plane plane){
		super(id, position.x, position.y, 0, 0, rMin);
		overlappingParticles = new LinkedList<Particle>();
		desireLocations = new LinkedList<Point>();
		desireLocations.add(plane.getPointForCorridorRow(id));
		desireLocations.add(plane.getPointForSeat(id));
		this.finalDestination = desireLocations.get(desireLocations.size()-1);
		this.position = position.clone();
		this.rMin = rMin;
		this.rMax = rMax;
		this.vdMax = vdMax;
		this.beta = beta;
		this.ve = ve;
		this.tau = tau;
		radius = rMin;
		velocity = new Point(0,0);
	}
	
	public void move(double time){
		if(desireLocations.isEmpty()){
			position = finalDestination;
			radius = rMin/2;
			return;
		}
		//System.out.print("soy "+id+" y me movi de "+position+" a ");
		Point deltaPosition = velocity.clone();
		deltaPosition.applyFunction(v->v*time);
		position.add(deltaPosition);
		if(desireLocations.size()>0 && Point.dist(position, desireLocations.get(0)) < 0.1){
			desireLocations.remove(0);
		}
	}
	
	public static <T extends Particle> boolean areOverlapped(T p, T q){
		return Point.dist2(p.position, q.position) <= Math.pow(p.getRadius()+q.getRadius(),2);
	}
	
	public void overlapWith(Particle q) {
		if(Particle.areOverlapped(this, q)){
			overlappingParticles.add(q);
		}
	}
	
	public void overlapInPlane(Plane plane){
		List<Particle> p = plane.collisionParticle(this);
		//System.out.println("soy "+this+" "+p);
		overlappingParticles.addAll(p);
	}
	
	private double radiusMultiplier(){
		return Math.pow((radius - rMin) / (rMax - rMin), beta);
	}

	public void updateStatus(double dt) {
		if(desireLocations.isEmpty()){
			velocity = new Point(0,0);
			return;
		}
		if(overlappingParticles.isEmpty()){
			//System.out.println("soy "+id+" no estoy overlapeado");
			radius = Math.min(rMax, radius+rMax/(tau/dt));
			Point vdDirection;
			vdDirection = Point.sub(desireLocations.get(0), position).normalize();
			velocity = vdDirection.times(vdMax * radiusMultiplier());
		}else{
			radius = rMin;
			velocity = currentVe;
		}
		overlappingParticles.clear();
		currentVe = null;
	}

	public void calculateVe() {
		List<Point> es = new LinkedList<Point>();
		for(Particle p : overlappingParticles){
			es.add(Point.sub(this.position, p.position).normalize());
		}
		
		Point sum = new Point(0,0);
		for(Point e : es){
			sum.add(e);
		}
		
		currentVe = sum.times(ve).normalize();			
		
	}

	public double getMinRadius() {
		return rMin;
	}
}
