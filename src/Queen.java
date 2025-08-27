import javax.swing.*;

public class Queen extends Piece {
    public Queen(String color, int row, int col, ImageIcon icon) {
        super("Queen", color, row, col, icon);
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) {
            return false;
        }

        int absCol = Math.abs(newCol - col);
        int absRow = Math.abs(newRow - row);

        // The Queen is moving like a rook
        if (newCol == col || newRow == row) {
            if (newCol == col) { // vertical move
                int step = (newRow > row) ? 1 : -1;
                for (int r = row + step; r != newRow; r += step) {
                    if (!board[r][col].isEmpty()) {
                        return false;
                    }
                }
            } else { // horizontal move
                int step = (newCol > col) ? 1 : -1;
                for (int c = col + step; c != newCol; c += step) {
                    if (!board[row][c].isEmpty()) {
                        return false;
                    }
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

                if (r == newRow && c == newCol) break;

                if (!board[r][c].isEmpty()) {
                    return false;
                }
            }
        } else {
            return false;
        }

        if (!board[newRow][newCol].isEmpty() &&
                board[newRow][newCol].piece.color.equals(color)) {
            return false;
        }

        return true;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }
}
