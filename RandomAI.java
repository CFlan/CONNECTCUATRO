/*
Connor Flanagan, Cristopher Spanos, Surya Teja Chinta, Kari Gilbertson
This is the code for the random AI for connect 4
*/

import java.util.Random;

public class RandomAI
{ 
	
	Random chooser = new Random();
	int column;

	public class RandomAI(int playerColor, )
	{
	
	
	}
	
	int public makeMove ()
	{
		int position;
		//all the random player should care about is picking a column to drop in.
		column = chooser.nextInt(8);
		//Make sure that column isn't filled, if it is, choose again. If there is still space there, make the move
		
		
		
		//return int of column to place in.
		return position
	}

}
