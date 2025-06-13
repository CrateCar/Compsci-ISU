//Ajay, Umaipagan

import java.util.Scanner;
import java.lang.Math;

public class Main {
    public static void printBoard(String[][] board) {
        // simply prints out the board for the user to see
        for (int i=0;i<board.length;i++) {
            System.out.print("|");
            for (int j=0;j<board[i].length;j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.print("\n");
        }
    }

    public static boolean drop(String[][] board, int column, int player) {
        // handles dropping into columns ONLY if its valid
        boolean dropped = false;
        String[] players = {"O","X"};

        for (int r=5;r>=0;r--) {
            if (board[r][column-1] == " ") {
                board[r][column-1] = players[player-1];
                dropped = true;
                break;
            }
        }

        return dropped;
    }

    public static int checkWin(String[][] board,int player) {
        //checks for wins
        String[] players = {"O","X"};
        int winner = -1;
        int t_count = 0;
        
        //checks if there is a draw
        for (int r=0;r<6;r++) {
            for (int c=0;c<7;c++) {
                if (board[r][c] != " ") {
                    t_count++;
                }
            }
        }

        if (t_count == (6*7)) {
            winner = 0;
            return winner;
        }

        // checks for wins in rows
        for (int r=0;r<6;r++) {
            for (int c=0;c<4;c++) {
                int count = 0;
                for (int i=0;i<4;i++) {
                    if(board[r][c+i] == players[player-1]) {
                        count++;
                    }
                }
                if(count == 4) {
                    winner = player;
                    return winner;
                }
            }
        }

        //checks for wins in columns
        for (int r=0;r<3;r++) {
            for (int c=0;c<7;c++) {
                int count = 0;
                for (int i=0;i<4;i++) {
                    if(board[r+i][c] == players[player-1]) {
                        count++;
                    }
                }
                if(count == 4) {
                    winner = player;
                    return winner;
                }
            }
        }

        //checks for diagonal wins going downwards
        for (int r=0;r<3;r++) {
            for (int c=0;c<4;c++) {
                int count = 0;
                for (int i=0;i<4;i++) {
                    if(board[r+i][c+i] == players[player-1]) {
                        count++;
                    }
                }
                if(count == 4) {
                    winner = player;
                    return winner;
                }
            }
        }

        //checks for diagonal wins going upwards
        for (int r=5;r>=3;r--) {
            for (int c=0;c<4;c++) {
                int count = 0;
                for (int i=0;i<4;i++) {
                    if(board[r-i][c+i] == players[player-1]) {
                        count++;
                    }
                }
                if(count == 4) {
                    winner = player;
                    return winner;
                }
            }
        }

        return winner; // returns a winner if there is one, otherwise, it returns -1
    }

    public static int gameOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Game Modes:");
        // displays the different game modes to the user
        System.out.println("[1] Player Vs Player");
        System.out.println("[2] Player Vs Computer (Random)");
        System.out.println("[3] Player Vs Computer (HARD)");

        int option = 0;
        // awaits a valid answer before actually collecting the option
        while (option == 0) {
            System.out.print("Select a game mode to play (by number):");
            int tempOption = sc.nextInt();

            if(1 <= tempOption && tempOption <= 3) {
                option = tempOption;
            }
        }

        //sc.close();

        return option; // returns the option given by the user
    }

    public static void randomizeColumn(String[][] board) {
        boolean valid = false;

        while (!valid) {
            int column = 1 +  (int) ((7-1)*Math.random());
            valid = drop(board, column, 2);
        }

        //very simple code, it just selects a random valid column and drops it in there
    }

    public static int checkRowsPriority(String[][] board) {
        // apart of the smart ai's priority system
        // this particular function checks through rows
        // it checks if there is a sequence of 4 columns that are not obstructed, and have potential to win
        // the more a player has filled in that is not obstructed, the higher the priority is

        int priority = 0;
        int col = 0;
        int player = 1;

        String[] players = {"O","X"};

        for (int p=0;p<2;p++) {
            for (int r=0;r<6;r++) {
                for (int c=0;c<4;c++) {
                    int count = 0;
                    int column = -1;
                    for (int i=0;i<4;i++) { 
                        if ((board[r][c+i]) == " ") { // checks if there is an open space to place a block
                            if(r<5) {
                                if (board[r+1][c+i] == " ") { // checks if placing in this open space would do anything (if the space below the open space is empty, placing anythign there would not be effective)
                                    column = -1;
                                    break;
                                } else {
                                    column = c+i; // gives an open column to place in
                                }
                            } else {
                                column = c+i; // automatically gives an open column to place if its on the bottom row
                            }
                        }else if(board[r][c+i] == players[p]) { // if the following space is the same player, it ups the priority
                            count++;
                        }else { // checks for obstruction, if there's obstruction, there's nothing to worry about
                            column = -1;
                            break;
                        }
                    }
                    if(column != -1) { // checks if there is a valid column
                        if (count > priority) { // checks if the priority in this little sequence is higher than the highest row priority recorded
                            priority = count; // resets the highest priority
                            col = column;
                            player = p+1;
                        }
                    }  
                }
            }
        }

        return (player * 100) + (col*10) + priority; // returns a chunk of information, the player, the column, and the priority
    }

    public static int checkColumnPriority(String[][] board) {
        // also apart of the ai's priority system
        // checks for priority in columns

        int priority = 0;
        int col = 0;
        int player = 1;

        String[] players = {"O","X"};

        for (int p=0;p<2;p++) {
            for (int r=0;r<3;r++) {
                for (int c=0;c<6;c++) {
                    int count = 0;
                    int column = -1;
                    for (int i=0;i<4;i++) {
                        if ((board[r+i][c]) == " ") { // checks for an open spot in this column sequence
                            column = c;
                        } else if(board[r+i][c] == players[p]) { // checks for the count of the player's occurence in this sequence
                            count++;
                        } else { // checks for obstruction
                            column = -1;
                            break;
                        }
                    }
                    if(column != -1) {
                        if (count > priority) {
                            priority = count;
                            col = column;
                            player = p+1;
                        }
                    }  
                }
            }
        }

        return (player * 100) + (col*10) + priority; // returns the highest priority in the columns
    }

    public static int checkDiagnolPriority(String[][] board) {
        // this function checks for the priority of diagonol sequences
        // however, this code has to run twice, to find the priority of the diagnol sequences going upwards AND downwards

        int priority = 0;
        int col = 0;
        int player = 1;

        String[] players = {"O","X"};

        for (int p=0;p<2;p++) {
            for (int r=0;r<3;r++) {
                for (int c=0;c<4;c++) {
                    int count = 0;
                    int column = -1;

                    //checks for the diagonal priority going downwards
                    for (int i=0;i<4;i++) {
                        if ((board[r+i][c+i]) == " ") { // checks for an open space
                            if(r+i<5) {
                                if (board[r+i+1][c+i] == " ") { // checks if dropping the piece in this column would actually be effective
                                    column = -1;
                                    break;
                                } else {
                                    column = c+i;
                                }
                            } else {
                                column = c+i;
                            }
                        } else if(board[r+i][c+i] == players[p]) { // counts the priority of this player's segment 
                            count ++;
                        } else { // breaks if there's obstruction
                            column = -1;
                            break;
                        }
                    }

                    if(column != -1) {
                        if (count > priority) {
                            priority = count;
                            col = column;
                            player = p+1;
                        }
                    }

                    count = 0;

                    //checks diagnolly going upwards
                    for (int i=0;i<4;i++) {
                        if ((board[5-r-i][c+i]) == " ") {
                            if(5-r-i<5) {
                                if (board[5-r-i+1][c+i] == " ") {
                                    column = -1;
                                    break;
                                } else {
                                    column = c+i;
                                }
                            } else {
                                column = c+i;
                            }
                        } else if(board[5-r-i][c+i] == players[p]) {
                            count ++;
                        } else {
                            column = -1;
                            break;
                        }
                    }  

                    if(column != -1) {
                        if (count > priority) {
                            priority = count;
                            col = column;
                            player = p+1;
                        }
                    }
                }
            }
        }

        return (player * 100) + (col*10) + priority; // returns diagonal priority data
    }

    public static void playSmart(String[][] board) {
        // main brain of the smart AI, this is where all the priority data is analyzed

        int priority = 0;
        int column = 0;

        int priorityCheck = checkRowsPriority(board); // gets the row priority data
        int tempPriority = priorityCheck % 10;
        priorityCheck = ((priorityCheck-tempPriority)/10);
        int tempCol = priorityCheck % 10;
        priorityCheck = ((priorityCheck-tempCol)/10);
        int tempPlayer = priorityCheck % 10;

        column = tempCol;
        priority = tempPriority;

        priorityCheck = checkColumnPriority(board); // gets the column priority data
        tempPriority = priorityCheck % 10;
        priorityCheck = ((priorityCheck-tempPriority)/10);
        tempCol = priorityCheck % 10;
        priorityCheck = ((priorityCheck-tempCol)/10);
        tempPlayer = priorityCheck % 10;

        if (tempPriority > priority || (tempPriority >= priority && (tempPlayer == 2))) { // checks if column has a higher priority, and priority is favoured if it could lead to the AI's win
            column = tempCol;
            priority = tempPriority;
        }

        priorityCheck = checkDiagnolPriority(board);
        tempPriority = priorityCheck % 10;
        priorityCheck = ((priorityCheck-tempPriority)/10);
        tempCol = priorityCheck % 10;
        priorityCheck = ((priorityCheck-tempCol)/10);
        tempPlayer = priorityCheck % 10;

        if (tempPriority > priority || (tempPriority >= priority && (tempPlayer == 2))) { // checks if the diagonals have a higher priority
            column = tempCol;
            priority = tempPriority;
        }

        boolean valid = drop(board, column+1, 2); // drops it in the best possible column

        if (!valid) { // in the rare case the column it selected was not valid, it will select a random column to drop into
            randomizeColumn(board);
        }
    }

    public static void Game(int mode, int[] wins) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Begin!");

        String[][] board = {
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "}    
        }; //2D array of the board

        printBoard(board);

        boolean win = false;
        int winner = 0;
        int option = 0;

        System.out.println("Player 1: O");
        System.out.println("Player 2: X");

        switch(mode) { // switches between the modes from 1-3
            case 1: // option 1 - player vs player
                while (!win) {
                    //First Player
                    System.out.println("Select a column(1-7):"); // player 1 selects a column
                    int column = sc.nextInt();
                    boolean valid = false;
                    while (!valid) { // validation checker
                        if (column >= 1 && column <=7) {
                            valid = drop(board,column,1);
                        }

                        if (!valid) {
                            System.out.println("Invalid column, try again!");
                            System.out.println("Select a column(1-7):");
                            column = sc.nextInt();
                        }
                    }
            
                    printBoard(board); // shows the board

                    int posWin = checkWin(board, 1); // checks if player 1 made a winning move

                    if (posWin != -1) { // if there's a win, exit the main loop
                        winner = posWin;
                        win = true;
                        break;
                    }

                    System.out.println("Select a column(1-7):"); // player 2 selects a column
                    column = sc.nextInt();
                    valid = false;
                    while (!valid) { // checks for validation
                        if (column >= 1 && column <=7) {
                            valid = drop(board,column,2);
                        }

                        if (!valid) {
                            System.out.println("Invalid column, try again!");
                            System.out.println("Select a column(1-7):");
                            column = sc.nextInt();
                        }
                    }
            
                    printBoard(board); // shows the board again

                    posWin = checkWin(board, 2); // checks if player 2 made any winning moves

                    if (posWin != -1) {
                        winner = posWin;
                        win = true;
                        break;
                    }
                }

                if (winner == 0) {
                    System.out.println("Nobody won!");
                }else{
                    System.out.println(String.format("Player %d won!", winner));
                    wins[winner-1] = wins[winner-1]+1; // if there was a win, it gives the winning player a win
                }
            case 2:
                while (!win) {
                    //First Player
                    System.out.println("Select a column(1-7):"); // the player goes
                    int column = sc.nextInt();
                    boolean valid = false;
                    while (!valid) {
                        if (column >= 1 && column <=7) {
                            valid = drop(board,column,1);
                        }

                        if (!valid) {
                            System.out.println("Invalid column, try again!");
                            System.out.println("Select a column(1-7):");
                            column = sc.nextInt();
                        }
                    }
            
                    printBoard(board); // the player is showed how is move looked like

                    int posWin = checkWin(board, 1); // checks if player 1 made a winning move

                    if (posWin != -1) {
                        winner = posWin;
                        win = true;
                        break;
                    }

                    System.out.println("AI's turn:");

                    randomizeColumn(board); // the ai moves to a random valid column

                    posWin = checkWin(board, 2); // checks if the bot made a winning move

                    if (posWin != -1) {
                        winner = posWin;
                        win = true;
                        break;
                    }
                }
                if (winner == 0) {
                    System.out.println("Nobody won!");
                }else{
                    System.out.println(String.format("Player %d won!", winner));
                    wins[winner-1] = wins[winner-1]+1; // gives the winning player a win on the leader board
                }
            case 3:
                while (!win) {
                    //First Player
                    System.out.println("Select a column(1-7):"); // the player goes
                    int column = sc.nextInt();
                    boolean valid = false;
                    while (!valid) { // validates the move
                        if (column >= 1 && column <=7) {
                            valid = drop(board,column,1);
                        }

                        if (!valid) {
                            System.out.println("Invalid column, try again!");
                            System.out.println("Select a column(1-7):");
                            column = sc.nextInt();
                        }
                    }
            
                    printBoard(board); // shows the player their move

                    int posWin = checkWin(board, 1); // checks if that was a winning move

                    if (posWin != -1) {
                        winner = posWin;
                        win = true;
                        break;
                    }

                    System.out.println("AI's turn:");

                    playSmart(board); // the smart AI makes a play
            
                    printBoard(board); // prints the board

                    posWin = checkWin(board, 2); // checks if the smart AI won

                    if (posWin != -1) {
                        winner = posWin;
                        win = true;
                        break;
                    }
                }

                if (winner == 0) {
                    System.out.println("Nobody won!");
                }else{
                    System.out.println(String.format("Player %d won!", winner));
                    wins[winner-1] = wins[winner-1]+1; // adds a win to the winner
                }
            default:
                break;
        }

        // leaderboard system

        System.out.println("\nLeaderboard:");
        System.out.println(String.format("Player 1 Wins - %d", wins[0])); // shows player 1's wins
        System.out.println(String.format("Player 2 Wins - %d", wins[1])); // shows player 2's wins

        System.out.println("\nPlay again?"); // asks the player if they want to play again
        System.out.println("[1] Yes");
        System.out.println("[2] No");
        
        option = 0;

        while (option == 0) { // validates the option
            System.out.println("Select an option:");
            int tempOption = sc.nextInt();

            if(1 <= tempOption && tempOption <= 2) {
                option = tempOption;
            }
        }

        if (option == 1) { // play again
            option = gameOptions();
            Game(option,wins);
        } else { // quit the game
            System.out.println("Quitting!");
            sc.close();
            return;
        }

        sc.close();
    }
    public static void main(String[] args) {
        // my fancy menu ui
        Scanner sc = new Scanner(System.in);

        int option = gameOptions();
        int wins[] = {0,0};

        Game(option,wins); // main game loop is located right here

        sc.close();
    }
}
