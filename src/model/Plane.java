package model;

import java.util.LinkedList;
import java.util.List;

import model.particle.Particle;

public class Plane {

	// Asiento de 80 de largo por 45 de ancho.
	// Fuente:
	// https://es.gizmodo.com/quien-decide-el-espacio-entre-asientos-de-los-aviones-y-1797187123

	private final double corridor = 0.5;
	private final double initSpace = 1.8;
	private final double seatLength = 0.8;
	private final double seatWidth = 0.45;
	private final double length = seatLength * 11 + initSpace; // 21.2
	private final double width = seatWidth * 6 + corridor; // 3.5
	private List<Segment> seatsRow;

	public Plane() {
		seatsRow = new LinkedList<Segment>();
		for (int i = 0; i < 13; i++) {
			seatsRow.add(new Segment(new Point(initSpace + i * seatLength - seatLength, 0), new Point(
					initSpace + i * seatLength- seatLength, seatWidth * 3)));
		}
		for (int i = 0; i < 13; i++) {
			seatsRow.add(new Segment(
					new Point(initSpace + i * seatLength- seatLength, seatWidth * 3 + corridor), new Point(
							initSpace + i * seatLength- seatLength, width)));
		}
		seatsRow.add(new Segment(new Point(0, 0), new Point(length, 0)));
		seatsRow.add(new Segment(new Point(0, width), new Point(length, width)));
		seatsRow.add(new Segment(new Point(0, 0), new Point(0, width)));
		seatsRow.add(new Segment(new Point(length, 0), new Point(length, width)));
	}

	public List<Particle> collisionParticle(Particle particle) {
		List<Particle> l = new LinkedList<Particle>();
		for (Segment seatRow : seatsRow) {
			
			Point p = seatRow.nearestPoint(particle.getPosition());
			if (Point.dist(particle.getPosition(), p) < particle.getRadius()) {
				l.add(new Particle(-1, p.x, p.y, 0, 0, 0));
			}

		}
		return l;
	}

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return width;
	}

	public List<Segment> getSegments() {
		return seatsRow;
	}

	// id/6 -> row
	// id%6 -> row -> seat
	
	public Point getPointForCorridorRow(int id) {
		int row = id/6;
		return new Point(initSpace+seatLength*row-seatLength*3/4, width/2);
	}

	public Point getPointForSeat(int id) {
		int row = id/6;
		int seat = id%6;
		if(seat < 3){
			return new Point(initSpace+seatLength*row-seatLength/6, seatWidth*seat + seatWidth/2);
		}else{
			return new Point(initSpace+seatLength*row-seatLength/6, seatWidth*seat+corridor+seatWidth/2);
		}
	}

}
