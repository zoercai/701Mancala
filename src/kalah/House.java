package kalah;

public class House extends Pit{
	
	public House(int playerId, int seeds){
		super(playerId, seeds);
	}
	
	/**
	 * Removes seeds in the house
	 * @return number of seeds removed
	 */
	public int removeSeeds(){
		int seeds = getSeeds();
		this.seeds = 0;
		return seeds;
	}
	
}
