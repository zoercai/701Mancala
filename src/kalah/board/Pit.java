package kalah.board;

public class Pit {

	private int playerId;

	// protected so can be accessed by subclasses
	protected int seeds;

	public Pit(int playerId, int seeds) {
		this.playerId = playerId;
		this.seeds = seeds;
	}

	public int getSeeds() {
		return this.seeds;
	}

	public void addSeeds(int seeds) {
		this.seeds += seeds;
	}

	public int getOwner() {
		return playerId;
	}

}
