package factory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BlockFactory implements PassengerFactory{
	
	private List<Integer>[] blocks;
	private List<Integer> ids;
	
	public BlockFactory(int passengerSize, int blocksNum){
		ids = new LinkedList<Integer>();
		blocks = new List[blocksNum];
		for(int i=0; i<blocksNum; i++){
			blocks[i] = new LinkedList<Integer>();
		}
		int division = passengerSize/blocksNum;
		for(int i=0; i<passengerSize; i++){
			blocks[i/division].add(i);
		}
		for(int i=blocksNum-1; i>=0; i--){
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
