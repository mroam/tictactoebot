

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
    /**
     * Default constructor for test class testGroup4CellsMaker
     */
    public testGroup4CellsMaker()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        //Goat x = 3;
        //int z = 3;
        //Integer y = 3;
        Group4CellsMaker billybob = new Group4CellsMaker(null, null );
        try {
            Group4 grp = billybob.makeNextGroup( );
            System.out.println(grp);
            // this needs to skip over 74 first groups and then slowly try the last few
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
        
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
}
