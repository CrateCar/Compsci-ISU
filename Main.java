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

    public static void Game(String mode) {
        String[][] board = {
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "}    
        }; //2D array of the board
        
        printBoard(board);
    }
    public static void main(String[] args) {
        // Fancy Menu UI
        Scanner sc = new Scanner(System.in);
        System.out.println("Game Modes:");

        System.out.println("[1] Player Vs Player");
        System.out.println("[2] Player Vs Computer (Random)");
        System.out.println("[3] Player Vs Computer (HARD)");
        Game("pvp");
        System.out.print("Select a game mode to play (by number):");
        sc.close();
    }
}
