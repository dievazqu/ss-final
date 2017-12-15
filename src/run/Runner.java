package run;

import java.util.Random;

import model.Plane;
import model.PlaneSimulation;
import model.Point;
import model.particle.Human;
import utils.OutputXYZFilesGenerator;
import utils.RandomUtils;

public class Runner {

	private static double rMin = 0.2;
	private static double rMax = 0.25;
	private static double vdMax = 1;
	private static double beta = 1;
	private static double tau = 2;
	private static double ve = 0.2;

	public static void main(String[] args) {
		Plane plane = new Plane();
		PlaneSimulation ps = new PlaneSimulation(plane, rMin, rMax, vdMax, beta, tau,
				ve);
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator(
				"animation/", "state");
		
		double timeOfLastPassenger = 0;
		boolean[] passenger = new boolean[12*6];
		RandomUtils.setSeed(123123);
		while (ps.getTime() < 360) {
			outputXYZFilesGenerator.printState(ps.getParticles(), plane);
			ps.run();
			if(timeOfLastPassenger + 3 < ps.getTime()){
				timeOfLastPassenger = ps.getTime();
				int id;
				do{
					id = RandomUtils.getRandomInt(0, 12*6-1);
				}while(passenger[id]);
				passenger[id] = true;
				ps.addHuman(new Human(id, new Point(rMax, plane.getWidth()/2), rMin, rMax, vdMax, beta, tau,
						ve, plane));
				
			}
		}
	}
}
