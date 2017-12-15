package run;

import factory.BackwardsFactory;
import factory.BackwardsImproveFactory;
import factory.Block3Factory;
import factory.PassengerFactory;
import factory.RandomFactory;
import model.Plane;
import model.PlaneSimulation;
import model.Point;
import model.particle.Human;
import model.particle.Particle;
import utils.OutputXYZFilesGenerator;
import utils.RandomUtils;

public class Runner {

	private static final double rMin = 0.2;
	private static final double rMax = 0.25;
	private static final double vdMax = 1;
	private static final double beta = 1;
	private static final double tau = 1;
	private static final double ve = 0.2;
	private static final double timeBetweenPassenger = 5;
	private static final double meanTimeToWait = 15;
	private static final double stdTimeToWait = 4;
	private static final boolean printingOut = true;

	public static void main(String[] args) {
		Plane plane = new Plane();
		PlaneSimulation planeSimulation = new PlaneSimulation(plane, rMin, rMax, vdMax, beta, tau,
				ve);
		OutputXYZFilesGenerator outputXYZFilesGenerator = new OutputXYZFilesGenerator(
				"animation/", "state");
		int seed = 1236;
		RandomUtils.setSeed(seed);
		System.out.println(seed);
		PassengerFactory passengerFactory = new RandomFactory(24*6);
		double timeOfLastPassenger = 0;
		Particle humanSpace = new Particle(0, rMax, plane.getWidth()/2, 0, 0, rMax);
		while (passengerFactory.hasPassengers() || !planeSimulation.areAllSeated() ) {
			if(printingOut){
				outputXYZFilesGenerator.printState(planeSimulation.getParticles(), plane);
			}
			planeSimulation.run();
			if(	passengerFactory.hasPassengers() &&
				timeOfLastPassenger + timeBetweenPassenger < planeSimulation.getTime() &&
				planeSimulation.planeHasSpaceForNewPasenger(humanSpace)){
				timeOfLastPassenger = planeSimulation.getTime();
				int id = passengerFactory.getNextPassengerId();
				planeSimulation.addHuman(new Human(id, new Point(rMax, plane.getWidth()/2), rMin, rMax, vdMax, beta, tau,
						ve, plane, meanTimeToWait, stdTimeToWait));
			}
		}
		System.out.println("All passenger are seated");
		System.out.println("Time: "+planeSimulation.getTime());
		System.out.println("Time avg for passengers: "+planeSimulation.getAvgPassengersTime());
	}
}
