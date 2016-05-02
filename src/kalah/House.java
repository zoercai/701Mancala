package kalah;

public class House extends Pit{
	
	public House(int seeds){
		super(seeds);
	}
	
	public int removeSeeds(){
		int seeds = getSeeds();
		this.seeds = 0;
		return seeds;
	}
	
}
