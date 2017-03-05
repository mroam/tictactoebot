/** 
 * After we look at a group of four cells, the information
 * discovered is summarized here for querying: didAnyOne Win?
 * Are there three 0's and a Blank? Three X's and a Blank? Etc.
 * 
 * @author Mike Roam
 * @version rev. 6 Dec 2004
 */
 class Group4CellsRpt extends Object {
        
    public  int howManyXs = 0;
    public  int howManyOs = 0;
    public  int howManyBlanks = 0;
    
    int[] theCellsValues = new int [ 4 ];
    Group4 theCellsLocs = null; // gets setup in constructor or die
    int howManyLocs = /* count as we add Locs */ 0;
    
    Board myBoard = null; // gets setup in constructor or die
    
    /** 
    * constructor
    */
    Group4CellsRpt( Group4 newLocs, Board myNewBoard ) {
        super();
        myBoard = myNewBoard;
        theCellsLocs = new Group4( myBoard );
        int miker = /* copy NEW cell locs, just for us */ 0;
        for ( int i = 0; i < 4; ++i ) {
            try  {
                theCellsLocs.theData[i] = new CellLoc( newLocs.theData[i] );
            } catch( BadLocExc bleh ) {
                System.out.println( "bad coordinates in Group4CellsRpt( ):" + bleh );
            }
        }
        this.howManyXs = 0;
        this.howManyOs = 0;
        this.howManyBlanks = 0;
        for ( int i = 0; i < 4; ++i ) {
            int currVal = myBoard.getCellXorO( theCellsLocs.theData[i] );
            /* getCellXorO is a Board method: this might have been an internal class to
            class Board, and now that it isn't should this class keep a ref to the board
            when constructed?? */
            this.theCellsValues[i] = currVal;
            switch (currVal)
            {
                case Cell.X :
                    this.howManyXs++;
                    break;
                
                case Cell.O :
                    this.howManyOs++;
                    break;
                
                case Cell.BLANK :
                    this.howManyBlanks++;
                    break;
                
                default :
                    System.out.println( "Bad Cell type " + currVal + " in cell[" + theCellsLocs.theData[i] + "]" );
                    int mike = /* could throw badCellTypeExc() */ 0;
            }
        }
    } // constructor()


    public  String toString() {
        StringBuffer myStr = new StringBuffer( "{" );
        for ( int i = 0; i < 4; ++i ) {
            myStr.append( theCellsLocs.theData[i] );
            myStr.append( "=" + Cell.toString( theCellsValues[i] ) );
            if ( i < 3 ) {
                myStr.append( "," );
            } else {
                myStr.append( "}" );
            }
        }
        return myStr.toString();
    } // toString()
    

    public int howManyXOBs( int XorOorB ) throws BadCellTypeExc {
        switch (XorOorB)
        {
            case Cell.X :
                return howManyXs;
                //break;
            
            case Cell.O :
                return howManyOs;
                //break;
            
            case Cell.BLANK :
                return howManyBlanks;
                //break;
            
            default :
                throw new BadCellTypeExc( Integer.toString( XorOorB ) );
        }
    } // howManyXOBs( int )
        

    /**
     * put another cell into the group of 4, if there's room
     */
    void addLoc( CellLoc newPt3D, int newVal ) throws TooManyCellsExc, BadLocExc {
        if ( howManyLocs < 4 ) {
            theCellsLocs.theData[howManyLocs] = new CellLoc( newPt3D );
            theCellsValues[howManyLocs] = newVal;
        }
        else {
            StringBuffer erStr = new StringBuffer( "{" );
            for ( int i = 0; i < 4; ++i ) {
                erStr.append( theCellsLocs.theData[i] );
                erStr.append( "=" + Cell.toString( theCellsValues[i] ) );
                if ( i < 3 ) {
                    erStr.append( "," );
                } else {
                    erStr.append( "}" );
                }
            }
            throw new TooManyCellsExc( erStr.toString() );/* print out the cells we've got already */
        }
    } // addLoc (CellLoc, int)


     /**
     * put another cell into the group of 4, if there's room
     */
   void addLoc( int newX, int newY, int newZ, int newVal ) throws TooManyCellsExc, BadLocExc {
        if ( howManyLocs < 4 ) {
            theCellsLocs.theData[howManyLocs] = new CellLoc( newX, newY, newZ );
            theCellsValues[howManyLocs] = newVal;
        } else {/* print out the cells we've got  already */
            throw new TooManyCellsExc(  this.toString() );
        }
    } // addLoc( int, int, int, int)


    boolean[] whereXs() throws BadCellTypeExc {
        return whereXOBs( Cell.X );
    }

    boolean[] whereOs() throws BadCellTypeExc {
        return whereXOBs( Cell.O );
    }

    boolean[] whereBlanks() throws BadCellTypeExc {
        return whereXOBs( Cell.BLANK );
    }


    /** 
     * Returns an array telling which of the four cells is X (or O or BLANK)
     */
    boolean[] whereXOBs( int XorOorB ) throws BadCellTypeExc {
        boolean[] theData = new boolean [ 4 ];
        if ( (XorOorB != Cell.X) && (XorOorB != Cell.O) && (XorOorB != Cell.BLANK) ) {
            throw new BadCellTypeExc( Integer.toString( XorOorB ) );
        }
        for ( int i = 0; i < 4; ++i ) {
            if ( theCellsValues[i] == XorOorB ) {
                theData[i] = true;
            } else {
                theData[i] = false;
            }
        }
        return theData;
    } // whereXOBs()


    /**
     * returns an array of (possibly 0)  LOCs of cells that are X (or O  or BLANK)
     */
    CellLoc[] locsOfXOBs( int XorOorB ) throws BadCellTypeExc {
        if ( (XorOorB != Cell.X) && (XorOorB != Cell.O) && (XorOorB != Cell.BLANK) ) {
            throw new BadCellTypeExc( Integer.toString( XorOorB ) );
        }
        int howManyFound = 0;
        int howManySupposedly = howManyXOBs( XorOorB );
        /* make an array just big enough for how many X's (or O's or Blanks) */
        int miker = /*could use a vector or an enumeration, but there 
                will only ever be at most 4,
                so why get the overhead? */
                0;
        CellLoc[] theLocs = new CellLoc [ howManySupposedly ];
        for ( int i = 0; i < 4; ++i ) {
            if ( theCellsValues[i] == XorOorB ) {
                theLocs[howManyFound] = theCellsLocs.theData[i];
                howManyFound++;
            }
        }
        if ( howManyFound != howManySupposedly ) {
            throw new BadCellTypeExc( "number of" + Cell.toString( XorOorB ) + " in data doesn't match num in report in locsOfXOBs(" );
        }
        return theLocs;
    } // locsOfXOBs()
}  // class Group4CellsRpt