/*
Connor Flanagan, Cristopher Spanos, Surya Teja Chinta, Kari Gilbertson
This is the code for the random AI for connect 4
*/

import java.util.Random;

public class AIplayer
{ 
	
	Random chooser = new Random();
	int Color;
	public AIplayer(int playerColor)
	{
		Color = playerColor;
	}
	
	public int makeMove (String aMoves)
	{
		//all the random player should care about is picking a column to drop in.
		int m = genMove();
		System.out.println(m+"");
		boolean gen = true;
		while(gen)
		{
			if(aMoves.contains(m+""))
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
}
