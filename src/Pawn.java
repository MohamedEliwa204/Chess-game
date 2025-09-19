import java.util.Stack;

import javax.swing.*;

public class Pawn extends Piece {
    public Pawn(String color, int row, int col, ImageIcon icon) {
        super("Pawn", color, row, col, icon);
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7)
            return false;

        int direction = color.equals("White") ? 1 : -1;
        int moveRow = newRow - row;
        int moveCol = newCol - col;

        if (moveCol == 0 && moveRow == direction && board[newRow][newCol].isEmpty()) {
            return true;
        }

        if (moveCol == 0
                && ((row == 1 && color.equals("White")) || (row == 6 && color.equals("Black")))
                && moveRow == 2 * direction
                && board[row + direction][col].isEmpty()
                && board[newRow][newCol].isEmpty()) {
            return true;
        }

        if (Math.abs(moveCol) == 1 && moveRow == direction) {
            if (!board[newRow][newCol].isEmpty() && !board[newRow][newCol].getPiece().color.equals(color)) {
                return true;
            }
        }

        return false;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }

    @Override
    public Pawn clone() {

        Pawn newPawn = new Pawn(this.color, this.row, this.col, this.icon);

        newPawn.hasMoved = this.hasMoved;
        newPawn.alive = this.alive;

        newPawn.parentRow = (Stack<Integer>) this.parentRow.clone();
        newPawn.parentCol = (Stack<Integer>) this.parentCol.clone();

        return newPawn;
    }
}
