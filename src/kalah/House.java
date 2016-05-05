package kalah;

public class House extends Pit{
	
	public House(int playerId, int seeds){
		super(playerId, seeds);
	}
	
	public int removeSeeds(){
		int seeds = getSeeds();
		this.seeds = 0;
		return seeds;
	}
	
}
