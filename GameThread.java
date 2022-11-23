package tetris2;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GameThread extends Thread
{
    private GameBoard gb;
    private GameForm gf;
    private int score = 0;
    private int level = 1;
    private int period = 1000;

    private void setPeriod( int divisor )
    {
        double period = (double) this.period;
        period -= ( 1 / (double)divisor ) * 20;
        this.period = (int) period;
    }
    
    public GameThread( GameBoard gb, GameForm gf )
    {
        this.gb = gb;
        this.gf = gf;
    }
    
    @Override
    public void run()
    {
        // game loop starts here
        while( true )
        {
            this.gb.spawnTetromino();
            while ( this.gb.moveTetrominoDown() )
            {
                try
                {
                    Thread.sleep( period );
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger( GameThread.class.getName() ).log( Level.SEVERE, null, ex );
                }
            }
            // if game over
            if( this.gb.isOutOfBounds() )
            {
                System.out.println("GAME OVER!");
                break;
            }
            // if the game continues
            this.gb.drawDead();
            this.score += 50*this.gb.clearLines();
            int lvl = this.score / 100;
            this.gf.updateScore( this.score );
            if( lvl > this.level )
            {
                this.gf.updateLevel( lvl );
                this.setPeriod( lvl );
                System.out.println( this.period );
            }
        }
    }   
}
