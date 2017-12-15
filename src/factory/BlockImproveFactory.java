package factory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BlockImproveFactory implements PassengerFactory{
	
	private List<Integer>[] blocks;
	private List<Integer> ids;
	
	public BlockImproveFactory(int passengerSize, int blocksNum){
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
		}
		for(int i=0; i<division; i++){
			for(int j=blocksNum-1; j>=0; j--){
				ids.add(blocks[j].get(i));
			}
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
