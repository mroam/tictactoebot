import java.awt.*;
import javax.swing.*;  // provides JApplet

/** 
* This class creates, whenever asked, the next of the 76 possible 4-in-a-row group.
* First come all columns, then diagonals in planes, then trans-plane diagonals.
* NOTE: doesn't seem to find (at least) one of the transplane diag-diags...
* 
* I'm not seeing how to run the applet in debug, but the trouble is below, in 
* Group4CellsMaker.makeNextDiagDiagGroup( ) 
* 
3D Java was missing human score (3,0,0)  (2,1,1)  (1,2,2)  (0,3,3)

...X
....   ....
....   ..X.   ....
....   ....   ....   ....
       ....   .X..   ....
              ....   ....
                     X...
* 
* 
* This _could_ merely create a 76 element vector of  "Group4"s, which people
* then loop through the enumeration of, which would be perhaps more thread-safe...
* @author Mike Roam
* @version rev. 6 Dec 2004
*/
class Group4CellsMaker extends Object {

     Group4 currGroup = null; // set up in constructor or die  new Group4( );
     Board myBoard = null; // set up in constructor or die
     
     boolean hasMoreGroups = true;  // should be private so can't be changed

     int groupNum = /* double check */ 0;

     int x = 0;
     int y = 0;
     int z = 0;

    /* the following static final ints tell us which direction we're 
    focusing on ("loopingThrough") for current "take all of this kind of group of 4"*/
    public final  int X = 0;
    public final  int Y = 1;
    public final  int Z = 2;

    public final  int XY = 3;
    public final  int XZ = 4;
    public final  int YZ = 5;

    public final  int X_Y_Z_ = 6;  // means x,y, and z count 0..3
    public final  int X_Y3Z_ = 7;  // means y counts DOWN 3..0 while x and z count 0..3
    public final  int X3Y3Z_ = 8;  // x and y count down 3..0, while z counts 0..3
    public final  int X3Y_Z_ = 9;  // x counts down 3..0, y & z count 0..3
    //  ^ this is the diag that is missing

    public final  int ALL_DONE = 10;  // turns true when we make the last one
    public final  int NONE = 11;   // should turn true after we consume the last one

    /* initially Z, will be changed by makeNext.. as we go */
    int loopingThrough = Z;
    boolean hasMoreNonDiag = true;
    boolean hasMorePlaneDiag = true;
    boolean firstDiag = /* for alternating diagonals */ true;
    
    TicTacTApplet myApplet = null; // is set in constructor so we can send debugMessages somewhere

     
    /**
    * default constructor
    */
    public Group4CellsMaker( Board newMyBoard, TicTacTApplet myNewApplet ) {
        super( );
        myApplet = myNewApplet;
        myBoard = newMyBoard;
        currGroup =  new Group4( myBoard );
        /* chooses the first column */
        try  {
            currGroup.theData[0] = new CellLoc( 0, 0, 0 );
            currGroup.theData[1] = new CellLoc( 0, 0, 1 );
            currGroup.theData[2] = new CellLoc( 0, 0, 2 );
            currGroup.theData[3] = new CellLoc( 0, 0, 3 );
            //Debug.debugPrtLn( "after group4CellsMaker constructor, currGroup is " + this.toString( ) );
        } catch( BadLocExc bleh ) {
            System.out.println( "bad coordinates in Group4CellsMaker( ):" + bleh );
        }
        /* making sure to initialize all flags */ startOver( );
    } // default constructor

    
    /**
     * Works through non-diag groups, then plane diags, then diag-diags
     * Hmmm, might be stopping before the very last diag-diag
     */
    public  Group4 makeNextGroup( ) throws NoMoreGroupsExc {
        /* figure out if we're using non-diag... */
        if ( ! hasMoreGroups ) {
            throw new NoMoreGroupsExc( "." );
        } else {
            /* these routines adjust the currGroup, which got all 'new'ed in init( ). */
            if ( hasMoreNonDiag ) {
                /* return */
                makeNextNonDiagGroup( );
            } else {
                if ( hasMorePlaneDiag ) {
                    /* return */
                    makeNextPlaneDiagGroup( );
                } else {
                    /* return */
                    Debug.debug = true;
                    makeNextDiagDiagGroup( );
                    Debug.debugPrtLn( currGroup.toString( ) );
                }
            }
            groupNum++;
            return currGroup;
        }
    } // makeNextGroup( )


    public  void startOver( ) {
        loopingThrough = Z;
        hasMoreGroups = true;
        hasMoreNonDiag = true;
        hasMorePlaneDiag = true;
        firstDiag = true;
        groupNum = /* double checking */ 0;
    } // startOver( )


    public  String toString( ) {
        return Integer.toString( groupNum ) + ": " + currGroup.toString( );
    } // toString( )



    /**
     * Not doing diagonals, so can loop through x,y, or z; holding 2 of them steady 
     */
    private  void makeNextNonDiagGroup( ) {
        if ( loopingThrough == Z ) {
            /* the current group will be points (1,1,1) (1,1,2) (1,1,3) 
            (1,1,4) when x and y are 1 */
            for ( int newZ = 0; newZ < 4; ++newZ ) {
                currGroup.theData[newZ].x = x;
                currGroup.theData[newZ].y = y;
                currGroup.theData[newZ].z = newZ;
            }
            if ( y < 3 ) {
                y++;
            } else {
                y = 0;
                if ( x < 3 ) {
                    x++;
                } else {
                    x = 0;
                    loopingThrough = Y;
                    z = 0;
                }
            }
            //Debug.debugPrtLn( "after group4CellsMaker does a Z, currGroup is " + this.toString( ) );
        } else {
            if ( loopingThrough == Y ) {
                for ( int newY = 0; newY < 4; ++newY ) {
                    currGroup.theData[newY].x = x;
                    currGroup.theData[newY].y = newY;
                    currGroup.theData[newY].z = z;
                }
                if ( z < 3 ) {
                    z++;
                } else {
                    z = 0;
                    if ( x < 3 ) {
                        x++;
                    } else {
                        y = 0;
                        loopingThrough = X;
                        z = 0;
                    }
                }
                //Debug.debugPrtLn( "after group4CellsMaker does a Y, currGroup is " + this.toString( ) );
            } else {
                if ( loopingThrough == X ) {
                    for ( int newX = 0; newX < 4; ++newX ) {
                        currGroup.theData[newX].x = newX;
                        currGroup.theData[newX].y = y;
                        currGroup.theData[newX].z = z;
                    }
                    if ( z < 3 ) {
                        z++;
                    } else {
                        z = 0;
                        if ( y < 3 ) {
                            y++;
                        } else {
                            x = 0;
                            y = 0;
                            z = 0;
                            loopingThrough = YZ;
                            hasMoreNonDiag = false;
                            firstDiag = /* for alternating diagonals */ true;
                        }
                    }
                }
                //Debug.debugPrtLn( "after group4CellsMaker does a X, currGroup is " + this.toString( ) );
            }
        }
        //return currGroup;
    } // makeNextNonDiagGroup( )


    /**
     * 
     * Doing diagonals in planes, so working through planes
     */
    private  void makeNextPlaneDiagGroup( ) {
        
        if ( loopingThrough == YZ ) {
            if ( firstDiag ) {
                /* keep in yz plane, so only x varies from call to call:
                firstDiag=(x,0,0) (x,1,1) (x,2,2) (x,3,3) */
                for ( int newY = 0; newY < 4; ++newY ) {
                    currGroup.theData[newY].x = x;
                    currGroup.theData[newY].y = newY;
                    currGroup.theData[newY].z = /* yes, z and y get same 
                    value for firstDiag */
                    newY;
                }
            } else {
                /* keep in yz plane, so only x varies from call to call:
                !firstDiag is second diag=(x,3,0) (x,2,1) (x,1,2) (x,0,3) */
                for ( int newY = 0; newY < 4; ++newY ) {
                    currGroup.theData[newY].x = x;
                    currGroup.theData[newY].y = newY;
                    currGroup.theData[newY].z = 3 - newY;
                }
                if ( x < 3 ) {
                    x++;
                } else {
                    x = 0;
                    loopingThrough = XZ;
                    y = 0;
                }
            }
            //Debug.debugPrtLn( "after group4CellsMaker does a YZ diag, currGroup is " + this.toString( ) );
        } else {
            if ( loopingThrough == XZ ) {
                if ( firstDiag ) {
                    /* keep in xz plane, so only y varies from call to call:
                    firstDiag=(0,y,0) (1,y,1) (2,y,2) (3,y,3) */
                    for ( int newX = 0; newX < 4; ++newX ) {
                        currGroup.theData[newX].x = newX;
                        currGroup.theData[newX].y = y;
                        currGroup.theData[newX].z = newX;
                    }
                } else {
                    /* keep in xz plane, so only y varies from call to call:
                    firstDiag=(3,y,0) (2,y,1) (1,y,2) (0,y,3) */
                    for ( int newX = 0; newX < 4; ++newX ) {
                        currGroup.theData[newX].x = newX;
                        currGroup.theData[newX].y = y;
                        currGroup.theData[newX].z = 3 - newX;
                    }
                    if ( y < 3 ) {
                        y++;
                    }
                    else {
                        y = 0;
                        loopingThrough = XY;
                        z = 0;
                    }
                }
                //Debug.debugPrtLn( "after group4CellsMaker does a XZ diag, currGroup is " + this.toString( ) );
            } else {
                if ( firstDiag ) {
                    /* keep in XY plane, so only z varies from call to call:
                    firstDiag=(0,0,z) (1,1,z) (2,2,z) (3,3,z) */
                    for ( int newX = 0; newX < 4; ++newX ) {
                        currGroup.theData[newX].x = newX;
                        currGroup.theData[newX].y = newX;
                        currGroup.theData[newX].z = z;
                    }
                } else {
                    /* keep in yz plane, so only x varies from call to call:
                    !firstDiag is second diag=(x,3,0) (x,2,1) (x,1,2) 
                    (x,0,3) */
                    /* keep in XY plane, so only z varies from call to call:
                    firstDiag=(3,0,z) (2,1,z) (1,2,z) (0,3,z) */
                    for ( int newX = 0; newX < 4; ++newX ) {
                        currGroup.theData[newX].x = 3 - newX;
                        currGroup.theData[newX].y = newX;
                        currGroup.theData[newX].z = z;
                    }
                    if ( z < 3 ) {
                        z++;
                    } else {
                        z = 0;
                        loopingThrough = X_Y_Z_;
                        y = 0;
                        x = 0;
                        hasMorePlaneDiag = false;
                    }
                }
                //Debug.debugPrtLn( "after group4CellsMaker does a XY diag, currGroup is " + this.toString( ) );
                // 
            }
        }
        /* the following always happens, factors out of ALL the cases above */
        firstDiag =  ! firstDiag;
        //return currGroup;
    } // makeNextPlaneDiagGroup( )



   /** 
    * Doing the 3D diagonals, of which there only four:
    * A: (0,0, 0) (1,1, 1) (2,2, 2) (3,3, 3)   aka X_Y_Z_  (means x,y, and z count 0..3 )
    * B: (0,3, 0) (1,2, 1) (2,1, 2) (3,0, 3)   aka X_Y3Z_  (y counts down from 3, xz = 0..3)
    * C: (3,3, 0) (2,2, 1) (1,1, 2) (0,0, 3)   aka X3Y3Z_  (x & y count down from 3, z = 0..3)
    * D: (3,0, 0) (2,1, 1) (1,2, 2) (0,3, 3)   aka X3Y_Z_  (x counts down from 3, yz = 0..3)
    *     ^ This last one was missing
    * Note: we always loop through all four levels, counting with z=0..3
    * 
    * This builds a group and sets the "loopingThrough" flag to specify what the
    * next group will be.
    */
    private  void makeNextDiagDiagGroup( ) {
        
        if ( loopingThrough == X_Y_Z_ ) {
            // should be (0,0, 0) (1,1, 1) (2,2, 2) (3,3, 3)
            for ( int newZ = 0; newZ < 4; ++newZ ) {
                currGroup.theData[newZ].x = newZ;
                currGroup.theData[newZ].y = newZ;
                currGroup.theData[newZ].z = newZ;
            }
            loopingThrough = X_Y3Z_;  // was "X_Y3Z_";
        } else {
            if ( loopingThrough == X_Y3Z_  ) {
                // should be (0,3, 0) (1,2, 1) (2,1, 2) (3,0, 3)
                for ( int newZ = 0; newZ < 4; ++newZ ) {
                    currGroup.theData[newZ].x = newZ;
                    currGroup.theData[newZ].y = 3 - newZ;
                    currGroup.theData[newZ].z = newZ;
                }
                loopingThrough = X3Y3Z_;
            } else {
                if ( loopingThrough == X3Y3Z_ ) {
                    if (myApplet != null) {
                        myApplet.showDebugInfo("makeNexDiagDiagGroup( ) is working on");
                        myApplet.showDebugInfo("(3,3, 0) (2,2, 1) (1,1, 2) (0,0, 3)");
                    }
                    for ( int newZ = 0; newZ < 4; ++newZ ) {
                        currGroup.theData[newZ].x = 3 - newZ;
                        currGroup.theData[newZ].y = 3 - newZ;
                        currGroup.theData[newZ].z = newZ;
                    }
                    loopingThrough = X3Y_Z_; 
                } else {
                    if ( loopingThrough == X3Y_Z_) {
                        // The following appears!
                        // if (myApplet != null) {
                        //      myApplet.showDebugInfo("makeNexDiagDiagGroup( ) is working on");
                        //      myApplet.showDebugInfo("(3,0, 0) (2,1, 1) (1,2, 2) (0,3, 3)");
                        //      should be (3,0, 0) (2,1, 1) (1,2, 2) (0,3, 3)
                        //  }
                        for ( int newZ = 0; newZ < 4; ++newZ ) {
                            currGroup.theData[newZ].x = 3 - newZ;
                            currGroup.theData[newZ].y = newZ;
                            currGroup.theData[newZ].z = newZ;
                        }
                        if (myApplet != null) {
                            myApplet.showDebugInfo( currGroup.toString( ) ); // shows a coordinate(s)
                            // how to see what the specified cells hold??
                        }
                        loopingThrough = ALL_DONE;
                    } else {
                        // is there anything to do??
                        if ( loopingThrough == ALL_DONE) {
                            loopingThrough = NONE;
                            hasMoreGroups = false;
                            hasMoreNonDiag = false;
                            hasMorePlaneDiag = false;
                            if (myApplet != null) {
                                myApplet.showDebugInfo("makeNexDiagDiagGroup( ) is done");
                            }
                        }
                    }
                } // x3y_z_
            } // x3y3z_
        } // x_y3z_
        //Debug.debugPrtLn( "after group4CellsMaker does a diagdiag, currGroup is " + this.toString( ) );
        //return currGroup;
    } // makeNextDiagDiagGroup( )
    
} // class Group4CellsMaker
