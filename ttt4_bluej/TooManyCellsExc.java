
/**
 * Exception when some group of 4 actually tries to have too many members.
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
class TooManyCellsExc extends Exception{

	 TooManyCellsExc( String msg ) {
		super();
			errMsg += msg;
		} // constructor()

	 String errMsg = "out of bounds (can only remember 4 cells)";

	public  String toString() {
		return "Bad type of cell:" + errMsg;
	}
}  // Exception TooManyCellsExc