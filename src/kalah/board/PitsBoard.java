package kalah.board;

import kalah.TurnState;

public interface PitsBoard {
	
	TurnState pick(int playerId, int house);

	boolean canMove(int playerId);

	int getSeedNumber(int playerId, int houseNumber);

	int getNumberOfPlayers();

	int getPitsPerPlayer();

}