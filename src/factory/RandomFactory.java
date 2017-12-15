package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomFactory implements PassengerFactory {

	private List<Integer> ids;
	
	public RandomFactory(int passengerSize){
		ids = new ArrayList<Integer>();
		for(int i=0; i<passengerSize; i++){
			ids.add(i);
		}
		Collections.shuffle(ids);
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
