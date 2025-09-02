import javax.swing.*;

public class Pawn extends Piece {
    boolean start;

    public Pawn(String color, int row, int col, ImageIcon icon, boolean start) {
        super("Pawn", color, row, col, icon);
        this.start = start;
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        if(newRow<0 || newRow>7 || newCol>7 || newCol<0){
            return false;
        }
        int absCol = Math.abs(newCol - col);
        int dir = color.equals("Black") ? -1 : 1;


        if (absCol == 0 && newRow == row + dir && board[newRow][newCol].isEmpty()) {
            return true;
        }


        if (absCol == 0 && start && newRow == row + 2 * dir
                && board[row + dir][col].isEmpty()
                && board[newRow][newCol].isEmpty()) {
            return true;
        }


        if (absCol == 1 && newRow == row + dir) {
            Square target = board[newRow][newCol];
            if (!target.isEmpty() && !target.getPiece().color.equals(color)) {
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
