/**
 * class BadCellTypeExc
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
class BadCellTypeExc extends Exception{

	 BadCellTypeExc( String msg ) {
		super();
			errMsg += msg;
		} // constructor( )

	 String errMsg = "unknown (not X or O or BLANK)";

	public  String toString() {
		return "Bad type of cell:" + errMsg;
	}
} // Exception BadCellTypeExc
