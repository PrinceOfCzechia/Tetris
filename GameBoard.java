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
    // this is what the game board usually looks like
    // keeping the placeholders height = 2 * width is essential though
    final private int numRows = 20;
    final private int numCols = 10;
    private int cellSize;
    private Color[][] background;
    
    private Tetromino tetromino;
    
    public GameBoard( JPanel placeholder )
    {
        placeholder.setVisible( false );
        this.setBounds( placeholder.getBounds() );
        this.setBackground( placeholder.getBackground() );
        this.setBorder( placeholder.getBorder() );
        cellSize = placeholder.getHeight() / numRows;
        background = new Color[numRows][numCols];
    }
    
    public void spawnTetromino()
    {
        Random r = new Random();
        tetromino = new Tetromino( r.nextInt( 7 ) );
        tetromino.spawn( numCols );
    }
    
    public boolean isOutOfBounds()
    {
        if(tetromino.getY() < 0)
        {
            tetromino = null;
            return true;
        }
        return false;
    }
    
    public boolean moveTetrominoDown()
    {
        if( checkBottom() == false ) return false;
        tetromino.moveDown();
        repaint(); // calls paintComponent()
        return true;
    }
    
    public void moveTetrominoRight()
    {
        if( tetromino == null) return;
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
        tetromino.rotate();
        repaint();
    }
    
    private boolean checkBottom()
    {
        // check board boundary
        if( tetromino.getBottomEdge() == numRows) return false;
        
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
        for( int row = 0; row < height; row++)
        {
            for( int col = 0; col < width; col++ )
            {
                if( shape[ row ][ col ] != 0 )
                {
                    // get cell to the left of the leftmost colored one
                    int x = col + tetromino.getX() - 1;
                    int y = row + tetromino.getY();
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
        for( int row = 0; row < height; row++)
        {
            for( int col = width - 1; col >= 0; col-- )
            {
                if( shape[ row ][ col ] != 0 )
                {
                    // get cell to the right of the rightmost colored one
                    int x = col + tetromino.getX() + 1;
                    int y = row + tetromino.getY();
                    if( background[ y ][ x ] != null ) return false;
                    // if not false, go to the next row
                    break;
                }
            }
        }
        return true;
    }
    
    private void drawTetromino( Graphics g )
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
    {
        Color color;
        for( int i = 0; i < numRows; i++)
        {
            for( int j = 0; j < numCols; j++)
            {
                color = background[i][j];
                
                if(color != null)
                {
                    int X = j * cellSize;
                    int Y = i * cellSize;
                    drawSquare( g, X, Y, color);
                }
            }
        }
    }
    
    public void drawDead()
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
    {                
        g.setColor( color );
        g.fillRect( X, Y, cellSize, cellSize );
        g.setColor( Color.black );
        g.drawRect( X, Y, cellSize, cellSize );
    }
    
    public int clearLines()
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
    {
        for( int row = r; row > 0; row--)
        {
            for( int col = 0; col < numCols; col++ )
            {
                background[ row ][ col ] = background[ row - 1 ][ col ];
            }
        }
    }
    
    @Override
    protected void paintComponent( Graphics g )
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
