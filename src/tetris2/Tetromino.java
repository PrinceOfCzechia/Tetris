package tetris2;

import java.awt.Color;
import java.util.Random;

public class Tetromino
{
    private int [][] shape;
    private final Color color;
    private int [][][] rotations;
    private int currentRotation;
    
    private int Xoffset;
    private int Yoffset;

    
    public Tetromino( int num )
    {
        this.shape = returnShape( num );
        this.color = returnColor( num );
        
        initRotations();
    }
    
    private void initRotations()
    {
        this.rotations = new int [ 4 ][][];
        
        for( int i = 0; i < 4; i++ )
        {
            int rows = shape[ 0 ].length;
            int cols = shape.length;
            rotations[ i ] = new int[ rows ][ cols ];
            for( int row = 0; row < rows; row++ )
            {
                for( int col = 0; col < cols; col++ )
                {
                    rotations[ i ][ row ][ col ] = shape[ cols - col - 1 ][ row ];
                }
            }
            shape = rotations[ i ];
        }
    }
    
    public void spawn( int numCols )
    {
        Random r = new Random();
        // randomly spawn the tetromino from -2 to +2 to the right from the middle
        int randOffset = -2 + r.nextInt( 4 );
        this.currentRotation = r.nextInt( 3 );
        this.shape = this.rotations[ currentRotation ];
        this.Yoffset = - this.getHeight();
        this.Xoffset = ( numCols - this.getWidth() ) / 2 + randOffset;
    }
    
    public int[][] getShape(){ return this.shape; }
    public int getHeight(){ return this.shape.length; }
    public int getWidth(){ return this.shape[ 0 ].length; }
    public Color getColor(){ return this.color; }
    public int getX(){ return this.Xoffset; }
    public int getY(){ return this.Yoffset; }
    public int[][] getNextRotation() { return this.rotations[ currentRotation++ ]; }
    
    public void moveDown(){ this.Yoffset++; }
    public void moveLeft(){ this.Xoffset--; }
    public void moveRight(){ this.Xoffset++; }
    public void moveUp(){ this.Yoffset--; } // to ensure rotation compatibility
    public void rotate()
    {
        this.currentRotation++;
        this.currentRotation %= 4;
        this.shape = this.rotations[ currentRotation ];
    } 
    
    public void rotateBack()
    // used in the checkRotation() method
    {
        this.currentRotation--;
        this.currentRotation %= 4;
        this.shape = this.rotations[ currentRotation ];
    }
    
    public int getBottomEdge(){ return this.Yoffset + this.getHeight(); }
    public int getLeftEdge(){ return this.Xoffset; }
    public int getRightEdge(){ return this.Xoffset + this.getWidth(); }
    
    private int[][] returnShape( int num )
    {
        switch( num )
        {
            case 0: return new int [][] { {1,0}, {1,0}, {1,1} };
            case 1: return new int [][] { {0,1}, {0,1}, {1,1} };
            case 2: return new int [][] { {1,1}, {1,1} };
            case 3: return new int [][] { {1,0}, {1,1}, {0,1} };
            case 4: return new int [][] { {0,1}, {1,1}, {1,0} };
            case 5: return new int [][] { {1}, {1}, {1}, {1} };
            case 6: return new int [][] { {1,0}, {1,1}, {1,0} };
        }
        // the infamous pentomino, allowing for a glorious pentis move
        return new int [][] {{1},{1},{1},{1},{1}};
    }
    
    private Color returnColor( int num )
    {
        switch( num )
        {
            case 0: return new Color( 0, 0, 220 );
            case 1: return new Color( 250, 175, 23 );
            case 2: return new Color( 250, 250, 0 );
            case 3: return new Color( 0, 255, 51 );
            case 4: return new Color( 240, 51, 51 );
            case 5: return new Color( 51, 255, 255 );
            case 6: return new Color( 174, 23, 230 );
        }
        // part of what makes the pentomino so dangerous is that it is white
        return new Color( 255, 255, 255 );
        // fortunately, it never spawns
    }
}
