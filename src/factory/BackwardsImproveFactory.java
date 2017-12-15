package factory;

import java.util.ArrayList;
import java.util.List;

public class BackwardsImproveFactory implements PassengerFactory {

private List<Integer> ids;
	
	public BackwardsImproveFactory(int passengerSize){
		ids = new ArrayList<Integer>();
		for(int i=passengerSize-1; i>=0; i-=6){
			ids.add(i);
		}
		for(int i=passengerSize-6; i>=0; i-=6){
			ids.add(i);
		}
		for(int i=passengerSize-2; i>=0; i-=6){
			ids.add(i);
		}
		for(int i=passengerSize-5; i>=0; i-=6){
			ids.add(i);
		}
		for(int i=passengerSize-3; i>=0; i-=6){
			ids.add(i);
		}
		for(int i=passengerSize-4; i>=0; i-=6){
			ids.add(i);
		}
		
		
		
	}
	
	@Override
	public int getNextPassengerId() {
		return ids.remove(0);
	}
	
	@Override
	public boolean hasPassengers() {
		return ids.size() != 0;
	}
}
