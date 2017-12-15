package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.particle.Human;
import model.particle.Particle;
import utils.CellIndexMethod;

public class PlaneSimulation {

	
	
	private List<Human> humans;
	private List<Particle> particles;
	private double dt;
	private double vdMax;
	private double beta;
	private double tau;
	private double ve;
	private CellIndexMethod<Particle> cim;
	private double time;
	private double radiusStep;
	private Plane plane;
	
	public PlaneSimulation(Plane plane, double rMin, double rMax, double vdMax, double beta, double tau, double ve) {
		this.time = 0;
		this.plane = plane;
		this.humans = new LinkedList<Human>();
		this.particles = new LinkedList<Particle>(humans);
		this.cim = new CellIndexMethod<Particle>(particles, plane.getLength(), 2*rMax, 0, false);
		this.dt = rMin / (2*Math.max(vdMax, ve));
		this.radiusStep = rMax / (tau / dt);
	} 
	
	public List<Human> getParticles() {
		return humans;
	}
	
	public void addHuman(Human p){
		humans.add(p);
		particles.add(p);
	}
	
	public double getTime() {
		return time;
	}
	
	public void run(){
		time+=dt;
		Map<Particle, Set<Particle>> neighbours = cim.getNeighbours();
		for(Human p : humans){
			for(Particle q : neighbours.get(p)){
				p.overlapWith(q);
			}
			p.overlapInPlane(plane);
		}
		for(Human p : humans){
			p.calculateVe();
		}
		for(Human p : humans){
			p.updateStatus(dt);
			p.move(dt);
		}
	}

	public boolean areAllSeated() {
		for(Human h : humans){
			if(!h.isSeated()){
				return false;
			}
		}
		return true;
	}

	public double getAvgPassengersTime() {
		return humans.stream().mapToDouble(h -> h.getTimeToSeat()).average().orElse(0);
	}

	public boolean planeHasSpaceForNewPasenger(Particle humanSpace) {
		for(Human h : humans){
			if(Particle.areOverlapped(h, humanSpace)){
				return false;
			}
		}
		return true;
	}
	
}
