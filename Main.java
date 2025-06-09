//Ajay, Umaipagan

import java.util.Scanner;

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
            }
        }

        return dropped;
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
                System.out.println("Select a column(1-7):");
                int column = Integer.parseInt(sc.nextLine());

                boolean valid = false;
                while (!valid) {
                    if (column >= 1 && column <=7) {

                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        // Fancy Menu UI
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

        Game(option);

        sc.close();
    }
}
