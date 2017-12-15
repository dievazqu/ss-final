package factory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Block3Factory implements PassengerFactory{
	
	private List<Integer>[] blocks;
	private List<Integer> ids;
	
	public Block3Factory(int passengerSize){
		ids = new LinkedList<Integer>();
		blocks = new List[3];
		for(int i=0; i<3; i++){
			blocks[i] = new LinkedList<Integer>();
		}
		int division = passengerSize/3;
		for(int i=0; i<passengerSize; i++){
			blocks[i/division].add(i);
		}
		for(int i=2; i>=0; i--){
			Collections.shuffle(blocks[i]);
			ids.addAll(blocks[i]);
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
