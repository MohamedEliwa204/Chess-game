import javax.swing.*;

public class Rook extends Piece {
    public Rook(String color, int row, int col, ImageIcon icon) {
        super("Rook", color, row, col, icon);
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7) {
            if (newCol == col) { // the rook will move vertically
                int step = (newRow > row) ? 1 : -1;
                for (int r = row + step; r != newRow; r += step) {
                    if (r < 0 || r > 7)
                        return false;
                    if (!board[r][col].isEmpty()) {
                        return false;
                    }
                }
                if (!board[newRow][newCol].isEmpty() && board[newRow][newCol].getPiece().color.equals(color)) {
                    return false;
                }
                return true;
            }
            if (newRow == row) { // the rook will move horizontally
                int step = (newCol > col) ? 1 : -1;
                for (int c = col + step; c != newCol; c += step) {
                    if (c < 0 || c > 7)
                        return false;
                    if (!board[row][c].isEmpty()) {
                        return false;
                    }
                }
                if (!board[newRow][newCol].isEmpty() && board[newRow][newCol].getPiece().color.equals(color)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public Rook clone() {
        Rook r = new Rook(this.color, this.row, this.col, this.icon);
        r.hasMoved = this.hasMoved;
        return r;
    }

}
