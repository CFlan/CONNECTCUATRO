import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
 
public class Connect4 {
        public static void main(String[] args) 
       {
                Connect4UI frame = new Connect4UI();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}

class Connect4Menu extends JFrame
{
    public Connect4Menu()
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run() {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
                {
                    System.out.println("Exception");
                }

                JPanel panel = new JPanel();
                panel.add(new JLabel("Choose player 1:"));
                DefaultComboBoxModel model = new DefaultComboBoxModel();
                model.addElement("Human Player");
                model.addElement("Random Player");
                model.addElement("Minimax Player");
                model.addElement("AI Player");
                JComboBox comboBox = new JComboBox(model);
                panel.add(comboBox);
                panel.add(new JLabel("Choose player2:"));
                DefaultComboBoxModel model2 = new DefaultComboBoxModel();
                model2.addElement("Human Player");
                model2.addElement("Random Player");
                model2.addElement("Minimax Player");
                model2.addElement("AI Player");
                JComboBox comboBox2 = new JComboBox(model2);
                panel.add(comboBox2);

                int result = JOptionPane.showConfirmDialog(null, panel, "Players", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);                
            }
        });
    }
}
class Connect4UI extends JFrame implements ActionListener 
{
        private JButton column1, column2, column3, column4, column5, column6, column7;
        private Label           butPos;
        MenuItem                newGame, exitGame, redPlayer, bluePlayer;
        int[][]                 boardArray;
        boolean                 gameover;
        boolean                 first;
        boolean			sim = false;
        int		round=1;
        int		wins = 0;
        int		score = 50;

        public static final int BLANK = 0;
        public static final int RED = 1;
        public static final int BLUE = 2;
        public static final int MAXROW = 6;     
        public static final int MAXCOL = 7;     
        public static final String SPACE = "                  "; 
        AIplayer AIplayer;
        int activeColour = RED;
       
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
 
        public void setup() 
       {
                boardArray=new int[MAXROW][MAXCOL];
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++)
                                boardArray[row][col]=BLANK;
                first=true;
               	gameover=false;
                activeColour = RED;
        	System.out.println("Next game starting. Round:" + (round) + " " + gameover + " " + activeColour);
                AIplayer = new AIplayer(BLUE);
        }
 
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
 
        public boolean place(int n) 
       {
       	        if(sim)
       	        	return false;
       	        if(activeColour==BLUE)
       	        {
       	        	AIplace(AIplayer.makeMove(availMoves()));
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
                        	AIplace(AIplayer.makeMove(availMoves()));
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
        
        public String availMoves()
        {
        	String aMoves = "";
        	for(int i = 0; i < 7; i++)
        	{
        		if(boardArray[0][i]==0)
        			aMoves = aMoves+(i+1);
        	}
        	System.out.println(aMoves);
        	return aMoves;
        }
        
        public void updateScore(int n)
        {
        	int cScore = 0;
        	
        	score = score + cScore;

        }
        
        public void displayWinner(int n) 
       {
                gameover=true;
                if (n==RED && gameover)
                {
                	wins++;
                	score = score+20;
                        JOptionPane.showMessageDialog(this, "You win. Current record is: You:" + wins + "- Computer:" + (round-wins) + ". Your current score is: " + score + ".");
                        //score calculation code
                }
                else if(gameover)
                {
                	score = score-20;
                        JOptionPane.showMessageDialog(this, "Computer wins. Current record is: You:" + wins + "- Computer:" + (round-wins) + ". Your current score is: " + score + ".");
                        //score calculation code
                }
                sim = true;
                column7.doClick();
                gameover=false;
        }
        
        public boolean gameStatus()
        {
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
        
        public void nextGame()
        {
               	if(round==7)
               	{
               		if(wins>3)
               			JOptionPane.showMessageDialog(this, "You win with a record of: You:" + wins + "- Computer:" + (round-wins) + ". Your score was: " + score + ".");
               		else
               			JOptionPane.showMessageDialog(this, "You lose with a record of: You:" + wins + "- Computer:" + (round-wins) + ". Your score was: " + score + ".");
               		//Score calculation and display based on score here
               		round = 1;
               	        wins = 0;
               	        sim = false;
                        gameover=false;
                        setup();
                        repaint();
                        score = 0;
               		return;
               	}
               	round++;
                setup();
                repaint();
                sim = false;
        }
 
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
