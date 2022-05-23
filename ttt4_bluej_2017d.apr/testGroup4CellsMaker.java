

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * The test class testGroup4CellsMaker.
 *
 * @author  Mike Roam
 * @version Your Mom
 */
public class testGroup4CellsMaker
{
    Group4CellsMaker grpDispenser = null ; // gets built in setUp( )
    
    /**
     * Default constructor for test class testGroup4CellsMaker
     */
    public testGroup4CellsMaker( )
    {
        
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp( )
    {
        //Goat x = 3;
        //int z = 3;
        //Integer y = 3;
        grpDispenser = new Group4CellsMaker(null, null );
        System.out.println("testGroup4CellsMaker setUp is done");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testGroup4CellsMaker( )
    {
        // in setup they built new grpDispenser 
        Group4 fourLocs = null;
        // the 4x4x4 has 76 different groups of 4 cells in a row
        // This would have to change for a 5x5x5, of course...
        final int howManyGroupsOf4InARow = 76; 
        try {
            // grab and discard the first 74 groups...
            for (int i = 1; i <= (howManyGroupsOf4InARow - 2); ++i) {
                fourLocs = grpDispenser.makeNextGroup( );
                assertNotNull(fourLocs);
                System.out.println("group " + grpDispenser.groupNum + " is " + fourLocs.toString( ) );
                // this needs to skip over 74 first groups and then slowly try the last few
            }
            fourLocs = grpDispenser.makeNextGroup( );
            assertNotNull(fourLocs);
            System.out.println("group " + grpDispenser.groupNum + " is " + fourLocs.toString( ) );
            fourLocs = grpDispenser.makeNextGroup( );
            assertNotNull(fourLocs);
            System.out.println("group " + grpDispenser.groupNum + " is " + fourLocs.toString( ) );
            fourLocs = grpDispenser.makeNextGroup( );
            assertNotNull(fourLocs);
            System.out.println("group " + grpDispenser.groupNum + " is " + fourLocs.toString( ) );
            
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
    }
    
}

