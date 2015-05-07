//Connect4 Driver/Board
//Surya Chinta, Connor Flanagan, Chris Spanos, Kari Anastasia
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
 
public class Connect4 {
	//main method
        public static void main(String[] args) 
       {
                Connect4UI frame = new Connect4UI();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}

 
class Connect4UI extends JFrame implements ActionListener 
{
	//buttons for placing tokens
        private JButton column1, column2, column3, column4, column5, column6, column7;
        //label used to position buttons
        private Label           butPos;
        //menu options to start new game or exit
        MenuItem                newGame, exitGame, redPlayer, bluePlayer;
        //Array which holds state of the board, 0 means no token, 1 is human player token, and 2 is computer player token
        int[][]                 boardArray;
        //Whether the current round is over
        boolean                 gameover;
        //Whether this is the first move
        boolean                 first;
        //Whether game is over
        boolean			sim = false;
        //boolean  used to decide which AI to play
        boolean			isLearning = false;
        //Current round
        int		round=1;
        //Current number of wins
        int		wins = 0;
        //Current score
        int		score = 10;
        //Final variables to hold token number designations and board dimensions
        public static final int BLANK = 0;
        public static final int RED = 1;
        public static final int BLUE = 2;
        public static final int MAXROW = 6;     
        public static final int MAXCOL = 7;     
        public static final String SPACE = "                  "; 
        //AIplayer
        AIplayer AIplayer;
        //Current player
        int activeColour = RED;
       //sets up connect4 gui
        public Connect4UI() 
        {
                setTitle("Connect4=");

                MenuBar menuB = new MenuBar();
                Menu fileMenu = new Menu("File");
                newGame = new MenuItem("New");
                newGame.addActionListener(this);
                fileMenu.add(newGame);
                exitGame = new MenuItem("Exit");
                exitGame.addActionListener(this);
                fileMenu.add(exitGame);
                menuB.add(fileMenu);
                Menu optionsM = new Menu("Options");
                redPlayer = new MenuItem("Red starts");
                redPlayer.addActionListener(this);
                optionsM.add(redPlayer);
                bluePlayer = new MenuItem("Blue starts");
                bluePlayer.addActionListener(this);
                optionsM.add(bluePlayer);
                menuB.add(optionsM);
                setMenuBar(menuB);
 
                Panel panel = new Panel();
 
                column1 = new JButton("v");
                column1.addActionListener(this);
                panel.add(column1);
                butPos = new Label(SPACE);
                panel.add(butPos);
 
                column2 = new JButton("v");
                column2.addActionListener(this);
                panel.add(column2);
                butPos = new Label(SPACE);
                panel.add(butPos);
 
                column3 = new JButton("v");
                column3.addActionListener(this);
                panel.add(column3);
                butPos = new Label(SPACE);
                panel.add(butPos);
 
                column4 = new JButton("v");
                column4.addActionListener(this);
                panel.add(column4);
                butPos = new Label(SPACE);
                panel.add(butPos);
 
                column5 = new JButton("v");
                column5.addActionListener(this);
                panel.add(column5);
                butPos = new Label(SPACE);
                panel.add(butPos);
 
                column6 = new JButton("v");
                column6.addActionListener(this);
                panel.add(column6);
                butPos = new Label(SPACE);
                panel.add(butPos);
 
                column7 = new JButton("v");
                column7.addActionListener(this);
                panel.add(column7);
 
                add(panel, BorderLayout.NORTH);
                setup();
                setSize(1024, 768);
        }
        //resets the board and the game variables
        public void setup() 
       {
                boardArray=new int[MAXROW][MAXCOL];
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++)
                                boardArray[row][col]=BLANK;
                first=true;
               	gameover=false;
                activeColour = RED;
        	System.out.println("Next game starting. Round:" + (round));
                if (!isLearning)
			{
				Object[] possibilities = {"Random", "Defensive", "Aggressive", "MiniMax", "Learning"};
				String answer = (String)JOptionPane.showInputDialog(
					null,
					"What type of player do you want to play?\n",
					"Difficulty",
					JOptionPane.PLAIN_MESSAGE,
					null,
					possibilities,
					"Learning");
					if (answer == "Learning")
					{
						isLearning = true;
						answer = "Random";
					}
					else
					{
						isLearning = false;
					}
					AIplayer = new AIplayer(BLUE);
					System.out.println("Answers = " + answer);
					AIplayer.setType(answer);
			}
		else
		{
			AIplayer.setPlayerType(score);
		}
        }
        //updates gui to display current board
        public void paint(Graphics g) 
       {
                g.setColor(Color.YELLOW);
                g.fillRect(110, 50, 100+100*MAXCOL, 100+100*MAXROW);
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++) 
                       {
                                if (boardArray[row][col]==BLANK) g.setColor(Color.BLACK);
                                if (boardArray[row][col]==RED) g.setColor(Color.RED);
                                if (boardArray[row][col]==BLUE) g.setColor(Color.BLUE);
                                g.fillOval(160+100*col, 100+100*row, 100, 100);
                        }
        }
       //places token in column n then triggers aiPlayer's turn if game is not over
       //@args n is the column where the human player chose to place the token
       //@return returns whether the token was successfully placed or not
        public boolean place(int n) 
       {
       	        if(sim)
       	        	return false;
       	        if(activeColour==BLUE)
       	        {
       	        	AIplace(AIplayer.makeMove(availMoves(), boardArray));
       	        	return false;
       	        }
       	        System.out.println("You choose column " + n);
                first=true;
                int row;
                n--;
                for (row=0; row<MAXROW; row++)
                        if (n>6 || boardArray[row][n]>0) break;
                
                if (row>0) 
               {
                        boardArray[--row][n]=activeColour;
                        if (activeColour==RED)
                                activeColour=BLUE;
                        else
                                activeColour=RED;;
                        repaint();
                        gameover = gameStatus();
                        if(!gameover)
                        {
                        	updateScoreTurn(n);
                        	AIplace(AIplayer.makeMove(availMoves(), boardArray));
                        }
                        else
                        {
                        	gameover = false;
                        	return false;
                        }
                        return true;
                }
                return false;
        }
        //places the ai's token in column n
        //@args n is the column the ai chose
        //@return returns whether the token was succesfully placed or not
        public boolean AIplace(int n) 
       {
       	        if (gameover) return false;
                first=true;
                int row;
                n--;
		System.out.println("I choose column " + (n + 1));
                for (row=0; row<MAXROW; row++)
                        if (n>6 || boardArray[row][n]>0) break;
                if (row>0) 
               {
                        boardArray[--row][n]=activeColour;
                        if (activeColour==RED)
                                activeColour=BLUE;
                        else
                                activeColour=RED;
                        repaint();
                        gameover = gameStatus();
                        if(gameover)
                        {
                        	gameover = false;
                        	return false;
                        }
                        return true;
                }
                return false;
        }
        //finds a list of columns which still have open slots then formats into a string
        //@returns a string representing the columns which are not filed in
        public String availMoves()
        {
        	String aMoves = "";
        	for(int i = 0; i < 7; i++)
        	{
        		if(boardArray[0][i]==0)
        			aMoves = aMoves+(i+1);
        	}
        	return aMoves;
        }
        //Updates the score at the end of each turn, factors that are looked are shown in comments
        //@args n is the column where the human player last placed a token
        public void updateScoreTurn(int n)
        {
        	int counter = 0;
        	boolean subtract = false;
        	//failing to block a potentially winning move and opponent in a position where they can win multiple ways
        	for (int row=0; row<MAXROW; row++) 
               {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 2 == boardArray[row][col+1]
                                 && 2 == boardArray[row][col+2]
                                 && 2 == boardArray[row][col+3]) {
                                        subtract = true;
                                        counter++;
                                }
                        }
                }
                for (int col=0; col<MAXCOL; col++) 
               {
                        for (int row=0; row<MAXROW-3; row++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 2 == boardArray[row+1][col]
                                 && 2 == boardArray[row+2][col]
                                 && 2 == boardArray[row+3][col]){
                                        subtract = true;
                                        counter++;
                                 }
                        }
                }
                for (int row=0; row<MAXROW-3; row++) 
                {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 2 == boardArray[row+1][col+1]
                                 && 2 == boardArray[row+2][col+2]
                                 && 2 == boardArray[row+3][col+3]){
                                        subtract = true;
                                        counter++;
                                 }
                        }
                }
                for (int row=MAXROW-1; row>=3; row--) 
               {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 2 == boardArray[row-1][col+1]
                                 && 2 == boardArray[row-2][col+2]
                                 && 2 == boardArray[row-3][col+3]){
                                        subtract = true;
                                        counter++;
                                 }
                        }
                }
                if(counter>1)
                	score = score -20;
                counter = 0;
                //putting yourself into a position with multiple winning moves and not taking a winning move
                for (int row=0; row<MAXROW; row++) 
               {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 1 == boardArray[row][col+1]
                                 && 1 == boardArray[row][col+2]
                                 && 1 == boardArray[row][col+3]) {
                                        counter++;
                                        if(!(col==n))
                                        	subtract = true;
                                }
                        }
                }
                for (int col=0; col<MAXCOL; col++) 
               {
                        for (int row=0; row<MAXROW-3; row++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 1 == boardArray[row+1][col]
                                 && 1 == boardArray[row+2][col]
                                 && 1 == boardArray[row+3][col]){
                                       counter++;
                                       if(!(col==n))
                                       	       subtract = true;
                                 }
                        }
                }
                for (int row=0; row<MAXROW-3; row++) 
                {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 1 == boardArray[row+1][col+1]
                                 && 1 == boardArray[row+2][col+2]
                                 && 1 == boardArray[row+3][col+3]){
                                        counter++;
                                        if(!(col==n))
                                       	       subtract = true;
                                 }
                        }
                }
                for (int row=MAXROW-1; row>=3; row--) 
               {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot==0
                                 && 1 == boardArray[row-1][col+1]
                                 && 1 == boardArray[row-2][col+2]
                                 && 1 == boardArray[row-3][col+3]){
                                        counter++;
                                        if(!(col==n))
                                       	       subtract = true;
                                 }
                        }
                }
                if(counter>1&&!subtract)
                	score=score+25;
                if(subtract)
                	score = score-5;
        	
        }
        //Updates score at the end of the round
        //@args n is the column in where the last human player token was placed
        public void updateScore(int n)
        {
        	int cScore = 0;
        	//Blocking the opponent, opponent having 3 in a row & getting 3 in a row
        	for (int row=0; row<MAXROW; row++) 
               {
                        for (int col=0; col<MAXCOL-2; col++) 
                       {
                                int slot =  boardArray[row][col];
                                if (slot>0
                                 && (slot == boardArray[row][col+1])
                                 && (slot == boardArray[row][col+2])) {
                                	if(slot == 1)
                                		cScore = cScore +4;
                                	else
                                		cScore = cScore - 8;
                                	break;
                                }
                        }
                }
                for (int col=0; col<MAXCOL; col++) 
               {
                        for (int row=0; row<MAXROW-2; row++) 
                       {
                                int slot =  boardArray[row][col];
                                if (slot>0
                                 && (slot == boardArray[row+1][col])
                                 && (slot == boardArray[row+2][col])){
                                	if(slot == 1)
                                		cScore = cScore + 4;
                                	else
                                		cScore = cScore - 8;
                                	break;
                                 }
                        }
                }
                for (int row=0; row<MAXROW-2; row++) 
                {
                        for (int col=0; col<MAXCOL-2; col++) 
                       {
                                int slot =  boardArray[row][col];
                                if (slot>0
                                 && (slot == boardArray[row+1][col+1])
                                 && (slot == boardArray[row+2][col+2])){
                               		if(slot == 1)
                                		cScore = cScore + 4;
                                	else
                                		cScore = cScore - 8;
                                	break;
                                 }
                        }
                }
                for (int row=MAXROW-1; row>=2; row--) 
               {
                        for (int col=0; col<MAXCOL-2; col++) 
                       {
                                int slot =  boardArray[row][col];
                                if (slot>0
                                 && (slot == boardArray[row-1][col+1])
                                 && (slot == boardArray[row-2][col+2])){
                               		if(slot == 1)
                                		cScore = cScore + 4;
                                	else
                                		cScore = cScore - 8;
                                	break;
                                 }
                        }
                }
        	score = score + cScore;

        }
        //displays the winner and updates score and wins, also shows dialog with this information
        //@args the last player who went, used to determine winner
        public void displayWinner(int n) 
       {
       	       //Displays winner, and adjusts wins and score based on passed in argument which is current player; 0 is a tie, RED is a win, BLUE is a loss
       	        updateScore(0);
                gameover=true;
                if(n==0 && gameover)
                {
                	System.out.println("Score: " + score);
                	wins++;
                		JOptionPane.showMessageDialog(this, "You Tied. Current record is: You:" + wins + "- Computer:" + (round-wins) + ". Your current score is: " + score + ".");
                }
                if (n==RED && gameover)
                {	
                	System.out.println("Score: " + score);
                	wins++;
                	score = score+20;
                	if(score > 100)
                		score = 100;
                	else if(score < 0)
                		score = 0;
                		JOptionPane.showMessageDialog(this, "You win. Current record is: You:" + wins + "- Computer:" + (round-wins) + ". Your current score is: " + score + ".");
                }
                else if(gameover)
                {
                	System.out.println("Score: " + score);
                	score = score-20;
                	if(score > 100)
                		score = 100;
                	else if(score < 0)
                		score = 0;
                		JOptionPane.showMessageDialog(this, "Computer wins. Current record is: You:" + wins + "- Computer:" + (round-wins) + ". Your current score is: " + score + ".");
                }
                sim = true;
                column7.doClick();
                gameover=false;
        }
        
        //Checks to see if board is full or if one of the players have four in a row.
        //@return true if game is over (first time run through), false if game is not over or if gameover has already been set to true for this round
        public boolean gameStatus()
        {
        	if(availMoves().equals(""))
        	{
        		displayWinner(0);
        		return true;
        	}
        	if(gameover)
        		return false;
        	for (int row=0; row<MAXROW; row++) 
               {
                        for (int col=0; col<MAXCOL-3; col++) 
                       {
                                int slot = boardArray[row][col];
                                if (slot>0
                                 && slot == boardArray[row][col+1]
                                 && slot == boardArray[row][col+2]
                                 && slot == boardArray[row][col+3]) {
                                	displayWinner(boardArray[row][col]);
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
                               		displayWinner(boardArray[row][col]);
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
                               		displayWinner(boardArray[row][col]);
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
                               		displayWinner(boardArray[row][col]);
                                        return true;
                                 }
                        }
                }
                return false;
        }
        //Resets all necessary variables and initiates next round. If 7 rounds are over, player rank is calculated and displayed then a new game is started
        public void nextGame()
        {
        	String rank = "";
               	if(round==7)
               	{
               		if(score >99)
               			rank = "Connect Cuatro Superstar";
               		else if(score > 89)
               			rank = "All-Pro";
               		else if(score > 79)
               			rank = "By the Time I Get To Bucharest";
               		else if(score > 69)
               			rank = "Wumpus Hunta";
               		else if(score >59)
               			rank = "Good Enough That These ArenÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢t Insults Any More";
               		else if(score>49)
               			rank = "The Line Between Competence and Anarchy";
               		else if(score>39)
               			rank = "Almost Competent at a Game for Children";
               		else if(score>29)
               			rank = "Go Back to Konane";
               		else if(score>19)
               			rank = "I Usually Play Canadien Rules Connect 4";
               		else if(score>9)
               			rank = "Wait, Get Four in a Row?!?";
               		else
               			rank = "Ayn Random";
               		
               		if(wins>3)
               			JOptionPane.showMessageDialog(this, "You win with a record of: You:" + wins + "- Computer:" + (round-wins) + ". \nYour rank was: " + rank + ".");
               		else
               			JOptionPane.showMessageDialog(this, "You lose with a record of: You:" + wins + "- Computer:" + (round-wins) + ". \nYour rank was: " + rank + ".");
               		//Score calculation and display based on score here
               		round = 1;
               	        wins = 0;
               	        sim = false;
                        gameover=false;
                        setup();
                        repaint();
                        score = 10;
               		return;
               	}
               	round++;
                setup();
                repaint();
                sim = false;
        }
        //Triggers placing of the human player's token in the column for which the button was clicked. Also controls actions of new game and exit menu options
        public void actionPerformed(ActionEvent e) 
       {
                if (e.getSource() == column1)
                {
                        place(1);
                }
                else if (e.getSource() == column2)
                {
                        place(2);
                }
                else if (e.getSource() == column3)
                {
                        place(3);
                }
                else if (e.getSource() == column4)
                {
                        place(4);
                }
                else if (e.getSource() == column5)
                {
                        place(5);
                }
                else if (e.getSource() == column6)
                {
                        place(6);
                }
                else if (e.getSource() == column7){
                        if(!sim){
                	place(7);
                	}
                	else
                	{
                		nextGame();
                		gameover=false;
                	}
                }
                else if (e.getSource() == newGame) 
               {
               	       
               	        round = 1;
               	        wins = 0;
                        gameover=false;
                        setup();
                        repaint();
                       
                } 
                else if (e.getSource() == exitGame) 
               {
                        System.exit(0);
                } 
               else if (e.getSource() == redPlayer) 
               {
                        if (!first) activeColour=RED;
                        AIplayer = new AIplayer(BLUE);
                } 
                else if (e.getSource() == bluePlayer) 
                {
                        if (!first) activeColour=BLUE;
                        AIplayer = new AIplayer(RED);
                }
        }
 
}
