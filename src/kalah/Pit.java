package kalah;

public class Pit {
	
	// protected so can be accessed by subclasses
	protected int seeds;
	
	public Pit(int seeds){
		this.seeds = seeds;
	}

	public int getSeeds() {
		return seeds;
	}

	public void addSeeds(int seeds){
		this.seeds += seeds;
	}
	
	
}
