import javax.swing.*;
import java.awt.*;

public class Bishop extends Piece{
    public Bishop(String color, int row, int col, ImageIcon icon){
        super("Bishop", color, row, col, icon);
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        int absCol = Math.abs(newCol-col);
        int absRow = Math.abs(newRow-row);


        if(newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
            return false;
        }

        if(absCol != absRow) {
            return false;
        }

        int stepRow = (newRow > row) ? 1 : -1;
        int stepCol = (newCol > col) ? 1 : -1;

        int c = col, r = row;
        while (true) {
            c += stepCol;
            r += stepRow;

            if (r == newRow && c == newCol) {
                break;
            }

            if (!board[r][c].isEmpty()) {
                return false;
            }
        }


        if (!board[newRow][newCol].isEmpty() && board[newRow][newCol].piece.color.equals(color)) {
            return false;
        }

        return true;
    }


    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }
}
