
/**
 * Boss has main( ) for starting a game, and has a JFrame for showing stuff
 * 
 * @author Mike Roam
 * @version 2016 Dec 14 a
 * from Java Swing tutorial
 * https://docs.oracle.com/javase/tutorial/uiswing/examples/start/HelloWorldSwingProject/src/start/HelloWorldSwing.java
 * downloaded 2016 Dec 14
 * 
 * This is trying to take the place of our TicTacTApplet, and our Board will probably want to become
 * a JPanel instead of its current antiquated "Canvas"
 * See http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJPanelasacanvas.htm
 */
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
/**
 * This example, like all Swing examples, exists in a package:
 * in this case, the "start" package.
 * If you are using an IDE, such as NetBeans, this should work 
 * seamlessly.  If you are compiling and running the examples
 * from the command-line, this may be confusing if you aren't
 * used to using named packages.  In most cases,
 * the quick and dirty solution is to delete or comment out
 * the "package" line from all the source files and the code
 * should work as expected.  For an explanation of how to
 * use the Swing examples as-is from the command line, see
 * http://docs.oracle.com/javase/javatutorials/tutorial/uiswing/start/compile.html#package
 */
// package start;       // <== this has to match the containing folder's name!!
 
/*
 * HelloWorldSwing.java requires no other files. 
 */
import javax.swing.*;
import java.awt.*;    // supplies "Color"
import java.awt.event.*;  // for buttons with listeners
 

public class Boss {
    
    static Boss myBoss = /* set up in createAndShowGUI( ) ? */ null;
    // instance or class variables
	static Board myBoard = /* set up in initGUI( ) */ null;
	// should mWF be static? Boss just has one?
	static JFrame myWindowFrame = /* gets setup in createAndShowGUI( ) */ null;

	static TextField xScoreFld = new TextField( /* size */ 4 );
	static TextField oScoreFld = new TextField( /* size */ 4 );

	static Button newGameBtn = new Button( "New Game" );

	static double xSeriesScore = 0;
	static double oSeriesScore = 0;

	static TextField xSeriesScoreFld = /* for multi game results */ new TextField( /* size */ 4 );
	static TextField oSeriesScoreFld = /* for multi game results */ new TextField( /* size */ 4 );
	
	static TextArea debugFeedback = new TextArea(/* linesTall */ 10, /* charsPerLine */ 65); /* debugMsgs */
    
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI( Boss newBoss) {
        myBoss = newBoss;
        //Create and set up the window.
        myWindowFrame = new JFrame("Boss ttt3D");
        myWindowFrame.setPreferredSize(new Dimension(600, 600));
        myWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Make sure we have nice window decorations.  // this line from old tutorial
        myWindowFrame.setDefaultLookAndFeelDecorated(true);

        //Create a menu bar with a green background.
        JMenuBar greenMenuBar = new JMenuBar( );
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(600, 60));

        //         //Add the standard "Hello World" label.
        //         JLabel yellowLabel = new JLabel("Boss ttt3D");
        //         yellowLabel.setOpaque(true);
        //         yellowLabel.setBackground(new Color(248, 213, 131));
        //         yellowLabel.setPreferredSize(new Dimension(60, 40));

        //Set the menu bar and add the label to the content pane.
        myWindowFrame.setJMenuBar(greenMenuBar);
        // myWindowFrame.getContentPane( ).add(yellowLabel, BorderLayout.TOP);
        myWindowFrame.getContentPane( ).setLayout(new FlowLayout( ) );
        //         myWindowFrame.getContentPane( ).add(yellowLabel);
        initGUI( );

        //Display the window.
        myWindowFrame.pack( );
        myWindowFrame.setVisible(true);
    } // createAndShowGUI( )
    
    
    /**
     * Secondary, is called by createAndShowGUI( )
     */
    public static void initGUI( ) {
        Debug.debug = true;
		myBoard = new Board( 400, 250, myBoss /* was this*/ );
		myBoard.setPreferredSize(new Dimension( 400, 250 ) );
		
		myWindowFrame.getContentPane( ).add( myBoard );
		Panel scorePanel = new Panel( );
		scorePanel.add( new Label( "Human Score:" ) );
		scorePanel.add( xScoreFld );
		xScoreFld.setText( Integer.toString( myBoard.xScore ) );
		xScoreFld.setEditable( false );
		scorePanel.add( new Label( "Computer Score" ) );
		scorePanel.add( oScoreFld );
		oScoreFld.setText( Integer.toString( myBoard.oScore ) );
		oScoreFld.setEditable( false );
		myWindowFrame.getContentPane( ).add( scorePanel );
		myWindowFrame.getContentPane( ).add( newGameBtn );
		Panel seriesPanel = new Panel( );
		seriesPanel.add( new Label( "Extended Series--Human Series Score:" ) );
		seriesPanel.add( xSeriesScoreFld );
		xSeriesScoreFld.setText( Pretty.trimDec( xSeriesScore, 1 ) );
		xSeriesScoreFld.setEditable( false );
		seriesPanel.add( new Label( "Computer Series Score" ) );
		seriesPanel.add( oSeriesScoreFld );
		oSeriesScoreFld.setText( Pretty.trimDec( oSeriesScore, 1 ) );
		oSeriesScoreFld.setEditable( false );
		seriesPanel.setBackground( Color.yellow );
		myWindowFrame.getContentPane( ).add( seriesPanel );
		myWindowFrame.getContentPane( ).add( debugFeedback ); // TextArea
		newGameBtn.addActionListener( new NewGameLsr( ) );
    }
 
    
    public static void scoresHaveChanged( ) {
		xScoreFld.setText( Integer.toString( myBoard.xScore ) );
		oScoreFld.setText( Integer.toString( myBoard.oScore ) );
		xSeriesScoreFld.setText( Pretty.trimDec( xSeriesScore, 1 ) );
		oSeriesScoreFld.setText( Pretty.trimDec( oSeriesScore, 1 ) );
	} // scoresHaveChanged( )
	
    
	/** 
	 * Try to show info in my debugFeedback Text Area.
	 * Also see the Debug class which merely uses System.out.print
	 */
	public void showDebugInfo( String newMsg ) {
	    debugFeedback.append( newMsg + "\n");  // deprecated "appendText( )
	}
	    
	
	static class NewGameLsr extends Object implements ActionListener{

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
			myBoard.newGame( );
			scoresHaveChanged( );
		} // actionPerformed( )

	} // class NewGameLsr

	
    public static void main(String[ ] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable( ) {
            public void run( ) {
                Boss newBoss = new Boss( );
                createAndShowGUI( newBoss );
            }
        });
    } // main( )
} // class Boss
