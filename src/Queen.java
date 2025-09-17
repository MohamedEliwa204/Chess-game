import javax.swing.*;

public class Queen extends Piece {
    public Queen(String color, int row, int col, ImageIcon icon) {
        super("Queen", color, row, col, icon);
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return false;
        }

        int absCol = Math.abs(newCol - col);
        int absRow = Math.abs(newRow - row);

        // The Queen is moving like a rook
        if (newCol == col) { // vertical move
            int step = (newRow > row) ? 1 : -1;
            for (int r = row + step; r != newRow; r += step) {
                if (r < 0 || r > 7)
                    return false;
                if (!board[r][col].isEmpty()) {
                    return false;
                }
            }
        } else if (newRow == row) { // horizontal move
            int step = (newCol > col) ? 1 : -1;
            for (int c = col + step; c != newCol; c += step) {
                if (!board[row][c].isEmpty()) {
                    return false;
                }
            }
        }
        // The Queen is moving like a bishop
        else if (absCol == absRow) {
            int stepRow = (newRow > row) ? 1 : -1;
            int stepCol = (newCol > col) ? 1 : -1;

            int r = row, c = col;
            while (true) {
                r += stepRow;
                c += stepCol;

                if (r == newRow && c == newCol)
                    break;

                if (!board[r][c].isEmpty()) {
                    return false;
                }
            }
        } else {
            return false;
        }

        if (!board[newRow][newCol].isEmpty() &&
                board[newRow][newCol].getPiece().color.equals(color)) {
            return false;
        }

        return true;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }

    @Override
    public Queen clone() {

        return new Queen(this.color, this.row, this.col, this.icon);
    }
}
