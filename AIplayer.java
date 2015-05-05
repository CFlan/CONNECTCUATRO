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
	public int makeMove (String aMoves)
	{
		answer = makeRandomMove(aMoves);
		answer = makeMiniMaxMove(aMoves);
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
	public int makeMiniMaxMove(String cMoves)
	{

	}
	public int genMiniMaxMove()
	{

	}
	public int makeAggressiveMove(String dMoves)
	{

	}
	public int genAggressiveMove()
	{

	}
	public int makeDefensiveMove(String eMoves)
	{

	}
	public int genDefensiveMove()
	{

	}

	public int getRandomPlayerColor()
	{
		return Color;
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
			
			
			return playerType;
	}
}
