/**
 * Write a description of class BadLocExc here.
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
class BadLocExc extends Exception{

	BadLocExc( String msg ) {
		super();
		errMsg += msg;
	}

	String errMsg = "out of bounds (has to be 0,1,2 or 3)";

	public  String toString() {
		return "Bad type of cell:" + errMsg;
	}
}  // Exception BadLocExc