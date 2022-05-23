
/**
 * Enumeration class Player  describes the possible pieces on the game board.
 * We could have extra kinds of players someday!
 * 
 * @author Mike Roam
 * @version 2017 Mar 29
 */
public enum Player
{
    X, O, BLANK;
    
    public String toString( ) {
        if (this == X ) {
            return "X";
        } else if (this == O ) {
            return "O";
        } else if (this == BLANK ) {
            return "BLANK";
        } else {
            return "OMG What is this thing?";
        }
    }
}
