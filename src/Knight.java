import javax.swing.*;

public class Knight extends Piece {
    public Knight(String color, int row, int col, ImageIcon icon) {
        super("Knight", color, row, col, icon);
    }

    @Override
    boolean isValidMove(int newRow, int newCol, Square[][] board) {
        Square target = board[newRow][newCol];
        if(target.isEmpty() || !target.getPiece().color.equals(color)){
            if((newRow==row+2 || newRow==row-2) && (newCol== col+1 || newCol==col-1)){
                return true;
            }
            if((newRow==row+1 || newRow == row-1) && (newCol==col-2 || newCol == col+2)){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }

}
