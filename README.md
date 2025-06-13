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
 - takes the current board as a parameter
 - it will keep trying to place a piece into a random column until the column is valid

## int checkRowsPriority(String[][] board)
 - takes the board as a parameter
 - apart of the smart ai's priority system
 - checks through each row, and checks through the different ways to connect 4 in that row
 - priority is based on the count of the same player in a potential connect 4
 - if there is an obstruction, the priority is neglected and will not be stored
 - if there is no way to block the connect 4, it will also be neglected and the data will not be stored
 - returns the player (to block or connect), the column, and the priority

## int checkColumnPriority(String[][] board)
 - takes the board as a parameter
 - also apart of the smart ai's priority system
 - this function checks through each column, and checks through the different ways to block the connect 4 in this column
 - once again, the priority is placed towards non-obstructed potential ways of winning
 - since columns are easier to block, there are no additional checks to select the column to drop the piece into
 - returns the player (to block or connect), the column, and the priority

## int checkDiagnolPriority(String[][] board)
 - also takes the board as a parameter
 - once again, part of the smart ai's priority system
 - this function checks through each diagnol, and checks through the different ways to block the connect 4 in these diagnols
 - this function has to check the diagnols going upwards and going downwards
 - priority is placed towards non-obstructed potential ways of winning and potential ways of winning that can be defended against
 - returns the player (to block or connect), the column, and the priority

## void playSmart(String[][] board)
 - takes in the board as a parameter
 - combines the 3 prior functions within this, as this function acts as the brain of the smart AI
 - it goes through and determines which function has the highest priority (if two functions have the same priority, but one of them involves the AI's winning moves, the priority is automatically handed to the AI)
 - it drops the piece into the column that belongs to the highest priority

## void Game(int mode, int[] wins)
 - takes in the mode selected as a parameter
 - takes in the wins for each player as a parameter
 - the player vs player mode takes input from the player twice, and after each input, a win is checked for
 - the player vs random mode takes input from the player once, a random column is selected, and a win is checked for after each
 - the player vs smart mode takes input from the player once, a smart decision is made, and a win is checked for after each
 - the winner is displayed
 - the leaderboard is shown
 - the user is prompted if they would like to play again

# Variables

## int p=0;
 - this variable was used in priority checks to loop through the priority of the two different players

## int r=0;
 - this variable was used when iterating through the rows

## int c=0;
 - this variable was used when iterating through columns

## int i=0;
 - this variable was used when iterating through connect 4 sequences
