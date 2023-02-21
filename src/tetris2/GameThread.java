package tetris2;

public class GameThread extends Thread
{
    public volatile boolean play; // needs to be volatile for the pause button to work
    final private GameBoard gb;
    final private GameForm gf;
    private int score = 0;
    private int level = 1;
    private int period = this.initPeriod(); 
    
    private int initPeriod()
    // adjusts speed of the game loop according to chosen difficulty
    // 1 a.k.a. "medium" is the default value
    {
        if( Tetris2.getDifficulty() == 0 ) return 1000;
        if( Tetris2.getDifficulty() == 1 ) return 500;
        else return 300;
    }

    private void adjustPeriod( int divisor )
    {
        double loopPeriod = (double) this.period;
        loopPeriod -= ( 1 / (double) divisor ) * 20;
        this.period = (int) loopPeriod;
    }
    
    public int incrementScore( int numCleared )
    {
        switch( numCleared )
        {
            case 4: return 300;
            case 3: return 200;
            case 2: return 120;
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
            
            // moves the tetromino down as long as possible
            while ( this.gb.moveTetrominoDown( this ) )
            {
                try
                {
                    Thread.sleep( period );
                }
                catch ( InterruptedException ex )
                {
                    // once interrupt() was called in the GameForm
                    // a.k.a. the "main menu" button was pressed
                    return;
                }
            }
            // proceeds here once the tetromino cannot move anymore

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
                this.adjustPeriod( lvl );
            }
        }
    }
}
