public class Move {
    final Piece movedPiece;
    final int startRow, startCol;
    final int endRow, endCol;
    final Piece capturedPiece; // Can be null if no piece was captured

    public Move(Piece movedPiece, int startRow, int startCol, int endRow, int endCol, Piece capturedPiece) {
        this.movedPiece = movedPiece;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.capturedPiece = capturedPiece;
    }
}
