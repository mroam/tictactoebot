import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * Class OldTicTacTApplet - oldfashioned way of starting the program,
 * being phased out by Mike in 2017, replaced by "Boss" which has JFrame
 * 
 * @author Mike Roam
 * @version 6 December 2004
 */
public class OldTicTacTApplet extends JApplet
{
	// instance variables
	 OldBoard myBoard = /* set up in init() */ null;

	 TextField xScoreFld = new TextField( /* size */ 4 );
	 TextField oScoreFld = new TextField( /* size */ 4 );

	 Button newGameBtn = new Button( "New Game" );

	 double xSeriesScore = 0;
	 double oSeriesScore = 0;

	TextField xSeriesScoreFld = /* for multi game results */ new TextField( /* size */ 4 );
	TextField oSeriesScoreFld = /* for multi game results */ new TextField( /* size */ 4 );
	
	TextArea debugFeedback = new TextArea(/* lines tall */ 10, /* chars per line */ 75); /* trying to have place to self debug */
	

	 /**
	 * Called by the browser or applet viewer to inform this JApplet that it
	 * has been loaded into the system. It is always called before the first 
	 * time that the start method is called.
	 */
	public void init()
	{
		// this is a workaround for a security conflict with some browsers
		// including some versions of Netscape & Internet Explorer which do 
        // not allow access to the AWT system event queue which JApplets do 
        // on startup to check access. May not be necessary with your browser. 
		JRootPane rootPane = this.getRootPane();	
		rootPane.putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
	
	    Debug.debug = true;
		myBoard = null; // = new Board( 450, 300, this );
		this.getContentPane( ).setLayout(new FlowLayout() );
		this.getContentPane( ).add( myBoard );
		Panel scorePanel = new Panel();
		scorePanel.add( new Label( "Human Score:" ) );
		scorePanel.add( xScoreFld );
		xScoreFld.setText( Integer.toString( myBoard.xScore ) );
		xScoreFld.setEditable( false );
		scorePanel.add( new Label( "Computer Score" ) );
		scorePanel.add( oScoreFld );
		oScoreFld.setText( Integer.toString( myBoard.oScore ) );
		oScoreFld.setEditable( false );
		this.getContentPane( ).add( scorePanel );
		this.getContentPane( ).add( newGameBtn );
		Panel seriesPanel = new Panel();
		seriesPanel.add( new Label( "Extended Series--Human Series Score:" ) );
		seriesPanel.add( xSeriesScoreFld );
		xSeriesScoreFld.setText( Pretty.trimDec( xSeriesScore, 1 ) );
		xSeriesScoreFld.setEditable( false );
		seriesPanel.add( new Label( "Computer Series Score" ) );
		seriesPanel.add( oSeriesScoreFld );
		oSeriesScoreFld.setText( Pretty.trimDec( oSeriesScore, 1 ) );
		oSeriesScoreFld.setEditable( false );
		seriesPanel.setBackground( Color.yellow );
		this.getContentPane( ).add( seriesPanel );
		this.getContentPane( ).add( debugFeedback ); // TextArea
		newGameBtn.addActionListener( new NewGameLsr() );
		// provide any initialisation necessary for your JApplet
	} // init()
	
	
	public  void scoresHaveChanged( ) {
		xScoreFld.setText( Integer.toString( myBoard.xScore ) );
		oScoreFld.setText( Integer.toString( myBoard.oScore ) );
		xSeriesScoreFld.setText( Pretty.trimDec( xSeriesScore, 1 ) );
		oSeriesScoreFld.setText( Pretty.trimDec( oSeriesScore, 1 ) );
	} // scoresHaveChanged()
	
	
	/** 
	 * Try to show info in my debugFeedback Text Area.
	 * Also see the Debug class which merely uses System.out.print
	 */
	public void showDebugInfo( String newMsg ) {
	    debugFeedback.append( newMsg + "\n");  // deprecated "appendText( )
	}
	    
	
	class NewGameLsr extends Object implements ActionListener{

		public  void actionPerformed( ActionEvent e ) {
			if ( myBoard.xScore > myBoard.oScore ) {
				xSeriesScore++;
			} else {
				if ( myBoard.oScore > myBoard.xScore ) {
					oSeriesScore++;
				} else {
					xSeriesScore += 0.5;
					oSeriesScore += 0.5;
				}
			}
			myBoard.newGame();
			scoresHaveChanged();
		} // actionPerformed()

	} // class NewGameLsr

	/**
	 * Called by the browser or applet viewer to inform this JApplet that it 
	 * should start its execution. It is called after the init method and 
	 * each time the JApplet is revisited in a Web page. 
	 */
	public void start()
	{
		// provide any code requred to run each time 
		// web page is visited
	}

	/** 
	 * Called by the browser or applet viewer to inform this JApplet that
	 * it should stop its execution. It is called when the Web page that
	 * contains this JApplet has been replaced by another page, and also
	 * just before the JApplet is to be destroyed. 
	 */
	public void stop()
	{
		// provide any code that needs to be run when page
		// is replaced by another page or before JApplet is destroyed 
	}

	/**
	 * Paint method for applet.
	 * 
	 * @param  g   the Graphics object for this applet
	 */
	public void paint(Graphics g)
	{
		// simple text displayed on applet
		g.setColor(Color.white);
		g.fillRect(0, 0, 200, 100);
        // 		g.setColor(Color.black);
        // 		g.drawString("Sample Applet", 20, 20);
        // 		g.setColor(Color.blue);
        // 		g.drawString("created by BlueJ", 20, 40);
	}

	/**
	 * Called by the browser or applet viewer to inform this JApplet that it
	 * is being reclaimed and that it should destroy any resources that it
	 * has allocated. The stop method will always be called before destroy. 
	 */
	public void destroy( )
	{
		// provide code to be run when JApplet is about to be destroyed.
	}


	/**
	 * Returns information about this applet. 
	 * An applet should override this method to return a String containing 
	 * information about the author, version, and copyright of the JApplet.
	 *
	 * @return a String representation of information about this JApplet
	 */
	public String getAppletInfo( )
	{
		// provide information about the applet
		return "Title:TicTacTapple  \nAuthor:Mike Roam  \nApplet that plays 3D ticTacToe ";
	}


	/**
	 * Returns parameter information about this JApplet. 
	 * Returns information about the parameters than are understood by this JApplet.
	 * An applet should override this method to return an array of Strings 
	 * describing these parameters. 
	 * Each element of the array should be a set of three Strings containing 
	 * the name, the type, and a description.
	 *
	 * @return a String[] representation of parameter information about this JApplet
	 */
	public String[][] getParameterInfo( )
	{
		// provide parameter information about the applet
		String paramInfo[][] = {
				 {"firstParameter",	"1-10",	"description of first parameter"},
				 {"status", "boolean", "description of second parameter"},
				 {"images",   "url",	 "description of third parameter"}
		};
		return paramInfo;
	}
} // class OldTicTacTApplet
