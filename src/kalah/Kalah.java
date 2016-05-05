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
		this.board = new Board();
	}
	
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	
	public void play(IO io) {
		// Replace what's below with your implementation
		Printer printer = new Printer(io);
		
		
		printer.print();
	}
}
