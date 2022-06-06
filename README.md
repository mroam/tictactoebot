# tictactoebot
***TicTacToe Game*** with AI, with 3D 4x4x4 games
The "ttt4_bluej" directory of 2017 Apr, works in macos 11, maybe 12, has transitioned from old Applet+Canvas to JFrame+JPanel. 
Hasn't ported to JavaFX yet. 

It plays 4x4x4 ticTacToe (3D) and watches for possible wins for itself and for its human opponent. (Colorcoded as warnings for the human.)
The AI jumps at opportunities to win and to block the human from winning, and we hope to improve the AI so that it, like Alpha Go, learns from projected games (using Monte Carlo methods) and from previous games.

There is more information in the README.txt inside the ttt4 folder, and at our website https://sites.google.com/a/saintannsny.org/tictactoebot/home (that google website is replicated here in https://github.com/mroam/tictactoebot/blob/master/tictactoebot-from-goosite.txt and ...)

# TODO
Put it into double-clickable jar that will run standalone. (See the examples in chordlifter's [z-demoJFrame-bluej](https://github.com/mroam/chordlifter/tree/main/z-demoJFrame-bluej))
