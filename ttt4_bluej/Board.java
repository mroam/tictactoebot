import java.awt.*;
import java.util.*;
import java.awt.event.*;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Contains (keeps track of and displays) 4 boards of 4x4 grids
 * "chooseMove( )" , below, has the code for computer deciding how to respond to a move.
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004, annotated & reformated 2016 oct 22
 * Was Canvas, pre Swing, and replaced by JPanel, 2017 mar 22
 * See http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJPanelasacanvas.htm
 * 
 * Does this need a preferredSize/Width/Height method(s)?
 */
class Board extends JPanel /* was Canvas*/ {
    int xScore = 0;
    int oScore = 0;
    int howManyCellsFull = /* gets incremented in setCellXorO( ) */ 0;

    Boss parentBoss = null;
    Group4CellsMaker myGroupMaker = null; // gets built in constructor
    
    private  Cell[][][] theData = new Cell[ 4 ] [ 4 ] [ 4 ];

    int totNumCells = 64;

    /** 
    * Constructor receives new width, new height, and a link back to the controlling applet.
    * Builds cells (which are rectangles with coordinates) so that the "coordinates" of cells are
    * in four boards with x,y origins in upper left corner. Each board is diff Z, counting from left to right... 
    * 0,0,0   1,0,0   2,0,0   3,0,0
    * 0,1,0   1,1,0   2,1,0   3,1,0       next board has z=1:  
    * 0,2,0   1,2,0   2,2,0   3,2,0       0,0,1   1,0,1   2,0,1   3,0,1
    * 0,3,0   1,3,0   2,3,0   3,3,0       0,1,1   1,1,1   2,1,1   3,1,1       next board over has z=2
    *                                     0,2,1   1,2,1   2,2,1   3,2,1       0,0,2   1,0,2   2,0,2   3,0,2
    *                                                                         0,1,2   1,1,2   2,1,2   3,1,2  etc...
    */
    public Board( int boardWidth, int boardHeight, Boss newParent ) {
        super( );
        this.setSize( boardWidth, boardHeight );  // dimensions of the image, NOT saying how many squares in a row
        /* Next line is so we can tell the Boss to repaint score fields  when they change.
        (How else would parent applet know?? */
        parentBoss = newParent; // might be null!
        myGroupMaker = new Group4CellsMaker( /*Board*/this, parentBoss);
        
        int spaceBetweenBoards = 3;
        int cellGap = 2;
        Point topLeftForThisBoard = new Point( 5, 5 );
        for ( int y = 0; y < 4; ++y ) {
            for ( int x = 0; x < 4; ++x ) {
                for ( int z = 0; z < 4; ++z ) {
                    theData[x][y][z] = new Cell( topLeftForThisBoard.x + (x * (Cell.WIDTH + cellGap)),
                                                         topLeftForThisBoard.y + (z * (Cell.HEIGHT + cellGap)) );
                }  // for z
            } // for x
            topLeftForThisBoard.y += spaceBetweenBoards + (int) (1.5 * (Cell.HEIGHT + cellGap));
            topLeftForThisBoard.x += spaceBetweenBoards + (4 * (Cell.WIDTH + cellGap));
        } // for y
        
        
        
        
        
        this.addMouseListener( new MouseAdapter( ) 
        {
        
            public void mousePressed( MouseEvent e ) {
                if ( howManyCellsFull >= totNumCells ) {
                    Debug.debugPrtLn( "Board is full" );
                }
                else {
                    int miker = /* Determine which Cell was clicked by checking in the rect of  every cell.
                            A faster way to do  this click location might be measuring distance of click 
                            from corner of board with knowledge of cell size... 
                            Either method would work if the board becomes adjustable */
                            0;
                    for ( int yLoc = 0; yLoc < 4; ++yLoc ) {
                        for ( int xLoc = 0; xLoc < 4; ++xLoc ) {
                            for ( int zLoc = 0; zLoc < 4; ++zLoc ) {
                                if ( theData[xLoc][yLoc][zLoc].contains( e.getPoint( ) ) ) {
                                    try  {
                                        registerOpponentMove( new CellLoc( xLoc, yLoc, zLoc ) );
                                    } catch( BadLocExc blem ) {
                                        System.out.println( "Opponent move not recognized:" + blem );
                                    } 
                                    return ;
                                } // if contins
                            } // for zLod
                        } // for xLoc
                    } // for Yloc
                } // not full
            } // mousePressed
        } ); // end of addMouseListener
    } // constructor
   
    
    

    /**
     * This decides how to respond to opponent's move
     */
     void registerOpponentMove( CellLoc theMoveLoc ) {
        Debug.debugPrt( "in registerOpponentMove( ), human trying to move to " + theMoveLoc );
        CellLoc newComputerMove = null;
        if ( howManyCellsFull < totNumCells ) {
            try  {
                setCellXorO( theMoveLoc, /* note: human is 'X' */ Cell.X );
                Vector newHumanWins = newWins( theMoveLoc, Cell.X );
                if ( ! newHumanWins.isEmpty( ) ) {
                    Debug.debugPrtLn( "New Human Win detected!" );
                    recountScores( );
                    parentBoss.scoresHaveChanged( );
                }
                if ( howManyCellsFull < totNumCells ) {
                    newComputerMove = chooseMoveSmart( );   // this used to call chooseMove( )
                    setCellXorO( newComputerMove, /* note: computer is 'o' */ Cell.O );
                    Vector newComputerWins = newWins( newComputerMove,
                                                                   Cell.O );
                    if ( ! newComputerWins.isEmpty( ) ) {
                        Debug.debugPrtLn( "New Computer Win detected!" );
                        recountScores( );
                        parentBoss.scoresHaveChanged( );
                    }
                    warnOpponent( );
                    /* warnOpponent blanks all cells, so now let's light up new 
                    green victories... **/
                    for ( Enumeration e = newComputerWins.elements( ); e.hasMoreElements( );  ) {
                        ((Group4) (e.nextElement( ))).setColor( Color.green );
                    }
                }
                else {
                    /* but not drawing new computer wins since board was full so 
                    computer didn't move */
                    warnOpponent( );
                }
                for ( Enumeration e = newHumanWins.elements( ); e.hasMoreElements( );  ) {
                    ((Group4) (e.nextElement( ))).setColor( Color.green );
                }
            } catch( Exception fse ) {  // might be FullSquareExc, BadCellTypeExc
                System.out.println( "That square is taken-" + fse );
            }
        }
        else {
            Debug.debugPrt( "Board is full, can't register new move " + theMoveLoc );
        }
    } //registerOpponentMove( )


    /**
     * Detect a human possibilites (three-in-a-group w/ blank; 2-in-a-group 
        w/ blanks) and color code them (red, yellow). Also detect the computer possibilities and color code them 
        (lightgrey, darkgrey?)
        
        Possible Optimization: run it once finding and saving all the 3s and 2s. Then 
        color all the twos, then finally color all the 3s so that
        their information washes over. Alternatives are to run through all 
        the groups twice with coloring, once for 2s and then for 3s.
     */
     void warnOpponent( ) {
        
        Vector groupOf2Xs = new Vector( );
        Vector groupOf3Xs = new Vector( );
        Vector groupOf2Os = new Vector( );
        Vector groupOf3Os = new Vector( );
        /* blank out all the previous color warnings and green win signals */
        setColor( Color.white );
        myGroupMaker.startOver( );
        try  {
            for ( Group4 currGroup = myGroupMaker.makeNextGroup( ); myGroupMaker.hasMoreGroups; currGroup = myGroupMaker.makeNextGroup( ) ) {
                Group4CellsRpt thisRpt = new Group4CellsRpt( currGroup, this );
                if ( (thisRpt.howManyXOBs( Cell.X ) == 3) && (thisRpt.howManyXOBs( Cell.BLANK ) == 1) ) {
                    groupOf3Xs.addElement( thisRpt.theCellsLocs );
                }
                else {
                    if ( (thisRpt.howManyXOBs( Cell.X ) == 2) && (thisRpt.howManyXOBs( Cell.BLANK ) == 2) ) {
                        groupOf2Xs.addElement( thisRpt.theCellsLocs );
                    }
                    else {
                        if ( (thisRpt.howManyXOBs( Cell.O ) == 3) && (thisRpt.howManyXOBs( Cell.BLANK ) == 1) ) {
                            groupOf3Os.addElement( thisRpt.theCellsLocs );
                        }
                        else {
                            if ( (thisRpt.howManyXOBs( Cell.O ) == 2) && (thisRpt.howManyXOBs( Cell.BLANK ) == 2) ) {
                                groupOf2Os.addElement( thisRpt.theCellsLocs );
                            }
                        }
                    }
                }
            }
        } catch( Exception AnyOldExc ) {
            /* possible exceptions:  
            myGroupMaker.makeNextGroup-->noMoreGroupsExc;
                                    howManyXOBs-->badCellTypeExc;  new 
            Group4CellsRpt-->badGroupSizeExc */
            System.out.println( "in didWin:" + AnyOldExc );
        }
        int miker = 0;
        /* the ordering of this coloring matters! (unless we start to combine colors ) */
        for ( Enumeration e = groupOf2Os.elements( ); e.hasMoreElements( );  ) {
            ((Group4) (e.nextElement( ))).setColor( Color.lightGray );
        }
        for ( Enumeration e = groupOf2Xs.elements( ); e.hasMoreElements( );  ) {
            ((Group4) (e.nextElement( ))).setColor( Color.yellow );
        }
        for ( Enumeration e = groupOf3Os.elements( ); e.hasMoreElements( );  ) {
            ((Group4) (e.nextElement( ))).setColor( Color.darkGray );
        }
        for ( Enumeration e = groupOf3Xs.elements( ); e.hasMoreElements( );  ) {
            ((Group4) (e.nextElement( ))).setColor( Color.red );
        }
        Debug.debugPrtLn( "warnOpponent is done" );
    } // warnOpponent( )

    /** 
     * Walk through the whole grid counting winning groups of four.
     * Somebody is not using the last diagdiag group -- maybe this for ( ) is going obob?
     */
     void recountScores( ) {
        int newXScore = 0;
        int newOScore = 0;
        myGroupMaker.startOver( );
        try  {
            for ( Group4 currGroup = myGroupMaker.makeNextGroup( ); 
                       myGroupMaker.hasMoreGroups;  /* is this bailing out during the last group?? */
                             /* should this be (while) currGroup <> nill ?? */
                       currGroup = myGroupMaker.makeNextGroup( ) ) {
                Group4CellsRpt thisRpt = new Group4CellsRpt( currGroup, this );
                if ( thisRpt.howManyXOBs( Cell.X ) == 4 ) {
                    newXScore++;
                } else {
                    if ( thisRpt.howManyXOBs( Cell.O ) == 4 ) {
                        newOScore++;
                    }
                }
            }
        } catch( Exception anyOldExc ) {
            /* possible exceptions:  
            myGroupMaker.makeNextGroup-->noMoreGroupsExc;
                                    howManyXOBs-->badCellTypeExc;  new 
            Group4CellsRpt-->badGroupSizeExc */
            System.out.println( "in didWin:" + anyOldExc );
        }
        if ( (oScore != newOScore) || (xScore != newXScore) ) {
            oScore = newOScore;
            xScore = newXScore;
            parentBoss.scoresHaveChanged( );
        }
        Debug.debugPrtLn( "recountScores( ) is done" );
    } // recountScores( )



    CellLoc chooseRandomMove( ) {
        /* for now, just randomly choose a blank square */
        int xR = (int) (Math.random( ) * 4.0);
        int yR = (int) (Math.random( ) * 4.0);
        int zR = (int) (Math.random( ) * 4.0);
        while ( getCellXorO( xR, yR, zR ) != Cell.BLANK ) {
            xR = (int) (Math.random( ) * 4.0);
            yR = (int) (Math.random( ) * 4.0);
            zR = (int) (Math.random( ) * 4.0);
        }
        CellLoc theNewMove = null;
        try  {
            theNewMove = new CellLoc( xR, yR, zR );
        } catch( BadLocExc bler ) {
            System.out.println( "Computer is choosing bogus random move:" + bler );
        }
        return theNewMove;
    } // chooseRandomMove( )


    /**
    * Going to call some methods which return null (no move found) or the loc of the move.
    * Finally, chooseRandomMove( ) is ALMOST guaranteed to return a move unless the board is full. 
    * What to do if the board is full?  hmmm, this just takes the first available win which might 
    * not be the BEST: some other win might win in two directions at once! 
    * Should rank and compare the wins (or all possible moves).
    * 
    * This is almost identical to chooseMoveSmart, except this does not run (AI self-play) tournaments to decide moves.
    * 
    * This used to be called by registerOpponentMove( ) but now is usually (only?) called by chooseMoveSmart( )
    */
     CellLoc chooseMove( int whoToLookForXorO ) {
        CellLoc theNewMove = null;
        if ( (whoToLookForXorO != Cell.X) && (whoToLookForXorO != Cell.O) ) {
            System.out.println("chooseMove( ) isn't happy to look for move for undefined nonX nonO '" + whoToLookForXorO + "'??!!");
            return null;
        }
        int ourOpponent = opponent( whoToLookForXorO );
        /* first check where we ("whoToLookForXorO") can win (will be null if can't win right now) */
        if ( (theNewMove = canWin( whoToLookForXorO )) == null ) {
            /* Failing that, check where opponent can win (will be null if human 
            can't win right now) */
            if ( (theNewMove = canWin( ourOpponent )) == null ) {
                /* Try to turn some 2's into 3s... */
                
                if ( (theNewMove = canImprove( whoToLookForXorO )) == null ) {
                    /* try to stop the human from turning 2's into 3's.. */
                    if ( (theNewMove = canImprove( ourOpponent )) == null ) {
                        theNewMove = chooseRandomMove( );
                    }
                }
            }
        }
        return theNewMove;
    } // chooseMove( )
    
    
    /**
    * Going to call some methods which return null (no move found) or the loc of the move.
    * Finally, chooseRandomMove( ) is ALMOST guaranteed to return a move unless the board is full. 
    * What to do if the board is full?  hmmm, this just takes the first available win which might 
    * not be the BEST: some other win might win in two directions at once! 
    * Should rank and compare the wins (or all possible moves).
    * 
    * This is almost identical to chooseMove, except this also runs AI self-play tournaments to decide moves.
    * PS: tournaments do NOT call chooseMoveSmart but instead merely use chooseMove( )
    * maybe we ought to have semi-smart (or time-restricted) moves for tournaments.
    * Perhaps the human moves would be random in our tournament games??
    * 
    * This is called by registerOpponentMove( )
    */
     CellLoc chooseMoveSmart( ) {
        CellLoc theNewMove = null;
        /* first check where computer can win (will be null if can't win right now) */
        if ( (theNewMove = canWin( /* computer is o */ Cell.O )) == null ) {
            /* Failing that, check where human can win (will be null if human 
            can't win right now) */
            if ( (theNewMove = canWin( /* human is x */ Cell.X )) == null ) {
                /* Try to turn some 2's into 3s... */
                
                if ( (theNewMove = useTournamentToDecide(  /* computer is o */ Cell.O, /* timeLimitInSeconds */ 60 )) == null) {
                
                    if ( (theNewMove = canImprove( Cell.O )) == null ) {
                        /* try to stop the human from turning 2's into 3's.. */
                        if ( (theNewMove = canImprove( Cell.X )) == null ) {
                            theNewMove = chooseRandomMove( );
                        }
                    }
                } // tournament
            }
        }
        return theNewMove;
    } // chooseMoveSmart( )
    

    
    /**
     * For all of the possible moves, try playing a full game starting with that move.
     * So there could be at most 64 games if we play one for each possible move...
     */
       CellLoc useTournamentToDecide( int XorO, int timeLimitInSeconds ) {
         // copy the current board, store it, and play on the copy
         
         // we need some kind of data collection (arrayList? dictionary?) to keep score of which moves lead to final win most often
           
         // this will call chooseMove( Cell.X ) and then call chooseMove( Cell.O );
           // return the move that won us the most games...
           Debug.debugPrtLn( "useTournamentToDecide( )  is done" );
           return null;
        }
    
    
    
    /**
     * Detect a three-in-a-row with blank and return the blank's loc (or null).
     */
     CellLoc canWin( int XorO ) {
        myGroupMaker.startOver( );
        try  {
            for ( Group4 currGroup = myGroupMaker.makeNextGroup( ); myGroupMaker.hasMoreGroups; currGroup = myGroupMaker.makeNextGroup( ) ) {
                Group4CellsRpt thisRpt = new Group4CellsRpt( currGroup, this );
                if ( (thisRpt.howManyXOBs( XorO ) == 3) && (thisRpt.howManyXOBs( Cell.BLANK ) == 1) ) {
                    /* have to figure out which square is the blank, and return it! */
                    CellLoc[] whereTheBlanksAre = thisRpt.locsOfXOBs( Cell.BLANK );
                    return (whereTheBlanksAre[0]);
                }
            }
        } catch( Exception anyOldExc ) {
            /* possible exceptions:  
            myGroupMaker.makeNextGroup-->noMoreGroupsExc;
                                howManyXOBs-->badCellTypeExc;  new 
            Group4CellsRpt-->badGroupSizeExc */
            System.out.println( "in canWin( ) " + anyOldExc );
        }
        Debug.debugPrtLn( "canWin( )  is done (false)" );
        return null;
    } // canWin( )


    /**
    * Detect a computer two-in-a-row (with rest blank) and return one of 
    * the blanks' loc (or null)
    */
     CellLoc canImprove( int XorO ) {
        
        myGroupMaker.startOver( );
        try  {
            for ( Group4 currGroup = myGroupMaker.makeNextGroup( ); myGroupMaker.hasMoreGroups; currGroup = myGroupMaker.makeNextGroup( ) ) {
                Group4CellsRpt thisRpt = new Group4CellsRpt( currGroup, this );
                if ( (thisRpt.howManyXOBs( XorO ) == 2) && (thisRpt.howManyXOBs( Cell.BLANK ) == 2) ) {
                    /* have to figure out which square is the blank, and return 
                    it! */
                    CellLoc[] whereTheBlanksAre = thisRpt.locsOfXOBs( Cell.BLANK );
                    return (whereTheBlanksAre[0]);
                }
            }
        } catch( Exception anyOldExc ) {
            /* possible exceptions:  
            myGroupMaker.makeNextGroup-->noMoreGroupsExc;
                                howManyXOBs-->badCellTypeExc;  new 
            Group4CellsRpt-->badGroupSizeExc */
            System.out.println( "in canImprove( ) " + anyOldExc );
        }
        Debug.debugPrtLn( "canImprove( )  is done (false)" );
        return null;
    } // canImprove( )


    /** 
    * This method is expressly for finding groups of 4 that won with the specified moves.
    * (This lets us light up the recent wins.)
    * Returns a Vector full of "Group4".
    */
     Vector<Group4> newWins( CellLoc newMove, int XorO ) {
        
        Vector<Group4> newWinVector = new Vector<Group4>( );
        if ( newMove == null ) {
            return newWinVector;
        }
        myGroupMaker.startOver( );
        try  {
            for ( Group4 currGroup = myGroupMaker.makeNextGroup( ); myGroupMaker.hasMoreGroups; currGroup = myGroupMaker.makeNextGroup( ) ) {
                if ( currGroup.hasCell( newMove ) ) {
                    Group4CellsRpt thisRpt = new Group4CellsRpt( currGroup, this );
                    if ( thisRpt.howManyXOBs( XorO ) == 4 ) {
                        /* Vector full of Group4  */
                        newWinVector.addElement( thisRpt.theCellsLocs );
                    }
                }
            }
        } catch( Exception anyOldExc ) {
            /* possible exceptions: myGroupMaker.makeNextGroup-->noMoreGroupsExc;
                        howManyXOBs-->badCellTypeExc; new 
            Group4CellsRpt-->badGroupSizeExc */
            System.out.println( "in didWin:" + anyOldExc );
        }
        return newWinVector;
    } // newWins( )

    

    /**
     * Draws a picture of the board, by telling cells to paint themselves.
     * Was "paint( )" until Board upgraded to JPanel
     */
    public  void paintComponent( Graphics g ) {
        int width = getWidth( );
        int height = getHeight( );
        
        for ( int y = 0; y < 4; ++y ) {
            for ( int x = 0; x < 4; ++x ) {
                for ( int z = 0; z < 4; ++z ) {
                    theData[x][y][z].paint( g );
                }
            }
        }
    } // paint( )


    public  void setColor( Color newColor ) {
        for ( int y = 0; y < 4; ++y ) {
            for ( int x = 0; x < 4; ++x ) {
                for ( int z = 0; z < 4; ++z ) {
                    theData[x][y][z].setColor( newColor );
                }
            }
        }
    } // setColor( )


    /**
     * utility function
     * for "X" this returns "O" and vice-versa
     */
    int opponent( int XorO ) {
        if (XorO == Cell.X) {
            return Cell.O;
        } else if (XorO == Cell.O) {
            return Cell.X;
        } else {
            System.out.println("What?? Board is trying to figure the opponent of undefined '" + XorO + "'!!??");
            return Cell.BLANK;
        }
    } // opponent

    /** 
     * Returns the pointer to the specified cell.
     */
     Cell getCellObj( int x, int y, int z ) {
        return theData[x][y][z];
    }

    /** 
     * Returns the pointer to the specified cell.
     */
    Cell getCellObj( CellLoc theLoc ) {
        return getCellObj( theLoc.x, theLoc.y, theLoc.z );
    }

    /**
     * Returns the DATA (blank,X, or O) of the specified cell.
     */
    int getCellXorO( int x, int y, int z ) {
        
        return theData[x][y][z].getData( );
    }

    /**
     * Returns the DATA (blank,X, or O) of the specified cell.
     */
    int getCellXorO( CellLoc theLoc ) {
        return getCellXorO( theLoc.x, theLoc.y, theLoc.z );
    }

    /** 
     * Sets the DATA  of the specified cell to blank, X, or O
     */
    void setCellXorO( int x, int y, int z, int XorO ) throws FullSquareExc, BadCellTypeExc {
    
        if ( theData[x][y][z].getData( ) != Cell.BLANK ) {
            throw new FullSquareExc( "(" + x + "," + y + "," + z + ")" );
        }
        else {
            try  {
                theData[x][y][z].setData( XorO );
                howManyCellsFull++;
                repaint( );
            } catch( Exception fse ) {
                throw fse;
            }
        }
    } // setCellXorO( int, int, int, int)


    /** 
     *  Sets the DATA  of the specified cell to blank, X, or O 
     */
    void setCellXorO( CellLoc theLoc, int XorO ) throws FullSquareExc, BadCellTypeExc {
        setCellXorO( theLoc.x, theLoc.y, theLoc.z, XorO );
    } // setCell( CellLoc, int)


    
    /** 
     * Blank out all the squares and the score, (including color)
     */
     void newGame( ) {
        for ( int yLoop = 0; yLoop < 4; ++yLoop ) {
            for ( int xLoop = 0; xLoop < 4; ++xLoop ) {
                for ( int zLoop = 0; zLoop < 4; ++zLoop ) {
                    theData[xLoop][yLoop][zLoop].clearData( );
                    theData[xLoop][yLoop][zLoop].setColor( Color.white );
                }
            }
        }
        oScore = 0;
        xScore = 0;
        howManyCellsFull = 0;
        repaint( );
    } // newGame( )
    
    
    /**
     * main( ) for board so we can instantiate one to mess around with.
     * Not normally used. (Boss will create a JFrame to hold us, jah?
     * From example of JPanel as canvas 
     * http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJPanelasacanvas.htm
     */
    public static void main(String args[]) {
        JFrame frame = new JFrame("Board...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Board(/*boardWidth*/400, /*boardHeight*/250, /*newParent*/null ));
        frame.setSize(400, 250);
        frame.setVisible(true);
    } // main( )
} // class Board
