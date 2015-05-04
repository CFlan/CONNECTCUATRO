/*
Connor Flanagan, Cristopher Spanos, Surya Teja Chinta, Kari Gilbertson
This is the code for the adaptive AI for connect 4.
At the end of every game we set the player type and then run a method based on a specific kind of AI.
*/

import java.util.Random;

public class AIplayer
{ 
	//Color tracks which player the AI is. playerType tracks what kind of moves the AI should be making. Answer returns the column of the move to be made regardless of the AI type.
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
	public int makeMove (String aMoves)
	{
		answer = makeRandomMove(aMoves);
		return answer;
	}
	
	/*
	makeRandomMove just chooses a random column on the board using genRandomMove and returns it to the game
	
	
	*/
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
	
	/*
	genRandomMove just uses Random to generate a column for makeRandomMove to use.
	*/
	public int genRandomMove()
	{
		return chooser.nextInt(7)+1;
	}
	/*
	getPlayerColor just returns the Color, or player number, of the AI.
	*/
	public int getPlayerColor()
	{
		return Color;
	}
	/*
	getPlayerType is just a getter for the Player type
	
	*/
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
