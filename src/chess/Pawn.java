package chess;
/**
 * Pawn Class
 *
 * @author  Thomas Hanna trh80
 */

public class Pawn extends Piece
{


    private boolean twoSpaces;
    public Pawn(boolean white){
        super(white);
        icon = icon + "P";
        twoSpaces = true;
    }

    /**
     * Used to determine if it's a piece's first move
     * @return returs true if it is a piece's first move
     */
    @Override
    public boolean moveFirst() {
        return true;
    }

    /**
     *
     * @param ans true if this is the first time moving the piece and we move two spaces(pawns only)
     */
    @Override
    public void setMovedTwo(boolean ans)
    {
        if(ans)
        {
            twoSpaces = true;
        }
        twoSpaces = false;
    }

    /**
     * Used to determine if a piece moved two
     * @return returns true if it did move 2 spaces
     */
    @Override
    public boolean movedTwo()
    {
        return twoSpaces;
    }

    /**
     *
     * @return returns true since it is a Pawn
     */
    @Override
    public boolean isPawn(){ return true;}

    /**
     *
     * @param board - board game being played on
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @return - if move is valid
     */
    @Override
    public boolean isValid(Board board, int[] to, int[] from ) {
        if (isValidHelper(board, to, from)) return false;
        if((from[0] == 6 && board.getSpot(from[0],from[1]).isWhite())|| (from[0] == 1 && !board.getSpot(from[0],from[1]).isWhite()))
        {
            if (white && from[0] == 6)  //white pawn first move
            {
                if(from[0] - to[0] == 1 && from[1] - to[1] == 0 && board.getSpot(to[0],to[1]) == null)
                {
                    return true;
                }
                else if((from[0] - to[0] == 2 && from[1] - to[1] == 0 && board.getSpot(to[0]+1,to[1]) == null && board.getSpot(to[0],to[1]) == null))
                {
                    return true;
                }
                else return (Math.abs(from[1] - to[1]) == 1 && from[0] - to[0] == 1 && board.getSpot(to[0], to[1]) != null);

            }
            else if(!white && from[0] == 1)
            {
                if(from[1] - to[1] == 0 && from[0] - to[0] == -1 && board.getSpot(to[0],to[1]) == null)
                {
                    return true;
                }
                else if((from[1] - to[1] == 0 && from[0] - to[0] == -2 && board.getSpot(to[0]-1,to[1]) == null && board.getSpot(to[0],to[1]) == null))
                {
                    return true;
                }
                else return (Math.abs(from[1] - to[1]) == 1 && from[0] - to[0] == -1 && board.getSpot(to[0], to[1]) != null);

            }
        }
        else{
            if (white)  //white pawn non first move
            {
                return (from[1] - to[1] == 0 && from[0] - to[0] == 1 && board.getSpot(to[0],to[1]) == null)
                        || (Math.abs(from[1] - to[1]) == 1 && from[0] - to[0] == 1 && board.getSpot(to[0],to[1]) != null);
            } else {
                return (from[1] - to[1] == 0 && from[0] - to[0] == -1 && board.getSpot(to[0],to[1]) == null)
                        || (Math.abs(from[1] - to[1]) == 1 && from[0] - to[0] == -1 && board.getSpot(to[0],to[1]) != null);
            }
        }
        return false;
    }


}
