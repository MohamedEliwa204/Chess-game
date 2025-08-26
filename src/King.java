import javax.swing.*;

public class King extends Piece{
    boolean safe;
    public King(String color, int row, int col, ImageIcon icon, boolean safe) {
        super("King", color, row, col, icon);
        this.safe=safe;
    }

    @Override
    boolean isValidMove(int newRow, int newCol,Piece[][] board) { // i am using a board of pieces could be changed to be of squares
        // to know whether this part of the board contains a piece of the same or different color or if it is empty
        int absRow = Math.abs(row-newRow);
        int absCol = Math.abs(col-newCol);
        Piece target = board[newRow][newCol];
        if((absCol<=1 && absRow<=1) && (!target.color.equals(color) || target==null)){
            return true;
        }
        return false;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }

}
