package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for the Modifiability Assignment.
 */
public class Kalah {
	
	// Board should only be modified via methods
	private Board board;
	
	int numberOfPlayers = 2;
	
	public Kalah(){
		this.board = new Board(this.numberOfPlayers);
	}
	
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	
	public void play(IO io) {
		// Replace what's below with your implementation
		Printer printer = new Printer(io, this.board);
		
		int currentPlayerId = 1;
		printer.printBoard();
		String input = io.readFromKeyboard("Player P" + currentPlayerId + "'s turn - Specify house number or 'q' to quit: ");
		
		boolean successful = false;
		
		while(true){
			// New turn, get input from user
			if(input.equals("q")){
				break;
			}
			
			TurnState turn = this.board.pick(currentPlayerId, Integer.parseInt(input));

			if (turn == TurnState.NextPlayer) {
				currentPlayerId = getNextPlayer(currentPlayerId);
			} else if (turn == TurnState.EmptyHouse) {
				printer.printEmptyHouse();
			} else if (turn == TurnState.AnotherTurn) {

			} else if (turn == TurnState.OutOfRangeHouse) {

			}
			
			printer.printBoard();
			
			if(!this.board.canMove(currentPlayerId)){
				// game over
				successful = true;
				break;
			}
			
			input = io.readFromKeyboard("Player P" + currentPlayerId + "'s turn - Specify house number or 'q' to quit: ");
		}
		
		io.println("Game over");
		printer.printBoard();
		
		if(successful){
			printer.printScore();
		}
		
	}
	
	
	private int getNextPlayer(int currentPlayerId){
		if(currentPlayerId==numberOfPlayers){
			return 1;
		}
		return currentPlayerId + 1;
	}
}
