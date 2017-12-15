package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import model.Plane;
import model.Point;
import model.Segment;
import model.particle.Human;
import model.particle.Particle;

public class OutputXYZFilesGenerator {

	private int frameNumber;
	private String path;

	public OutputXYZFilesGenerator(String directory, String file) {
		frameNumber = 0;
		this.path = directory + file;
		try {
			Files.createDirectories(Paths.get(directory));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printState(List<Human> particles, Plane plane) {
		List<String> lines = new LinkedList<String>();
		
		lines.add("ParticleId xCoordinate yCoordinate xDisplacement yDisplacement Radius R G B Transparency Selection");
		for (Human p : particles) {
			lines.add(getInfo(p, null, 0, 0));
		}
		printPlane(plane, lines);
		writeFile(lines);
	}
	
	private void printPlane(Plane plane, List<String> lines) {
		for(Segment s : plane.getSegments()){
			for(Point p : s.points()){
				lines.add("10000 "+ p.x +" " + p.y + " 0 0 0.02 0 1 0 0 0");				
			}
		}
	}
/*
	private int addBorderParticles(List<String> lines) {
		int counter = 0;
		for (double i = 0; i * 0.02 <= EscapeRunner.L; i++) {
			lines.add("10000 0 " + (i * 0.02 + EscapeRunner.fall) + " 0 0 0.02 0 1 0 0 0");
			lines.add("10000 " + EscapeRunner.W + " " + (i * 0.02 + EscapeRunner.fall) + " 0 0 0.02 0 1 0 0 0");
			counter += 2;
		}
		for (int i = 0; i * 0.02 <= (EscapeRunner.W - EscapeRunner.D) / 2; i++) {
			lines.add("10000 " + i * 0.02 + " " + EscapeRunner.fall + " 0 0 0.02 0 1 0 0 0");
			lines.add("10000 " + (EscapeRunner.W - i * 0.02) + " " + EscapeRunner.fall + " 0 0 0.02 0 1 0 0 0");
			counter += 2;
		}
		return counter;
	*/

	private String getInfo(Human p, String color, double transparency, int selection) {
		return p.getId() + " " + p.getX() + " " + p.getY() + " " + p.getXVelocity() + " " + p.getYVelocity() + " "
				+ p.getMinRadius() + " " + "1 0 0" + " " + transparency + " " + selection;
	}

	private void writeFile(List<String> lines) {
		Path file = Paths.get(path + frameNumber + ".xyz");
		frameNumber++;
		lines.add(0, ""+(lines.size()-1));
		try {
			Files.write(file, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
