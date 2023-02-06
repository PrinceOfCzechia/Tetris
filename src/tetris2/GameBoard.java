package tetris2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Kral
 */
public class GameBoard extends JPanel
{
    // this (20 by 10 cells) is what the game board usually looks like
    // keeping the placeholders height = 2 * width seems essential though
    final private int numRows = 20;
    final private int numCols = 10;
    final private int cellSize;
    private Color[][] background;
    
    private Tetromino tetromino;
    
    public GameBoard( JPanel placeholder )
    // is constructed based on a JPanel
    {
        placeholder.setVisible( false );
        this.setBounds( placeholder.getBounds() );
        this.setBackground( placeholder.getBackground() );
        this.setBorder( placeholder.getBorder() );
        cellSize = placeholder.getHeight() / numRows;
        background = new Color[ numRows ][ numCols ];
    }
    
    public void initBackground()
    {
        this.background = new Color[ numRows ][ numCols ];
    }
    
    public void spawnTetromino()
    // there are seven different (shape, color) vectors
    // unambiguously assigned to numbers 0 - 6
    {
        Random r = new Random();
        tetromino = new Tetromino( r.nextInt( 7 ) );
        tetromino.spawn( numCols );
    }
    
    public boolean isOutOfBounds()
    {
        if( tetromino.getY() < 0 )
        {
            tetromino = null;
            return true;
        }
        return false;
    }
    
    public boolean moveTetrominoDown( GameThread gt )
    // once false is returned, we check if the tetromino is out of bounds
    // if yes, GameOver method is called
    // if not, the tetromino is killed, painted and new one is spawned
    {
        if( checkBottom() == false ) return false;
        while( !gt.play ){};
        tetromino.moveDown();
        repaint(); // calls paintComponent()
        return true;
    }
    
    // in the following methods, tetromino == null if the game is over
    // then, the methods do nothing, which is what we logically want
    public void moveTetrominoRight()
    {
        if( tetromino == null ) return;
        if( checkRight() ) tetromino.moveRight();
    }
    
    public void moveTetrominoLeft()
    {
        if( tetromino == null ) return;
        if( checkLeft() ) tetromino.moveLeft();
    }
    
    public void dropTetromino()
    {
        if( tetromino == null ) return;
        while( checkBottom() )
        {
            tetromino.moveDown();
        }
        repaint();
    }
    
    public void rotateTetromino()
    {
        if( tetromino == null ) return;
        checkRotation();
        while( tetromino.getLeftEdge() < 0 )
        {
            tetromino.moveRight();
        }
        while( tetromino.getRightEdge() > numCols )
        {
            tetromino.moveLeft();
        }
        while( tetromino.getBottomEdge() > numRows )
        {
            tetromino.moveUp();
        }
        repaint();
    }
    
    private boolean checkRotation()
    {
        int x = 0;
        int y = 0;
        tetromino.rotate();
        int height = tetromino.getHeight();
        int width = tetromino.getWidth();
        int[][] shape = tetromino.getShape();
        for( int col = 0; col < height; col++ )
        {
            for( int row = 0; row < width; row++ )
            {
                if( shape[ col ][ row ] == 1 ) x = col + tetromino.getX();
                else continue;
                if( x < 0 || x > numCols ) continue;
                if( shape[ col ][ row ] == 1 ) y = row + tetromino.getY();
                else continue;
                if ( y < 0 ) continue;
                if( background[ y ][ x ] != null )
                {
                    tetromino.rotateBack();
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean checkBottom()
    {
        // check board boundary
        if( tetromino.getBottomEdge() == numRows ) return false;
        
        // check dead tetrominos below
        int [][] shape = tetromino.getShape();
        int height = tetromino.getHeight();
        int width = tetromino.getWidth();
        for( int col = 0; col < width; col++ )
        {
            for( int row = height - 1; row >= 0; row-- )
            {
                if( shape[ row ][ col ] != 0 )
                {
                    // get cell below the lowest colored one
                    int x = col + tetromino.getX();
                    int y = row + tetromino.getY() + 1;
                    // if the tetromino is above the board, skip the loop
                    if( y < 0 ) break;
                    if( background[ y ][ x ] != null ) return false;
                    // if not false, go to the next column
                    break;
                }
            }
        }
        return true;
    }
    
    private boolean checkLeft()
    {
        // check board boundary
        if( tetromino.getLeftEdge() <= 0 ) return false;
        
        // check dead tetrominos to the left
        int [][] shape = tetromino.getShape();
        int height = tetromino.getHeight();
        int width = tetromino.getWidth();
        for( int row = 0; row < height; row++ )
        {
            for( int col = 0; col < width; col++ )
            {
                if( shape[ row ][ col ] != 0 )
                {
                    // get cell to the left of the leftmost colored one
                    int x = col + tetromino.getX() - 1;
                    int y = row + tetromino.getY();
                    if( y < 0 ) continue;
                    if( background[ y ][ x ] != null ) return false;
                    // if not false, go to the next row
                    break;
                }
            }
        }
        return true;
    }
    
    private boolean checkRight()
    {
        // check board boundary
        if( tetromino.getRightEdge() >= numCols ) return false;
        
        // check dead tetrominos to the right
        int [][] shape = tetromino.getShape();
        int height = tetromino.getHeight();
        int width = tetromino.getWidth();
        for( int row = 0; row < height; row++ )
        {
            for( int col = width - 1; col >= 0; col-- )
            {
                if( shape[ row ][ col ] != 0 )
                {
                    // get cell to the right of the rightmost colored one
                    int x = col + tetromino.getX() + 1;
                    int y = row + tetromino.getY();
                    if( y < 0 ) continue;
                    if( background[ y ][ x ] != null ) return false;
                    // if not false, go to the next row
                    break;
                }
            }
        }
        return true;
    }
    
    private void drawTetromino( Graphics g )
    // gets coordinates, colors what needs to be colored
    {
        int [][] shape = tetromino.getShape();
        int height = tetromino.getHeight();
        int width = tetromino.getWidth();
        Color color = tetromino.getColor();
        for( int i = 0; i < height; i++ )
        {
            for ( int j = 0; j < width; j++ )
            {
                if( shape[i][j] == 1 )
                {
                    int X = ( tetromino.getX() + j ) * cellSize;
                    int Y = ( tetromino.getY() + i ) * cellSize;
                    drawSquare( g, X, Y, color );
                }
            }
        }
    }
    
    private void drawBackground( Graphics g )
    // draws the basic empty grid
    {
        Color color;
        for( int i = 0; i < numRows; i++ )
        {
            for( int j = 0; j < numCols; j++ )
            {
                color = background[ i ][ j ];
                
                if( color != null )
                {
                    int X = j * cellSize;
                    int Y = i * cellSize;
                    drawSquare( g, X, Y, color);
                }
            }
        }
    }
    
    public void drawDead()
    // once tetromino cannot move down anymore
    // this method draws it on the background
    {
        int [][] shape = tetromino.getShape();
        int height = tetromino.getHeight();
        int width = tetromino.getWidth();
        int X = tetromino.getX();
        int Y = tetromino.getY();
        Color color = tetromino.getColor();
        
        for( int i = 0; i < height; i++ )
        {
            for( int j = 0; j < width; j++ )
            {
                if( shape[i][j] == 1 ) background[ i + Y ][ j + X ] = color;
            }
        }
    }
    
    private void drawSquare( Graphics g, int X, int Y, Color color )
    // draws a single cell of the grid
    {                
        g.setColor( color );
        g.fillRect( X, Y, cellSize, cellSize );
        g.setColor( Color.black );
        g.drawRect( X, Y, cellSize, cellSize );
    }
    
    public int clearLines()
    // checks which rows need to be cleared
    // calls clearOne() on them
    {
        int count = 0;
        boolean isFilled;
        for( int row = numRows - 1; row >= 0; row-- )
        {
            isFilled = true;
            for( int col = 0; col < numCols; col++ )
            {
                if( background[ row ][ col ] == null )
                {
                    isFilled = false;
                    break;
                }
            }
            if( isFilled )
            {
                count++;
                clearOne( row );
                shiftDown( row );
                // to keep indices nice and tight
                row++;
                clearOne( 0 );
            }
        }
        return count;
    }
    
    public void clearOne( int row )
    {
        for( int col = 0; col < numCols; col++ )
            {
                background[ row ][ col ] = null;
            }
        repaint();
    }
    
    public void shiftDown( int r )
    // moves all colored cells one row down once a line is cleared
    {
        for( int row = r; row > 0; row-- )
        {
            for( int col = 0; col < numCols; col++ )
            {
                background[ row ][ col ] = background[ row - 1 ][ col ];
            }
        }
    }
    
    @Override
    protected void paintComponent( Graphics g )
    // overriden blackbox, it is somehow necessary
    {
        super.paintComponent( g );
        
        g.setColor( Color.black );
        for( int i = 0; i < numCols; i++ )
        {
            for( int j = 0; j < numRows; j++ )
            {
            g.drawRect( i * cellSize, j * cellSize, cellSize, cellSize );
            }
        }
        
        drawTetromino( g );
        drawBackground( g );
    }
}
