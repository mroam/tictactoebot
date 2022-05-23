import java.awt.*;

/**
 * 
 * old note:??
 * "This should probably replace the CellLoc[] arrays of 4 that are everywhere
 *  (especially in and around group4CellsMaker and group4CellsRpt). Would have 
 *  to change their method calls and override the Board's setData() methods."
 *
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
class Group4 extends Object{

		/* be aware that this doesn't new the Cells */
		CellLoc[ ] theData =  new CellLoc [ 4 ];
		Board myBoard = null;

		 Group4( Board newMyBoard) {
			super();
			myBoard = newMyBoard;
		} // default constructor()
		
		
        /** Tells us if a given CellLoc is in this Group4's list of locations */
		 boolean hasCell( CellLoc theCellLoc ) {
			for ( int i = 0; i < 4; i++ ) {
				if ( theData[i].equals( theCellLoc ) ) {
					return true;
				}
			}
			return false;
		} // hasCell


		public  String toString() {
			StringBuffer myStr = new StringBuffer( "{" );
			for ( int i = 0; i < 4; ++i ) {
			    if (theData[i] == null) {
			        myStr.append( "_" );
			    } else {
				    myStr.append( theData[i].toString() );
				}
				if ( i < 3 ) {
					myStr.append( "," );
				} else {
					myStr.append( "}" );
				}
			}
			return myStr.toString();
		} // toString()



		/** 
		 * this could set the edge color of all the cells in the group to newColor.
		 * this could set all the cells in the group to a different color
		 */
		 void setColor( Color newColor ) {
			/*  */
			for ( int i = 0; i < 4; ++i ) {
				myBoard.getCellObj( this.theData[i] ).setColor( newColor );
			}
			myBoard.repaint();
		} // setColor


	} // class Group4