package kalah;

public class Board {
	int[] playerStores;
	int[][] playersHouses;

	public Board() {
		int numberOfPlayers = 2;
		int storeStartingSeedNum = 0;
		int numberOfHouses = 6;
		int houseStartingSeedNum = 4;

		// Player stores in array so that number of players could be changed
		// easily without having to create individual player variables
		this.playerStores = new int[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			this.playerStores[i] = storeStartingSeedNum;
		}

		this.playersHouses = new int[numberOfPlayers][numberOfHouses];
		for (int i = 0; i < numberOfPlayers; i++) {
			for (int j = 0; j < numberOfHouses; j++) {
				this.playersHouses[i][j] = houseStartingSeedNum;
			}
		}
	}

	public void pick(int player, int houseNum){
		boolean anotherMove = false;
		int numOfSeeds = this.playersHouses[player-1][houseNum-1];
		for(int i = houseNum; i<=Math.min(houseNum + this.playersHouses[player-1][houseNum-1], this.playersHouses[0].length-1); i++){
			this.playersHouses[player-1][i]++;
			numOfSeeds--;
		}
		
		// TODO Need to check if last house is empty before deposit
		
		// If 1 seed left, deposit into player own store and give another turn
		if(numOfSeeds==1){
			numOfSeeds--;
			this.playerStores[player-1]++;
			anotherMove = true;
		} 
		// If more than 1 seed left, 
		else if(numOfSeeds > 1){
			numOfSeeds--;
			this.playerStores[player-1]++;
			sowSeeds(nextPlayer, numOfSeeds);
		}
		
	}
}
