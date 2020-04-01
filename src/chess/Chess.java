package chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main Class
 *
 * @author  Thomas Hanna trh80
 */
public class Chess
{
    public static int[] moveLocInt, pieceLocInt;

    public static void main(String[] args) {
        boolean whiteDone = false;
        boolean blackDone = false;
        String userMove;
        String userTemp;
        String[][] strBoard;

        Scanner input = new Scanner(System.in);
        Board board = new Board();
        print(board);

        //Checks if game still in play
        while(true)
        {
            while(!whiteDone)       //goes through white's turn
            {
                System.out.print("White's move: ");
                userMove = input.nextLine();

                strBoard = board.stringBoard();
                userTemp = userMove.substring(0,5);
                userMoveFormat(userTemp, board, "white");

                if(kingsDead(board)) return;
                if(canPrint()) {
                    if (userMove.matches("[abcdefgh][12345678] [abcdefgh][12345678]")
                            || userMove.matches("[abcdefgh][12345678] [abcdefgh][12345678] [QNRB]") ||
                            userMove.matches("[abcdefgh][12345678] [abcdefgh][12345678] draw[?]"))        //checks for draw, a promotion or for a regular move
                    {
                        if (userMove.length() == 11)             //checks for draw
                        {
                            userTemp = userMove.substring(6);
                            System.out.println(userTemp);
                            userTemp = input.nextLine();

                            if (userTemp.equalsIgnoreCase("draw")) {
                                System.out.println(userTemp);
                                return;
                            } else {
                                if (board.checkMate(board)) return;
                                if (board.inCheck(board)) System.out.println("\nCheck");

                                for (int i = 0; i < 8; i++) {
                                    if (!Arrays.equals(board.stringBoard()[i], strBoard[i]))
                                        whiteDone = true;
                                }
                                print(board);
                            }
                        }

                        if (Board.chessBoard[moveLocInt[1]][moveLocInt[0]] != null
                                && (Board.chessBoard[moveLocInt[1]][moveLocInt[0]].isPawn()
                                && moveLocInt[1] == 0))         //checks if pawn and at edge of board for it's color
                        {
                            if (userMove.length() == 7) {
                                switch (userMove.charAt(6)) {
                                    case 'N':
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Knight(true);
                                        break;
                                    case 'R':
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Rook(true);
                                        break;
                                    case 'B':
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Bishop(true);
                                        break;
                                    case 'Q':
                                    default:
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Queen(true);
                                        break;
                                }

                                if (board.checkMate(board)) return;
                                if (board.inCheck(board)) System.out.println("\nCheck");

                                for (int i = 0; i < 8; i++) {
                                    if (!Arrays.equals(board.stringBoard()[i], strBoard[i]))
                                        whiteDone = true;
                                }
                                print(board);
                            } else              //defaults to queen
                            {
                                Board.chessBoard[pieceLocInt[1]][pieceLocInt[0]] = null;
                                Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Queen(true);
                            }
                        }

                        if (userMove.length() == 5) {
                            if (board.checkMate(board)) return;
                            if (board.inCheck(board)) System.out.println("\nCheck");

                            for (int i = 0; i < 8; i++) {
                                if (!Arrays.equals(board.stringBoard()[i], strBoard[i]))
                                    whiteDone = true;
                            }

                            print(board);
                        }

                    } else if (userMove.equalsIgnoreCase("resign")) {
                        System.out.println(userMove + "\n" + "Black Wins");
                        return;
                    } else {
                        System.out.println("Illegal move, try again.");
                        System.out.println();
                    }
                }

            }
            while(!blackDone)           //goes through black's turn
            {
                System.out.print("Black's move: ");
                userMove = input.nextLine();

                strBoard = board.stringBoard();
                userTemp = userMove.substring(0,5);
                userMoveFormat(userTemp, board, "black");

                if(kingsDead(board)) return;
                if(canPrint()) {
                    if (userMove.matches("[abcdefgh][12345678] [abcdefgh][12345678]")
                            || userMove.matches("[abcdefgh][12345678] [abcdefgh][12345678] [QNRB]") ||
                            userMove.matches("[abcdefgh][12345678] [abcdefgh][12345678] draw[?]"))        //checks for draw, a promotion or for a regular move
                    {
                        if (userMove.length() == 11)         //checks for draw
                        {
                            userTemp = userMove.substring(6);
                            System.out.println(userTemp);
                            userTemp = input.nextLine();

                            if (userTemp.equalsIgnoreCase("draw")) {
                                System.out.println(userTemp);
                                return;
                            } else {
                                if (board.checkMate(board)) return;
                                if (board.inCheck(board)) System.out.println("\nCheck");

                                for (int i = 0; i < 8; i++) {
                                    if (!Arrays.equals(board.stringBoard()[i], strBoard[i]))
                                        blackDone = true;
                                }
                                print(board);
                            }
                        }

                        if ((Board.chessBoard[moveLocInt[1]][moveLocInt[0]] != null
                                && Board.chessBoard[moveLocInt[1]][moveLocInt[0]].isPawn() && moveLocInt[1] == 7))     //checks if pawn and at edge of board for it's color
                        {
                            if (userMove.length() == 7) {
                                switch (userMove.charAt(6)) {
                                    case 'N':
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Knight(false);
                                        break;
                                    case 'R':
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Rook(false);
                                        break;
                                    case 'B':
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Bishop(false);
                                        break;
                                    case 'Q':
                                    default:
                                        Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Queen(false);
                                        break;
                                }

                                if (board.checkMate(board)) return;
                                if (board.inCheck(board)) System.out.println("\nCheck");

                                for (int i = 0; i < 8; i++) {
                                    if (!Arrays.equals(board.stringBoard()[i], strBoard[i]))
                                        blackDone = true;
                                }
                                print(board);
                            } else                  //defaults to queen
                            {
                                Board.chessBoard[pieceLocInt[1]][pieceLocInt[0]] = null;
                                Board.chessBoard[moveLocInt[1]][moveLocInt[0]] = new Queen(false);
                            }
                        }

                        if (userMove.length() == 5) {
                            if (board.checkMate(board)) return;
                            if (board.inCheck(board)) System.out.println("\nCheck");

                            for (int i = 0; i < 8; i++) {
                                if (!Arrays.equals(board.stringBoard()[i], strBoard[i]))
                                    blackDone = true;
                            }
                            print(board);
                        }

                    } else if (userMove.equalsIgnoreCase("resign")) {
                        System.out.println(userMove + "\n" + "White Wins");
                        return;
                    } else {
                        System.out.println("Illegal move, try again.");
                        System.out.println();
                    }
                }
            }
            whiteDone = false;
            blackDone = false;
        }
    }

    /**
     *
     * @return basically if there is an illegal move this turns to false so board doesnt print
     */
    private static boolean canPrint()
    {
        return Board.printIt;
    }

    /**
     * Code for making the move with users inputs
     * @param userMove - user input for their move
     * @param board - gameboard array
     * @param color - color of piece
     */
    private static void userMoveFormat(String userMove, Board board, String color) {
        String[] pieceLoc;
        pieceLoc = userMove.split(" ",2);
        pieceLocInt = formatInput(pieceLoc[0]);
        moveLocInt = formatInput(pieceLoc[1]);
        board.setSpot(board, board.getSpot(pieceLocInt[1], pieceLocInt[0]),
                new int[]{moveLocInt[1],moveLocInt[0]}, new int[]{pieceLocInt[1], pieceLocInt[0]}, color);
    }

    /**
     * Formats input for setSpot method
     * @param loc - has piece location
     * @return -returns location in proper format to input into arrays
     */
    public static int[] formatInput(String loc){
        int[] formatted = new int[2];
        switch (loc.charAt(0)) {
            case 'a':
                break;
            case 'b':
                formatted[0] = 1;
                break;
            case 'c':
                formatted[0] = 2;
                break;
            case 'd':
                formatted[0] = 3;
                break;
            case 'e':
                formatted[0] = 4;
                break;
            case 'f':
                formatted[0] = 5;
                break;
            case 'g':
                formatted[0] = 6;
                break;
            case 'h':
                formatted[0] = 7;
                break;
            default:
                formatted[0] = -1;
                break;
        }
        formatted[1] = Math.abs(Character.getNumericValue(loc.charAt(1)) - 8);

        return formatted;
    }

    /**
     * Prints board as a string
     * @param boar - gameboard object
     */
    public static void print(Board boar){
        String[][] now = boar.stringBoard();
        System.out.println();
        for(int i = 0;i <8;i++){
            for(int j = 0; j < 8; j++) {
                if(j < 7) {
                    System.out.print(now[i][j]);
                } else        //if at the last column prints number at end
                {
                    System.out.print(now[i][j] + " " + (8-i));
                }
            }
            System.out.println();
        }
        System.out.println("a  b  c  d  e  f  g  h");
        System.out.println();
    }

    /**
     *
     * @param board - gameboard object
     * @return true if the king is dead
     */
    public static boolean kingsDead(Board board)
    {
        boolean wKAlive = false, bKAlive = false;

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSpot(i,j) != null) {
                    if (board.getSpot(i, j).isKing() && board.getSpot(i, j).isWhite()) {
                        wKAlive = true;
                    }
                    if (board.getSpot(i, j).isKing() && !board.getSpot(i, j).isWhite()) {
                        bKAlive = true;
                    }
                }
            }
        }

        if(!wKAlive) System.out.println("\nBlack Wins.");
        if(!bKAlive) System.out.println("\nWhite Wins.");

        return !wKAlive || !bKAlive;
    }

}
