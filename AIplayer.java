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
		return answer;
	}
	public int makeRandomMove(String bMoves)
	{
		//all the random player should care about is picking a column to drop in.
		int m = genMove();
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
				m = genMove();
				System.out.println(m+"");
			}
		}
		return m;
	}
	public int genMove()
	{
		return chooser.nextInt(7)+1;
	}
	public int getRandomPlayerColor()
	{
		return Color;
	}
	public String getPlayerType()
	{
		return playerType;
	}
}
