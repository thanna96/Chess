package chess;
/**
 * King Class
 *
 * @author  Thomas Hanna trh80
 */

public class King extends Piece {

    private boolean firstMove;
    public King(boolean white){
        super(white);
        icon = icon + "K";
        firstMove = true;
    }

    /**
     * sets variable firstMove to correct value
     * @param ans true if this is the first time moving the piece
     */
    public void setfirstMove(boolean ans)
    {
        if(ans)
        {
            firstMove = true;
        }
        firstMove = false;
    }

    /**
     * Used to determine if a piece moved two
     * @return returns true if it did move 2 spaces
     */
    @Override
    public boolean movedTwo(){
        return false;
    }

    /**
     * Used to determine if it's a piece's first move
     * @return returs true if it is a piece's first move
     */
    @Override
    public boolean moveFirst()
    {
        return firstMove;
    }

    @Override
    public boolean isKing(){ return true;}

    /**
     *
     * @param board - board game being played on
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @return - if move is valid
     */
    @Override
    public boolean isValid(Board board, int[] to, int[] from)
    {
        if (isValidHelper(board, to, from)) return false;

        return Math.abs(from[0] - to[0]) <= 1 &&
                Math.abs(from[1] - to[1]) <= 1;
    }

    /**
     * blocks the king from moving to a spot that could get him killed
     * @param board - board game being played on
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @return - if piece is blocked on where it is going
     */
    @Override
    public boolean isBlocked(Board board, int[] to, int[] from){
        if (board.getSpot(from[0],from[1]).isWhite() && board.dangerZone(board,"white")[to[0]][to[1]].equals("b")) return true;
        return !board.getSpot(from[0], from[1]).isWhite() && board.dangerZone(board, "black")[to[0]][to[1]].equals("w");
    }

}
