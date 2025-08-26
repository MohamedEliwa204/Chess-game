import javax.swing.*;

public class Pawn extends Piece {
    boolean start;

    public Pawn(String color, int row, int col, ImageIcon icon, boolean start) {
        super("Pawn", color, row, col, icon);
        this.start = start;
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Piece[][] board) { // i am using a board of pieces could be changed to be of squares
        // to know whether this part of the board contains a piece of the same or different color or if it is empty
        int absCol = Math.abs(newCol - col);
        int dir = color.equals("Black") ? -1 : 1;


        if (absCol == 0 && newRow == row + dir && board[newRow][newCol] == null) {
            return true;
        }


        if (absCol == 0 && start && newRow == row + 2 * dir
                && board[row + dir][col] == null
                && board[newRow][newCol] == null) {
            return true;
        }


        if (absCol == 1 && newRow == row + dir) {
            Piece target = board[newRow][newCol];
            if (target != null && !target.color.equals(this.color)) {
                return true;
            }
        }

        return false;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
        start = false;
    }
}

