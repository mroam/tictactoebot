
/**
 * Exception when ??
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
class NoMoreGroupsExc extends Exception{
    String errMsg = "No More Groups of 4 left:";

	NoMoreGroupsExc( String msg ) {
		super();
		errMsg += msg;
    } // constructor( )

	public  String toString() {
		return "Bad type of cell:" + errMsg;
	}
} // Exception NoMoreGroupsExc