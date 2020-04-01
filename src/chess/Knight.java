package chess;
/**
 * Knight Class
 *
 * @author  Thomas Hanna trh80
 */

public class Knight extends Piece {

    public Knight(boolean white){
        super(white);
        icon = icon + "N";
    }

    /**
     * Used to determine if a piece moved two
     * @return returns false not needed
     */
    @Override
    public boolean movedTwo(){
        return false;
    }

    /**
     * Used to determine if it's a piece's first move
     * @return returs true not needed
     */
    @Override
    public boolean moveFirst() {
        return true;
    }

    /**
     *
     * @param board - board game being played on
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @return - if move is valid
     */
    @Override
    public boolean isValid(Board board, int[] to, int[] from) {

        if (isValidHelper(board, to, from)) return false;

        int x = Math.abs(from[0] - to[0]);
        int y = Math.abs(from[1] - to[1]);

        return x*y == 2;
    }
}
