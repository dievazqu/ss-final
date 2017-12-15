package factory;

import java.util.ArrayList;
import java.util.List;

public class BackwardsFactory implements PassengerFactory {

	private List<Integer> ids;
	
	public BackwardsFactory(int passengerSize){
		ids = new ArrayList<Integer>();
		for(int i=passengerSize-1; i>=0; i-=1){
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
