package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

public class Printer {
	IO io;
	Board board;
	
	public Printer(IO io, Board board){
		this.io = io;
		this.board = board;
	}
	
	public void printBoard(){
		printBorder();
		
		io.print("| P2 |");
		for(int i = 6; i>0; i--){
			io.print(printHouse(2, i) + "|");
		}
		io.println(" " + String.format("%2d",this.board.getSeedNumber(1, 7)) + " |");
		
		io.println("|    |-------+-------+-------+-------+-------+-------|    |");
		
		io.print("| "+ String.format("%2d",this.board.getSeedNumber(2, 7)) + " |");
		for(int i = 1; i<=6; i++){
			io.print(printHouse(1, i)+ "|");
		}
		io.println(" P1 |");
		
		printBorder();
	}
	
	public void printEmptyHouse(){
		io.println("House is empty. Move again.");
	}
	
	private void printBorder(){
		io.println("+----+-------+-------+-------+-------+-------+-------+----+");
	}
	
	private String printHouse(int playerId, int houseNumber){
		StringBuilder sb = new StringBuilder();
		
		sb.append(" " + houseNumber + "[");
		sb.append(String.format("%2d",this.board.getSeedNumber(playerId, houseNumber)));
		sb.append("] ");
		
		return sb.toString();
	}

	public void printScore() {
		int highestScore = 0;
		int winner = 1;
		boolean tie = true;
		
		for(int i = 0; i<board.numberOfPlayers; i++){
			int sum = 0;
			for(int j = 1; j<=board.pitsPerPlayer; j++){
				sum += board.getSeedNumber(i+1, j);
			}
			io.println("\tplayer " + (i+1) + ":"+sum);
			
			if(i==0){
				highestScore = sum;
			} else{
				if(highestScore!=sum){
					tie = false;
					if(highestScore<sum){
						winner = i+1;
						highestScore = sum;
					}
				}
			}
		}
		
		if(tie){
			io.println("A tie!");
		} else{
			io.println("Player "+winner+" wins!");
		}
	}
	
}
