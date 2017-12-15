package factory;

public class BackwardsImproveFactory extends BlockImproveFactory {

	public BackwardsImproveFactory(int passengerSize){
		super(passengerSize, passengerSize/6);
	}
}
