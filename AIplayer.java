/*
Connor Flanagan, Cristopher Spanos, Surya Teja Chinta, Kari Gilbertson
This is the code for the random AI for connect 4
*/

import java.util.Random;

public class AIplayer
{ 
	
	Random chooser = new Random();
	int column;
	int Color;
	public AIplayer(int playerColor)
	{
		Color = playerColor;
	}
	
	public int makeMove ()
	{
		int position;
		//all the random player should care about is picking a column to drop in.
		column = chooser.nextInt(7);
		//Make sure that column isn't filled, if it is, choose again. If there is still space there, make the move
		return column;
	}
	public int getRandomPlayerColor()
	{
		return Color;
	}
}
