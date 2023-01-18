package tetris2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread
{
    public volatile boolean play;
    private GameBoard gb;
    private GameForm gf;
    private int score = 0;
    private int level = 1;
    private int period = 500; 

    private void setPeriod( int divisor )
    {
        double period = (double) this.period;
        period -= ( 1 / (double)divisor ) * 20;
        this.period = (int) period;
    }
    
    public int incrementScore( int numCleared )
    {
        switch( numCleared )
        {
            case 4: return 150;
            case 3: return 100;
            case 2: return 70;
            case 1: return 50;
            default: return 10;
        }
    }
    
    public GameThread( GameBoard gb, GameForm gf )
    {
        this.gb = gb;
        this.gf = gf;
        this.play = true;
        
        this.gf.updateLevel( 1 );
        this.gf.updateScore( 0 );
    }
    
    @Override
    public void run()
    {
        // game loop starts here
        while( true )
        {         
            this.gb.spawnTetromino();
            
            while ( this.gb.moveTetrominoDown( this ) )
            {
                try
                {
                    Thread.sleep( period );
                }
                catch (InterruptedException ex)
                {
                    // once interrupt() was called in the GameForm
                    return;
                }
            }

            // if game over
            if( this.gb.isOutOfBounds() )
            {
                Tetris2.gameOver( this.level, this.score );
                break;
            }
            
            // if the game continues
            this.gb.drawDead();
            this.score += incrementScore( this.gb.clearLines() );
            int lvl = this.score / 100;
            this.gf.updateScore( this.score );
            if( lvl > this.level )
            {
                this.level = lvl;
                this.gf.updateLevel( lvl );
                this.setPeriod( lvl );
            }
        }
    }
}
