package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {

	// Board is only modified via methods
	private Board board;

	// Could be changed
	int numberOfPlayers = 2;

	public Kalah() {
		this.board = new Board(this.numberOfPlayers);
	}

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		Printer printer = new Printer(io, this.board);

		// Could be changed so that another player starts first
		int currentPlayerId = 1;

		printer.printBoard();
		String input = printer.askForInput(currentPlayerId);

		boolean completeGame = false;

		while (true) {
			if (input.equals("q"))
				break;

			TurnState turn = this.board.pick(currentPlayerId, Integer.parseInt(input));

			if (turn == TurnState.NextPlayer) {
				currentPlayerId = getNextPlayer(currentPlayerId);
			} else if (turn == TurnState.EmptyHouse) {
				printer.printEmptyHouse();
			}

			printer.printBoard();

			if (!this.board.canMove(currentPlayerId)) {
				// game over
				completeGame = true;
				break;
			}

			input = printer.askForInput(currentPlayerId);
		}

		io.println("Game over");
		printer.printBoard();

		if (completeGame) {
			printer.printScore();
		}
	}

	/**
	 * Order could be modified
	 * @param currentPlayerId
	 * @return Id of the next player
	 */
	private int getNextPlayer(int currentPlayerId) {
		if (currentPlayerId == numberOfPlayers) {
			return 1;
		}
		return currentPlayerId + 1;
	}
}
