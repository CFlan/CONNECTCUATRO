import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
 
public class Connect4 
{
    public static void main(String[] args) 
    {
        Connect4Menu menu = new Connect4Menu();
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
    private Button          column1, column2, column3, column4, column5, column6, column7;
    private Label           butPos;
    MenuItem                newGame, exitGame, redPlayer, bluePlayer;
    int[][]                 boardArray;
    boolean                 gameover=false;
    boolean                 first;

    public static final int BLANK = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;
    public static final int MAXROW = 6;     
    public static final int MAXCOL = 7;     
    public static final String SPACE = "  "; 
 
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

        column1 = new Button("v");
        column1.addActionListener(this);
        panel.add(column1);
        butPos = new Label(SPACE);
        panel.add(butPos);

        column2 = new Button("v");
        column2.addActionListener(this);
        panel.add(column2);
        butPos = new Label(SPACE);
        panel.add(butPos);

        column3 = new Button("v");
        column3.addActionListener(this);
        panel.add(column3);
        butPos = new Label(SPACE);
        panel.add(butPos);

        column4 = new Button("v");
        column4.addActionListener(this);
        panel.add(column4);
        butPos = new Label(SPACE);
        panel.add(butPos);

        column5 = new Button("v");
        column5.addActionListener(this);
        panel.add(column5);
        butPos = new Label(SPACE);
        panel.add(butPos);

        column6 = new Button("v");
        column6.addActionListener(this);
        panel.add(column6);
        butPos = new Label(SPACE);
        panel.add(butPos);

        column7 = new Button("v");
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
        first=false;
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
        gameStatus(g);
    }

    public void place(int n) 
   {
        if (gameover) return;
        first=true;
        int row;
        n--;
        for (row=0; row<MAXROW; row++)
            if (boardArray[row][n]>0) break;
        if (row>0) 
        {
            boardArray[--row][n]=activeColour;
            if (activeColour==RED)
                activeColour=BLUE;
            else
                activeColour=RED;
            repaint();
        }
    }

    public void displayWinner(Graphics g, int n) 
   {
        g.setColor(Color.CYAN);
        g.setFont(new Font("Courier", Font.BOLD, 100));
        if (n==RED)
            g.drawString("Red wins!", 250, 400);
        else
            g.drawString("Blue wins!", 250, 400);
        gameover=true;
    }

    public void gameStatus(Graphics g) 
   {
        for (int row=0; row<MAXROW; row++) 
       {
            for (int col=0; col<MAXCOL-3; col++) 
           {
                int slot = boardArray[row][col];
                if (slot>0
                 && slot == boardArray[row][col+1]
                 && slot == boardArray[row][col+2]
                 && slot == boardArray[row][col+3]) {
                    displayWinner(g, boardArray[row][col]);
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
                 && slot == boardArray[row+3][col])
                    displayWinner(g, boardArray[row][col]);
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
                 && slot == boardArray[row+3][col+3])
                    displayWinner(g, boardArray[row][col]);
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
                 && slot == boardArray[row-3][col+3])
                    displayWinner(g, boardArray[row][col]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) 
   {
        if (e.getSource() == column1)
            place(1);
        else if (e.getSource() == column2)
            place(2);
        else if (e.getSource() == column3)
            place(3);
        else if (e.getSource() == column4)
            place(4);
        else if (e.getSource() == column5)
            place(5);
        else if (e.getSource() == column6)
            place(6);
        else if (e.getSource() == column7)
            place(7);
        else if (e.getSource() == newGame) 
        {
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
        } 
        else if (e.getSource() == bluePlayer) 
        {
            if (!first) activeColour=BLUE;
        }
    }
 
}
