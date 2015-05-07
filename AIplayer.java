/*
Connor Flanagan, Cristopher Spanos, Surya Teja Chinta, Kari Gilbertson
This is the code for the random AI for connect 4
*/

import java.util.Random;

public class AIplayer
{

	Random chooser = new Random();
	int Color;
	int answer;
	String playerType;
	public AIplayer(int playerColor)
	{
		Color = playerColor;
		//playerType = "RANDOM";
	}
	/*
	makeMove is going to make a decision about which method to call based on the string playerType.


	*/
	public int makeMove (String aMoves, int[][] board)
	{
		//if(playerType == "RANDOM")
		//	answer = makeRandomMove(aMoves);
		//if(playerType == "DEFENSIVE")
		  	answer = makeDefensiveMove(aMoves, board);
		// if(playerType == "AGGRESSIVE")
		// 	answer = makeAggressiveMove(aMoves, board);
		//  if(playerType == "MINIMAX")
		//  	answer = makeMiniMaxMove(aMoves, board);
		return answer;
	}
	public int makeRandomMove(String bMoves)
	{
		//all the random player should care about is picking a column to drop in.
		int m = genRandomMove();
		System.out.println(m+"");
		boolean gen = true;
		while(gen)
		{
			if(bMoves.contains(m+""))
			{
				gen = false;
			}
			else
			{
				m = genRandomMove();
				System.out.println(m+"");
			}
		}

		return m;
	}
	public int genRandomMove()
	{
		return chooser.nextInt(7)+1;
	}

	public int makeDefensiveMove(String cMoves, int[][] board)
	{
		// Array of heuristic values
		int[] heuristics = new int[cMoves.length()];

		// For each possible move...
		for(int i=0;i<cMoves.length();i++) {
			int move = Character.getNumericValue(cMoves.charAt(i)) - 1;
			int[][] child1 = makeMove(board, move, 2);
			int[][] child2 = makeMove(board, move, 1);
			heuristics[i] = heuristic(child2, 1) - heuristic(child1, 1);
		}

		for(int i=0;i<heuristics.length;i++) {
			System.out.println(heuristics[i]);
		}

		// Find the move minimizing token 1's chances at winning
		int max = Integer.MIN_VALUE;
		int bestMove = 0;
		for(int i=0;i<heuristics.length;i++) {
			if(heuristics[i] > max) {
				max = heuristics[i];
				bestMove = i;
			}
		}

		return bestMove + 1;
	}

	/

	public int getPlayerColor()
	{
		return Color;
	}
	public int getOppositePlayerColor()
	{
		int player = 0;
		if(Color == 1)
			player = 2;
		if(Color == 2)
			player = 1;
		return player;
	}

	public String getPlayerType()
	{
		return playerType;
	}
	/*
	The Connect 4 game will push the score in a setter to AI player which will then change playerType based on that.

	*/
	public void setPlayerType(int score)
	{
			if(score < 20)
				playerType = "RANDOM";
			return;
	}

	// http://stackoverflow.com/questions/21146940/connect-4-java-win-conditions-check
	public int heuristic(int[][] board, int token) {
		int h = 0;

		// Look at sequences in the rows
		for(int row=0;row<6;row++) {
			int count = 0;
		    for(int col=0;col<7;col++) {
				if(board[row][col] == token) {
					count++;
				}
		        else {
					h+=utility(count);
					count = 0;
				}
			}
			h+=utility(count);
		}

		// Look at sequences in the columns
		for(int col = 0;col<7;col++) {
			int count = 0;
		    for(int row = 0;row<6;row++) {
				if(board[row][col] == token) {
					count++;
				}
		        else {
					h+=utility(count);
					count = 0;
				}
			}
			h+=utility(count);
		}

		return h;
	}

	private void printBoard(int[][] board) {
		for(int x=0;x<board.length;x++) {
			for(int y=0;y<board[0].length;y++) {
				System.out.print(board[x][y]);
			}
			System.out.println();
		}
	}

	private int utility(int count) {
		if(count == 2) {
			return 10;
		} else if(count == 3) {
			return 100;
		} else if(count>=4) {
			return 666666;
		} else {
			return 0;
		}
	}

	private int[][] makeMove(int[][] board, int column, int token) {
		int[][] result = new int[6][7];

		// copy the array into result
		for(int row = 0;row<6;row++) {
			for(int col=0;col<7;col++) {
				result[row][col] = board[row][col];
			}
		}

		// place token into col
		for(int row = 5;row>=0;row--) {
			if(result[row][column] == 0){
				result[row][column] = token;
				return result;
			}
		}

		// probably shouldn't get here
		return result;
	}

	private int min(int r, int c)
    {
        if(r < c)
            return r;
        else return c;
    }
}
