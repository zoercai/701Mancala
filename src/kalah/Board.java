package kalah;

public class Board {
	CircularArrayList<Pit> board = new CircularArrayList<Pit>();

	// These are default values, they could be modified in constructor
	int numberOfPlayers = 2;
	int housesPerPlayer = 6;
	int storesPerPlayer = 1;
	
	int initialHouseSeedNum = 4;
	int initialStoreSeedNum = 0;

	int pitsPerPlayer = housesPerPlayer + storesPerPlayer;
	int totalPits = numberOfPlayers * (pitsPerPlayer);

	public Board() {
		initialiseBoard();
	}

	public Board(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		initialiseBoard();
	}

	/**
	 * So that multiple constructors could be added and they could reuse this
	 * code
	 */
	private void initialiseBoard() {
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
	 * Called when player picks a house in a turn
	 * 
	 * @param playerId
	 *            player id, starts from 1
	 * @param house
	 *            house number, starts from 1
	 * @return outcome of the current turn, an enum
	 */
	public TurnState pick(int playerId, int house) {
		int houseIndex = getIndex(playerId, house);
		int seedsToSow = board.get(houseIndex).getSeeds();

		// Removes all seeds from selected house
		((House) board.get(houseIndex)).removeSeeds();

		// Check if there are seeds in house picked
		if (seedsToSow == 0) {
			return TurnState.EmptyHouse;
		}

		// Sow all but one seed into the following houses and player's own store
		int currentPitIndex = houseIndex + 1;
		while (seedsToSow > 1) {
			// Use of variable for easy to read + understand code and so pit
			// doesn't need to be read more than once
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
			if (lastPit.getOwner() != playerId) {
				board.get(currentPitIndex).addSeeds(1);
			} // If the house belongs to the current player
			else {
				// If house is empty
				if (lastPit.getSeeds() == 0) {
					int seedsInOppositeHouse = ((House) board.get(getOppositeHouse(currentPitIndex))).removeSeeds();
					// Check if opposite house has at least one seed, if so,
					// capture
					if (seedsInOppositeHouse > 0) {
						// Deposit opposite house seeds + 1 into player's store
						board.get(currentPitIndex / this.pitsPerPlayer * this.pitsPerPlayer + this.housesPerPlayer)
								.addSeeds(seedsInOppositeHouse + 1);
					} else {
						// If opposite house is empty, just deposit
						board.get(currentPitIndex).addSeeds(1);
					}
				} // If house is not empty, just deposit
				else {
					// Deposit last seed
					board.get(currentPitIndex).addSeeds(1);
				}
			}
			return TurnState.NextPlayer;
		}

		// If last pit is player's own store, sow seed and give another
		// turn
		board.get(currentPitIndex).addSeeds(1);
		return TurnState.AnotherTurn;
	}

	/**
	 * @param playerId
	 *            player id, starts from 1
	 * @return whether the player has remaining moves
	 */
	public boolean canMove(int playerId) {
		for (int i = 0; i < this.housesPerPlayer; i++) {
			if (this.board.get((playerId - 1) * this.pitsPerPlayer + i).getSeeds() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param playerId
	 *            player id, starts from 1
	 * @param houseNumber
	 *            number of the house
	 * @return number of seeds in the house
	 */
	public int getSeedNumber(int playerId, int houseNumber) {
		return board.get(getIndex(playerId, houseNumber)).getSeeds();
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

	/**
	 * Can work for any number of players (must be even)
	 * @param houseIndex
	 *            index of the house in board
	 * @return index of the house opposite to the input
	 */
	private int getOppositeHouse(int houseIndex) {
		int playerId = houseIndex / this.pitsPerPlayer % this.numberOfPlayers + 1;
		int houseNumber = houseIndex % this.pitsPerPlayer + 1;

		int oppositePlayerIndex = (this.numberOfPlayers / 2 + (playerId - 1)) % this.numberOfPlayers;
		int indexOfFirstOppositeHouse = oppositePlayerIndex * this.pitsPerPlayer;
		int oppositeHouseIndex = indexOfFirstOppositeHouse + (this.housesPerPlayer - houseNumber);
		return oppositeHouseIndex;
	}

}
