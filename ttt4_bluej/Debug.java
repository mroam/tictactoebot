/**
 * Static class to hold global "debug" boolean that controls on/off of debugPrt() and debugPrtLn()
 * 
 * @author Mike Roam 
 * @version rev. 6 Dec 2004
 */
class Debug extends Object{

    public static boolean debug = /* global for whether to print debug info */ false;
    
    /**
     * only System.out.prints if debug has been turned on
     * @param msg the error string
     */
    public static  void debugPrt( String msg ) {
        if ( debug ) {
            System.out.print( msg );
        }
    }

    public static  void debugPrtLn( String msg ) {
        if ( debug ) {
            System.out.print( msg + "\n" );
        }
    }

    /**
     * as of java 1.4 "assert" (the former name of this) is a key word
     */
    public static  void assertMe( boolean condition, String msg ) {
        /* will holler if condition is FALSE */
        if ( debug && ! condition ) {
            System.out.print( "Asserted condition was FALSE! " + msg + "\n" );
        }
    }
}  // class Debug
