package chess;
/**
 * Bishop Class
 *
 * @author  Thomas Hanna trh80
 */

public class Bishop extends Piece {
    public Bishop(boolean white){
        super(white);
        icon = icon + "B";
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

        //Checks if the location we're trying to reach is a valid move
        for(int i = 0; i <8; i++){
            if(Math.abs(from[0] - to[0]) == i && Math.abs(from[1] - to[1]) == i){
                return !isBlocked(board, to, from);
            }
        }

        return false;
    }

    /**
     *
     * @param board - board game being played on
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @return - if piece is blocked on where it is going
     */
    @Override
    public boolean isBlocked(Board board, int[] to, int[] from){
        //For finding out the direction were attempting to move the piece
        int pathX = -1;
        int pathY = -1;
        if (to[0]>from[0]) pathX = 1;
        if (to[1]>from[1]) pathY = 1;

        for (int i = 1; i < Math.abs(to[0] - from[0]); i++) {
            if (board.getSpot(from[0]+i*pathX,from[1]+i*pathY) != null) {
                return true;
            }
        }
        return false;
    }


}
