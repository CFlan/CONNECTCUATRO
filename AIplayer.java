/*
Connor Flanagan, Cristopher Spanos, Surya Teja Chinta, Kari Gilbertson
AI players for connect 4
*/

import java.util.Random;
//The player class
public class AIplayer
{
	Random chooser = new Random();
	//Color of the AI player
	int Color;
	//Move made by the makeMove method
	int answer;
	//Size of the board
        public static final int MAXROW = 6;     
        public static final int MAXCOL = 7; 
	//String containing the name of the player type
	String playerType;
	/*Constructor for the AI player
	@args color of the AI player
	*/
	public AIplayer(int playerColor)
	{
		Color = playerColor;
	}
	/*Setter for the player type
	@args string of the type of player
	*/
	public void setType(String answer)
	{
		playerType = answer;
	}
	/*makeMove is going to make a decision about which method to call based on the string playerType.
	@args aMoves- the string containing all available moves, board- the array of the board
	@returns result of various make move methods
	*/
	public int makeMove (String aMoves, int[][] board)
	{
		boolean gen = true;
		answer = -1;
		int counter = 0;
		//Tries to generate a move, if it attepts to fill an illegal spot, it will check another
		while(gen)
		{
			if(aMoves.contains(answer+""))
				return answer;
			else{
				if(counter>10)
					return makeRandomMove(aMoves);
		if(playerType == "Random")
			answer = makeRandomMove(aMoves);
		if(playerType == "Defensive")
		  	answer = makeDefensiveMove(aMoves, board, answer);
		if(playerType == "Aggressive")
		 	answer = makeAggressiveMove(aMoves, board, answer);
		if(playerType == "Dumb")
		 	answer = makeDumbMove(aMoves, board, answer);
			}
			counter++;
		}
		return answer;
	}
	/*Generates a random move
	@args bMoves list of legal moves
	@returns int column of move to make
	*/
	public int makeRandomMove(String bMoves)
	{
		//all the random player should care about is picking a column to drop in.
		int m = genRandomMove();
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
			}
		}

		return m;
	}
	/*Generates the random number
	@returns a random number between 1 and 7
	*/
	public int genRandomMove()
	{
		return chooser.nextInt(7)+1;
	}
	/*Makes a dumb move, essentially random until it notices a winning position for itself and
		purposely does not take it
	@args cMoves- legal moves, board- the game board, lastTry- the last move the player tried to make
	@returns the column to place the piece in
	*/
	public int makeDumbMove(String cMoves, int[][] board, int lastTry)
	{
		// Make a random move, if its a winning move regenerate random move
		int counter = 0;
		int m = 0;
		boolean win = true;
		int row;
		while(counter <80){
		m = makeRandomMove(cMoves);
		for (row=0; row<MAXROW; row++)
                        if (m>6 || board[row][m]>0) break;
                if (row>0) 
               {
                        board[--row][m]=2;
                        win = gameStatus(board);
                        board[row][m]=0;
                        if(!win)	
                        	return m;
                }
                counter++;
		}
		return m;
	}
	/*Makes a defensive move, tries to block the user whenever it notices multiple pieces
		in a row
	@args cMoves- legal moves, board- the game board, lastTry- the last move the player tried to make
	@returns the column to place the piece in
	*/
	public int makeDefensiveMove(String cMoves, int[][] board, int lastTry)
	{
		// Array of heuristic values
		int[] heuristics = new int[cMoves.length()];

		// For each possible move...
		for(int i=0;i<cMoves.length();i++)
		{
			int move = Character.getNumericValue(cMoves.charAt(i)) - 1; //get possible moves
			int[][] child1 = makeMove(board, move, 2); //make move for opponent
			int[][] child2 = makeMove(board, move, 1); //make move for you
			heuristics[i] = heuristic(child2, 1) - heuristic(child1, 1); //find heuristic for you - opponent
		}

		// Find the move minimizing token 1's chances at winning
		int max = Integer.MIN_VALUE;
		int bestMove = 0;
		for(int i=0;i<heuristics.length;i++)
		{
			if(heuristics[i] > max && !(i==lastTry))
			{
				max = heuristics[i];
				bestMove = i;
			}
		}

		return bestMove + 1;
	}
	/*Makes an aggressive move, will choose the position that maximizes it's chance of winning
	@args cMoves- legal moves, board- the game board, lastTry- the last move the player tried to make
	@returns the column to place the piece in
	*/
	public int makeAggressiveMove(String dMoves, int[][] board, int lastTry)
	{
		// Array of heuristic values
		int[] heuristics = new int[dMoves.length()];

		// For each possible move...
		for(int i=0;i<dMoves.length();i++)
		{
			int move = Character.getNumericValue(dMoves.charAt(i)) - 1; //get possible moves
			int[][] child1 = makeMove(board, move, 2); //make move for opponent
			int[][] child2 = makeMove(board, move, 1); //make move for you
			heuristics[i] = heuristic(child1, 1) - heuristic(child2, 1); //find heuristic for you - opponent
		}

		// Find the move maximizing token 1's chances at winning
		int min = Integer.MAX_VALUE;
		int bestMove = 0;
		for(int i=0;i<heuristics.length;i++)
		{
			if(heuristics[i] < min && !(i==lastTry))
			{
				min = heuristics[i];
				bestMove = i;
			}
		}

		return bestMove + 1;
	}
	/*Generates a heuristic value for each possible move by counting how many tokens
		belonging to one player are in each row, column, or diagonal
	@args board, token
	@returns utility value of a move
	*/
	public int heuristic(int[][] board, int token)
	{
		int h = 0;

		// Look at sequences in the rows
		for(int row=0;row<6;row++)
		{
			int count = 0;
		    for(int col=0;col<7;col++)
		    {
				if(board[row][col] == token)
				{
					count++;
				}
		        else
		        {
					h+=utility(count);
					count = 0;
				}
			}
			h+=utility(count);
		}

		// Look at sequences in the columns
		for(int col = 0;col<7;col++)
		{
			int count = 0;
		    for(int row = 0;row<6;row++)
		    {
				if(board[row][col] == token)
				{
					count++;
				}
		        else
		        {
					h+=utility(count);
					count = 0;
				}
			}
			h+=utility(count);
		}

		// Look at sequences in the diagonals
		for(int col = 0;col<7;col++)
		{
			int count = 0;
			for(int row = 0;row<6;row++)
			{
				count = 0;
				for(int delta = 0;delta<6;delta++)
				{
					if((row+delta) < 6 && (col+delta) < 7)
					{
						if(board[row+delta][col+delta] == token)
						{
							count++;
						}
						else
						{
							h+=utility(count);
							count = 0;
						}
					}
				}
			}
			h+=utility(count);
		}

		return h;
	}
	/*Gives a utility value to each move
	@args count of how many tokens are in a row, column, or diagonal
	@returns the utility value
	*/
	private int utility(int count) {
		if(count == 2)
		{
			return 10;
		}
		else if(count == 3)
		{
			return 100;
		}
		else if(count>=4)
		{
			return 666666;
		}
		else
		{
			return 0;
		}
	}
	/*Makes the move using whatever color is passed
	@args board, column, token
	@returns updated board
	*/
	private int[][] makeMove(int[][] board, int column, int token)
	{
		int[][] result = new int[6][7];

		// copy the array into result
		for(int row = 0;row<6;row++)
		{
			for(int col=0;col<7;col++)
			{
				result[row][col] = board[row][col];
			}
		}

		// place token into col
		for(int row = 5;row>=0;row--)
		{
			if(result[row][column] == 0)
			{
				result[row][column] = token;
				return result;
			}
		}

		//in case you get here, but you shouldn't
		return result;
	}
	/*Accessor for player color
	@returns player color
	*/
	public int getPlayerColor()
	{
		return Color;
	}
	/*Accessor for player type
	@returns player type
	*/
	public String getPlayerType()
	{
		return playerType;
	}
	/*The Connect 4 game will push the score in a setter to AI player which will then change playerType based on that.
	@args score achieved by human player
	*/
	public void setPlayerType(int score)
	{
			if (score == 0)
				playerType = "Dumb";
			else if(score < 20)
				playerType = "Random";
			else if(score >= 20 && score < 50)
				playerType = "Aggressive";
			else if(score >= 50)
				playerType = "Defensive";
			return;
	}
	/*Check to see if move is winning move
	@args board
	@returns whether the move is a winning move
	*/
	public boolean gameStatus(int[][]board)
        {
        	int[][] boardArray = board;
        	for (int row=0; row<MAXROW; row++) 
               {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot>0
                                 && slot == boardArray[row][col+1]
                                 && slot == boardArray[row][col+2]
                                 && slot == boardArray[row][col+3]) {
                                        return true;
                                }
                        }
                }
                for (int col=0; col<MAXCOL; col++) 
               {
                        for (int row=0; row<MAXROW-3; row++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot>0
                                 && slot == boardArray[row+1][col]
                                 && slot == boardArray[row+2][col]
                                 && slot == boardArray[row+3][col]){
                                        return true;
                                 }
                        }
                }
                for (int row=0; row<MAXROW-3; row++) 
                {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot>0
                                 && slot == boardArray[row+1][col+1]
                                 && slot == boardArray[row+2][col+2]
                                 && slot == boardArray[row+3][col+3]){
                                        return true;
                                 }
                        }
                }
                for (int row=MAXROW-1; row>=3; row--) 
               {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot>0
                                 && slot == boardArray[row-1][col+1]
                                 && slot == boardArray[row-2][col+2]
                                 && slot == boardArray[row-3][col+3]){
                                        return true;
                                 }
                        }
                }
                return false;
        }
}
