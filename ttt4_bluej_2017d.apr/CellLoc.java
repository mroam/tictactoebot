
/**
 * Keeps track of the 3 dimensional location within the board for a cell.
 * Throws exception if coordinates other than 0..3
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
class CellLoc extends Object{

     int x = 0;
     int y = 0;
     int z = 0;

    /**
     * constructor
     */
     CellLoc( int newX, int newY, int newZ ) throws BadLocExc {
        super();

        if ( (newX >= 0) && (newX < 4) ) {
            x = newX;
        } else {
            throw new BadLocExc( "newX = " + newX );
        }
        if ( (newY >= 0) && (newY < 4) ) {
            y = newY;
        } else {
            throw new BadLocExc( "newY = " + newY );
        }
        if ( (newZ >= 0) && (newZ < 4) ) {
            z = newZ;
        } else {
            throw new BadLocExc( "newZ = " + newZ );
        }
    } // constructor( int, int, int)
        

    CellLoc( CellLoc newPt3d ) throws BadLocExc {
        this( newPt3d.x, newPt3d.y, newPt3d.z );
    } // constructor ( CellLoc)
    

    CellLoc() {
        super();
    } // default constructor
    

    public  boolean equals( CellLoc otherCellLoc ) {
        return ((x == otherCellLoc.x) && (y == otherCellLoc.y) && (z == otherCellLoc.z));
    } // equals

    public  String toString() {
        return "(" + x + "," + y + "," + z + ")";
    } // toString()
} // class CellLoc