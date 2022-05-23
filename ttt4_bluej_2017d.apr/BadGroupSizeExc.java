/**
 * Complains about groups not having exactly 4 cells.
 * 
 * @author (Mike Roam) 
 * @version (2016 oct 22)
 */
public class BadGroupSizeExc extends Exception {

	public BadGroupSizeExc( String msg ) {
		super();
		errMsg += msg;
	} // constructor( String) 


	String errMsg = "There should be exactly 4 CellLocs in a group, not ";

	public  String toString() {
		return "Bad type of cell:" + errMsg;
	}
} // Exception BadGroupSizeExc