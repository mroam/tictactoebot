
/**
 * Yells when trying to change an already-occupied square?
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
class FullSquareExc extends Exception{
	String errMsg = "The Square is Full";

	FullSquareExc( String msg ) {
		super();
        errMsg += msg;
    }

	public  String toString() {
		return "Bad setting of cell:" + errMsg;
	}
} // Exception FullSquareExc