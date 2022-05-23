import java.awt.*;

/**
 * Cells are the little boxes on the screen. A board is made up of multiple cells.
 * 
 * These are mostly for drawing, and have a single data to hold (X,O,BLANK).
 * [ ]This class should get re-named CellView!
 * 
 * @author (Mike Roam) 
 * @version (Started approx 2000, revised 2002 with Sandy Gifford and Aaron Pellman-Isaacs, revised 2004, 2013, 2016)
 */
class Cell extends Rectangle {
    // "Rectangle" have x,y of left upper corner, and width,height dimensions

	 Color myColor = Color.red;
	 Color myEdgeColor = Color.red;
     static final  int WIDTH = 20; // cell size on the screen in pixels
     static final  int HEIGHT = 20; // cell size on the screen in pixels
         // different possible occupants of the cell as "enum"
     static final  int BLANK = 0;
     static final  int X = 1;
     static final  int O = 2;
         // the initial occupant of this cell...
	 int data = BLANK;
	 

    /**
    * constructor
    */
	 Cell( int newLeft, int newTop ) {
		super( newLeft, newTop, WIDTH, HEIGHT );
	} // Cell constructor( int, int )


	 void setColor( Color newColor ) {
		if ( newColor != null ) {
			myColor = newColor;
		}
	} // setColor( )


	/**
	 * Merely draws a cell as a text char ('O' or 'X' or '_' or '?') at position specified in our Rectangle's .x and .y
	 * 
	 */
	public  void paint( Graphics g ) {
		int left = this.x;
		int top = this.y;
		int rWidth = this.width;
		int rHeight = this.height;
		g.setColor( myColor );
		if ( data == BLANK ) {
			g.drawRect( left, top, rWidth, rHeight );
		}
		else {
			g.fillRect( left, top, rWidth, rHeight );
		}
		g.setColor( myEdgeColor );
		g.drawRect( left, top, rWidth, rHeight );
		if ( (myColor == Color.black) || (myColor == Color.darkGray) ) {
			g.setColor( Color.white );
		}
		else {
			g.setColor( Color.black );
		}
		g.drawString( this.toString( ), left + 3, top + height - 3 );
		//System.out.println( "Drawing rectangle " + left + "," + top + "," + rWidth + "," + rHeight );
	} // paint()


	 void clearData( ) {
		data = BLANK;
	} // clearData()

     /**
      * Only lets empty cells get new values.
      * Use "clearData( )" instead of setData( ) to blank out a cell!
      * @param newData Either an X or O (int) constant.
      */
	 void setData( int newData ) throws FullSquareExc, BadCellTypeExc {
        if ((newData != X) && (newData != O) && (newData != BLANK)) {
            throw new BadCellTypeExc( "unknown value '" + Integer.toString(newData) + "'");
        } else {
            // okay, legit possibility...
            if ( data != BLANK ) {
                // uh-oh, cell is already filled with X or O
                throw new FullSquareExc( " " );
            } else {
                data = newData;
            }
        }
	} // setData()


	 int getData() {
		return data;
	} // getData()


	public  String toString() {
		if ( data == X ) {
			return "X";
		} else if ( data == O ) {
			return "O";
		} else if ( data == BLANK ) {
			return "_";
		} else {
			return "?";
		}
	} // toString()


    /**
     * static utility function that lets us print an int as if it were a cell occupant.
     * @param cellValue theoretically the value of some cell ("?" will be returned if out of range).
     */
	public static String toString( int cellValue ) {
		if ( cellValue == X ) {
			return "X";
		} else if ( cellValue == O ) {
			return "O";
		} else if ( cellValue == BLANK ) {
			return "_";
		} else {
			return "?";
	    }
	} // toString()

} // class Cell