package chess;
/**
 * Queen Class
 *
 * @author  Thomas Hanna trh80
 */

public class Queen extends Piece
{
    public Queen(boolean white)
    {
        super(white);
        icon = icon + "Q";
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
     *
     * @param board - board game being played on
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @return - if move is valid
     */
    @Override
    public boolean isValid(Board board, int[] to, int[] from) {
        if (isValidHelper(board, to, from)) return false;

        for(int i = 0; i <8; i++){
            if(Math.abs(from[0] - to[0]) == i &&
                    Math.abs(from[1] - to[1]) == i){
                return !isBlocked(board,to, from);
            }
            if(Math.abs(from[0] - to[0]) == i &&
                    Math.abs(from[1] - to[1]) == 0){
                return !isBlocked(board,to, from);
            }
            if(Math.abs(from[0] - to[0]) == 0 &&
                    Math.abs(from[1] - to[1]) == i){
                return !isBlocked(board,to, from);
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
        if (to[1]>from[1] ) pathY = 1;

        if(to[0] == from[0]){
            for (int i = 1; i < Math.abs(to[1] - from[1]); i++) {
                if (board.getSpot(to[0],from[1]+i*pathY) != null) {
                    return true;
                }
            }
            return false;
        }
        if(to[1] == from[1]){
            for (int i = 1; i < Math.abs(to[0] - from[0]); i++) {
                if (board.getSpot(from[0]+i*pathX,to[1]) != null) {
                    return true;
                }
            }
            return false;
        }

        for (int i = 1; i < Math.abs(to[0] - from[0]); i++) {
            if (board.getSpot(from[0]+i*pathX,from[1]+i*pathY) != null) {
                return true;
            }
        }
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

}
