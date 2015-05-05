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
		playerType = "RANDOM";
	}
	/*
	makeMove is going to make a decision about which method to call based on the string playerType.
	
	
	*/
	public int makeMove (String aMoves, int[][] board)
	{
		if(playerType == "RANDOM")
			answer = makeRandomMove(aMoves);
		if(playerType == "DEFENSIVE")
		 	answer = makeDefensiveMove(aMoves, board);
		if(playerType == "AGGRESSIVE")
			answer = makeAggressiveMove(aMoves, board);
		 if(playerType == "MINIMAX")
		 	answer = makeMiniMaxMove(aMoves, board);
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
		int m = genDefensiveMove(board);
		System.out.println(m+"");
		boolean gen = true;
		while(gen)
		{
			if(cMoves.contains(m+""))
			{
				gen = false;
			}
			else
			{
				m = genDefensiveMove(board);
				System.out.println(m+"");
			}
		}

		return m;
	}
	public int genDefensiveMove(int[][] board)
	{
		int[] value = new int[7];
		int max = 0;
        int num = 3;
        int sum = 0;

		for (int i=0; i<7; i++)
        {
        	for(int j = 0; j<6; j++)
        	{
            	int col = i;
            	int row = j;
            	//check x direction.
            	//left
	            if ((col>=3) 
	                && (board[row][col-1] == getOppositePlayerColor())
	                && (board[row][col-2] == getOppositePlayerColor())
	                && (board[row][col-3] == getOppositePlayerColor()))
	                value[i] = value[i]+8;
	            //right
	            if ((col<=3) 
	                && (board[row][col+1] == getOppositePlayerColor())
	                && (board[row][col+2] == getOppositePlayerColor())
	                && (board[row][col+3] == getOppositePlayerColor()))
	                value[i] = value[i]+8;
	            //check y direction
	            if ((row<=2) 
	                && (board[row+1][col] == getOppositePlayerColor())
	                && (board[row+2][col] == getOppositePlayerColor())
	                && (board[row+3][col] == getOppositePlayerColor()))
	                value[i] = value[i]+8;
	            //check left diagonal
	            if ((col>=3) && (row<=2)
	                && (board[row+1][col-1] == getOppositePlayerColor())
	                && (board[row+2][col-2] == getOppositePlayerColor())
	                && (board[row+3][col-3] == getOppositePlayerColor()))
	                value[i] = value[i]+8;
	            
	            if ((col<=3) && (row<=2)
	                && (board[row+1][col+1] == getOppositePlayerColor())
	                && (board[row+2][col+2] == getOppositePlayerColor())
	                && (board[row+3][col+3] == getOppositePlayerColor()))
	                value[i] = value[i]+8;
	            
	            if ((col>=3) && (row>=3)
	                && (board[row-1][col-1] == getOppositePlayerColor())
	                && (board[row-2][col-2] == getOppositePlayerColor())
	                && (board[row-3][col-3] == getOppositePlayerColor()))
	                value[i] = value[i]+8;
	            
	            if ((col<=3) && (row>=3)
	                && (board[row-1][col+1] == getOppositePlayerColor())
	                && (board[row-2][col+2] == getOppositePlayerColor())
	                && (board[row-3][col+3] == getOppositePlayerColor()))
	                value[i] = value[i]+8;
	            
	            if ((col>=2) 
	                && (board[row][col-1] == getOppositePlayerColor())
	                && (board[row][col-2] == getOppositePlayerColor()))
	                value[i] = value[i]+4;
	            //right
	            if ((col<=4) 
	                && (board[row][col+1] == getOppositePlayerColor())
	                && (board[row][col+2] == getOppositePlayerColor()))
	                value[i] = value[i]+4;
	            //check y direction
	            if ((row<=3) 
	                && (board[row+1][col] == getOppositePlayerColor())
	                && (board[row+2][col] == getOppositePlayerColor()))
	                value[i] = value[i]+4;
	            //check left diagonal
	            if ((col>=2) && (row<=3)
	                && (board[row+1][col-1] == getOppositePlayerColor())
	                && (board[row+2][col-2] == getOppositePlayerColor()))
	                value[i] = value[i]+4;
	            
	            if ((col<=4) && (row<=3)
	                && (board[row+1][col+1] == getOppositePlayerColor())
	                && (board[row+2][col+2] == getOppositePlayerColor()))
	                value[i] = value[i]+4;
	            
	            if ((col>=2) && (row>=2)
	                && (board[row-1][col-1] == getOppositePlayerColor())
	                && (board[row-2][col-2] == getOppositePlayerColor()))
	                value[i] = value[i]+4;
	            
	            if ((col<=4) && (row>=2)
	                && (board[row-1][col+1] == getOppositePlayerColor())
	                && (board[row-2][col+2] == getOppositePlayerColor()))
	                value[i] = value[i]+4;
	            
	            if ((col>=1) 
	                && (board[row][col-1] == getOppositePlayerColor()))
	                value[i] = value[i]+2;
	            //right
	            
	            if ((col<=5) 
	                && (board[row][col+1] == getOppositePlayerColor()))
	                value[i] = value[i]+2;
	            //check y direction
	            if ((row<=4) 
	                && (board[row+1][col] == getOppositePlayerColor()))
	                value[i] = value[i]+2;
	            //check left diagonal
	            if ((col>=1) && (row<=4)
	                && (board[row+1][col-1] == getOppositePlayerColor()))
	                value[i] = value[i]+2;
	            
	            if ((col<=5) && (row<=4)
	                && (board[row+1][col+1] == getOppositePlayerColor()))
	                value[i] = value[i]+2;
	            
	            if ((col>=1) && (row>=1)
	                && (board[row-1][col-1] == getOppositePlayerColor()))
	                value[i] = value[i]+2;
	            
	            if ((col<=5) && (row>=1)
	                && (board[row-1][col+1] == getOppositePlayerColor()))
	                value[i] = value[i]+2;   
          	}              
        }

        for (int i=0; i<7; i++)
        {
            if (value[i] > max)
            {
            	max = value[i];
            	num = i;
            }
            sum = sum + value[i];
        }
        if (sum == 0)
        	num = (int)(Math.random()*7);
        return num;
	}
	public int makeAggressiveMove(String dMoves, int[][] board)
	{
		int m = genAggressiveMove(board);
		System.out.println(m+"");
		boolean gen = true;
		while(gen)
		{
			if(dMoves.contains(m+""))
			{
				gen = false;
			}
			else
			{
				m = genAggressiveMove(board);
				System.out.println(m+"");
			}
		}

		return m;
	}
	public int genAggressiveMove(int[][] board)
	{
		int[] value = new int[7];
		int max = 0;
        int num = 3;
        int sum = 0;

		for (int i=0; i<7; i++)
        {
        	for(int j = 0; j<6; j++)
        	{
            	int col = i;
            	int row = j;

            	if ((col>=3) 
                	&& (board[row][col-1] == getPlayerColor())
                	&& (board[row][col-2] == getPlayerColor())
                	&& (board[row][col-3] == getPlayerColor()))
                	value[i]=value[i]+16;
	            //right
	            if ((col<=3) 
	                && (board[row][col+1] == getPlayerColor())
	                && (board[row][col+2] == getPlayerColor())
	                && (board[row][col+3] == getPlayerColor()))
	                value[i]=value[i]+16;
	            //check y direction
	            if ((row<=2) 
	                && (board[row+1][col] == getPlayerColor())
	                && (board[row+2][col] == getPlayerColor())
	                && (board[row+3][col] == getPlayerColor()))
	                value[i]=value[i]+16;
	            //check left diagonal
	            if ((col>=3) && (row<=2)
	                && (board[row+1][col-1] == getPlayerColor())
	                && (board[row+2][col-2] == getPlayerColor())
	                && (board[row+3][col-3] == getPlayerColor()))
	                value[i]=value[i]+16;
	            
	            if ((col<=3) && (row<=2)
	                && (board[row+1][col+1] == getPlayerColor())
	                && (board[row+2][col+2] == getPlayerColor())
	                && (board[row+3][col+3] == getPlayerColor()))
	                value[i]=value[i]+16;
	            
	            if ((col>=3) && (row>=3)
	                && (board[row-1][col-1] == getPlayerColor())
	                && (board[row-2][col-2] == getPlayerColor())
	                && (board[row-3][col-3] == getPlayerColor()))
	                value[i]=value[i]+16;
	            
	            if ((col<=3) && (row>=3)
	                && (board[row-1][col+1] == getPlayerColor())
	                && (board[row-2][col+2] == getPlayerColor())
	                && (board[row-3][col+3] == getPlayerColor()))
	                value[i]=value[i]+16;
	            
	            if ((col>=2) 
	                && (board[row][col-1] == getPlayerColor())
	                && (board[row][col-2] == getPlayerColor()))
	                value[i]=value[i]+4;
	            //right
	            if ((col<=4) 
	                && (board[row][col+1] == getPlayerColor())
	                && (board[row][col+2] == getPlayerColor()))
	                value[i]=value[i]+4;
	            //check y direction
	            if ((row<=3) 
	                && (board[row+1][col] == getPlayerColor())
	                && (board[row+2][col] == getPlayerColor()))
	                value[i]=value[i]+4;
	            //check left diagonal
	            if ((col>=2) && (row<=3)
	                && (board[row+1][col-1] == getPlayerColor())
	                && (board[row+2][col-2] == getPlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col<=4) && (row<=3)
	                && (board[row+1][col+1] == getPlayerColor())
	                && (board[row+2][col+2] == getPlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col>=2) && (row>=2)
	                && (board[row-1][col-1] == getPlayerColor())
	                && (board[row-2][col-2] == getPlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col<=4) && (row>=2)
	                && (board[row-1][col+1] == getPlayerColor())
	                && (board[row-2][col+2] == getPlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col>=1) 
	                && (board[row][col-1] == getPlayerColor()))
	                value[i]=value[i]+2;
	            //right
	            
	            if ((col<=5) 
	                && (board[row][col+1] == getPlayerColor()))
	                value[i]=value[i]+2;
	            //check y direction
	            if ((row<=4) 
	                && (board[row+1][col] == getPlayerColor()))
	                value[i]=value[i]+2;
	            //check left diagonal
	            if ((col>=1) && (row<=4)
	                && (board[row+1][col-1] == getPlayerColor()))
	                value[i]=value[i]+2;
	            
	            if ((col<=5) && (row<=4)
	                && (board[row+1][col+1] == getPlayerColor()))
	                value[i]=value[i]+2;
	            
	            if ((col>=1) && (row>=1)
	                && (board[row-1][col-1] == getPlayerColor()))
	                value[i]=value[i]+2;
	            
	            if ((col<=5) && (row>=1)
	                && (board[row-1][col+1] == getPlayerColor()))
	                value[i]=value[i]+2;
	            
	            //check x direction.
	            //left
	            if ((col>=3) 
	                && (board[row][col-1] == getOppositePlayerColor())
	                && (board[row][col-2] == getOppositePlayerColor())
	                && (board[row][col-3] == getOppositePlayerColor()))
	                value[i]=value[i]+8;
	            //right
	            if ((col<=3) 
	                && (board[row][col+1] == getOppositePlayerColor())
	                && (board[row][col+2] == getOppositePlayerColor())
	                && (board[row][col+3] == getOppositePlayerColor()))
	                value[i]=value[i]+8;
	            //check y direction
	            if ((row<=2) 
	                && (board[row+1][col] == getOppositePlayerColor())
	                && (board[row+2][col] == getOppositePlayerColor())
	                && (board[row+3][col] == getOppositePlayerColor()))
	                value[i]=value[i]+8;
	            //check left diagonal
	            if ((col>=3) && (row<=2)
	                && (board[row+1][col-1] == getOppositePlayerColor())
	                && (board[row+2][col-2] == getOppositePlayerColor())
	                && (board[row+3][col-3] == getOppositePlayerColor()))
	                value[i]=value[i]+8;
	            
	            if ((col<=3) && (row<=2)
	                && (board[row+1][col+1] == getOppositePlayerColor())
	                && (board[row+2][col+2] == getOppositePlayerColor())
	                && (board[row+3][col+3] == getOppositePlayerColor()))
	                value[i]=value[i]+8;
	            
	            if ((col>=3) && (row>=3)
	                && (board[row-1][col-1] == getOppositePlayerColor())
	                && (board[row-2][col-2] == getOppositePlayerColor())
	                && (board[row-3][col-3] == getOppositePlayerColor()))
	                value[i]=value[i]+8;
	            
	            if ((col<=3) && (row>=3)
	                && (board[row-1][col+1] == getOppositePlayerColor())
	                && (board[row-2][col+2] == getOppositePlayerColor())
	                && (board[row-3][col+3] == getOppositePlayerColor()))
	                value[i]=value[i]+8;
	            
	            if ((col>=2) 
	                && (board[row][col-1] == getOppositePlayerColor())
	                && (board[row][col-2] == getOppositePlayerColor()))
	                value[i]=value[i]+4;
	            //right
	            if ((col<=4) 
	                && (board[row][col+1] == getOppositePlayerColor())
	                && (board[row][col+2] == getOppositePlayerColor()))
	                value[i]=value[i]+4;
	            //check y direction
	            if ((row<=3) 
	                && (board[row+1][col] == getOppositePlayerColor())
	                && (board[row+2][col] == getOppositePlayerColor()))
	                value[i]=value[i]+4;
	            //check left diagonal
	            if ((col>=2) && (row<=3)
	                && (board[row+1][col-1] == getOppositePlayerColor())
	                && (board[row+2][col-2] == getOppositePlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col<=4) && (row<=3)
	                && (board[row+1][col+1] == getOppositePlayerColor())
	                && (board[row+2][col+2] == getOppositePlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col>=2) && (row>=2)
	                && (board[row-1][col-1] == getOppositePlayerColor())
	                && (board[row-2][col-2] == getOppositePlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col<=4) && (row>=2)
	                && (board[row-1][col+1] == getOppositePlayerColor())
	                && (board[row-2][col+2] == getOppositePlayerColor()))
	                value[i]=value[i]+4;
	            
	            if ((col>=1) 
	                && (board[row][col-1] == getOppositePlayerColor()))
	                value[i]=value[i]+2;
	            //right
	            
	            if ((col<=5) 
	                && (board[row][col+1] == getOppositePlayerColor()))
	                value[i]=value[i]+2;
	            //check y direction
	            if ((row<=4) 
	                && (board[row+1][col] == getOppositePlayerColor()))
	                value[i]=value[i]+2;
	            //check left diagonal
	            if ((col>=1) && (row<=4)
	                && (board[row+1][col-1] == getOppositePlayerColor()))
	                value[i]=value[i]+2;
	            
	            if ((col<=5) && (row<=4)
	                && (board[row+1][col+1] == getOppositePlayerColor()))
	                value[i]=value[i]+2;
	            
	            if ((col>=1) && (row>=1)
	                && (board[row-1][col-1] == getOppositePlayerColor()))
	                value[i]=value[i]+2;
	            
	            if ((col<=5) && (row>=1)
	                && (board[row-1][col+1] == getOppositePlayerColor()))
	                value[i]=value[i]+2;            
          	}              
        }

        for (int i=0; i<7; i++)
        {
            if (value[i] > max)
            {
            	max = value[i];
            	num = i;
            }
            sum = sum + value[i];
        }
        if (sum == 0)
        	num = (int)(Math.random()*7);
        return num;
	}
	public int makeMiniMaxMove(String eMoves, int[][] board)
	{
		int m = genMiniMaxMove(board);
		System.out.println(m+"");
		boolean gen = true;
		while(gen)
		{
			if(eMoves.contains(m+""))
			{
				gen = false;
			}
			else
			{
				m = genMiniMaxMove(board);
				System.out.println(m+"");
			}
		}

		return m;
	}
	public int genMiniMaxMove(int[][] board)
	{
	
	}

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
}
