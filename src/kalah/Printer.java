package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

public class Printer {
	IO io;
	
	public Printer(IO io){
		this.io = io;
	}
	
	public void print(){
		printBorder();
		io.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		io.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
		printBorder();
		io.println("Player 1's turn - Specify house number or 'q' to quit: ");
	}
	
	public void printBorder(){
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
	}
}
