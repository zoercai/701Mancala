package kalah.display;

public interface Printer {

	void printBoard();

	void printScore();

	void printInvalidMove();

	String askForInput(int currentPlayerId);

}