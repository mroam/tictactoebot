
/**
 * Write a description of class Pretty here.
 * 
 * @author Mike Roam
 * @version rev.6 Dec 2004
 */
public class Pretty
{
    /** 
     * trims off extra numbers (and zeros) after the decimal point:  
     *  with howManyDec == 2, then 3.14159 becomes "3.14"
     *  NOTE: only works for 0,1,2, or 3 places.
     *  Note 2004: isn't there a numberformat thing now in java 1.4+?
     */
    public static String trimDec( double theNum, int howManyDec ) {
        double newNum = theNum;
        switch (howManyDec)
        {
            case 0 :
                return Double.toString( Math.round( theNum ) );
            
            case 1 :
                newNum = 0.1 * Math.round( 10.0 * theNum );
                break;
            
            case 2 :
                newNum = 0.01 * Math.round( 100.0 * theNum );
                break;
            
            case 3 :
                newNum = (0.001 * Math.round( 1000.0 * theNum ));
                break;
        }
        if ( newNum == Math.round( newNum ) ) {
            return (Integer.toString( (int) newNum ));
        }
        else {
            return Double.toString( newNum );
        }
    } // trimDec( )

} // class Pretty