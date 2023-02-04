package tetris2;

import javax.swing.JOptionPane;

public class Tetris2 {

    private static GameForm gf;
    private static LeaderboardForm lf;
    private static WelcomeForm wf;
    private static OptionsForm of;
    
    public static void start()
    {
        gf.setVisible( true );
        gf.startGame();
    }
    
    public static void showLeaderboard()
    {
        lf.setVisible( true );
    }
    
    public static void showMenu()
    {
        wf.setVisible( true );
    }
    
    public static void showOptions()
    {
        of.setVisible( true );
    }
    
    public static int getDifficulty()
    {
        return OptionsForm.difficulty;
    }
    
    public static void gameOver( int level, int score )
    {
        String name = JOptionPane.showInputDialog("Game over!\nEnter your name:");
        Database.insertPlayer( name, level, score );
        lf.updateTable();
        gf.setVisible( false );
        lf.setVisible( true );
    }
    
    public static void main( String[] args ) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gf = new GameForm();
                lf = new LeaderboardForm();
                wf = new WelcomeForm();
                of = new OptionsForm();
                
                wf.setVisible( true );
            }
        });
    }
    
}
