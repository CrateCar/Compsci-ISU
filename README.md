# Connect 4 Project
The connect 4 project was the project I selected to do for my ISU.  It meets all the criteria, having 3 game modes: player vs player, player vs bot (random), and player vs bot (unbeatable).

# Functions

## void printBoard(String[][] board)
 - the board parameter takes in the current board
 - prints out the board for the user to see

## boolean drop(String[][] board, int column, int player)
 - the board parameter takes in how the board looks like
 - the column parameter takes in an integer for the column that you want to drop your piece in
 - the player parameter takes in what player is currently dropping their piece
 - the function attempts to drop a piece into the board
 - the function returns whether or not the column is a valid dropping space

## int checkWin(String[][] board,int player)
 - the board parameter takes how the board looks like
 - the player parameter takes which player the win should be checked for
 - the function checks for a win in rows, then columns, then diagonal (downwards), and finally diagonal again (upwards)
 - it will return if a winner, a draw, or nothing was found

## int gameOptions()
 - simply checks for and returns what game option the user selects

## void randomizeColumn(String[][] board)
