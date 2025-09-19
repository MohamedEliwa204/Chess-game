public class BoardUtils {

    public static Square[][] copyBoard(Square[][] original) {
        Square[][] copy = new Square[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Copy the square (same row/col/color)
                Square origSquare = original[i][j];
                Square newSquare = new Square(null, origSquare.getBackground(), i, j);

                // Copy the piece if present
                if (!origSquare.isEmpty()) {
                    Piece origPiece = origSquare.getPiece();
                    newSquare.setPiece(origPiece.clone()); // make sure Piece has a proper clone()
                }

                copy[i][j] = newSquare;
            }
        }

        return copy;
    }
}
