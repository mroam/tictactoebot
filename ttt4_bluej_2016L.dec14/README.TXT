3D Tic Tac Toe
Building AI that plays 3D (4 in a row) tic tac toe against person.
by Mike Roam (started working on this in 1998 or earlier)

VERSION or DATE: 2016 oct 22 revision
Was 2013 jan 22 upgrade of 2004 Dec 4 version. (from original approx 2000)

HOW TO START THIS PROJECT: Run TicTacTApplet in applet viewer or browser.
ps: to open for editing in bluej, double-click the "package.bluej" file.

USER INSTRUCTIONS: The game continues until no more moves are available
and the winner is the player with the most 4-in-a-row. Unlike 3x3 tictactoe, this lacks sudden death.

Bugs:
[]Compiler is warning me "Board.java uses unchecked or unsafe operations. 
     Recompile with -Xlint:unchecked for details."
[]The board doesn't notice all 4 in a rows right away, 
3D Java Misses This human score  (3,0,0) (2,1,1) (1,2,2) (0,3,3) which is the last diagDiag

...X
....   ....
....   ..X.   ....
....   ....   ....   ....
       ....   .X..   ....
              ....   ....
                     X...
trouble might be in Group4CellsMaker.makeNextDiagDiagGroup( ) 
(Is sometimes or ever catching up on the next move?)
The problem might be in Board which has five methods 
(recountScores( ), newWins( ), canImprove( ), canWin( ), warnOpponent( ))
... which all use the idiom
for ( Group4 currGroup = myGroupMaker.makeNextGroup(); 
        myGroupMaker.hasMoreGroups; 
        currGroup = myGroupMaker.makeNextGroup()) { ... }
which might maybe be stopping at the last (diagdiag) group instead of AFTER it??

Plans:
[]Board should notice when game over (no more scoring moves of 4 in a row are available).
[]Teach the AI to build forks or traps.
[]Add some unit tests.
[]Could this be rewritten to play from server side?
