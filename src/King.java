import javax.swing.*;

public class King extends Piece {
    boolean safe;
    boolean hasCastled = false;

    public King(String color, int row, int col, ImageIcon icon, boolean safe) {
        super("King", color, row, col, icon);
        this.safe = safe;
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        if (newRow < 0 || newRow > 7 || newCol > 7 || newCol < 0) {
            return false;
        }

        int absRow = Math.abs(row - newRow);
        int absCol = Math.abs(col - newCol);

        Square target = board[newRow][newCol];

        if ((absCol <= 1 && absRow <= 1) && (target.isEmpty() || !target.getPiece().color.equals(color))) {
            return true;
        }

        // This is the Castling part
        if (!hasMoved && absRow == 0 && absCol == 2) {
            // The Short Castling
            if (newCol > col) {
                Square rookSquare = board[row][7];
                if (!rookSquare.isEmpty() && rookSquare.getPiece() instanceof Rook) {
                    Rook rook = (Rook) rookSquare.getPiece();
                    if (!rook.hasMoved) {
                        if (board[row][col + 1].isEmpty() && board[row][col + 2].isEmpty()) {

                            if (GameLogic.isInCheck(color, board, row, col))
                                return false;

                            if (GameLogic.isInCheck(color, board, row, col + 1))
                                return false;
                            if (GameLogic.isInCheck(color, board, row, col + 2))
                                return false;

                            return true;
                        }
                    }
                }
            }

            // The Long Castling
            else if (newCol < col) {
                Square rookSquare = board[row][0];
                if (!rookSquare.isEmpty() && rookSquare.getPiece() instanceof Rook) {
                    Rook rook = (Rook) rookSquare.getPiece();
                    if (!rook.hasMoved) {
                        if (board[row][col - 1].isEmpty() && board[row][col - 2].isEmpty()
                                && board[row][col - 3].isEmpty()) {

                            if (GameLogic.isInCheck(color, board, row, col))
                                return false;

                            if (GameLogic.isInCheck(color, board, row, col - 1))
                                return false;
                            if (GameLogic.isInCheck(color, board, row, col - 2))
                                return false;

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    void move(int newRow, int newCol) {

        if (!hasMoved && row == newRow && Math.abs(newCol - col) == 2) {
            if (newCol > col) {

                Rook rook = (Rook) Square.parentBoard.board[row][7].getPiece();
                Square rookSquare = Square.parentBoard.board[row][7];
                Square newRookSquare = Square.parentBoard.board[row][col + 1];
                rookSquare.removePiece();
                newRookSquare.setPiece(rook);
                rook.move(row, col + 1);
            } else {

                Rook rook = (Rook) Square.parentBoard.board[row][0].getPiece();
                Square rookSquare = Square.parentBoard.board[row][0];
                Square newRookSquare = Square.parentBoard.board[row][col - 1];
                rookSquare.removePiece();
                newRookSquare.setPiece(rook);
                rook.move(row, col - 1);
            }
            hasCastled = true;
        }
        super.move(newRow, newCol);
        this.hasMoved = true;
    }

    @Override
    public King clone() {
        King k = new King(this.color, this.row, this.col, this.icon, this.safe);
        k.hasCastled = this.hasCastled;
        return k;
    }
}
