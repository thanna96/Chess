package chess;
/**
 * Piece Class
 *
 * @author  Thomas Hanna trh80
 */

public abstract class Piece {
    public boolean white = false;
    public String icon = "b";
    public boolean firstMove, twoSpaces;

    /**
     * Constructor for Piece Object
     * @param white set True for white pieces, false for black
     */
    public Piece(boolean white){
        this.setWhite(white);
        if(isWhite()){
            icon = "w";
        }
    }

    /**
     *
     * @return a String that represents letter of the piece
     */
    public String getIcon(){
        return icon;
    }

    /**
     *
     * @return a Boolean for whether a piece is white or not
     */
    public boolean isWhite() {
        return this.white;
    }

    /**
     * Setter method for piece color
     * @param white true for white
     */
    public void setWhite(boolean white){
        this.white = white;
    }

    /**
     *
     * @param board The game board object
     * @param to the location you want to move your piece to[0] is the row to[1] is the column
     * @param from the location your piece is moving from to[0] is the row to[1] is the column
     * @return whether or not the location you're moving to is valid or not
     */
    public boolean isValid(Board board, int[] to, int[] from){
        return false;
    }

    /**
     * For finding the king when searching the board
     * @return true if the piece is a king
     */
    public boolean isKing(){ return false;}

    /**
     * For finding the pawn when searching the board
     * @return true if the piece is a pawn
     */
    public boolean isPawn(){ return false;}

    /**
     * For finding the rook when searching the board
     * @return true if the piece is a rook
     */
    public boolean isRook(){ return false;}

    /**
     * Checks if you're moving to a spot that has a piece of the same color
     * @param board - board game being played on
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @return - true if move is valid
     */
    static boolean isValidHelper(Board board, int[] to, int[] from) {
        if(to[0] >= 8 || to[1] >=8 || to[0] < 0 || to[1] < 0)
        {
            return true;
        }

        if (board.getSpot(to[0],to[1]) != null && board.getSpot(from[0],from[1]) != null){
            if (board.getSpot(to[0],to[1]).isWhite() && board.getSpot(from[0],from[1]).isWhite()){
                return true;
            }
            return !board.getSpot(to[0], to[1]).isWhite() && !board.getSpot(from[0], from[1]).isWhite();
        }
        return false;
    }

    /**
     * checks if there is ANY piece in the path from one piece to the desired location
     * @param board The game board object
     * @param to the location you want to move your piece to[0] is the row to[1] is the column
     * @param from the location your piece is moving from to[0] is the row to[1] is the column
     * @return true if the path for the piece is blocked
     */
    public boolean isBlocked(Board board, int[] to, int[] from){
        return false;
    }

    /**
     *
     * @param ans true if this is the first time moving the piece
     */
    public void setfirstMove(boolean ans)
    {
        firstMove = true;
    }

    /**
     *
     * @param ans true if this is the first time moving the piece and we move two spaces(pawns only)
     */
    public void setMovedTwo(boolean ans)
    {
        twoSpaces = true;
    }

    /**
     * Used to determine if a piece moved two
     * @return returns true if it did move 2 spaces
     */
    public abstract boolean movedTwo();

    /**
     * Used to determine if it's a piece's first move
     * @return returs true if it is a piece's first move
     */
    public abstract boolean moveFirst();
}
