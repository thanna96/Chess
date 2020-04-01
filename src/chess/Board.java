package chess;
/**
 * Board Class
 *
 * @author  Thomas Hanna trh80
 */
public class Board {
    public static Piece[][] chessBoard = new Piece[8][8];
    public static boolean printIt = false;

    /**
     * Constructor for the Board object
     */
    public Board(){
        chessBoard[0][0] = new Rook(false);
        chessBoard[0][1] = new Knight(false);
        chessBoard[0][2] = new Bishop(false);
        chessBoard[0][3] = new Queen(false);
        chessBoard[0][4] = new King(false);
        chessBoard[0][5] = new Bishop(false);
        chessBoard[0][6] = new Knight(false);
        chessBoard[0][7] = new Rook(false);
        for(int i=0;i<8;i++){
            chessBoard[1][i] = new Pawn(false);
        }
        chessBoard[7][0] = new Rook(true);
        chessBoard[7][1] = new Knight(true);
        chessBoard[7][2] = new Bishop(true);
        chessBoard[7][3] = new Queen(true);
        chessBoard[7][4] = new King(true);
        chessBoard[7][5] = new Bishop(true);
        chessBoard[7][6] = new Knight(true);
        chessBoard[7][7] = new Rook(true);
        for(int i=0;i<8;i++){
            chessBoard[6][i] = new Pawn(true);
        }
    }

    /**
     *
     * @return 2D String version of the board for printing
     */
    public String[][] stringBoard(){
        String[][] board = new String[8][8];
        for(int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                    if(chessBoard[i][j] == null){
                        if ((i+j)%2 == 0){
                            board[i][j] = "   ";
                        }
                        else {
                            board[i][j] = "## ";
                        }
                    }
                    else {
                        board[i][j] = chessBoard[i][j].getIcon() + " ";
                    }
                }
            }

        return board;
    }

    /**
     * This method checks different cases and moves the piece to the desired location if it passes.
     *
     * @param board - actual board display
     * @param piece - current object being moved
     * @param to - location piece is being moved to
     * @param from - location piece was moved from
     * @param color - if piece is white or black
     */
    public void setSpot(Board board, Piece piece, int[] to, int[] from, String color)
    {
        // castling
        if(piece.isKing() && piece.moveFirst())
        {
            if(to[1] == 6 && !piece.isWhite() && chessBoard[0][7] != null
                    && chessBoard[0][7].isRook() &&  chessBoard[0][7].moveFirst()
                    && piece.moveFirst() && castlingHelper() && !exposesKing(board, to, from, color))
            {                                                                                                //black piece right side of board
                chessBoard[to[0]][to[1]] = piece;
                chessBoard[0][7] = null;
                chessBoard[0][5] = new Rook(false);
                chessBoard[from[0]][from[1]] = null;
                piece.setfirstMove(false);
                printIt = true;
                return;
            }
            else if(to[1] == 2 && !piece.isWhite() && chessBoard[0][0] != null
                    && chessBoard[0][0].isRook() && chessBoard[0][0].moveFirst()
                    && piece.moveFirst() && castlingHelper() && !exposesKing(board,to,from,color))
            {                                                                                               //black piece left side of board
                chessBoard[to[0]][to[1]] = piece;
                chessBoard[0][0] = null;
                chessBoard[0][3] = new Rook(false);
                chessBoard[from[0]][from[1]] = null;
                piece.setfirstMove(false);
                printIt = true;
                return;
            }
            else if(to[1] == 6 && piece.isWhite() && chessBoard[7][7] != null
                    && chessBoard[7][7].isRook() && chessBoard[7][7].moveFirst()
                    && piece.moveFirst() && castlingHelper() && !exposesKing(board,to,from,color))
            {                                                                                               //white piece right side of board
                chessBoard[to[0]][to[1]] = piece;
                chessBoard[7][7] = null;
                chessBoard[7][5] = new Rook(true);
                chessBoard[from[0]][from[1]] = null;
                piece.setfirstMove(false);
                printIt = true;
                return;
            }
            else if(to[1] == 2 && piece.isWhite() && chessBoard[7][0] != null
                    && chessBoard[7][0].isRook() &&  chessBoard[7][0].moveFirst()
                    && piece.moveFirst() && castlingHelper() && !exposesKing(board,to,from,color))
            {                                                                                               //white piece left side of board
                chessBoard[to[0]][to[1]] = piece;
                chessBoard[7][0] = null;
                chessBoard[7][3] = new Rook(true);
                chessBoard[from[0]][from[1]] = null;
                piece.setfirstMove(false);
                printIt = true;
                return;
            }
        }

        //Enpassant checks initially if it its a white pawn at row 3 or a black pawn at row 4
        if((piece.isPawn() && from[0] == 3 && piece.isWhite()) || (piece.isPawn() && from[0] == 4 && !piece.isWhite()) )
        {
            if(from[0] == 3 && (from[1] < 7 && from[1] > 0))
            {
                from[1] = from[1] + 1;      //white pawn right side enpassant
                if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && !chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo()) {
                    chessBoard[to[0]][to[1]] = piece;
                    chessBoard[from[0]][from[1]] = null;
                    from[1] = from[1] - 1;
                    chessBoard[from[0]][from[1]] = null;
                    from[1] = from[1] + 1;
                    piece.setMovedTwo(false);
                    return;
                }
                from[1] = from[1] - 2;      //white pawn left side enpassant
                if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && !chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo())
                {
                    chessBoard[to[0]][to[1]] = piece;
                    chessBoard[from[0]][from[1]] = null;
                    from[1] = from[1] + 1;
                    chessBoard[from[0]][from[1]] = null;
                    piece.setMovedTwo(false);
                    return;
                }
                from[1] = from[1] + 1;
            }
            else if(from[0] == 3 && (from[1] == 7 || from[1] == 0))
            {
                if(from[1] == 7)
                {
                    from[1] = from[1] - 1;      //white pawn left side enpassant for board right edge
                    if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && !chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo())
                    {
                        chessBoard[to[0]][to[1]] = piece;
                        chessBoard[from[0]][from[1]] = null;
                        from[1] = from[1] + 1;
                        chessBoard[from[0]][from[1]] = null;
                        piece.setMovedTwo(false);
                        return;
                    }
                    from[1] = from[1] + 1;
                }
                else
                {
                    from[1] = from[1] + 1;      //white pawn right side enpassant for left edge of board
                    if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && !chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo())
                    {
                        chessBoard[to[0]][to[1]] = piece;
                        chessBoard[from[0]][from[1]] = null;
                        from[1] = from[1] - 1;
                        chessBoard[from[0]][from[1]] = null;
                        from[1] = from[1] + 1;
                        piece.setMovedTwo(false);
                        return;
                    }
                    from[1] = from[1] - 1;
                }
            }
            else if(from[0] == 4 && (from[1] < 7 && from[1] > 0))
            {
                from[1] = from[1] + 1;      //black pawn right side enpassant
                if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo())
                {
                    chessBoard[to[0]][to[1]] = piece;
                    chessBoard[from[0]][from[1]] = null;
                    from[0] = from[0] - 1;
                    chessBoard[from[0]][from[1]] = null;
                    from[0] = from[0] + 1;
                    piece.setMovedTwo(false);
                    return;
                }
                from[1] = from[1] - 2;      //black pawn left side enpassant
                if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo())
                {
                    chessBoard[to[0]][to[1]] = piece;
                    chessBoard[from[0]][from[1]] = null;
                    from[1] = from[1] + 1;
                    chessBoard[from[0]][from[1]] = null;
                    piece.setMovedTwo(false);
                    return;
                }
                from[1] = from[1] + 1;
            }
            else if(from[0] == 4 && (from[1] == 7 || from[1] == 0))
            {
                if(from[1] == 7)
                {
                    from[1] = from[1] - 1;      //black pawn left side enpassant for right edge of board
                    if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo())
                    {
                        chessBoard[to[0]][to[1]] = piece;
                        chessBoard[from[0]][from[1]] = null;
                        from[1] = from[1] + 1;
                        chessBoard[from[0]][from[1]] = null;
                        piece.setMovedTwo(false);
                        return;
                    }
                    from[1] = from[1] + 1;
                }
                else
                {
                    from[1] = from[1] + 1;      //black pawn right side enpassant for left side of board
                    if(chessBoard[from[0]][from[1]] != null && chessBoard[from[0]][from[1]].isPawn() && chessBoard[from[0]][from[1]].isWhite() && chessBoard[from[0]][from[1]].movedTwo())
                    {
                        chessBoard[to[0]][to[1]] = piece;
                        chessBoard[from[0]][from[1]] = null;
                        from[0] = from[0] - 1;
                        chessBoard[from[0]][from[1]] = null;
                        from[0] = from[0] + 1;
                        piece.setMovedTwo(false);
                        return;
                    }
                    from[1] = from[1] - 1;
                }
            }

        }

        //if the selected spot has no piece
        if(chessBoard[from[0]][from[1]] == null){
            System.out.println("Illegal move, try again");
            System.out.println();
            printIt = false;
            return;
        }

        //If piece is black but user is white
        if(!piece.isWhite() && color.equals("white")){
            System.out.println("Illegal move, try again");
            System.out.println();
            printIt = false;
            return;
        }

        //If piece is white but user is not white
        if(piece.isWhite() && color.equals("black")){
            System.out.println("Illegal move, try again");
            System.out.println();
            printIt = false;
            return;
        }

        //For attempting to move to the same spot
        if (to[0] == from[0] && to[1] == from[1]) {
            System.out.println("Illegal move, try again");
            System.out.println();
            printIt = false;
            return;
        }

        //If you're trying to move the king piece to an exposed spot
        if (color.equals("white") && piece.isKing() && board.dangerZone(board,color)[to[0]][to[1]].equals("b")) {
            System.out.println("Illegal move, try again");
            printIt = false;
            return;
        }
        if (color.equals("black") && piece.isKing() && board.dangerZone(board,color)[to[0]][to[1]].equals("w")) {
            System.out.println("Illegal move, try again");
            printIt = false;
            return;
        }

        if(piece.isValid(board,to,from) && !exposesKing(board,to,from,color))
        {
            if(piece.isRook() || piece.isKing() )       //sets Kings/Rooks to false if they moved
            {
                piece.setfirstMove(false);
            }

            if(piece.isPawn())
            {
                piece.twoSpaces = Math.abs(from[0] - to[0]) == 2;
            }

            chessBoard[to[0]][to[1]] = piece;
            chessBoard[from[0]][from[1]] = null;
            printIt = true;

        }
        else{
            System.out.println("Illegal move, try again");
            System.out.println();
            printIt = false;
        }
    }

    /**
     *
     * @param x - row value of spot
     * @param y - column value of spot
     * @return - return location of piece/certain spot
     */
    public Piece getSpot(int x, int y){
        if(x >= 8 || y >=8 || x < 0 || y < 0) return null;

        if(chessBoard[x][y] != null){
            return chessBoard[x][y];
        }
        return null;
    }

    /**
     * Returns a board that will have the locations a piece can attack with the format:
     * 'b' if black piece can move to the spot
     * 'w' if a white piece can move to the spot
     *
     * @param board - actual board display
     * @param color - piece color
     * @return - a string of the board that displays where each piece can 'attack'
     */
    public String[][] dangerZone(Board board, String color){
        String[][] danZoneW = new String[8][8];
        String[][] danZoneB = new String[8][8];
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getSpot(i,j) != null) {
                    //White Attack radius
                    if (getSpot(i, j).isWhite()) {
                        for (int a = 0; a < 8; a++) {
                            for (int b = 0; b < 8; b++) {
                                if (getSpot(i, j).isValid(board, new int[]{a, b}, new int[]{i, j}) && !getSpot(i, j).isPawn()) {
                                    danZoneW[a][b] = "w";
                                }
                            }
                        }
                        //Black Attack radius
                    } else if(!getSpot(i, j).isWhite()){
                        for (int a = 0; a < 8; a++) {
                            for (int b = 0; b < 8; b++) {
                                if (getSpot(i, j).isValid(board, new int[]{a, b}, new int[]{i, j}) && !getSpot(i, j).isPawn()) {
                                    danZoneB[a][b] = "b";
                                }
                            }
                        }
                    }
                }
            }
        }
        //Separate statement for the Pawns
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(danZoneB[i][j] == null) {
                    danZoneB[i][j] = "0";
                }
                if(danZoneW[i][j] == null) {
                    danZoneW[i][j] = "0";
                }
                if(getSpot(i,j) != null) {
                    if (getSpot(i, j).isPawn() && i > 0 && i < 7) {
                        //White pawns attack radius
                        if(getSpot(i, j).isWhite() ) {
                            if(j<7 && j>0) {
                                danZoneW[i - 1][j - 1] = "w";
                                danZoneW[i - 1][j + 1] = "w";
                            }
                            if(j == 0) danZoneW[i-1][j+1] = "w";
                        }
                        //Black pawns attack radius
                        else if(!getSpot(i, j).isWhite()){
                            if(j<7 && j>0) {
                                danZoneB[i + 1][j - 1] = "b";
                                danZoneB[i + 1][j + 1] = "b";
                            }
                            if(j == 7) danZoneB[i+1][j-1] = "b";
                        }
                        if(j == 0 && !getSpot(i, j).isWhite()) danZoneB[i+1][j+1] = "b";
                        if(j == 7 && getSpot(i, j).isWhite()) danZoneW[i-1][j-1] = "w";
                    }
                }
            }
        }
        if(color.equals("white") )return danZoneB;
        return danZoneW;
    }

    /**
     * The same as danger zone but AFTER a piece is moved to the location
     * @param board - actual board display/being used
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @param color - piece color
     * @return - string of board danger zone after move is made
     */
    public String[][] futureDangerZone(Board board, int[] to, int[] from, String color){
        String[][] danZone;

        if(getSpot(to[0], to[1]) == null){
            //sets new board
            chessBoard[to[0]][to[1]] = getSpot(from[0], from[1]);
            chessBoard[from[0]][from[1]] = null;

            danZone = board.dangerZone(board,color);

            //returns to old board
            chessBoard[from[0]][from[1]] = getSpot(to[0], to[1]);
            chessBoard[to[0]][to[1]] = null;
        }else{
            Piece temp = getSpot(to[0], to[1]);

            //sets new board
            chessBoard[to[0]][to[1]] = getSpot(from[0], from[1]);
            chessBoard[from[0]][from[1]] = null;

            danZone = board.dangerZone(board,color);

            //returns to old board
            chessBoard[from[0]][from[1]] = getSpot(to[0], to[1]);
            chessBoard[to[0]][to[1]] = temp;
        }

        return danZone;
    }

    /**
     *
     * @param board - actual board display/being used
     * @param to - location where piece is going
     * @param from - location where piece moved from
     * @param color - piece color
     * @return true if the move exposes your king to enemy attack
     */
    public boolean exposesKing(Board board, int[] to, int[] from,String color){
        String[][] sBoard = board.futureDangerZone(board,to,from,color);
        int wx=0,wy=0,bx=0,by=0;
        if(board.getSpot(from[0], from[1]).isKing()) return false;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getSpot(i,j) != null) {
                    if (board.getSpot(i, j).isKing() && board.getSpot(i, j).isWhite()) {
                        wx = i;
                        wy = j;
                    }
                    if (board.getSpot(i, j).isKing() && !board.getSpot(i, j).isWhite()) {
                        bx = i;
                        by = j;
                    }
                }
            }
        }

        if (board.getSpot(from[0],from[1]) != null) {
            if (board.getSpot(from[0],from[1]).isWhite() && sBoard[wx][wy].equals("b")) {

                return true;

            } else return !board.getSpot(from[0], from[1]).isWhite() && sBoard[bx][by].equals("w");
        }
        return false;
    }

    /**
     *
     * @return returns true if no pieces in between rook and king during castling, false if pieces in the way
     */
    public boolean castlingHelper()
    {
        if(Board.chessBoard[7][5] == null && Board.chessBoard[7][6] == null)    //checks right side white side for pieces
        {
            return true;
        }
        else if( (Board.chessBoard[0][5] == null && Board.chessBoard[0][6] == null))     //checks right side black side for pieces
        {
            return true;
        }
        else //checks left side black side for pieces
            if( (Board.chessBoard[7][1] == null && Board.chessBoard[7][2] == null &&
                Board.chessBoard[7][3] == null))     //checks left side white side for pieces
        {
            return true;
        }
        else return (Board.chessBoard[0][1] == null && Board.chessBoard[0][2] == null &&
                    Board.chessBoard[0][3] == null);
    }

    /**
     *
     * @param board - actual board display/being used
     * @return true if the king is in the danger zone
     */
    public boolean inCheck(Board board){
        int bKingI = 0;
        int bKingJ = 0;
        int wKingI = 0;
        int wKingJ = 0;

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getSpot(i, j) != null){
                    if (getSpot(i, j).isKing() && !getSpot(i, j).isWhite()){
                        bKingI = i;
                        bKingJ = j;
                    }
                    if (getSpot(i, j).isKing() && getSpot(i, j).isWhite()){
                        wKingI = i;
                        wKingJ = j;
                    }
                }

            }
        }

        if (board.dangerZone(board,"black")[bKingI][bKingJ].equals("w")) return true;
        return board.dangerZone(board, "white")[wKingI][wKingJ].equals("b");
    }

    /**
     *
     * @param board - actual board display/being used
     * @return true if a king is in checkmate
     */
    public boolean checkMate(Board board){
        int countB=0,countW=0,Bvalid=0,Wvalid=0;
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(getSpot(i, j) != null){
                    if (getSpot(i, j).isKing() && getSpot(i, j).isWhite()){
                        if((getSpot(i, j).isValid(board, new int[]{i+1, j}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i+1][j].equals("b")) countW++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i+1, j-1}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i+1][j-1].equals("b")) countW++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i+1, j+1}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i+1][j+1].equals("b")) countW++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i, j-1}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i][j-1].equals("b")) countW++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i, j+1}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i][j+1].equals("b")) countW++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i-1, j-1}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i-1][j-1].equals("b")) countW++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i-1, j}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i-1][j].equals("b")) countW++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i-1, j+1}, new int[]{i, j}))){
                            Wvalid++;
                            if(board.dangerZone(board,"white")[i-1][j+1].equals("b")) countW++;
                        }
                    }
                    if (getSpot(i, j).isKing() && !getSpot(i, j).isWhite()){
                        if((getSpot(i, j).isValid(board, new int[]{i+1, j}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i+1][j].equals("w")) countB++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i+1, j-1}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i+1][j-1].equals("w")) countB++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i+1, j+1}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i+1][j+1].equals("w")) countB++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i, j-1}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i][j-1].equals("w")) countB++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i, j+1}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i][j+1].equals("w")) countB++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i-1, j-1}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i-1][j-1].equals("w")) countB++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i-1, j+1}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i-1][j+1].equals("w")) countB++;
                        }
                        if((getSpot(i, j).isValid(board, new int[]{i-1, j}, new int[]{i, j}))){
                            Bvalid++;
                            if(board.dangerZone(board,"black")[i-1][j].equals("w")) countB++;
                        }
                    }
                }

            }
        }
        if (countW == Wvalid && board.inCheck(board)) System.out.println("\nCheckmate. \nBlack Wins");
        if (countB==Bvalid && board.inCheck(board))System.out.println("\nCheckmate. \nWhite Wins");

        return (countW == Wvalid || countB == Bvalid) && board.inCheck(board);
    }
}
