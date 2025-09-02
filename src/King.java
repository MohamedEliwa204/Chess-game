import javax.swing.*;

public class King extends Piece{
    boolean safe;
    public King(String color, int row, int col, ImageIcon icon, boolean safe) {
        super("King", color, row, col, icon);
        this.safe=safe;
    }

    @Override
    boolean isValidMove(int newRow, int newCol,Square[][] board) {
        if(newRow<0 || newRow>7 || newCol>7 || newCol<0){
            return false;
        }

        int absRow = Math.abs(row-newRow);
        int absCol = Math.abs(col-newCol);
        Square target = board[newRow][newCol];
        if((absCol<=1 && absRow<=1) && (target.isEmpty() || !target.getPiece().color.equals(color))){
            return true;
        }
        return false;
    }

    @Override
    void move(int newRow, int newCol) {
        super.move(newRow, newCol);
    }

}
