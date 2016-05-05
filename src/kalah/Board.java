package kalah;

public class Board {
	CircularArrayList<Pit> board = new CircularArrayList<Pit>();

	int numberOfPlayers = 2;
	int housesPerPlayer = 6;
	int storesPerPlayer = 1;
	int pitsPerPlayer = housesPerPlayer + storesPerPlayer;
	int totalPits = numberOfPlayers * (pitsPerPlayer);

	int initialHouseSeedNum = 4;
	int initialStoreSeedNum = 0;
	
	public Board(){
		initialiseBoard();
	}

	public Board(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		initialiseBoard();
	}
	
	private void initialiseBoard(){
		for (int i = 1; i <= this.numberOfPlayers; i++) {
			// Add houses for each player
			for (int j = 0; j < this.housesPerPlayer; j++) {
				this.board.add(new House(i, this.initialHouseSeedNum));
			}
			// Add stores for each player
			for (int j = 0; j < this.storesPerPlayer; j++) {
				this.board.add(new Store(i, this.initialStoreSeedNum));
			}
		}
	}

	/**
	 * @param playerId
	 * @param house
	 * @return whether player gets another turn
	 */
	public TurnState pick(int playerId, int house) {
		// Check if house out of range, do nothing, give player another turn
		if (house > this.housesPerPlayer) {
			return TurnState.OutOfRangeHouse;
		}

		int houseIndex = getIndex(playerId, house);
		int seedsToSow = board.get(houseIndex).getSeeds();

		// Removes all seeds from selected house
		((House) board.get(houseIndex)).removeSeeds();

		// Check if there are seeds in house, if no, do nothing, give player
		// another turn
		if (seedsToSow == 0) {
			return TurnState.EmptyHouse;
		}

		// Sow all but one seed into following houses and player's own store
		int currentPitIndex = houseIndex + 1;
		while (seedsToSow > 1) {
			// Use of variable for easy to read + understand code and so pit
			// doesn't need to be gotten again and again
			Pit currentPit = board.get(currentPitIndex);

			if (currentPit instanceof House || currentPit.getOwner() == playerId) {
				board.get(currentPitIndex).addSeeds(1);
				seedsToSow--;
			}
			currentPitIndex++;
		}
		
		// Sow the last seed
		
		// Get the last pit
		Pit lastPit = board.get(currentPitIndex);
		
		// If current pit is another's player's store, go to next pit
		if (lastPit instanceof Store && lastPit.getOwner() != playerId) {
			currentPitIndex++;
			lastPit = board.get(currentPitIndex);
		}
		
		// If last pit is a house 
		if (lastPit instanceof House) {
			// if house belongs to another player, just deposit
			if(lastPit.getOwner()!=playerId){
				board.get(currentPitIndex).addSeeds(1);
			} // If the house belongs to the current player 
			else {
				// If house is empty
				if (lastPit.getSeeds() == 0) {
					int seedsInOppositeHouse = ((House) board.get(getOppositeHouse(currentPitIndex))).removeSeeds();
					// Check if opposite house has at least one seed, if so, capture
					if (seedsInOppositeHouse > 0) {
						// Deposit opposite house seeds + 1 into player's store
						// TODO refactor
						board.get(currentPitIndex/this.pitsPerPlayer*this.pitsPerPlayer+this.housesPerPlayer).addSeeds(seedsInOppositeHouse + 1);
					} else {
						// If not capture, just deposit
						board.get(currentPitIndex).addSeeds(1);
					}
				} // If house is not empty, just deposit
				else {
					// Deposit last seed, no more turn (return false)
					board.get(currentPitIndex).addSeeds(1);
				}
			}
			return TurnState.NextPlayer;
		}
		
		// Else next pit is player's own store, so sow seed and give another turn (return
		// true)
		board.get(currentPitIndex).addSeeds(1);
		return TurnState.AnotherTurn;
	}

	public boolean canMove(int playerId) {
		for(int i = 0; i<this.housesPerPlayer; i++){
			if(this.board.get((playerId-1)*this.pitsPerPlayer + i).getSeeds() > 0){
				return true;
			}
		}
		return false;
	}

	/**
	 * @param playerId
	 *            Id of player, starting from 1
	 * @param pitNumber
	 *            Number of the house. If pit is a store, it is highest house
	 *            number + 1
	 * @return index of the pit in the board array
	 */
	private int getIndex(int playerId, int pitNumber) {
		return pitsPerPlayer * (playerId - 1) + pitNumber - 1;
	}

	
	private int getOppositeHouse(int houseIndex) {
		int playerId = houseIndex / this.pitsPerPlayer % this.numberOfPlayers + 1;
		int houseNumber = houseIndex % this.pitsPerPlayer + 1;
		
		if(houseNumber>this.housesPerPlayer){
			throw new IllegalArgumentException();
		}
		
		int oppositePlayerIndex = (this.numberOfPlayers/2 + playerId)/(this.numberOfPlayers)>1 ?  (this.numberOfPlayers/2 + playerId)%(this.numberOfPlayers)-1 : (this.numberOfPlayers/2 + playerId)-1;
		int indexOfFirstOppositeHouse = oppositePlayerIndex * this.pitsPerPlayer;
		int oppositeHouseIndex = indexOfFirstOppositeHouse + (this.housesPerPlayer - houseNumber);
		return oppositeHouseIndex;
	}

	public int getSeedNumber(int playerId, int houseNumber) {
		return board.get(getIndex(playerId, houseNumber)).getSeeds();
	}

}
