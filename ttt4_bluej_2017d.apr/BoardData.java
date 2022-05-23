
/**
 * BoardData keeps track of which moves (x,o,none) are in which squares (cells) of a board.
 * This is different than "Board" Class which actually draws pictures of a Board.
 * 
 * @author Mike Roam
 * @version 2017 Mar 29
 */
public class BoardData
{
    // instance variables
    final int boardWidth = 4;
    
    private  Player[][][] theData = new Player[ boardWidth ] [ boardWidth ] [ boardWidth ];
    
    /**
     * Constructor for objects of class BoardData
     */
    public BoardData()
    {
        // initialise instance variables
    }


    /** 
     * Sets the DATA  of the specified cell to blank, X, or O
     */
    void setCellXorO( int x, int y, int z, Player XorO ) throws FullSquareExc, BadCellTypeExc, BadLocExc {
        if ((x < 0) || (x >= boardWidth)) {
            throw new BadLocExc( XorO.toString( ) );
        }
        if ((y < 0) || (y >= boardWidth)) {
            throw new BadLocExc( XorO.toString( ) );
        }
        if ((z < 0) || (z >= boardWidth)) {
            throw new BadLocExc( XorO.toString( ) );
        }
        theData[x][y][z] = XorO;
    }
    
}
