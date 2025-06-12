//Ajay, Umaipagan

import java.util.Scanner;
import java.lang.Math;

public class Main {
    public static void printBoard(String[][] board) {
        for (int i=0;i<board.length;i++) {
            System.out.print("|");
            for (int j=0;j<board[i].length;j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.print("\n");
        }
    }

    public static boolean drop(String[][] board, int column, int player) {
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
        //Check Rows
        String[] players = {"O","X"};
        int winner = -1;
        int t_count = 0;

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

        return winner;
    }

    public static int gameOptions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Game Modes:");

        System.out.println("[1] Player Vs Player");
        System.out.println("[2] Player Vs Computer (Random)");
        System.out.println("[3] Player Vs Computer (HARD)");

        int option = 0;

        while (option == 0) {
            System.out.print("Select a game mode to play (by number):");
            int tempOption = Integer.parseInt(sc.nextLine());

            if(1 <= tempOption && tempOption <= 3) {
                option = tempOption;
            }
        }

        return option;
    }

    public static void randomizeColumn(String[][] board) {
        boolean valid = false;

        while (!valid) {
            int column = 1 +  (int) ((7-1)*Math.random());
            valid = drop(board, column, 2);
        }
    }

    public static int checkRowsPriority(String[][] board) {
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
                        if ((board[r][c+i]) == " ") {
                            if(r<5) {
                                if (board[r+1][c+i] == " ") {
                                    column = -1;
                                    break;
                                }
                            } else {
                                column = c+i;
                            }
                        } else if(board[r][c+i] == players[p]) {
                            count += 1;
                        } else {
                            column = -1;
                            break;
                        }
                    }
                    if(column != -1) {
                        if (count >= priority) {
                            priority = count;
                            col = column;
                            player = p+1;
                        }
                    }  
                }
            }
        }

        return (player * 100) + (col*10) + priority;
    }

    public static void playSmart(String[][] board) {
        int priority = 0;
        int column = 0;
        int player = 1;


        int rowsPriority = checkRowsPriority(board);
        int tempPriority = rowsPriority % 10;
        int tempCol = ((rowsPriority-tempPriority)/10) % 10;
        int tempPlayer = ((rowsPriority-tempCol)/10) % 10;

        player = tempPlayer;
        column = tempCol;
        priority = tempPriority;

        System.out.println(rowsPriority);

        drop(board, column+1, 2);
    }

    public static void Game(int mode) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Game Modes:");

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

        if (mode == 1) {
            boolean win = false;
            int winner = 0;

            System.out.println("Player 1: O");
            System.out.println("Player 2: X");

            while (!win) {
                //First Player
                System.out.println("Select a column(1-7):");
                int column = Integer.parseInt(sc.nextLine());
                boolean valid = false;
                while (!valid) {
                    if (column >= 1 && column <=7) {
                        valid = drop(board,column,1);
                    }

                    if (!valid) {
                        System.out.println("Invalid column, try again!");
                        System.out.println("Select a column(1-7):");
                        column = Integer.parseInt(sc.nextLine());
                    }
                }
        
                printBoard(board);

                int posWin = checkWin(board, 1);

                if (posWin != -1) {
                    winner = posWin;
                    win = true;
                    break;
                }

                System.out.println("Select a column(1-7):");
                column = Integer.parseInt(sc.nextLine());
                valid = false;
                while (!valid) {
                    if (column >= 1 && column <=7) {
                        valid = drop(board,column,2);
                    }

                    if (!valid) {
                        System.out.println("Invalid column, try again!");
                        System.out.println("Select a column(1-7):");
                        column = Integer.parseInt(sc.nextLine());
                    }
                }
        
                printBoard(board);

                posWin = checkWin(board, 2);

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
            }

            System.out.println("Play again?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            
            int option = 0;

            while (option == 0) {
                System.out.println("Select an option:");
                int tempOption = Integer.parseInt(sc.nextLine());

                if(1 <= tempOption && tempOption <= 2) {
                    option = tempOption;
                }
            }

            if (option == 1) {
                option = gameOptions();
                Game(option);
            } else {
                System.out.println("Quitting!");
                sc.close();
                return;
            }
        } else if (mode == 2) {
            boolean win = false;
            int winner = 0;

            System.out.println("Player 1: O");
            System.out.println("Player 2: X");

            while (!win) {
                //First Player
                System.out.println("Select a column(1-7):");
                int column = Integer.parseInt(sc.nextLine());
                boolean valid = false;
                while (!valid) {
                    if (column >= 1 && column <=7) {
                        valid = drop(board,column,1);
                    }

                    if (!valid) {
                        System.out.println("Invalid column, try again!");
                        System.out.println("Select a column(1-7):");
                        column = Integer.parseInt(sc.nextLine());
                    }
                }
        
                printBoard(board);

                int posWin = checkWin(board, 1);

                if (posWin != -1) {
                    winner = posWin;
                    win = true;
                    break;
                }

                System.out.println("AI's turn:");

                randomizeColumn(board);
        
                printBoard(board);

                posWin = checkWin(board, 2);

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
            }

            System.out.println("Play again?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            
            int option = 0;

            while (option == 0) {
                System.out.println("Select an option:");
                int tempOption = Integer.parseInt(sc.nextLine());

                if(1 <= tempOption && tempOption <= 2) {
                    option = tempOption;
                }
            }

            if (option == 1) {
                option = gameOptions();
                Game(option);
            } else {
                System.out.println("Quitting!");
                sc.close();
                return;
            }
        } else {
            boolean win = false;
            int winner = 0;

            System.out.println("Player 1: O");
            System.out.println("Player 2: X");

            while (!win) {
                //First Player
                System.out.println("Select a column(1-7):");
                int column = Integer.parseInt(sc.nextLine());
                boolean valid = false;
                while (!valid) {
                    if (column >= 1 && column <=7) {
                        valid = drop(board,column,1);
                    }

                    if (!valid) {
                        System.out.println("Invalid column, try again!");
                        System.out.println("Select a column(1-7):");
                        column = Integer.parseInt(sc.nextLine());
                    }
                }
        
                printBoard(board);

                int posWin = checkWin(board, 1);

                if (posWin != -1) {
                    winner = posWin;
                    win = true;
                    break;
                }

                System.out.println("AI's turn:");

                playSmart(board);
        
                printBoard(board);

                posWin = checkWin(board, 2);

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
            }

            System.out.println("Play again?");
            System.out.println("[1] Yes");
            System.out.println("[2] No");
            
            int option = 0;

            while (option == 0) {
                System.out.println("Select an option:");
                int tempOption = Integer.parseInt(sc.nextLine());

                if(1 <= tempOption && tempOption <= 2) {
                    option = tempOption;
                }
            }

            if (option == 1) {
                option = gameOptions();
                Game(option);
            } else {
                System.out.println("Quitting!");
                sc.close();
                return;
            }
        }
    }
    public static void main(String[] args) {
        // Fancy Menu UI
        Scanner sc = new Scanner(System.in);

        int option = gameOptions();

        Game(option);

        sc.close();
    }
}
